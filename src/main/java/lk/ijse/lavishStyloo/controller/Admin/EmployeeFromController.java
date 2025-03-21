package lk.ijse.lavishStyloo.controller.Admin;


import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.model.EmployeeModel;
import lk.ijse.lavishStyloo.model.SalaryModel;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeFromController implements Initializable {
    public JFXRadioButton employeeSalary;
    public AnchorPane pane;
    public JFXRadioButton employee;
    public Text txtSalaryCount;
    public Text txtEmployeesCount;

    private static EmployeeFromController controller;
    public EmployeeFromController() {
        controller = this;
    }
    public static EmployeeFromController getController() {
        return controller;
    }

    public void employeeSalaryOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(pane, "/Admin/EmployeeSalaryFrom.fxml");
    }

    public void employeeOnAction(ActionEvent actionEvent) {
        NavigationUtility.onTheTopNavigation(pane, "/Admin/EmployeeChaildFrom.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NavigationUtility.onTheTopNavigation(pane, "/Admin/EmployeeChaildFrom.fxml");
        setCount();
    }

    public void setCount() {
        try {
            txtEmployeesCount.setText(EmployeeModel.CountEmployee() + "+");
            txtSalaryCount.setText(SalaryModel.getCount() + "+");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
