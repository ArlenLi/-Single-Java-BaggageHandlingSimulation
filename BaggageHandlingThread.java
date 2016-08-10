/**
 * A super class for all threads in the baggage handling system
 */
abstract public class BaggageHandlingThread extends Thread {

    // if this thread terminates, this exception provides a reason
    protected static Exception terminateException;

    /**
     * A constructor to be called by subclasses.
     */
    public BaggageHandlingThread() {
        terminateException = null;
    }

    /**
     * Terminate this thread, and signal other threads to terminate using the
     * static variables.
     * 
     * @param exception
     *            an Exception detailing the reason for termination.
     */
    public static void terminate(Exception exception) {
        terminateException = exception;
        Thread.currentThread().interrupt();
    }

    /**
     * @return the reason for the termination.
     */
    public static Exception getTerminateException() {
        return terminateException;
    }
}
