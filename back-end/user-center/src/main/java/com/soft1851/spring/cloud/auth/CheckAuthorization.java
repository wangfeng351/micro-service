package com.soft1851.spring.cloud.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description TODO
 * @Author wf
 * @Date 2020/10/13
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAuthorization {
    String value();
}
