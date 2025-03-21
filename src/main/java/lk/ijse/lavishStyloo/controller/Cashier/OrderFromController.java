package lk.ijse.lavishStyloo.controller.Cashier;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import lk.ijse.lavishStyloo.db.DBConnection;
import lk.ijse.lavishStyloo.dto.CustomerDTO;
import lk.ijse.lavishStyloo.dto.OrderDTO;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.dto.tm.OrderTm;
import lk.ijse.lavishStyloo.model.CustomerModel;
import lk.ijse.lavishStyloo.model.OrderModel;
import lk.ijse.lavishStyloo.model.ProductModel;
import lk.ijse.lavishStyloo.util.DateTimeUtil;
import lk.ijse.lavishStyloo.util.MailUtil;
import lk.ijse.lavishStyloo.util.NavigationUtility;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFromController implements Initializable {
    public TableView tblOrder;
    public TableColumn colCode;
    public TableColumn colProduct;
    public TableColumn colUnitPrice;
    public TableColumn colPrice;
    public TableColumn colQty;
    public Text txtQty;
    public Text txtDescription;
    public Text txtCutId;
    public Text txtAddress;
    public JFXComboBox cmbCustomerId;
    public JFXTextField lblCusNo;
    public JFXTextField lblCustomerName;
    public Text txtOrderCount;
    public Text txtTotalBalance;
    public JFXTextField lblBalance;
    public Text txtNetTotal;
    public Text txtTotal;
    public JFXTextField lblQty;
    public JFXTextField LblCode;
    public ImageView imgItem;
    public Text txtPrice;
    public Text txtProduct;
    public JFXButton btnAdd;
    ArrayList<OrderTm> list = new ArrayList<>();
    ObservableList<OrderTm> orderTms = FXCollections.observableArrayList();

    public void customerFromAddOnAction(ActionEvent actionEvent) {
        NavigationUtility.popupNavigation("Cashier/CustomerAddFrom.fxml");

    }

    public void cmbCustomerOnaAction(ActionEvent actionEvent) throws ClassNotFoundException {
        try {
            CustomerDTO customerDTO = CustomerModel.findCustomerById(String.valueOf(cmbCustomerId.getValue()));
            txtAddress.setText(customerDTO.getStreet() + " ," + customerDTO.getLane() + " ," + customerDTO.getCity());
            txtCutId.setText(customerDTO.getCustomer_id());
            lblCustomerName.setText(customerDTO.getFirst_name() + " " + customerDTO.getLast_name());
            lblCusNo.setText(customerDTO.getContact());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void contactOnKeyReleas(KeyEvent keyEvent) {
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

    public void lblNameOnKeyReleas(KeyEvent keyEvent) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrder.getItems().addAll(orderTms);
        loadAllCustomerId();
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

    public void lblCodeOnKeyReleased(KeyEvent keyEvent) {
        try {
            List<ProductDTO> productByCode = ProductModel.findProductsByCode(LblCode.getText());
            for (ProductDTO dto : productByCode) {
                System.out.println(dto.toString());
                txtQty.setText(dto.getQty());
                txtPrice.setText(dto.getUnit_price());
                Image image = new Image("/imgAsset/" + dto.getImg());
                imgItem.setImage(image);
            }
            setOrderCount();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setOrderCount() {
        txtOrderCount.setText(String.valueOf(list.size()));
    }

    public void lblQryOnKeReleasd(KeyEvent keyEvent) {

        if (!lblQty.getText().isEmpty()) {
            double t = Double.parseDouble(lblQty.getText()) * Double.parseDouble(txtPrice.getText());
            txtTotal.setText(String.valueOf(t));
            btnAdd.setDisable(Double.parseDouble(txtTotal.getText()) < 0);
        } else {
            txtTotal.setText("00.00");
            btnAdd.setDisable(true);
        }


    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        boolean isNotDuplicate = false;
        for (OrderTm orderTm : list) {
            if (orderTm.getItemCode().equals(LblCode.getText())) {
                orderTm.setQty(String.valueOf(Integer.parseInt(orderTm.getQty()) + Integer.parseInt(lblQty.getText())));
                orderTm.setPrice(String.valueOf(Double.parseDouble(orderTm.getPrice()) + Double.parseDouble(txtPrice.getText())));
                isNotDuplicate = false;
                break;
            } else {
                isNotDuplicate = true;
            }
        }

        if (isNotDuplicate) {
            OrderTm tm = new OrderTm();
            tm.setItemCode(LblCode.getText());
            tm.setProduct(txtProduct.getText());
            tm.setQty(lblQty.getText());
            tm.setPrice(txtPrice.getText());
            tm.setUnitPrice(txtTotal.getText());
            list.add(tm);
        }

        if (list.isEmpty()) {
            OrderTm tm = new OrderTm();
            tm.setItemCode(LblCode.getText());
            tm.setProduct(txtProduct.getText());
            tm.setQty(lblQty.getText());
            tm.setPrice(txtPrice.getText());
            tm.setUnitPrice(txtTotal.getText());
            list.add(tm);
        }

        tblOrder.getItems().clear();
        orderTms.clear();
        tblOrder.getItems().addAll(list);
        tblOrder.refresh();
        setOrderCount();
        setNetTotal();
    }

    private void setNetTotal() {
        double total = 0;
        for (OrderTm tm : list) {
            total += Double.parseDouble(tm.getPrice());
        }
        txtNetTotal.setText(String.valueOf(total));
    }

    public void balanceOnKeyReleased(KeyEvent keyEvent) {
        double total = Double.parseDouble(lblBalance.getText()) - Double.parseDouble(txtNetTotal.getText());
        txtTotalBalance.setText(String.valueOf(total));
    }

    public void btnTotalOnAction(ActionEvent actionEvent) {
        try {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setTotal(Double.parseDouble(txtNetTotal.getText()));
            orderDTO.setCust_id(txtCutId.getText());
            orderDTO.setCust_oid(nextId());
            orderDTO.setDate(DateTimeUtil.dateNow());
            orderDTO.setTime(DateTimeUtil.timeNow());
            boolean savedOrder = OrderModel.placeOrder(list, orderDTO);
            if (savedOrder) {
                new Alert(Alert.AlertType.CONFIRMATION, "order saved").show();
                printBill(orderDTO.getCust_oid());
            } else {
                new Alert(Alert.AlertType.WARNING, "something Wong").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private String nextId() throws SQLException, ClassNotFoundException {
        return OrderModel.next();
    }

    private void printBill(String cust_oid) {

        String fileNamePdf = "C:\\Users\\Sasindu Malshan\\Downloads\\Prabo\\Lavish_Styloo\\src\\main\\resources\\PrintPDF\\"+cust_oid + ".pdf";
        System.out.println(cust_oid + " report id");
        InputStream resource = this.getClass().getResourceAsStream("/report/Bill.jrxml");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("id", cust_oid);
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(print, false);

            JasperExportManager.exportReportToPdfFile(print, fileNamePdf);
            System.out.println("Successfully completed the export");

            String body="<h1 style=\"font-size: 50px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; color: rgb(43, 180, 226);text-align: center;\">Lavish Stylo</h1>\n" +
                    "               <p style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;\">\n" +
                    "                 Lavish styloo is a luxury salon located in Galle.we provide you various treatments from head to toe using thebest product,advanced and affordable price</p>\n" +
                    "               \n" +
                    "               <h1 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: rgb(57, 53, 53); font-size: 20px; margin-top: 40px; margin-left: 30px;\">opens at 8.00 am</h1>\n" +
                    "               <h1 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: rgb(57, 53, 53); font-size: 20px; margin-left: 30px;\">close at 8.00 pm</h1>\n" +
                    "               <h3 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: #545252; margin-top: 40px;\">Bookings for treatments during the day can only be made between 8.00 am to 10.am. </h1>\n" +
                    "               <h3 style=\"font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; color: #545252;\">For later days, the salon is open that bookings can be made at any time</h4>\n" +
                    "                \n" +
                    "                              <h1 style=\"font-size: 50px; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; color: rgb(17, 45, 54);text-align: center;\">THANK YOU</h1>\n" +
                    "\n" ;

            MailUtil.sendEmail("sasindu.malshan03262001@gmail.com","Lavish Stylo Bill Payment",body,cust_oid);

        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }




     /*   try {
            JasperDesign jasperDesign = JRXmlLoader.load(fileNameJrxml);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            String first_language = "Java";
            String second_language = "Structured text";
            HashMap hm = new HashMap();
            hm.put("id", cust_oid);

            JasperPrint jprint = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperExportManager.exportReportToPdfFile(jprint, fileNamePdf);
            System.out.println("Successfully completed the export");

            sendEmail(orderId, mail);

        } catch (
                Exception e) {
            System.out.println("fuck");
            System.out.print("Exception:" + e);
        }*/
    }
}
