package probe.com.view.core;

import com.vaadin.server.Resource;
import java.io.Serializable;

import com.vaadin.ui.Embedded;

/**
 * This class represents a customised table component
 *
 * @author Yehia Farag
 */
public class CustomEmbedded extends Embedded implements Serializable, Comparable<CustomEmbedded> {

    private static final long serialVersionUID = 1L;
    private final boolean value;
    private final String booleanValue;

    /**
     * Initialise component
     *
     * @param value
     * @param res
     */
    public CustomEmbedded(boolean value, Resource res) {
        super(String.valueOf(value), res);
        this.value = value;
        this.booleanValue = String.valueOf(value).toUpperCase();
    }

    /**
     * Override to string to return the string value of the boolean (value)
     *
     * @return
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Return boolean value as string
     *
     * @return string of boolean (value)
     */
    public String getBooleanValue() {
        return this.booleanValue;
    }

    /**
     * Override compare to method
     *
     * @param o object to compare with
     * @return results of the comparison
     */
    @Override
    public int compareTo(CustomEmbedded o) {
        String valueToCompare = o.getBooleanValue();
        return this.booleanValue.compareTo(valueToCompare);
    }
}
