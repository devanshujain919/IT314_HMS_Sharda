package Controller.Prescription;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import application.Main;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

@SuppressWarnings("deprecation")
public class Controller_Search_Patient implements Initializable
{
	private ObservableList<Patient_Info> patientList = FXCollections.observableArrayList();
	private ObservableList<Patient_Info> allPatients = FXCollections.observableArrayList();
	private Stage stage;
	private Main mainApp;
	private int count = 1;
	
	@FXML TextField search;
	@FXML TableView<Patient_Info> table_view;
	@FXML TableColumn<Patient_Info, String> s_no_col;
	@FXML TableColumn<Patient_Info, String> patient_name_col;
	
	@FXML TextField name;
	@FXML Label id_placeholder;
	@FXML TextArea birth_date, sex, marital_status, phone;
	@FXML TextArea emergency_name, emergency_relation, emergency_contact;
	@FXML TextArea address, city, state;	
	
	@FXML Button btn_choose, btn_cancel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		getFromDB();
		s_no_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patient_Info,String>, ObservableValue<String>>() 
		{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Patient_Info, String> param) 
			{
				return new ReadOnlyObjectWrapper(table_view.getItems().indexOf(param.getValue())+1);
			}
		});
		s_no_col.setSortable(false);
		patient_name_col.setCellValueFactory(cellData -> cellData.getValue().getFirst_name());
		table_view.setItems(patientList);
		showSelectedPatient(null);
		table_view.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSelectedPatient(newValue));
		
		search.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
			{
				if(newValue.length() > 3)
				{
					System.out.println("More than 3");
					// TODO: Logic to select relevant patientLists from allPatients
					patientList.clear();
					System.out.println(patientList.size());
					patientList.addAll(allPatients.subList(0, allPatients.size()/count));
					System.out.println(patientList.size());
					if(newValue.length() > oldValue.length())
					{
						count ++;
					}
					else if(newValue.length() < oldValue.length())
					{
						if(count != 1)
						{
							count --;
						}
					}
					System.out.println("count :  " + count);
					refreshTableView();
				}
			}
		});
	}
	
	@FXML
	private void handle_btn_choose()
	{
		//TODO
		System.out.println("Showing Search Prescription");
		try
		{
			FXMLLoader loader = new FXMLLoader();
			System.out.println("1");
			loader.setLocation(Main.class.getResource("/View/Prescription/Search_Prescription.fxml"));
			System.out.println("2");
			AnchorPane anchor_pane = (AnchorPane) loader.load();
			Main.getRootLayout().setCenter(anchor_pane);
			Controller_Search_Prescription controller = loader.getController();
			controller.setMainApp(mainApp);
			controller.setPatient(table_view.getSelectionModel().getSelectedItem());
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	@FXML
	private void handle_btn_cancel()
	{
		//TODO
	}
	
	private void refreshTableView() 
	{
		for(TableColumn tc : table_view.getColumns())
		{
			tc.setVisible(false);
			tc.setVisible(true);
		}
	}
	
	private void getFromDB()
	{
		for(int i = 0 ; i < 100 ; i ++)
		{
			Patient_Info p1 = new Patient_Info();
			p1.setPat_id(new SimpleStringProperty(Integer.toString(i)));
			p1.setFirst_name(new SimpleStringProperty("Devanshu" + i));
			p1.setLast_name(new SimpleStringProperty("Jain" + i));
			
			patientList.add(p1);
			allPatients.add(p1);
		}
		
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

	private void showSelectedPatient(Patient_Info pat_info)
	{
		if(pat_info == null)
		{
			name.setVisible(false);
			id_placeholder.setVisible(false);
			birth_date.setVisible(false);
			sex.setVisible(false);
			marital_status.setVisible(false);
			phone.setVisible(false);
			emergency_name.setVisible(false);
			emergency_contact.setVisible(false);
			emergency_relation.setVisible(false);
			address.setVisible(false);
			city.setVisible(false);
			state.setVisible(false);
		}
		else
		{
			name.setVisible(true);
			id_placeholder.setVisible(true);
			birth_date.setVisible(true);
			sex.setVisible(true);
			marital_status.setVisible(true);
			phone.setVisible(true);
			emergency_name.setVisible(true);
			emergency_contact.setVisible(true);
			emergency_relation.setVisible(true);
			address.setVisible(true);
			city.setVisible(true);
			state.setVisible(true);
			
			name.setText(pat_info.getFirst_name().getValue() + ", " + pat_info.getMiddle_name().getValue() + ", " + pat_info.getLast_name().getValue());
			id_placeholder.setText(pat_info.getPat_id().getValue());
			birth_date.setText(pat_info.getBirth_date().toString());
			sex.setText(pat_info.getSex().getValue());
			marital_status.setText(pat_info.getMarital_status().getValue());
			phone.setText(pat_info.getPhone().getValue());
			emergency_name.setText(pat_info.getEmergency_name().getValue());
			emergency_contact.setText(pat_info.getEmergency_contact().getValue());
			emergency_relation.setText(pat_info.getEmergency_relation().getValue());
			address.setText(pat_info.getAddress().getValue());
			city.setText(pat_info.getCity().getValue());
			state.setText(pat_info.getState().getValue());			
		}
	}
	
	public void setMainApp(Main main)
	{
		this.mainApp = main;
		this.stage = mainApp.getStage();
	}
	
}