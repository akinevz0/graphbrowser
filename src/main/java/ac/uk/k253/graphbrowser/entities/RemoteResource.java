package ac.uk.k253.graphbrowser.entities;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@JsonIgnoreProperties(value = "id")
public class RemoteResource extends PanacheEntity implements OnlineResource {

    public static <T extends RemoteResource> Optional<? extends T> findByUrlOptional(final String url) {
        return find("url", url).firstResultOptional();
    }

    private static void deleteByUrl(final String url) {
        final var existing = findByUrlOptional(url);
        existing.ifPresent(RemoteResource::delete);
    }

    @Column
    private String url;

    @Column
    private String title;

    public RemoteResource() {
        super();
    }

    public RemoteResource(final String url, final String title) {
        RemoteResource.deleteByUrl(url);
        setUrl(url);
        setTitle(title);
    }

    @Override
    public String toString() {
        return String.format("RemoteResource{title='%s', url='%s'}", getTitle(), getUrl());
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public ViewedResource view(final List<OnlineResource> list) {
        return new ViewedResource(url, title, list);
    }

    @Override
    public RemoteResource view() {
        return this;
    }

}
