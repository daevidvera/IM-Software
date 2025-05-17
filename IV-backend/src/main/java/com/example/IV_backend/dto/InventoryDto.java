package com.example.IV_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {

    private long productID;
    private String productName;
    private String photoURL;
    private String category;
    private double  priceBought;
    private double  sellingPrice;
    private  int quantity;

}
