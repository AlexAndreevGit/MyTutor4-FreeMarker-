package com.MyTutor2.service;

import com.MyTutor2.exceptions.CategoryNotFoundException;
import com.MyTutor2.exceptions.TutorialNotFoundException;
import com.MyTutor2.exceptions.UserNotFoundException;
import com.MyTutor2.model.DTOs.TutorialAddDTO;
import com.MyTutor2.model.DTOs.TutorialEditDTO;
import com.MyTutor2.model.DTOs.TutorialViewDTO;

import java.util.List;

public interface TutorialsService {

    List<TutorialViewDTO> findAllByCategoryID(Long id);

    List<TutorialViewDTO> findAllTutoringOffersByUserId(Long id);

    List<TutorialViewDTO> findAllTutoringOffersByWordInTitleAndSubjectID(String word, Long subjectId);

    void addTutoringOffer(TutorialAddDTO tutorialAddDTO, String userName) throws CategoryNotFoundException, UserNotFoundException;

    void addTutoringOfferAfterEdit(TutorialEditDTO tutorialEditDTO, String userName) throws CategoryNotFoundException,UserNotFoundException;

    void removeOfferById(Long id) throws TutorialNotFoundException;

    TutorialEditDTO findTutorialById(Long id) throws TutorialNotFoundException;
}
