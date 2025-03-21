package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.controller.Admin.SupplerManageFromController;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.dto.SupplierDTO;
import lk.ijse.lavishStyloo.dto.TreatmentDTO;
import lk.ijse.lavishStyloo.model.ProductModel;
import lk.ijse.lavishStyloo.model.SupplierModel;
import lk.ijse.lavishStyloo.model.TreatmentModel;
import lk.ijse.lavishStyloo.util.NavigationUtility;
import lk.ijse.lavishStyloo.util.RegexUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupplyUpdateFromController implements Initializable {
    public JFXTextField txtSupperName;
    public JFXTextField txtCompany;
    public JFXTextField txtMail;
    public JFXTextField txtLocation;
    public JFXTextField txtContact;
    public Text txtId;
    public JFXButton btn;

    public void customerAddOnAction(ActionEvent actionEvent) {
        SupplierDTO supplierDTO = new SupplierDTO();
        supplierDTO.setSupplier_id(txtId.getText());
        supplierDTO.setSupplier_name(txtSupperName.getText());
        supplierDTO.setCompany(txtCompany.getText());
        supplierDTO.setContact(txtContact.getText());
        supplierDTO.setEmail(txtMail.getText());
        supplierDTO.setLocation(txtLocation.getText());

        try {
            boolean isSave= SupplierModel.update(supplierDTO);
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


    public void closeOnAction(ActionEvent actionEvent) {
        NavigationUtility.close(actionEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSupplierById(SupplerManageFromController.supplier_id);
    }

    private void loadSupplierById(String id) {
        try {
            SupplierDTO dto = SupplierModel.findSupplierById(id);
            txtCompany.setText(dto.getCompany());
            txtContact.setText(dto.getContact());
            txtId.setText(dto.getSupplier_id());
            txtLocation.setText(dto.getLocation());
            txtMail.setText(dto.getEmail());
            txtSupperName.setText(dto.getSupplier_name());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
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
