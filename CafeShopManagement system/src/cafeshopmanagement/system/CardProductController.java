/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cafeshopmanagement.system;
import java.util.Date;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import java.sql.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author kmrsk
 */
public class CardProductController implements Initializable {

    @FXML
    private AnchorPane card_form;
    @FXML
    private Label prod_name;
    @FXML
    private Label prod_price;
    @FXML
    private ImageView prod_imageView;
    @FXML
    private Spinner<Integer> prod_spinner;
    @FXML
    private Button prod_addBtn;
    
    private  ProductData prodData;
    private Image image;
    private String prod_image;
    
    private String prodID;

    private String type;
    private String prod_date;
    
    
      private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;
    
    
    
    private SpinnerValueFactory<Integer> spin;
    
    public void setData(ProductData prodData){
        this.prodData = prodData;
        prod_date = String.valueOf(prodData.getDate());
        prod_image = prodData.getImage();
        
         type = prodData.getType();
    
                 
         prodID = prodData.getProductId();
        
        prod_name.setText(prodData.getProductName());
        prod_price.setText("$" + String.valueOf(prodData.getPrice()));
        
        String path = "File:" + prodData.getImage();
        
        image = new Image(path, 203, 107, false, true);
        
        prod_imageView.setImage(image);
        pr = prodData.getPrice();
        
    }
    
     private int qty;

    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        prod_spinner.setValueFactory(spin);
    }
    
    private double totalP;
    private double pr;
    
    public void addBtn(){
        
        MainScreenController mForm = new MainScreenController();
        mForm.customerID();
        
        
          qty = prod_spinner.getValue();
        String check = "";
        String checkAvailable = "SELECT status FROM product WHERE product_id = '"
                + prodID + "'";

        connect = database.connectDB();
        
        try {
            
            
              prepare = connect.prepareStatement(checkAvailable);
            result = prepare.executeQuery();

            if (result.next()) {
                check = result.getString("status");
            }
            
            
            if (!check.equals("Available") || qty == 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something Wrong :3");
                alert.showAndWait();
            }
            else{
                
                int checkStck = 0;
            String checkStock = "SELECT stock FROM product WHERE product_id = '"
                    + prodID + "'";
            

            prepare = connect.prepareStatement(checkStock);
            result = prepare.executeQuery();
            
            if(result.next()){
                checkStck = result.getInt("stock");
                
            }
            if(checkStck < qty){
                alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid. This product is Out of stock");
                    alert.showAndWait();
                
            }
            else{
                
                 prod_image = prod_image.replace("\\", "\\\\");
                
                  String insertData = "INSERT INTO customer "
                            + "(customer_id, prod_id, prod_name, type, quantity, price, date, image, em_username) "
                            + "VALUES(?,?,?,?,?,?,?,?,?)";
                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, String.valueOf(shop_data.cID));
                    prepare.setString(2, prodID);
                    prepare.setString(3, prod_name.getText());
                    prepare.setString(4, type);
                    prepare.setString(5, String.valueOf(qty));
                    
                    totalP = (qty * pr);
                    prepare.setString(6, String.valueOf(totalP));
 
                     Date date = new Date();
                     
                     java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                     
                    prepare.setString(7, String.valueOf(sqlDate));
                    prepare.setString(8, prod_image);
                    prepare.setString(9, shop_data.username);
                    prepare.executeUpdate();
                    
                    
                    int upStock = checkStck - qty;
                    
                     prod_image = prod_image.replace("\\", "\\\\");
                    

                    
                    
                      String updateStock = "UPDATE product SET product_name = '"
                            + prod_name.getText() + "', type = '"
                            + type + "', stock = " + upStock + ", price = " + pr
                            + ", status = '"
                            + check + "', image = '"
                            + prod_image + "', date = '"
                            + prod_date + "' WHERE product_id = '"
                            + prodID + "'";
                      
                      
                      prepare = connect.prepareStatement(updateStock);
                      prepare.executeUpdate();

                    
                    
                     alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    
                    mForm.menuGetTotal();
                
            }
                 
                
            }
            
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
   
 
    public void initialize(URL url, ResourceBundle rb) {
        setQuantity();
    }    
    
}
