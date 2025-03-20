package ac.uk.k253.graphbrowser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ExternalProgramTest {

    // jboss logger
    private static final Logger LOGGER = Logger.getLogger(ExternalProgramTest.class.getName());

    @Test
    public void testEnsureExists() {
        // Test the ensureExists method to verify that the program exists on the system
        assertThrows(NoSuchProgramException.class, this::construct_fail);
        LOGGER.debug("construct_fail passed");
        final var program = assertDoesNotThrow(this::construct_pass);
        final var exitActualFuture = assertDoesNotThrow(() -> program.runWithArgs("-h"));
        final var exitActual = assertDoesNotThrow(() -> exitActualFuture.get());

        assertEquals(0, exitActual);
        LOGGER.debug("construct_pass passed");
    }


    @Test
    public void testRunWithArgs() {
        final var pwdProgram = assertDoesNotThrow(() -> new ExternalProgram("pwd"));
        // construct a printstream that writes to a string "buffer"
        try (final var baos = new ByteArrayOutputStream()) {
            final var pwdExitFuture = assertDoesNotThrow(() -> pwdProgram.redirectOutput(baos).runWithArgs());
            final var pwdExitActual = assertDoesNotThrow(() -> pwdExitFuture.get());
            assertEquals(0, pwdExitActual);
            final var actual = baos.toString();
            final var expected = System.getProperty("user.dir");
            assertEquals(expected, actual);
        } catch (final IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    private ExternalProgram construct_pass() throws NoSuchProgramException {
        return new ExternalProgram("top");
    }

    private ExternalProgram construct_fail() throws NoSuchProgramException {
        return new ExternalProgram("test_program");
    }
}