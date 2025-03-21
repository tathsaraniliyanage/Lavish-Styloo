package lk.ijse.lavishStyloo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class UserDTO {
    private String userName;
    private String password;
    private String nic;

}
