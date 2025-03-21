package lk.ijse.lavishStyloo.dto;

import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class EmployeeDTO {
    private String nic;
    private String first_name;
    private String last_name;
    private String email;
    private String city;
    private String lane;
    private String street;
    private String contact;
    private LocalDate dateOfBirth;
    private String gender;
    private String role;

}
