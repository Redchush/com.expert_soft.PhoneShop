package com.expert_soft.test_util;


import com.expert_soft.service.ResponseService;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.validation.Validator;
import java.util.Collections;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

public class MockData {

    public static final String STUB_ANSWER = "stub_answer";

    public static Validator getMockTrueValidator(){
        Validator mockValidator = Mockito.mock(Validator.class);
        when(mockValidator.validate(anyObject()))
                .thenReturn(Collections.EMPTY_SET);
        return mockValidator;
    }

    public static ResponseService getSingleAnswerResponseService(){
        return Mockito.mock(ResponseService.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return STUB_ANSWER;
            }
        });
    }


}
