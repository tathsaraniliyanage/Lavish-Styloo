package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.model.*;
import lk.ijse.lavishStyloo.util.DateTimeUtil;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;


public class AdminHomeController implements Initializable {
    @FXML
    private Text txtPendingBookings;

    @FXML
    private Text txtToDayBokkings;

    @FXML
    private JFXTextField txtText11;

    @FXML
    private Text date;

    @FXML
    private Text txtToDayAttendance;

    @FXML
    private Text txtAvalebelEmployees;

    @FXML
    private Text txtUnComlitedBookings;

    @FXML
    private Text txtCompliteBookings;

    @FXML
    private Text timeHouer;

    @FXML
    private Text timeMin;

    @FXML
    private Text timeStatus;

    @FXML
    private Text txtLimitedItems;

    @FXML
    private Text txtTodayOrders;

    @FXML
    public void onKeyReleased(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDate();
        setCount();
        setTime();
    }

    private void setTime() {
        Thread thread=new Thread(() -> {
            SimpleDateFormat format=new SimpleDateFormat("hh:mm:ss");
            SimpleDateFormat format2=new SimpleDateFormat("a");
            while (true){
                LocalTime time = LocalTime.parse(format.format(new Date()));
                timeStatus.setText(format2.format(new Date()));
                timeHouer.setText(String.valueOf(time.getHour()<10?("0"+time.getHour()):time.getHour()));
                timeMin.setText(String.valueOf(time.getMinute()<10?("0"+time.getMinute()):time.getMinute()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void setDate() {
        SimpleDateFormat format=new SimpleDateFormat("E, dd MMM");
        date.setText(format.format(new Date()));
    }

    private void setCount() {
        try {
            txtLimitedItems.setText(ProductModel.CountByQTY()+"+");
            txtAvalebelEmployees.setText(EmployeeModel.CountAvailable()+"+");
            txtPendingBookings.setText(BookingModel.PendingCount()+"+");
            txtUnComlitedBookings.setText(BookingModel.UnCompliedCount()+"+");
            txtCompliteBookings.setText(BookingModel.CompliedCount()+"+");
            txtToDayBokkings.setText(BookingModel.CountBookingByDate(DateTimeUtil.dateNow())+"+");
            txtToDayAttendance.setText(AttendanceModel.countAttendanceByDate(DateTimeUtil.dateNow())+"+");
            txtTodayOrders.setText(CustomerOrderModel.CountCustomerOrderByDate()+"+");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
