package com.example.Scrapper.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Scrapper.scrapper.Scrapper;

public interface ScrapperRepository extends JpaRepository<Scrapper, Long>{
    Optional<Scrapper> findByTitle(String title);
}
