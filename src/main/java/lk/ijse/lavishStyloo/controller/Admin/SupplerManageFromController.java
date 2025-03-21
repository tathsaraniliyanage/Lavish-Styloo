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
import lk.ijse.lavishStyloo.dto.SupplierDTO;
import lk.ijse.lavishStyloo.dto.tm.SupplerTm;
import lk.ijse.lavishStyloo.dto.tm.TreatmentTm;
import lk.ijse.lavishStyloo.model.SupplierModel;
import lk.ijse.lavishStyloo.model.TreatmentModel;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class SupplerManageFromController implements Initializable {
    private static SupplerManageFromController controller;
    public TableColumn colCompany;
    public TableColumn colDealer;
    public TableColumn colId;
    public TableColumn colLocation;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colOption;
    public TableView tbl;
    public Text txtAllSuppliers;
    public Text btnText;
    public JFXButton btnClear;

    public SupplerManageFromController() {
        controller=this;
    }

    public static SupplerManageFromController getController() {
        return controller;
    }

    ObservableList<SupplerTm> list = FXCollections.observableArrayList();


    /*public void searchOnKeyReleased(KeyEvent keyEvent) {
        try {
            List<SupplerTm> list = TreatmentModel.loadAllTreatmentByLike(txtSearchText.getText());
            toProcess(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colDealer.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));
        tbl.setItems(list);

        setTableData();

    }

    public void setTableData() {
        setCount();
        this.list.clear();
        tbl.getItems().clear();
        try {
            List<SupplierDTO> all = SupplierModel.findAll();
            for (SupplierDTO dto:all){
                SupplerTm supplerTm = new SupplerTm();
                supplerTm.setSupplier_id(dto.getSupplier_id());
                supplerTm.setSupplier_name(dto.getSupplier_name());
                supplerTm.setCompany(dto.getCompany());
                supplerTm.setEmail(dto.getEmail());
                supplerTm.setContact(dto.getContact());
                supplerTm.setLocation(dto.getLocation());
                this.list.add(supplerTm);
            }
            toProcess(this.list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setCount() {
        try {
            txtAllSuppliers.setText(SupplierModel.countSupper());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void toProcess(ObservableList<SupplerTm> list) {
        tbl.setItems(list);
        tbl.refresh();

    }

    public void customerFromAddOnAction(ActionEvent actionEvent) {
        if (btnText.getText().equals("NEW")) {
            NavigationUtility.popupNavigation("Admin/SupplyAddFrom.fxml");
        } else {
            NavigationUtility.popupNavigation("Admin/SupplyUpdateFrom.fxml");
        }
    }

    public void CustomerFromClearOnAction(ActionEvent actionEvent) {
        tbl.getSelectionModel().clearSelection();
        btnClear.setVisible(false);
        btnText.setText("NEW");
    }

    public void tblOnMouseClick(MouseEvent mouseEvent) {
        btnText.setText("UPDATE");
        btnClear.setVisible(true);

        SupplerTm supplerTm = (SupplerTm) tbl.getSelectionModel().getSelectedItem();
        supplier_id = supplerTm.getSupplier_id();
    }
    public static String supplier_id;
}
