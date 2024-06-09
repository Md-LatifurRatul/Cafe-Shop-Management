/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cafeshopmanagement.system;

import java.io.File;
import java.sql.*;
import java.sql.ResultSet;
import java.net.URL;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author kmrsk
 */
public class MainScreenController implements Initializable {

    @FXML
    private AnchorPane main_form;
    @FXML
    private Label username;
    @FXML
    private Button dashboard_btn;
    @FXML
    private Button inventory_btn;
    @FXML
    private Button menu_btn;
    @FXML
    private Button customers_btn;
    @FXML
    private Button logout_btn;
    @FXML
    private AnchorPane inventory_form;
    @FXML
    private TableView<ProductData> inventory_tableView;
    @FXML
    private TableColumn<ProductData, String> inventory_col_productID;
    @FXML
    private TableColumn<ProductData, String> inventory_col_productName;
    @FXML
    private TableColumn<ProductData, String> inventory_col_type;
    @FXML
    private TableColumn<ProductData, String> inventory_col_stock;
    @FXML
    private TableColumn<ProductData, String> inventory_col_price;
    @FXML
    private TableColumn<ProductData, String> inventory_col_status;
    @FXML
    private TableColumn<ProductData, String> inventory_col_date;
    @FXML
    private ImageView inventory_imageView;
    @FXML
    private Button inventory_import_btn;
    @FXML
    private Button inventory_addBtn;
    @FXML
    private Button inventory_updateBtn;
    @FXML
    private Button inventory_clearBtn;
    @FXML
    private Button inventory_deleteBtn;
     @FXML
    private TextField inventory_product_id;
    @FXML
    private TextField inventory_product_name;
    @FXML
    private TextField inventory_stock;
    @FXML
    private TextField inventory_price;
    @FXML
    private ComboBox<?> inventory_status;
    @FXML
    private ComboBox<?> inventory_type;

    private  Alert alert;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    private  Image image;
    @FXML
    private AnchorPane menu_form;
    @FXML
    private ScrollPane menu_scrollPane;
    @FXML
    private GridPane menu_gridPane;
    @FXML
    private TableView<ProductData> menu_tableView;
    @FXML
    private TableColumn<ProductData, String> menu_col_productName;
    @FXML
    private TableColumn<ProductData, String> menu_col_quantity;
    @FXML
    private TableColumn<ProductData, String> menu_col_price;
    @FXML
    private Label menu_total;
    @FXML
    private TextField menu_amount;
    @FXML
    private Label menu_change;
    @FXML
    private Button menu_payBtn;
    @FXML
    private AnchorPane dashboard_form;
    @FXML
    private AnchorPane customers_form;
    @FXML
    private TableView<customersData> customers_tableView;
    @FXML
    private TableColumn<customersData, String> customer_col_customerID;
    @FXML
    private TableColumn<customersData, String> customer_col_total;
    @FXML
    private TableColumn<customersData, String> customer_col_date;
    @FXML
    private TableColumn<customersData, String> customer_col_cashier;
    @FXML
    private Label dashboard_NC;
    @FXML
    private Label dashboard_TI;
    @FXML
    private Label dashboard_NSP;
    @FXML
    private AreaChart<?, ?> dashboard_IncomeChart;
    @FXML
    private BarChart<?, ?> dashboard_CustomerChart;
     private ObservableList<ProductData> cardListData = FXCollections.observableArrayList();
    @FXML
    private Label dashboard_TotalI;
     
