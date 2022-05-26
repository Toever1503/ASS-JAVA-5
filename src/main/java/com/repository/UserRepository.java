package com.repository;

import java.util.List;
import java.util.Optional;

import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmailEquals(String email);

    Optional<User> findByEmailOrUsername(String email, String username);

    List<User> findAllByEmailOrUsername(String email, String username);
}