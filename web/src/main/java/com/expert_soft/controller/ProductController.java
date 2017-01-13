package com.expert_soft.controller;


import com.expert_soft.model.Phone;
import com.expert_soft.service.PhoneService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class);


    private PhoneService service;

    @Autowired
    public void setService(PhoneService service) {
        this.service = service;
    }

    @GetMapping(value = "/products")
    public ModelAndView products(){
        logger.debug("logger enabled!!");
        List<Phone> all = service.findAll();
        return new ModelAndView("productList", "phones", all);
    }
//
//    @GetMapping(value = "/product")
//    public ModelAndView product(@RequestParam("id") Long id){
//        Phone phone = service.getPhone(id);
//        return new ModelAndView("productDetails", "phone", phone);
//    }

    @GetMapping(value = "/product/{id}")
    public ModelAndView product(@PathVariable("id") Long id){
        Phone phone = service.getPhone(id);
        return new ModelAndView("productDetails", "phone", phone);
    }

//    @GetMapping(value = "/product")
//    public ModelAndView product(){
//        Phone phone = service.getPhone(id);
//        return new ModelAndView("productDetails", "phone", phone);
//    }
}
