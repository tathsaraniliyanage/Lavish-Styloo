package lk.ijse.lavishStyloo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SupperOrderDTO {
    private String sup_oid;
    private String supplier_id;
    private String date;
    private String time;
    private double total;

}
