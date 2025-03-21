package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.dto.tm.TreatmentTm;
import lk.ijse.lavishStyloo.model.TreatmentModel;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TreatmentFromController implements Initializable {
    public static String treatmentId;
    private static TreatmentFromController controller;
    public JFXTextField txtSearchText;
    public TableView tblTreatment;
    public TableColumn colId;
    public TableColumn colTreatment;
    public TableColumn colPrice;
    public TableColumn colCategory;
    public TableColumn colDescription;
    public TableColumn colOption;

    public JFXComboBox cmbCategory;
    public Text txtAllTreatment;
    public Text btnText;
    public JFXButton btnClear;
    ObservableList<TreatmentTm> list = FXCollections.observableArrayList();

    public TreatmentFromController() {
        controller = this;
    }

    public static TreatmentFromController getController() {
        return controller;
    }

    public void searchOnKeyReleased(KeyEvent keyEvent) {
        TreatmentTm tm=new TreatmentTm();

        try {
            List<TreatmentTm> list = TreatmentModel.loadAllTreatmentByLike(txtSearchText.getText());
            toProcess(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTreatment.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("remove"));
        tblTreatment.setItems(list);

        loadCategory();

        setCount();
    }

    private void setCount() {
        try {
            txtAllTreatment.setText(TreatmentModel.countTreatment());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void cmbCatOnAction(ActionEvent actionEvent) {
        if (cmbCategory.getValue() != null) {
            try {
                List<TreatmentTm> list = TreatmentModel.findTreatmentByCategory(String.valueOf(cmbCategory.getValue()));
                toProcess(list);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else if (cmbCategory.getValue().equals("LOAD ALL")) {
            loadAllTreatment();
        }
    }

    private void loadCategory() {
        try {
            List<String> list = TreatmentModel.loadCategory();
            cmbCategory.getItems().add("LOAD ALL");
            cmbCategory.setValue("LOAD ALL");
            cmbCategory.getItems().addAll(list);
            loadAllTreatment();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void loadAllTreatment() {
        try {
            List<TreatmentTm> list = TreatmentModel.loadAllTreatment();

            toProcess(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void toProcess(List<TreatmentTm> list) {
        this.list.clear();
        this.list.addAll(list);
    }

    public void customerFromAddOnAction(ActionEvent actionEvent) {
        if (btnText.getText().equals("NEW")) {
            NavigationUtility.popupNavigation("Admin/TreatmentAddFrom.fxml");
        } else {
            NavigationUtility.popupNavigation("Admin/TreatmentUpdateFrom.fxml");
        }
    }

    public void CustomerFromClearOnAction(ActionEvent actionEvent) {
        tblTreatment.getSelectionModel().clearSelection();
        btnClear.setVisible(false);
        btnText.setText("NEW");
    }

    public void tblOnMouseClick(MouseEvent mouseEvent) {
        btnText.setText("UPDATE");
        btnClear.setVisible(true);

        TreatmentTm treatmentTm = (TreatmentTm) tblTreatment.getSelectionModel().getSelectedItem();
        treatmentId = treatmentTm.getId();
    }
}
