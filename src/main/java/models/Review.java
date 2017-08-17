package models;

/**
 * Created by Guest on 8/16/17.
 */
public class Review {
    private int rating;
    private String content;
    private int id;
    private int restaurantId;


    public Review (int rating, String content, int restaurantId) {
        this.rating = rating;
        this.content = content;
        this.restaurantId = restaurantId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (rating != review.rating) return false;
        if (id != review.id) return false;
        return content.equals(review.content);
    }

    @Override
    public int hashCode() {
        int result = rating;
        result = 31 * result + content.hashCode();
        result = 31 * result + id;
        return result;
    }
}
