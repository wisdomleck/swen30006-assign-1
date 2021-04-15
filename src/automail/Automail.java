package automail;

import com.unimelb.swen30006.wifimodem.WifiModem;

import simulation.IMailDelivery;

public class Automail {
	      
    public Robot[] robots;
    public MailPool mailPool;
    public Charge charge;
    
    public Automail(MailPool mailPool, IMailDelivery delivery, int numRobots) {  	
    	/** Initialize the MailPool */
    	
    	this.mailPool = mailPool;
    	
    	/** Initialize robots */
    	robots = new Robot[numRobots];
    	for (int i = 0; i < numRobots; i++) robots[i] = new Robot(delivery, mailPool, i);
    }
    
}
