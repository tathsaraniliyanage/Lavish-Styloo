package lk.ijse.lavishStyloo.model;

import lk.ijse.lavishStyloo.dto.tm.SupperOrderDetailsTm;
import lk.ijse.lavishStyloo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class SupperOrderDetailsModel {
    public static List<SupperOrderDetailsTm> findCustomerOrderDetailsByOrderId(String supperOrderId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet= CrudUtil.crudUtil("SELECT p.product_code,p.product,s.price,s.qty FROM supplier_order_details s inner join product p on s.product_code = p.product_code where s.sup_oid=?",supperOrderId);
        List<SupperOrderDetailsTm> list=new ArrayList<>();

        while (resultSet.next()){
            SupperOrderDetailsTm tm=new SupperOrderDetailsTm();
            tm.setCode(resultSet.getString(1));
            tm.setProduct(resultSet.getString(2));
            tm.setPrice(resultSet.getString(3));
            tm.setQty(resultSet.getString(4));
            list.add(tm);
        }
        return list;
    }
}
