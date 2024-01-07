package srinageswari.programmedhousehold.coreservice.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import srinageswari.programmedhousehold.coreservice.common.Constants;

/**
 * @author smanickavasagam
 *     <p>Custom constraint annotation for ItemValidator
 */
@Documented
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ItemBusinessValidator.class})
public @interface ValidItem {

  String message() default Constants.NOT_VALIDATED_ELEMENT;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
