package lk.ijse.lavishStyloo.model;

import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.dto.tm.OrderTm;
import lk.ijse.lavishStyloo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ProductModel {
    public static List<ProductDTO> findProduct() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * from product ");
        return toDTOs(resultSet);
    }

    private static List<ProductDTO> toDTOs(ResultSet resultSet) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        while (resultSet.next()) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProduct_code(resultSet.getString(1));
            productDTO.setProduct(resultSet.getString(2));
            productDTO.setDescription(resultSet.getString(3));
            productDTO.setUnit_price(resultSet.getString(4));
            productDTO.setQty(resultSet.getString(5));
            productDTO.setImg(resultSet.getString(6));
            list.add(productDTO);
        }
        return list;
    }
    private static ProductDTO toDTO(ResultSet resultSet) throws SQLException {

        ProductDTO productDTO = new ProductDTO();
        if (resultSet.next()) {
            productDTO.setProduct_code(resultSet.getString(1));
            productDTO.setProduct(resultSet.getString(2));
            productDTO.setDescription(resultSet.getString(3));
            productDTO.setUnit_price(resultSet.getString(4));
            productDTO.setQty(resultSet.getString(5));
            productDTO.setImg(resultSet.getString(6));
        }
        return productDTO;
    }

    public static String MaxProductByPrice() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT MAX(unit_price) from product");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public static String MinProductByPrice() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT MIN(unit_price) from product");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public static List<ProductDTO> betweenProductByPrice(String price) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * from product where unit_price between ( SELECT MIN(unit_price) from product) and ? ", price);
        return toDTOs(resultSet);
    }

    public static List<ProductDTO> findProductByLike(String searchText) throws SQLException, ClassNotFoundException {
        String args = searchText + "%";
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * from product where product LIKE ? OR  unit_price LIKE  ? OR  product_code LIKE  ? OR qty LIKE ?", args, args, args, args);
        return toDTOs(resultSet);
    }

    public static List<ProductDTO> findProductsByCode(String code) throws SQLException, ClassNotFoundException {
        String args = code + "%";
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * from product where  product_code LIKE  ?", args);
        return toDTOs(resultSet);
    }
    public static ProductDTO findProductByCode(String code) throws SQLException, ClassNotFoundException {
        String args = code + "%";
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * from product where  product_code LIKE  ?", args);
        return toDTO(resultSet);
    }

    public static boolean updateItems(ArrayList<OrderTm> list) throws SQLException, ClassNotFoundException {
        for (OrderTm d : list) {
            boolean isSaved = CrudUtil.crudUtil("UPDATE product SET qty=qty-? WHERE product_code=?",
                    d.getQty(),
                    d.getItemCode()
            );
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }

    public static boolean save(ProductDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO  product VALUES (?,?,?,?,?,?)"
                , dto.getProduct_code()
                , dto.getProduct()
                , dto.getDescription()
                , dto.getUnit_price()
                , dto.getQty()
                , dto.getImg()
        );
    }

    public static boolean update(ProductDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE product SET product=?,description=?,unit_price=?,img=? WHERE product_code=?"
                , dto.getProduct()
                , dto.getDescription()
                , dto.getUnit_price()
                , dto.getImg()
                , dto.getProduct_code()
        );
    }

    public static boolean updateSupperItems(ArrayList<OrderTm> list) throws SQLException, ClassNotFoundException {
        for (OrderTm d : list) {
            boolean isSaved = CrudUtil.crudUtil("UPDATE product SET qty=qty+? WHERE product_code=?",
                    d.getQty(),
                    d.getItemCode()
            );
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }

    public static String nextId() throws SQLException, ClassNotFoundException {
        List<ProductDTO> ids = findProduct();
        String oldId = null;
        for (ProductDTO dto : ids) {
            oldId = dto.getProduct_code();
        }
        int lastIndex;
        try {
            String[] split = oldId.split("P00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "P001";
        }
        lastIndex++;
        return "P00" + lastIndex;
    }

    public static boolean delete(String product_code) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE from product where product_code=?",product_code);
    }

    public static String CountByQTY() throws SQLException, ClassNotFoundException {
        String sql = "SELECT  COUNT(*) FROM product WHERE qty='0'";
        ResultSet result = CrudUtil.crudUtil(sql);
        if (result.next()) {
            return result.getString(1);
        }
        return "0";
    }
}
