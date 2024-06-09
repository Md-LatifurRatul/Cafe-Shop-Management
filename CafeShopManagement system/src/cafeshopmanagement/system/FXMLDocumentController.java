/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package cafeshopmanagement.system;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.sql.*;
import java.util.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
/**
 *
 * @author kmrsk
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane si_loginForm;
    @FXML
    private TextField si_username;
    @FXML
    private Button si_loginBtn;
    @FXML
    private Hyperlink si_forgotPass;
    @FXML
    private PasswordField si_password;
    @FXML
    private AnchorPane su_signUpForm;
    @FXML
    private TextField su_username;
    @FXML
    private Button su_signUpBtn;
    @FXML
    private PasswordField su_password;
    @FXML
    private ComboBox<?> su_question;
    @FXML
    private TextField su_answer;
    @FXML
    private AnchorPane side_form;
    @FXML
    private Button side_create_btn;
    @FXML
    private Button side_alreadyHave;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private  Alert alert;
    @FXML
    private AnchorPane fp_question_form;
    @FXML
    private Button fp_proceed;
    @FXML
    private ComboBox<?> fp_question;
    @FXML
    private TextField fp_answer;
    @FXML
    private Button fp_back;
    @FXML
    private AnchorPane np_newPasswordForm;
    @FXML
    private PasswordField np_newPassword;
    @FXML
    private PasswordField np_confirmPassword;
    @FXML
    private Button np_changePassword;
    @FXML
    private Button np_back;
    @FXML
    private TextField fp_username;
    

    @FXML
    public void loginBtn() {

        if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/Password");
            alert.showAndWait();

        } else {
            String selectData = "SELECT username, password FROM employee WHERE username = ? and password = ?";

            connect = database.connectDB();

            try {

                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());

                //IF SUCCEFFULY LOGIN THEN PROCEED TO THE ANOTHER FORM WHICH IS MAIN FORM
                result = prepare.executeQuery();
                if (result.next()) {
                    
                    //To get the user name that the user used
                    
                    
                    shop_data.username = si_username.getText();
                    
                    
                    
                    
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    
                    //Linking to my main form
                    
                    Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    stage.setTitle("Cafe Shop Management System");
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);
                    
                    
                    stage.setScene(scene);
                    stage.show();
                    
                    si_loginBtn.getScene().getWindow().hide();
                    
                    
                } 
                else {                //IF NOT THEN ERROR MESSAGE WILL SHOW

                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/password");
                    alert.showAndWait();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    
    
   
    @FXML
    public void regBtn() {
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty() || su_question.getSelectionModel().getSelectedItem() == null || su_answer.getText().isEmpty()) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            String regData = "INSERT INTO employee (username, password, question, answer, date) " + "VALUES(?,?,?,?,?)";
            connect = database.connectDB();

            try {
                //CHECKING IF THE USERNAME IS ALREADY IN THE RECORDED DATABASE

                String checkUsername = "SELECT username FROM employee WHERE username = '" + su_username.getText() + "'";

                prepare = connect.prepareStatement(checkUsername);

                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(su_username.getText() + " is already taken");
                    alert.showAndWait();

                } else if (su_password.getText().length() < 8) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid Password, atleast 8 character are needed");
                    alert.showAndWait();

                } else {

                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());
                    prepare.setString(3, (String) su_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, su_answer.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setContentText("Successfully registered Account!");
                    alert.showAndWait();

                    su_username.setText("");
                    su_password.setText("");
                    su_question.getSelectionModel().clearSelection();
                    su_answer.setText("");

                    TranslateTransition slider = new TranslateTransition();

                    slider.setNode(side_form);
                    slider.setToX(0);
                    slider.setDuration(Duration.seconds(.5));
                    slider.setOnFinished((ActionEvent e) -> {
                        side_alreadyHave.setVisible(false);
                        side_create_btn.setVisible(true);

                    });
                    slider.play();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    
    public String[] questionList = {"What is your favourite Color?", "What is favourite food?","What is your birth date?"};
    
    public void regLquestionList(){
        List<String> listQ = new ArrayList<>();
        
        for(String data: questionList){
            listQ.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(listQ);
        su_question.setItems(listData);
    }

    
    
    @FXML
    public void switchForgotePassword(){
        
        fp_question_form.setVisible(true);
        si_loginForm.setVisible(false);
        
        forgotPassQuestionList();
        
        
    }
    
    
    @FXML
    public void proccedBtn(){
        
        if(fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem()== null || fp_answer.getText().isEmpty() ){
             alert = new Alert(AlertType.ERROR);
             
             alert.setTitle("Error Message");
             alert.setHeaderText(null);
             alert.setContentText("Please fill all blank fields");
             alert.showAndWait();
        }
        else{
            String selectData = "SELECT username, question, answer FROM employee WHERE username = ? AND question = ? AND answer = ?";
            
            connect = database.connectDB();
            
            try{
                
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, (String)fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());
                result = prepare.executeQuery();
                
                if(result.next()){
                    np_newPasswordForm.setVisible(true);
                    fp_question_form.setVisible(false);
                    
                          } else {
                    alert = new Alert(AlertType.ERROR);

                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Information");
                    alert.showAndWait();

                }

            } catch (Exception e) {
                e.printStackTrace();

            }

        }

    }

    @FXML
    public void changePassBtn() {
        if (np_newPassword.getText().isEmpty() || np_confirmPassword.getText().isEmpty()) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the blank fields");
            alert.showAndWait();

        } else {

            if (np_newPassword.getText().equals(np_confirmPassword.getText())) {

                String getDate = "SELECT date FROM employee WHERE username = '"
                        + fp_username.getText() + "'";

                connect = database.connectDB();
                try {

                    prepare = connect.prepareStatement(getDate);
                    result = prepare.executeQuery();
                    String date = "";

                    if (result.next()) {
                        date = result.getString("date");

                    }

                    String updatesPassword = "UPDATE employee SET password = '"
                            + np_newPassword.getText()
                            + "', question = '"
                            + fp_question.getSelectionModel().getSelectedItem() + "', answer = '"
                            + fp_answer.getText() + "', date = '"
                            + date + "' WHERE username = '"
                            + fp_username.getText() + "'";

                    prepare = connect.prepareStatement(updatesPassword);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully changed password!");
                    alert.showAndWait();

                    si_loginForm.setVisible(true);
                    np_newPasswordForm.setVisible(false);

                    // TO CLEAR FIELDS
                    np_confirmPassword.setText("");
                    np_newPassword.setText("");
                    fp_question.getSelectionModel().clearSelection();
                    fp_answer.setText("");
                    fp_username.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Not match");
                alert.showAndWait();
            }

        }
    }

    
    
    public void forgotPassQuestionList(){
        
        List<String> listQ = new ArrayList<>();
        
        for(String data: questionList){
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);
        
    }
    
    
    @FXML
    public void backToLoginForm(){
        si_loginForm.setVisible(true);
        fp_question_form.setVisible(false);
        
        
    }
    
    @FXML
    public void backToQuestionForm(){
        fp_question_form.setVisible(true);
        np_newPasswordForm.setVisible(false);
        
    }
    
    
    @FXML
    public void switchForm(ActionEvent event){
        
        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == side_create_btn) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(true);
                side_create_btn.setVisible(false);
                
                fp_question_form.setVisible(false);
                si_loginForm.setVisible(true);
                
                np_newPasswordForm.setVisible(false);
                
                
                
                regLquestionList();

            });
            slider.play();

        } else if (event.getSource() == side_alreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(false);
                side_create_btn.setVisible(true);
                
                  fp_question_form.setVisible(false);
                si_loginForm.setVisible(true);
                
                np_newPasswordForm.setVisible(false);

            });
            slider.play();

        }
               
    }
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    
}


// 1:10:02 sec