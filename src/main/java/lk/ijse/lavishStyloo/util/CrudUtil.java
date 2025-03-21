package lk.ijse.lavishStyloo.util;

import lk.ijse.lavishStyloo.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T crudUtil(String sql, Object... arg) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < arg.length; i++) {
            statement.setObject((i + 1), arg[i]);
        }
        if (sql.startsWith("SELECT")) {
            return (T) statement.executeQuery();
        } else {
            return (T) (Boolean) (statement.executeUpdate() > 0);
        }
    }
}

/*public class CrudUtil{
    public static <T> T crudUtil(String sql,Object...arg)throws SQLException,ClassNotFoundException{
      PreparedStatement statement =DbConnection.getInstance().getConnection.prepareStatement(sql);
      for(int i=0; i<arg.length;i++){
        statement.setObject((i+1),arg[i]);
        }
        if(sql.setStartsWith("SELECT")){

         return (T) statement.executeQuery();
        }else{
         return (T) (Boolean) (statement.executeUpdate()>0);
        }
    }
}*/