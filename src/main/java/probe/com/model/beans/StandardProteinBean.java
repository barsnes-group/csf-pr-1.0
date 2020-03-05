package probe.com.model.beans;

import java.io.Serializable;

/**
 * This class represents standard protein object that use as reference for
 * fraction plots
 *
 * @author Yehia Farag
 */
public class StandardProteinBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private double MW_kDa;
    private int lowerFraction;
    private int upperFraction;
    private String fractionIndicator;
    private String name;
    private String color;
    private boolean theoretical;

    /**
     * Get the lower fraction limit
     *
     * @return the value of lower fraction
     */
    public int getLowerFraction() {
        return lowerFraction;
    }

    /**
     * Set the lower fraction limit
     *
     * @param lowerFraction the value of lower fraction
     */
    public void setLowerFraction(int lowerFraction) {
        this.lowerFraction = lowerFraction;
    }

    /**
     * Get molecular weight in kDa
     *
     * @return value in kDa
     */
    public double getMW_kDa() {
        return MW_kDa;
    }

    /**
     * Set molecular weight in kDa
     *
     * @param mW_kDa value in kDa
     */
    public void setMW_kDa(double mW_kDa) {
        MW_kDa = mW_kDa;
    }

    /**
     * Get the upper fraction limit
     *
     * @return the value of upper fraction
     */
    public int getUpperFraction() {
        return upperFraction;
    }

    /**
     * Set the upper fraction limit
     *
     * @param upperFraction the value of upper fraction
     */
    public void setUpperFraction(int upperFraction) {
        this.upperFraction = upperFraction;
    }

    /**
     * Get protein name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set protein name
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the reference colour (hashed colour used in the plot)
     *
     * @return hashed colour
     */
    public String getColor() {
        return color;
    }

    /**
     * Set the reference colour (hashed colour used in the plot)
     *
     * @param color hashed colour
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Override to String method the get name and value
     *
     * @return name and colour of the protein
     */
    @Override
    public String toString() {
        return this.name + " " + this.color;
    }

    /**
     * Check if the protein is theoretical
     *
     * @return the protein is theoretical
     */
    public boolean isTheoretical() {
        return theoretical;
    }

    /**
     * Set the protein to be theoretical
     *
     * @param theoretical the protein is theoretical
     */
    public void setTheoretical(boolean theoretical) {
        this.theoretical = theoretical;
    }

    /**
     * Get the fraction indicator
     *
     * @return fraction indicator value
     */
    public String getFractionIndicator() {
        return fractionIndicator;
    }

    /**
     * Set the fraction indicator
     *
     * @param fractionIndicator  fraction indicator value
     */
    public void setFractionIndicator(String fractionIndicator) {
        this.fractionIndicator = fractionIndicator;
    }
}
