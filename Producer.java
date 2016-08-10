import java.util.Random;

/**
 * A producer continually tries, at varying time intervals, 
 * to put a bag onto a belt
 */
public class Producer extends BaggageHandlingThread {

    // the maximum amount of time the consumer waits
    protected final static int MAX_SLEEP = 3000;

    // the belt to which the producer puts the bags
    protected Belt belt;

    /**
     * Create a new producer to feed a given belt
     */
    Producer(Belt belt) {
        super();
        this.belt = belt;
    }

    /**
     * The thread's main method. 
     * Continually tries to place bags on the belt at random intervals.
     */
    public void run() {
        while (!isInterrupted()) {
            try {
                // put a new bag on the belt

                Bag bag = Bag.getInstance();
                belt.put(bag, 0);

                // sleep for a bit....
                Random random = new Random();
                int sleepTime = random.nextInt(MAX_SLEEP);
                sleep(sleepTime);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }

        System.out.println("Producer terminated");
    }
}
