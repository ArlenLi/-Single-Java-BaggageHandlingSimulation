import java.util.Random;

/**
 * A consumer continually tries to take bags from the end of a belt
 */

public class Consumer extends BaggageHandlingThread {

    // the maximum amount of time the consumer waits
    protected final static int MAX_SLEEP = 2800;

    // the belt from which the consumer takes the bags
    protected Belt belt;

    // the belt from which the consumer takes the bags
    protected ShortBelt shortbelt;

    /**
     * Create a new Consumer that consumes from a belt
     */
    public Consumer(Belt belt, ShortBelt shortBelt) {
        super();
        this.belt = belt;
        this.shortbelt = shortBelt;
    }

    /**
     * Loop indefinitely trying to get bags from the baggage belt
     */
    public void run() {
        while (!isInterrupted()) {
            try {
                if(belt.peek(belt.beltLength-1)!=null) {
                    Bag bag = belt.getEndBelt();
                    // let some time pass ...
                    Random random = new Random();
                    int sleepTime = 500 + random.nextInt(MAX_SLEEP - 500);
                    sleep(sleepTime);
                }

                if (shortbelt.peek(shortbelt.beltLength-1)!= null) {
                    Bag bag = shortbelt.getEndBelt();
                    // let some time pass ...
                    Random random = new Random();
                    int sleepTime = 500 + random.nextInt(MAX_SLEEP - 500);
                    sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }

        System.out.println("Consumer terminated");
    }
}
