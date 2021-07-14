package com.netcracker.auto.service;

import java.util.List;
import java.util.Optional;

//import com.baeldung.persistence.model.PasswordResetToken;
//import com.baeldung.persistence.model.User;
//import com.baeldung.persistence.model.VerificationToken;
//import com.baeldung.persistence.model.NewLocationToken;
import com.netcracker.auto.entity.User;
import com.netcracker.auto.web.dto.UserDTO;

public interface IUserService {

    User registerNewUserAccount(UserDTO accountDto);

//    User getUser(String verificationToken);
//
//    void saveRegisteredUser(User user);
//
//    void deleteUser(User user);
//
//    void createVerificationTokenForUser(User user, String token);
//
//    User findUserByEmail(String email);
//
//    Optional<User> getUserByID(long id);
//
//    List<String> getUsersFromSessionRegistry();
}