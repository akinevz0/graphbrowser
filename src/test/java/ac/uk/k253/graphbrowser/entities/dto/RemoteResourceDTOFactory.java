package ac.uk.k253.graphbrowser.entities.dto;

public class RemoteResourceDTOFactory {

    public static PagertsResourceDTO of(final String string) {
        final var dto = new PagertsResourceDTO();
        dto.setLink(link(string));
        dto.setText(text(string));
        return dto;
    }

    private static PagertsTextDTO text(final String string) {
        final var dto = new PagertsTextDTO();
        dto.setKey("innerHTML");
        dto.setValue(string);
        return dto;
    }

    private static PagertsLinkDTO link(final String string) {
        final var dto = new PagertsLinkDTO();
        dto.setKey("href");
        dto.setUrl(string);
        return dto;
    }
    
}
