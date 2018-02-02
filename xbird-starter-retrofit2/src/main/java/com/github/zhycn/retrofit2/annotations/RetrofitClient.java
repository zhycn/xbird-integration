package com.github.zhycn.retrofit2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * Annotates an interface as Retrofit service.
 *
 * Use this annotation to qualify a Retrofit annotated interface for auto-detection and automatic
 * instantiation.
 * 
 * @author zhycn
 * @since 1.0.0 2018-02-02
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface RetrofitClient {

  /**
   * Defines the name of the service bean when registered to the underlying context. If left
   * unspecified the name of the service bean is generated using
   * {@link org.springframework.beans.factory.annotation.Qualifier}, If no Qualifier annotation, we
   * would use full class name instead.
   *
   * @return the name of the bean.
   */
  String name() default "";

  /**
   * Defines the name of retrofit should be used in building the service endpoint. Allows for more
   * concise annotation declarations e.g. {@code @RetrofitClient("default")}
   * 
   * @return the specified retrofit instance to build endpoint
   */
  String value() default "default";

}
