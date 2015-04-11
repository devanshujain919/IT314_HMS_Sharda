package Controller.Search;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import application.Main;
import Model.Patient.Indoor_Patient;
import Model.Patient.Patient_Info;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

@SuppressWarnings("deprecation")
public class Controller_Indoor_Patient implements Initializable 
{
	
	private ObservableList<Indoor_Patient> indoorPatientList = FXCollections.observableArrayList();
	private ObservableList<Patient_Info> allPatients = FXCollections.observableArrayList();
	
	private Main mainApp;
	private Stage stage;
	
	@FXML private Button btn_add = new Button();
	@FXML private Button btn_edit = new Button();
	@FXML private Button btn_del = new Button();
	@FXML private Button btn_discharge = new Button();
	
	@FXML private TextArea pat_id = new TextArea();
	@FXML private TextArea date_admit = new TextArea();
	@FXML private TextArea time_admit = new TextArea();
	@FXML private TextArea date_discharge = new TextArea();
	@FXML private TextArea time_discharge = new TextArea();
	
	@FXML private TableView<Indoor_Patient> table_view = new TableView<Indoor_Patient>();
	@FXML private TableColumn<Indoor_Patient, String> s_no_col = new TableColumn<Indoor_Patient, String> ();
	@FXML private TableColumn<Indoor_Patient, String> patient_name_col = new TableColumn<Indoor_Patient, String> ();
	
	@FXML private GridPane grid_pane = new GridPane();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		
	}
	
	private void handle_btn_add()
	{
		
	}
	
	private void handle_btn_edit()
	{
		
	}
	
	private void handle_btn_del()
	{
		
	}
	
	private void handle_btn_discharge()
	{
		
	}
	
	private void getFromDB()
	{
		Connection con = Main.getConnection();
		if(con == null)
		{
			Dialogs.create()
    		.owner(stage)
    		.title(" ALERT ")
    		.masthead(" Database is not setup ")
    		.message("Please set up the connection ")
    		.showWarning();
			return ;
		}
		else
		{
			PreparedStatement stmt = null;
			try
			{
				String query = "SELECT * FROM Patient_Info;";
				stmt = con.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();
				while(rs.next())
				{
					Patient_Info pat_info = new Patient_Info();
					pat_info.setPat_id(new SimpleStringProperty(rs.getString("pat_id")));
					allPatients.add(pat_info);
				}
				stmt.close();
				
				query = "SELECT * FROM Indoor_patient;";
				stmt = con.prepareStatement(query);
				rs = stmt.executeQuery();
				while(rs.next())
				{
					Indoor_Patient pat_info = new Indoor_Patient();
					pat_info.setPat_id(new SimpleStringProperty(rs.getString("pat_ID")));
					pat_info.setDate_of_admission(new SimpleStringProperty(rs.getDate("Date_of_admission").toString()));
					pat_info.setTime_of_admission(new SimpleStringProperty(rs.getTime("Time_of_admission").toString()));
					pat_info.setDate_of_discharge(new SimpleStringProperty(rs.getDate("Date_of_discharge").toString()));
					pat_info.setTime_of_discharge(new SimpleStringProperty(rs.getTime("Time_of_discharge").toString()));
					pat_info.setRoomNo(new SimpleStringProperty(rs.getString(rs.getString("Room_no"))));
				}
				stmt.close();
			}
			catch(SQLException E)
			{
				Dialogs.create()
	    		.owner(stage)
	    		.title(" ALERT ")
	    		.masthead(" Database is not setup ")
	    		.message("Items could not be loaded... ")
	    		.showWarning();
				return ;
			}
		}
	}
	
	public void setMaainApp(Main main)
	{
		this.mainApp = main;
		this.stage = mainApp.getStage();
	}
	
}
