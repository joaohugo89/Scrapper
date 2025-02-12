package com.example.Scrapper.scrapper.response;

import com.example.Scrapper.scrapper.Scrapper;

public record ScrapperResponseDTO (String title, Double price, String url){
    public ScrapperResponseDTO(Scrapper scrapper){
        this(scrapper.getTitle(), scrapper.getPrice(), scrapper.getLink());
    }
}
