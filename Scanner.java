import java.util.Random;

/**
 * A Scanner continually scans suspicious bag unloaded from the robot
 */
public class Scanner extends BaggageHandlingThread {


    // the bag will be scanned
    protected Bag scanningBag = null;

    // the corresponding robot
    protected Robot robot;

    // shortBelt is used to transfer scannedBag to consumer
    protected Belt shortBelt;

    // time the robot needs to unload a bag
    protected final static int SCANNER_TIME = 5000;

    // the maximum amount of time the consumer waits
    protected final static int MAX_SLEEP = 3000;

    final private static String indentation = "                          ";

    // the animation to keep up to date
    protected Animation a;

    public Scanner(Robot robot, Belt shortBelt, Animation a) {
        super();
        this.robot = robot;
        this.shortBelt = shortBelt;
        this.a = a;
    }

    public Bag getScanningBag() {
        return scanningBag;
    }

    // a new scanningBag comes
    public void setScanningBag(Bag scanningBag) {
        this.scanningBag = scanningBag;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // // robot unload the bag on the scanner
                scanningBag = robot.unloadBag();
                a.animateRobot(robot);
                a.animateScanner(this);
                System.out.println(indentation + indentation + indentation + scanningBag.getId() +
                        " is loaded on the scanner from the robot");
                // spend SCANNER_TIME milliseconds to scan a bag
                sleep(SCANNER_TIME);
                // change the state of the bag to clean
                scanningBag.clean();
                a.animateScanner(this);
                // sleep for a bit....
                Random random = new Random();
                int sleepTime = random.nextInt(MAX_SLEEP);
                sleep(sleepTime);
                // put the bag on the scannerBelt
                shortBelt.put(scanningBag, 0);
                scanningBag = null;
                a.animateScanner(this);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
