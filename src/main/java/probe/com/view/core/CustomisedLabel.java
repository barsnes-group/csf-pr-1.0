package probe.com.view.core;

import com.vaadin.shared.ui.label.ContentMode;
import java.io.Serializable;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * This class represent a customised label
 *
 * @author Yehia Farag
 */
public class CustomisedLabel extends VerticalLayout implements Serializable, Comparable<Object> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String labelValue;

    /**
     * Initialise customised label
     *
     * @param str the value of the label
     * @param color the font colour of the label
     */
    public CustomisedLabel(String str, String color) {
        Label l = new Label("<label style='font-family:verdana; color:" + color + ";'>" + str + "</label>");
        l.setContentMode(ContentMode.HTML);
        this.labelValue = str;
        l.setHeight(17,Unit.PIXELS);
        CustomisedLabel.this.setHeight(18,Unit.PIXELS);
        CustomisedLabel.this.setMargin(false);
        CustomisedLabel.this.addComponent(l);
        CustomisedLabel.this.setComponentAlignment(l, Alignment.TOP_LEFT);
        CustomisedLabel.this.setDescription("The Peptide Sequence: " + str);

    }

    /**
     * Override compare to method
     *
     * @param myLabel label to compare
     * @return comparison result
     */
    @Override
    public int compareTo(Object myLabel) {

        String compareString = myLabel.toString();

        return (this.labelValue.compareTo(compareString));

    }

    /**
     *Override toString method
     * @return the value of the label
     */
    @Override
    public String toString() {
        return labelValue;
    }

}
