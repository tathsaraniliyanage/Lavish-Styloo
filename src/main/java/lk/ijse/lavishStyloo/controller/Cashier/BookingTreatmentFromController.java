package lk.ijse.lavishStyloo.controller.Cashier;

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
import lk.ijse.lavishStyloo.dto.tm.TreatmentTm;
import lk.ijse.lavishStyloo.model.TreatmentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class BookingTreatmentFromController implements Initializable {
    public TableView tblOrder;
    public TableColumn colId;
    public TableColumn colTreatment;
    public TableColumn colPrice;
    public TableColumn colCategory;
    public TableColumn colDescription;
    public TableColumn colSelected;
    public JFXTextField txtSearchText;
    public JFXComboBox cmbCategory;

    ObservableList<TreatmentTm> list = FXCollections.observableArrayList();

    public void SearchOnKeyReleased(KeyEvent keyEvent) {

    }

    public void cmbCatOnAction(ActionEvent actionEvent) {
        if (cmbCategory.getValue() != null) {
            try {
                List<TreatmentTm> list = TreatmentModel.findTreatmentByCategory(String.valueOf(cmbCategory.getValue()));
                toProcess(list);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else if (cmbCategory.getValue().equals("LOAD ALL")){
            loadAllTreatment();
        }

    }

    private void toProcess(List<TreatmentTm> list) {

        this.list.clear();
        this.list.addAll(list);
        ObservableList<TreatmentTm> items = tblOrder.getItems();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTreatment.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSelected.setCellValueFactory(new PropertyValueFactory<>("tick"));
        tblOrder.setItems(list);

       // loadAllTreatment();
        loadCategory();
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

    private void loadAllTreatment() {
        try {
            List<TreatmentTm> list = TreatmentModel.loadAllTreatment();
            toProcess(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
