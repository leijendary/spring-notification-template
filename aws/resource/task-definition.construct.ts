import { Duration, RemovalPolicy } from "aws-cdk-lib";
import { IRepository, Repository } from "aws-cdk-lib/aws-ecr";
import {
  AppProtocol,
  Compatibility,
  ContainerImage,
  CpuArchitecture,
  LogDriver,
  OperatingSystemFamily,
  Secret,
  TaskDefinition,
  TaskDefinitionProps,
} from "aws-cdk-lib/aws-ecs";
import { PolicyDocument, PolicyStatement, Role, ServicePrincipal } from "aws-cdk-lib/aws-iam";
import { LogGroup, RetentionDays } from "aws-cdk-lib/aws-logs";
import { DatabaseSecret } from "aws-cdk-lib/aws-rds";
import { Secret as SecretManager } from "aws-cdk-lib/aws-secretsmanager";
import { ITopic } from "aws-cdk-lib/aws-sns";
import { Construct } from "constructs";
import env, { isProd } from "../env";

type SnsTopic = {
  firebase: ITopic;
  ios: ITopic;
};

type TaskDefinitionConstructProps = {
  repositoryArn: string;
  snsTopic: SnsTopic;
};

const environment = env.environment;
const port = env.port;
const imageTag = env.imageTag;
const { id, name } = env.stack;
const family = `${name}-${environment}`;
const assumedBy = new ServicePrincipal("ecs-tasks.amazonaws.com");
const logPrefix = "/ecs/fargate";

export class TaskDefinitionConstruct extends TaskDefinition {
  constructor(scope: Construct, props: TaskDefinitionConstructProps) {
    const { repositoryArn, snsTopic } = props;
    const memoryMiB = isProd() ? "2 GB" : "0.5 GB";
    const cpu = isProd() ? "1 vCPU" : "0.25 vCPU";
    const repository = getRepository(scope, repositoryArn);
    const image = getImage(repository);
    const logGroup = createLogGroup(scope);
    const taskRole = createTaskRole(scope);
    const executionRole = createExecutionRole(scope, logGroup, repository);
    const config: TaskDefinitionProps = {
      family,
      compatibility: Compatibility.FARGATE,
      memoryMiB,
      cpu,
      runtimePlatform: {
        cpuArchitecture: CpuArchitecture.ARM64,
        operatingSystemFamily: OperatingSystemFamily.LINUX,
      },
      taskRole,
      executionRole,
    };

    super(scope, `${id}TaskDefinition-${environment}`, config);

    this.container(scope, image, snsTopic, logGroup);
    this.trustPolicy(taskRole, executionRole);
    this.grantTopicAccess(taskRole, snsTopic);
  }

  private container(scope: Construct, image: ContainerImage, snsTopic: SnsTopic, logGroup: LogGroup) {
    const auroraCredentials = getAuroraCredentials(scope);
    const dataStorageCredentials = getDataStorageCredentials(scope);
    const integrationCredentials = getIntegrationCredentials(scope);

    this.addContainer(`${id}Container-${environment}`, {
      containerName: name,
      image,
      logging: LogDriver.awsLogs({
        streamPrefix: logPrefix,
        logGroup,
      }),
      portMappings: [
        {
          name,
          containerPort: port,
          hostPort: port,
          appProtocol: AppProtocol.http,
        },
      ],
      healthCheck: {
        command: ["CMD-SHELL", "wget -qO- http://localhost/actuator/health || exit 1"],
        startPeriod: Duration.seconds(isProd() ? 0 : 200),
      },
      environment: {
        SPRING_PROFILES_ACTIVE: environment,
        AWS_EC2_METADATA_DISABLED: "true",
        SPRING_CLOUD_AWS_SNS_PLATFORM_FIREBASE_ARN: snsTopic.firebase.topicArn,
        SPRING_CLOUD_AWS_SNS_PLATFORM_IOS_ARN: snsTopic.ios.topicArn,
      },
      secrets: {
        SPRING_DATASOURCE_PRIMARY_USERNAME: auroraCredentials.username,
        SPRING_DATASOURCE_PRIMARY_PASSWORD: auroraCredentials.password,
        SPRING_DATASOURCE_READONLY_USERNAME: auroraCredentials.username,
        SPRING_DATASOURCE_READONLY_PASSWORD: auroraCredentials.password,
        SPRING_KAFKA_JAAS_OPTIONS_USERNAME: dataStorageCredentials.kafka.username,
        SPRING_KAFKA_JAAS_OPTIONS_PASSWORD: dataStorageCredentials.kafka.password,
        SPRING_SENDGRID_API_KEY: integrationCredentials.sendgrid.apiKey,
        TWILIO_ACCOUNT_SID: integrationCredentials.twilio.accountSid,
        TWILIO_AUTH_TOKEN: integrationCredentials.twilio.authToken,
      },
    });
  }

