package com.expert_soft.controller;


import com.expert_soft.model.Phone;
import com.expert_soft.service.PhoneService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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


}
