package ru.bobrov.vyacheslav.test2.annotations;


import lombok.val;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class TimesInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //Прокси может быть создан для интерфейса,
        //необходимо получить соответствующий метод целевого класса для поиска аннотаций
        val invocationMethod = invocation.getMethod();
        val targetAnnotatedMethod = Arrays.stream(Objects.requireNonNull(invocation.getThis()).getClass()
                        .getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Times.class) && isMethodEquals(method, invocationMethod))
                .findFirst()
                .orElse(null);

        val times = targetAnnotatedMethod != null ? targetAnnotatedMethod.getAnnotation(Times.class) : null;
        if (times != null)
            return invoke(invocation, times);
        else
            return invocation.proceed();
    }

    private Object invoke(MethodInvocation invocation, Times times) throws Throwable {
        Object retValue = null; //Метод может вызываться несколько раз, возвращаем результат последнего вызова
        for (int i = 0; i < times.count(); i++)
            retValue = invocation.proceed();

        return retValue;
    }

    //Проверка соответствия метода из целевого класса, методу из прокси по сигнатуре методов
    private boolean isMethodEquals(Method method1, Method method2) {
        return method1.getName().equals(method2.getName()) &&
                method1.getReturnType().equals(method2.getReturnType()) &&
                Arrays.equals(method1.getParameterTypes(), method2.getParameterTypes());
    }
}
