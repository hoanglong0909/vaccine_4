package com.vaccine.controller;


import com.vaccine.model.User;
import com.vaccine.model.WarehouseVaccine;
import com.vaccine.repository.IWarehouseRepository;
import com.vaccine.service.admindestination.IAdminDestinationService;
import com.vaccine.service.user.IUserService;
import com.vaccine.service.warehouseVaccine.IWarehouseVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/admin", produces = "application/x-www-form-urlencoded;charset=UTF-8")
public class AdminController {
    int count=0;

    @Autowired
    IUserService userService;

    @Autowired
    IAdminDestinationService adminDestinationService;

    @Autowired
    IWarehouseVaccineService warehouseVaccineService;


    @GetMapping
    public ModelAndView showForm() {
        ModelAndView modelAndView = new ModelAndView("/admin/ListVaccine");
        List<User> userList = userService.getUserListIsDone("01-08-2021 ");
        modelAndView.addObject("userList", userList);
        return modelAndView;
    }
    @GetMapping("/check/{id}")
    public ModelAndView setCheck(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/admin/ListVaccine");
        User user = userService.findById(id).get();
        user.setCheckStatus(1);
        userService.save(user);
        count++;
        System.out.println(count);
        return showForm();
    }

    @GetMapping("/unchecked/{id}")
    public ModelAndView setUnchecked(@PathVariable Long id){
        User user = userService.findById(id).get();
        user.setCheckStatus(0);
        userService.save(user);
        count--;
        System.out.println(count);
        return showForm();
    }


    @GetMapping("/setAll")
    public ModelAndView minusVaccines(){
        WarehouseVaccine warehouseVaccine = warehouseVaccineService.findById(1L).get();
        warehouseVaccine.setAmountVaccine(warehouseVaccine.getAmountVaccine()-count);
        warehouseVaccineService.save(warehouseVaccine);
        count=0;
        return showForm();
    }





}

