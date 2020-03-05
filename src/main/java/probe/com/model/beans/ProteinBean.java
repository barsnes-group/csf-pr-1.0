package probe.com.model.beans;

import java.io.Serializable;

/**
 * This class represents protein object
 *
 * @author Yehia Farag
 */
public class ProteinBean implements Serializable, Comparable<ProteinBean> {

    private static final long serialVersionUID = 1L;
    private String accession;
    private int datasetId;
    private String description;
    private boolean validated;
    private int decoy;
    private String geneName;
    private String chromosomeNumber;
    private int protGroupId;

    private String otherProteins;
    private String proteinInference;
    private double sequenceCoverage;
    private double observableCoverage;
    private String confidentPtmSites;
    private int numberValidatedPeptides;
    private int numberValidatedSpectra;
    private int numberSpectra;
    private int numberPeptides;
    private double nsaf;
    private double mw_kDa;
    private double score;
    private double confidence;
    private String peptideFractionSpread_lower_range_kDa;
    private String peptideFractionSpread_upper_range_kDa;
    private String spectrumFractionSpread_lower_range_kDa;
    private String spectrumFractionSpread_upper_range_kDa;
    private boolean nonEnzymaticPeptides;
    private int numberOfPeptidePerFraction;
    private int numberOfSpectraPerFraction;
    private double AveragePrecursorIntensityPerFraction;

    /**
     * Initialise protein object.
     */
    public ProteinBean() {
    }

    /**
     * Initialise protein from another abject (clone)
     *
     * @param proteinBean Object to be cloned
     */
    public ProteinBean(ProteinBean proteinBean) {
        this.accession = proteinBean.getAccession();
        this.description = proteinBean.getDescription();
        this.validated = proteinBean.isValidated();
        this.otherProteins = proteinBean.getOtherProteins();
        this.proteinInference = proteinBean.getProteinInference();
        this.sequenceCoverage = proteinBean.getSequenceCoverage();
        this.observableCoverage = proteinBean.getObservableCoverage();
        this.confidentPtmSites = proteinBean.getConfidentPtmSites();

        this.numberValidatedPeptides = proteinBean.getNumberValidatedPeptides();
        this.numberValidatedSpectra = proteinBean.getNumberValidatedSpectra();
        this.nsaf = proteinBean.getNsaf();
        this.mw_kDa = proteinBean.getMw_kDa();
        this.score = proteinBean.getScore();
        this.confidence = proteinBean.getConfidence();
        this.peptideFractionSpread_lower_range_kDa = proteinBean.getPeptideFractionSpread_lower_range_kDa();
        this.peptideFractionSpread_upper_range_kDa = proteinBean.getPeptideFractionSpread_upper_range_kDa();
        this.spectrumFractionSpread_lower_range_kDa = proteinBean.getSpectrumFractionSpread_lower_range_kDa();
        this.spectrumFractionSpread_upper_range_kDa = proteinBean.getSpectrumFractionSpread_upper_range_kDa();
        this.nonEnzymaticPeptides = proteinBean.isNonEnzymaticPeptides();
        this.geneName = proteinBean.getGeneName();
        this.chromosomeNumber = proteinBean.getChromosomeNumber();
        this.numberPeptides = proteinBean.getNumberPeptides();
        this.numberSpectra = proteinBean.getNumberSpectra();
        this.datasetId = proteinBean.getDatasetId();
        this.decoy = proteinBean.getDecoy();
        this.protGroupId = proteinBean.getProtGroupId();

    }

    /**
     * Get the protein related gene name
     *
     * @return gene name
     */
    public String getGeneName() {
        return geneName;
    }

    /**
     * Set the protein related gene name
     *
     * @param geneName gene name
     */
    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    /**
     * Get the related chromosome number
     *
     * @return chromosome number
     */
    public String getChromosomeNumber() {
        return chromosomeNumber;
    }

    /**
     * Set the related chromosome number
     *
     * @param chromosomeNumber chromosome number
     */
    public void setChromosomeNumber(String chromosomeNumber) {
        this.chromosomeNumber = chromosomeNumber;
    }

    /**
     * Check if the protein is a validated protein
     *
     * @return is a validated protein
     */
    public boolean isValidated() {
        return validated;
    }

    /**
     * Set the protein is a validated protein
     *
     * @param validated is a validated protein
     */
    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    /**
     * Set main protein accession (group leading accession)
     *
     * @param accession mainProteinAccession accession number
     */
    public void setAccession(String accession) {
        this.accession = accession;
    }

    /**
     * Get main protein accession (group leading accession)
     *
     * @return accession number
     */
    public String getAccession() {
        return accession;
    }

    /**
     * Set leading protein in the group description (protein name)
     *
     * @param description protein name
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get leading protein in the group description (protein name)
     *
     * @return protein name
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set other proteins in the protein group
     *
     * @param otherProteins other protein accessions in the group
     */
    public void setOtherProteins(String otherProteins) {
        this.otherProteins = otherProteins;
    }

