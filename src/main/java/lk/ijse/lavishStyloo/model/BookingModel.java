package lk.ijse.lavishStyloo.model;

import lk.ijse.lavishStyloo.db.DBConnection;
import lk.ijse.lavishStyloo.dto.BookingDTO;
import lk.ijse.lavishStyloo.dto.BookingDetailsDTO;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.dto.tm.BookingDetailsTm;
import lk.ijse.lavishStyloo.dto.tm.BookingTM;
import lk.ijse.lavishStyloo.util.CrudUtil;
import lk.ijse.lavishStyloo.util.DateTimeUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookingModel {
    public static String countBooking() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.crudUtil("SELECT COUNT(booking_id) FROM booking");
        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    public static String getNextId() throws SQLException, ClassNotFoundException {
        List<BookingDTO> ids = findAll();
        String oldId = null;
        for (BookingDTO bookingDTO : ids) {
            oldId = bookingDTO.getBooking_id();
        }
        int lastIndex;
        try {
            String[] split = oldId.split("B00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "B001";
        }
        lastIndex++;
        return "B00" + lastIndex;
    }

    private static List<BookingDTO> findAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.crudUtil("SELECT  * FROM booking");
        return toDTOList(resultSet);
    }

    private static List<BookingDTO> toDTOList(ResultSet resultSet) throws SQLException {
        List<BookingDTO> list=new ArrayList<>();
        while (resultSet.next()){
            BookingDTO  dto=new BookingDTO();
            dto.setBooking_id(resultSet.getString(1));
            dto.setDate(resultSet.getString(2));
            dto.setTime(resultSet.getString(3));
            dto.setTotal(resultSet.getString(4));
            dto.setCus_id(resultSet.getString(5));
            dto.setBooking_start(resultSet.getString(6));
            dto.setBooking_end(resultSet.getString(7));

            list.add(dto);
        }
        return list;
    }

    public static boolean booking(BookingDTO bookingDTO, List<BookingDetailsDTO> list) throws SQLException, ClassNotFoundException {
        /**
         * get connection
         * */

        Connection connection = DBConnection.getInstance().getConnection();
        /**
         * auto commit off
         * */
        connection.setAutoCommit(false);
        try {
            /**
             * save Booking
             * */
            boolean isSaved = save(bookingDTO);
            if (isSaved) {
                /**
                 * save Booking Details
                 * */
                boolean isSavedDetails = BookingDetailsModel.save(list);
                if (isSavedDetails) {

                    /**
                     * commit to data base
                     * */
                  connection.commit();
                  return true;
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            /**
             * auto commit on
             * */
            connection.setAutoCommit(true);
        }
        return false;
    }

    private static boolean save(BookingDTO bookingDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO booking VALUES (?,?,?,?,?,?,?)",
                bookingDTO.getBooking_id(),
                bookingDTO.getDate(),
                bookingDTO.getTime(),
                bookingDTO.getTotal(),
                bookingDTO.getCus_id(),
                bookingDTO.getBooking_start(),
                bookingDTO.getBooking_end()
                );
    }

    public static List<ReportCm> getAppointment() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.crudUtil("SELECT MONTH(date) AS month, SUM(total)as num FROM booking GROUP BY month");
        List<ReportCm> list=new ArrayList<>();
        while (result.next()) {
            ReportCm reportCm = new ReportCm();
            reportCm.setTitle(result.getString(1));
            reportCm.setValue(result.getDouble(2));
            list.add(reportCm);
        }
        return list;
    }

    public static String CountBooking() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM  booking";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public static String CountBookingByDate(String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM  booking WHERE date=?";
        ResultSet resultSet = CrudUtil.crudUtil(sql,date);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public static List<BookingTM> findBooking() throws SQLException, ClassNotFoundException {
        String sql ="SELECT b.booking_id,c.cust_id,c.first_name,c.last_name,b.date,b.time,b.total,b.booking_start,b.booking_end FROM booking b  INNER JOIN customer c on b.cust_id = c.cust_id";
        ResultSet resultSet=CrudUtil.crudUtil(sql);
        return toTms(resultSet);
    }

    private static List<BookingTM> toTms(ResultSet resultSet) throws SQLException {
        List<BookingTM> list=new ArrayList<>();
        while (resultSet.next()){
            BookingTM bookingTM = new BookingTM();
            bookingTM.setBookingId(resultSet.getString(1));
            bookingTM.setCusId(resultSet.getString(2));
            bookingTM.setCustomer(resultSet.getString(3)+" "+resultSet.getString(4));
            bookingTM.setDate(resultSet.getString(5));
            bookingTM.setTime(resultSet.getString(6));
            bookingTM.setTotal(resultSet.getString(7));
            bookingTM.setBookingStart(resultSet.getString(8));
            bookingTM.setBookingEnd(resultSet.getString(9));
            list.add(bookingTM);
        }
        return list;
    }

    public static List<BookingTM> findBookingByDate(String date) throws SQLException, ClassNotFoundException {
        String sql ="SELECT b.booking_id,c.cust_id,c.first_name,c.last_name,b.date,b.time,b.total,b.booking_start,b.booking_end FROM booking b  INNER JOIN customer c on b.cust_id = c.cust_id WHERE b.date=?";
        ResultSet resultSet=CrudUtil.crudUtil(sql,date);
        return toTms(resultSet);
    }

    public static List<BookingDetailsTm> findBookingDetailsByBookingId(String bookingId) throws SQLException, ClassNotFoundException {
        String sql ="SELECT t.treatment,e.first_name,e.last_name,e.nic,b.charge  FROM booking_details b JOIN booking b2 on b.booking_id = b2.booking_id INNER JOIN employee e on b.nic = e.nic INNER JOIN treatment t on b.treat_id = t.treat_id WHERE b.booking_id=?";
        ResultSet resultSet=CrudUtil.crudUtil(sql,bookingId);
        return toDetailsTms(resultSet);
    }

    private static List<BookingDetailsTm> toDetailsTms(ResultSet resultSet) throws SQLException {
        List<BookingDetailsTm> list=new ArrayList<>();
        while (resultSet.next()){
            BookingDetailsTm tm=new BookingDetailsTm();
            tm.setTreatment(resultSet.getString(1));
            tm.setEmployee(resultSet.getString(2)+" "+resultSet.getString(3));
            tm.setNic(resultSet.getString(4));
            tm.setCharge(resultSet.getString(5));
            list.add(tm);
        }
        return list;
    }

    public static String UnCompliedCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM booking b LEFT JOIN payment p on b.booking_id = p.booking_id";
        ResultSet result = CrudUtil.crudUtil(sql);
        if (result.next()) {
            return result.getString(1);
        }
        return "0";
    }

    public static String CompliedCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM booking b RIGHT JOIN payment p on b.booking_id = p.booking_id WHERE b.date= CURDATE()";
        ResultSet result = CrudUtil.crudUtil(sql);
        if (result.next()) {
            return result.getString(1);
        }
        return "0";
    }

    public static String PendingCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM booking b LEFT JOIN payment p on b.booking_id = p.booking_id WHERE b.date= CURDATE();";
        ResultSet result = CrudUtil.crudUtil(sql);
        if (result.next()) {
            return result.getString(1);
        }
        return "0";
    }

    public static List<ReportCm> getYearlyAppointment(String year) throws SQLException, ClassNotFoundException {
        String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        List<ReportCm> list=new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM booking WHERE YEAR(date)=? and MONTH(date)= ? GROUP BY MONTH(date)",year,(i<10?("0"+i):i));
            if (result.next()) {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(allMonth[(i-1)]);
                reportCm.setValue(result.getDouble(1));
                list.add(reportCm);
            }else {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(allMonth[(i-1)]);
                reportCm.setValue(0);
                list.add(reportCm);
            }
        }
        return list;
    }

    public static List<ReportCm> getMonthlyAppointment(String year, String moth) throws SQLException, ClassNotFoundException {
        String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        int month=0;
        for (int i = 0; i < allMonth.length; i++) {
            if (allMonth[i].equals(moth)){
                month=i;
                break;
            }
        }
        int days = DateTimeUtil.getDays(Integer.parseInt(year), (month + 1));
        List<ReportCm> list=new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM booking WHERE YEAR(date)=? and MONTH(date)= ? and DAY(date)=? GROUP BY DAY(date)",year,(month + 1),(i<10?("0"+i):i));
            if (result.next()) {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(String.valueOf((i-1)));
                reportCm.setValue(result.getDouble(1));
                list.add(reportCm);
            }else {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(String.valueOf((i)));
                reportCm.setValue(0);
                list.add(reportCm);
            }
        }
        return list;
    }

    public static List<ReportCm> getDayAppointment(String year, String month) throws SQLException, ClassNotFoundException {
        String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        int thisMonth=0;
        for (int i = 0; i < allMonth.length; i++) {
            if (allMonth[i].equals(month)){
                thisMonth=i;
                break;
            }
        }
        List<ReportCm> list=new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM booking WHERE YEAR(date)=? and MONTH(date)= ? GROUP BY MONTH(date)",year,(thisMonth+1));
            if (result.next()) {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(allMonth[(i-1)]);
                reportCm.setValue(result.getDouble(1));
                list.add(reportCm);
            }else {
                ReportCm reportCm = new ReportCm();
                reportCm.setTitle(allMonth[(i-1)]);
                reportCm.setValue(0);
                list.add(reportCm);
            }
        }
        return list;
    }
}
