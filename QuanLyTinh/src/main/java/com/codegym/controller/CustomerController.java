package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import com.codegym.service.CustomerService;
import com.codegym.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces(){
        return provinceService.findAll();
    }

    @GetMapping("/showform-customer")
    public ModelAndView showForm(){
        Iterable<Customer> customers=customerService.findAll();
        ModelAndView modelAndView=new ModelAndView("/customer/showlist");
        modelAndView.addObject("customers",customers);
        return modelAndView;
    }

    @GetMapping("/show-create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }
    @PostMapping("/create-customer")
    public ModelAndView createForm(@ModelAttribute Customer customer){
        customerService.save(customer);
        ModelAndView modelAndView =new ModelAndView("/customer/create");

        modelAndView.addObject("addCustomer",new Customer());
        modelAndView.addObject("addMessage","add new successfully customer");
        return modelAndView;
    }

    @GetMapping("/detail-customer/{id}")
    public ModelAndView detailCustomer(@PathVariable Long id){
        Customer customer=null;
        if (id!=null){
            customer=customerService.findById(id);
        }
        ModelAndView modelAndView=new ModelAndView("/customer/detail");
        modelAndView.addObject("customer",customer);
        return modelAndView;
    }

    @GetMapping("/show-deleteCustomer/{id}")
    public ModelAndView showDelete(@PathVariable Long id){
     ModelAndView modelAndView=new ModelAndView("/customer/delete");
     modelAndView.addObject("customer",customerService.findById(id));
     return modelAndView;
    }

    @PostMapping("/delete-customer")
    public ModelAndView deleteCustomer(@ModelAttribute Customer customer){
        Long id=customer.getId();
        if (id!=null){
            customerService.remove(id);
            ModelAndView modelAndView=new ModelAndView("/customer/delete");
            modelAndView.addObject("message","Xoa thanh cong");
            return modelAndView;
        }else {
            ModelAndView modelAndView=new ModelAndView("/customer/error-404");
            return modelAndView;
        }
    }

    @GetMapping("/edit-showCustomer/{id}")
    public ModelAndView showEdit(@PathVariable Long id){
        ModelAndView modelAndView=new ModelAndView("/customer/edit");
        modelAndView.addObject("customer",customerService.findById(id));
        return modelAndView;
    }
    @PostMapping("/edit-customer")
    public ModelAndView editCustomer(@ModelAttribute Customer customer){
        if (customer!=null){
            customerService.save(customer);
            ModelAndView modelAndView=new ModelAndView("/customer/edit");
            modelAndView.addObject("customer",customer);
            modelAndView.addObject("message","Sua thanh cong");
            return modelAndView;
        }else {
            ModelAndView modelAndView=new ModelAndView("/customer/edit");
            modelAndView.addObject("message1","Sua that bai");
            return modelAndView;
        }
    }
}
