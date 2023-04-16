package ru.bobrov.vyacheslav.test2.configuration;

import lombok.val;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import ru.bobrov.vyacheslav.test2.annotations.Times;
import ru.bobrov.vyacheslav.test2.annotations.TimesInterceptor;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
public class CustomBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //Подключаем прокси с кастомным перехватчиком для всех бинов, помеченных аннотацией
        val annotationTimes = Arrays.stream(bean.getClass().getDeclaredMethods())
                .flatMap(method -> Arrays.stream(method.getDeclaredAnnotations()))
                .filter(annotation -> annotation.annotationType().equals(Times.class))
                .map(annotation -> (Times) annotation)
                .collect(Collectors.toSet());

        if (!annotationTimes.isEmpty()) {
            val annotationsValid = annotationTimes.stream()
                    .noneMatch(annotation -> annotation.count() <= 0);
            if (!annotationsValid)
                throw new BeanInitializationException("Wrong Times annotation in bean: " + beanName);

            val factory = new ProxyFactory(bean);
            factory.addAdvice(new TimesInterceptor());
            return factory.getProxy();
        } else
            return bean;
    }
}
