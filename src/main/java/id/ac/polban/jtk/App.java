package id.ac.polban.jtk;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

	@Autowired
	private NewsRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// clear data
		repository.deleteAll();

		News newsItem1 = repository.save(new News("title-1", "author-1", new Date(), "content-1", 0));
		News newsItem2 = repository.save(new News("title-2", "author-2", new Date(), "content-2", 0));
		News newsItem3 = repository.save(new News("title-3", "author-3", new Date(), "content-3", 0));
		News newsItem4 = repository.save(new News("title-4", "author-4", new Date(), "content-4", 0));
		try {
			News newsItem5 = repository.save(new News(null     , "author-5", new Date(), "content-5", 0));
		}
		catch (Exception e) {
			System.out.println("Expected exception");
		}

		try {
			News newsItem6 = repository.save(new News("title-6", ""        , new Date(), "content-6", 0));
		}
		catch (Exception e) {
			System.out.println("Expected exception");
		}

		try {
			News newsItem7 = repository.save(new News("title-7", "author-7", null      , "content-7", 0));	
		}
		catch (Exception e) {
			System.out.println("Expected exception");
		}


		repository.findById(newsItem1.getId());
		repository.findById(newsItem1.getId());
		repository.findById(newsItem1.getId());
		repository.findById(newsItem1.getId());
		repository.findById(newsItem2.getId());
		repository.findById(newsItem2.getId());
		repository.findById(newsItem2.getId());
		repository.findById(newsItem3.getId());
		repository.findById(newsItem3.getId());
		repository.findById(newsItem4.getId());

		System.out.println("News found with findAll():");		
		System.out.println("-------------------------------");

		for (News news : repository.findAll()) {
			System.out.println(news);
		}
	}
}