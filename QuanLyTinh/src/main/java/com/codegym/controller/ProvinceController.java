package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import com.codegym.service.CustomerService;
import com.codegym.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.rmi.MarshalledObject;

@Controller
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private CustomerService customerService;



    @GetMapping("/show-province")
    public ModelAndView listProvinces() {
        Iterable<Province> provinces = provinceService.findAll();
        ModelAndView modelAndView = new ModelAndView("/province/list");
        modelAndView.addObject("provinces", provinces);
        return modelAndView;
    }

    @GetMapping("/show-createProvince")
    public String showCreate(Model model) {
        model.addAttribute("province", new Province());
        return "/province/create";
    }

    @PostMapping("/create-province")
    public ModelAndView saveProvince(@ModelAttribute Province province) {
        provinceService.save(province);

        ModelAndView modelAndView = new ModelAndView("/province/create");
        modelAndView.addObject("province", new Province());
        modelAndView.addObject("message", "New province created successfully");
        return modelAndView;
    }

    @GetMapping("/view-province/{id}")
    public ModelAndView showView(@PathVariable Long id) {
        Province province = provinceService.findById(id);
        if (province!=null){
            Iterable<Customer> customers=customerService.findAllByProvince(province);
            ModelAndView modelAndView = new ModelAndView("/province/view");
            modelAndView.addObject("provinces", province);
            modelAndView.addObject("customer",customers);
            return modelAndView;
        }

       return new ModelAndView("/province/error");
    }

    @GetMapping("/delete-showProvince/{id}")
    public ModelAndView showDelete(@PathVariable Long id) {
        Province province = provinceService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/province/delete");
        modelAndView.addObject("province", province);
        return modelAndView;
    }

    @PostMapping("/delete-province")
    public ModelAndView deleteProvince(@ModelAttribute Province province) {
        Long id = province.getId();
        if (id != null) {
            provinceService.remove(id);
            ModelAndView modelAndView = new ModelAndView("/province/delete");
            modelAndView.addObject("province", new Province());
            modelAndView.addObject("message", "Xoa thanh cong");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/province/delete");
            modelAndView.addObject("message1", "Xoa Loi");
            return modelAndView;
        }
    }

    @GetMapping("/edit-showProvince/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {
        Province province1 = provinceService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/province/edit");
        modelAndView.addObject("province", province1);
        return modelAndView;
    }

    @PostMapping("/edit-province")
    public ModelAndView editProvince(@ModelAttribute Province province) {
        if (province != null) {
            provinceService.save(province);
            ModelAndView modelAndView = new ModelAndView("/province/edit");
            modelAndView.addObject("province", province);
            modelAndView.addObject("message", "Sua thanh cong");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/province/edit");
            modelAndView.addObject("message1", "Sua loi");
            return modelAndView;
        }
    }
}
