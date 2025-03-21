package lk.ijse.lavishStyloo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderDTO {
    private String cust_oid;
    private String cust_id;
    private String date;
    private String time;
    private double total;

}
