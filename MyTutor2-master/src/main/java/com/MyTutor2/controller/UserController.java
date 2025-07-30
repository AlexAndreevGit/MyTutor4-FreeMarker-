package com.MyTutor2.controller;

import com.MyTutor2.exceptions.TutorialNotFoundException;
import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.model.DTOs.UserLogInDTO;
import com.MyTutor2.model.DTOs.UserRegisterDTO;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.repo.UserRepository;
import com.MyTutor2.service.ExRateService;
import com.MyTutor2.service.TutorialsService;
import com.MyTutor2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;
    private TutorialsService tutorialsService;
    private ExRateService exRateService;

    public UserController(UserService userService, UserRepository userRepository, TutorialsService tutorialsService, ExRateService exRateService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.tutorialsService = tutorialsService;
        this.exRateService = exRateService;
    }

    @ModelAttribute("userRegisterDTO")
    public UserRegisterDTO initUserRegisterDTO(){
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/registerFM")
    public String registerFM(){
        return "registerFM";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterDTO userRegisterDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  Model model){

        if(bindingResult.hasErrors() || !userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())){

            redirectAttributes.addFlashAttribute("userRegisterDTO",userRegisterDTO);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO",bindingResult);

//            model.addAttribute("tutorialAddDTO", tutorialAddDTO);
            model.addAttribute("userRegisterDTO_errors", bindingResult);

//            return "redirect:register";
            return "registerFM";
        }

        userService.registerUser(userRegisterDTO);
        return "redirect:login";
    }

    @ModelAttribute("userLogInDTO")
    public UserLogInDTO initUserLogInDTO(){
        return new UserLogInDTO();
    }

    @GetMapping("/login")
    public String login(Model model){

        UserLogInDTO userLogInDTO=new UserLogInDTO();
        userLogInDTO.setUsername("user1");
        userLogInDTO.setPassword("12345");

        model.addAttribute("userLogInDTO",userLogInDTO);


        return "login";
    }

    @GetMapping("/loginFM")
    public String loginFM(Model model){

        UserLogInDTO userLogInDTO=new UserLogInDTO();
        userLogInDTO.setUsername("user1");
        userLogInDTO.setPassword("12345");

        model.addAttribute("userLogInDTO",userLogInDTO);


        return "loginFM";
    }


    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);

        return "login";
    }

    @RequestMapping(value = "/my-informationFM", method = RequestMethod.GET)
    public String myInformation(@AuthenticationPrincipal UserDetails userDetails, Model model, Authentication auth){

        User logedInUser= userRepository.findByUsername(userDetails.getUsername()).orElse(null);

        List<TutorialViewDTO> submittedByMeTutorialsAsView = tutorialsService.findAllTutoringOffersByUserId(logedInUser.getId());

        double averagePriceEU = submittedByMeTutorialsAsView.stream()
                .mapToDouble(TutorialViewDTO::getPrice)
                .average()
                .orElse(0.0);

        BigDecimal averagePriceBGN = BigDecimal.ZERO;

        try{
            averagePriceBGN = exRateService.convert("EUR","BGN",BigDecimal.valueOf(averagePriceEU));
        }catch(Exception e){
            System.out.println("It is not possible to calculate the average price.");  //TODO throw my exception ?
        }

        model.addAttribute("userName",logedInUser.getName());
        model.addAttribute("userEmail",logedInUser.getEmail());
        model.addAttribute("numberOfClasses",submittedByMeTutorialsAsView.size());
        model.addAttribute("submittedByMeTutorialsAsView",submittedByMeTutorialsAsView);

        DecimalFormat df = new DecimalFormat("#.00");

        model.addAttribute("averagePriceEUR", df.format(averagePriceEU));
        model.addAttribute("averagePriceBGN", df.format(averagePriceBGN));

        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);


        return "my-informationFM";

    }

    @PostMapping("/delete-account")
    public String deleteAccount(@AuthenticationPrincipal UserDetails userDetails,
                                HttpServletRequest request,    //is used to get the current user's request
                                HttpServletResponse response) throws TutorialNotFoundException {  //is used to get the current user's response

        User logedInUser= userRepository.findByUsername(userDetails.getUsername()).orElse(null);

        userService.deleteUser(logedInUser);

        //--- logout ---

        //get the current user's authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //securityContextLogoutHandler is used to log out the current user
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();  //

        if (authentication != null) {
            securityContextLogoutHandler.logout(request, response, authentication);
        }

        return "redirect:/";
    }


}
