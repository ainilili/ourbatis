package org.nico.ourbatis.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.nico.ourbatis.annotation.RenderIgnore;
import org.nico.ourbatis.annotation.RenderName;
import org.nico.ourbatis.annotation.RenderPrimary;

public class ReflactUtils {

	public static Field[] getFields(Class<?> clazz){
		return clazz.getDeclaredFields();
	}
	
	public static <T extends Annotation> T getAnnotation(Field field, Class<T> annotationType){
		return field.getDeclaredAnnotation(annotationType);
	}
	
	public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotationType){
		return clazz.getDeclaredAnnotation(annotationType);
	}
	
	public static boolean isAnnotation(Field field, Class<? extends Annotation> annotationType){
		return getAnnotation(field, annotationType) != null;
	}
	
	public static boolean isAnnotation(Class<?> clazz, Class<? extends Annotation> annotationType){
		return getAnnotation(clazz, annotationType) != null;
	}
	
	public static boolean isRenderIgnore(Field field){
		return isAnnotation(field, RenderIgnore.class);
	}
	
	public static boolean isRenderPrimary(Field field){
		return isAnnotation(field, RenderPrimary.class);
	}
	
	public static boolean isRenderName(Field field){
		return isAnnotation(field, RenderName.class);
	}
	
	public static boolean isRenderName(Class<?> clazz){
		return isAnnotation(clazz, RenderName.class);
	}
	
	public static String getRenderName(Field field){
		return getAnnotation(field, RenderName.class).value();
	}
	
	public static String getRenderName(Class<?> clazz){
		return getAnnotation(clazz, RenderName.class).value();
	}
	
	public static Object getFieldValue(String fieldName, Object obj){
		Object target = null;
		Field field = getField(fieldName, obj.getClass());
		if(field != null) {
			try {
				field.setAccessible(true);
				target = field.get(obj);
				//jump
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
		}
		return target;
	}
	
	public static Field getField(String fieldName, Class<?> clazz){
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			if(clazz.getSuperclass() != null){
				field = getField(fieldName, clazz.getSuperclass());
			}else{
				return null;
			}
		}
		return field;
	}
}
