package Controller.Prescription;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import application.Main;
import Model.Patient.Medicine_Prescribed;
import Model.Patient.Patient_Info;
import Model.Patient.Prescription;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

@SuppressWarnings("deprecation")
public class Controller_Search_Prescription implements Initializable
{
	private Patient_Info pat_info;
	private ObservableList<Prescription> prescriptionList = FXCollections.observableArrayList();
	private Stage stage;
	private Main mainApp;
	
	@FXML TableView<Prescription> table_view = new TableView<Prescription> ();
	@FXML TableColumn<Prescription, String> s_no_col = new TableColumn<Prescription, String> ();
	@FXML TableColumn<Prescription, String> date_col = new TableColumn<Prescription, String> ();
	@FXML TableColumn<Prescription, String> time_col = new TableColumn<Prescription, String> ();
	
	@FXML GridPane grid_pane = new GridPane();
	@FXML TextArea pres_date = new TextArea();
	@FXML TextArea pres_follow = new TextArea();
	@FXML TextArea 	pres_disease = new TextArea();
	@FXML TextArea pres_remarks = new TextArea();
	
	@FXML Button btn_add_pres = new Button();
	@FXML Button btn_edit_pres = new Button();
	@FXML Button btn_see_med = new Button();
	@FXML Button btn_del_pres = new Button();
	
	@FXML TextField patient_name = new TextField(), patient_id = new TextField();
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		getFromDB();
		pres_date.setEditable(false);
		pres_follow.setEditable(false);
		pres_disease.setEditable(false);
		pres_remarks.setEditable(false);
		
