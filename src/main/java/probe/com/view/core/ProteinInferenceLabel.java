package probe.com.view.core;

import com.vaadin.server.Resource;
import java.io.Serializable;

import com.vaadin.ui.Embedded;

/**
 * This class represents Protein inference label
 *
 * @author Yehia Farag
 */
public class ProteinInferenceLabel extends Embedded implements Serializable, Comparable<ProteinInferenceLabel> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String value;

    /**
     * initialise protein inference label
     *
     * @param value the value of the label
     * @param resource the resource of the label image
     */
    public ProteinInferenceLabel(String value, Resource resource) {
        super(value, resource);
        this.value = value;
        ProteinInferenceLabel.this.setHeight(15, Unit.PIXELS);
        ProteinInferenceLabel.this.setWidth(20, Unit.PIXELS);

    }

    /**
     * Override toString method
     *
     * @return the value of the label
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     *Override compare to method
     * @param proteinInferenceLabel label to compare
     * @return comparison result
     */
    @Override
    public int compareTo(ProteinInferenceLabel proteinInferenceLabel) {
        String valueToCompare = proteinInferenceLabel.toString();
        return this.value.compareTo(valueToCompare);
    }

}
