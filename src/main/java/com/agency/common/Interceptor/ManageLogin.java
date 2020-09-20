package com.agency.common.Interceptor;

import java.lang.annotation.*;

/**
 * @author ch33o
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ManageLogin {

}
