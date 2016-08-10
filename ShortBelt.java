/**
 * The short baggage belt servers scanner
 */
public class ShortBelt extends Belt {
    final private static String indentation = "                          ";

    // Constructor
    public ShortBelt(int beltLength, Animation a) {
        super(beltLength, a);
    }

    /**
     * Move the belt along one segment
     *
     * @throws OverloadException
     *             if there is a bag at position beltLength.
     * @throws InterruptedException
     *             if the thread executing is interrupted.
     */
    public synchronized void move()
            throws InterruptedException, OverloadException {
        // if there is something at the end of the belt, or the belt is empty,
        // or something needs to be picked up for a scan, do not move the belt
        Bag bag = null;
        while (isEmpty() || segment[segment.length-1] != null) {
            wait();
        }

        // double check that a bag cannot fall of the end
        if (segment[segment.length-1] != null) {
            String message = "Bag fell off end of " + " belt";
            throw new OverloadException(message);
        }

        // move the elements along, making position 0 null
        for (int i = segment.length-1; i > 0; i--) {
            segment[i] = segment[i-1];
        }
        segment[0] = null;
        System.out.println(indentation + indentation + indentation + "scanner belt move");
        a.animateSbMove(this);

        // notify any waiting threads that the belt has changed
        notifyAll();
    }

    /**
     * Take a bag off the end of the belt
     *
     * @return the removed bag
     * @throws InterruptedException
     *             if the thread executing is interrupted
     */
    public synchronized Bag getEndBelt() throws InterruptedException {

        Bag bag;

        while (segment[segment.length-1] == null) {
            wait();
        }

        // get the next item
        bag = segment[segment.length-1];
        segment[segment.length-1] = null;

        // make a note of the event
        System.out.print(indentation);
        if (!bag.isClean()) {
            System.out.println(indentation + indentation + indentation + indentation + bag.getId()  +
                    " departed -- unclean!!! from scanner belt");
        } else {
            System.out.println(indentation + indentation + indentation + indentation + bag.getId() +
                    " departed from scanner belt");
        }

        // notify any waiting threads that the belt has changed
        a.animateSbBagPanel(this);
        notifyAll();
        return bag;
    }

    /**
     * Put a bag on the belt.
     *
     * @param bag
     *            the bag to put onto the belt.
     * @param index
     *            the place to put the bag
     * @throws InterruptedException
     *             if the thread executing is interrupted.
     */
    public synchronized void put(Bag bag, int index)
            throws InterruptedException {
        // while there is another bag in the way, block this thread
        while (segment[index] != null) {
            wait();
        }

        // insert the element at the specified location
        segment[index] = bag;

        // make a note of the event
        if (bag.isSuspicious()) {
            System.out.println(indentation + indentation + indentation + bag.getId() +
                    " arrived (sus) on scanner belt");
        } else {
            System.out.println(indentation + indentation + indentation + bag.getId() + " arrived on scanner belt");
        }

        // notify any waiting threads that the belt has changed
        notifyAll();
    }
}
