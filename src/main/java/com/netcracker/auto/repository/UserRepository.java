package com.netcracker.auto.repository;

import com.netcracker.auto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    /*@Override
    void delete(User user);

    @Override
    User save(User user);*/

}