package com.tapfood.application.tapFood.restaurantList;

import com.tapfood.application.tapFood.addFood.AddFoodDataItem;

import java.io.Serializable;
import java.util.List;

public class RestaurantDataItem implements Serializable {

    public String nameRestaurant,urlImageRestaurant,descriptionRestaurant,saleRestaurant,rateRestaurant;
    public List<AddFoodDataItem> addFoodDataItems;



    public RestaurantDataItem() {
    }

    public String getNameRestaurant() {
        return nameRestaurant;
    }

    public void setNameRestaurant(String nameRestaurant) {
        this.nameRestaurant = nameRestaurant;
    }

    public String getUrlImageRestaurant() {
        return urlImageRestaurant;
    }

    public void setUrlImageRestaurant(String urlImageRestaurant) {
        this.urlImageRestaurant = urlImageRestaurant;
    }

    public String getDescriptionRestaurant() {
        return descriptionRestaurant;
    }

    public void setDescriptionRestaurant(String descriptionRestaurant) {
        this.descriptionRestaurant = descriptionRestaurant;
    }

    public String getSaleRestaurant() {
        return saleRestaurant;
    }

    public void setSaleRestaurant(String saleRestaurant) {
        this.saleRestaurant = saleRestaurant;
    }

    public String getRateRestaurant() {
        return rateRestaurant;
    }

    public void setRateRestaurant(String rateRestaurant) {
        this.rateRestaurant = rateRestaurant;
    }

    public List<AddFoodDataItem> getAddFoodDataItems() {
        return addFoodDataItems;
    }

    public void setAddFoodDataItems(List<AddFoodDataItem> addFoodDataItems) {
        this.addFoodDataItems = addFoodDataItems;
    }
}
