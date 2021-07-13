package com.vaccine.controller;


import com.vaccine.model.User;
import com.vaccine.model.WarehouseVaccine;
import com.vaccine.repository.IWarehouseRepository;
import com.vaccine.service.admindestination.IAdminDestinationService;
import com.vaccine.service.user.IUserService;
import com.vaccine.service.warehouseVaccine.IWarehouseVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

//    <------------------------dashboard------------------------------------>

    @GetMapping("/db")
    public ModelAndView showdb(){
        ModelAndView modelAndView = new ModelAndView("/victory/dashBoar");
        return modelAndView;
    }


    @GetMapping("/user")
    public ModelAndView listUsers(){
        List<User> userList = userService.findAll();
        ModelAndView modelAndView = new ModelAndView("/victory/user");
        modelAndView.addObject("user",userList);
        return modelAndView;
    }


    @GetMapping("/delete-user/{id}")
    public ModelAndView deleteUsers(@PathVariable("id")Long id){
        userService.remove(id);
        List<User> userList = userService.findAll();
        ModelAndView modelAndView = new ModelAndView("/victory/user");
        modelAndView.addObject("user",userList);
        return modelAndView;
    }

    @GetMapping("/edit-user/{id}")
    public ModelAndView showEdit(@PathVariable("id") Long id){
        User user = userService.findById(id).get();
        ModelAndView modelAndView = new ModelAndView("/editForm");
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @PostMapping("/edit-user")
    public ModelAndView editUser(@ModelAttribute("user") User user){
        userService.save(user);
        ModelAndView modelAndView = new ModelAndView("/editForm");
        modelAndView.addObject("user",new User());
        return modelAndView;
    }

//    ---------------------------------User------------------------------------------>


    @GetMapping("/hospital")
    public ModelAndView listHosp(){
        List<User> userList = userService.findAll();
        ModelAndView modelAndView = new ModelAndView("/victory/hospital");
        modelAndView.addObject("user",userList);
        return modelAndView;
    }

    @GetMapping("/warehouse")
    public ModelAndView listWareH(){
       List<WarehouseVaccine> warehouseVaccineList = warehouseVaccineService.findAll();
       ModelAndView modelAndView = new ModelAndView("/victory/warehousevaccine");
       modelAndView.addObject("warehousevaccine",warehouseVaccineList);
       return modelAndView;
    }





}