       public void dashboardDisplayNC() {
        
        String sql = "SELECT COUNT(id) FROM receipt";
        connect = database.connectDB();
        
        try {
            int nc = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }
            dashboard_NC.setText(String.valueOf(nc));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
     public void dashboardDisplayTI() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        String sql = "SELECT SUM(total) FROM receipt WHERE date = '"
                + sqlDate + "'";
        
        connect = database.connectDB();
        
        try {
            double ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }
            
            dashboard_TI.setText("$" + ti);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   
     public void dashboardTotalI() {
        String sql = "SELECT SUM(total) FROM receipt";
        
        connect = database.connectDB();
        
        try {
            float ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) {
                ti = result.getFloat("SUM(total)");
            }
            dashboard_TotalI.setText("$" + ti);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
         public void dashboardNSP() {
        
        String sql = "SELECT COUNT(quantity) FROM customer";
        
        connect = database.connectDB();
        
        try {
            int q = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) {
                q = result.getInt("COUNT(quantity)");
            }
            dashboard_NSP.setText(String.valueOf(q));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
 public void dashboardIncomeChart() {
        dashboard_IncomeChart.getData().clear();
        
        String sql = "SELECT date, SUM(total) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        connect = database.connectDB();
        XYChart.Series chart = new XYChart.Series();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getFloat(2)));
            }
            
            dashboard_IncomeChart.getData().add(chart);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public void dashboardCustomerChart(){
        dashboard_CustomerChart.getData().clear();
        
        String sql = "SELECT date, COUNT(id) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        connect = database.connectDB();
        XYChart.Series chart = new XYChart.Series();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }
            
            dashboard_CustomerChart.getData().add(chart);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
         
         
   
    @FXML
    public void inventoryAddBtn() {

        if (inventory_product_id.getText().isEmpty()
                || inventory_product_name.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || shop_data.path == null) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            String checkProdID = "SELECT product_id FROM product WHERE product_id = '"
                    + inventory_product_id.getText() + "'";

            connect = database.connectDB();

            try {

                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);
                if (result.next()) {

                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(inventory_product_id.getText() + " is already taken");
                    alert.showAndWait();

                } else {
                    String insertData = "INSERT INTO product "
                            + "(product_id, product_name, type, stock, price, status, image, date) "
                            + "VALUES(?,?,?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, inventory_product_id.getText());
                    prepare.setString(2, inventory_product_name.getText());
                    prepare.setString(3, (String) inventory_type.getSelectionModel().getSelectedItem());
                    prepare.setString(4, inventory_stock.getText());
                    prepare.setString(5, inventory_price.getText());
                    prepare.setString(6, (String) inventory_status.getSelectionModel().getSelectedItem());

                    String path = shop_data.path;
                    path = path.replace("\\", "\\\\");

                    prepare.setString(7, path);

                    // TO GET CURRENT DATE
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(8, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    inventoryShowData();
                    inventoryClearBtn();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    
    
    @FXML
    public void inventoryUpdateBtn() {

        if (inventory_product_id.getText().isEmpty()
                || inventory_product_name.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || shop_data.path == null || shop_data.id == 0) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            String path = shop_data.path;
            path = path.replace("\\", "\\\\");

            String updateData = "UPDATE product SET "
                    + "product_id = '" + inventory_product_id.getText() + "', product_name = '"
                    + inventory_product_name.getText() + "', type = '"
                    + inventory_type.getSelectionModel().getSelectedItem() + "', stock = '"
                    + inventory_stock.getText() + "', price = '"
                    + inventory_price.getText() + "', status = '"
                    + inventory_status.getSelectionModel().getSelectedItem() + "', image = '"
                    + path + "', date = '"
                    + shop_data.date + "' WHERE id = " + shop_data.id;

            connect = database.connectDB();

            try {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE PRoduct ID: " + inventory_product_id.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    
                    inventoryShowData();
                   
                    inventoryClearBtn();
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();

            }

        }
    }

    
    @FXML
  public void inventoryDeleteBtn() {
        if (shop_data.id == 0) {
            
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
            
        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Product ID: " + inventory_product_id.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            
            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM product WHERE id = " + shop_data.id;
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted!");
                    alert.showAndWait();

                    
                    inventoryShowData();
                   
                    inventoryClearBtn();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        }
    }
     
    @FXML
       public void inventoryClearBtn() {
        
        inventory_product_id.setText("");
        inventory_product_name.setText("");
        inventory_type.getSelectionModel().clearSelection();
        inventory_stock.setText("");
        inventory_price.setText("");
        inventory_status.getSelectionModel().clearSelection();
        shop_data.path = "";
        shop_data.id = 0;
        inventory_imageView.setImage(null);
        
    }
       
    //Making a behaviour of import btn
    @FXML
     public void inventoryImportBtn() {
        
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new ExtensionFilter("Open Image File", "*png", "*jpg"));
        
        File file = openFile.showOpenDialog(main_form.getScene().getWindow());
        
        if (file != null) {
            
            shop_data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 156, 150, false, true);
            
            inventory_imageView.setImage(image);
        }
    }
     
    
    //Merging all the data
    public ObservableList<ProductData> inventoryDataList() {

        ObservableList<ProductData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";
        connect = database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ProductData productData;

            while (result.next()) {
                productData = new ProductData(result.getInt("id"), result.getString("product_id"), result.getString("product_name"),result.getString("type"), result.getInt("stock"), result.getDouble("price"), result.getString("status"), result.getString("image"), result.getDate("date"));
                listData.add(productData);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;

    }
    
    //To show data on the table
    
    private ObservableList<ProductData> inventoryListData;
    
    public void inventoryShowData(){
        
        inventoryListData = inventoryDataList();
        inventory_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        inventory_tableView.setItems(inventoryListData);

    }
    
    
    @FXML
     public void inventorySelectData() {
        
        ProductData prodData = inventory_tableView.getSelectionModel().getSelectedItem();
        int num = inventory_tableView.getSelectionModel().getSelectedIndex();
        
        if ((num - 1) < -1) {
            return;
        }
        
        inventory_product_id.setText(prodData.getProductId());
        inventory_product_name.setText(prodData.getProductName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrice()));
        
          shop_data.path = prodData.getImage();
        
          String path = "File:" + prodData.getImage();
          shop_data.date = String.valueOf(prodData.getDate());
          shop_data.id = prodData.getId();
        
        image = new Image(path, 156, 150, false, true);
        inventory_imageView.setImage(image);
    }
    
    
   
    
    private String[] typeList = {"Meals", "Drinks"};
    
    public void inventoryTypeList(){
        List<String> typleL = new ArrayList<>();
        for(String data: typeList){
            typleL.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(typleL);
         inventory_type.setItems(listData);
        
        
    }
    
    
    private String[] statusList = {"Available", "Unavailable"};
    
    public void inventoryStatusList(){
        List<String> statusL = new ArrayList<>();
        for(String data: statusList){
            statusL.add(data);
        }
         ObservableList listData = FXCollections.observableArrayList(statusL);
         inventory_status.setItems(listData);
        
        
    }
    
    
    //22.09
    public ObservableList<ProductData> menuGetData(){
        
         String sql = "SELECT * FROM product";
        
        ObservableList<ProductData> listData = FXCollections.observableArrayList();
        connect = database.connectDB();
        
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            ProductData prod;
            
            while (result.next()) {
                prod = new ProductData(result.getInt("id"),
                        result.getString("product_id"),
                        result.getString("product_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));
                
                listData.add(prod);
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        
       
        
        return listData;
    }
    
    
    private ObservableList<ProductData> menuOrderListData;
    
    public void menuShowOrderData() {
        menuOrderListData = menuGetOrder();
        
        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        menu_tableView.setItems(menuOrderListData);
    }
    
     private int getid;
    
    @FXML
      public void menuSelectOrder() {
        ProductData prod = menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();
        
        if ((num - 1) < -1) {
            return;
        }
        // TO GET THE ID PER ORDER
        getid = prod.getId();
        
    }
    
    
    
    private double totalP;
    
    public void menuGetTotal(){
        
         customerID();
        
        
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;
        
        connect = database.connectDB();
        try {
            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();
            
            if(result.next()){
                totalP = result.getDouble("SUM(price)");
                
            }
        
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    
    public void menuDisplayTotal(){
        menuGetTotal();
        menu_total.setText("$" + totalP);
        
        
    }
    
    
     
    
     public void menuDisplayCard() {
        
        cardListData.clear();
        cardListData.addAll(menuGetData());
        
        int row = 0;
        int column = 0;
        
        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();
        
        for (int q = 0; q < cardListData.size(); q++) {
            
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("cardProduct.fxml"));
                AnchorPane pane = load.load();
                CardProductController cardC = load.getController();
                cardC.setData(cardListData.get(q));
                
                if (column == 3) {
                    column = 0;
                    row += 1;
                }
                
                menu_gridPane.add(pane, column++, row);
                
                GridPane.setMargin(pane, new Insets(10));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
     
     
     public ObservableList<ProductData> menuGetOrder(){
            customerID();
         ObservableList<ProductData> listData = FXCollections.observableArrayList();
      
         
         String sql = "SELECT * FROM customer WHERE customer_id = " + cID;
         
         connect = database.connectDB();
         
         try {
             
             prepare = connect.prepareStatement(sql);
             result = prepare.executeQuery();
             
             ProductData prod;
             
             while(result.next()){
                 prod = new ProductData(result.getInt("id"),
                         result.getString("prod_id"), result.getString("prod_name"), 
                         result.getString("type"), result.getInt("quantity"),
                         result.getDouble("price"), result.getString("image"),
                         result.getDate("date"));
                 listData.add(prod);
             }
             
         } catch (Exception e) {
             e.printStackTrace();
         }
         return  listData;
         
     }
     
     
     private double amount;
     
     private double change;
     
     
    @FXML
     public void menuAmount(){
        menuGetTotal();
        if (menu_amount.getText().isEmpty() || totalP == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :3");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(menu_amount.getText());
         
            if(amount < totalP){
                menu_amount.setText("");
                
            }
            else{
                 change = (amount - totalP);
                 menu_change.setText("$" + change);
            }
            
           
        }
         
         
     }
     
    @FXML
      public void menuPayBtn() {
        
        if (totalP == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
        } else {
            menuGetTotal();
             String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username) "
                    + "VALUES(?,?,?,?)";
             
             connect  = database.connectDB();
            
            try {
                
                if (amount == 0) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Something wrong");
                    alert.showAndWait();
                } else {
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure?");

                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        customerID();
                        menuGetTotal();

                        prepare = connect.prepareStatement(insertPay);
                        
                        prepare.setString(1, String.valueOf(cID));
                        prepare.setString(2, String.valueOf(totalP));
                        
                        Date date = new Date();
                        
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        
                        prepare.setString(3, String.valueOf(sqlDate));
                        
                        prepare.setString(4,shop_data.username);
                        
                        
                        
                        prepare.executeUpdate();
                        
                         alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful");
                        alert.showAndWait();
                        
                        menuShowOrderData();
                        menuRestart();
                

                    } else {
                        alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled");
                        alert.showAndWait();
                    }

                    
                }
                
               
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
      }
     
     
      
    @FXML
         public void menuRemoveBtn() {
        
        if (getid == 0) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the order you want to remove");
            alert.showAndWait();
        } else {
            String deleteData = "DELETE FROM customer WHERE id = " + getid;
            connect = database.connectDB();
            try {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this order?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                }
                
                menuShowOrderData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
         
        
    public void menuRestart() {
        totalP = 0;
        change = 0;
        amount = 0;
        menu_total.setText("$0.0");
        menu_amount.setText("");
        menu_change.setText("$0.0");
    }
      
     
     private int cID;
     
     
     public void customerID() {
        
        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = database.connectDB();
        
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) {
                cID = result.getInt("MAX(customer_id)");
            }
            
            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }
            
            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }
            
            shop_data.cID = cID;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
   
    public ObservableList<customersData> customersDataList() {
        
        ObservableList<customersData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM receipt";
        connect = database.connectDB();
        
        try {
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            customersData cData;
            
            while (result.next()) {
                cData = new customersData(result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date"),
                        result.getString("em_username"));
                
                listData.add(cData);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    
     
    private ObservableList<customersData> customersListData;
    
    public void customersShowData() {
        customersListData = customersDataList();
        
        customer_col_customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customer_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customer_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customer_col_cashier.setCellValueFactory(new PropertyValueFactory<>("emUsername"));
        
        customers_tableView.setItems(customersListData);
    } 
    
     
     
    @FXML
     public void switchForm(ActionEvent event) {
        
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(false);
            
            dashboardDisplayNC();
            dashboardDisplayTI();
            dashboardTotalI();
            dashboardNSP();
            dashboardIncomeChart();
            dashboardCustomerChart();
            
        } else if (event.getSource() == inventory_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            customers_form.setVisible(false);
            
            inventoryTypeList();
            inventoryStatusList();
            inventoryShowData();
        } else if (event.getSource() == menu_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            customers_form.setVisible(false);            
            menuDisplayCard();
            menuDisplayTotal();
            menuShowOrderData();
        } else if (event.getSource() == customers_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(true);
            
            customersShowData();
        }
        
    }
     
   
 
    @FXML
    public void logout(){
        
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout");
            
            Optional<ButtonType> option = alert.showAndWait();
            
            if(option.get().equals(ButtonType.OK)){
                
       
                
                logout_btn.getScene().getWindow().hide();
                
              
                
                
                
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new  Scene(root);
                
                stage.setTitle("Cafe Shop Management System");
                stage.setScene(scene);
                stage.show();
                
                
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    
    public void displayUsername(){
        
        String user = shop_data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayUsername();

        dashboardDisplayNC();
        dashboardDisplayTI();
        dashboardTotalI();
        dashboardNSP();
        dashboardIncomeChart();
        dashboardCustomerChart();
        
        inventoryTypeList();
        inventoryStatusList();
        inventoryShowData();
        menuDisplayCard();
        menuGetOrder(); 
        menuDisplayTotal();
        menuShowOrderData();
        customersShowData();
        
        
    
    }    
    
}

