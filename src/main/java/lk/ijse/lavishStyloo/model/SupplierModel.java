package lk.ijse.lavishStyloo.model;

import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.dto.SupplierDTO;
import lk.ijse.lavishStyloo.dto.tm.SupperOrderTm;
import lk.ijse.lavishStyloo.dto.tm.TreatmentTm;
import lk.ijse.lavishStyloo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class SupplierModel {
    public static boolean save(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO supplier VALUES (?,?,?,?,?,?)",
                supplierDTO.getSupplier_id(),
                supplierDTO.getSupplier_name(),
                supplierDTO.getCompany(),
                supplierDTO.getEmail(),
                supplierDTO.getContact(),
                supplierDTO.getLocation()

        );
    }

    public static SupplierDTO findSupplierById(String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM supplier WHERE supplier_id =?", id);
        return toDTO(set);
    }

    private static SupplierDTO toDTO(ResultSet set) throws SQLException {
        SupplierDTO supplierDTO = new SupplierDTO();
        if (set.next()) {
            supplierDTO.setSupplier_id(set.getString(1));
            supplierDTO.setSupplier_name(set.getString(2));
            supplierDTO.setCompany(set.getString(3));
            supplierDTO.setEmail(set.getString(4));
            supplierDTO.setContact(set.getString(5));
            supplierDTO.setLocation(set.getString(6));
        }
        return supplierDTO;
    }

    public static boolean update(SupplierDTO supplierDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE supplier SET supplier_name=?,company=?,email=?,contact=?,location=? WHERE supplier_id=?"
                , supplierDTO.getSupplier_name()
                , supplierDTO.getCompany()
                , supplierDTO.getEmail()
                , supplierDTO.getContact()
                , supplierDTO.getLocation()
                , supplierDTO.getSupplier_id()
        );
    }

    public static List<SupplierDTO> findAll() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.crudUtil("SELECT * FROM supplier");
        return toDTOs(set);
    }
    private static List<SupplierDTO> toDTOs(ResultSet set) throws SQLException {
        List<SupplierDTO> list=new ArrayList<>();
        while (set.next()) {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setSupplier_id(set.getString(1));
            supplierDTO.setSupplier_name(set.getString(2));
            supplierDTO.setCompany(set.getString(3));
            supplierDTO.setEmail(set.getString(4));
            supplierDTO.setContact(set.getString(5));
            supplierDTO.setLocation(set.getString(6));
            list.add(supplierDTO);
        }
        return list;
    }


    public static String countSupper() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*)from supplier");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public static boolean delete(String supplier_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE  FROM supplier WHERE  supplier_id=?",supplier_id);
    }

    public static String nextId() throws SQLException, ClassNotFoundException {
        List<SupplierDTO> ids = findAll();
        String oldId = null;
        for (SupplierDTO supplierDTO : ids) {
            oldId = supplierDTO.getSupplier_id();
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
}
