package lk.ijse.lavishStyloo.model;

import lk.ijse.lavishStyloo.dto.TreatmentDTO;
import lk.ijse.lavishStyloo.dto.tm.TreatmentTm;
import lk.ijse.lavishStyloo.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class TreatmentModel {
    public static List<TreatmentTm> loadAllTreatment() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * From treatment");
        List<TreatmentTm> list = new ArrayList<>();
        while (resultSet.next()) {
            TreatmentTm treatmentTm = new TreatmentTm();
            treatmentTm.setId(resultSet.getString(1));
            treatmentTm.setPrice(resultSet.getString(2));
            treatmentTm.setCategory(resultSet.getString(3));
            treatmentTm.setTreatment(resultSet.getString(4));
            treatmentTm.setDescription(resultSet.getString(5));
            list.add(treatmentTm);
        }
        return list;
    }

    public static List<String> loadCategory() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT category From treatment");
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }

    public static List<TreatmentTm> findTreatmentByCategory(String value) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * From treatment");
        return setTm(resultSet);
    }

    private static List<TreatmentTm> setTm(ResultSet resultSet) throws SQLException {
        List<TreatmentTm> list = new ArrayList<>();
        while (resultSet.next()) {
            TreatmentTm treatmentTm = new TreatmentTm();
            treatmentTm.setId(resultSet.getString(1));
            treatmentTm.setPrice(resultSet.getString(2));
            treatmentTm.setCategory(resultSet.getString(3));
            treatmentTm.setTreatment(resultSet.getString(4));
            treatmentTm.setDescription(resultSet.getString(5));
            list.add(treatmentTm);
        }
        return list;
    }

    public static List<TreatmentTm> loadAllTreatmentByLike(String text) throws SQLException, ClassNotFoundException {

        String arg=text+"%";

        ResultSet resultSet = CrudUtil.crudUtil("SELECT * From treatment WHERE treat_id LIKE ? OR price LIKE ? OR category LIKE ? OR treatment LIKE ? OR description LIKE ? "
        ,arg,arg,arg,arg,arg
        );
        List<TreatmentTm> list = new ArrayList<>();
        while (resultSet.next()) {
            TreatmentTm treatmentTm = new TreatmentTm();
            treatmentTm.setId(resultSet.getString(1));
            treatmentTm.setPrice(resultSet.getString(2));
            treatmentTm.setCategory(resultSet.getString(3));
            treatmentTm.setTreatment(resultSet.getString(4));
            treatmentTm.setDescription(resultSet.getString(5));
            list.add(treatmentTm);
        }
        return list;
    }

    public static String countTreatment() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(treat_id) from treatment");
        List<TreatmentTm> list = new ArrayList<>();
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    public static boolean save(TreatmentDTO treatmentDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO treatment VALUES (?,?,?,?,?)"
                ,treatmentDTO.getTreat_id()
                ,treatmentDTO.getPrice()
                ,treatmentDTO.getCategory()
                ,treatmentDTO.getTreatment()
                ,treatmentDTO.getDescription()
        );
    }

    public static boolean update(TreatmentDTO treatmentDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE treatment SET price=?,category=?,treatment=?,description=? WHERE treat_id=?"
                ,treatmentDTO.getPrice()
                ,treatmentDTO.getCategory()
                ,treatmentDTO.getTreatment()
                ,treatmentDTO.getDescription()
                ,treatmentDTO.getTreat_id()
        );
    }

    public static TreatmentDTO findTreatmentById(String treatmentId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * From treatment where treat_id=?",treatmentId);

        TreatmentDTO dto = new TreatmentDTO();
        if (resultSet.next()) {
            dto.setTreat_id(resultSet.getString(1));
            dto.setPrice(resultSet.getDouble(2));
            dto.setCategory(resultSet.getString(3));
            dto.setTreatment(resultSet.getString(4));
            dto.setDescription(resultSet.getString(5));

        }
        return dto;
    }

    public static String getNext() throws SQLException, ClassNotFoundException {
        List<TreatmentTm> list = loadAllTreatment();
        String oldId = null;
        for (TreatmentTm tm : list) {
            oldId = tm.getId();
        }
        int lastIndex;
        try {
            String[] split = oldId.split("T00");
            lastIndex = Integer.parseInt(split[1]);
        } catch (NullPointerException nullPointerException) {
            return "T001";
        }
        lastIndex++;
        return "T00" + lastIndex;
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
       return CrudUtil.crudUtil("DELETE from treatment where treat_id=?",id);
    }
}
