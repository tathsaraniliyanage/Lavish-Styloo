package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.dto.AttendanceDTO;
import lk.ijse.lavishStyloo.dto.EmployeeDTO;
import lk.ijse.lavishStyloo.dto.tm.AttendanceTm;
import lk.ijse.lavishStyloo.model.AttendanceModel;
import lk.ijse.lavishStyloo.model.EmployeeModel;
import lk.ijse.lavishStyloo.util.DateTimeUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class EmployeeAttendanceFromController implements Initializable {

    public Text txtDetailsFistName;
    public Text txtDetailsNIC;
    public Text txtDetailsInTime;
    public Text txtDetailsOutIme;
    public Text txtDetailsDate;

    public TableView tblAttendance;
    public TableColumn colNic;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colDate;
    public TableColumn colInTime;
    public TableColumn colOutTime;

    public Text txtAllTodayAttendance;

    public JFXTextField txtSearchText;

    public JFXTextField txtEmployeeNic;
    public Text txtName;
    public Text txtNIC;
    public DatePicker txtDate;
    public JFXRadioButton rBtnToDayAttendance;
    public JFXRadioButton rBtnSelectedAttendance;
    public JFXButton btnAdd;

    ObservableList<AttendanceTm> list = FXCollections.observableArrayList();


    public void employeeNicOnKeyReleased(KeyEvent keyEvent) {
        EmployeeDTO employeeDTO = null;
        try {
            employeeDTO = EmployeeModel.findById(txtEmployeeNic.getText());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        if (employeeDTO.getNic() != null) {
            btnAdd.setDisable(false);
            txtNIC.setText(employeeDTO.getNic());
            txtName.setText(employeeDTO.getFirst_name() + " " + employeeDTO.getLast_name());
        } else {
            txtNIC.setText("");
            txtName.setText("");
            btnAdd.setDisable(true);
        }
    }

    public void addOnAction(ActionEvent actionEvent) {

        btnAdd.setDisable(true);

        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setNic(txtNIC.getText());
        attendanceDTO.setDate(DateTimeUtil.dateNow());
        attendanceDTO.setIn_time(DateTimeUtil.timeNow());
        attendanceDTO.setOut_time("00:00:00");

        try {
            AttendanceDTO dto = AttendanceModel.findAttendanceByDateAndNic(txtNIC.getText(), DateTimeUtil.dateNow());
            if (dto.getNic() == null) {
                boolean attendanceSave = AttendanceModel.save(attendanceDTO);
                if (attendanceSave) {
                    txtName.setText("");
                    txtNIC.setText("");
                    txtEmployeeNic.clear();
                    loadTodayAttendance();

                }
            } else {
                txtEmployeeNic.clear();
                new Alert(Alert.AlertType.WARNING, "Employee is Exist ! ").show();
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void attendanceFromTblOnClick(MouseEvent mouseEvent) {
        AttendanceTm tm = (AttendanceTm) tblAttendance.getSelectionModel().getSelectedCells();
        txtDetailsFistName.setText(tm.getName());
        txtDetailsNIC.setText(tm.getNic());
        txtDetailsDate.setText(tm.getDate());
        txtDetailsInTime.setText(tm.getInTime());
        txtDetailsOutIme.setText(tm.getOutTime());


    }

    public void attendanceFromSearchOnKeyReleased(KeyEvent keyEvent) {
        try {
            String date = String.valueOf(txtDate.getValue());
            if (txtDate.getValue() == null)
                date = DateTimeUtil.dateNow();
            List<AttendanceTm> list = AttendanceModel.findByDateAndNameAndNic(date, txtSearchText.getText());
            toProcess(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void loadTodayAttendance() {
        setAttendanceCount(DateTimeUtil.dateNow());
        try {
            List<AttendanceTm> list = AttendanceModel.findByDate(DateTimeUtil.dateNow());
            toProcess(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void toProcess(List<AttendanceTm> list) {
        /**
         * Set Details to employee attendance details box
         * */
        for (AttendanceTm tm : list) {
            txtDetailsFistName.setText(tm.getName());
            txtDetailsNIC.setText(tm.getNic());
            txtDetailsDate.setText(tm.getDate());
            txtDetailsInTime.setText(tm.getInTime());
            txtDetailsOutIme.setText(tm.getOutTime());
        }
        /**
         * clear table and Observable list
         * */
        tblAttendance.getItems().clear();
        this.list.clear();
        /**
         * set data to Observable list
        * */
        tblAttendance.getItems().addAll(list);
        tblAttendance.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colInTime.setCellValueFactory(new PropertyValueFactory<>("inTime"));
        colOutTime.setCellValueFactory(new PropertyValueFactory<>("outTime"));
        tblAttendance.setItems(list);

        loadTodayAttendance();
        setAttendanceCount(DateTimeUtil.dateNow());
    }

    private void setAttendanceCount(String date) {
        try {
            txtAllTodayAttendance.setText(AttendanceModel.countAttendanceByDate(date));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dateOnAction(ActionEvent actionEvent) {
        setSelectedDateAttendance();
    }

    private void setSelectedDateAttendance() {
        String date = String.valueOf(txtDate.getValue());
        if (txtDate.getValue() == null)
            date = DateTimeUtil.dateNow();
        setAttendanceCount(date);
        try {
            List<AttendanceTm> list = AttendanceModel.findByDate(date);
            toProcess(list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void rBtnToDayAttendanceOnAction(ActionEvent actionEvent) {
        setDate();
    }

    private void setDate() {
        if (rBtnSelectedAttendance.isSelected()) {
            txtDate.setDisable(false);
            setSelectedDateAttendance();
        }
        if (rBtnToDayAttendance.isSelected()) {
            txtDate.setDisable(true);
            loadTodayAttendance();
        }

    }

    public void rBtnSelectedAttendanceOnAction(ActionEvent actionEvent) {
        setDate();
    }
}
