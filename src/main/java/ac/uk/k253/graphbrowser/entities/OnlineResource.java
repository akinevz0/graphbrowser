package ac.uk.k253.graphbrowser.entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public interface OnlineResource {

    @Id
    String getUrl();

    @Column(nullable = false)
    String getTitle();

    void setTitle(String title);

    void setUrl(String url);

    RemoteResource view();

    ViewedResource view(List<OnlineResource> list);

}
