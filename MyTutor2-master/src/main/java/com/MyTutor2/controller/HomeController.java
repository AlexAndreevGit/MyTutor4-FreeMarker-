package com.MyTutor2.controller;

import com.MyTutor2.model.entity.TutoringOffer;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.repo.TutoringRepository;
import com.MyTutor2.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {


    private TutoringRepository tutoringRepository;
    private UserRepository userRepository;

    //constructor injection
    public HomeController(TutoringRepository tutoringRepository, UserRepository userRepository) {
        this.tutoringRepository = tutoringRepository;
        this.userRepository = userRepository;
    }


    @GetMapping(value={"/home", "/"})
    public String home2(Model model, Authentication auth) {

        List<TutoringOffer> countInformaticsTutorials = tutoringRepository.findAllByCategoryId(2L);

        List<TutoringOffer> countMathematicsTutorials = tutoringRepository.findAllByCategoryId(1L);

        List<TutoringOffer> countOtherTutorials = tutoringRepository.findAllByCategoryId(3L);

        List<User> countAllUsers= userRepository.findAll();


        model.addAttribute("countInformaticsTutorials", countInformaticsTutorials.size());

        model.addAttribute("countMathematicsTutorials", countMathematicsTutorials.size());

        model.addAttribute("countOtherTutorials", countOtherTutorials.size());

        model.addAttribute("countAllUsers", countAllUsers.size()-1);

        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);

        return "home";
    }

    @GetMapping("/about-us")
    public String aboutUs2(Model model, Authentication auth){

        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);

        return "about-us";
    }

}
