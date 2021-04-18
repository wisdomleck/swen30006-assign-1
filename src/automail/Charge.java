package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;
import java.lang.*;

public class Charge {
	
	/** Details about a Charge **/
	private final static double activityUnitMult = 5;
	
	private double activityUnitPrice;
	
	// The values we need to print out
	private double serviceFee = 0;
	private double markUp;
	private StatsLog stats;
	private WifiModem modem;
	
	//private boolean isPrio = false;
	//private double chargeThreshold;
	
	
	public Charge(WifiModem modem, double activityUnitPrice, double markUp) {
		this.activityUnitPrice = activityUnitPrice;
		this.markUp = markUp;
		this.modem = modem;
		this.stats = new StatsLog();
	}
	
	public String bill(MailItem mailItem){
		int floor = mailItem.getDestFloor();
		double charge = this.getCharge(floor);
		double cost = this.getCost(floor);
		double serviceFee = this.serviceFee;
		double activityCost = this.getActivityCost(floor);
		
		this.stats.logDelivery();
		this.stats.logActivityCost(activityCost);
		this.stats.logServiceCost(serviceFee);
		this.stats.logActivityUnits((activityUnitMult*(floor-1) + 0.1));
		
		return String.format("Charge: %4f | Cost: %2f | Fee: %4f | Activity: %4f", 
				charge, cost, serviceFee, activityCost);
	}
	
	public StatsLog getStats() {
		return this.stats;
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
		double serviceFee = this.modem.forwardCallToAPI_LookupPrice(floor);
		this.stats.logLookUp();
		if (serviceFee >= 0) {
			this.serviceFee = serviceFee;
			this.stats.logSuccessfulLookUp();
		}
	}
	
	public double getActivityCost(int floor) {
		return this.activityUnitPrice*(activityUnitMult*(floor-1) + 0.1);
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
