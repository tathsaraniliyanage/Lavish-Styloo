package lk.ijse.lavishStyloo.model;

import lk.ijse.lavishStyloo.dto.AttendanceDTO;
import lk.ijse.lavishStyloo.dto.tm.AttendanceTm;
import lk.ijse.lavishStyloo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AttendanceModel {
    public static boolean save(AttendanceDTO attendanceDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO attendance VALUES( ?,?,?,?)";
        return CrudUtil.crudUtil(sql, attendanceDTO.getDate(), attendanceDTO.getIn_time(),attendanceDTO.getOut_time(), attendanceDTO.getNic());
    }

    public static List<AttendanceTm> findByDate(String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT e.nic,e.first_name,e.last_name,e.street,e.lane,e.city,e.contact,a.date,a.in_time,a.out_time from attendance a INNER JOIN employee e on e.nic = a.nic where a.date=?";
        ResultSet resultSet = CrudUtil.crudUtil(sql, date);
        return toTm(resultSet);

    }

    private static List<AttendanceTm> toTm(ResultSet resultSet) throws SQLException {
        List<AttendanceTm> tmList = new ArrayList<>();
        while (resultSet.next()) {
            AttendanceTm tm = new AttendanceTm();
            tm.setNic(resultSet.getString(1));
            tm.setName(resultSet.getString(2) + " " + resultSet.getString(3));
            tm.setAddress(resultSet.getString(4) + ", " + resultSet.getString(5) + ", " + resultSet.getString(6));
            tm.setContact(resultSet.getString(7));
            tm.setDate(resultSet.getString(8));
            tm.setInTime(resultSet.getString(9));
            tm.setOutTime(resultSet.getString(10));

            tmList.add(tm);
        }
        return tmList;
    }

    public static AttendanceDTO findAttendanceByDateAndNic(String nic, String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM attendance where date=? AND nic=?";
        ResultSet result = CrudUtil.crudUtil(sql, date, nic);
        return toDTO(result);
    }

    private static AttendanceDTO toDTO(ResultSet result) throws SQLException {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        if (result.next()) {
            attendanceDTO.setDate(result.getString(1));
            attendanceDTO.setIn_time(result.getString(2));
            attendanceDTO.setOut_time(result.getString(3));
            attendanceDTO.setNic(result.getString(4));
        }

        return attendanceDTO;
    }

    public static String countAttendanceByDate(String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(nic) FROM attendance where date=?";
        ResultSet result = CrudUtil.crudUtil(sql, date);
        if (result.next()) {
            return result.getString(1);
        }
        return "0";
    }

    public static List<AttendanceTm> findByDateAndNameAndNic(String dateNow, String text) throws SQLException, ClassNotFoundException {
        String sql = "SELECT e.nic,e.first_name,e.last_name,e.street,e.lane,e.city,e.contact,a.date,a.in_time,a.out_time from attendance a INNER JOIN employee e on e.nic = a.nic where a.date=? AND e.first_name LIKE ? OR e.last_name LIKE ? OR e.nic LIKE ?";
        String args = text + "%";
        ResultSet resultSet = CrudUtil.crudUtil(sql, dateNow, args, args, args);
        return toTm(resultSet);
    }

    public static String getEmployee(String nic) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.crudUtil("SELECT COUNT(nic) from attendance WHERE date between  CURDATE() AND (SELECT date from salary WHERE nic ORDER BY date DESC LIMIT 1)");
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "NOTHING ATTENDANCE";
    }
}
