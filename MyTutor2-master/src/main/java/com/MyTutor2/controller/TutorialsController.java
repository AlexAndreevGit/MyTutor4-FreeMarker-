package com.MyTutor2.controller;


import com.MyTutor2.exceptions.CategoryNotFoundException;
import com.MyTutor2.exceptions.TutorialNotFoundException;
import com.MyTutor2.exceptions.UserNotFoundException;
import com.MyTutor2.model.DTOs.TutorialAddDTO;
import com.MyTutor2.model.DTOs.TutorialEditDTO;
import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.model.enums.CategoryNameEnum;
import com.MyTutor2.service.OpenAIService;
import com.MyTutor2.service.TutorialsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tutorials")   ///tutorials/ask-question
public class TutorialsController {

    private TutorialsService tutorialsService;
    private OpenAIService openAIService;

    public TutorialsController(TutorialsService tutorialsService, OpenAIService openAIService) {
        this.tutorialsService = tutorialsService;
        this.openAIService = openAIService;
    }

//    @GetMapping("/add")
//    public String add() {
//        return "tutorial-add";
//    }

    @GetMapping("/add")
    public String add2(Model model, Authentication auth) {

        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());
        model.addAttribute("categoryEnumValues", CategoryNameEnum.values());

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);

        return "tutorial-addFM";
    }


    //Populating the model with data to be made available to the view before rendering
    @ModelAttribute("tutorialAddDTO")
    public TutorialAddDTO initTutorialAddDTO() {
        return new TutorialAddDTO();
    }


    //@Valid TutorialAddDTO tutorialAddDTO - we make a validation. Validate that the submitted input information fulfill the DTO(@Size(), @NotNull, @Positiv ) validation criteria
    @PostMapping("/add")
    public String createTutorial(@AuthenticationPrincipal UserDetails userDetails,  // source: Spring security
                                 @Valid TutorialAddDTO tutorialAddDTO,              // source: HTTP request
                                 BindingResult bindingResult,                       // source: Spring MVC
                                 Model model) throws UserNotFoundException, CategoryNotFoundException {           // source: Spring MVC

        //BindingResult bindingResult - through bindingResult we can access the result(errors) from the validation
        if (bindingResult.hasErrors()) {

            model.addAttribute("tutorialAddDTO", tutorialAddDTO);
            model.addAttribute("tutorialAddDTO_errors", bindingResult);

            return "tutorial-addFM";

        }

        String userName = userDetails.getUsername();
        tutorialsService.addTutoringOffer(tutorialAddDTO, userName);

        return "redirect:/";
    }

    @PostMapping("/addAfterEdit")
    public String editutorial(@AuthenticationPrincipal UserDetails userDetails,  // source: Spring security
                                 @Valid TutorialEditDTO tutorialEditDTO,              // source: HTTP request
                                 BindingResult bindingResult,                       // source: Spring MVC
                                 Model model) throws UserNotFoundException, CategoryNotFoundException {           // source: Spring MVC

        //BindingResult bindingResult - through bindingResult we can access the result(errors) from the validation
        if (bindingResult.hasErrors()) {

            model.addAttribute("tutorialEditDTO", tutorialEditDTO);
            model.addAttribute("tutorialEditDTO_errors", bindingResult);

            return "tutorial-editFM";

        }

        String userName = userDetails.getUsername();
        tutorialsService.addTutoringOfferAfterEdit(tutorialEditDTO, userName);

        return "redirect:/";
    }

    @PostMapping("/ask-question")
    public ResponseEntity<Map<String, Object>> askQuestion(@RequestBody Map<String, String> payload) { //
        Map<String, Object> response = new HashMap<>();
        String query = payload.get("query");

        String answer = openAIService.askQuestion(query);
        response.put("answer", answer);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ask-question")
    public String askQuestion() {
        return "ask-question";
    }

    @GetMapping("/ask-questionFM")
    public String askQuestion2( Model model, Authentication auth) {

        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);

        return "ask-questionFM";
    }

    @GetMapping("/infoFM")
    public String informaticsOffersFM(@AuthenticationPrincipal UserDetails userDetails, Model model, Authentication auth) {

        if (userDetails == null) {
            return "/";
        }

        List<TutorialViewDTO> informaticsTutorialsAsView = tutorialsService.findAllByCategoryID(2L);

        model.addAttribute("informaticsTutorialsAsView", informaticsTutorialsAsView);
        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);

        return "tutorialsInformaticsFM";

    }

    @GetMapping("/mathFM")
    public String mathematicsOffers(@AuthenticationPrincipal UserDetails userDetails, Model model, Authentication auth) {  //SpringSecurity_8  Use @AuthenticationPrincipal

        if (userDetails == null) {
            return "/";
        }

        List<TutorialViewDTO> mathematicsTutorialsAsView = tutorialsService.findAllByCategoryID(1L);
        model.addAttribute("mathematicsTutorialsAsView", mathematicsTutorialsAsView);
        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);

        return "tutorialsMathematicsFM";

    }

    @GetMapping("/otherFM")
    public String otherOffers(@AuthenticationPrincipal UserDetails userDetails, Model model , Authentication auth) {

        if (userDetails == null) {
            return "/";
        }

        List<TutorialViewDTO> otherTutorialsAsView = tutorialsService.findAllByCategoryID(3L);
        model.addAttribute("otherTutorialsAsView", otherTutorialsAsView);
        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);

        return "tutorialsOtherFM";

    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id) throws TutorialNotFoundException {

        tutorialsService.removeOfferById(id);

        return "redirect:/home";
    }

    //The input is send as a query parameter "http://localhost:8080/tutorials/informaticsFind?searchTerm=JAvas"
    @GetMapping("/informaticsFind")
    public String findInformaticsOffers(@RequestParam("searchTerm") String searchTerm, Model model, Authentication auth) {
        // Suche nach Angeboten basierend auf dem Suchbegriff
        List<TutorialViewDTO> searchResults = tutorialsService.findAllTutoringOffersByWordInTitleAndSubjectID(searchTerm,2L);

        // Ergebnisse dem Model hinzufügen
        model.addAttribute("informaticsTutorialsAsView", searchResults);
        model.addAttribute("searchTerm", searchTerm);

        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);

        // Rückgabe der View
        return "tutorialsInformaticsFM";
    }

    @GetMapping("/mathematicsFind")
    public String findMathematicsOffers(@RequestParam("searchTerm") String searchTerm, Model model, Authentication auth) {
        // Suche nach Angeboten basierend auf dem Suchbegriff
        List<TutorialViewDTO> searchResults = tutorialsService.findAllTutoringOffersByWordInTitleAndSubjectID(searchTerm,1L);

        // Ergebnisse dem Model hinzufügen
        model.addAttribute("mathematicsTutorialsAsView", searchResults);
        model.addAttribute("searchTerm", searchTerm);

        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);

        // Rückgabe der View
        return "tutorialsMathematicsFM";
    }

    @GetMapping("/otherFind")
    public String findOtherOffers(@RequestParam("searchTerm") String searchTerm, Model model, Authentication auth) {
        // Suche nach Angeboten basierend auf dem Suchbegriff
        List<TutorialViewDTO> searchResults = tutorialsService.findAllTutoringOffersByWordInTitleAndSubjectID(searchTerm,3L);

        // Ergebnisse dem Model hinzufügen
        model.addAttribute("otherTutorialsAsView", searchResults);
        model.addAttribute("searchTerm", searchTerm);

        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);

        // Rückgabe der View
        return "tutorialsOtherFM";
    }

    //write a function where i will edint the information in the tutorial. the erquest is send to /tutorials/edit/${w.id}"
    @GetMapping("/edit/{id}")
    public String editTutorial(@PathVariable Long id, Model model, Authentication auth) throws TutorialNotFoundException {

        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        TutorialEditDTO tutorialEditDTO = tutorialsService.findTutorialById(id);

        model.addAttribute("tutorialEditDTO", tutorialEditDTO);
        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());


        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);


        return "tutorial-editFM";
    }


}
