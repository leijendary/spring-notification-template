import { Stack, StackProps } from "aws-cdk-lib";
import { Topic } from "aws-cdk-lib/aws-sns";
import { Construct } from "constructs";
import env, { isProd } from "../env";
import { FargateServiceConstruct } from "./../resource/fargate-service.construct";
import { TaskDefinitionConstruct } from "./../resource/task-definition.construct";

const environment = env.environment;
const { id, name } = env.stack;
const vpcId = env.vpcId;
const account = env.account;
const region = env.region;
const { id: namespaceId } = env.namespace;

export class ApplicationStack extends Stack {
  constructor(scope: Construct, props: StackProps) {
    super(scope, `${id}Stack-${environment}`, props);

    const repositoryArn = `arn:aws:ecr:${region}:${account}:repository/${name}`;
    const clusterArn = `arn:aws:ecs:${region}:${account}:cluster/api-cluster-${environment}`;
    const namespaceArn = `arn:aws:servicediscovery:${region}:${account}:namespace/${namespaceId}`;
    const firebaseArn = `arn:aws:sns:${region}:${account}:app/GCM/app-firebase`;
    const iosArn = `arn:aws:sns:${region}:${account}:app/${isProd() ? "APNS" : "APNS_SANDBOX"}/app-ios`;
    const snsTopic = {
      firebase: Topic.fromTopicArn(this, `${id}FirebaseTopic-${environment}`, firebaseArn),
      ios: Topic.fromTopicArn(this, `${id}IosTopic-${environment}`, iosArn),
    };
    const taskDefinition = new TaskDefinitionConstruct(this, {
      repositoryArn,
      snsTopic,
    });

    new FargateServiceConstruct(this, {
      vpcId,
      clusterArn,
      namespaceArn,
      taskDefinition,
    });
  }
}
