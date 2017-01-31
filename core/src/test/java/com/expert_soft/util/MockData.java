package com.expert_soft.util;


import org.mockito.Mockito;

import javax.validation.Validator;
import java.util.Collections;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.when;

public class MockData {

    public static Validator getTrueValidator(){
        Validator mockValidator = Mockito.mock(Validator.class);
        when(mockValidator.validate(anyObject()))
                .thenReturn(Collections.EMPTY_SET);
        return mockValidator;
    }
}
