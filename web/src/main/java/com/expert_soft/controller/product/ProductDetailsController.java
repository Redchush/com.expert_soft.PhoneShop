package com.expert_soft.controller.product;

import com.expert_soft.model.Phone;
import com.expert_soft.service.PhoneService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Controller("productDetailsController")
public class ProductDetailsController {
    private static final Logger logger = Logger.getLogger(ProductDetailsController.class);

    private PhoneService service;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ModelAndView product(@PathVariable(value = "id") Long id,
                                HttpServletRequest request){
        Phone phone = service.getPhone(id);
        if (phone == null){
            return notFound(request);
        }
        return new ModelAndView("productDetails", "phone", phone);
    }

    private ModelAndView notFound(HttpServletRequest req) {
        logger.error(String.format("Somebody try to find non-existent entity. Url: %s \n",
                req.getRequestURL() + req.getQueryString()));

        ModelAndView mav = new ModelAndView();
        mav.addObject("url", req.getRequestURL());
        mav.addObject("object", "phone");
        mav.addObject("description", "This phone not exist in our list or it was deleted.");
        mav.setViewName("/error/notFound");
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NumberFormatException.class, ConstraintViolationException.class})
    public ModelAndView invalidKey(HttpServletRequest req, Exception ex) {
        logger.error(String.format("Somebody try to find unexacting entity by invalid code. " +
                "Url: %s \n Raised %s",req.getRequestURL(), ex));
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", req.getRequestURL());
        mav.addObject("object", "phone");
        mav.addObject("description", "This phone keys must be positive number");
        mav.setViewName("/error/notFound");
        return mav;
    }

    @Autowired
    public void setService(PhoneService service) {
        this.service = service;
    }

}
