package ac.uk.k253.graphbrowser;

public class NoSuchProgramException extends Exception {

    public NoSuchProgramException(final String message, final Exception e) {
        super(message, e);
    }
}
