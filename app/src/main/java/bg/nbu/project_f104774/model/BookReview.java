package bg.nbu.project_f104774.model;

public class BookReview {
    private int id;
    private String name;
    private String author;
    private String summary;
    private int rate;

    public BookReview(int id, String name, String author, String summary, int rate) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.summary = summary;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    public int getRate() {
        return rate;
    }
}
