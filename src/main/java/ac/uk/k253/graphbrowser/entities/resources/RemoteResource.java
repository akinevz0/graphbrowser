package ac.uk.k253.graphbrowser.entities.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@JsonIgnoreProperties(value = "id")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Cached")
@DiscriminatorValue("remote")
public class RemoteResource extends PanacheEntity {

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String title;

    @Override
    public String toString() {
        return String.format("RemoteResource{title='%s', url='%s', persistent='%s'}", title, url,
                isPersistent() ? "yes" : "no");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

}
