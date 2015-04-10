/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Tests;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author gunjan
 */
public class AddTestReportController implements Initializable 
{
    @FXML
    private DatePicker textDate;
    @FXML
    private TextField textTime;
    @FXML
    private TextField textName;
    @FXML
    private TextArea textDetail;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonCancel;
    @FXML
    private void goHome(ActionEvent e) throws IOException
    {
     home();   
    }
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    @FXML
    public void saveEntry(ActionEvent e) throws IOException
    {
        System.out.println( "wtf");
       String date =  Integer.toString(textDate.getValue().getYear()) + "-" + Integer.toString(textDate.getValue().getMonthValue()) + "-" + Integer.toString(textDate.getValue().getDayOfMonth());
       System.out.println(date + "wtf");
      String q = "select test_ID from Test_Info where test_name ='" + textName.getText() + "'";
        try {
            ResultSet rs = sen.db.doquery(q);
            while(rs.next())
            {
                String q1 = "Insert into Patient_Tests values('" + rs.getString("test_ID") + "','" + HomeController.PID +"','" + textDetail.getText() + "','" + date + "','" + textTime.getText() + "')";
                sen.db.insertDataToDB(q1);
            }
            home();
            
            
            // add these to database
        } catch (SQLException ex) {
            error();
            Logger.getLogger(AddTestReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void home() throws IOException
    {
        Parent home = FXMLLoader.load(getClass().getResource("TestReports.fxml"));
        Scene scene_home = new Scene(home);
        Stage stage_home = (Stage) buttonCancel.getScene().getWindow();
        stage_home.setScene(scene_home);
        stage_home.show();
    }

        
 public void error()
 {
     JOptionPane.showMessageDialog(null, "Failed to insert data \n woohoo");
     
 }
         public void addHandlers()
         {
             
             
         }
    
}
