package lk.ijse.lavishStyloo.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import lk.ijse.lavishStyloo.model.UserModel;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.sql.SQLException;

public class LoginFromController {
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;

    public void LoginOnAction(ActionEvent actionEvent) {
        try {
            if (UserModel.checkUserNamePassword(txtPassword.getText(),txtUserName.getText()).equals("ADMIN")){
                NavigationUtility.switchNavigation("/Admin/AdminDashboardFrom.fxml",actionEvent);
            }  else if (UserModel.checkUserNamePassword(txtPassword.getText(),txtUserName.getText()).equals("CASHIER")){
                NavigationUtility.switchNavigation("/Cashier/CashierDashboardFrom.fxml",actionEvent);
            }else {
                new Alert(Alert.AlertType.WARNING,"Check your user name password").show();
                txtUserName.clear();
                txtPassword.clear();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }
}
