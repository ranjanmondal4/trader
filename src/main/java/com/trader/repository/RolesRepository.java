package com.trader.repository;

import com.trader.domain.user.Roles;
import com.trader.domain.user.User;
import com.trader.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

public interface RolesRepository extends JpaRepository<Roles, Serializable> {

    @Query("select r.role from Roles r where r.user = :user")
    List<Role> findRoleByUser(@Param("user") User user);
}

