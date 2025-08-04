package com.MyTutor2.service.impl;

import com.MyTutor2.exceptions.CategoryNotFoundException;
import com.MyTutor2.exceptions.TutorialNotFoundException;
import com.MyTutor2.exceptions.UserNotFoundException;
import com.MyTutor2.model.DTOs.TutorialAddDTO;
import com.MyTutor2.model.DTOs.TutorialEditDTO;
import com.MyTutor2.model.DTOs.TutorialViewDTO;
import com.MyTutor2.model.entity.Category;
import com.MyTutor2.model.entity.TutoringOffer;
import com.MyTutor2.model.entity.User;
import com.MyTutor2.repo.CategoryRepository;
import com.MyTutor2.repo.TutoringRepository;
import com.MyTutor2.repo.UserRepository;
import com.MyTutor2.service.TutorialsService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TutorialsServiceImpl implements TutorialsService {

    private TutoringRepository tutoringRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private final RestClient restClient;

    private final Logger LOGGER = LoggerFactory.getLogger(ExRateServiceImpl.class);  //initialise a logger to log messages

    public TutorialsServiceImpl(TutoringRepository tutoringRepository, ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository, RestClient.Builder restClientBuilder) {
        this.tutoringRepository = tutoringRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.restClient = restClientBuilder.build();
    }

    @Override
    public List<TutorialViewDTO> findAllByCategoryID(Long id) {

        List<TutoringOffer> listOfOffers = tutoringRepository.findAllByCategoryId(id);

        return returnListOfOffersAsViewDTO(listOfOffers);
    }

    @Override
    public List<TutorialViewDTO> findAllTutoringOffersByUserId(Long id) {

        List<TutoringOffer> tutoringOffersLogedInUser = tutoringRepository.findAllByAddedById(id);

        List<TutorialViewDTO> listOfViewOffers = tutoringOffersLogedInUser.stream()
                .map(currentOffer -> {
                    TutorialViewDTO tutorialViewDTO = modelMapper.map(currentOffer, TutorialViewDTO.class);
                    tutorialViewDTO.setEmailOfTheTutor(currentOffer.getAddedBy().getEmail());
                    return tutorialViewDTO;

                }).toList();

        return listOfViewOffers;
    }

    @Override
    public List<TutorialViewDTO> findAllTutoringOffersByWordInTitleAndSubjectID(String word, Long subjectId) {

        List<TutoringOffer> listOfOffers = tutoringRepository.findAllByCategoryId(subjectId).stream()
                .filter(offer -> offer.getName().toLowerCase().contains(word.toLowerCase()))
                .collect(Collectors.toList());

        if (listOfOffers.isEmpty()) {
            LOGGER.warn("No tutoring offers found with the word: {}", word);
        } else {
            LOGGER.info("Found {} tutoring offers with the word: {}", listOfOffers.size(), word);
        }

        return returnListOfOffersAsViewDTO(listOfOffers);

    }


    @Override
    public void addTutoringOffer(TutorialAddDTO tutorialAddDTO, String userName) throws CategoryNotFoundException,UserNotFoundException {

        TutoringOffer tutoringOffer = modelMapper.map(tutorialAddDTO, TutoringOffer.class);

        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException(userName));

        tutoringOffer.setAddedBy(user);

        Category category = categoryRepository.findByName(tutorialAddDTO.getCategory());

        if (category == null) {
            throw new CategoryNotFoundException(tutorialAddDTO.getCategory());
        }

        tutoringOffer.setCategory(category);

        tutoringOffer.setCreatedOn(java.time.LocalDate.now());

        tutoringRepository.save(tutoringOffer);

        LOGGER.info("A new tutoring offer was added by {}.", userName);

    }

    @Override
    public void addTutoringOfferAfterEdit(TutorialEditDTO tutorialEditDTO, String userName) throws CategoryNotFoundException,UserNotFoundException {

        TutoringOffer tutoringOffer = modelMapper.map(tutorialEditDTO, TutoringOffer.class);

        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException(userName));

        tutoringOffer.setAddedBy(user);

        Category category = categoryRepository.findByName(tutorialEditDTO.getCategory());

        if (category == null) {
            throw new CategoryNotFoundException(tutorialEditDTO.getCategory());
        }

        tutoringOffer.setCategory(category);
        tutoringOffer.setCreatedOn(java.time.LocalDate.now());

        tutoringRepository.save(tutoringOffer);

        LOGGER.info("A new tutoring offer was added by {}.", userName);

    }

    @Override
    public void removeOfferById(Long id) throws TutorialNotFoundException{

        if (!tutoringRepository.existsById(id)) {
            throw new TutorialNotFoundException(id);
        }

        tutoringRepository.deleteById(id);
    }

    @Override
    public TutorialEditDTO findTutorialByIdForEdit(Long id) throws TutorialNotFoundException {

        TutoringOffer tutoringOffer = tutoringRepository.findById(id)
                .orElseThrow(() -> new TutorialNotFoundException(id));

        TutorialEditDTO tutorialEditDTO = modelMapper.map(tutoringOffer, TutorialEditDTO.class);
        tutorialEditDTO.setCategory(tutoringOffer.getCategory().getName());

        return tutorialEditDTO;
    }

    @Override
    public TutoringOffer findTutoringOfferByID(Long id) {
        return tutoringRepository.findById(id).orElse(null);
    }

    @Override
    public List<TutorialViewDTO> sortAlphabetically(List<TutorialViewDTO> offers) {

        if (offers != null && !offers.isEmpty()) {
            return offers.stream()
                    .sorted((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()))
                    .collect(Collectors.toList());
        }
        return List.of(); // Return an empty list if offers is null or empty
    }

    @Override
    public List<TutorialViewDTO> sortByDate(List<TutorialViewDTO> offers) {

        if (offers != null && !offers.isEmpty()) {
            return offers.stream()
                    .sorted((o1, o2) -> o2.getCreatedOn().compareTo(o1.getCreatedOn())) // Sort by date in descending order
                    .collect(Collectors.toList());
        }

        return List.of(); // Return an empty list if offers is null or empty
    }

    private List<TutorialViewDTO> returnListOfOffersAsViewDTO(List<TutoringOffer> listOfOffers) {

        return listOfOffers.stream()
                .map(offer -> {
                    TutorialViewDTO dto = modelMapper.map(offer, TutorialViewDTO.class);
                    dto.setEmailOfTheTutor(offer.getAddedBy().getEmail());
                    return dto;
                })
                .collect(Collectors.toList());

    }

}
