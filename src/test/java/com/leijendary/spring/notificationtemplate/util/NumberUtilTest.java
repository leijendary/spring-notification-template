package com.leijendary.spring.notificationtemplate.util;

import com.leijendary.spring.notificationtemplate.ApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class NumberUtilTest extends ApplicationTests {

    @Test
    public void sampleTest_should_work() {
        Assert.isTrue(SampleUtil.isSample(), "This is working");
    }
}
