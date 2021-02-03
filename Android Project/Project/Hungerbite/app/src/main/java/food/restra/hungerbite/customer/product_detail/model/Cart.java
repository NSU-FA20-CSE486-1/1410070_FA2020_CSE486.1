package food.restra.hungerbite.customer.product_detail.model;

import food.restra.hungerbite.customer.feed.model.FoodItem;

/**
 * Created by Yeahia Muhammad Arif on 03,February,2021
 */
public class Cart {
    String customerId;
    int itemCount;
    FoodItem item;

    public Cart(){}

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public FoodItem getItem() {
        return item;
    }

    public void setItem(FoodItem item) {
        this.item = item;
    }
}
