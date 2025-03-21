package lk.ijse.lavishStyloo.model;

import lk.ijse.lavishStyloo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserModel {
    public static String checkUserNamePassword(String user, String password) throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.crudUtil("SELECT e.role FROM user u inner join employee e on u.nic=e.nic where u.password=? && u.userName=?",user,password);
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "404";
    }
}
