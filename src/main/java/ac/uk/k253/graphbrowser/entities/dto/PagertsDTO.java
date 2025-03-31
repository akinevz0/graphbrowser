package ac.uk.k253.graphbrowser.entities.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ac.uk.k253.graphbrowser.entities.ViewedResource;

@JsonSerialize
public class PagertsDTO {
    private String url = null;

    private String title = null;

    private List<PagertsResourceDTO> resources;

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setResources(final List<PagertsResourceDTO> resources) {
        this.resources = resources;
    }

    public ViewedResource toViewedResource() {
        return new ViewedResource(url, title, resources.stream().map(PagertsResourceDTO::toRemoteResource).toList());
    }

}
