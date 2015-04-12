package Controller.AdmitPatient;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import application.Main;
import Controller.Prescription.Controller_Search_Patient;
import Model.CMS.Tests_Info;
import Model.Patient.Indoor_Patient;
import Model.Patient.Patient_Info;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

@SuppressWarnings("deprecation")
public class Controller_Indoor_Patient implements Initializable 
{
	
	private ObservableList<Indoor_Patient> indoorPatientList = FXCollections.observableArrayList();
	private ObservableList<Patient_Info> allPatients = FXCollections.observableArrayList();
	private HashMap<String, String> pat_id_name = new HashMap<String, String>();
	public static Patient_Info pat_info;
	
	public static String dateID, timeID, medName, testName, value;
	
	private Main mainApp;
	private Stage stage;
	
	@FXML private Button btn_admit = new Button();
	@FXML private Button btn_edit = new Button();
	@FXML private Button btn_del = new Button();
	@FXML private Button btn_discharge = new Button();
	
	@FXML private Button btn_test = new Button();
	@FXML private Button btn_chart = new Button();
	
	@FXML private TextField pat_id = new TextField();
	@FXML private TextField date_admit = new TextField();
	@FXML private TextField time_admit = new TextField();
	@FXML private TextField date_discharge = new TextField();
	@FXML private TextField time_discharge = new TextField();
	@FXML private TextField room_no = new TextField();
	
	@FXML private TableView<Indoor_Patient> table_view = new TableView<Indoor_Patient>();
	@FXML private TableColumn<Indoor_Patient, String> s_no_col = new TableColumn<Indoor_Patient, String> ();
	@FXML private TableColumn<Indoor_Patient, String> patient_name_col = new TableColumn<Indoor_Patient, String> ();
	
	@FXML private GridPane grid_pane = new GridPane();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		getFromDB();
		pat_id.setEditable(false);
		date_admit.setEditable(false);
		time_admit.setEditable(false);
		date_discharge.setEditable(false);
		time_discharge.setEditable(false);
		room_no.setEditable(false);
		
