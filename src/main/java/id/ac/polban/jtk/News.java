package id.ac.polban.jtk;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class News {
    @Id
    private String id;
    private String title;
    private String author;
    private Date date;
    private String content;
    private Integer views;

    @PersistenceConstructor
    public News(final String title, 
                final String author,
                final Date date, 
                final String content, 
                final Integer views) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.content = content;
        this.views = views;
    }

    public News(final String id,
                final String title,
                final String author,
                final Date date,
                final String content,
            final Integer views) {
        this(title, author, date, content, views);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public Integer getViews() {
        return views;
    }

    @Override
    public String toString() {
        return String.format("News[id=%s, title='%s', author='%s', date='%tD', content='%s', views='%d']", 
            this.id, this.title, this.author, this.date, this.content, this.views);
    }
}