package com.springapp;

import com.springapp.services.RestMappingApiDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Component
public class IncludeApiInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {
    private ConfigurableListableBeanFactory factory;
    private RestMappingApiDescriptionService dictionary;

    @Autowired
    public void setFactory(ConfigurableListableBeanFactory factory) {
        this.factory = factory;
    }

    @Autowired
    public void setDictionary(RestMappingApiDescriptionService dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name: beanDefinitionNames) {
            BeanDefinition beanDefinition = factory.getBeanDefinition(name);
            String originalClassName = beanDefinition.getBeanClassName();
            try {
                if (originalClassName == null) continue;
                Class<?> aClass = Class.forName(originalClassName);

                if (aClass.isAnnotationPresent(RestController.class) && aClass.isAnnotationPresent(RequestMapping.class)) {
                    String basePath = aClass.getAnnotation(RequestMapping.class).value()[0];

                    Method[] methods = aClass.getMethods();

                    for (Method m: methods) {
                        if (m.isAnnotationPresent(IncludeAPI.class) && m.isAnnotationPresent(RequestMapping.class)) {
                            StringBuilder params = new StringBuilder("(");
                            boolean first = true;

                            Parameter[] parameters = m.getParameters();
                            for (Parameter parameter: parameters) {
                                if (first) {
                                    params.append(parameter.getType().getName()).append(" ").append(parameter.getName());
                                    first = false;
                                } else {
                                    params.append(",").append(parameter.getType().getName()).append(" ").append(parameter.getName());
                                }
                            }
                            params.append(")");

                            RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
                            String value = requestMapping.value().length != 0 ? requestMapping.value()[0] : "";
                            String method = requestMapping.method().length != 0 ? requestMapping.method()[0].name() : "GET";

                            dictionary.addRequestMappingDescription(basePath + value, method, m.getName(), basePath.replace("/",""), params.toString());
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
