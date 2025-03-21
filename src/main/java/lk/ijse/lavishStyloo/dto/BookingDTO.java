package lk.ijse.lavishStyloo.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class BookingDTO {
    private String booking_id;
    private String date;
    private String time;
    private String total;
    private String cus_id;
    private String booking_start;
    private String booking_end;

}




