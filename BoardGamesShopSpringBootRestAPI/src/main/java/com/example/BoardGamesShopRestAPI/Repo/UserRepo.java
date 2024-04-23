package com.example.BoardGamesShopRestAPI.Repo;

import com.example.BoardGamesShopRestAPI.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