    /**
     * Get other proteins in the protein group
     *
     * @return other protein accessions in the group
     */
    public String getOtherProteins() {
        return otherProteins;
    }

    /**
     * Set protein inference (single, shared, etc)
     *
     * @param proteinInference PI value
     */
    public void setProteinInference(String proteinInference) {
        this.proteinInference = proteinInference;
    }

    /**
     * Get protein inference (single, shared, etc)
     *
     * @return PI value
     */
    public String getProteinInference() {
        return proteinInference;
    }

    /**
     * Set percent of the protein sequence coverage
     *
     * @param sequenceCoverage coverage %
     */
    public void setSequenceCoverage(double sequenceCoverage) {
        this.sequenceCoverage = sequenceCoverage;
    }

    /**
     * Get percent of the protein sequence coverage
     *
     * @return coverage %
     */
    public double getSequenceCoverage() {
        return sequenceCoverage;
    }

    /**
     * Set percent of the possible protein sequence coverage
     *
     * @param observableCoverage coverage %
     */
    public void setObservableCoverage(double observableCoverage) {
        this.observableCoverage = observableCoverage;
    }

    /**
     * Get percent of the possible protein sequence coverage
     *
     * @return coverage %
     */
    public double getObservableCoverage() {
        return observableCoverage;
    }

    /**
     * Set confident post translation modifications site
     *
     * @param confidentPtmSites modifications site
     */
    public void setConfidentPtmSites(String confidentPtmSites) {
        this.confidentPtmSites = confidentPtmSites;
    }

    /**
     * Get confident post translation modifications site
     *
     * @return modifications site
     */
    public String getConfidentPtmSites() {
        return confidentPtmSites;
    }

    /**
     * Set number of validated peptides related to the protein group
     *
     * @param numberValidatedPeptides validated peptides number
     */
    public void setNumberValidatedPeptides(int numberValidatedPeptides) {
        this.numberValidatedPeptides = numberValidatedPeptides;
    }

    /**
     * Get number of validated peptides related to the protein group
     *
     * @return validated peptides number
     */
    public int getNumberValidatedPeptides() {
        return numberValidatedPeptides;
    }

    /**
     * Set number of validated spectra related to the protein group
     *
     * @param numberValidatedSpectra validated spectra number
     */
    public void setNumberValidatedSpectra(int numberValidatedSpectra) {
        this.numberValidatedSpectra = numberValidatedSpectra;
    }

    /**
     * Get number of validated spectra related to the protein group
     *
     * @return validated spectra number
     */
    public int getNumberValidatedSpectra() {
        return numberValidatedSpectra;
    }

    /**
     * Set a normalised spectral abundance factor
     *
     * @param nsaf
     */
    public void setNsaf(double nsaf) {
        this.nsaf = nsaf;
    }

    /**
     * Get a normalised spectral abundance factor
     *
     * @return nsaf value
     */
    public double getNsaf() {
        return nsaf;
    }

    /**
     * Set the protein molecular weight in kilo-Dalton
     *
     * @param mw_kDa value in kilo-Dalton
     */
    public void setMw_kDa(double mw_kDa) {
        this.mw_kDa = mw_kDa;
    }

    /**
     * Get the protein molecular weight in kilo-Dalton
     *
     * @return value in kilo-Dalton
     */
    public double getMw_kDa() {
        return mw_kDa;
    }

    /**
     * Set protein calculated score
     *
     * @param score score
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Get protein calculated score
     *
     * @return score
     */
    public double getScore() {
        return score;
    }

    /**
     * Set protein confidence score
     *
     * @param confidence score
     */
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    /**
     * Get protein confidence score
     *
     * @return confidence score
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * Set number of peptides per single fraction
     *
     * @param numberOfPeptidePerFraction number of peptide per fraction
     */
    public void setNumberOfPeptidePerFraction(int numberOfPeptidePerFraction) {
        this.numberOfPeptidePerFraction = numberOfPeptidePerFraction;
    }

    /**
     * Get number of peptides per single fraction
     *
     * @return number of peptide per fraction
     */
    public int getNumberOfPeptidePerFraction() {
        return numberOfPeptidePerFraction;
    }

    /**
     * Set number of spectra per single fraction
     *
     * @param numberOfSpectraPerFraction number of spectra per fraction
     */
    public void setNumberOfSpectraPerFraction(int numberOfSpectraPerFraction) {
        this.numberOfSpectraPerFraction = numberOfSpectraPerFraction;
    }

    /**
     * Get number of spectra per single fraction
     *
     * @return number of spectra per fraction
     */
    public int getNumberOfSpectraPerFraction() {
        return numberOfSpectraPerFraction;
    }

