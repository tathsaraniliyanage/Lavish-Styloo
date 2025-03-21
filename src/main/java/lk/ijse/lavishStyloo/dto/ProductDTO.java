package lk.ijse.lavishStyloo.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ProductDTO {
    private String product_code;
    private String product;
    private String description;
    private String unit_price;
    private String qty;
    private String img;

}
