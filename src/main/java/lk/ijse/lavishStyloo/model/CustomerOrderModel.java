package lk.ijse.lavishStyloo.model;

import lk.ijse.lavishStyloo.dto.tm.CustomerOrderDetailsTm;
import lk.ijse.lavishStyloo.dto.tm.CustomerOrderTm;
import lk.ijse.lavishStyloo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class CustomerOrderModel {
    public static List<CustomerOrderTm> findCustomerOrders() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT co.cust_oid,c.cust_id,c.first_name,c.last_name,co.date,co.time,co.total,COUNT(od.cust_oid) FROM customer_order co inner join customer c on co.cust_id = c.cust_id inner join order_details od on co.cust_oid = od.cust_oid;");
        return toTm(resultSet);
    }

    private static List<CustomerOrderTm> toTm(ResultSet resultSet) throws SQLException {
        List<CustomerOrderTm> list = new ArrayList<>();
        while (resultSet.next()) {
            CustomerOrderTm tm = new CustomerOrderTm();
            tm.setCustomerOrderId(resultSet.getString(1));
            tm.setCustomerId(resultSet.getString(2));
            tm.setName(resultSet.getString(3) + " " + resultSet.getString(4));
            tm.setDate(resultSet.getString(5));
            tm.setTime(resultSet.getString(6));
            tm.setTotal(resultSet.getString(7));
            tm.setCount(resultSet.getString(8));
            list.add(tm);
        }
        return list;
    }

    public static List<CustomerOrderTm> findCustomerOrdersByDate(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT co.cust_oid,c.cust_id,c.first_name,c.last_name,co.date,co.time,co.total,COUNT(od.cust_oid) FROM customer_order co inner join customer c on co.cust_id = c.cust_id inner join order_details od on co.cust_oid = od.cust_oid where co.date=?",date);
        return toTm(resultSet);
    }

    public static String CountCustomerOrder() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(cust_oid) from customer_order");
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "0";
    }

    public static String CountCustomerOrderByDate() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(cust_oid) from customer_order where date=CURDATE()");
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return "0";
    }

    public static List<CustomerOrderDetailsTm> findCustomerOrderDetailsByOrderId(String customerOrderId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT o.product_code, p.product, o.price, o.qty from order_details o inner join product p on o.product_code=p.product_code where cust_oid=?",customerOrderId);
        return toODTm(resultSet);
    }
    private static List<CustomerOrderDetailsTm> toODTm(ResultSet resultSet) throws SQLException {
        List<CustomerOrderDetailsTm> list = new ArrayList<>();
        while (resultSet.next()) {
            CustomerOrderDetailsTm tm = new CustomerOrderDetailsTm();
            tm.setCode(resultSet.getString(1));
            tm.setProduct(resultSet.getString(2));
            tm.setPrice(resultSet.getString(3) );
            tm.setQty(resultSet.getString(4));
            list.add(tm);
        }
        return list;
    }

    public static List<CustomerOrderTm> findCustomerOrdersByLike(String text) throws SQLException, ClassNotFoundException {
        String arg=text+"%";
        ResultSet resultSet = CrudUtil.crudUtil("SELECT co.cust_oid,c.cust_id,c.first_name,c.last_name,co.date,co.time,co.total,COUNT(od.cust_oid) FROM customer_order co inner join customer c on co.cust_id = c.cust_id inner join order_details od on co.cust_oid = od.cust_oid where co.cust_oid LIKE ?  or c.first_name LIKE ? or c.last_name LIKE ? or co.date LIKE ? or co.time LIKE ? or co.cust_id LIKE ? GROUP BY co.cust_oid;",arg,arg,arg,arg,arg,arg);
        return toTm(resultSet);
    }
}
