package com.expert_soft.controller;


import com.expert_soft.exception.service.NoSuchEntityException;
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
import java.util.List;

@Controller
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class);

    private PhoneService service;

    @Autowired
    public void setService(PhoneService service) {
        this.service = service;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ModelAndView products(){
        logger.debug("logger enabled!!");
        List<Phone> all = service.findAll();
        return new ModelAndView("productList", "phones", all);
    }


    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ModelAndView product(@PathVariable(value = "id") Long id){
        Phone phone = service.getPhone(id);
        return new ModelAndView("productDetails", "phone", phone);
    }


    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchEntityException.class)
    public ModelAndView notFound(HttpServletRequest req, Exception ex) {
        logger.error(String.format("Somebody try to find unexacting entity. Url: %s \n Raised %s",
                     req.getRequestURL(), ex));
        ModelAndView mav = new ModelAndView();
        mav.addObject("url", req.getRequestURL());
        mav.addObject("object", "phone");
        mav.addObject("description", "This phone not exist in our list or it was deleted.");
        mav.setViewName("/error/productNotFound");
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
        mav.setViewName("/error/productNotFound");
        return mav;
    }






}
