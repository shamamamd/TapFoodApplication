package com.tapfood.application.tapFood.addFood;

import java.io.Serializable;

public class AddFoodDataItem implements Serializable {

    public String nameFood, descriptionFood, saleFood, urlImageFood, salePriceFood, realPriceFood;
    public  int numberFood;



    public AddFoodDataItem() {
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getDescriptionFood() {
        return descriptionFood;
    }

    public void setDescriptionFood(String descriptionFood) {
        this.descriptionFood = descriptionFood;
    }

    public String getSaleFood() {
        return saleFood;
    }

    public void setSaleFood(String saleFood) {
        this.saleFood = saleFood;
    }

    public String getUrlImageFood() {
        return urlImageFood;
    }

    public void setUrlImageFood(String urlImageFood) {
        this.urlImageFood = urlImageFood;
    }

    public String getSalePriceFood() {
        return salePriceFood;
    }

    public void setSalePriceFood(String salePriceFood) {
        this.salePriceFood = salePriceFood;
    }

    public String getRealPriceFood() {
        return realPriceFood;
    }

    public void setRealPriceFood(String realPriceFood) {
        this.realPriceFood = realPriceFood;
    }

    public int getNumberFood() {
        return numberFood;
    }

    public void setNumberFood(int numberFood) {
        this.numberFood = numberFood;
    }


}
