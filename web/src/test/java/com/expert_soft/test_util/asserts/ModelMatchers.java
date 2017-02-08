package com.expert_soft.test_util.asserts;


import com.expert_soft.model.Phone;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.List;


public class ModelMatchers {

   public static Matcher<Object> equals(final Phone phone){
       return new BaseMatcher<Object>(){

           @Override
           public boolean matches(final Object item) {
              return Comparators.equals((Phone) item, phone);
           }

           @Override
           public void describeTo(Description description) {
              description.appendText(Comparators.failMsg(phone, null));
           }
       };
   }

    public static Matcher<Object> hasPhone(final Phone phone){
        return new BaseMatcher<Object>(){

            @Override
            public boolean matches(Object item) {
                List<Phone> phones = (List<Phone>) item;
                return Comparators.equals(phones.get(0), phone);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" list has at 0 index item: " + phone);
            }
        };
    }
}
