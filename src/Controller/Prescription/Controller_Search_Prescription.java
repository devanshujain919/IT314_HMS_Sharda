package Controller.Prescription;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.Main;
import Model.Patient.Patient_Info;
import Model.Patient.Prescription;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Controller_Search_Prescription implements Initializable
{
	private ObservableList<Prescription> prescriptionList = FXCollections.observableArrayList();
	private Stage stage;
	private Main mainApp;
	
	@FXML TreeView<Prescription> tree_view = new TreeView<Prescription>();
	@FXML AnchorPane anchor_pane = new AnchorPane();
	@FXML AnchorPane anchor_right = new AnchorPane();
	@FXML AnchorPane anchor_left = new AnchorPane();
	@FXML BorderPane border_pane = new BorderPane();
	
	@FXML TextField patient_name = new TextField(), patient_id = new TextField();
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		System.out.println("Happy");
		patient_id.setEditable(false);
		patient_name.setEditable(false);
		
		getFromDB();
		
		TreeItem<Prescription> root = new TreeItem<Prescription>(new Prescription());
		root.getValue().setDate(LocalDate.MIN);
		tree_view.setRoot(root);
		
		
		for(Prescription p : prescriptionList)
		{
			TreeItem<Prescription> tree_item = new TreeItem<Prescription>();
			tree_item.setValue(p);
			root.getChildren().add(tree_item);
			
			tree_view.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Prescription>>() 
					{

                		@Override
                		public void changed(ObservableValue<? extends TreeItem<Prescription>> observable, TreeItem<Prescription> old_val, TreeItem<Prescription> new_val) 
                		{
                			TreeItem<Prescription> selectedItem = new_val;
                			System.out.println("Selected Text : " + selectedItem.getValue());
                			// 	do what ever you want
                			
                			                			
                			
                		}

					});
		}
	}
	
	public void setPatient(Patient_Info pat_info)
	{
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
		p1.setDisease(new SimpleStringProperty("Proness"));
		p1.setPat_id(new SimpleStringProperty("1"));
		p1.setRemarks(new SimpleStringProperty("hoolaaaaaa..."));
		
		Prescription p2 = new Prescription();
		p2.setDate(LocalDate.of(2015, 10, 15));
		p2.setDisease(new SimpleStringProperty("Beemari"));
		p2.setPat_id(new SimpleStringProperty("2"));
		p2.setRemarks(new SimpleStringProperty("hoolaaaaaa..."));
		
		prescriptionList.add(p1);
		prescriptionList.add(p2);
	}
	
		
	
}
