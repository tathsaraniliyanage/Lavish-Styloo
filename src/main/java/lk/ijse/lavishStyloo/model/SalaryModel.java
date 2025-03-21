package lk.ijse.lavishStyloo.model;

import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.dto.SalaryDTO;
import lk.ijse.lavishStyloo.dto.tm.SalaryTm;
import lk.ijse.lavishStyloo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SalaryModel {
    public static List<SalaryTm> findSalary() throws SQLException, ClassNotFoundException {
        String sql = "SELECT e.nic,e.first_name,e.last_name,e.city,e.street,e.lane,e.email,e.contact,s.bonus,s.salary FROM salary s inner join employee e on s.nic = e.nic WHERE MONTH(date) = MONTH(CURDATE()) && YEAR(date) = YEAR(CURDATE()) and (SELECT COUNT(*) FROM attendance WHERE nic='34343' and date BETWEEN DATE_SUB(NOW(), INTERVAL 31 DAY) AND NOW()) !=0";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        return setTms(resultSet);
    }

    private static List<SalaryTm> setTms(ResultSet resultSet) throws SQLException {
        List<SalaryTm> list = new ArrayList<>();
        while (resultSet.next()) {
            SalaryTm salaryTm = new SalaryTm();
            salaryTm.setNic(resultSet.getString(1));
            salaryTm.setName(resultSet.getString(2) + " " + resultSet.getString(3));
            salaryTm.setAddress(resultSet.getString(4) + " ," + resultSet.getString(5) + " ," + resultSet.getString(6));
            salaryTm.setMail(resultSet.getString(7));
            salaryTm.setContact(resultSet.getString(8));
            salaryTm.setBones(resultSet.getDouble(9));
            salaryTm.setSalary(resultSet.getDouble(10));
            list.add(salaryTm);
        }
        return list;
    }

    public static List<SalaryTm> findSalaryByLike(String text) throws SQLException, ClassNotFoundException {
        String arg = text + "%";
        String sql = "SELECT e.nic,e.first_name,e.last_name,e.city,e.street,e.lane,e.email,e.contact,s.bonus,s.salary FROM salary s inner join employee e on s.nic = e.nic WHERE MONTH(date) = MONTH(CURDATE()) && YEAR(date) = YEAR(CURDATE()) and (SELECT COUNT(*) FROM attendance WHERE nic=e.nic and date BETWEEN DATE_SUB(NOW(), INTERVAL 31 DAY) AND NOW()) !=0 OR e.nic LIKE ? OR e.first_name LIKE ? OR  e.last_name LIKE ? OR e.contact LIKE ? OR s.salary LIKE ? OR s.bonus LIKE ?";
        ResultSet resultSet = CrudUtil.crudUtil(sql, arg, arg, arg, arg, arg, arg);
        return setTms(resultSet);
    }

    public static boolean save(SalaryDTO salaryDTO) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO salary VALUES (?,?,?,?,?,?)";
        return CrudUtil.crudUtil(sql
                , salaryDTO.getS_id()
                , salaryDTO.getDate()
                , salaryDTO.getTime()
                , salaryDTO.getSalary()
                , salaryDTO.getBonus()
                , salaryDTO.getNic()
        );
    }

    public static boolean isExsitThisMonth(String nic) throws SQLException, ClassNotFoundException {
        String sql = "SELECT e.nic,e.first_name,e.last_name,e.city,e.street,e.lane,e.email,e.contact,s.bonus,s.salary FROM salary s inner join employee e on s.nic = e.nic WHERE MONTH(date) = MONTH(CURDATE()) && YEAR(date) = YEAR(CURDATE()) and (SELECT COUNT(*) FROM attendance WHERE nic=e.nic and date BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW()) !=0 AND s.nic=?";
        ResultSet set = CrudUtil.crudUtil(sql, nic);
        return set.next();
    }

    public static String getCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM salary s inner join employee e on s.nic = e.nic WHERE MONTH(date) = MONTH(CURDATE()) && YEAR(date) = YEAR(CURDATE()) and (SELECT COUNT(*) FROM attendance WHERE nic= e.nic and date BETWEEN DATE_SUB(NOW(), INTERVAL 31 DAY) AND NOW()) !=0";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";

    }

    public static String getNext() throws SQLException, ClassNotFoundException {
        List<String> salary = findIdSalary();
        String oldId = null;
        for (String id : salary) {
            oldId = id;
        }
        int lastIndex;
        try {
            String[] split = oldId.split("S00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "S001";
        }
        lastIndex++;
        return "S00" + lastIndex;
    }

    /**
     * load all ids from salary
     * */
    private static List<String> findIdSalary() throws SQLException, ClassNotFoundException {
        ResultSet set=CrudUtil.crudUtil("SELECT s_id from salary");
        List<String> list=new ArrayList<>();
        while (set.next()){
          list.add(set.getString(1));
        }
        return list;
    }
}
