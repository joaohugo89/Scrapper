package com.example.Scrapper.scrapper.service;

import java.io.IOException;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.Scrapper.repositories.ScrapperRepository;
import com.example.Scrapper.scrapper.Scrapper;
import com.example.Scrapper.scrapper.request.ScrapperRequestDTO;

import jakarta.transaction.Transactional;

@Service
public class ScrapperService{
    @Autowired
    private ScrapperRepository scrapperRepository;

    @Value("#{'${urls}'.split(',')}")
    String baseUrl;



    public void extractDataFromToScrape(){
        ScrapperRequestDTO data;
        try {
            String url = "https://books.toscrape.com/catalogue/";
            Document baseDocument = Jsoup.connect(baseUrl).get();
            Document document = Jsoup.connect(baseUrl).get();
                Elements books = document.select(".product_pod");
                System.out.println(baseUrl);
                for (Element bk:books){
                    String title = bk.select("h3 > a[href][title]").attr("title");
                    String price = bk.select(".price_color").text();
                    String link = bk.getElementsByTag("a").first().attr("href");
                    data = new ScrapperRequestDTO(title, Double.parseDouble(price.substring(1)), baseUrl + link);
                    saveOrUpdateBook(data);
				}
			Elements nextElements = baseDocument.select(".next");
			Element nextElement = nextElements.first();
			String relativeUrl = nextElement.getElementsByTag("a").first().attr("href");
			
			while (!nextElements.isEmpty()) {
				String completeUrl = baseUrl + relativeUrl;
				document = Jsoup.connect(completeUrl).get();
				books = document.select(".product_pod");
				for (Element bk:books){
					String title = bk.select("h3 > a[href][title]").attr("title");
					String price = bk.select(".price_color").text();
					String link = bk.getElementsByTag("a").first().attr("href");
                    data = new ScrapperRequestDTO(title, Double.parseDouble(price.substring(1)), baseUrl + link);
                    saveOrUpdateBook(data);
				}
				baseUrl = url;
				nextElements = document.select(".next");
				nextElement = nextElements.first();
				relativeUrl = nextElement.getElementsByTag("a").first().attr("href");
            }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }

    @Transactional
    public void saveOrUpdateBook(ScrapperRequestDTO data) {
        Optional<Scrapper> existingBook = scrapperRepository.findByTitle(data.title());

        if (existingBook.isPresent()) {
            Scrapper book = existingBook.get();
            book.setPrice(data.price()); // Atualiza preço, se necessário
            book.setLink(data.link());
            scrapperRepository.save(book);
        } else {
            Scrapper newBook = new Scrapper(data);
            scrapperRepository.save(newBook);
        }
    }
}
