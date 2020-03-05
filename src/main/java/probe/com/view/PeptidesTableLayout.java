package probe.com.view;

import com.vaadin.data.Property;
import com.vaadin.event.LayoutEvents;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import probe.com.model.beans.PeptideBean;
import probe.com.view.components.PeptideTable;
import probe.com.view.core.ExpandCollapseIconLabel;
import probe.com.view.core.TableSizeController;

/**
 * This class represents peptide table layout
 *
 * @author Yehia Farag
 */
public class PeptidesTableLayout extends VerticalLayout implements Serializable {

    private String peptideTableHeight = "160px";
    private TableSizeController trs;
    private PeptideTable peptideTable;
    private VerticalLayout mainLayout;
    private ExpandCollapseIconLabel show;
    private boolean stat;
    private VerticalLayout pepTableLayout = new VerticalLayout();
    private final VerticalLayout exportPepLayout = new VerticalLayout();
    private PopupView expBtnPepPepTable;
    private PeptideTable validPeptideTable, activeTable;

    /**
     * Get active peptides table
     *
     * @return current active peptide table
     */
    public PeptideTable getPepTable() {
        return activeTable;
    }

    /**
     * Initialise peptide table
     *
     * @param validPep number of validated peptides
     * @param totalPep total number of peptides
     * @param desc the protein description (protein name)
     * @param pepProtList list of peptides related to the selected protein
     * @param accession protein accession
     * @param datasetName the selected dataset name
     */
    public PeptidesTableLayout(final int validPep, final int totalPep, final String desc, final Map<Integer, PeptideBean> pepProtList, final String accession, final String datasetName) {
        //for  peptides information (table) view
        MarginInfo m = new MarginInfo(false, false, true, false);
        PeptidesTableLayout.this.setMargin(m);
        PeptidesTableLayout.this.setSpacing(false);
        PeptidesTableLayout.this.setWidth(100,Unit.PERCENTAGE);

        final HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setHeight(45, Unit.PIXELS);
        headerLayout.setSpacing(true);
        show = new ExpandCollapseIconLabel(true);
        headerLayout.addComponent(show);
        headerLayout.setComponentAlignment(show, Alignment.BOTTOM_LEFT);
        stat = true;

        final Label pepLabel = new Label("<h4 style='font-family:verdana;color:black;'>Peptides (" + validPep + ") " + desc + "</h4>");

        pepLabel.setContentMode(ContentMode.HTML);
        pepLabel.setHeight(45, Unit.PIXELS);
        headerLayout.addComponent(pepLabel);
        headerLayout.setComponentAlignment(pepLabel, Alignment.TOP_RIGHT);

        PeptidesTableLayout.this.addComponent(headerLayout);
        mainLayout = new VerticalLayout();
        mainLayout.setWidth(100, Unit.PERCENTAGE);
        PeptidesTableLayout.this.addComponent(mainLayout);
        mainLayout.addComponent(pepTableLayout);
        mainLayout.setComponentAlignment(pepTableLayout, Alignment.MIDDLE_CENTER);

        Map<Integer, PeptideBean> vPepProtList = getValidatedList(pepProtList);

        validPeptideTable = new PeptideTable(vPepProtList, null, false, desc);
        pepTableLayout.addComponent(validPeptideTable);
        if (trs != null) {
            peptideTableHeight = trs.getCurrentSize();
        }
        validPeptideTable.setHeight(peptideTableHeight);
        activeTable = validPeptideTable;
        HorizontalLayout lowerLayout = new HorizontalLayout();
        lowerLayout.setWidth(100, Unit.PERCENTAGE);
        lowerLayout.setHeight(25, Unit.PIXELS);
        lowerLayout.setSpacing(false);
        mainLayout.addComponent(lowerLayout);
        mainLayout.setComponentAlignment(lowerLayout, Alignment.TOP_CENTER);

        HorizontalLayout lowerLeftLayout = new HorizontalLayout();
        lowerLayout.addComponent(lowerLeftLayout);
        lowerLeftLayout.setSpacing(true);
        lowerLeftLayout.setMargin(new MarginInfo(false, false, false, false));
        lowerLayout.setComponentAlignment(lowerLeftLayout, Alignment.MIDDLE_LEFT);

        HorizontalLayout lowerRightLayout = new HorizontalLayout();

        lowerRightLayout.setWidth(450, Unit.PIXELS);
        lowerLayout.addComponent(lowerRightLayout);
        lowerLayout.setComponentAlignment(lowerRightLayout, Alignment.BOTTOM_RIGHT);

        final OptionGroup selectionType = new OptionGroup();
        selectionType.setMultiSelect(true);
        selectionType.addItem("\t\tShow Validated Peptides Only");
        selectionType.select("\t\tShow Validated Peptides Only");
        selectionType.setHeight(15, Unit.PIXELS);
        lowerLeftLayout.addComponent(selectionType);
        lowerLeftLayout.setComponentAlignment(selectionType, Alignment.BOTTOM_LEFT);

        final TableSizeController trs1 = new TableSizeController(validPeptideTable, peptideTableHeight);//resize tables 
        lowerLeftLayout.addComponent(trs1);
        lowerLeftLayout.setComponentAlignment(trs1, Alignment.BOTTOM_LEFT);

        exportPepLayout.setWidth(300, Unit.PIXELS);
        lowerRightLayout.addComponent(exportPepLayout);
        lowerRightLayout.setComponentAlignment(exportPepLayout, Alignment.BOTTOM_RIGHT);

        mainLayout.setSpacing(true);

        headerLayout.addLayoutClickListener(new com.vaadin.event.LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {

                if (stat) {
                    stat = false;
                    show.setExpandLabel(false);
                    mainLayout.setVisible(false);
                } else {
                    stat = true;
                    show.setExpandLabel(true);
                    mainLayout.setVisible(true);
                }
            }
        });
        selectionType.setImmediate(true);
        selectionType.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (selectionType.isSelected("\t\tShow Validated Peptides Only")) {

                    headerLayout.removeAllComponents();
                    headerLayout.addComponent(show);
                    headerLayout.setComponentAlignment(show, Alignment.BOTTOM_LEFT);

                    pepLabel.setContentMode(ContentMode.HTML);
                    pepLabel.setHeight(45, Unit.PIXELS);
                    headerLayout.addComponent(pepLabel);
                    headerLayout.setComponentAlignment(pepLabel, Alignment.TOP_RIGHT);

                    pepTableLayout.removeAllComponents();
                    pepTableLayout.addComponent(validPeptideTable);
                    trs1.setTable(validPeptideTable);
                    validPeptideTable.setHeight(peptideTable.getHeight() + "");

                } else {
                    headerLayout.removeAllComponents();
                    headerLayout.addComponent(show);
                    headerLayout.setComponentAlignment(show, Alignment.BOTTOM_LEFT);
                    Label pepLabel = new Label("<h4 style='font-family:verdana;color:black;'>Peptides (" + validPep + "/" + totalPep + ") " + desc + "</h4>");
                    pepLabel.setContentMode(ContentMode.HTML);
                    headerLayout.addComponent(pepLabel);
                    headerLayout.setComponentAlignment(pepLabel, Alignment.TOP_RIGHT);

                    pepTableLayout.removeAllComponents();
                    peptideTable = new PeptideTable(pepProtList, null, false, desc);
                    pepTableLayout.addComponent(peptideTable);
                    trs1.setTable(peptideTable);
                    peptideTable.setHeight(validPeptideTable.getHeight() + "");
                    activeTable = peptideTable;

                }
            }

        });

    }

    private Map<Integer, PeptideBean> getValidatedList(Map<Integer, PeptideBean> pepProtList) {
        Map<Integer, PeptideBean> vPepList = new HashMap<Integer, PeptideBean>();
        for (int key : pepProtList.keySet()) {
            PeptideBean pb = pepProtList.get(key);
            if (pb.getValidated() == 1) {
                vPepList.put(key, pb);
            }

        }
        return vPepList;

    }

    /**
     * Get current peptides table height
     *
     * @param peptidetableHeight peptide table height
     */
    public void setPeptideTableHeight(String peptidetableHeight) {
        PeptidesTableLayout.this.peptideTableHeight = peptidetableHeight;
    }

    /**
     * Add export button for peptide table layout
     *
     * @param expBtnPepPepTable export button layout
     */
    public void setExportBtnForPeptideTable(PopupView expBtnPepPepTable) {
        PeptidesTableLayout.this.expBtnPepPepTable = expBtnPepPepTable;
        updateExportLayouts();

    }

    private void updateExportLayouts() {
        exportPepLayout.removeAllComponents();
        expBtnPepPepTable.setHideOnMouseOut(false);
        exportPepLayout.addComponent(expBtnPepPepTable);
        exportPepLayout.setComponentAlignment(expBtnPepPepTable, Alignment.MIDDLE_LEFT);

    }
}
