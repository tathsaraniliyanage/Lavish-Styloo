package lk.ijse.lavishStyloo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SupplierDTO {
    private String supplier_id;
    private String supplier_name;
    private String company;
    private String email;
    private String contact;
    private String location;

}