		s_no_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Indoor_Patient,String>, ObservableValue<String>>() 
			{
				@Override
				public ObservableValue<String> call(CellDataFeatures<Indoor_Patient, String> param) 
				{
					return new ReadOnlyObjectWrapper(table_view.getItems().indexOf(param.getValue())+1);
				}
			});
		patient_name_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Indoor_Patient,String>, ObservableValue<String>>() 
				{
					@Override
					public ObservableValue<String> call(CellDataFeatures<Indoor_Patient, String> param) 
					{
						return new ReadOnlyObjectWrapper(pat_id_name.get(param.getValue().getPat_id().getValue()));
					}
				});
		table_view.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSelectedPatient(newValue));
	}
	
	private Object showSelectedPatient(Indoor_Patient newValue) 
	{
		if(newValue == null)
		{
			grid_pane.setVisible(false);
		}
		else
		{
			grid_pane.setVisible(true);
			pat_id.setText(newValue.getPat_id().getValue());
			date_admit.setText(newValue.getDate_of_admission().getValue());
			time_admit.setText(newValue.getTime_of_admission().getValue());
			date_discharge.setText(newValue.getDate_of_discharge().getValue());
			time_discharge.setText(newValue.getTime_of_discharge().getValue());
			room_no.setText(newValue.getRoomNo().getValue());
		}
		return null;
	}
	
	@FXML
	private void handle_btn_chart()
	{
		if(table_view.getSelectionModel().getSelectedIndex() < 0)
        {
			if(table_view.getSelectionModel().getSelectedIndex() < 0)
			{
				Dialogs.create()
	    		.owner(stage)
	    		.title(" ALERT ")
	    		.masthead(" No selection found ")
	    		.message("Please select a patient... ")
	    		.showWarning();
				return ;
			}
        }
        else
        {
        	try
        	{
        		for(Patient_Info pi : allPatients)
    			{
    				if(pi.getPat_id().equals(table_view.getSelectionModel().getSelectedItem().getPat_id()))
    				{
    					Controller_Indoor_Patient.pat_info = pi;
    					break;
    				}
    			}
        		
	        	Parent chart = FXMLLoader.load(getClass().getResource("/View/IndoorPatient/chart.fxml"));
		        Scene scene_chart = new Scene(chart);
		        Stage stage_chart = (Stage) btn_chart.getScene().getWindow();
		        stage_chart.setScene(scene_chart);
		        stage_chart.show();
        	}
        	catch(IOException E)
        	{
        		E.printStackTrace();
        	}
        }
	}
	
	@FXML
	private void handle_btn_test()
	{
		if(table_view.getSelectionModel().getSelectedIndex() < 0)
        {
			if(table_view.getSelectionModel().getSelectedIndex() < 0)
			{
				Dialogs.create()
	    		.owner(stage)
	    		.title(" ALERT ")
	    		.masthead(" No selection found ")
	    		.message("Please select a patient... ")
	    		.showWarning();
				return ;
			}
        }
        else
        {
	        // check for valid patient
	        try
	        {
	        	for(Patient_Info pi : allPatients)
				{
					if(pi.getPat_id().equals(table_view.getSelectionModel().getSelectedItem().getPat_id()))
					{
						Controller_Indoor_Patient.pat_info = pi;
						break;
					}
				}
	        	
	        	Parent chart = FXMLLoader.load(getClass().getResource("/View/IndoorPatient/TestReports.fxml"));
		        Scene scene_chart = new Scene(chart);
		        Stage stage_chart = (Stage) btn_chart.getScene().getWindow();
		        stage_chart.setScene(scene_chart);
		        stage_chart.show();
	        }
	        catch(IOException E)
	        {
	        	E.printStackTrace();
	        }
        }
	}
	
	@FXML
	private void handle_btn_del()
	{
		//TODO
		//Delete test reports and medication reports
		
		if(table_view.getSelectionModel().getSelectedIndex() < 0)
		{
			Dialogs.create()
    		.owner(stage)
    		.title(" ALERT ")
    		.masthead(" No selection found ")
    		.message("Please select a patient... ")
    		.showWarning();
			return ;
		}		
	}
	
	@FXML
	private void handle_btn_discharge()
	{
		
		if(table_view.getSelectionModel().getSelectedIndex() < 0)
		{
			Dialogs.create()
    		.owner(stage)
    		.title(" ALERT ")
    		.masthead(" No selection found ")
    		.message("Please select a patient... ")
    		.showWarning();
			return ;
		}
		
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/AdmitPatient/DischargePatient.fxml"));
			AnchorPane anchor_pane = (AnchorPane) loader.load();
			Main.getRootLayout().setCenter(anchor_pane);
			Controller_Discharge_Patient controller = loader.getController();
			controller.setMainApp(mainApp);
			for(Patient_Info pi : allPatients)
			{
				if(pi.getPat_id().equals(table_view.getSelectionModel().getSelectedItem().getPat_id()))
				{
					Controller_Indoor_Patient.pat_info = pi;
					break;
				}
			}
			controller.setDetails(table_view.getSelectionModel().getSelectedItem(), Controller_Indoor_Patient.pat_info);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	@FXML
	private void handle_btn_admit()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/AdmitPatient/Search_Patient.fxml"));
			AnchorPane anchor_pane = (AnchorPane) loader.load();
			Main.getRootLayout().setCenter(anchor_pane);
			Controller_Search_Patient_Admit controller = loader.getController();
			controller.setMainApp(mainApp);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	@FXML
	private void handle_btn_edit()
	{
		if(table_view.getSelectionModel().getSelectedIndex() < 0)
		{
			Dialogs.create()
    		.owner(stage)
    		.title(" ALERT ")
    		.masthead(" No selection found ")
    		.message("Please select a patient... ")
    		.showWarning();
			return ;
		}
		
		Indoor_Patient ind_pat_info = table_view.getSelectionModel().getSelectedItem();
		String name = "";
		for(Patient_Info pi : allPatients)
		{
			if(pi.getPat_id().equals(ind_pat_info.getPat_id()))
			{
				name = pi.getFirst_name().getValue();
				break;
			}
		}
		
		showDialogAdmitPatient(ind_pat_info, name);

	}
	
	private boolean showDialogAdmitPatient(Indoor_Patient ind_pat_info, String name)
	{
		boolean retValue = false;
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/AdmitPatient/Dialog_Admit_Patient.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Patient Details");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			Controller_Admit_Patient controller = loader.getController();
			System.out.println("Hi!!\n");
			controller.setStage(dialogStage);
			controller.setDetails(ind_pat_info, name);
			dialogStage.showAndWait();
			return controller.isSaveClicked();
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
		return retValue;
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
					pat_info.setFirst_name(new SimpleStringProperty(rs.getString("name")));
	                pat_info.setAddress(new SimpleStringProperty(rs.getString("Address")));
	                pat_info.setBirth_date((rs.getDate("Birth_date").toLocalDate()));
	                pat_info.setCity(new SimpleStringProperty(rs.getString("City")));
	                pat_info.setState(new SimpleStringProperty(rs.getString("State")));
	                pat_info.setEmergency_contact(new SimpleStringProperty(rs.getString("Emergency_contact")));
	                pat_info.setEmergency_name(new SimpleStringProperty(rs.getString("Emergency_name")));
	                pat_info.setEmergency_relation(new SimpleStringProperty(rs.getString("Emergency_relation")));
	                pat_info.setMarital_status(new SimpleStringProperty(rs.getString("Marital_status")));
	                pat_info.setPhone(new SimpleStringProperty(rs.getString("Phone")));
	                pat_info.setSex(new SimpleStringProperty(rs.getString("Sex")));
	                pat_info.setPat_id(new SimpleStringProperty(rs.getString("pat_ID")));
					allPatients.add(pat_info);
				}
								
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
					indoorPatientList.add(pat_info);
				}
				stmt.close();
				
				for(Indoor_Patient ind_pat_info : indoorPatientList)
				{
					for(Patient_Info pat_info : allPatients)
					{
						if(ind_pat_info.getPat_id().equals(pat_info.getPat_id()))
						{
							pat_id_name.put(ind_pat_info.getPat_id().getValue(), pat_info.getFirst_name().getValue());
							Controller_Indoor_Patient.pat_info = pat_info;
							break;
						}
					}
				}
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
	
	public void setMainApp(Main main)
	{
		this.mainApp = main;
		this.stage = mainApp.getStage();
	}
	
}
