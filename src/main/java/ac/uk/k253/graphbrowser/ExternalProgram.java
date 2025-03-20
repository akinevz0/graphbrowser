package ac.uk.k253.graphbrowser;

import java.beans.JavaBean;
import java.io.*;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;

import org.jboss.logging.Logger;

@JavaBean
public class ExternalProgram {

    // jboss logger
    private final static Logger LOGGER = Logger.getLogger(ExternalProgram.class);
    private static final String PREFIX = "graphbrowser";
    private static final Random RANDOM = new Random();
    private static final int SUFFIX_LENGTH = 8;

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    private Optional<OutputStream> out;

    private final String programName;
    private final ProcessBuilder processBuilder;

    public ExternalProgram(final String name) throws NoSuchProgramException {
        this.programName = name;
        this.out = Optional.empty();
        this.processBuilder = new ProcessBuilder(name);
        ensureExists("--help");
    }

    /**
     * Ensure that the program exists on the system before running it.
     * 
     * @param usingArgs passed to the program to test if it exists. Can be empty.
     * @throws NoSuchProgramException if the program is not found on the system.
     */
    protected final void ensureExists(final String... usingArgs) throws NoSuchProgramException {
        try {
            // Check if the program exists by running it with test arguments
            final var errorCode = (int) runWithArgs(usingArgs).get();
            if (errorCode != 0) {
                throw new RuntimeException(programName + " not found on the system");
            }
        } catch (final Exception e) {
            // Handle the exception, if program is not found on the system
            throw new NoSuchProgramException(e.getMessage(), e);
        }
    }

    public Future<Integer> runWithArgs(final String... args) throws InterruptedException, ExecutionException {
        return executorService.submit(() -> {
            final var commandString = processBuilder.command();
            // set the command string
            commandString.clear();
            commandString.add(programName);
            commandString.addAll(Arrays.asList(args));
            Path redirectPipe = null;
            if (out.isPresent()) {
                // redirect output to file
                redirectPipe = outputPipe();
            }
            // run the command
            final var process = processBuilder.start();
            // wait for the process to finish
            final var errorCode = process.waitFor();
            // copy output from the process if redirect is set
            if (redirectPipe != null) {
                final var redirect = out.get();
                final var content = Files.readAllLines(redirectPipe);
                System.out.println(content.toString());
                final var outStream = new PrintStream(new BufferedOutputStream(redirect));
                content.forEach(outStream::print);
                outStream.close();
                // cleanup the redirect file
                cleanupRedirectFile(redirectPipe);
            }
            // return the error code
            return errorCode;
        });
    }

    public ExternalProgram redirectOutput(final OutputStream out) {
        this.out = Optional.of(out);
        System.out.println("output redirect registered");
        return this;
    }

    /**
     * Redirects the standard output of the external program to a temporary file.
     *
     */
    private Path outputPipe() {
        try {
            final var path = Files.createTempFile(PREFIX, ".tmp");
            final var file = path.toFile();
            processBuilder.redirectOutput(Redirect.appendTo(file));
            processBuilder.redirectError(Redirect.appendTo(file));
            return path;
        } catch (final IOException e) {
            final String message = "Could not create a temp file for redirecting output";
            throw new RuntimeException(message, e);
        }
    }

    void cleanupRedirectFile(final Path redirectTmp) {
        try {
            Files.delete(redirectTmp);
        } catch (final IOException e) {
            final String message = "Failed to delete temporary file after running program";
            throw new RuntimeException(message, e);
        }
    }
}
