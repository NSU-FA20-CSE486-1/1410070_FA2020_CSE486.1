package food.restra.hungerbite.chef.addfood.model;

/**
 * Created by Yeahia Muhammad Arif on 02,February,2021
 */
public class FoodItem {
    String title;
    String price;
    String category;
    String foodCategory;
    String image;
    String uploaderId;

    public FoodItem(String title, String price, String category, String foodCategory, String image, String uploaderId) {
        this.title = title;
        this.price = price;
        this.category = category;
        this.foodCategory = foodCategory;
        this.image = image;
        this.uploaderId = uploaderId;
    }

    public FoodItem(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }
}
