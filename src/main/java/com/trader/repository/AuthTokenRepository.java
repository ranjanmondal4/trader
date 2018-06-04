package com.trader.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trader.doman.AuthToken;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Serializable> {
	AuthToken findByToken(String token);
}
