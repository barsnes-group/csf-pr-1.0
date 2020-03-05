package probe.com.view.components;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import com.vaadin.data.Item;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Table;
import probe.com.model.beans.PeptideBean;
import probe.com.view.core.CustomEmbedded;
import probe.com.view.core.CustomisedLabel;
import probe.com.view.core.ProteinInferenceLabel;

/**
 * this class represents the peptide table component
 *
 * @author Yehia Farag
 *
 */
public class PeptideTable extends Table implements Serializable {

    private static final long serialVersionUID = 1L;
    private DecimalFormat df = null;

    /**
     * Initialise peptide table
     *
     * @param peptideList peptide list related to selected protein
     * @param pepSet set of peptide sequence
     * @param toExportAsReport the table is made to be exported
     * @param mainProtDesc selected protein description (protein name)
     */
    public PeptideTable(Map<Integer, PeptideBean> peptideList, Set<String> pepSet, boolean toExportAsReport, String mainProtDesc) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        otherSymbols.setGroupingSeparator('.');
        df = new DecimalFormat("#.##", otherSymbols);
        PeptideTable.this.setSelectable(false);
        PeptideTable.this.setColumnReorderingAllowed(true);
        PeptideTable.this.setColumnCollapsingAllowed(true);
        PeptideTable.this.setImmediate(true); // react at once when something is selected
        PeptideTable.this.setWidth(100,Unit.PERCENTAGE);
        PeptideTable.this.setHeight(167,Unit.PIXELS);
        PeptideTable.this.addContainerProperty("Index", Integer.class, null, "", null, com.vaadin.ui.Table.ALIGN_RIGHT);

        String Protein_Inference = "Protein Inference";
        PeptideTable.this.addContainerProperty(Protein_Inference, ProteinInferenceLabel.class, null, "PI", null, com.vaadin.ui.Table.ALIGN_CENTER);

        PeptideTable.this.addContainerProperty("Peptide Protein(s)", String.class, null);
        PeptideTable.this.setColumnCollapsed("Peptide Protein(s)", true);

        PeptideTable.this.addContainerProperty("Peptide Prot. Descrip.", String.class, null);
        PeptideTable.this.setColumnCollapsed("Peptide Prot. Descrip.", true);

        final String sequence = "Sequence";
        PeptideTable.this.addContainerProperty(sequence, CustomisedLabel.class, null);
        PeptideTable.this.addContainerProperty("AA Before", String.class, null);
        PeptideTable.this.addContainerProperty("AA After", String.class, null);
        PeptideTable.this.setColumnCollapsed("AA Before", true);
        PeptideTable.this.setColumnCollapsed("AA After", true);
        PeptideTable.this.addContainerProperty("Peptide Start", String.class, null, "Start", null, com.vaadin.ui.Table.ALIGN_RIGHT);
        PeptideTable.this.addContainerProperty("Peptide End", String.class, null, "End", null, com.vaadin.ui.Table.ALIGN_RIGHT);
        PeptideTable.this.setColumnCollapsed("Peptide Start", true);
        PeptideTable.this.setColumnCollapsed("Peptide End", true);

        PeptideTable.this.addContainerProperty("# Validated Spectra", Integer.class, null, "#Spectra", null, Align.RIGHT);
        PeptideTable.this.addContainerProperty("Other Protein(s)", String.class, null);
        PeptideTable.this.addContainerProperty("Other Prot Descrip.", String.class, null);

        PeptideTable.this.setColumnCollapsed("Other Protein(s)", true);
        PeptideTable.this.addContainerProperty("Variable Modification", String.class, null);
        PeptideTable.this.addContainerProperty("Location Confidence", String.class, null);
        PeptideTable.this.setColumnCollapsed("Variable Modification", true);
        PeptideTable.this.setColumnCollapsed("Location Confidence", true);

        PeptideTable.this.addContainerProperty("Precursor Charge(s)", String.class, null, "Precursor Charge(s)", null, Align.RIGHT);

        PeptideTable.this.addContainerProperty("Enzymatic", CustomEmbedded.class, null, "Enzymatic", null, com.vaadin.ui.Table.Align.CENTER);

