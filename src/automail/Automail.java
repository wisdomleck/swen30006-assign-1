package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;

import simulation.IMailDelivery;

public class Automail {
	      
    public Robot[] robots;
    public MailPool mailPool;
    public WifiModem modem;
    public Charge charge;
    
    public Automail(MailPool mailPool, IMailDelivery delivery, int numRobots, WifiModem modem) {  	
    	/** Initialize the MailPool */
    	
    	this.mailPool = mailPool;
    	this.modem = modem;
    	
    	/** Initialize robots */
    	robots = new Robot[numRobots];
    	charge = new Charge(modem, 0.224, 5.9);
    	for (int i = 0; i < numRobots; i++) robots[i] = new Robot(delivery, mailPool, i, charge);
    }
    
}
