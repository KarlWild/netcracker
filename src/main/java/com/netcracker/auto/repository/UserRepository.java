package com.netcracker.auto.repository;

import com.netcracker.auto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    /*@Override
    void delete(User user);

    @Override
    User save(User user);*/

    @Modifying
    @Query("UPDATE User u SET u.balance = u.balance + :money where u.userId = :id")
    void updateUserBalanceById(@Param("money") Double money,
                               @Param("id") Long id);

}
