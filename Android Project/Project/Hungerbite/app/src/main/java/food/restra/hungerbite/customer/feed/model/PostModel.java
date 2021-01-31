package food.restra.hungerbite.customer.feed.model;

/**
 * Created by Yeahia Muhammad Arif on 29,January,2021
 */
public class PostModel {
    private String title;
    private String image;

    public PostModel(){}
    public PostModel(String title, String image) {
        this.title = title;
        this.image = image;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
