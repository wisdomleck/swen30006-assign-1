package automail;

import java.util.LinkedList;
import java.util.Comparator;
import java.util.ListIterator;

import exceptions.ItemTooHeavyException;

import com.unimelb.swen30006.wifimodem.WifiModem;

/**
 * addToPool is called when there are mail items newly arrived at the building to add to the MailPool or
 * if a robot returns with some undelivered items - these are added back to the MailPool.
 * The data structure and algorithms used in the MailPool is your choice.
 * 
 */
public class MailPool {
	
	// mailpool should have a charge object and should pass it to robots when they are assigned delivery
	private Charge chargeObject;
	
	private class Item {
		double charge;
		double destination;
		MailItem mailItem;
		// Use stable sort to keep arrival time relative positions
		
		public Item(MailItem mailItem) {
			this.charge = chargeObject.getCharge(mailItem.getDestFloor());
			this.destination = mailItem.getDestFloor();
			this.mailItem = mailItem;
			
		}
	}
	
	public class ItemComparator implements Comparator<Item> {
		@Override
		public int compare(Item i1, Item i2) {
			int order = 0;
			if (chargeObject.getThreshold() > 0) {
				if (i1.charge < i2.charge) {
					order = 1;
				} else if (i1.charge > i2.charge) {
					order = -1;
				}
				return order;
			}
			else {
				if (i1.destination < i2.destination) {
					order = 1;
				} else if (i1.destination > i2.destination) {
					order = -1;
				}
				return order;
			}
		}
	}
	
	private LinkedList<Item> pool;
	private LinkedList<Robot> robots;
	
	public Charge getCharge() {
		return this.chargeObject;
	}
	public MailPool(int nrobots, WifiModem wModem, boolean chargeDisplayed, double markUpPercentage, double activityUnitPrice, double threshold){
		// Start empty
		this.chargeObject = new Charge(wModem, activityUnitPrice, markUpPercentage, chargeDisplayed, threshold);
		pool = new LinkedList<Item>();
		robots = new LinkedList<Robot>();
	}

	/**
     * Adds an item to the mail pool
     * @param mailItem the mail item being added.
     */
	public void addToPool(MailItem mailItem) {
		Item item = new Item(mailItem);
		pool.add(item);
		pool.sort(new ItemComparator());
	}
	
	
	/**
     * load up any waiting robots with mailItems, if any.
     */
	public void loadItemsToRobot() throws ItemTooHeavyException {
		//List available robots
		ListIterator<Robot> i = robots.listIterator();
		while (i.hasNext()) loadItem(i);
	}
	
	//load items to the robot
	private void loadItem(ListIterator<Robot> i) throws ItemTooHeavyException {
		Robot robot = i.next();
		assert(robot.isEmpty());
		// System.out.printf("P: %3d%n", pool.size());
		ListIterator<Item> j = pool.listIterator();
		if (pool.size() > 0) {
			try {
			robot.addToHand(j.next().mailItem); // hand first as we want higher priority delivered first
			j.remove();
			if (pool.size() > 0) {
				robot.addToTube(j.next().mailItem);
				j.remove();
			}
			robot.dispatch(chargeObject); // send the robot off if it has any items to deliver
			i.remove();       // remove from mailPool queue
			} catch (Exception e) { 
	            throw e; 
	        } 
		}
	}

	/**
     * @param robot refers to a robot which has arrived back ready for more mailItems to deliver
     */	
	public void registerWaiting(Robot robot) { // assumes won't be there already
		robots.add(robot);
	}

}
