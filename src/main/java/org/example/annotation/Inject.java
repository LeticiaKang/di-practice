
package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD}) //생성자, 필드, 메서드에 붙을 수 있음
@Retention(RetentionPolicy.RUNTIME) // 유지기간 : runtime동안
public @interface Inject {
}