package org.nico.ourbatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Applied to the field and class name of the domain, the annotation will exist using the name 
 * specified in value as the target name.In addition, inside the annotation is a parameter named 
 * render, which is used to determine whether to participate in the wrapper modifications if the 
 * value is specified, which defaults to false, meaning that the target name will be equal to the 
 * value if the value is not specified
 * 
 * @author nico
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RenderName {
	
	String value();
	
	boolean render() default false;
}
