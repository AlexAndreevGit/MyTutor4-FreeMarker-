package com.MyTutor2.controller;


import com.MyTutor2.service.impl.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/email")
public class EmailController {

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam String recipientEmail,
                            @RequestParam String message,
                            HttpServletRequest request){


//        EmailService emailService = new EmailService();

//        emailService.sendSimpleMessage(recipientEmail, "Tutoring Inquiry", message);

        return "redirect:" + request.getHeader("Referer");
    }


}
