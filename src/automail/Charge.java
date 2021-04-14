package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;
import java.lang.*;

public class Charge {
	
	/** Details about a Charge **/
	private final static double activityUnitMult = 5;
	
	private double activityUnitPrice;
	
	// The values we need to print out
	private double activityUnits;
	private double serviceFee;
	private double markUp;
	
	private WifiModem modem;
	
	//private boolean isPrio = false;
	//private double chargeThreshold;
	
	
	public Charge(WifiModem modem, double activityUnitPrice, double markUp) {
		this.activityUnitPrice = activityUnitPrice;
		this.markUp = markUp;
		this.modem = modem;
		this.activityUnits = 0;
		//this.chargeThreshold = chargeThreshold;
	}
	
	// Updates the configurable markup percentage
	public void updateMarkupPercentage(double markUp) {
		this.markUp = markUp;
	}
	
	// Updates the configurable activity unit price
	public void updateActivityUnitPrice(double activityUnitPrice) {
		this.activityUnitPrice = activityUnitPrice;
	}
	
	
	// setters
	// Updates the number of activity units in the current charge
	public void addActivityUnits(double units) {
		this.activityUnits += units;
	}
	
	// pings the BMS to get a service fee
	public void pingServiceFee(int floor) {
		this.serviceFee = this.modem.forwardCallToAPI_LookupPrice(floor);
		// cost of pinging servicefee
		this.activityUnits += 0.1;
	}
	
	
	// Resets the charge for a new delivery
	public void resetCharges() {
		this.serviceFee = 0;
		this.activityUnits = 0;
	}
	
	
	// Getters for the charge metrics
	public double getActivityUnits() {
		return this.activityUnits;
	}
	
	public double getServiceFee(int floor) {
		return this.serviceFee;
	}
	
	public double getTotalCost() {
		return this.serviceFee + this.activityUnits*this.activityUnitPrice;
	}
	
	public double getTotalCharge() {
		return (this.serviceFee + this.activityUnits*this.activityUnitPrice)*this.markUp;
	}
	
}
