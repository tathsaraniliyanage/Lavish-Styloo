package lk.ijse.lavishStyloo.model;

import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.dto.cm.ReportCm;
import lk.ijse.lavishStyloo.util.CrudUtil;
import lk.ijse.lavishStyloo.util.DateTimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerModel {
    public static List<CustomerDTO> findAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM customer");
        return toDTO(resultSet);
    }

    public static boolean save(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer VALUES (?,?,?,?,?,?,?,?)";
        return CrudUtil.crudUtil(sql,
                customerDTO.getCustomer_id(),
                customerDTO.getFirst_name(),
                customerDTO.getLast_name(),
                customerDTO.getEmail(),
                customerDTO.getCity(),
                customerDTO.getLane(),
                customerDTO.getStreet(),
                customerDTO.getContact()
        );
    }

    public static boolean update(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET first_name=?,last_name=?,email=?,city=?,lane=?,street=?,contact=? WHERE cust_id=?";
        return CrudUtil.crudUtil(sql,
                customerDTO.getFirst_name(),
                customerDTO.getLast_name(),
                customerDTO.getEmail(),
                customerDTO.getCity(),
                customerDTO.getLane(),
                customerDTO.getStreet(),
                customerDTO.getContact(),
                customerDTO.getCustomer_id()
        );
    }

    public static CustomerDTO findCustomerById(String customerId) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.crudUtil("SELECT * FROM customer WHERE cust_id=?",customerId);
        CustomerDTO customerDTO = null;
        if (result.next()) {
            customerDTO = new CustomerDTO(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8)
            );
        }
        return customerDTO;
    }

    public static List<CustomerDTO> findCustomerByLike(String searchText) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE cust_id LIKE ? OR first_name LIKE ? OR last_name LIKE ? OR city LIKE ? OR street LIKE ? OR lane LIKE ? OR contact LIKE ? OR email LIKE ?";
        String args = searchText + "%";
        ResultSet resultSet = CrudUtil.crudUtil(sql,
                args, args, args, args, args, args, args, args
        );
        return toDTO(resultSet);
    }

    private static List<CustomerDTO> toDTO(ResultSet resultSet) throws SQLException {
        List<CustomerDTO> list = new ArrayList<>();
        while (resultSet.next()) {
            CustomerDTO customerDTO = new CustomerDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            );
            list.add(customerDTO);
        }
        return list;
    }

    public static String nextID() throws SQLException, ClassNotFoundException {
        List<CustomerDTO> ids = findAll();
        String oldId = null;
        for (CustomerDTO customerDTO : ids) {
            oldId = customerDTO.getCustomer_id();
        }
        int lastIndex;
        try {
            String[] split = oldId.split("C00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "C001";
        }
        lastIndex++;
        return "C00" + lastIndex;
    }

    public static List<String> findIds() {
        return null;
    }

    public static String countCustomer() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.crudUtil("SELECT COUNT(cust_id) FROM customer");
        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    public static List<ReportCm> getCustomerOrder() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.crudUtil("SELECT MONTH(date) AS month, SUM(total)as num FROM customer_order GROUP BY month");
        List<ReportCm> list=new ArrayList<>();
        while (result.next()) {
            ReportCm reportCm = new ReportCm();
            reportCm.setTitle(result.getString(1));
            reportCm.setValue(result.getDouble(2));
            list.add(reportCm);
        }
        return list;
    }

    public static boolean delete(String colId) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE  from customer where cust_id=?",colId);
    }

    public static List<ReportCm> getYearlyCustomerOrder(String year) throws SQLException, ClassNotFoundException {
        String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        List<ReportCm> list=new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM customer_order WHERE YEAR(date)=? and MONTH(date)= ? GROUP BY MONTH(date)",year,(i<10?("0"+i):i));
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

    public static List<ReportCm> getMonthlyCustomerOrder(String year, String moth) throws SQLException, ClassNotFoundException {
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
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM customer_order WHERE YEAR(date)=? and MONTH(date)= ? and DAY(date)=? GROUP BY DAY(date)",year,(month + 1),(i<10?("0"+i):i));
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

    public static List<ReportCm> getDayCustomerOrder(String year, String month) throws SQLException, ClassNotFoundException {
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
            ResultSet result = CrudUtil.crudUtil("SELECT  SUM(total)as num FROM customer_order WHERE YEAR(date)=? and MONTH(date)= ? GROUP BY MONTH(date)",year,(thisMonth+1));
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
