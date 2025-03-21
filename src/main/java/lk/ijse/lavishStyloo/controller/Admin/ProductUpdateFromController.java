package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.model.ProductModel;
import lk.ijse.lavishStyloo.util.NavigationUtility;
import lk.ijse.lavishStyloo.util.RegexUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ProductUpdateFromController implements Initializable {
    public Text txtCode;
    public JFXTextField txtProduct;
    public JFXTextField txtDescription;
    public JFXTextField txtPrice;
    public ImageView image;
    public JFXButton btn;

    public void UpdateOnAction(ActionEvent actionEvent) {
        ProductDTO dto = new ProductDTO();
        dto.setProduct(txtProduct.getText());
        dto.setDescription(txtDescription.getText());
        dto.setUnit_price(txtPrice.getText());
        dto.setImg(txtCode.getText() + ".png");
        dto.setProduct_code(txtCode.getText());
        try {
            boolean isSave = ProductModel.update(dto);
            if (isSave) {
                closeOnAction(actionEvent);
                ProductFromController.getController().loadAllProduct();
                new Alert(Alert.AlertType.CONFIRMATION, "ok").show();
                ProductFromController.getController().btnClear.setVisible(false);
                ProductFromController.getController(). btnText.setText("NEW");

            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "not").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void closeOnAction(ActionEvent actionEvent) {
        NavigationUtility.close(actionEvent);
    }

    public void ImageAddOnAction(ActionEvent actionEvent) {
        try {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open a file");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files", "*.png"));
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                Image image = new Image(selectedFile.getPath());
                this.image.setImage(image);
                byte[] bytes = Files.readAllBytes(selectedFile.toPath());
                String name = selectedFile.getName();
                System.out.println(name);
                String[] split = name.split("\\.");
                System.out.println(Arrays.toString(split));
                String extenuation = split[split.length - 1];
                Path path = Paths.get("C:\\Users\\Sasindu Malshan\\Downloads\\Prabo\\Lavish_Styloo\\src\\main\\resources\\imgAsset\\" + txtCode.getText() + "." + extenuation);
                Files.write(path, bytes);
            } else {
                System.out.println("No file has been selected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProductById(ProductFromController.product_code);
    }

    private void loadProductById(String product_code) {
        try {
            ProductDTO product = ProductModel.findProductByCode(product_code);
            txtCode.setText(product.getProduct_code());
            txtProduct.setText(product.getProduct());
            txtPrice.setText(product.getUnit_price());
            txtDescription.setText(product.getDescription());
            Image img = new Image("/imgAsset/" + product.getImg());
            image.setImage(img);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public void priceOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtPrice, "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$","-fx-text-fill: black");
    }

    public void descriptionOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtDescription, "\\b([a-z]|[A-Z]|[\\s])+","-fx-text-fill: black");
    }

    public void productOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtProduct, "\\b([a-z]|[A-Z]|[\\s])+","-fx-text-fill: black");
    }
}
