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

    public Products update (Products products) throws UserNotFoundException{
        if (!repo.existsById(products.getId())){
            throw new UserNotFoundException("User not Found ") ;
        }
        return repo.save(products);
    }

    public Products get (Integer id) throws UserNotFoundException{
        Optional<Products> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get() ;
        }
        throw new UserNotFoundException("User Not Found");
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
