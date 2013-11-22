package de.shop.util.cdi;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Stereotype;

/**
 * @author <a>Team 8</a>
 */
@Alternative
@Stereotype
@Target(TYPE)
@Retention(RUNTIME)
public @interface MockService {
}
