package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import lk.ijse.lavishStyloo.controller.Admin.SupplerManageFromController;
import lk.ijse.lavishStyloo.dto.SupplierDTO;
import lk.ijse.lavishStyloo.model.SupplierModel;
import lk.ijse.lavishStyloo.model.TreatmentModel;
import lk.ijse.lavishStyloo.util.NavigationUtility;
import lk.ijse.lavishStyloo.util.RegexUtil;

import java.sql.SQLException;

public class SupplyAddFromController {
    public JFXTextField txtSupperName;
    public JFXTextField txtCompany;
    public JFXTextField txtMail;
    public JFXTextField txtLocation;
    public JFXTextField txtContact;
    public JFXButton btn;

    public void customerAddOnAction(ActionEvent actionEvent) {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setSupplier_id(nextId());
        supplierDTO.setSupplier_name(txtSupperName.getText());
        supplierDTO.setCompany(txtCompany.getText());
        supplierDTO.setContact(txtContact.getText());
        supplierDTO.setEmail(txtMail.getText());
        supplierDTO.setLocation(txtLocation.getText());

        try {
            boolean isSave= SupplierModel.save(supplierDTO);
            if (isSave){
                closeOnAction(actionEvent);
                SupplerManageFromController.getController().setTableData();
                new Alert(Alert.AlertType.CONFIRMATION,"ok").show();

            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"not").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private String nextId() {
        try {
            return SupplierModel.nextId();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void closeOnAction(ActionEvent actionEvent) {
        NavigationUtility.close(actionEvent);
    }

    public void mailOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtMail, "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{3,4}$","-fx-text-fill: black");
    }

    public void companyOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtCompany, "\\b([a-z]|[A-Z]|[\\s])+","-fx-text-fill: black");

    }

    public void locationOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtLocation, "\\b([a-z]|[A-Z]|[\\s])+","-fx-text-fill: black");

    }

    public void contactOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtContact, "0((11)|(7(7|0|8|4|9|1|[3-7]))|(3[1-8])|(4(1|5|7))|(5(1|2|4|5|7))|(6(3|[5-7]))|([8-9]1))[0-9]{7}","-fx-text-fill: black");

    }

    public void nameOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtSupperName, "\\b([a-z]|[A-Z]|[\\s])+","-fx-text-fill: black");

    }
}
