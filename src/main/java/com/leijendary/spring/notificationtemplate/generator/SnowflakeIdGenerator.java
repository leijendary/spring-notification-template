package com.leijendary.spring.notificationtemplate.generator;

import com.leijendary.spring.notificationtemplate.worker.SnowflakeIdWorker;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class SnowflakeIdGenerator implements IdentifierGenerator {

    public static final String STRATEGY = "com.leijendary.spring.notificationtemplate.generator.SnowflakeIdGenerator";

    private static final SnowflakeIdWorker WORKER = new SnowflakeIdWorker();

    @Override
    public Serializable generate(final SharedSessionContractImplementor session,
                                 final Object object) throws HibernateException {
        return WORKER.nextId();
    }
}
