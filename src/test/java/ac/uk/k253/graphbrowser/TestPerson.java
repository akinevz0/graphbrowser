package ac.uk.k253.graphbrowser;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestPerson {
    @JsonProperty("name")
    private String name;

    public TestPerson() {
    }

    public TestPerson(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
