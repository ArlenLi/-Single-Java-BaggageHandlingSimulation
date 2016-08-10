/**
 * An exception that occurs when a suspicious bag makes it to the end of
 * the belt without being cleaned
 */
public class SusException extends HandlingException {
    /**
     * Create a new SusException
     */
    public SusException(String message) {
        super(message);
    }
}
