package org.nico.ourbatis.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.nico.ourbatis.annotation.BuildIgnore;

public class ReflactUtils {

	public static Field[] getFields(Class<?> clazz){
		return clazz.getDeclaredFields();
	}
	
	public static <T extends Annotation> T getAnnotation(Field field, Class<T> annotationType){
		return field.getDeclaredAnnotation(annotationType);
	}
	
	public static boolean isAnnotation(Field field, Class<? extends Annotation> annotationType){
		return field.getDeclaredAnnotation(annotationType) != null;
	}
	
	public static boolean isBuildIgnore(Field field){
		return isAnnotation(field, BuildIgnore.class);
	}
}
