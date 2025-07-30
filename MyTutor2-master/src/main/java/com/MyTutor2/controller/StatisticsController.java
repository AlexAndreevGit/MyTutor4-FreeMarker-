package com.MyTutor2.controller;

import com.MyTutor2.model.entity.User;
import com.MyTutor2.repo.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class StatisticsController {

    private UserRepository userRepository;

    public StatisticsController( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @PreAuthorize("hasRole('ADMIN')") // SpringSecurity_12 Only users with the ADMIN role can access this method
//    @GetMapping("/statistics")
//    public String statistics(Model model) {
//
//        List<User> countAllUsers= userRepository.findAll();
//
//        model.addAttribute("countAllUsers", countAllUsers.size()-1);
//
//        return "statistics";
//    }

    @PreAuthorize("hasRole('ADMIN')") // SpringSecurity_12 Only users with the ADMIN role can access this method
    @GetMapping("/statisticsFM")
    public String statistics(Model model, Authentication auth) {

        List<User> countAllUsers= userRepository.findAll();

        model.addAttribute("countAllUsers", countAllUsers.size()-1);

        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);

        return "statisticsFM";
    }

}
