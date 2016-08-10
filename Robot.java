/**
 * A Robot transfers suspicious bag from sensor to scanner
 */
public class Robot extends BaggageHandlingThread {
    // the Bag that the robot carrying now. If it is empty, it is null;
    protected Bag carryingBag = null;


    // time the robot needs to load a bag
    protected final static int LOAD_TIME = 900;

    // time the robot needs to unload a bag
    protected final static int UNLOAD_TIME = 900;

    final private static String indentation = "                          ";

    // the animation to keep up to date
    protected Animation a;

    public Robot(Animation a) {
        this.a = a;
    }

    // If the robot are not carrying any bag, return true;
    // If not, return false
    public boolean isEmpty() {
        if (carryingBag == null)
            return true;
        else
            return false;
    }

    public Bag getCarryingBag() {
        return carryingBag;
    }

    public void setCarryingBag(Bag carryingBag) {
        this.carryingBag = carryingBag;
    }


    public static int getLOAD_TIME() {
        return LOAD_TIME;
    }

    public static int getUNLOAD_TIME() {
        return UNLOAD_TIME;
    }

    // robot load bag from the sensor
    public synchronized void loadBag(Bag bag) throws InterruptedException{
        // if robot now is carrying a bag, then sensor waits
        while(!isEmpty()) {
            wait();
        }
        // spend LOAD_TIME milliseconds to load a bag on the robot from the sensor
        sleep(LOAD_TIME);
        carryingBag = bag;
        System.out.println(indentation + indentation + bag.getId() + " is loaded on the robot");
        notifyAll();
    }

    // robot unload the bag on the scanner
    public synchronized Bag unloadBag() throws InterruptedException {
        // if there is no bag on the robot, then scanner waits
        while(isEmpty()) {
            wait();
        }
        // spend UNLOAD_TIME milliseconds to unload a bag from the robot to the scanner
        sleep(UNLOAD_TIME);
        Bag bag = carryingBag;
        carryingBag = null;
        notifyAll();
        return bag;
    }
}
