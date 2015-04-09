package Model.Patient;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Medicine_Prescribed 
{
	private StringProperty pat_id;
	private LocalDate date;
	private StringProperty time;
	private StringProperty medicine_ID;
	private StringProperty morning_amt, noon_amt, evening_amt;
	private StringProperty remark;
	
	public Medicine_Prescribed()
	{
		this.pat_id = new SimpleStringProperty("");
		this.date = LocalDate.now();
		this.time = new SimpleStringProperty("");
		this.medicine_ID = new SimpleStringProperty("");
		this.morning_amt = new SimpleStringProperty("");
		this.noon_amt = new SimpleStringProperty("");
		this.evening_amt = new SimpleStringProperty("");
		this.remark = new SimpleStringProperty("");
	}

	public StringProperty getPat_id() {
		return pat_id;
	}

	public void setPat_id(StringProperty pat_id) {
		this.pat_id = pat_id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public StringProperty getTime() {
		return time;
	}

	public void setTime(StringProperty time) {
		this.time = time;
	}

	public StringProperty getMedicine_ID() {
		return medicine_ID;
	}

	public void setMedicine_ID(StringProperty medicine_ID) {
		this.medicine_ID = medicine_ID;
	}

	public StringProperty getMorning_amt() {
		return morning_amt;
	}

	public void setMorning_amt(StringProperty morning_amt) {
		this.morning_amt = morning_amt;
	}

	public StringProperty getNoon_amt() {
		return noon_amt;
	}

	public void setNoon_amt(StringProperty noon_amt) {
		this.noon_amt = noon_amt;
	}

	public StringProperty getEvening_amt() {
		return evening_amt;
	}

	public void setEvening_amt(StringProperty evening_amt) {
		this.evening_amt = evening_amt;
	}

	public StringProperty getRemark() {
		return remark;
	}

	public void setRemark(StringProperty remark) {
		this.remark = remark;
	}
	
	
}
