package ac.uk.k253.graphbrowser;

import jakarta.inject.Singleton;

@Singleton
public class PagerService {

    public Pager getPager() {
        return getPagerTS();
    }

    private PagerTS getPagerTS() {
        return new PagerTS();
    }

}
