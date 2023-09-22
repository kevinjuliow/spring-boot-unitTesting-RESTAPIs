package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;


    public Products add (Products products){
        return repo.save(products);
    }

    public Products update (Products products , Integer id ) throws UserNotFoundException{
        if (repo.findById(id).isPresent()){
            return repo.save(products);
        }else{
            throw new UserNotFoundException("User Not Found") ;
        }

    }

    public Products get (Integer id) throws UserNotFoundException{
        return repo.findById(id).orElseThrow(()->new UserNotFoundException("User Not Found"));
    }

    public List<Products> getAllProducts (){
        return repo.findAll();
    }
    public void delete (Integer id) throws UserNotFoundException{
        Optional<Products> result = repo.findById(id);
        if (result.isPresent()) {
            repo.deleteById(id);
        }
        throw new UserNotFoundException("User Not Found");
    }
}
