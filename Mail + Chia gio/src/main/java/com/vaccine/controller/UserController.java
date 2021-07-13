package com.vaccine.controller;


import com.vaccine.model.AdminDestination;
import com.vaccine.model.User;
import com.vaccine.service.admindestination.IAdminDestinationService;
import com.vaccine.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping(value = "/vaccine", produces = "application/x-www-form-urlencoded;charset=UTF-8")
public class UserController {
    static int countTime = 0;
    static int oneDayDone = 0;

    static int setPeoplePerHour = 3;
    static int setToChangeDay = setPeoplePerHour*4;

    @Autowired
    IUserService userService;

    @Autowired
    MailSender mailSender;

    @Autowired
    IAdminDestinationService adminDestinationService;

    @ModelAttribute("destinations")
    public List<AdminDestination> destinations(){
        return adminDestinationService.findAll();
    }

    @GetMapping("/db")
    public ModelAndView showdb(){
        ModelAndView modelAndView = new ModelAndView("/victory/dashBoar");
        return modelAndView;
    }


    public void sendEmail(String from,String to, String subject,String content){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailSender.send(mailMessage);
    }



    @GetMapping("/form")
    public ModelAndView showForm() {
        ModelAndView modelAndView = new ModelAndView("/form");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("/home");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createUser(User user) {
        user.setAge(java.time.LocalDate.now().getYear() - user.getAge());

        user.setStatus(setStatus(user));
//      get currently date
        LocalDateTime a = LocalDateTime.now();
        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("dd-MM-yyyy ");
        String formattedDate = a.format(formatterDay);
        user.setCreateDay(formattedDate);

        String str = userService.getMaxDayFromData() + userService.getMaxTimeFromData();
        int countMaxTime = userService.countMaxTimeInDay();
        System.out.println("max day is: " + countMaxTime);
        int countMaxDay = userService.countMaxDayToNext();
        System.out.println("to change day is: " + countMaxDay);
        System.out.println("Day is: "+userService.getMaxDayFromData());

        System.out.println("Mã day: "+userService.getMaxDayFromData());
        System.out.println("Time max day: "+userService.getMaxTimeFromData());


        setDayTimeVaccine(user, str, countMaxTime, countMaxDay);

        userService.save(user);
//        if(user.getEmail()!=null){
//            sendEmail("phuockhanh1010@gmail.com",user.getEmail(),"Hello","Xin chao"+user.getUserName());
//        }

        ModelAndView modelAndView = new ModelAndView("/form");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    public int setStatus(User user) {
//        1,2 ok
        int status;

        if (user.getHealthyStatus() != null) {
            String[] arr = user.getHealthyStatus().split(",");
            if (arr.length > 1 || user.getAge() < 16 || user.getAge() > 65) {
                status = 3;
            } else if (arr.length == 1 && user.getAge() >= 16 && user.getAge() <= 65) {
                status = 1;
            } else {
                status = 2;
            }
        } else {
            if (user.getAge() >= 16 && user.getAge()<=65) {
                status = 2;
            } else {
                status = 3;
            }
        }
        return status;
    }

    public static void setDayTimeVaccine(User user, String str, Integer countMaxTime, int coutMaxDay) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        if (str.equals("nullnull")) {
            str = "01-08-2021 08:00";
        }
        LocalDateTime currentDateTime = LocalDateTime.parse(str, formatter);
//      Divide date to time
        DateTimeFormatter formatterDay = DateTimeFormatter.ofPattern("dd-MM-yyyy ");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");

//        Set day start is 1/8/2021
//        Set 1 day with 8h,14h
//        Set day end is 31/8/2021

        if (countMaxTime != null ) {
            countTime = countMaxTime;
        }
        if (countMaxTime != null && countMaxTime > setPeoplePerHour) {
              countTime = setPeoplePerHour;
        }

        if (coutMaxDay == setToChangeDay) {
            oneDayDone = setToChangeDay;
        }

//        Số mũi tiêm trong một ngày, cứ thế nhân lên
        if (oneDayDone == setToChangeDay) {
            currentDateTime = currentDateTime.plusDays(1);
            currentDateTime = currentDateTime.minusHours(8);
            oneDayDone = 0;
            countTime = 0;
        }

//  Mấy tk một giờ, ở đây là 2, 2 tk tăng lên 6h
        if (countTime == setPeoplePerHour) {
            currentDateTime = currentDateTime.plusHours(2);
            if (currentDateTime.getHour() == 12) {
                currentDateTime = currentDateTime.plusHours(2);
            }
            countTime = 0;
//  Set giá trị cho từng tk
        }
        while (countTime < setPeoplePerHour) {
            String formattedDate = currentDateTime.format(formatterDay);
            String formattedTime = currentDateTime.format(formatterTime);
            user.setTimeVaccine(formattedTime);
            user.setDateVaccine(formattedDate);
            countTime++;


            oneDayDone++;
            return;
        }
    }

    @GetMapping("/user")
    public ModelAndView listUsers(){
        List<User> userList = userService.findAll();
        ModelAndView modelAndView = new ModelAndView("/victory/user");
        modelAndView.addObject("user",userList);
        return modelAndView;
    }

    @GetMapping("/delete-user/{id}")
    public ModelAndView deleteUsers(@PathVariable("id") Long id){
        userService.remove(id);
        ModelAndView modelAndView = new ModelAndView("/victory/user");
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

}
