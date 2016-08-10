/**
 * An exception that occurs when the scanner is asked to scan a bag
 * which is already known to be clean
 */
public class UnsusException extends HandlingException {
    /**
     * Create a new UnsusException
     */
    public UnsusException(String message) {
        super(message);
    }
}
