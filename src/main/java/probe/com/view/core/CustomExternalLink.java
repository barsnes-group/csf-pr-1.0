package probe.com.view.core;

import com.vaadin.shared.ui.label.ContentMode;
import java.io.Serializable;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * This class represents click-able string title to open in external tab
 *
 * @author Yehia Farag
 */
public class CustomExternalLink extends VerticalLayout implements Serializable, Comparable<CustomExternalLink> {

    private final String title;
    private final Label label;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Initialise the external title
     *
     * @param title the title
     * @param url the web url title
     */
    public CustomExternalLink(String title, String url) {
        this.title = title;
        label = new Label("<a href='" + url + "' target='_blank' style='color:#000000;'>" + title + "</a>");
        label.setContentMode(ContentMode.HTML);
        CustomExternalLink.this.addComponent(label);

    }

    /**
     * Override to string
     *
     * @return the title value
     */
    @Override
    public String toString() {
        return title;
    }

    /**
     * Override compare to method
     *
     * @param myLink object to compare
     * @return
     */
    @Override
    public int compareTo(CustomExternalLink myLink) {
        return (this.title.compareTo(myLink.getTitle()));

    }

    /**
     * Get the title of the link
     *
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

}
