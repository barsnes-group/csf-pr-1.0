package probe.com.model.beans;

import java.io.Serializable;
import java.util.Map;

/**
 * This class represents fraction objects
 *
 * @author Yehia Farag
 */
public class FractionBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int fractionId;
    private Map<String, ProteinBean> proteinList; //the key is protein Accession  will be used for insertion only
    private double minRange;
    private double maxRange;
    private int fractionIndex;

    /**
     * Set unique id for fraction (index)
     *
     * @param fractionId fraction index
     */
    public void setFractionId(int fractionId) {
        this.fractionId = fractionId;
    }

    /**
     * Get unique id for fraction
     *
     * @return fraction key
     */
    public int getFractionId() {
        return fractionId;
    }

    /**
     * Set list of protein objects that is related to the fraction
     *
     * @param proteinList list of protein objects
     */
    public void setProteinList(Map<String, ProteinBean> proteinList) {
        this.proteinList = proteinList;
    }

    /**
     * Get list of protein objects that is related to the fraction
     *
     * @return list of protein objects
     */
    public Map<String, ProteinBean> getProteinList() {
        return proteinList;
    }

    /**
     * Get fraction minimum range
     *
     * @return minimum range
     */
    public double getMinRange() {
        return minRange;
    }

    /**
     * Set fraction minimum range
     *
     * @param minRange minimum range
     */
    public void setMinRange(double minRange) {
        this.minRange = minRange;
    }

    /**
     * Get fraction maximum range
     *
     * @return maximum range
     */
    public double getMaxRange() {
        return maxRange;
    }

    /**
     * Set fraction maximum range
     *
     * @param maxRange maximum range
     */
    public void setMaxRange(double maxRange) {
        this.maxRange = maxRange;
    }

    /**
     * Get fraction unique index
     *
     * @return fraction unique index
     */
    public int getFractionIndex() {
        return fractionIndex;
    }

    /**
     * Set fraction unique index
     *
     * @param fractionIndex fraction unique index
     */
    public void setFractionIndex(int fractionIndex) {
        this.fractionIndex = fractionIndex;
    }

}
