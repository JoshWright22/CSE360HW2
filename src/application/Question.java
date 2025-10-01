package application;
import java.time.Instant;

public class Question {
    private String id;
    private String title;
    private String body;
    private String author;
    private Instant createdAt;
    private Instant updatedAt;

    public Question(String id, String title, String body, String author) {
        this.id = id;
        setTitle(title);
        setBody(body);
        if (author == null || author.trim().isEmpty())
            throw new IllegalArgumentException("Author cannot be empty.");
        this.author = author;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("Title cannot be empty.");
        if (title.length() > 200)
            throw new IllegalArgumentException("Title too long.");
        this.title = title.trim();
        this.updatedAt = Instant.now();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        if (body == null || body.trim().isEmpty())
            throw new IllegalArgumentException("Description cannot be empty.");
        if (body.length() > 2000)
            throw new IllegalArgumentException("Description too long.");
        this.body = body.trim();
        this.updatedAt = Instant.now();
    }

    public String getAuthor() {
        return author;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", author='" + author + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
