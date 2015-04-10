/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Medication;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gunjan
 */
public class AddChartController implements Initializable 
{
  
    @FXML
    private DatePicker textDate;
    @FXML
    private TextField textTime;
    @FXML
    private TextField textMed;
    @FXML
    private TextField textDosage;
    @FXML
    private TextField textRemark;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @FXML 
    private Button buttonBack;
    @FXML
    private Button buttonSave;
     @FXML
    ObservableList<String> sugg = FXCollections.observableArrayList();
    @FXML
    private void goHome() throws IOException
    {
        home();
    }
    public void saveChart() throws IOException 
    {
        String date =  Integer.toString(textDate.getValue().getYear()) + "-" + Integer.toString(textDate.getValue().getMonthValue()) + "-" + Integer.toString(textDate.getValue().getDayOfMonth());
        System.out.println(date);
         String q = "Select medicine_ID from Medicine where medicine_name ='"+ textMed.getText() +"'";
                    ResultSet rs;
        try {
            rs = sen.db.doquery(q);
        
                    while(rs.next())
                    {
       String query = "Insert Into Medication values ('" + HomeController.PID + "','" + date + "','" +textTime.getText() + "','" + rs.getString("medicine_ID") + "','" +textDosage.getText()+"','"+ textRemark.getText()+"')";
            sen.db.doquery(query);
            home();
                    }
        } catch (SQLException ex) {
            Logger.getLogger(AddChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void home() throws IOException
    {
        Parent home = FXMLLoader.load(getClass().getResource("TestReports.fxml"));
        Scene scene_home = new Scene(home);
        Stage stage_home = (Stage) buttonBack.getScene().getWindow();
        stage_home.setScene(scene_home);
        stage_home.show();
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
       
    }
    
}

