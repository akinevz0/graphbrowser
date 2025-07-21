package ac.uk.k253.graphbrowser.exceptions;

import java.io.IOException;

public class NoSuchProgramException extends IOException {

    public NoSuchProgramException(final String message, final Exception e) {
        super(message, e);
    }
}
