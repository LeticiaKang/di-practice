package org.example.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BeanFactory {
    private Set<Class<?>> preInstantiatedBeans;

    private Map<Class<?>, Object> beans = new HashMap<>();
    //클래스 타입의 객체를 key로 갖고, 인스턴스를 value로 가지는 필드

    public BeanFactory(Set<Class<?>> preInstantiatedBeans) {
        this.preInstantiatedBeans = preInstantiatedBeans; // Beans 초기화
        initialize();
    }

    @SuppressWarnings("unchecked")
    public void initialize() {
        for (Class<?> clazz : preInstantiatedBeans) {
            Object instance = createInstance(clazz); //클래스 타입 객체를 전달해서 인스턴스를 생성하고 hashmap에 넣기
            beans.put(clazz, instance); //key는 class타입 객체, value는 instance
        }
    }

    private Object createInstance(Class<?> concreteClass) {
        //생성자
        Constructor<?> constructor = findConstructor(concreteClass); //class타입 객체로 Constructor를 조회

        // 파라미터
        List<Object> parameters = new ArrayList<>();
        for (Class<?> typeClass : Objects.requireNonNull(constructor).getParameterTypes()) {
            parameters.add(getBean(typeClass));
        }

        try {
            return constructor.newInstance(parameters.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Constructor<?> findConstructor(Class<?> concreteClass) {
        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(concreteClass);

        if (Objects.nonNull(constructor)) {
            return constructor;
        }

        return concreteClass.getConstructors()[0];
    }

    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }
}

