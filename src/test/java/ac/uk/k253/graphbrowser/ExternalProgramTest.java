package ac.uk.k253.graphbrowser;

import static org.junit.jupiter.api.Assertions.*;

import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import ac.uk.k253.graphbrowser.beans.ExternalProgram;
import ac.uk.k253.graphbrowser.exceptions.NoSuchProgramException;
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
        final var exitActual = assertDoesNotThrow(() -> exitActualFuture.get().getStatusCode());

        assertEquals(0, exitActual);
        LOGGER.debug("construct_pass passed");
    }

    @Test
    public void testRunWithArgs() {
        final var pwdProgram = assertDoesNotThrow(() -> new ExternalProgram("pwd"));
        final var pwdExitFuture = assertDoesNotThrow(() -> pwdProgram.runWithArgs());
        final var pwdExitActual = assertDoesNotThrow(() -> pwdExitFuture.get());
        assertEquals(0, pwdExitActual.getStatusCode());
        final var actual = pwdExitActual.getOutput().get(0);
        final var expected = System.getProperty("user.dir");
        assertEquals(expected, actual);
    }

    @Test
    public void testFromJson() {
        final var catProgram = assertDoesNotThrow(() -> new ExternalProgram("echo"));
        final var exitFuture = assertDoesNotThrow(() -> catProgram.runWithArgs("{\"name\":\"Jason\"}"));
        final var exitActual = assertDoesNotThrow(() -> exitFuture.get());
        assertEquals(0, exitActual.getStatusCode());
        final var expected = "Jason";
        final var actual = assertDoesNotThrow(() -> catProgram.getOutput(TestPerson.class)).getName();
        assertEquals(expected, actual);
        assertDoesNotThrow(() -> catProgram.close());
    }

    private ExternalProgram construct_pass() throws NoSuchProgramException {
        return new ExternalProgram("top");
    }

    private ExternalProgram construct_fail() throws NoSuchProgramException {
        return new ExternalProgram("test_program");
    }
}