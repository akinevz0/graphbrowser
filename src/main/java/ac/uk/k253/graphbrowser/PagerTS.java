package ac.uk.k253.graphbrowser;

import java.util.List;

public class PagerTS implements Pager {

    private final ExternalProgram pagerts;

    public PagerTS() {
        try {
            this.pagerts = new ExternalProgram("pagerts");
        } catch (final NoSuchProgramException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public ExternalProgram getProgram() {
        return pagerts;
    }

    @Override
    public String getTitle(final Page parent) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTitle'");
    }

    @Override
    public List<ResourceReference> getReferences(final Page parent) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReferences'");
    }

}
