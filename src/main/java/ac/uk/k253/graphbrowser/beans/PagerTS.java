package ac.uk.k253.graphbrowser.beans;

import java.beans.JavaBean;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.jboss.logging.Logger;

import ac.uk.k253.graphbrowser.entities.ViewedResource;
import ac.uk.k253.graphbrowser.entities.dto.PagertsDTO;
import ac.uk.k253.graphbrowser.exceptions.NoSuchProgramException;
import ac.uk.k253.graphbrowser.exceptions.PagerRunException;
import jakarta.enterprise.inject.Default;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@JavaBean
@Default
public class PagerTS implements AutoCloseable  {

    private final ExternalProgram pagerts;

    public PagerTS() throws NoSuchProgramException {
        this.pagerts = new ExternalProgram("pagerts");
    }

    @Override
    public void close() throws IOException {
        pagerts.close();
    }

    @Transactional(value = TxType.REQUIRES_NEW)
    public ViewedResource view(final String url) {
        Logger.getLogger(getClass()).debug("invoking pagerts.view() on " + url);
        // viewing a page
        final var cached = ViewedResource.locate(url);
        // if cached and in database nothing else needed
        if (cached.isPresent())
            return cached.get();

        // if remote then locate
        final var dto = intoDto(url);
        final var viewed =  dto.toViewedResource();
        return viewed;
    }

    PagertsDTO intoDto(final String url) {
        try {
            final var result = pagerts.runWithArgs(url).get();
            final var statusCode = result.getStatusCode();
            if (statusCode != 0)
                throw new PagerRunException("Program exited abnormally: " + statusCode);
            final var cache = pagerts.getOutput(PagertsDTO[].class)[0];
            return cache;
        } catch (final IOException e) {
            throw new PagerRunException("Could not parse pagerts output", e);
        } catch (InterruptedException | ExecutionException e) {
            throw new PagerRunException("Could not run pagerts", e);
        }
    }

}
