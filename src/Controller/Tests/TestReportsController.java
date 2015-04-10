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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import static jdk.nashorn.internal.objects.NativeRegExp.test;

/**
 *
 * @author gunjan
 */



public class TestReportsController implements Initializable
{
    HashMap<Label, TextField> t1 = new HashMap<Label, TextField> ();
    @FXML
    private Label labelMSG;
    @FXML
    private Button buttonBack;
        @FXML
    private Button buttonSave;
            @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonNew;
    @FXML
    private VBox vboxReport;
    private final Node rootChart = new ImageView( new Image(getClass().getResourceAsStream("chart.png")));
    private TreeItem<String> root = new TreeItem<> ("All Reports" , rootChart);
    TreeItem<String> date = new TreeItem<String>("");
    TreeItem<String> time = new TreeItem<String> ("");
    @FXML
    private TreeView<String> treeviewDate;  
    @FXML
    public Stage newStage;
    @FXML
    public void deleteReport(ActionEvent e)
    {
        buttonDelete.setVisible(false);
        String query = "select test_ID from Test_Info where test_name = '" + HomeController.testName + "'";
        ResultSet rs;
        try {
            rs = sen.db.doquery(query);
            while(rs.next())
            {
                String q2 = "delete from Patient_Tests where pat_ID = '" + HomeController.PID + "' and date = '" + HomeController.dateID + "' and time = '" + HomeController.timeID +"' and test_id = '" + rs.getString("test_ID") + "'";
                sen.db.doquery(q2);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(TestReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    public void goHome(ActionEvent e) throws IOException
    {
            Parent add_chart = FXMLLoader.load(getClass().getResource("home.fxml"));
            Scene scene_add_chart = new Scene(add_chart);
            Stage stage_chart = (Stage) buttonNew.getScene().getWindow();
            stage_chart.setScene(scene_add_chart);
            stage_chart.show();
    }
            
    @FXML
    public void saveReport(ActionEvent e)throws IOException
    {
        buttonSave.setVisible(false);
        String query = "select test_ID from Test_Info where test_name = '" + HomeController.testName + "'";
        
        for(Map.Entry<Label, TextField> entry : t1.entrySet())
        {
            
            Label lbl = entry.getKey();
            if(lbl.getText() == HomeController.testName)
            {
            HomeController.value = entry.getValue().getText();
            }
        }
        
        ResultSet rs;
        try {
            rs = sen.db.doquery(query);
            while(rs.next())
            {
                System.out.println("finally");
                String q2 = "update Patient_Tests set test_value = '" + HomeController.value + "' where pat_ID = '" + HomeController.PID + "' and test_ID = '" + rs.getString("test_ID") + "' and date ='"+ HomeController.dateID +"' and time = '" + HomeController.timeID + "'";
                sen.db.doquery(q2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    @FXML
    public void newEntry(ActionEvent e) throws IOException
        {
            Parent add_chart = FXMLLoader.load(getClass().getResource("AddTestReport.fxml"));
            Scene scene_add_chart = new Scene(add_chart);
            Stage stage_chart = (Stage) buttonNew.getScene().getWindow();
            stage_chart.setScene(scene_add_chart);
            stage_chart.show();
        }
        @Override
        public void initialize(URL url, ResourceBundle rb) 
        {    
            treeviewDate.setRoot(root);
            addHandlers();
            // remove this
            getDates();
            buttonDelete.setVisible(false);
            buttonSave.setVisible(false);
               
        }    
        public void addHandlers()
         {
             vboxReport.setSpacing(10);
             labelMSG.setVisible(false);
             treeviewDate.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>()
                   
        {
            @Override
            public void handle(javafx.scene.input.MouseEvent mouseEvent)
            {            
                if(mouseEvent.getClickCount() == 2)
                {
                    vboxReport.getChildren().clear();
                    t1.clear();
                    TreeItem<String> selected_time = treeviewDate.getSelectionModel().getSelectedItem();
                    TreeItem<String>  selected_date = selected_time.getParent();
                    HomeController.dateID = selected_date.getValue();
                    HomeController.timeID = selected_time.getValue();
                    String q = "Select test_ID,test_value  from Patient_Tests where pat_ID = '" + HomeController.PID +"' and date = '" + selected_date.getValue() +"' and time = '" + selected_time.getValue()+"'" ;
                    try {
                        ResultSet rs = sen.db.doquery(q);
                        while(rs.next())
                        {
                            String q2 = "select test_name from Test_Info where test_id = '" + rs.getString("test_ID") + "'";
                            ResultSet rs2 = sen.db.doquery(q2);
                            while(rs2.next())
                            {
                        Label lbl = new Label(rs2.getString("test_name"));
                        lbl.setOnMouseClicked(new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent event) {
                          if(mouseEvent.getClickCount() == 2)
                          {
                              HomeController.testName = lbl.getText();
                              buttonDelete.setVisible(true);
                              buttonSave.setVisible(true);
                          }
                            }
                        });
                        TextField tf = new TextField(rs.getString("test_value"));
                        tf.setOnKeyTyped(new EventHandler<KeyEvent>() {

                            @Override
                            public void handle(KeyEvent event) {
                                System.out.println("ggg");
                                if(event.getCode() == KeyCode.ENTER)
                                {
                                    System.out.println("afasf");
                                }
                            }
                        });
                        t1.put(lbl, tf);
                        vboxReport.getChildren().add(lbl);
                        vboxReport.getChildren().add(tf);
                            }
                        }
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(TestReportsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
             treeviewDate.setRoot(root);
             
         }
        public void getDates()
        {
            String query = "select distinct date from Patient_Tests where pat_ID = '" + HomeController.PID + "'";
            ResultSet rs,rs1;
        try {
            rs = sen.db.doquery(query);
             while(rs.next())
            {
                TreeItem<String> test = new TreeItem<String>(rs.getString("date"));
                root.getChildren().add(test);
                String q2 = "Select distinct time from Patient_Tests where pat_ID='" + HomeController.PID + "'" + "and date = '" + rs.getString("date") + "'";
                   rs1 = sen.db.doquery(q2);
                while(rs1.next())
                {
                    TreeItem<String> time = new TreeItem<String>(rs1.getString("time"));
                   test.getChildren().add(time);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
}
