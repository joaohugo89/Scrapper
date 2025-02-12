package com.example.Scrapper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Scrapper.repositories.ScrapperRepository;
import com.example.Scrapper.scrapper.Scrapper;
import com.example.Scrapper.scrapper.request.ScrapperRequestDTO;
import com.example.Scrapper.scrapper.response.ScrapperResponseDTO;
import com.example.Scrapper.scrapper.service.ScrapperService;

@RestController
@CrossOrigin
@RequestMapping("scrapper/")
public class ScrapperController {
    @Autowired
    private ScrapperRepository scrapperRepository;

    @Autowired
    private ScrapperService scrapperService;

    @PostMapping("extract/")
    public String extractData() {
        scrapperService.extractDataFromToScrape();
        return "Scraping iniciado e dados salvos no banco!";
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/")
    public void saveBooksScrapped(@RequestBody ScrapperRequestDTO data){
        Scrapper scrapperData = new Scrapper(data);
        scrapperRepository.save(scrapperData);
        return;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<ScrapperResponseDTO> getAllBooksScrapped() {
        List<ScrapperResponseDTO> scrapperList = scrapperRepository.findAll().stream().map(ScrapperResponseDTO::new).toList();
        return scrapperList;
    }
}