        PeptideTable.this.addContainerProperty("Sequence Tagged", String.class, null, "Sequence Annotated", null, Align.LEFT);
        PeptideTable.this.addContainerProperty("Deamidation & Glycopattern", CustomEmbedded.class, null, "Glycopeptide", null, Align.CENTER);
        PeptideTable.this.addContainerProperty("Glycopattern Positions", String.class, null, "Glyco Position(s)", null,Align.RIGHT);

        String Confidence = "Confidence";
        PeptideTable.this.addContainerProperty(Confidence, Double.class, null, Confidence, null,Align.RIGHT);
        PeptideTable.this.addContainerProperty("Validated", CustomEmbedded.class, null, "Validated", null, Align.CENTER);

        CustomEmbedded enz ;
        Resource res ;
        ProteinInferenceLabel pi =null;
        Resource res2 ;
        Resource res3 ;
        CustomisedLabel seq =null;
        CustomEmbedded deamidationAndGlycopattern ;

        CustomEmbedded validated ;
        int index = 1;
        for (PeptideBean pb : peptideList.values()) {
            if (pb.isEnzymatic()) {
                res = new ThemeResource("img/true.jpg");
            } else {
                res = new ThemeResource("img/false.jpg");
            }

            enz = new CustomEmbedded(pb.isEnzymatic(), res);
            enz.setWidth(16,Unit.PIXELS);
            enz.setHeight(16,Unit.PIXELS);
            enz.setDescription("" + pb.isEnzymatic());

            if (pb.isDeamidationAndGlycopattern() == null) {
                res3 = new ThemeResource("img/false.jpg");
                deamidationAndGlycopattern = new CustomEmbedded(false, res3);
                deamidationAndGlycopattern.setWidth(16,Unit.PIXELS);
                deamidationAndGlycopattern.setHeight(16,Unit.PIXELS);
                deamidationAndGlycopattern.setDescription("FALSE");
            } else if (pb.isDeamidationAndGlycopattern()) {
                res3 = new ThemeResource("img/true.jpg");
                deamidationAndGlycopattern = new CustomEmbedded(pb.isDeamidationAndGlycopattern(), res3);
                deamidationAndGlycopattern.setWidth(16,Unit.PIXELS);
                deamidationAndGlycopattern.setHeight(16,Unit.PIXELS);
                deamidationAndGlycopattern.setDescription("" + pb.isDeamidationAndGlycopattern());
            } else {
                res3 = new ThemeResource("img/false.jpg");
                deamidationAndGlycopattern = new CustomEmbedded(pb.isDeamidationAndGlycopattern(), res3);
                deamidationAndGlycopattern.setWidth(16,Unit.PIXELS);
                deamidationAndGlycopattern.setHeight(16,Unit.PIXELS);
                deamidationAndGlycopattern.setDescription("" + pb.isDeamidationAndGlycopattern());

            }
            if (pb.getProteinInference() == null) {

            } else if (pb.getProteinInference().equalsIgnoreCase("SINGLE PROTEIN")) {
                res2 = new ThemeResource("img/green.jpg");
                pi = new ProteinInferenceLabel(pb.getProteinInference(), res2);
                pi.setDescription(pb.getProteinInference());
                //add peptides protein and desc value in case of single protein
                pb.setPeptideProteins(pb.getMainProteinAccession());
                if (mainProtDesc != null && !mainProtDesc.equalsIgnoreCase("")) {
                    pb.setPeptideProteinsDescriptions(mainProtDesc);
                }

            } else if (pb.getProteinInference().trim().equalsIgnoreCase("UNRELATED PROTEINS")) {
                res2 = new ThemeResource("img/red.jpg");
                pi = new ProteinInferenceLabel(pb.getProteinInference(), res2);
                pi.setDescription(pb.getProteinInference());

            } else if (pb.getProteinInference().trim().equalsIgnoreCase("Related Proteins")) {
                res2 = new ThemeResource("img/yellow.jpg");
                pi = new ProteinInferenceLabel(pb.getProteinInference(), res2);
                pi.setDescription(pb.getProteinInference());
                if (!pb.getMainProteinAccession().equalsIgnoreCase("SHARED PEPTIDE")) {
                    if (pb.getOtherProteins() != null && !pb.getOtherProteins().equalsIgnoreCase("")) {
                        pb.setPeptideProteins(pb.getMainProteinAccession() + "," + pb.getOtherProteins());
                        if (mainProtDesc != null && !mainProtDesc.equalsIgnoreCase("")) {
                            pb.setPeptideProteinsDescriptions(mainProtDesc + ";" + pb.getOtherProteinDescriptions());
                        }
                    } else {
                        pb.setPeptideProteins(pb.getMainProteinAccession() + "," + pb.getPeptideProteins());
                        if (mainProtDesc != null && !mainProtDesc.equalsIgnoreCase("")) {
                            pb.setPeptideProteinsDescriptions(mainProtDesc + ";" + pb.getPeptideProteinsDescriptions());
                        }

                    }
                }

            } else if (pb.getProteinInference().trim().equalsIgnoreCase("UNRELATED ISOFORMS") || pb.getProteinInference().equalsIgnoreCase("ISOFORMS AND UNRELATED PROTEIN(S)")) {
                res2 = new ThemeResource("img/orange.jpg");
                pi = new ProteinInferenceLabel(pb.getProteinInference(), res2);
                pi.setDescription(pb.getProteinInference());
            } else {

                res2 = new ThemeResource("img/red.jpg");
                pi = new ProteinInferenceLabel(pb.getProteinInference(), res2);
                pi.setDescription(pb.getProteinInference());

            }
            boolean valid = false;

            if (pb.getValidated() == 1.0) {
                valid = true;
                res2 = new ThemeResource("img/true.jpg");
            } else {
                res2 = new ThemeResource("img/false.jpg");
            }

            validated = new CustomEmbedded(valid, res2);
            validated.setDescription(String.valueOf(pb.getValidated()));

            if (pepSet != null) {
                for (String str : pepSet) {
                    if (pb.getSequence().contains(str)) {
                        seq = new CustomisedLabel(pb.getSequence(), "red");
                        break;
                    }
                    seq = new CustomisedLabel(pb.getSequence(), "black");
                }
            } else {
                seq = new CustomisedLabel(pb.getSequence(), "black");
            }
            if (seq != null) {
                seq.setDescription("The Peptide Sequence: " + pb.getSequence());
            }

            PeptideTable.this.addItem(new Object[]{index, pi, pb.getPeptideProteins(), pb.getPeptideProteinsDescriptions(), seq, pb.getAaBefore(), pb.getAaAfter(), pb.getPeptideStart(), pb.getPeptideEnd(), pb.getNumberOfValidatedSpectra(),
                pb.getOtherProteins(), pb.getOtherProteinDescriptions(),
                pb.getVariableModification(), pb.getLocationConfidence(), pb.getPrecursorCharges(), enz, pb.getSequenceTagged(), deamidationAndGlycopattern, pb.getGlycopatternPositions(), Double.valueOf(df.format(pb.getConfidence())), validated}, index);
            index++;
        }
        PeptideTable.this.sort(new String[]{Confidence, "# Validated Spectra"}, new boolean[]{false, false});
        PeptideTable.this.setSortAscending(false);
        int indexing = 1;
        for (Object id : PeptideTable.this.getItemIds()) {
            Item item = PeptideTable.this.getItem(id);
            item.getItemProperty("Index").setValue(indexing);
            indexing++;
        }

        PeptideTable.this.setColumnWidth("Index", 33);
        PeptideTable.this.setColumnWidth("Protein_Inference", 33);
        PeptideTable.this.setColumnWidth("# Validated Spectra", 55);
        PeptideTable.this.setColumnWidth("Other Prot Descrip.", 110);
        PeptideTable.this.setColumnWidth("Precursor Charge(s)", 120);
        PeptideTable.this.setColumnWidth("Enzymatic", 55);
        PeptideTable.this.setColumnWidth("Deamidation & Glycopattern", 80);
        PeptideTable.this.setColumnWidth("Glycopattern Positions", 85);
        PeptideTable.this.setColumnWidth(Confidence, 65);
        PeptideTable.this.setColumnWidth("Validated", 55);
        for (Object propertyId : PeptideTable.this.getSortableContainerPropertyIds()) {
            if (propertyId.toString().equals(sequence)) {
                PeptideTable.this.setColumnExpandRatio(propertyId, 0.4f);
            } else if (propertyId.toString().equals("Sequence Tagged")) {
                PeptideTable.this.setColumnExpandRatio(propertyId.toString(), 0.6f);
            }
        }

    }
}
