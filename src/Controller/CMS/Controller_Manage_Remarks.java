package Controller.CMS;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import Model.CMS.Remarks_Info;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller_Manage_Remarks implements Initializable
{
	private ObservableList<Remarks_Info> remarksList = FXCollections.observableArrayList();
	private Stage primaryStage;
	private Main mainApp;
	
	@FXML AnchorPane anchor_pane = new AnchorPane();
	
	@FXML SplitPane split_pane = new SplitPane();
	
	@FXML TableView<Remarks_Info> table_view;
	
	@FXML TableColumn<Remarks_Info, String> remark_eng_col = new TableColumn<Remarks_Info, String> ();
	
	@FXML TableColumn<Remarks_Info, String> remark_guj_col = new TableColumn<Remarks_Info, String>();
	
	@FXML Button btn_add = new Button("Add New");
	
	@FXML Button btn_edit = new Button("Edit");
	
	@FXML Button btn_del = new Button("Delete");
	
	@FXML GridPane grid_pane = new GridPane();
	
	@FXML TextArea remark_id = new TextArea();
	
	@FXML TextArea remark_eng = new TextArea();
	
	@FXML TextArea remark_guj = new TextArea();
	
	@FXML TextArea remark_context = new TextArea();
	
	@FXML Label remark_id_label = new Label();
	
	@FXML Label remark_eng_label = new Label();
	
	@FXML Label remark_guj_label = new Label();
	
	@FXML Label remark_context_label = new Label();
	
	public void getFromDB()
	{
		Remarks_Info r1 = new Remarks_Info("Disease", "rog", "death");
		Remarks_Info r2 = new Remarks_Info("Disease2", "rog biju", "death 2");
		remarksList.add(r1);
		remarksList.add(r2);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{		
		getFromDB();
		
		remark_id.setEditable(false);
		remark_eng.setEditable(false);
		
		Font gujju_font = Font.loadFont(getClass().getResourceAsStream("/Resources/Gujrati-Saral-1.ttf"), 20);
		remark_guj.setFont(gujju_font);
		remark_guj.setEditable(false);
		
		remark_context.setEditable(false);
		
		remark_eng_col.setCellValueFactory(cellData -> cellData.getValue().get_english_text());
		remark_guj_col.setCellValueFactory(cellData -> cellData.getValue().get_gujarati_text());
		
		table_view.setItems(remarksList);
		
		showSelectedRemark(null);
		
		table_view.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSelectedRemark(newValue));

	}
	
	private void showSelectedRemark(Remarks_Info remark_info)
	{
		if(remark_info == null)
		{
			remark_id_label.setVisible(false);
			remark_eng_label.setVisible(false);
			remark_guj_label.setVisible(false);
			remark_context_label.setVisible(false);
			
			remark_id.setVisible(false);
			remark_eng.setVisible(false);
			remark_guj.setVisible(false);
			remark_context.setVisible(false);
		}
		else
		{
			remark_id_label.setVisible(true);
			remark_eng_label.setVisible(true);
			remark_guj_label.setVisible(true);
			remark_context_label.setVisible(true);
			
			remark_id.setVisible(true);
			remark_eng.setVisible(true);
			remark_guj.setVisible(true);
			remark_context.setVisible(true);
						
			remark_id.setText("Nothing");
			remark_eng.setText(remark_info.get_english_text().getValue());
			remark_guj.setText(remark_info.get_gujarati_text().getValue());
			remark_context.setText(remark_info.get_context().getValue());
			
			remark_id_label.setWrapText(true);
			remark_eng_label.setWrapText(true);
			remark_guj_label.setWrapText(true);
			remark_context_label.setWrapText(true);
			
			remark_id.setWrapText(true);
			remark_eng.setWrapText(true);
			remark_guj.setWrapText(true);
			remark_context.setWrapText(true);
		}
	}
	
	@FXML
	private void handle_btn_add()
	{
		Remarks_Info remark_info = new Remarks_Info();
		boolean isSaveClicked = showDialogAddRemark(remark_info);
		
		if(isSaveClicked)
		{
			remarksList.add(remark_info);
		}
	}
	
	private boolean showDialogAddRemark(Remarks_Info remark_info) 
	{
		boolean retValue = false;
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/View/CMS/Dialog_Add_Remarks.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add New Remark");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			Controller_Add_Remarks controller = loader.getController();
			System.out.println("Hi!!\n");
			controller.setStage(dialogStage);
			controller.setRemark(remark_info);
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
	private void handle_btn_edit()
	{
		int selectedIndex = table_view.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0)
		{
			Remarks_Info remark_info = table_view.getItems().get(selectedIndex);
			boolean isSaveClicked = showDialogAddRemark(remark_info);
			if(isSaveClicked)
			{
				refreshTableView();
				table_view.getSelectionModel().select(remark_info);
				showSelectedRemark(remark_info);
			}
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
	private void handle_btn_del()
	{
		int selectedIndex = table_view.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0)
		{
			Remarks_Info remark_info = table_view.getItems().remove(selectedIndex);
			System.out.println("Medicine deleted: " + remark_info.get_english_text().getValue());
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(primaryStage);
			alert.setTitle("Nothing has been selected...");
			alert.setHeaderText("No Medicine Selected");
			alert.setContentText("Please select a medicine in the table.");
			
			alert.showAndWait();
		}
	}

	public void setMainApp(Main main) 
	{
		this.mainApp = main;
		this.primaryStage = mainApp.getStage();
	}
	
}
