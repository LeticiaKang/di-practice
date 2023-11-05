
package org.example;

import org.example.annotation.Controller;
import org.example.annotation.Service;
import org.example.di.BeanFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BeanFactoryTest {
    private Reflections reflections;
    private BeanFactory beanFactory;

    @BeforeEach // test매서드가 호출되기 전에 미리 호출되는 메서드
    @SuppressWarnings("unchecked")
    public void setup() {
        reflections = new Reflections("org.example");
        //org.example 밑에 있는 class를 reflection에 사용 / reflection 초기화
        Set<Class<?>> preInstantiatedClazz = getTypesAnnotatedWith(Controller.class, Service.class);
        beanFactory = new BeanFactory(preInstantiatedClazz);
    }

    //Class<? extends Annotation>... annotations : Annotation타입이 여러개 들어와도 됨
    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        return Arrays.stream(annotations)
                .flatMap(annotation -> reflections.getTypesAnnotatedWith(annotation).stream())
                .collect(Collectors.toSet()); //중복 제거
    }

    @Test
    public void diTest() {
        UserController userController = beanFactory.getBean(UserController.class);
        assertNotNull(userController.getUserService()); //userController(UserService)가 null이 아니면
    }
}
