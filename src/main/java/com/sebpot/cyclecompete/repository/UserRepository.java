package com.sebpot.cyclecompete.repository;

import com.sebpot.cyclecompete.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);
}
