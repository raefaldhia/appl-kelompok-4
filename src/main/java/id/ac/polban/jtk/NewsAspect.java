package id.ac.polban.jtk;

import java.util.List;
import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NewsAspect {
    @Autowired
    private NewsRepository repository;

    @Around("execution(* id.ac.polban.jtk.NewsRepository.save(..)) && args(newsItem)")
    public Object validate(ProceedingJoinPoint pjp, News newsItem) throws Throwable {
        validateNews(newsItem);

        return pjp.proceed();
    }
    
    @Around("execution(* id.ac.polban.jtk.NewsRepository.saveAll(..)) && args(newsItems)")
    public Object validate(ProceedingJoinPoint pjp, List<News> newsItems) throws Throwable {
        for (News newsItem : newsItems) {
            validateNews(newsItem);
        }

        return pjp.proceed();
    }

    @AfterReturning(pointcut = "execution(java.util.List org.springframework.data.mongodb.repository.MongoRepository+.find*(..)) && target(id.ac.polban.jtk.NewsRepository)", returning = "retVal")
    private void addView(List retVal) {
        List<News> newsItems = retVal;

        for (News newsItem : newsItems) {
            incrementView(newsItem);
        }
    }

    @AfterReturning(pointcut = "execution(java.util.Optional org.springframework.data.mongodb.repository.MongoRepository+.find*(..)) && target(id.ac.polban.jtk.NewsRepository)", returning = "retVal")
    private void addView(Optional retVal) {
        Optional<News> newsItem = retVal;

        if (!newsItem.isPresent()) {
            return;
        }

        incrementView(newsItem.get());
    }

    private void incrementView(News newsItem) {
        repository.save(
            new News(newsItem.getId(), newsItem.getTitle(), newsItem.getAuthor(),
            newsItem.getDate(),
            newsItem.getContent(), newsItem.getViews() + 1));
    }

    private void validateNews(News newsItem) throws Exception {
        if (!(newsItem.getTitle() != null &&
              newsItem.getTitle().length() != 0 &&
              newsItem.getAuthor() != null &&
              newsItem.getAuthor().length() != 0 &&
              newsItem.getDate() != null &&
              newsItem.getContent() != null &&
              newsItem.getContent().length() != 0 &&
              newsItem.getViews() != null)) {
            throw new Exception("news item is not valid");
        }
    }
}