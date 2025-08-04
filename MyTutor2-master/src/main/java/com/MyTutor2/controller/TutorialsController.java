package com.MyTutor2.controller;


import com.MyTutor2.exceptions.CategoryNotFoundException;
import com.MyTutor2.exceptions.TutorialNotFoundException;
import com.MyTutor2.exceptions.UserNotFoundException;
import com.MyTutor2.model.DTOs.TutorialAddDTO;
import com.MyTutor2.model.DTOs.TutorialEditDTO;
import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.model.DTOs.UserDTO;
import com.MyTutor2.model.entity.TutoringOffer;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.model.enums.CategoryNameEnum;
import com.MyTutor2.repo.UserRepository;
import com.MyTutor2.service.OpenAIService;
import com.MyTutor2.service.TutorialsService;
import com.MyTutor2.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tutorials")   ///tutorials/ask-question
public class TutorialsController {

    private TutorialsService tutorialsService;
    private OpenAIService openAIService;
    private ModelMapper modelMapper;
    private UserRepository userRepository; // TODO delete, not used
    private UserService userService; // TODO delete, not used

    public TutorialsController(TutorialsService tutorialsService, OpenAIService openAIService,ModelMapper modelMapper,UserRepository userRepository, UserService userService) {
        this.tutorialsService = tutorialsService;
        this.openAIService = openAIService;
        this.modelMapper = modelMapper;            // TODO delete
        this.userRepository = userRepository;      // TODO delete, not used
        this.userService = userService;
    }

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

        return "redirect:/users/my-informationFM";
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

    @GetMapping("/removeFromMyOffers/{id}")
    public String remove(@PathVariable Long id) throws TutorialNotFoundException {

        List<User> allUsers = userService.findAllUsers();

        for(User user : allUsers) {  //Iterate through all users to find the one who has this offer in his favorite offers
            List<TutoringOffer> favoriteOffers = user.getFavoriteTutoringOffers();
            for(TutoringOffer offer : favoriteOffers) {
                if(offer.getId().equals(id)) {  //If the offer is found in the user's favorite offers
                    favoriteOffers.remove(offer);  //Remove it from the user's favorite offers
                    user.setFavoriteTutoringOffers(favoriteOffers);
                    userRepository.save(user);  // Save the updated user back to the database
                    break;  // Exit the loop once the offer is removed
                }
            }

        }

        tutorialsService.removeOfferById(id);

        return "redirect:/home";
    }



    @GetMapping("/removeOfferFromFavorite/{id}")
    public String removeOfferFromFavorite(@PathVariable Long id,
                                          @AuthenticationPrincipal UserDetails userDetails,
                                          @RequestHeader(value = "Referer",required = false) String referer) throws TutorialNotFoundException {

        User user = userService.findUserByUsername(userDetails.getUsername());

        List<TutoringOffer> listOfFavoriteOffers = user.getFavoriteTutoringOffers();

        for(TutoringOffer offer : listOfFavoriteOffers) {  //Iterate through the list of favorite offers to find the one to remove
            if(offer.getId().equals(id)) {
                // If the offer is found, remove it from the list
                listOfFavoriteOffers.remove(offer);
                user.setFavoriteTutoringOffers(listOfFavoriteOffers);
                userRepository.save(user);  // Save the updated user back to the database
                break;  // Exit the loop once the offer is removed
            }

        }

        return "redirect:" + (referer != null ? referer : "/") ;
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

        TutorialEditDTO tutorialEditDTO = tutorialsService.findTutorialByIdForEdit(id);

        model.addAttribute("tutorialEditDTO", tutorialEditDTO);
        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());


        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);


        return "tutorial-editFM";
    }

    //a method getting /sortAlphabetically
    @GetMapping("/sortAlphabetically")
    public String sortAlphabetically(@RequestParam("categoryId") Long categoryId,String typeForSort, Model model, Authentication auth) {

        List<TutorialViewDTO> Offers = tutorialsService.findAllByCategoryID(categoryId);
        List<TutorialViewDTO> sortedOffers = new ArrayList<>();

        if(typeForSort.equals("alphabetical")){
            sortedOffers = tutorialsService.sortAlphabetically(Offers);
        }else if (typeForSort.equals("date")){
            sortedOffers = tutorialsService.sortByDate(Offers);
        }

        model.addAttribute("informaticsTutorialsAsView", sortedOffers);
        model.addAttribute("isAuthenticated", auth != null && auth.isAuthenticated());

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("hasRole_Admin", isAdmin);

        return "tutorialsInformaticsFM";
    }


    @GetMapping("/addToFavorite/{id}")
    public String addToFavorite(@PathVariable Long id,
                                @AuthenticationPrincipal UserDetails userDetails,
                                @RequestHeader(value = "Referer", required = false) String referer) // to get the previous page URL
            throws TutorialNotFoundException {

        User user = userService.findUserByUsername(userDetails.getUsername());

        List<TutoringOffer> listOfFavoriteOffers = user.getFavoriteTutoringOffers();

        TutoringOffer tutoringOfferToBeAddedToFavorite = tutorialsService.findTutoringOfferByID(id);

        //iterate through the listOfFavoriteOffers and check if the tutoringOfferToBeAddedToFavorite is already in the list
        for (TutoringOffer offer : listOfFavoriteOffers) {
            if (offer.getId().equals(tutoringOfferToBeAddedToFavorite.getId())) {

                // If the offer is already in the favorites, return without adding it again
                return "redirect:" + (referer != null ? referer : "/");
            }
        }

        listOfFavoriteOffers.add(tutoringOfferToBeAddedToFavorite);

        user.setFavoriteTutoringOffers(listOfFavoriteOffers);

        // Here you would typically save the user back to the database
        userRepository.save(user);
        return "redirect:" + (referer != null ? referer : "/");  //Return to the previous page or home page if referer is null
    }

}
