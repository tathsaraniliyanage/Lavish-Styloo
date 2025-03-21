package lk.ijse.lavishStyloo.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sasindu Malshan
 * @project Lavish_Styloo
 * @date 11/25/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BookingDetailsTm {
    String treatment;
    String employee;
    String nic;
    String charge;
}
