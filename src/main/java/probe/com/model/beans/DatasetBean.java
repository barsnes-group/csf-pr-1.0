package probe.com.model.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents dataset object that store the main dataset information
 *
 * @author Yehia Farag
 */
public class DatasetBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int datasetId;
    private List<StandardProteinBean> standerdPlotProt;
    private int peptidesInclude;
    private String name, description;
    private int fractionsNumber;
    private Map<Integer, FractionBean> fractionsList; //the key is fraction id
    private Map<String, ProteinBean> proteinList; //the we use it only in case of protein file
    private Map<Integer, PeptideBean> peptideList = new HashMap<Integer, PeptideBean>();
    private String species, sampleType, sampleProcessing, instrumentType, fragMode, UploadedByName, email, publicationLink;
    private int proteinsNumber, peptidesNumber, datasetType;
    private int numberValidProt;
    private List<Integer> fractionIds;
    private Set<Integer> peptidesIds;
    private Map<String, PeptideBean> gPeptideList;

    /**
     * Set unique dataset id (dataset index)
     *
     * @param datasetId dataset index
     */
    public void setDatasetId(int datasetId) {
        this.datasetId = datasetId;
    }

    /**
     **Get unique dataset id (dataset index)
     *
     * @return dataset unique id
     */
    public int getDatasetId() {
        return datasetId;
    }

    /**
     * Set Dataset name
     *
     * @param name dataset name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Dataset name
     *
     * @return dataset name
     */
    public String getName() {
        return name;
    }

    /**
     * Set number of fractions in the dataset
     *
     * @param fractionsNumber
     */
    public void setFractionsNumber(int fractionsNumber) {
        this.fractionsNumber = fractionsNumber;
    }

    /**
     * Get number of fractions in the dataset
     *
     * @return number of the fractions
     */
    public int getFractionsNumber() {
        return fractionsNumber;
    }

    /**
     * Set full list of the fractions in the dataset
     *
     * @param fractionsList set of fractions objects that contain all fraction
     * information
     */
    public void setFractionsList(Map<Integer, FractionBean> fractionsList) {
        this.fractionsList = fractionsList;
    }

    /**
     * Get full list of the fractions in the dataset
     *
     * @return set of fractions objects that contain all fraction information
     */
    public Map<Integer, FractionBean> getFractionsList() {
        return fractionsList;
    }

    /**
     * Set map of protein objects included in the dataset
     *
     * @param proteinList map of proteins
     */
    public void setProteinList(Map<String, ProteinBean> proteinList) {
        this.proteinList = proteinList;
    }

    /**
     * Get map of protein objects included in the dataset
     *
     * @return proteinList map of proteins
     */
    public Map<String, ProteinBean> getProteinList() {
        return proteinList;
    }

    /**
     * Get map of peptides objects included in the dataset
     *
     * @return peptideList map of all peptides related to the dataset
     */
    public Map<Integer, PeptideBean> getPeptideList() {
        return peptideList;
    }

    /**
     * Set map of peptides objects included in the dataset
     *
     * @param peptideList map of all peptides related to the dataset
     */
    public void setPeptideList(Map<Integer, PeptideBean> peptideList) {
        this.peptideList = peptideList;
    }

    /**
     * Get total number of peptides included in the dataset
     *
     * @return integer number of the peptides
     */
    public int getPeptidesInclude() {
        return peptidesInclude;
    }

    /**
     * Set total number of peptides included in the dataset
     *
     * @param peptidesInclude integer number of the peptides
     */
    public void setPeptidesInclude(int peptidesInclude) {
        this.peptidesInclude = peptidesInclude;
    }

    /**
     * Get species
     *
     * @return species
     */
    public String getSpecies() {
        return species;
    }

    /**
     * Set species
     *
     * @param species
     */
    public void setSpecies(String species) {
        this.species = species;
    }

    /**
     * Set the sample type
     *
     * @return the type of the sample
     */
    public String getSampleType() {
        return sampleType;
    }

    /**
     * Set the sample type
     *
     * @param sampleType the type of the sample
     */
    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    /**
     * Get the sample processing
     *
     * @return the sample processing
     */
    public String getSampleProcessing() {
        return sampleProcessing;
    }

    /**
     * Set the sample processing
     *
     * @param sampleProcessing
     */
    public void setSampleProcessing(String sampleProcessing) {
        this.sampleProcessing = sampleProcessing;
    }

    /**
     * Get the type of the used instrument
     *
     * @return instrument type
     */
    public String getInstrumentType() {
        return instrumentType;
    }

    /**
     * Set the type of the used instrument
     *
     * @param instrumentType instrument type
     */
    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    /**
     * Get the fragmentation mode
     *
     * @return fragmentation mode
     *
     */
    public String getFragMode() {
        return fragMode;
    }

    /**
     * Set the fragmentation mode
     *
     * @param fragMode fragmentation mode
     */
    public void setFragMode(String fragMode) {
        this.fragMode = fragMode;
    }

    /**
     * Get Admin name
     *
     * @return admin name
     */
    public String getUploadedByName() {
        return UploadedByName;
    }

    /**
     * Set Admin name
     *
     * @param uploadedByName admin name
     */
    public void setUploadedByName(String uploadedByName) {
        UploadedByName = uploadedByName;
    }

    /**
     * Get Admin Email
     *
     * @return admin email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set Admin email
     *
     * @param email email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the link for the publication
     *
     * @return web link for the publication
     */
    public String getPublicationLink() {
        return publicationLink;
    }

    /**
     * Set the link for the publication
     *
     * @param publicationLink web link for the publication
     */
    public void setPublicationLink(String publicationLink) {
        this.publicationLink = publicationLink;
    }

    /**
     * Get the total number for the proteins identified in the dataset
     *
     * @return number of identified proteins
     */
    public int getProteinsNumber() {
        return proteinsNumber;
    }

    /**
     * Set the total number for the proteins identified in the dataset
     *
     * @param proteinsNumber number of identified proteins
     */
    public void setProteinsNumber(int proteinsNumber) {
        this.proteinsNumber = proteinsNumber;
    }

    /**
     * Get the total number for the peptides identified in the dataset
     *
     * @return number of identified peptides
     */
    public int getPeptidesNumber() {
        return peptidesNumber;
    }

    /**
     * Set the total number for the peptides identified in the dataset
     *
     * @param peptidesNumber number of identified peptides
     */
    public void setPeptidesNumber(int peptidesNumber) {
        this.peptidesNumber = peptidesNumber;
    }

    /**
     * Get the dataset general description
     *
     * @return dataset description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the dataset general description
     *
     * @param description dataset description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get list of the fractions id (indexes) related to the dataset
     *
     * @return fraction indexes
     */
    public List<Integer> getFractionIds() {
        return fractionIds;
    }

    /**
     * Set list of the fractions id (indexes) related to the dataset
     *
     * @param fractionIds fraction indexes
     */
    public void setFractionIds(List<Integer> fractionIds) {
        this.fractionIds = fractionIds;
    }

    /**
     * Get list of the peptides id (indexes) related to the dataset
     *
     * @return peptides indexes
     */
    public Set<Integer> getPeptidesIds() {
        return peptidesIds;
    }

    /**
     * Set list of the peptides id (indexes) related to the dataset
     *
     * @param peptidesIds peptides indexes
     */
    public void setPeptidesIds(Set<Integer> peptidesIds) {
        this.peptidesIds = peptidesIds;
    }

    /**
     * Get dataset type 1: identification 2: quantification
     *
     * @return dataset type
     */
    public int getDatasetType() {
        return datasetType;
    }

    /**
     * Set dataset type 1: identification 2: quantification
     *
     * @param datasetType dataset type
     */
    public void setDatasetType(int datasetType) {
        this.datasetType = datasetType;
    }

    /**
     * Get the total number for the validated proteins identified in the dataset
     *
     * @return number of identified validated proteins
     */
    public int getNumberValidProt() {
        return numberValidProt;
    }

    /**
     * Set the total number for the validated proteins identified in the dataset
     *
     * @param numberValidProt number of identified validated proteins
     */
    public void setNumberValidProt(int numberValidProt) {
        this.numberValidProt = numberValidProt;
    }

    /**
     * Get set of standard proteins plot that used in gel charts
     *
     * @return list of standard protein objects
     */
    public List<StandardProteinBean> getStanderdPlotProt() {
        return standerdPlotProt;
    }

    /**
     * Set list of standard proteins plot that used in gel charts
     *
     * @param standerdPlotProt list of standard protein objects
     */
    public void setStanderdPlotProt(List<StandardProteinBean> standerdPlotProt) {
        this.standerdPlotProt = standerdPlotProt;
    }

    /**
     * Get full list of peptides objects related to the dataset
     *
     * @return full related peptides list
     */
    public Map<String, PeptideBean> getgPeptideList() {
        return gPeptideList;
    }

   /**
     * Set full list of peptides objects related to the dataset
     *
     * @param gPeptideList  full related peptides list
     */
    public void setgPeptideList(Map<String, PeptideBean> gPeptideList) {
        this.gPeptideList = gPeptideList;
    }
}
