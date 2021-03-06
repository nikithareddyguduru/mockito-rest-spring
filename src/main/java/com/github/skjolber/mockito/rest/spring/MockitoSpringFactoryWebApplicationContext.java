package com.github.skjolber.mockito.rest.spring;

import java.util.List;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class MockitoSpringFactoryWebApplicationContext extends AnnotationConfigWebApplicationContext {

	protected List<Class<?>> beans;
	
	public MockitoSpringFactoryWebApplicationContext(List<Class<?>> beans) {
		this.beans = beans;
	}

	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
		super.loadBeanDefinitions(beanFactory);
		
		for(int i = 0; i < beans.size(); i++) {
			GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
			beanDefinition.setBeanClass(MockitoSpringFactoryBean.class);
			beanDefinition.setLazyInit(false);
			beanDefinition.setAbstract(false);
			beanDefinition.setAutowireCandidate(true);
			
			MutablePropertyValues propertyValues = new MutablePropertyValues();
			propertyValues.addPropertyValue("className", beans.get(i).getName());
			beanDefinition.setPropertyValues(propertyValues);
	
			beanFactory.registerBeanDefinition(MockitoSpringFactoryBean.class.getSimpleName() + i, beanDefinition);
		}
	}
}
