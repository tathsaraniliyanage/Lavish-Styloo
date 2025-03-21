package lk.ijse.lavishStyloo.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AttendanceDTO{
    private String date;
    private String in_time;
    private String out_time;
    private String nic;

}
