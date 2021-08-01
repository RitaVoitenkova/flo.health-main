package com.example.health.controller;

import com.example.health.dto.UserAuthDto;
import com.example.health.dto.UserRegDto;
import com.example.health.entity.Role;
import com.example.health.entity.Status;
import com.example.health.entity.User;
import com.example.health.service.UserService;
import com.example.health.service.exception.EmailIsBusyServiceException;
import com.example.health.service.exception.UsernameIsBusyServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //logout в Security уже создан в методе POST

    @GetMapping("/login")
    public ModelAndView auth(){
        return new ModelAndView("auth");
    }

    //не работает
//    @GetMapping("/login")
//    public ModelAndView authenticationView() {
//        return new ModelAndView("auth", "userModel", new UserAuthDto());
//            }


//    @GetMapping("/password-recovery")
//    public ModelAndView passwordRecoveryView() {
//        return new ModelAndView("auth", "userModel", new UserAuthDto());
//    }

    //@PoseMapping login уже готов у секюрити

    @GetMapping("/reg")
    public ModelAndView registration(Model model){
        model.addAttribute("userRegDto", new UserRegDto());
        return new ModelAndView("reg");
    }

    @PostMapping("/reg")
    public ModelAndView saveUserReg(UserRegDto userRegDto, Model model) {
//        if(userService.findUserByUserName(userRegDto.getNameRegDTO().is)){
//            model.addAttribute("message", "this username is already occupied");
//            return new ModelAndView("reg");
//        }
        try {
            userService.registration(new UserRegDto(userRegDto.getNameRegDTO(),userRegDto.getPasswordRegDTO(), userRegDto.getEmailRegDTO()));
        } catch (UsernameIsBusyServiceException e) {
            e.printStackTrace();
        } catch (EmailIsBusyServiceException e) {
            e.printStackTrace();
        }
//        userService.saveUser(new User(userRegDto.getNameRegDTO(), userRegDto.getPasswordRegDTO(), userRegDto.getEmailRegDTO()));
        model.addAttribute("message", "User created successfully");
        return new ModelAndView("reg");
    }

    @PostMapping("registration")
    public ModelAndView registrationHandler(@Valid @ModelAttribute("userModel") UserRegDto user, BindingResult result) {
        if(result.hasErrors()) {
            return new ModelAndView("registration", result.getModel());
        }

        try {
            userService.registration(user);
            return new ModelAndView("redirect:/user/reg");
        } catch (UsernameIsBusyServiceException e) {
            ModelAndView model = new ModelAndView("reg", result.getModel());
            model.addObject("message", "Username is busy");
            return model;
        } catch (EmailIsBusyServiceException e) {
            ModelAndView model = new ModelAndView("reg", result.getModel());
            model.addObject("message", "Email is busy");
            return model;
        }
    }
//        @PostMapping("/reg")
//    public ModelAndView saveUserReg(UserRegDto user) throws UsernameIsBusyServiceException, EmailIsBusyServiceException {
//        userService.registration(user);
//        return new ModelAndView("login");
//        try {
//            userService.saveUser(user);
//            return new ModelAndView("auth");
//        } catch (RuntimeException e) {
//            model.addAttribute("message", e.getMessage());
//            return new ModelAndView("testRegPage");
//        }
//    }
}

