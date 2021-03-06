package com.leijendary.spring.notificationtemplate.validator.v1;

import com.leijendary.spring.notificationtemplate.data.request.v1.SampleRequestV1;
import com.leijendary.spring.notificationtemplate.validator.annotation.v1.FieldsNotEqualV1;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Optional.ofNullable;

public class SampleRequestV1FieldsNotSameV1Validator implements ConstraintValidator<FieldsNotEqualV1, SampleRequestV1> {

    @Override
    public void initialize(final FieldsNotEqualV1 constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final SampleRequestV1 value, final ConstraintValidatorContext context) {
        return ofNullable(value.getField1())
                .map(field1 -> !field1.equals(String.valueOf(value.getField2())))
                .orElse(true);
    }
}
