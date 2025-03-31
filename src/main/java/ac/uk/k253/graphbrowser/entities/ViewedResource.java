package ac.uk.k253.graphbrowser.entities;

import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.*;

@Entity
public class ViewedResource extends RemoteResource {

    @ManyToAny
    private List<RemoteResource> resources;

    public ViewedResource() {
        super();
    }

    public ViewedResource(final String url, final String title, final List<? extends OnlineResource> resources) {
        setUrl(url);
        setTitle(title);
        setResources(resources);
    }

    public ViewedResource(final RemoteResource viewedResource, final List<OnlineResource> list) {
        setUrl(viewedResource.getUrl());
        setTitle(viewedResource.getTitle());
    }

    @Override
    public RemoteResource view() {
        return RemoteResource.locate(this, getTitle());
    }

    @Override
    public ViewedResource view(final List<OnlineResource> list) {
        return new ViewedResource(this, list);
    }

    public List<RemoteResource> getResources() {
        return resources;
    }

    public void setResources(final List<? extends OnlineResource> resources) {
        this.resources = resources.stream().map(OnlineResource::view).toList();
    }

}
