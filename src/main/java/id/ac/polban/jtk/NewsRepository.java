package id.ac.polban.jtk;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<News, String> {
	public List<News> findByTitleLike(String title);
}