  private trustPolicy(taskRole: Role, executionRole: Role) {
    const trustPolicy = new PolicyStatement({
      actions: ["sts:AssumeRole"],
      resources: [this.taskDefinitionArn],
    });

    taskRole.addToPolicy(trustPolicy);
    executionRole.addToPolicy(trustPolicy);
  }

  private grantTopicAccess(role: Role, snsTopic: SnsTopic) {
    const createEndpointPolicy = new PolicyStatement({
      actions: ["sns:CreatePlatformEndpoint"],
      resources: [snsTopic.firebase.topicArn, snsTopic.ios.topicArn],
    });

    role.addToPolicy(createEndpointPolicy);

    snsTopic.firebase.grantPublish(role);
    snsTopic.ios.grantPublish(role);
  }
}

const createLogGroup = (scope: Construct) => {
  return new LogGroup(scope, `${id}LogGroup-${environment}`, {
    logGroupName: `${logPrefix}/${family}`,
    removalPolicy: RemovalPolicy.DESTROY,
    retention: RetentionDays.ONE_MONTH,
  });
};

const createTaskRole = (scope: Construct) => {
  return new Role(scope, `${id}TaskRole-${environment}`, {
    roleName: `${id}TaskRole-${environment}`,
    assumedBy,
  });
};

const createExecutionRole = (scope: Construct, logGroup: LogGroup, repository: IRepository) => {
  return new Role(scope, `${id}ExecutionRole-${environment}`, {
    roleName: `${id}ExecutionRole-${environment}`,
    assumedBy,
    inlinePolicies: {
      [`${id}ExecutionRolePolicy-${environment}`]: new PolicyDocument({
        statements: [
          new PolicyStatement({
            actions: [
              "ecr:BatchCheckLayerAvailability",
              "ecr:BatchGetImage",
              "ecr:GetAuthorizationToken",
              "ecr:GetDownloadUrlForLayer",
            ],
            resources: [repository.repositoryArn],
          }),
          new PolicyStatement({
            actions: ["logs:CreateLogStream", "logs:PutLogEvents"],
            resources: [logGroup.logGroupArn],
          }),
        ],
      }),
    },
  });
};

const getRepository = (scope: Construct, repositoryArn: string) => {
  return Repository.fromRepositoryArn(scope, `${id}Repository-${environment}`, repositoryArn);
};

const getImage = (repository: IRepository) => {
  return ContainerImage.fromEcrRepository(repository, imageTag);
};

const getAuroraCredentials = (scope: Construct) => {
  const credential = DatabaseSecret.fromSecretNameV2(
    scope,
    `${id}AuroraSecret-${environment}`,
    `api-aurora-${environment}`
  );

  return {
    username: Secret.fromSecretsManager(credential, "username"),
    password: Secret.fromSecretsManager(credential, "password"),
  };
};

const getDataStorageCredentials = (scope: Construct) => {
  const credential = SecretManager.fromSecretNameV2(
    scope,
    `${id}DataStorageSecret-${environment}`,
    `data-storage-${environment}`
  );

  return {
    kafka: {
      username: Secret.fromSecretsManager(credential, "kafka.username"),
      password: Secret.fromSecretsManager(credential, "kafka.password"),
    },
  };
};

const getIntegrationCredentials = (scope: Construct) => {
  const credential = SecretManager.fromSecretNameV2(
    scope,
    `${id}IntegrationSecret-${environment}`,
    `integration-${environment}`
  );

  return {
    sendgrid: {
      apiKey: Secret.fromSecretsManager(credential, "sendgrid.apiKey"),
    },
    twilio: {
      accountSid: Secret.fromSecretsManager(credential, "twilio.accountSid"),
      authToken: Secret.fromSecretsManager(credential, "twilio.authToken"),
    },
  };
};
