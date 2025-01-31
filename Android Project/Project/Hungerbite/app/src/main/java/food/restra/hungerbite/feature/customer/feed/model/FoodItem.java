package food.restra.hungerbite.feature.customer.feed.model;

import food.restra.hungerbite.feature.login.model.Profile;

/**
 * Created by Yeahia Muhammad Arif on 29,January,2021
 */
public class FoodItem {
    private String productId;
    private String title;
    private String image;
    String price;
    String category;
    String foodCategory;
    String uploaderId;
    String uploaderName;
    String uploaderImage;
    String location;
    String uploaderToken;

    public FoodItem(){}

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    public String getUploaderImage() {
        return uploaderImage;
    }

    public void setUploaderImage(String uploaderImage) {
        this.uploaderImage = uploaderImage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUploaderToken() {
        return uploaderToken;
    }

    public void setUploaderToken(String uploaderToken) {
        this.uploaderToken = uploaderToken;
    }
}
