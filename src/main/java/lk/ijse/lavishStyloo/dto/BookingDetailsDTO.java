package lk.ijse.lavishStyloo.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BookingDetailsDTO {
    private String booking_id;
    private String nic;
    private String treat_id;
    private double charge;
}
