package lk.ijse.lavishStyloo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class TreatmentDTO {
    private String treat_id;
    private double price;
    private String category;
    private String treatment;
    private String description;

}
