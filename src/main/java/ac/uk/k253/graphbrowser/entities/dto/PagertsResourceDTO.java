package ac.uk.k253.graphbrowser.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ac.uk.k253.graphbrowser.entities.RemoteResource;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagertsResourceDTO {
    PagertsTextDTO text;
    PagertsLinkDTO link;

    public PagertsTextDTO getText() {
        return text;
    }

    public void setText(final PagertsTextDTO text) {
        this.text = text;
    }

    public PagertsLinkDTO getLink() {
        return link;
    }

    public void setLink(final PagertsLinkDTO link) {
        this.link = link;
    }

    public RemoteResource toRemoteResource() {
        final var existing = RemoteResource.findByUrlOptional(link.getUrl());
        if (!existing.isPresent())
            return new RemoteResource(text.getValue(), link.getUrl());
        final var remote = existing.get();
        if (!remote.getTitle().equals(text.getValue()))
            return RemoteResource.locate(remote, getTitle());
        return remote;
    }

    private String getTitle() {
        return text.getText();
    }

}
