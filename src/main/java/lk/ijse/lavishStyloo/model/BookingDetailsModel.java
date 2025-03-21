package lk.ijse.lavishStyloo.model;

import lk.ijse.lavishStyloo.dto.BookingDetailsDTO;
import lk.ijse.lavishStyloo.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;



public class BookingDetailsModel {
    public static boolean save(List<BookingDetailsDTO> list) throws SQLException, ClassNotFoundException {
        for (BookingDetailsDTO dto : list) {
            boolean save = CrudUtil.crudUtil("INSERT INTO booking_details VALUES (?,?,?,?)",
                    dto.getBooking_id(),
                    dto.getNic(),
                    dto.getTreat_id(),
                    dto.getCharge()
            );
            if (save == false) {
                return false;
            }
        }
        return true;
    }
}