    /**
     * Set average precursor intensity per fraction
     *
     * @param averagePrecursorIntensityPerFraction average precursor intensity
     * per fraction
     */
    public void setAveragePrecursorIntensityPerFraction(
            double averagePrecursorIntensityPerFraction) {
        AveragePrecursorIntensityPerFraction = averagePrecursorIntensityPerFraction;
    }

    /**
     * Get average precursor intensity per fraction
     *
     * @return average precursor intensity per fraction
     */
    public double getAveragePrecursorIntensityPerFraction() {
        return AveragePrecursorIntensityPerFraction;
    }

    /**
     * Check if the peptides is a non enzymatic peptides
     *
     * @return is a non enzymatic peptides
     */
    public boolean isNonEnzymaticPeptides() {
        return nonEnzymaticPeptides;
    }

    /**
     * Set the peptides is a non enzymatic peptides
     *
     * @param nonEnzymaticPeptides is a non enzymatic peptides
     */
    public void setNonEnzymaticPeptides(boolean nonEnzymaticPeptides) {
        this.nonEnzymaticPeptides = nonEnzymaticPeptides;
    }

    /**
     *
     * @return
     */
    public String getPeptideFractionSpread_lower_range_kDa() {
        return peptideFractionSpread_lower_range_kDa;
    }

    /**
     *
     * @param peptideFractionSpread_lower_range_kDa
     */
    public void setPeptideFractionSpread_lower_range_kDa(
            String peptideFractionSpread_lower_range_kDa) {
        this.peptideFractionSpread_lower_range_kDa = peptideFractionSpread_lower_range_kDa;
    }

    /**
     *
     * @return
     */
    public String getPeptideFractionSpread_upper_range_kDa() {
        return peptideFractionSpread_upper_range_kDa;
    }

    /**
     *
     * @param peptideFractionSpread_upper_range_kDa
     */
    public void setPeptideFractionSpread_upper_range_kDa(
            String peptideFractionSpread_upper_range_kDa) {
        this.peptideFractionSpread_upper_range_kDa = peptideFractionSpread_upper_range_kDa;
    }

    /**
     *
     * @return
     */
    public String getSpectrumFractionSpread_lower_range_kDa() {
        return spectrumFractionSpread_lower_range_kDa;
    }

    /**
     *
     * @param spectrumFractionSpread_lower_range_kDa
     */
    public void setSpectrumFractionSpread_lower_range_kDa(
            String spectrumFractionSpread_lower_range_kDa) {
        this.spectrumFractionSpread_lower_range_kDa = spectrumFractionSpread_lower_range_kDa;
    }

    /**
     *
     * @return
     */
    public String getSpectrumFractionSpread_upper_range_kDa() {
        return spectrumFractionSpread_upper_range_kDa;
    }

    /**
     *
     * @param spectrumFractionSpread_upper_range_kDa
     */
    public void setSpectrumFractionSpread_upper_range_kDa(
            String spectrumFractionSpread_upper_range_kDa) {
        this.spectrumFractionSpread_upper_range_kDa = spectrumFractionSpread_upper_range_kDa;
    }

    /**
     * Get number of related spectra
     *
     * @return number of related spectra
     */
    public int getNumberSpectra() {
        return numberSpectra;
    }

    /**
     * Set number of related spectra
     *
     * @param numberSpectra number of related spectra
     */
    public void setNumberSpectra(int numberSpectra) {
        this.numberSpectra = numberSpectra;
    }

    /**
     * Get number of related peptides
     *
     * @return number of related peptides
     */
    public int getNumberPeptides() {
        return numberPeptides;
    }

    /**
     * Set number of related peptides
     *
     * @param numberPeptides number of related peptides
     */
    public void setNumberPeptides(int numberPeptides) {
        this.numberPeptides = numberPeptides;
    }

    /**
     * Check if the protein is a decoy protein
     *
     * @return is a decoy protein
     */
    public int getDecoy() {
        return decoy;
    }

    /**
     * Set the protein is a decoy protein
     *
     * @param decoy
     */
    public void setDecoy(int decoy) {
        this.decoy = decoy;
    }

    /**
     * Get dataset id
     *
     * @return the dataset id (index)
     */
    public int getDatasetId() {
        return datasetId;
    }

    /**
     * Get dataset id
     *
     * @param datasetId the dataset id (index)
     */
    public void setDatasetId(int datasetId) {
        this.datasetId = datasetId;
    }

    /**
     * Get the protein group key
     *
     * @return protein group unique key
     */
    public int getProtGroupId() {
        return protGroupId;
    }

    /**
     * Set the protein group key
     *
     * @param protGroupId protein group unique key
     */
    public void setProtGroupId(int protGroupId) {
        this.protGroupId = protGroupId;
    }

    /**
     * Override compare to method
     *
     * @param comparableProteinBean
     * @return comparison results
     */
    @Override
    public int compareTo(ProteinBean comparableProteinBean) {
        if (this.nsaf > comparableProteinBean.getNsaf()) {
            return 1;
        } else {
            return -1;
        }

    }
}
