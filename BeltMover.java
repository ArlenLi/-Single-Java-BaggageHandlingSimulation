/*
 * A beltmover moves a belt along as often as possible, but only
 * when there is a bag on the belt not at the last position.
 */

public class BeltMover extends BaggageHandlingThread {

    // the belt to be handled
    protected Belt belt;

    // the amount of time it takes to move the belt
    protected final static int MOVE_TIME = 900;

    /**
     * Create a new BeltMover with a belt to move
     */
    public BeltMover(Belt belt) {
        super();
        this.belt = belt;
    }

    /**
     * Move the belt as often as possible, but only if there 
     * is a bag on the belt which is not in the last position.
     */
    public void run() {
        while (!isInterrupted()) {
            try {
                // spend MOVE_TIME milliseconds moving the belt
                Thread.sleep(MOVE_TIME);
                belt.move();
            } catch (OverloadException e) {
                terminate(e);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }

        System.out.println("BeltMover terminated");
    }
}
