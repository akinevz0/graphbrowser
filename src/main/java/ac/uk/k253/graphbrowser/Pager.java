package ac.uk.k253.graphbrowser;

import java.util.List;

public interface Pager {

    public ExternalProgram getProgram();

    public String getTitle(Page parent);

    public List<ResourceReference> getReferences(Page parent);

}
