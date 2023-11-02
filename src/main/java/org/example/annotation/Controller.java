package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) //class, interface, enum declaration에 붙을 수 있는 annotation임
@Retention(RetentionPolicy.RUNTIME) //유지기간 : runtime동안
public @interface Controller {
}
