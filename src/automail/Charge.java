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
		//this.serviceFee = 0;
		//this.activityUnits = 0;
		//this.chargeThreshold = chargeThreshold;
	}
	
	public String toString(int floor){
		return String.format("Charge: %4f | Cost: %2f | Fee: %4f | Activity: %4f", 
				this.getCharge(floor), this.getCost(floor), this.serviceFee, this.getActivityCost(floor));
	}
	
	// Updates the configurable markup percentage
	public void updateMarkupPercentage(double markUp) {
		this.markUp = markUp;
	}
	
	// Updates the configurable activity unit price
	public void updateActivityUnitPrice(double activityUnitPrice) {
		this.activityUnitPrice = activityUnitPrice;
	}
	
	public void setServiceFee(int floor) {
		this.serviceFee = this.modem.forwardCallToAPI_LookupPrice(floor);
	}
	
	public double getActivityCost(int floor) {
		return this.activityUnitPrice*(5*(floor-1) + 0.1);
	}
	
	public double getServiceFee() {
		return this.serviceFee;
	}
	
	public double getCost(int floor) {
		return  this.serviceFee + this.getActivityCost(floor);
	}
	
	public double getCharge(int floor) {
		this.setServiceFee(floor);
		return this.getCost(floor)*(1+this.markUp);
	}

	
}
