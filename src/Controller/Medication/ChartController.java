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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gunjan
 */
public class ChartController implements Initializable 
{
    

    /*
    make changes between 2 comments
    */
    @FXML
    private final Node rootChart = new ImageView( new Image(getClass().getResourceAsStream("/Resources/chart.png")));
    private TreeItem<String> root = new TreeItem<> ("All Charts" , rootChart);
    TreeItem<String> date = new TreeItem<String>("");
    TreeItem<String> time = new TreeItem<String> ("");
    
    
    
    /*
    and here
    */
    @FXML
    private TableColumn<Meds,String> columnName;
    @FXML
    private TableColumn<Meds,String> columnDosage;
    @FXML
    private TableColumn<Meds,String> columnRemark;
    @FXML
    private TableCell name;
    @FXML
    private TableCell dosage;
    @FXML
    private TableCell remark;
    
    public Stage newStage;
    @FXML
    private Button buttonNew;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonBack;
    @FXML
    private Label labelPatient;
    @FXML
    private TreeView<String> treeviewDate;
    @FXML
    private TableView<Meds> tableChart;
    @FXML
    private HBox hboxMenu;
    @FXML
    final ObservableList<Meds> data=FXCollections.observableArrayList();
    @FXML
    private void newEntry(ActionEvent e) throws IOException {
        
        Parent add_chart = FXMLLoader.load(getClass().getResource("addChart.fxml"));
        Scene scene_add_chart = new Scene(add_chart);
        Stage stage_chart = (Stage) buttonNew.getScene().getWindow();
        stage_chart.setScene(scene_add_chart);
        stage_chart.show();
    }
    @FXML
    private void saveEntry(ActionEvent e) throws IOException
    {
        
    }
    @FXML
    private void deleteEntry(ActionEvent e) throws IOException
    {
        System.out.println(HomeController.PID);
        System.out.println(HomeController.dateID);
        System.out.println(HomeController.timeID);
        System.out.println(tableChart.getSelectionModel().getSelectedItem().getMedname());
        String q2 = "Select medicine_ID from Medicine where medicine_name = '" + tableChart.getSelectionModel().getSelectedItem().getMedname() +"'";
        try {
            ResultSet rs = sen.db.doquery(q2);
            while(rs.next())
            {
            String query = "Delete From Medication Where pat_ID = '" + HomeController.PID +"' and date ='" +HomeController.dateID + "' and time = '" + HomeController.timeID +"' and medicine_ID = '" +rs.getString("medicine_ID")+"'";
            sen.db.doquery(query);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void goHome(ActionEvent e) throws IOException
    {
        Parent home = FXMLLoader.load(getClass().getResource("home.fxml"));
        Scene scene_home = new Scene(home);
        Stage stage_home = (Stage) buttonNew.getScene().getWindow();
        stage_home.setScene(scene_home);
        stage_home.show();
        
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
   
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        treeviewDate.setOnMouseClicked(new EventHandler<MouseEvent>()
                   
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {            
                if(mouseEvent.getClickCount() == 2)
                {
                    
                    data.clear();
                    tableChart.setItems(data);
                    TreeItem<String> selected_time = treeviewDate.getSelectionModel().getSelectedItem();
                    TreeItem<String>  selected_date = selected_time.getParent();
                    HomeController.dateID = selected_date.getValue();
                    HomeController.timeID = selected_time.getValue();
                    String q = "Select medicine_ID,amount,remark  from Medication where pat_ID = '" + HomeController.PID +"' and date = '" + selected_date.getValue() +"' and time = '" + selected_time.getValue()+"'" ;
                    ResultSet rs;
                    try {
                        rs = sen.db.doquery(q);
                        while(rs.next())
                        {
                            String q1 = "select medicine_name from Medicine where medicine_ID = '" + rs.getString("medicine_ID") + "'";
                            ResultSet rs2 = sen.db.doquery(q1);
                           System.out.println(rs.getString("medicine_ID"));
                            while(rs2.next())
                            {
                                data.add(new Meds(rs2.getString("medicine_name"),rs.getString("amount"),rs.getString("remark")));
                            }
                            
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tableChart.setItems(data);
                }
            }
        });
        treeviewDate.setRoot(root);
              
    tableChart.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) 
            {
                if(event.getClickCount() > 1)
                {
                   Meds m = tableChart.getSelectionModel().getSelectedItem();
                   HomeController.medName = m.medname;
                }
                
            }
        });
   columnName.setCellValueFactory(new PropertyValueFactory<>("medname"));
   columnDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
   columnRemark.setCellValueFactory(new PropertyValueFactory<>("remark"));
   
   columnName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Meds, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Meds, String> event) {
                try {
                    String query1 = "Select medicine_ID from Medicine where medicine_name='"+event.getNewValue()+"'"; 
                    
                    ResultSet rs =sen.db.doquery(query1);
                    while(rs.next())
                    {
                    String query = "Update Medication set medicine_ID ='" + rs.getString("medicine_ID") + "' where pat_ID ='" + HomeController.PID + "' and date ='" + HomeController.dateID + "' and time ='" + HomeController.timeID + "'" ;
                    sen.db.doquery(query);
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
     columnDosage.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Meds, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Meds, String> event) {
                try {
                    String q = "Select medicine_ID from Medicine where medicine_name ='"+ HomeController.medName +"'";
                    ResultSet rs = sen.db.doquery(q);
                    while(rs.next())
                    {
                    String query = "Update Medication set amount = '" + event.getNewValue() + "' where pat_ID ='" + HomeController.PID + "' and date ='" + HomeController.dateID + "' and time ='" + HomeController.timeID + "' and medicine_ID ='" + rs.getString("medicine_ID") + "'" ;
                    sen.db.doquery(query);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
     columnRemark.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Meds, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Meds, String> event) {
                try {
                     String q = "Select medicine_ID from Medicine where medicine_name ='"+ HomeController.medName +"'";
                    ResultSet rs = sen.db.doquery(q);
                    while(rs.next())
                    {
                    String query = "Update Medication set remark = '" + event.getNewValue() + "' where pat_ID ='" + HomeController.PID + "' and date ='" + HomeController.dateID + "' and time ='" + HomeController.timeID + "' and medicine_ID='" + rs.getString("medicine_ID") +"'" ;
                    sen.db.doquery(query);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
   columnName.setCellFactory(TextFieldTableCell.forTableColumn());
   columnDosage.setCellFactory(TextFieldTableCell.forTableColumn());
   columnRemark.setCellFactory(TextFieldTableCell.forTableColumn());
   tableChart.setEditable(true);
   getDates();
    }
    public void getDates() 
    {
        String query = "Select distinct date from Medication where pat_ID='" + HomeController.PID + "'";
        ResultSet rs,rs1;
        try {
            rs = sen.db.doquery(query);
            while(rs.next())
            {
                TreeItem<String> test = new TreeItem<String>(rs.getString("date"));
                root.getChildren().add(test);
                System.out.println(rs.getString("date"));
                String q2 = "Select distinct time from Medication where pat_ID='" + HomeController.PID + "'" + "and date = '" + rs.getString("date") + "'";
                rs1 = sen.db.doquery(q2);
                while(rs1.next())
                {
                    TreeItem<String> time = new TreeItem<String>(rs1.getString("time"));
                   test.getChildren().add(time);
                    System.out.println(rs1.getString("time"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChartController.class.getName()).log(Level.SEVERE, null, ex);
        }
                  
    }

    public static class Meds {

        public Meds(String a, String b, String c) {
            this.medname = a;
            this.dosage =b;
            this.remark = c;
            
        }
            private String medname;

        public String getMedname() {
            return medname;
        }

        public void setMedname(String medname) {
            this.medname = medname;
        }

        public String getDosage() {
            return dosage;
        }

        public void setDosage(String dosage) {
            this.dosage = dosage;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
            private String dosage;
            private  String remark;
    }
    

    

}
    
    

