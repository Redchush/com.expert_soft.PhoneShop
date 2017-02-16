package com.expert_soft.controller.login;


import com.expert_soft.controller.ServletConstants;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class LoginController implements MessageSourceAware {

    private MessageSource msgSource;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(Locale locale,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            String msg = msgSource.getMessage("security.login.error", null, locale);
            model.addObject(ServletConstants.ERROR_MSG,  msg);
        }
        if (logout != null) {
            String msg = msgSource.getMessage("security.logout.success", null, locale);
            model.addObject(ServletConstants.SUCCESS_MSG, msg);
        }
        model.setViewName("logIn");
        return model;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.msgSource = messageSource;
    }
}
