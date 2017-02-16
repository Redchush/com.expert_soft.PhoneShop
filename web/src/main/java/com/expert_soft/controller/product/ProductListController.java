package com.expert_soft.controller.product;

import com.expert_soft.model.Phone;
import com.expert_soft.service.PhoneService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.expert_soft.controller.ServletConstants.PHONE_LIST;

@Controller("productListController")
public class ProductListController {

    private static final Logger logger = Logger.getLogger(ProductListController.class);

    private PhoneService service;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ModelAndView products(){
        List<Phone> all = service.findAll();
        return new ModelAndView("productList", PHONE_LIST, all);
    }

    @Autowired
    public void setService(PhoneService service) {
        this.service = service;
    }

}
