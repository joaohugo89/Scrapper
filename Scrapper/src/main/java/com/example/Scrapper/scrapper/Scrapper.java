package com.example.Scrapper.scrapper;

import com.example.Scrapper.scrapper.request.ScrapperRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books_scrapped")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Scrapper {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String title;
    Double price;
    String link;

    public Scrapper(ScrapperRequestDTO data){
        this.title = data.title();
        this.price = data.price();
        this.link = data.link();
    }
}
