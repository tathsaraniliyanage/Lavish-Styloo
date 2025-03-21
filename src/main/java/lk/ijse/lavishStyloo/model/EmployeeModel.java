package lk.ijse.lavishStyloo.model;

import lk.ijse.lavishStyloo.db.DBConnection;
import lk.ijse.lavishStyloo.dto.EmployeeDTO;
import lk.ijse.lavishStyloo.dto.UserDTO;
import lk.ijse.lavishStyloo.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class EmployeeModel {
    public static EmployeeDTO findById(String nic) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee WHERE nic =?";
        ResultSet result = CrudUtil.crudUtil(sql, nic);
        return toDTO(result);

    }

    private static EmployeeDTO toDTO(ResultSet result) throws SQLException {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        if (result.next()) {
            employeeDTO.setNic(result.getString(1));
            employeeDTO.setFirst_name(result.getString(2));
            employeeDTO.setLast_name(result.getString(3));
            employeeDTO.setEmail(result.getString(4));
            employeeDTO.setCity(result.getString(5));
            employeeDTO.setLane(result.getString(6));
            employeeDTO.setStreet(result.getString(7));
            employeeDTO.setContact(result.getString(8));
            employeeDTO.setDateOfBirth(LocalDate.parse(result.getString(9)));
            employeeDTO.setGender(result.getString(10));
        }
        return employeeDTO;
    }

    public static List<EmployeeDTO> findEmployee() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee";
        ResultSet result = CrudUtil.crudUtil(sql);
        return toDTOList(result);
    }

    private static List<EmployeeDTO> toDTOList(ResultSet result) throws SQLException {
        List<EmployeeDTO> list = new ArrayList<>();

        while (result.next()) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setNic(result.getString(1));
            employeeDTO.setFirst_name(result.getString(2));
            employeeDTO.setLast_name(result.getString(3));
            employeeDTO.setEmail(result.getString(4));
            employeeDTO.setCity(result.getString(5));
            employeeDTO.setLane(result.getString(6));
            employeeDTO.setStreet(result.getString(7));
            employeeDTO.setContact(result.getString(8));
            employeeDTO.setDateOfBirth(LocalDate.parse(result.getString(9)));
            employeeDTO.setGender(result.getString(10));
            list.add(employeeDTO);
        }
        return list;
    }

    public static boolean delete(String nic) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE from employee where nic=?", nic);
    }


    public static List<EmployeeDTO> findEmployeeByLike(String searchText) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee where nic LIKE ? or first_name LIKE ? or last_name LIKE ? or contact LIKE ? or city LIKE ? or gender LIKE ?";
        String arg=searchText+"%";
        ResultSet result = CrudUtil.crudUtil(sql,arg,arg,arg,arg,arg,arg);
        return toDTOList(result);
    }

    public static boolean save(EmployeeDTO employeeDTO, UserDTO userDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {

            boolean save = save(employeeDTO);
            if (save){
                boolean saveUser = userSave(userDTO);
                if (saveUser){
                    connection.commit();
                }else {
                    connection.rollback();
                    return false;
                }
            }else {
                connection.rollback();
                return false;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }
        return true;
    }

    private static boolean userSave(UserDTO userDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO user VALUES (?,?,?)",
                userDTO.getUserName(),
                userDTO.getPassword(),
                userDTO.getNic()
                );

    }

    public static boolean save(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        String sql ="INSERT INTO employee VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        return CrudUtil.crudUtil(sql,
                employeeDTO.getNic(),
                employeeDTO.getFirst_name(),
                employeeDTO.getLast_name(),
                employeeDTO.getEmail(),
                employeeDTO.getCity(),
                employeeDTO.getLane(),
                employeeDTO.getStreet(),
                employeeDTO.getContact(),
                employeeDTO.getDateOfBirth(),
                employeeDTO.getGender(),
                employeeDTO.getRole()
                );
    }

    public static boolean update(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        String sql ="UPDATE employee SET first_name=?, last_name=?, email=?, city=?, lane=?, street=?, contact=?, dateOfBirth=?, gender=?, `role`=? WHERE nic = ?";
        return CrudUtil.crudUtil(sql,
                employeeDTO.getFirst_name(),
                employeeDTO.getLast_name(),
                employeeDTO.getEmail(),
                employeeDTO.getCity(),
                employeeDTO.getLane(),
                employeeDTO.getStreet(),
                employeeDTO.getContact(),
                employeeDTO.getDateOfBirth(),
                employeeDTO.getGender(),
                employeeDTO.getNic()
        );
    }

    public static String CountEmployee() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM  employee";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public static String CountAvailable() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM attendance a INNER JOIN employee e ON  e.nic=a.nic WHERE NOT e.role='ADMIN' OR e.role ='CASHIER' AND date = CURDATE() AND NOT e.nic=(SELECT bd.nic FROM booking b LEFT JOIN payment p on b.booking_id = p.booking_id INNER JOIN booking_details bd on b.booking_id = bd.booking_id WHERE b.date= CURDATE())";
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }
}
