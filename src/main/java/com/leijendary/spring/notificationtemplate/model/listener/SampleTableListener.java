package com.leijendary.spring.notificationtemplate.model.listener;

import com.leijendary.spring.notificationtemplate.event.producer.SampleProducer;
import com.leijendary.spring.notificationtemplate.model.SampleTable;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import static com.leijendary.spring.notificationtemplate.factory.SampleFactory.toSchema;
import static com.leijendary.spring.notificationtemplate.util.SpringContext.getBean;

public class SampleTableListener {

    @PostPersist
    public void onSave(final SampleTable sampleTable) {
        final var sampleProducer = getBean(SampleProducer.class);
        final var sampleSchema = toSchema(sampleTable);

        sampleProducer.create(sampleSchema);
    }

    @PostUpdate
    public void onUpdate(final SampleTable sampleTable) {
        final var sampleProducer = getBean(SampleProducer.class);
        final var sampleSchema = toSchema(sampleTable);

        sampleProducer.update(sampleSchema);
    }

    @PostRemove
    public void onDelete(final SampleTable sampleTable) {
        final var sampleProducer = getBean(SampleProducer.class);
        final var sampleSchema = toSchema(sampleTable);

        sampleProducer.delete(sampleSchema);
    }
}
