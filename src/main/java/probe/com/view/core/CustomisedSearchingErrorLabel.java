
package probe.com.view.core;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.io.Serializable;

/**
 * This class represents customised error label
 *
 * @author Yehia Farag
 */
public class CustomisedSearchingErrorLabel extends VerticalLayout implements Serializable {

    /**
     * Initialise / update searching error label
     *
     * @param keywords the keywords without results
     */
    public void updateErrot(String keywords) {
        this.removeAllComponents();
        Label errorLabel = new Label("<h3 Style='color:gray;max-height: 20px;line-height: 20px;margin-top: -16px;position: absolute;'>No results found:</h3><h4 style='word-spacing: 5px; color: gray;'> " + keywords + " </h4>");
        errorLabel.setContentMode(ContentMode.HTML);
        this.addComponent(errorLabel);

    }
}
