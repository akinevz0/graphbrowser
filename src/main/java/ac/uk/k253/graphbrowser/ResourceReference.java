package ac.uk.k253.graphbrowser;

import java.beans.JavaBean;
import java.net.*;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@JavaBean
@Entity
public class ResourceReference extends PanacheEntityBase implements Resource {

    public ResourceReference(final URI uri) throws MalformedURLException {
        this(uri.toURL());
    }

    public ResourceReference(final URL url) {
        this.url = url;
    }

    public ResourceReference() {
    }

    @Id
    private URL url;

    @Override
    public URL getURL() {
        return this.url;
    }

}
