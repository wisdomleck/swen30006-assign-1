package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;
import java.lang.*;

public class Charge {
	
	/** Details about a Charge **/
	private final static double activityUnitMult = 5;
	
	private double activityUnitPrice;
	
	private double markUp;
	
	private WifiModem modem;
	
	//private boolean isPrio = false;
	//private double chargeThreshold;
	
	
	public Charge(WifiModem modem, double activityUnitPrice, double markUp) {
		this.activityUnitPrice = activityUnitPrice;
		this.markUp = markUp;
		this.modem = modem;
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
	
	
	// Calculates the charge of the mail when its still in the automail
	public double calculateCharge(int endFloor) {
		// pre-computes cost assuming 0.1 activity units max and we precompute this during mail pickup
		return ((activityUnitMult*(Math.abs(endFloor-1)) + 0.1)*this.activityUnitPrice + this.modem.forwardCallToAPI_LookupPrice(endFloor)) * (1 + this.markUp);
	}
	
}
