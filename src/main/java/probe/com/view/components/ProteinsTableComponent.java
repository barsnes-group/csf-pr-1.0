package probe.com.view.components;

import com.vaadin.data.Item;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;

import com.vaadin.ui.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import probe.com.model.beans.ProteinBean;
import probe.com.view.core.CustomEmbedded;
import probe.com.view.core.CustomExternalLink;
import probe.com.view.core.ProteinInferenceLabel;

/**
 * This class represents main proteins table component
 *
 * @author Yehia Farag
 */
public class ProteinsTableComponent extends Table implements Serializable {

    private static final long serialVersionUID = 1L;
    private DecimalFormat df = null;
    private Map<String, Integer> tableSearchMap = new HashMap<String, Integer>();
    private Map<String, Integer> tableSearchMapIndex = new HashMap<String, Integer>();
    private int firstIndex;

    /**
     * Initialise protein table component
     *
     * @param proteinsList list of protein objects
     * @param fractionNumber total number of the fractions
     */
    @SuppressWarnings("UseSpecificCatch")
    public ProteinsTableComponent(Map<String, ProteinBean> proteinsList, int fractionNumber) {

        Map<String, Integer> rankMap = initRank(proteinsList);
        ProteinsTableComponent.this.setSelectable(true);
        ProteinsTableComponent.this.setColumnReorderingAllowed(true);
        ProteinsTableComponent.this.setColumnCollapsingAllowed(true);
        ProteinsTableComponent.this.setImmediate(true); // react at once when something is selected
        ProteinsTableComponent.this.setWidth(100,Unit.PERCENTAGE);
        ProteinsTableComponent.this.setHeight(160,Unit.PIXELS);

        ProteinsTableComponent.this.addContainerProperty("Index", Integer.class, null, "", null, Align.RIGHT);
        String Protein_Inference = "Protein Inference";
        ProteinsTableComponent.this.addContainerProperty(Protein_Inference, ProteinInferenceLabel.class, null, "PI", null, Align.CENTER);

        ProteinsTableComponent.this.addContainerProperty("Accession", CustomExternalLink.class, null);

        ProteinsTableComponent.this.addContainerProperty("Other Protein(s)", String.class, null);
        ProteinsTableComponent.this.setColumnCollapsed("Other Protein(s)", true);

        ProteinsTableComponent.this.addContainerProperty("Description", String.class, null);
        ProteinsTableComponent.this.addContainerProperty("Chr", String.class, null, "CHROMOSOME", null, Align.RIGHT);

        ProteinsTableComponent.this.addContainerProperty("Gene Name", String.class, null);

        ProteinsTableComponent.this.addContainerProperty("Sequence Coverage(%)", Double.class, null, "Coverage(%)", null, Align.RIGHT);
        ProteinsTableComponent.this.addContainerProperty("Non Enzymatic Peptides", CustomEmbedded.class, null, "Non Enzymatic Peptides", null, Align.CENTER);
        ProteinsTableComponent.this.addContainerProperty("# Validated Peptides", Integer.class, null, "#Peptides", null, Align.RIGHT);
        ProteinsTableComponent.this.addContainerProperty("# Validated Spectra", Integer.class, null, "#Spectra", null, Align.RIGHT);
        ProteinsTableComponent.this.addContainerProperty("NSAF", Double.class, null, "NSAF", null, Align.RIGHT);
        ProteinsTableComponent.this.addContainerProperty("RANK", Integer.class, null, "RANK (NSAF)", null, Align.RIGHT);
        ProteinsTableComponent.this.addContainerProperty("MW", Double.class, null, "MW", null, Align.RIGHT);
        String Confidence = "Confidence";
        ProteinsTableComponent.this.addContainerProperty(Confidence, Double.class, null, Confidence, null, Align.RIGHT);
        if (fractionNumber > 0) {
            ProteinsTableComponent.this.addContainerProperty("SpectrumFractionSpread lower range_kDa", Double.class, null, "Spectrum Lower Range", null, Align.RIGHT);
            ProteinsTableComponent.this.addContainerProperty("SpectrumFractionSpread upper range kDa", Double.class, null, "Spectrum Upper Range", null, Align.RIGHT);
            ProteinsTableComponent.this.addContainerProperty("PeptideFractionSpread lower range kDa", Double.class, null, "Peptide Lower Range", null, Align.RIGHT);
            ProteinsTableComponent.this.addContainerProperty("PeptideFractionSpread upper range kDa", Double.class, null, "Peptide Upper Range", null, Align.RIGHT);
            ProteinsTableComponent.this.setColumnCollapsed("SpectrumFractionSpread lower range_kDa", true);
            ProteinsTableComponent.this.setColumnCollapsed("SpectrumFractionSpread upper range kDa", true);
            ProteinsTableComponent.this.setColumnCollapsed("PeptideFractionSpread lower range kDa", true);
            ProteinsTableComponent.this.setColumnCollapsed("PeptideFractionSpread upper range kDa", true);

        }
        ProteinsTableComponent.this.addContainerProperty("Validated", CustomEmbedded.class, null, "Validated", null, Align.CENTER);
        int index = 1;
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        otherSymbols.setGroupingSeparator('.');
        df = new DecimalFormat("#.##", otherSymbols);
        CustomExternalLink link;
        CustomEmbedded nonEnz;
        CustomEmbedded validated;
        Resource res;
        Double d1;
        Double d2;
        Double d3 = null;
        Double d4 = null;
        ProteinInferenceLabel pi;
        Resource res2 = null;
        Resource res3;
        for (ProteinBean pb : proteinsList.values()) {

            link = new CustomExternalLink(pb.getAccession(), "http://www.uniprot.org/uniprot/" + pb.getAccession());
            link.setDescription("UniProt link for " + pb.getAccession());

            if (pb.isNonEnzymaticPeptides()) {
                res = new ThemeResource("img/true.jpg");
            } else {
                res = new ThemeResource("img/false.jpg");
            }

            if (pb.isValidated()) {
                res3 = new ThemeResource("img/true.jpg");
            } else {
                res3 = new ThemeResource("img/false.jpg");
            }

            nonEnz = new CustomEmbedded(pb.isNonEnzymaticPeptides(), res);
            nonEnz.setWidth(16,Unit.PIXELS);
            nonEnz.setHeight(16,Unit.PIXELS);
            nonEnz.setDescription("" + pb.isNonEnzymaticPeptides());

            validated = new CustomEmbedded(pb.isValidated(), res3);
            validated.setWidth(16,Unit.PIXELS);
            validated.setHeight(16,Unit.PIXELS);
            validated.setDescription("" + pb.isValidated());

            if (pb.getProteinInference().trim().equalsIgnoreCase("SINGLE PROTEIN")) {
                res2 = new ThemeResource("img/green.jpg");
            } else if (pb.getProteinInference().trim().equalsIgnoreCase("UNRELATED PROTEINS")) {
                res2 = new ThemeResource("img/red.jpg");
            } else if (pb.getProteinInference().equalsIgnoreCase("ISOFORMS")) {
                res2 = new ThemeResource("img/yellow.jpg");
            } else if (pb.getProteinInference().trim().equalsIgnoreCase("UNRELATED ISOFORMS") || pb.getProteinInference().equalsIgnoreCase("ISOFORMS AND UNRELATED PROTEIN(S)")) {
                res2 = new ThemeResource("img/orange.jpg");
            } else if (pb.getProteinInference().trim().equalsIgnoreCase("Related Proteins")) {
                res2 = new ThemeResource("img/yellow.jpg");
            } else if (pb.getProteinInference().equalsIgnoreCase("RELATED AND UNRELATED PROTEINS")) {
                res2 = new ThemeResource("img/red.jpg");
            }

            pi = new ProteinInferenceLabel(pb.getProteinInference(), res2);
            pi.setDescription(pb.getProteinInference());
            try {
                d1 = Double.valueOf(pb.getSpectrumFractionSpread_lower_range_kDa());
            } catch (Exception nfx) {
                d1 = null;
            }
            try {
                d2 = Double.valueOf(pb.getSpectrumFractionSpread_upper_range_kDa());
            } catch (Exception nfx) {
                d2 = null;
            }

            try {
                d3 = Double.valueOf(pb.getPeptideFractionSpread_lower_range_kDa());
            } catch (Exception nfx) {
            }
            try {
                d4 = Double.valueOf(pb.getPeptideFractionSpread_upper_range_kDa());
            } catch (Exception nfx) {
            }

            int rank = rankMap.get(pb.getAccession() + "," + pb.getOtherProteins());
            if (fractionNumber <= 0) {
                ProteinsTableComponent.this.addItem(new Object[]{index, pi, link, pb.getOtherProteins(), pb.getDescription(), pb.getChromosomeNumber(), pb.getGeneName(), Double.valueOf(df.format(pb.getSequenceCoverage())), nonEnz, pb.getNumberValidatedPeptides(), pb.getNumberValidatedSpectra(), Double.valueOf(df.format(pb.getNsaf())), rank, Double.valueOf(df.format(pb.getMw_kDa())), Double.valueOf(df.format(pb.getConfidence())), validated}, index);
            } else {
                ProteinsTableComponent.this.addItem(new Object[]{index, pi, link, pb.getOtherProteins(), pb.getDescription(), pb.getChromosomeNumber(), pb.getGeneName(), Double.valueOf(df.format(pb.getSequenceCoverage())), nonEnz, pb.getNumberValidatedPeptides(), pb.getNumberValidatedSpectra(), Double.valueOf(df.format(pb.getNsaf())), rank, Double.valueOf(df.format(pb.getMw_kDa())), Double.valueOf(df.format(pb.getConfidence())), d1, d2, d3, d4, validated}, index);
            }

            index++;

        }
        ProteinsTableComponent.this.sort(new String[]{Confidence, "# Validated Peptides"}, new boolean[]{false, false});

        ProteinsTableComponent.this.setSortAscending(false);

        ProteinsTableComponent.this.setColumnWidth("Index", 33);
        ProteinsTableComponent.this.setColumnWidth("Protein_Inference", 33);
        ProteinsTableComponent.this.setColumnWidth("Accession", 60);
        ProteinsTableComponent.this.setColumnWidth("Chr", 36);
        ProteinsTableComponent.this.setColumnWidth("Gene Name", 70);
        ProteinsTableComponent.this.setColumnWidth("Sequence Coverage(%)", 75);
        ProteinsTableComponent.this.setColumnWidth("Non Enzymatic Peptides", 80);
        ProteinsTableComponent.this.setColumnWidth("# Validated Peptides", 58);
        ProteinsTableComponent.this.setColumnWidth("# Validated Spectra", 55);
        ProteinsTableComponent.this.setColumnWidth("NSAF", 35);
        ProteinsTableComponent.this.setColumnWidth("RANK", 35);

        ProteinsTableComponent.this.setColumnWidth(Confidence, 65);
        ProteinsTableComponent.this.setColumnWidth("Validated", 55);

        for (Object propertyId : ProteinsTableComponent.this.getSortableContainerPropertyIds()) {
            if (propertyId.toString().equals("Description")) {
                ProteinsTableComponent.this.setColumnExpandRatio(propertyId, 1.0f);
            }

        }

        TreeMap<Integer, String> sortMap = new TreeMap<Integer, String>();
        int indexing = 1;
        for (Object id : ProteinsTableComponent.this.getItemIds()) {
            Item item = ProteinsTableComponent.this.getItem(id);
            item.getItemProperty("Index").setValue(indexing);
            if (indexing == 1) {
                firstIndex = (Integer) id;
            }
            sortMap.put(indexing, item.getItemProperty("Accession").getValue().toString().toUpperCase().trim() + "," + item.getItemProperty("Other Protein(s)").getValue().toString().toUpperCase().trim() + "," + item.getItemProperty("Description").getValue().toString().toUpperCase().trim() + "," + (Integer) id);
            indexing++;
        }
        tableSearchMap.clear();
        tableSearchMapIndex.clear();
        for (int key = 1; key <= sortMap.size(); key++) {
            String str = sortMap.get(key);
            int itemIndex = Integer.valueOf(str.split(",")[str.split(",").length - 1]);
            tableSearchMap.put(str, itemIndex);
            tableSearchMapIndex.put(str, key);
        }

    }

