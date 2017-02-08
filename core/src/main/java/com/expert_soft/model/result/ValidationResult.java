package com.expert_soft.model.result;


import javax.validation.ConstraintViolation;
import java.util.Collections;
import java.util.Set;

public class ValidationResult<T> {

    /**
     * immutable success set
     */
    public static final ValidationResult SUCCESS_VALIDATION_RESULT = new ValidationResult();

    private final Set<ConstraintViolation<T>> violations;
    private final T infoObject;

    public ValidationResult() {
        violations = Collections.EMPTY_SET;
        infoObject = null;
    }

    public ValidationResult(Set<ConstraintViolation<T>> violations, T infoObject) {
        this.violations = violations;
        this.infoObject = infoObject;
    }

    public Set<ConstraintViolation<T>> getViolations() {
        return violations;
    }

    public T getInfoObject() {
        return infoObject;
    }

    public boolean isSuccess(){
        return violations.isEmpty();
    }


}
