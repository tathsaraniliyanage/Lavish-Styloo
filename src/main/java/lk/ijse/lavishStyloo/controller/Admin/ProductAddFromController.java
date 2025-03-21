package lk.ijse.lavishStyloo.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.utils.JFXNodeUtils;
import com.jfoenix.utils.JFXUtilities;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.lavishStyloo.dto.ProductDTO;
import lk.ijse.lavishStyloo.model.ProductModel;
import lk.ijse.lavishStyloo.util.NavigationUtility;
import lk.ijse.lavishStyloo.util.RegexUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;

public class ProductAddFromController {
    public JFXTextField txtProduct;
    public JFXTextField txtDescription;
    public JFXTextField txtPrice;
    public ImageView image;
    public JFXButton btnAdd;

    public void AddOnAction(ActionEvent actionEvent) {
        ProductDTO dto = new ProductDTO();
        dto.setProduct(txtProduct.getText());
        dto.setQty("0");
        dto.setDescription(txtDescription.getText());
        dto.setUnit_price(txtPrice.getText());
        dto.setImg(getNextID()+".png");
        dto.setProduct_code(getNextID());
        try {
            boolean isSave= ProductModel.save(dto);
            if (isSave){
                closeOnAction(actionEvent);
                ProductFromController.getController().loadAllProduct();
                new Alert(Alert.AlertType.CONFIRMATION,"ok").show();

            }else {
                new Alert(Alert.AlertType.CONFIRMATION,"not").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private String getNextID() {
        try {
            return ProductModel.nextId();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void closeOnAction(ActionEvent actionEvent) {
        NavigationUtility.close(actionEvent);
    }

    public void ImageAddOnAction(ActionEvent actionEvent) {
        try {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open a file");
            fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files","*.png"));
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if(selectedFile != null){
                Image image = new Image(selectedFile.getPath());
                this.image.setImage(image);
                byte[] bytes = Files.readAllBytes(selectedFile.toPath());
                String name = selectedFile.getName();
                System.out.println(name);
                String[] split = name.split("\\.");
                System.out.println(Arrays.toString(split));
                String extenuation =split[split.length-1];
                Path path=Paths.get("C:\\Users\\Sasindu Malshan\\Downloads\\Prabo\\Lavish_Styloo\\src\\main\\resources\\imgAsset\\"+getNextID()+"."+extenuation);
                Files.write(path,bytes);
            }else{
                System.out.println("No file has been selected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void priceOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtPrice, "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$","-fx-text-fill: black");
    }

    public void descriptionOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtDescription, "\\b([a-z]|[A-Z]|[\\s])+","-fx-text-fill: black");
    }

    public void productOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtProduct, "\\b([a-z]|[A-Z]|[\\s])+","-fx-text-fill: black");
    }
}
