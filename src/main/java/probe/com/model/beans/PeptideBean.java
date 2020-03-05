package probe.com.model.beans;

import java.io.Serializable;

/**
 *This class represents peptide object
 * @author Yehia Farag
 */
public class PeptideBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String mainProteinAccession;
    private String otherProteins;
    private String peptideProteins;
    private String otherProteinDescriptions;
    private String peptideProteinsDescriptions;
    private String aaBefore;
    private String sequence;
    private String aaAfter;
    private String peptideStart;
    private String fixedModification;
    private String peptideEnd;
    private String variableModification;
    private String locationConfidence;
    private String precursorCharges;
    private int numberOfValidatedSpectra;
    private double score;
    private double confidence;
    private int peptideId;
    private String proteinInference;
    private String sequenceTagged;
    private boolean enzymatic;
    private double validated;
    private int decoy;
    private boolean starred;
    private Boolean deamidationAndGlycopattern;
    private String glycopatternPositions;
    private Boolean likelyNotGlycosite;
    private String mainProteinDescription;

    /**
     * Get  main protein accession (group leading accession)
     *
     * @return mainProteinAccession accession number
     */
    public String getMainProteinAccession() {
        return mainProteinAccession;
    }

    /**
     * Set main protein accession (group leading accession)
     *
     * @param mainProteinAccession mainProteinAccession accession number
     */
    public void setMainProteinAccession(String mainProteinAccession) {
        this.mainProteinAccession = mainProteinAccession;
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
     * Set other proteins in the protein group
     *
     * @param otherProteins other protein accessions in the group
     */
    public void setOtherProteins(String otherProteins) {
        this.otherProteins = otherProteins;
    }

    /**
     * Get protein accessions related to the peptide as string
     *
     * @return protein accessions related to the peptide
     */
    public String getPeptideProteins() {
        return peptideProteins;
    }

    /**
     * Set protein accessions related to the peptide as string
     *
     * @param peptideProteins protein accessions related to the peptide
     */
    public void setPeptideProteins(String peptideProteins) {
        this.peptideProteins = peptideProteins;
    }

    /**
     * Get other proteins in the group description (proteins name)
     *
     * @return proteins name
     */
    public String getOtherProteinDescriptions() {
        return otherProteinDescriptions;
    }

    /**
     * Set other proteins in the group description (proteins name)
     *
     * @param otherProteinDescriptions proteins name
     */
    public void setOtherProteinDescriptions(String otherProteinDescriptions) {
        this.otherProteinDescriptions = otherProteinDescriptions;
    }

    /**
     *
     * @return
     */
    public String getPeptideProteinsDescriptions() {
        return peptideProteinsDescriptions;
    }

    /**
     * Set full protein group description (proteins name)
     *
     * @param peptideProteinsDescriptions proteins name
     */
    public void setPeptideProteinsDescriptions(
            String peptideProteinsDescriptions) {
        this.peptideProteinsDescriptions = peptideProteinsDescriptions;
    }

    /**
     * Get amino acid before the peptide
     *
     * @return amino acid
     */
    public String getAaBefore() {
        return aaBefore;
    }

    /**
     * Set amino acid before the peptide
     *
     * @param aaBefore amino acid
     */
    public void setAaBefore(String aaBefore) {
        this.aaBefore = aaBefore;
    }

    /**
     * Get non modified peptide sequence
     *
     * @return peptide sequence
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * Set non modified peptide sequence
     *
     * @param sequence peptide sequence
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * Get amino acid after the peptide
     *
     * @return amino acid
     */
    public String getAaAfter() {
        return aaAfter;
    }

    /**
     * Set amino acid after the peptide
     *
     * @param aaAfter amino acid
     */
    public void setAaAfter(String aaAfter) {
        this.aaAfter = aaAfter;
    }

    /**
     * Get peptide start position as string (';' separated)
     *
     * @return peptide start position
     */
    public String getPeptideStart() {

        return peptideStart;
    }

    /**
     * Set peptide start position as string (';' separated)
     *
     * @param peptideStart peptide start position
     */
    public void setPeptideStart(String peptideStart) {
        this.peptideStart = peptideStart;
    }

    /**
     * Set peptide id
     *
     * @return peptide unique id
     */
    public int getPeptideId() {
        return peptideId;
    }

    /**
     * Set peptide id
     *
     * @param peptideId peptide unique id
     */
    public void setPeptideId(int peptideId) {
        this.peptideId = peptideId;
    }

    /**
     * Get number of validated spectra related to the peptide
     *
     * @return number of validated spectra
     */
    public int getNumberOfValidatedSpectra() {
        return numberOfValidatedSpectra;
    }

    /**
     * Set number of validated spectra related to the peptide
     *
     * @param numberOfValidatedSpectra number of validated spectra
     */
    public void setNumberOfValidatedSpectra(int numberOfValidatedSpectra) {
        this.numberOfValidatedSpectra = numberOfValidatedSpectra;
    }

    /**
     * Get the peptide location confidence
     *
     * @return peptide location confidence
     */
    public String getLocationConfidence() {
        return locationConfidence;
    }

    /**
     * Get the peptide location confidence
     *
     * @param locationConfidence peptide location confidence
     */
    public void setLocationConfidence(String locationConfidence) {
        this.locationConfidence = locationConfidence;
    }

    /**
     * Get peptide variable modification
     *
     * @return modification name
     */
    public String getVariableModification() {
        return variableModification;
    }

    /**
     * Set peptide variable modification
     *
     * @param variableModification modification name
     */
    public void setVariableModification(String variableModification) {
        this.variableModification = variableModification;
    }

    /**
     * Get peptide end position as string (';' separated)
     *
     * @return peptide end position
     */
    public String getPeptideEnd() {
        return peptideEnd;
    }

    /**
     * Set peptide end position as string (';' separated)
     *
     * @po
     * @param peptideEnd peptide end position
     */
    public void setPeptideEnd(String peptideEnd) {
        this.peptideEnd = peptideEnd;
    }

    /**
     * Get the charge value
     *
     * @return charge value
     */
    public String getPrecursorCharges() {
        return precursorCharges;
    }

    /**
     * Set the charge value
     *
     * @param precursorCharges charge value
     */
    public void setPrecursorCharges(String precursorCharges) {
        this.precursorCharges = precursorCharges;
    }

    /**
     * Get peptide calculated score
     *
     * @return score
     */
    public double getScore() {
        return score;
    }

    /**
     * Set peptide calculated score
     *
     * @param score score
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Get peptide confidence score
     *
     * @return confidence score
     */
    public double getConfidence() {
        return confidence;
    }

    /**
     * Set peptide confidence score
     *
     * @param confidence score
     */
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    /**
     * Get peptide fixed modification
     *
     * @return modification names
     */
    public String getFixedModification() {
        return fixedModification;
    }

    /**
     * Set peptide fixed modifications
     *
     * @param fixedModification modification names
     */
    public void setFixedModification(String fixedModification) {
        this.fixedModification = fixedModification;
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
     * Set protein inference (single, shared, etc)
     *
     * @param proteinInference PI value
     */
    public void setProteinInference(String proteinInference) {
        this.proteinInference = proteinInference;
    }

    /**
     * Get tagged peptide sequence
     *
     * @return peptide sequence
     */
    public String getSequenceTagged() {
        return sequenceTagged;
    }

    /**
     * Set tagged peptide sequence
     *
     * @param sequenceTagged peptide sequence
     */
    public void setSequenceTagged(String sequenceTagged) {
        this.sequenceTagged = sequenceTagged;
    }

    /**
     * Check if the peptide is a enzymatic peptide
     *
     * @return is a enzymatic peptide
     */
    public boolean isEnzymatic() {
        return enzymatic;
    }

    /**
     * Set the peptide is a enzymatic peptide
     *
     * @param enzymatic is a enzymatic peptide
     */
    public void setEnzymatic(boolean enzymatic) {
        this.enzymatic = enzymatic;
    }

    /**
     * Check if the peptide is a validated peptide
     *
     * @return is a validated peptide
     */
    public double getValidated() {
        return validated;
    }

    /**
     * Set the peptide is a validated peptide
     *
     * @param validated is a validated peptide
     */
    public void setValidated(double validated) {
        this.validated = validated;
    }

    /**
     * Check if the peptide is a decoy peptide
     *
     * @return is a decoy peptide
     */
    public int getDecoy() {
        return decoy;
    }

    /**
     * Set the peptide is a decoy peptide
     *
     * @param decoy
     */
    public void setDecoy(int decoy) {
        this.decoy = decoy;
    }

    /**
     * Check if the peptide has de-amidation and glyco-pattern
     *
     * @return the peptide has de-amidation and glyco-pattern
     */
    public Boolean getDeamidationAndGlycopattern() {
        return deamidationAndGlycopattern;
    }

    /**
     * Set if the peptide has de-amidation and glyco-pattern
     *
     * @param deamidationAndGlycopattern the peptide has de-amidation and
     * glyco-pattern
     */
    public void setDeamidationAndGlycopattern(Boolean deamidationAndGlycopattern) {
        this.deamidationAndGlycopattern = deamidationAndGlycopattern;
    }

    /**
     * Check if it is not likely for the peptide to have glyco-site
     *
     * @return likely not glyco-site
     */
    public Boolean isLikelyNotGlycopeptide() {
        return likelyNotGlycosite;
    }

    /**
     * Set if it is not likely for the peptide to have glyco-site
     *
     * @param likelyNotGlycosite
     */
    public void setLikelyNotGlycosite(Boolean likelyNotGlycosite) {
        this.likelyNotGlycosite = likelyNotGlycosite;
    }

    /**
     * Check if the peptide has deamidation and glyco pattern
     *
     * @return peptide has deamidation and glyco pattern
     */
    public Boolean isDeamidationAndGlycopattern() {
        return deamidationAndGlycopattern;
    }

    /**
     * Set the peptide has deamidation and glyco pattern
     *
     * @param deamidationAndGlycopattern peptide has deamidation and glyco
     * pattern
     */
    public void setDeamidationAndGlycopattern(boolean deamidationAndGlycopattern) {
        this.deamidationAndGlycopattern = deamidationAndGlycopattern;
    }

    /**
     * Get the peptide glyco pattern position (optional)
     *
     * @return glyco pattern positions
     */
    public String getGlycopatternPositions() {
        return glycopatternPositions;
    }

    /**
     * Set the peptide glyco pattern position (optional)
     *
     * @param glycopatternPositions postion
     */
    public void setGlycopatternPositions(String glycopatternPositions) {
        this.glycopatternPositions = glycopatternPositions;
    }

    /**
     * Get leading protein in the group description (protein name)
     *
     * @return protein name
     */
    public String getMainProteinDescription() {
        return mainProteinDescription;
    }

    /**
     * Set leading protein in the group description (protein name)
     *
     * @param mainProteinDescription protein name
     */
    public void setMainProteinDescription(String mainProteinDescription) {
        this.mainProteinDescription = mainProteinDescription;
    }
}
