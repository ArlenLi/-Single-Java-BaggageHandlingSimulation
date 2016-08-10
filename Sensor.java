/**
 * A Sensor continually tries to detect suspicious bag on the 3rd segment of the main belt
 */
public class Sensor extends BaggageHandlingThread {

    // the belt would be monitored by the sensor
    protected Belt belt;

    // the position that the sensor would be set up
    protected int position;

    // corresponding robot of this sensor
    protected Robot robot;

    // the animation to keep up to date
    protected Animation a;

    public Sensor(Belt belt, int position, Robot robot, Animation a) {
        super();
        this.belt = belt;
        this.position = position;
        this.robot = robot;
        this.a = a;
    }

    // return the belt on which the sensor set up
    public Belt getBelt() {
        return belt;
    }

    // return the position of the sensor
    public int getPostion() {
        return position;
    }

    // if the bag on the position is suspicious and unclean, return true;
    // if not, return false;
    public boolean isSuspiciousUnclean() {
        Bag bag = belt.peek(position - 1);
        if (bag.isSuspicious() && !bag.isClean())
            return true;
        else
            return false;
    }


    /**
     * The thread's main method.
     * Continually check the if the bag is suspicious.
     */
    public void run() {
        Bag bag = null;
        while (!isInterrupted()) {
            // the procedure that the sensor detect suspicious bag
            // and deal with the suspicious bag need to lock the belt
            synchronized (belt) {
                try {
                    // if the 3rd segment is empty, then realize the belt
                    while ((bag = belt.peek(position - 1)) == null) {
                        belt.wait();
                    }

                    if (bag.isSuspicious()) {
                        System.out.println(bag.getId() + "is suspicious");
                        // robot load bag from the sensor
                        robot.loadBag(bag);
                        // set the bag on the position of the segment empty
                        belt.setOneSegmentNull(position);
                        a.animateRobot(robot);
                    }
                    belt.notifyAll();
                } catch (InterruptedException e) {
                    this.interrupt();
                }
            }
        }

        System.out.println("Producer terminated");
    }
}
