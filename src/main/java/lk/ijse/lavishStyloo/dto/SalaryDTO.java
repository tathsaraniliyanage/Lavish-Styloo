package lk.ijse.lavishStyloo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SalaryDTO {
    private String s_id;
    private String date;
    private String time;
    private double salary;
    private double bonus;
    private String nic;

}
