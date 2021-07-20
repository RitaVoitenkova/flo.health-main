package by.tms.controller;

import by.tms.dto.UserAuthDto;
import by.tms.dto.UserRegistrationDto;
import by.tms.repository.UserRepository;
import by.tms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/home")
public class RegAndAuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @GetMapping(path = "/reg")
    public ModelAndView getRegistrationPage(ModelAndView modelAndView){
        modelAndView.addObject("userReg", new UserRegistrationDto());
        modelAndView.setViewName("reg");
        return modelAndView;
    }
    @PostMapping(path = "/reg")
    public ModelAndView postRegistrationUser(@ModelAttribute("userReg")@Valid UserRegistrationDto userRegDTO, BindingResult bindingResult, ModelAndView modelAndView ){
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("reg");
        } else {
            userService.registrationUser(userRegDTO);
            modelAndView.setViewName("hello");
        }
        return  modelAndView;
    }
    @GetMapping(path = "/auth")
    public ModelAndView getAuthorizationPage(ModelAndView modelAndView){
        modelAndView.setViewName("auth");
        return modelAndView;
    }

    @GetMapping(path = "/recover")
    public ModelAndView recoverPage(ModelAndView modelAndView){
        modelAndView.addObject("auth", new UserAuthDto());
        modelAndView.setViewName("recover");
        return modelAndView;
    }
    @PostMapping(path = "/recover" )
    public ModelAndView recoverUser(@ModelAttribute("auth") UserAuthDto userAuthDTO, ModelAndView modelAndView){
        userService.setUserNotDeleted(userAuthDTO.getUsername());
        modelAndView.setViewName("auth");
        return modelAndView;
    }
}
