package lk.ijse.lavishStyloo.controller.Cashier;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.dto.BookingDTO;
import lk.ijse.lavishStyloo.dto.BookingDetailsDTO;
import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.dto.tm.AppointmentTm;
import lk.ijse.lavishStyloo.model.BookingModel;
import lk.ijse.lavishStyloo.model.CustomerModel;
import lk.ijse.lavishStyloo.util.DateTimeUtil;
import lk.ijse.lavishStyloo.util.NavigationUtility;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BookingFromController implements Initializable {

    public Text txtNextAvailableTime;
    public JFXTextField lblCustomerName;
    public JFXTextField lblCusNo;
    public Text btnText;
    public Text txtAddress;
    public JFXComboBox cmbCustomerId;
    public Text txtCutId;
    public Text txtAvailableEmployee;
    public Text txtNetTotal;
    public Text txtTotal;
    public Text txtProduct;
    public JFXTimePicker timeStart;
    public JFXTimePicker timeEnd;
    public JFXButton btnTreatment;

    public TableView tblBooking;
    public TableColumn colTreatment;
    public TableColumn colAmount;
    public TableColumn colCategory;
    public TableColumn colEmployee;
    public TableColumn colChoose;

    private static BookingFromController instance;
    public JFXTextField lblStartTimeH;
    public JFXTextField lblStartTimeM;
    public JFXTextField lblEndTimeH;
    public JFXTextField lblEndTimeM;

    public BookingFromController() {
        instance=this;
    }

    public static BookingFromController getInstance() {
        return instance;
    }

    public ObservableList<AppointmentTm> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllCustomerId();
        colTreatment.setCellValueFactory(new PropertyValueFactory<>("treatment"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colEmployee.setCellValueFactory(new PropertyValueFactory<>("employee"));
        colChoose.setCellValueFactory(new PropertyValueFactory<>("choose"));
        tblBooking.setItems(list);
    }

    public void btnTreatmentOnAction(ActionEvent actionEvent) {
        NavigationUtility.popupNavigation("Cashier/BookingTreatmentFrom.fxml");
    }

    public void btnApplyOnAction(ActionEvent actionEvent) {

        try {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setBooking_id(BookingModel.getNextId());
            bookingDTO.setDate(DateTimeUtil.dateNow());
            bookingDTO.setTime(DateTimeUtil.timeNow());
            bookingDTO.setCus_id(txtCutId.getText());
            bookingDTO.setTotal(txtNetTotal.getText());
            bookingDTO.setBooking_start(lblStartTimeH.getText()+":"+lblStartTimeM.getText()+":00");
            bookingDTO.setBooking_end(lblEndTimeH.getText()+":"+lblEndTimeM.getText()+":00");

            List<BookingDetailsDTO> list=new ArrayList<>();

            for (AppointmentTm tm:this.list){
                BookingDetailsDTO dto = new BookingDetailsDTO();
                dto.setBooking_id(bookingDTO.getBooking_id());
                dto.setTreat_id(tm.getTreatment_id());
                dto.setNic(tm.getNic());
                dto.setCharge(Double.parseDouble(tm.getAmount()));
                list.add(dto);
            }
            BookingModel.booking(bookingDTO,list);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void cmbCustomerOnaAction(ActionEvent actionEvent) {
        try {
            CustomerDTO customerDTO = CustomerModel.findCustomerById(String.valueOf(cmbCustomerId.getValue()));
            txtAddress.setText(customerDTO.getStreet() + " ," + customerDTO.getLane() + " ," + customerDTO.getCity());
            txtCutId.setText(customerDTO.getCustomer_id());
            lblCustomerName.setText(customerDTO.getFirst_name() + " " + customerDTO.getLast_name());
            lblCusNo.setText(customerDTO.getContact());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void customerFromAddOnAction(ActionEvent actionEvent) {
        NavigationUtility.popupNavigation("Cashier/CustomerAddFrom.fxml");
    }

    public void lblNameOnKeyReleased(KeyEvent keyEvent) {
        try {
            List<CustomerDTO> customerByLike = CustomerModel.findCustomerByLike(lblCustomerName.getText());
            for (CustomerDTO customerDTO : customerByLike) {
                System.out.println(customerDTO.toString());
                txtAddress.setText(customerDTO.getStreet() + " ," + customerDTO.getLane() + " ," + customerDTO.getCity());
                txtCutId.setText(customerDTO.getCustomer_id());
                lblCustomerName.setText(customerDTO.getFirst_name() + " " + customerDTO.getLast_name());
                lblCusNo.setText(customerDTO.getContact());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void contactOnKeyReleased(KeyEvent keyEvent) {
        try {
            List<CustomerDTO> customerByLike = CustomerModel.findCustomerByLike(lblCusNo.getText());
            for (CustomerDTO customerDTO : customerByLike) {

                txtAddress.setText(customerDTO.getStreet() + " ," + customerDTO.getLane() + " ," + customerDTO.getCity());
                txtCutId.setText(customerDTO.getCustomer_id());
                lblCustomerName.setText(customerDTO.getFirst_name() + " " + customerDTO.getLast_name());
                lblCusNo.setText(customerDTO.getContact());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void loadAllCustomerId() {

        try {
            List<String> ids = new ArrayList<>();
            List<CustomerDTO> all = CustomerModel.findAll();
            for (CustomerDTO dto : all) {
                ids.add(dto.getCustomer_id());
            }
            cmbCustomerId.getItems().addAll(ids);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void setData(AppointmentTm appointmentTm) {
        boolean isNot=true;
        for (AppointmentTm tm:list){
            if (appointmentTm.getTreatment_id().equals(tm.getTreatment_id())){
                isNot=false;
            }
        }
        if (isNot){
            list.add(appointmentTm);
            setTotal();
        }
    }

    private void setTotal() {
        double total=0;
        for (AppointmentTm tm: list){
            total+=Double.parseDouble(tm.getAmount());
        }
        txtNetTotal.setText(String.valueOf(total));
    }
}
