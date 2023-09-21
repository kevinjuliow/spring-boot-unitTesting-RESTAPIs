package com.example;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @Column(nullable = false , unique = true)
    @Length(min = 2)
    private String name ;
    @Column(nullable = false)
    @Min(0)
    private double price ;

    public Products (String name , double price){
        this.name = name ;
        this.price = price;
    }

}
