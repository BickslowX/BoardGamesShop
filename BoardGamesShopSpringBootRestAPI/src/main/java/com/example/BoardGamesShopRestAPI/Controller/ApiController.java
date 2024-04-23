package com.example.BoardGamesShopRestAPI.Controller;

import com.example.BoardGamesShopRestAPI.Model.Product;
import com.example.BoardGamesShopRestAPI.Model.User;
import com.example.BoardGamesShopRestAPI.Repo.ProductRepo;
import com.example.BoardGamesShopRestAPI.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/getUsers")
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    @PostMapping(value = "/saveUser")
    public String saveUsers(@RequestBody User user){
          userRepo.save(user);
          return "Saved";
    }

    @PutMapping(value = "/updateUser/{id}")
    public String updateUser(@PathVariable int id, @RequestBody User user){
        User updateUser = userRepo.findById(id).get();
        updateUser.setName(user.getName());
        updateUser.setSurname(user.getSurname());
        updateUser.setContact_info(user.getContact_info());
        updateUser.setDate_of_birth(user.getDate_of_birth());
        updateUser.setPassword_hash(user.getPassword_hash());
        userRepo.save(updateUser);
        return "Updated";
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public String deleteUser(@PathVariable int id){
        User deleteUser = userRepo.findById(id).get();
        userRepo.delete(deleteUser);
        return "Deleted";
    }

    @Autowired
    private ProductRepo productRepo;

    @GetMapping(value = "/getProducts")
    public List<Product> getProducts(){
        return productRepo.findAll();
    }

    @PostMapping(value = "/saveProduct")
    public String saveProduct(@RequestBody Product product){
        productRepo.save(product);
        return "Saved";
    }

    @PutMapping(value = "/updateProduct/{id}")
    public String updateProduct(@PathVariable int id, @RequestBody Product product){
        Product updateProduct = productRepo.findById(id).get();
        updateProduct.setName(product.getName());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setPrice(product.getPrice());
        updateProduct.setQuantity(product.getQuantity());
        productRepo.save(updateProduct);
        return "Updated";
    }

    @DeleteMapping(value = "/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id){
        Product deleteProduct = productRepo.findById(id).get();
        productRepo.delete(deleteProduct);
        return "Deleted";
    }
}