		s_no_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Prescription,String>, ObservableValue<String>>() 
		{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Prescription, String> param) 
			{
				return new ReadOnlyObjectWrapper(table_view.getItems().indexOf(param.getValue())+1);
			}
		});
		s_no_col.setSortable(false);
		date_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));
		time_col.setCellValueFactory(cellData -> cellData.getValue().getTime());
		table_view.setItems(prescriptionList);
		showSelectedPrescription(null);
		table_view.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSelectedPrescription(newValue));
	}
	
	@FXML
	private void handle_btn_add_pres()
	{
		Prescription pres_info = new Prescription();
		pres_info.setPat_id(pat_info.getPat_id());
		pres_info.setDate(LocalDate.now());
		pres_info.setTime(new SimpleStringProperty(Integer.toString(Calendar.getInstance().HOUR_OF_DAY)));
		boolean isSaveClicked = showDialogAddPrescription(pres_info);
		
		if(isSaveClicked)
		{
			prescriptionList.add(pres_info);
		}
		
		System.out.println("WTF");
	}
	
	private boolean showDialogAddPrescription(Prescription pres_info)
	{
		boolean retValue = false;
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/Prescription/Add_Prescription.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add New Fees Type");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(stage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			Controller_Add_Prescription controller = loader.getController();
			System.out.println("Hi!!\n");
			controller.setStage(dialogStage);
			controller.setPrescription(pres_info);
			dialogStage.showAndWait();
			return controller.isSaveClicked();
			
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
		return retValue;
	}
	
	@FXML
	private void handle_btn_edit_pres()
	{
		int selectedIndex = table_view.getSelectionModel().getSelectedIndex();
		
		if(selectedIndex < 0)
		{
			Dialogs.create()
    		.owner(stage)
    		.title(" ALERT ")
    		.masthead(" Please select a prescription ")
    		.message("Need to select a prescription to edit... ")
    		.showWarning();
			return ;
		}
		
		Prescription pres_info = table_view.getItems().get(selectedIndex);
		boolean isSaveClicked = showDialogAddPrescription(pres_info);
		if(isSaveClicked)
		{
			refreshTableView();
			table_view.getSelectionModel().select(pres_info);
			showSelectedPrescription(pres_info);
		}		
	}
	
	private void refreshTableView() 
	{
		for(TableColumn tc : table_view.getColumns())
		{
			tc.setVisible(false);
			tc.setVisible(true);
		}
	}
	
	@FXML
	private void handle_btn_del_pres()
	{
		int selectedIndex = table_view.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0)
		{
			Prescription pres = table_view.getItems().get(selectedIndex);
			
			boolean db_del = DelDB(pres);
			if(db_del == false)
			{
				return ;
			}

			Prescription pres_info = table_view.getItems().remove(selectedIndex);
			System.out.println("Prescription Type deleted: " + pres_info.getDisease().getValue());
		}
		else
		{
			Dialogs.create()
    		.owner(stage)
    		.title(" ALERT ")
    		.masthead(" Please select a prescription ")
    		.message("Need to select a prescription to delete... ")
    		.showWarning();
		}
	}
	
	private boolean DelDB(Prescription pres) 
	{
		Connection con = Main.getConnection();
		if(con == null)
		{
			Main.setConnection(null);
			Main.setUsername("");
			Main.setPort("");
			Main.setpassword("");
			Main.setDbName("");
			Main.setIP("");
			
			Dialogs.create()
    		.owner(stage)
    		.title(" ALERT ")
    		.masthead(" Database is not setup ")
    		.message("Please set up the connection ")
    		.showWarning();
			return false;
		}
		PreparedStatement stmt = null;
		try
		{
			String query = "DELETE FROM Prescription WHERE pat_ID=? AND date=? AND time=?;";
			stmt = con.prepareStatement(query);
			stmt.setString(1, pres.getPat_id().getValue());
			stmt.setString(2, pres.getDate().toString());
			stmt.setString(3, pres.getTime().getValue());
			int no = stmt.executeUpdate(query);
			System.out.println("No.of rows deleted: " + no);
		}
		catch(SQLException E)
		{
			Dialogs.create()
    		.owner(stage)
    		.title(" ALERT ")
    		.masthead(" SQlException encountered ")
    		.message("Item could not be deleted ")
    		.showWarning();
			return false;
		}
		return true;
	}
	
	@FXML
	private void handle_btn_see_med()
	{
		int selectedIndex = table_view.getSelectionModel().getSelectedIndex();
		if(selectedIndex < 0)
		{
			Dialogs.create()
    		.owner(stage)
    		.title(" ALERT ")
    		.masthead(" Please select a prescription ")
    		.message("Need to select a prescription to see medicines for... ")
    		.showWarning();
			return;
		}
		Prescription pres_info = new Prescription();
		showDialogAddMedicine(pres_info);
	}
	
	private void showDialogAddMedicine(Prescription pres_info) 
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			System.out.println("1");
			loader.setLocation(Main.class.getResource("/View/Prescription/Search_Prescribed_Medicines.fxml"));
			System.out.println("2");
			AnchorPane anchor_pane = (AnchorPane) loader.load();
			Main.getRootLayout().setCenter(anchor_pane);
			Controller_Search_Prescribed_Medicines controller = loader.getController();
			controller.setMainApp(mainApp);
			controller.setPrescription(table_view.getSelectionModel().getSelectedItem(), pat_info);
			Main.getRootLayout().setCenter(anchor_pane);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}

	private void showSelectedPrescription(Prescription pres_info)
	{
		if(pres_info == null)
		{
			grid_pane.setVisible(false);
		}
		else
		{
			grid_pane.setVisible(true);
			
			pres_date.setText(pres_info.getDate().toString() + ", at " + pres_info.getTime().getValue());
			pres_follow.setText(pres_info.getFollow_up_date().toString());
			pres_disease.setText(pres_info.getDisease().getValue());
			pres_remarks.setText(pres_info.getRemarks().getValue());
			
			pres_date.setWrapText(true);
			pres_follow.setWrapText(true);
			pres_disease.setWrapText(true);
			pres_remarks.setWrapText(true);
		}
	}
	
	public void setPatient(Patient_Info pat_info)
	{
		this.pat_info = pat_info;
		System.out.println("Setting");
		patient_id.setText(pat_info.getPat_id().getValue());
		patient_name.setText(pat_info.getFirst_name().getValue() + ", " + pat_info.getLast_name().getValue());
	}
	
	public void setMainApp(Main main)
	{
		this.mainApp = main;
		this.stage = mainApp.getStage();
	}

	private void getFromDB()
	{
		//TODO
		Prescription p1 = new Prescription();
		p1.setDate(LocalDate.of(1994, 10, 18));
		p1.setTime(new SimpleStringProperty(Calendar.getInstance().HOUR_OF_DAY + ":" + Calendar.getInstance().MINUTE + ":" + Calendar.getInstance().SECOND));
		p1.setDisease(new SimpleStringProperty("Proness"));
		p1.setPat_id(new SimpleStringProperty("1"));
		p1.setRemarks(new SimpleStringProperty("hoolaaaaaa..."));
		
		Prescription p2 = new Prescription();
		p2.setDate(LocalDate.of(2015, 10, 15));
		p2.setTime(new SimpleStringProperty(Calendar.getInstance().HOUR_OF_DAY + ":" + Calendar.getInstance().MINUTE + ":" + Calendar.getInstance().SECOND));
		p2.setDisease(new SimpleStringProperty("Beemari"));
		p2.setPat_id(new SimpleStringProperty("2"));
		p2.setRemarks(new SimpleStringProperty("hoolaaaaaa..."));
		
		prescriptionList.add(p1);
		prescriptionList.add(p2);
	}
	
		
	
}
