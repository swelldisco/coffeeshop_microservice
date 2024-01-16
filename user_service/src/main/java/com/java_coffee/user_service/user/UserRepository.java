package com.java_coffee.user_service.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    Optional<User> findByUserId(long userId);
    boolean existsByUserId(long userId);
    boolean existsByUserNameIgnoreCase(String userName);
    boolean existsByEmailAddressIgnoreCase(String emailAddress);
    Optional<User> findByUserNameIgnoreCase(String userName);
    Optional<User> findByEmailAddressIgnoreCase(String emailAddress);
    @Transactional
    void deleteByUserId(long userId);

}
