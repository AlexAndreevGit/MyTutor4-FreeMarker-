package com.MyTutor2.service;


import com.MyTutor2.exceptions.TutorialNotFoundException;
import com.MyTutor2.model.DTOs.UserDTO;
import com.MyTutor2.model.DTOs.UserRegisterDTO;
import com.MyTutor2.model.entity.User;

import java.util.List;

public interface UserService {
    List<UserDTO> findAllUsersAsDTO() ;

    void registerUser(UserRegisterDTO userRegisterDTO);

    void deleteUser(User logedInUser) throws TutorialNotFoundException;

    void deleteUserById(Long userId) throws TutorialNotFoundException;

    User findUserById(Long id) throws TutorialNotFoundException;

    User findUserByUsername(String username);

    List<User> findAllUsers();
}
