package com.expert_soft.model.util;


import com.expert_soft.model.UserInfoValidationTest;
import org.apache.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestValidationUtil {

    private static final Logger LOGGER = Logger.getLogger(UserInfoValidationTest.class);
    private Validator validator;

    public TestValidationUtil() {
        validator = createValidator();
    }

    public TestValidationUtil(Validator validator) {
        this.validator = validator;

    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public static Validator createValidator(){
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        return vf.getValidator();
    }


    /**
     * @param obj - obj for validation
     * @param invalidValueExp - expected invalid value
     * @param <T> - type of object to be validated
     */
    public<T> ConstraintViolation<T> executeOneInvalidField(T obj, Object invalidValueExp){

        Set<ConstraintViolation<T>> violations_one = validator.validate(obj);

        assertEquals(getTestFailMsg(violations_one), 1, violations_one.size());
        ConstraintViolation<T> next = violations_one.iterator().next();
        Object invalidValueAct = next.getInvalidValue();

        assertEquals(getTestFailMsg(violations_one), invalidValueExp, invalidValueAct);
        return next;
    }

    /**
     * @param obj - obj for validation
     * @param invalidValueExp - expected invalid value
     * @param <T> - type of object to be validated
     */
    public<T> ConstraintViolation<T> executeOneInvalidField(T obj,
                                                            Object invalidValueExp,
                                                            Class<?> group){

        Set<ConstraintViolation<T>> violations_one = validator.validate(obj, group);

        assertEquals(getTestFailMsg(violations_one), 1, violations_one.size());
        ConstraintViolation<T> next = violations_one.iterator().next();
        Object invalidValueAct = next.getInvalidValue();

        assertEquals(getTestFailMsg(violations_one), invalidValueExp, invalidValueAct);
        return next;
    }

    /**
     *
     * @param obj - obj for validation
     * @param invalidValueExp - expected invalid value
     * @param msgExp -expected message
     * @param <T> - type of object to be validated
     */
    public<T> void executeOneInvalidField(T obj, Object invalidValueExp, String msgExp){
        ConstraintViolation<T> violation = executeOneInvalidField(obj, invalidValueExp);
        assertEquals(getErrorMsg(violation, invalidValueExp), msgExp, violation.getMessage());
    }


    public<T> void executeOneInvalidField(T obj, Object invalidValueExp, String msgExp, Class group){
        ConstraintViolation<T> violation = executeOneInvalidField(obj, invalidValueExp, group);
        assertEquals(getErrorMsg(violation, invalidValueExp), msgExp, violation.getMessage());
    }

    public <T> String getTestFailMsg(Set<ConstraintViolation<T>> violations){
        return String.format("Violations:%s\n%s", violations.size(),violations);
    }

    private String getErrorMsg(ConstraintViolation<?> violation, Object value){
        return String.format("Validation msg %s \nfailed on value %s", violation, value);
    }


}
