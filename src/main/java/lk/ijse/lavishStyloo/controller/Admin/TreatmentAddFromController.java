package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import lk.ijse.lavishStyloo.controller.Admin.TreatmentFromController;
import lk.ijse.lavishStyloo.dto.TreatmentDTO;
import lk.ijse.lavishStyloo.model.TreatmentModel;
import lk.ijse.lavishStyloo.util.NavigationUtility;
import lk.ijse.lavishStyloo.util.RegexUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TreatmentAddFromController implements Initializable {

    public JFXTextField txtTreatment;
    public JFXTextField txtPrice;
    public JFXTextField txtDescription;
    public JFXComboBox cmbCategory;
    public JFXButton btn;

    public void AddOnAction(ActionEvent actionEvent) {
        TreatmentDTO treatmentDTO = new TreatmentDTO();
        treatmentDTO.setTreat_id(getNext());
        treatmentDTO.setTreatment(txtTreatment.getText());
        treatmentDTO.setCategory(String.valueOf(cmbCategory.getValue()));
        treatmentDTO.setPrice(Double.parseDouble(txtPrice.getText()));
        treatmentDTO.setDescription(txtDescription.getText());
        try {
            boolean isSave = TreatmentModel.save(treatmentDTO);
            if (isSave) {
                TreatmentFromController.getController().loadAllTreatment();
                closeOnAction(actionEvent);
                new Alert(Alert.AlertType.CONFIRMATION, "ok").show();

            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "not").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private String getNext() {
        try {
            return TreatmentModel.getNext();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void closeOnAction(ActionEvent actionEvent) {
        NavigationUtility.close(actionEvent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCategory();
    }

    private void setCategory() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("HAIR TREATMENT");
        list.add("HAIR CUTS");
        list.add("HAIR STYLES");
        list.add("HAIR COLOURING");
        list.add("FULL MAKEUP");
        list.add("NAIL TREATMENTS");
        list.add("LASH LIFT & TINT");
        cmbCategory.setItems(list);
    }

    public void priceOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtPrice, "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$","-fx-text-fill: black");
    }

    public void treatmentOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtTreatment, "\\b([a-z]|[A-Z]|[\\s])+","-fx-text-fill: black");

    }

    public void descriptionOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtDescription, "\\b([a-z]|[A-Z]|[\\s])+","-fx-text-fill: black");

    }
}
