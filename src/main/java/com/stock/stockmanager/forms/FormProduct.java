package com.stock.stockmanager.forms;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.intellij.lang.annotations.RegExp;

import java.io.Serializable;

public class FormProduct implements Serializable {
    private Integer id;
    @NotNull(message = "Image must be not null")
    @NotEmpty(message = "Image must be not empty")
    private String image;
    @NotNull(message = "Name must be not null")
    @NotEmpty(message = "Name must be not empty")
    private String name;
    @NotNull(message = "Description must be not null")
    @NotEmpty(message = "Description must be not empty")
    private String description;
    @NotNull(message = "Quantity must be not null")
    @Min(value = 1, message = "Price must be greater to 0")
    private Integer quantity;
    @NotNull(message = "Price must be not null")
    @Min(value = 1, message = "Price must be greater to 0")
    private Double price;

    public FormProduct(Integer id, String image, String name, String description, Integer quantity, Double price) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
    public FormProduct(String image, String name, String description, Integer quantity, Double price) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FormProduct{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
