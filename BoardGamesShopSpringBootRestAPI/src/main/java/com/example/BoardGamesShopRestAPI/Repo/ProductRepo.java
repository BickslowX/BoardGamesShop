package com.example.BoardGamesShopRestAPI.Repo;

import com.example.BoardGamesShopRestAPI.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
