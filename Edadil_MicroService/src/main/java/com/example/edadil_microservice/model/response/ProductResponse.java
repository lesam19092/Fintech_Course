package com.example.edadil_microservice.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private String name;
    private String firm;
    private Integer count;
    private Double price;


    //TODO потом убрать toString
    @Override
    public String toString() {
        return "ProductResponse{" +
                "name='" + name + '\'' +
                ", firm='" + firm + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}