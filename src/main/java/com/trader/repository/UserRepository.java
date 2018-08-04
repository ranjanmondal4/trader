package com.trader.repository;

import com.trader.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface UserRepository extends JpaRepository<User, Serializable> {
    User findByApiKey(String apiKey);
}

