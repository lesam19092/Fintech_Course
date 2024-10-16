package com.example.edadil_microservice.model.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopResponse {
    private String companyName;
    private int id;
    private String city;

    //TODO потом убрать toString

    @Override
    public String toString() {
        return "ShopResponse{" +
                "companyName='" + companyName + '\'' +
                ", id=" + id +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    private String address;
}
