package com.example.Scrapper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScrapperApplication {

	public static void main(String[] args) {
		try {
			String baseUrl = "https://books.toscrape.com/";
			String url = "https://books.toscrape.com/catalogue/";
			Document baseDocument = Jsoup.connect(baseUrl).get();
			Document document = Jsoup.connect(baseUrl).get();
				Elements books = document.select(".product_pod");
				System.out.println(baseUrl);
				for (Element bk:books){
					String title = bk.select("h3 > a[href][title]").attr("title");
					String price = bk.select(".price_color").text();
					String link = bk.getElementsByTag("a").first().attr("href");
					System.out.println(title + " - " + price + " - " + baseUrl + link);
				}
			Elements nextElements = baseDocument.select(".next");
			Element nextElement = nextElements.first();
			String relativeUrl = nextElement.getElementsByTag("a").first().attr("href");

			System.out.println("=================================================================="); 
			
			while (!nextElements.isEmpty()) {
				String completeUrl = baseUrl + relativeUrl;
				document = Jsoup.connect(completeUrl).get();
				books = document.select(".product_pod");
				for (Element bk:books){
					String title = bk.select("h3 > a[href][title]").attr("title");
					String price = bk.select(".price_color").text();
					String link = bk.getElementsByTag("a").first().attr("href");
					System.out.println(title + " - " + price + " - " + baseUrl + baseUrl + link);
				}
				System.out.println("==================================================================");
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
}

