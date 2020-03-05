
package probe.com.view.components;

import com.vaadin.data.Item;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Table;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;
import probe.com.model.beans.DatasetBean;
import probe.com.model.beans.ProteinBean;
import probe.com.view.core.CustomExternalLink;
import probe.com.view.core.ProteinInferenceLabel;

/**
 *This class represents search results table
 * @author Yehia Farag
 */
public class SearchResultsTable extends Table implements Serializable {

    private final DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
    private DecimalFormat df = null;

    /**
     *Initialise search results table to view searching results
     * @param datasetDetailsList list of found datasets
     * @param foundProteinList list of found proteins object
     */
    public SearchResultsTable(Map<Integer, DatasetBean> datasetDetailsList, Map<Integer, ProteinBean> foundProteinList) {

        SearchResultsTable.this.setSelectable(true);
        SearchResultsTable.this.setColumnReorderingAllowed(true);
        SearchResultsTable.this.setColumnCollapsingAllowed(true);
        SearchResultsTable.this.setImmediate(true); // react at once when something is selected
        SearchResultsTable.this.setWidth(100,Unit.PERCENTAGE);
        SearchResultsTable.this.setHeight(150,Unit.PIXELS);

        SearchResultsTable.this.addContainerProperty("Index", Integer.class, null, "", null, Align.RIGHT);
        SearchResultsTable.this.addContainerProperty("Experiment", String.class, null);
        SearchResultsTable.this.addContainerProperty("Accession", CustomExternalLink.class, null);

        SearchResultsTable.this.addContainerProperty("Species", String.class, null);
        SearchResultsTable.this.addContainerProperty("Sample Type", String.class, null);

        SearchResultsTable.this.addContainerProperty("Sample Processing", String.class, null);
        SearchResultsTable.this.addContainerProperty("Instrument Type", String.class, null);
        SearchResultsTable.this.addContainerProperty("Frag. Mode", String.class, null);

        String Protein_Inference = "Protein Inference";
        SearchResultsTable.this.addContainerProperty(Protein_Inference, ProteinInferenceLabel.class, null, "PI", null, Align.CENTER);

        SearchResultsTable.this.addContainerProperty("Other Protein(s)", String.class, null);
        SearchResultsTable.this.addContainerProperty("Description", String.class, null);
        SearchResultsTable.this.addContainerProperty("Sequence Coverage(%)", Double.class, null, "Coverage(%)", null, Align.RIGHT);
        SearchResultsTable.this.addContainerProperty("# Validated Peptides", Integer.class, null, "#Peptides", null, Align.RIGHT);
        SearchResultsTable.this.addContainerProperty("# Validated Spectra", Integer.class, null, "#Spectra", null, Align.RIGHT);
        SearchResultsTable.this.addContainerProperty("NSAF", Double.class, null, "NSAF", null, Align.RIGHT);
        SearchResultsTable.this.addContainerProperty("MW", Double.class, null, "MW", null, Align.RIGHT);
        String Confidence = "Confidence";
        SearchResultsTable.this.addContainerProperty(Confidence, Double.class, null, Confidence, null, Align.RIGHT);

        CustomExternalLink link;
        String dataset;
        ProteinInferenceLabel pi;
        ThemeResource res2 = null;

        int index = 1;
        df = new DecimalFormat("#.##", otherSymbols);

        for (int key : foundProteinList.keySet()) {
            ProteinBean pb = foundProteinList.get(key);
            DatasetBean datasetDetails = datasetDetailsList.get(pb.getDatasetId());

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
            link = new CustomExternalLink(pb.getAccession(), "http://www.uniprot.org/uniprot/" + pb.getAccession());
            link.setDescription("UniProt link for " + pb.getAccession());

            dataset = datasetDetails.getName();
            SearchResultsTable.this.addItem(new Object[]{index, dataset, link, datasetDetails.getSpecies(), datasetDetails.getSampleType(), datasetDetails.getSampleProcessing(), datasetDetails.getInstrumentType(), datasetDetails.getFragMode(), pi, pb.getOtherProteins(), pb.getDescription(), Double.valueOf(df.format(pb.getSequenceCoverage())), pb.getNumberValidatedPeptides(), pb.getNumberValidatedSpectra(), Double.valueOf(df.format(pb.getNsaf())), Double.valueOf(df.format(pb.getMw_kDa())), Double.valueOf(df.format(pb.getConfidence()))}, index);
            index++;
        }

        for (Object propertyId : SearchResultsTable.this.getSortableContainerPropertyIds()) {
            if (propertyId.toString().equals("Description")) {
                SearchResultsTable.this.setColumnExpandRatio(propertyId, 4.0f);
            } else {
                SearchResultsTable.this.setColumnExpandRatio(propertyId.toString(), 0.5f);
            }

        }
        SearchResultsTable.this.sort(new String[]{Confidence, "# Validated Peptides"}, new boolean[]{false, false});

        SearchResultsTable.this.setColumnWidth("Experiment", 115);
        SearchResultsTable.this.setColumnWidth("Index", 33);
        SearchResultsTable.this.setColumnWidth("Accession", 60);
        SearchResultsTable.this.setColumnWidth("Sequence Coverage(%)", 75);

        SearchResultsTable.this.setColumnWidth("Species", 45);
        SearchResultsTable.this.setColumnWidth("Sample Type", 73);
        SearchResultsTable.this.setColumnWidth("Sample Processing", 90);
        SearchResultsTable.this.setColumnWidth("Frag. Mode", 65);

        SearchResultsTable.this.setColumnWidth("Instrument Type", 80);

        SearchResultsTable.this.setColumnWidth(Protein_Inference, 33);
        SearchResultsTable.this.setColumnWidth("# Validated Peptides", 58);
        SearchResultsTable.this.setColumnWidth("# Validated Spectra", 55);
        SearchResultsTable.this.setColumnWidth("NSAF", 35);
        SearchResultsTable.this.setColumnWidth(Confidence, 65);
        SearchResultsTable.this.setColumnWidth("MW", 55);
        for (Object propertyId :  SearchResultsTable.this.getSortableContainerPropertyIds()) {
            if (propertyId.toString().equals("Other Protein(s)")) {
                 SearchResultsTable.this.setColumnExpandRatio(propertyId, 1.0f);
            }
        }

        int indexing = 1;
        for (Object id :  SearchResultsTable.this.getItemIds()) {
            Item item =  SearchResultsTable.this.getItem(id);
            item.getItemProperty("Index").setValue(indexing);
            indexing++;

        }
    }
}
