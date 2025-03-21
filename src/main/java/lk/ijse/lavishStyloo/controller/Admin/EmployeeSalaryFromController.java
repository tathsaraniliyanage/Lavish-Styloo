package lk.ijse.lavishStyloo.controller.Admin;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.dto.EmployeeDTO;
import lk.ijse.lavishStyloo.dto.SalaryDTO;
import lk.ijse.lavishStyloo.dto.tm.SalaryTm;
import lk.ijse.lavishStyloo.model.AttendanceModel;
import lk.ijse.lavishStyloo.model.EmployeeModel;
import lk.ijse.lavishStyloo.model.SalaryModel;
import lk.ijse.lavishStyloo.util.DateTimeUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeSalaryFromController implements Initializable {

    public JFXTextField lblSalary;
    ObservableList<SalaryTm> list = FXCollections.observableArrayList();
    @FXML
    private AnchorPane pane;
    @FXML
    private Text txtNetTotal;
    @FXML
    private JFXTextField LblBones;
    @FXML
    private Text txtAttendance;
    @FXML
    private Text txtAddress12;
    @FXML
    private Text txtAddress3;
    @FXML
    private Text txtAddresse;
    @FXML
    private JFXTextField LblCode;
    @FXML
    private Text txtAddress;
    @FXML
    private Text txtName;
    @FXML
    private Text txtContact;
    @FXML
    private JFXTextField txtSearchText;
    @FXML
    private TableView tbl;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colAddress;
    @FXML
    private TableColumn colMail;
    @FXML
    private TableColumn colContact;
    @FXML
    private TableColumn colBones;
    @FXML
    private TableColumn colSalary;
    @FXML
    private JFXButton btnClear;

    @FXML
    void btnTotalOnAction(ActionEvent event) {
        try {

            SalaryDTO salaryDTO = new SalaryDTO();
            salaryDTO.setSalary(Double.parseDouble(lblSalary.getText()));
            salaryDTO.setBonus(Double.parseDouble(LblBones.getText()));
            salaryDTO.setS_id(getNextId());
            salaryDTO.setDate(DateTimeUtil.dateNow());
            salaryDTO.setTime(DateTimeUtil.timeNow());
            salaryDTO.setNic(LblCode.getText());
            boolean saved = SalaryModel.save(salaryDTO);
            if (saved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "something wrong ").show();
            }
        } catch (SQLException | ClassNotFoundException |NullPointerException throwables) {
            //throwables.printStackTrace();
        }
    }

    private String getNextId() throws SQLException, ClassNotFoundException {
        return SalaryModel.getNext();
    }

    @FXML
    void employeeFromSearchOnKeyReleased(KeyEvent event) {
        try {
            List<SalaryTm> salaryTms = SalaryModel.findSalaryByLike(txtSearchText.getText());
            toProcess(salaryTms);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void lblNicOnKeyReleased(KeyEvent event) {
        try {
            boolean isAlReadyExist = SalaryModel.isExsitThisMonth(LblCode.getText());
            if (!isAlReadyExist) {
                EmployeeDTO employeeDTO = EmployeeModel.findById(LblCode.getText());
                txtAddress.setText(employeeDTO.getCity() + " ," + employeeDTO.getStreet() + " ," + employeeDTO.getLane());
                txtContact.setText(employeeDTO.getEmail() + " / " + employeeDTO.getContact());
                txtName.setText(employeeDTO.getFirst_name() + " " + employeeDTO.getLast_name());
                if (employeeDTO.getNic() != null) {
                    String count = AttendanceModel.getEmployee(employeeDTO.getNic());
                    txtAttendance.setText(count);
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "This employee already got a salary").show();
                LblCode.clear();
                txtAddress.setText("");
                txtContact.setText("");
                txtName.setText("");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colBones.setCellValueFactory(new PropertyValueFactory<>("bones"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colId.setCellValueFactory(new PropertyValueFactory<>("nic"));
        tbl.setItems(list);
        loadAllSalary();
    }

    private void loadAllSalary() {
        try {
            List<SalaryTm> salaryTms = SalaryModel.findSalary();
            toProcess(salaryTms);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void toProcess(List<SalaryTm> salaryTms) {
        tbl.getItems().clear();
        list.clear();
        tbl.getItems().addAll(salaryTms);
    }

    public void lblSalaryOnKeyReleased(KeyEvent keyEvent) {
        setNetTotal();
    }

    private void setNetTotal() {
        if (!txtAttendance.getText().equals("NOTHING ATTENDANCE")) {
            txtNetTotal.setText("0.0");
            int attendance = Integer.parseInt(txtAttendance.getText());
            double bones;
            try {
                bones = Double.parseDouble(LblBones.getText() == null | LblBones.getText().isEmpty() ? "0.0" : LblBones.getText());
            } catch (NumberFormatException e) {
                bones = 0.0;
            }
            double salary;
            try {
                salary = Double.parseDouble(lblSalary.getText() == null | lblSalary.getText().isEmpty() ? "0.0" : lblSalary.getText());
            } catch (NumberFormatException e) {
                salary = 0.0;
            }
            double total = 0;
            total = salary * attendance + bones;
            txtNetTotal.setText(String.valueOf(total));
        }
    }

    public void lblBonesOnKeyReleased(KeyEvent keyEvent) {
        setNetTotal();
    }
}