    /**
     * search proteins map for searching proteins by accession or description
     * (name)
     *
     * @return tableSearchMap
     */
    public Map<String, Integer> getTableSearchMap() {
        return tableSearchMap;
    }

    /**
     * Get map of keywords to protein row index
     *
     * @return map of keywords to protein row index
     */
    public Map<String, Integer> getTableSearchMapIndex() {
        return tableSearchMapIndex;
    }

    /**
     * Initialise the rank column for the selected dataset
     *
     * @param proteinsList proteins list
     * @return rankMap
     */
    private Map<String, Integer> initRank(Map<String, ProteinBean> proteinsList) {
        List<ProteinBean> protList = new ArrayList<ProteinBean>();
        Map<String, Integer> rankMap = new TreeMap<String, Integer>();
        protList.addAll(proteinsList.values());
        Collections.sort(protList);
        double currentNsaf = -1;
        int rank = 0;
        for (int index = (protList.size() - 1); index >= 0; index--) {
            ProteinBean pb = protList.get(index);
            if (currentNsaf != pb.getNsaf()) {
                rank++;
            }

            rankMap.put(pb.getAccession() + "," + pb.getOtherProteins(), rank);
            currentNsaf = pb.getNsaf();

        }
        return rankMap;

    }

    /**
     * Get the key of first row in the table
     *
     * @return key for first row in the table
     */
    public int getFirstIndex() {
        return firstIndex;
    }
}
