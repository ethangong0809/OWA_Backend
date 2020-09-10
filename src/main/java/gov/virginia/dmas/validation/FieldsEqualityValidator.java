package gov.virginia.dmas.validation;

import java.lang.reflect.Field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.springframework.util.ReflectionUtils;

public class FieldsEqualityValidator implements ConstraintValidator<FieldsEquality, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldsEquality constraintAnnotation) {
        firstFieldName = constraintAnnotation.firstFieldName();
        secondFieldName = constraintAnnotation.secondFieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        try {
            Class<?> clazz = value.getClass();

            Field firstField = ReflectionUtils.findField(clazz, firstFieldName);
            firstField.setAccessible(true);
            Object first = firstField.get(value);

            Field secondField = ReflectionUtils.findField(clazz, secondFieldName);
            secondField.setAccessible(true);
            Object second = secondField.get(value);
            
            if (first != null && second != null && !first.equals(second)) {
            	ConstraintViolationBuilder cvb = context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate());
            	cvb.addPropertyNode(firstFieldName).addConstraintViolation();
            	ConstraintViolationBuilder cvb2 = context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate());
            	cvb2.addPropertyNode(secondFieldName).addConstraintViolation();

            	return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
	
}
