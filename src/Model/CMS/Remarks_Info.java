package Model.CMS;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * This is a model file for storing data about the various types of remarks, the doctor can store to suggest to the patients. It is so that, he does not have to type them everytime. It will be a dropdown.
 * Author: Devanshu Jain
 */

public class Remarks_Info 
{
	private StringProperty remark_id;
	private StringProperty remark_english; // In english language
	private StringProperty remark_gujarati; // In Gujarati (transliterated) language
	private StringProperty remark_context; // Context of the remark
	
	/*
	 * Constructors
	 */
	
	public Remarks_Info()
	{
		
	}
	
	public Remarks_Info(String english, String gujarati, String context)
	{
		this.remark_english = new SimpleStringProperty(english);
		this.remark_gujarati = new SimpleStringProperty(gujarati);
		this.remark_context = new SimpleStringProperty(context);
	}
	
	/*
	 * Getters and setters
	 */
	
	public void setRemarkID(String id)
	{
		this.remark_id = new SimpleStringProperty(id);
	}
	
	public void setEnglishText(String name)
	{
		this.remark_english = new SimpleStringProperty(name);
	}
	
	public void setGujaratiText(String name)
	{
		this.remark_gujarati = new SimpleStringProperty(name);
	}
	
	public void setContext(String name)
	{
		this.remark_context = new SimpleStringProperty(name);
	}
	
	public StringProperty getRemarkID()
	{
		return this.remark_id;
	}
	
	public StringProperty get_english_text()
	{
		return this.remark_english;
	}
	
	public StringProperty get_gujarati_text()
	{
		return this.remark_gujarati;
	}
	
	public StringProperty get_context()
	{
		return this.remark_context;
	}
}
