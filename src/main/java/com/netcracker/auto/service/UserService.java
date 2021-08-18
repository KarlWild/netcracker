package com.netcracker.auto.service;

import com.netcracker.auto.entity.RolesEntity;
import com.netcracker.auto.entity.User;
import com.netcracker.auto.repository.UserRepository;
import com.netcracker.auto.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public User registerNewUserAccount(UserDTO userDto) {//throws UserAlreadyExistException
        if (emailExist(userDto.getEmail())) {
//            throw new UserAlreadyExistException("There is an account with that email address: "
//                    + userDto.getEmail());
            System.out.println("There is an account with that email address:"+userDto.getEmail());
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRoles(Set.of(RolesEntity.ROLE_USER));
        user.setDefault();
        return repository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    private boolean emailExist(String email) { return repository.findByEmail(email) != null; }

    public void updateBalance(Double money, Long id) {
        repository.updateUserBalanceById(money, id);
    }

    public void saveUser(User user) {
        repository.save(user);
    }

}
