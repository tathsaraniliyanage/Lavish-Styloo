package lk.ijse.lavishStyloo.model;

import lk.ijse.lavishStyloo.db.DBConnection;
import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.dto.OrderDTO;
import lk.ijse.lavishStyloo.dto.tm.OrderTm;
import lk.ijse.lavishStyloo.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrderModel {
    public static boolean placeOrder(ArrayList<OrderTm> list, OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean isSaved = setOder(orderDTO);
            if (isSaved) {
                boolean isSavedDetails = setOderDetails(list, orderDTO);
                if (isSavedDetails) {
                    boolean isUpdated = ProductModel.updateItems(list);
                    if (isUpdated) {
                        return true;
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    private static boolean setOderDetails(ArrayList<OrderTm> list, OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        for (OrderTm d : list) {
            boolean isSaved = CrudUtil.crudUtil("INSERT INTO order_details VALUES (?,?,?,?)",
                    d.getItemCode(),
                    orderDTO.getCust_oid(),
                    d.getPrice(),
                    d.getQty()
            );
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }

    private static boolean setOder(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO customer_order VALUES (?,?,?,?,?)",
                orderDTO.getCust_oid(),
                orderDTO.getCust_id(),
                orderDTO.getDate(),
                orderDTO.getTime(),
                orderDTO.getTotal()
        );
    }

    public static List<String> findDistinctYears() throws SQLException, ClassNotFoundException {
        List<String> list = new ArrayList<>();
        ResultSet resultSet = CrudUtil.crudUtil("SELECT DISTINCT YEAR(o.date)  FROM customer_order o UNION SELECT DISTINCT YEAR(s.date) FROM supplier_order s UNION SELECT DISTINCT YEAR(b.date) FROM booking b");
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public static String next() throws SQLException, ClassNotFoundException {
        List<String> ids = findID();
        String oldId = null;
        for (String id : ids) {
            oldId =id;
        }
        int lastIndex;
        try {
            String[] split = oldId.split("O00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "O001";
        }
        lastIndex++;
        return "O00" + lastIndex;
    }

    private static List<String> findID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.crudUtil("SELECT cust_oid from customer_order");
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }
}
