package probe.com.view.core;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Label;
import java.io.Serializable;

/**
 * This class represents expand / collapse arrow with label
 *
 * @author Yehia Farag
 */
public final class ExpandCollapseIconLabel extends Label implements Serializable {

    private boolean expand = false;

    /**
     * Initialise component.
     */
    public ExpandCollapseIconLabel() {
        setExpandLabel(this.expand);
    }

    /**
     * Initialise component with default view/hide
     *
     * @param showLabel
     */
    public ExpandCollapseIconLabel(boolean showLabel) {
        setExpandLabel(showLabel);
    }

    /**
     * set expand / collapse label
     *
     * @param expand expand label
     */
    public void setExpandLabel(boolean expand) {
        if (expand) {
            this.expand = false;
            setIcon(new ThemeResource("img/down.jpg"));

        } else {
            this.expand = true;
            setIcon(new ThemeResource("img/right.jpg"));

        }
    }
}
