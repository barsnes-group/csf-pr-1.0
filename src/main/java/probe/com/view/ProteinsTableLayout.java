package probe.com.view;

import com.vaadin.data.Property;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import probe.com.model.beans.ProteinBean;
import probe.com.view.components.ProteinsTableComponent;
import probe.com.view.core.TableSizeController;

/**
 * This class represents protein table container
 *
 * @author Yehia Farag
 */
public class ProteinsTableLayout extends VerticalLayout implements Serializable {

    private String protTableHeight = "160px";
    private Label protCounterLabel;

    /**
     * get protein table height in string pixels
     *
     * @return protein table height
     */
    public String getProtTableHeight() {
        return protTableHeight;
    }

    /**
     * set protein table height in string pixels
     *
     * @param protSize protein table height
     */
    public void setProtTableHeight(String protSize) {
        this.protTableHeight = protSize;
    }
    private ProteinsTableComponent protTable, currentVisibleTable;
    private final VerticalLayout exportAllPepLayout = new VerticalLayout();
    private VerticalLayout protTableLayout = new VerticalLayout();
    private VerticalLayout protLabelLayout = new VerticalLayout();
    private PopupView datasetBtnProtAllPepTablePopup;
    private final HorizontalLayout topLayout = new HorizontalLayout();
    private final VerticalLayout exportProtLayout = new VerticalLayout();
    private PopupView datasetBtnProtPepTable;
    private Property.ValueChangeListener listener;
    private ProteinsTableComponent validProteinsTable;
    private Button nextSearchBtn;

    /**
     * get protein table search field
     *
     * @return table search field for protein table
     */
    public TextField getSearchField() {
        return searchField;
    }
    private TextField searchField;

    /**
     * Initialise protein table layout
     *
     * @param proteinsList list of proteins beans
     * @param fractionNumber number of fractions for the dataset
     * @param validProtNumber the number of valid proteins
     * @param datasetProteinsNumber number of all proteins in the dataset
     */
    public ProteinsTableLayout(final Map<String, ProteinBean> proteinsList, final int fractionNumber, final int validProtNumber, final int datasetProteinsNumber) {

        ProteinsTableLayout.this.setWidth(100, Unit.PERCENTAGE);
        ProteinsTableLayout.this.setSpacing(true);
        ProteinsTableLayout.this.setMargin(true);
        ProteinsTableLayout.this.setStyleName(Reindeer.LAYOUT_WHITE);
        ProteinsTableLayout.this.addComponent(topLayout);

        topLayout.setWidth(100, Unit.PERCENTAGE);
        topLayout.setMargin(false);
        topLayout.setSpacing(false);
        topLayout.addComponent(protLabelLayout);
        topLayout.setComponentAlignment(protLabelLayout, Alignment.TOP_LEFT);

        topLayout.setExpandRatio(protLabelLayout, 0.15f);

        Label protTableHeaderLabel = new Label("<h4 style='font-family:verdana;color:black;font-weight:bold;'>Protein Groups (" + validProtNumber + ")</h4>");
        protTableHeaderLabel.setContentMode(ContentMode.HTML);
        protTableHeaderLabel.setHeight(40,Unit.PIXELS);
        protLabelLayout.addComponent(protTableHeaderLabel);

        //allow search in 
        HorizontalLayout searchContainer = new HorizontalLayout();
        searchContainer.setSizeUndefined();
        searchContainer.setStyleName("searchcontainerlayout");
        topLayout.addComponent(searchContainer);
        topLayout.setComponentAlignment(searchContainer, Alignment.TOP_RIGHT);

        searchField = new TextField(" ");
        searchField.setDescription("Search Proteins By Name or Accession");
        searchField.setImmediate(true);
        searchField.setWidth(150,Unit.PIXELS);
        searchField.setHeight(23,Unit.PIXELS);

        searchContainer.addComponent(searchField);
        nextSearchBtn = new Button();
        nextSearchBtn.setDescription("Next Result");
        nextSearchBtn.setStyleName(Reindeer.BUTTON_LINK);
        nextSearchBtn.setIcon(new ThemeResource("img/next.gif"));
        nextSearchBtn.addStyleName("nextsearchbtn");
        nextSearchBtn.setWidth(20,Unit.PIXELS);
        nextSearchBtn.setHeight(20,Unit.PIXELS);
        nextSearchBtn.setEnabled(false);

        searchContainer.addComponent(nextSearchBtn);

        protCounterLabel = new Label("");
        protCounterLabel.setWidth(150,Unit.PIXELS);
        protCounterLabel.setHeight(23,Unit.PIXELS);
        searchContainer.addComponent(protCounterLabel);

        ProteinsTableLayout.this.addComponent(protTableLayout);
        ProteinsTableLayout.this.setComponentAlignment(protTableLayout, Alignment.MIDDLE_CENTER);
        Map<String, ProteinBean> vProteinsList = getValidatedList(proteinsList);

        validProteinsTable = new ProteinsTableComponent(vProteinsList, fractionNumber);
        protTableLayout.addComponent(validProteinsTable);
        currentVisibleTable = validProteinsTable;

        HorizontalLayout underTableLayout = new HorizontalLayout();
        underTableLayout.setWidth(100, Unit.PERCENTAGE);
        underTableLayout.setHeight(25,Unit.PIXELS);
        ProteinsTableLayout.this.addComponent(underTableLayout);
        ProteinsTableLayout.this.setComponentAlignment(underTableLayout, Alignment.TOP_CENTER);

        HorizontalLayout lowerLeftLayout = new HorizontalLayout();
        lowerLeftLayout.setSpacing(true);
        underTableLayout.addComponent(lowerLeftLayout);
        lowerLeftLayout.setMargin(new MarginInfo(false, false, false, false));
        underTableLayout.setComponentAlignment(lowerLeftLayout, Alignment.MIDDLE_LEFT);

        HorizontalLayout lowerRightLayout = new HorizontalLayout();
        lowerRightLayout.setSpacing(true);
        lowerRightLayout.setWidth(550,Unit.PIXELS);
        underTableLayout.addComponent(lowerRightLayout);
        underTableLayout.setComponentAlignment(lowerRightLayout, Alignment.BOTTOM_RIGHT);
        underTableLayout.setExpandRatio(lowerRightLayout, 0.7f);

        final OptionGroup selectionType = new OptionGroup();
        selectionType.setMultiSelect(true);
        selectionType.addItem("\t\tShow Validated Proteins Only");
        selectionType.select("\t\tShow Validated Proteins Only");

        selectionType.setHeight(15,Unit.PIXELS);
        lowerLeftLayout.addComponent(selectionType);
        lowerLeftLayout.setComponentAlignment(selectionType, Alignment.BOTTOM_LEFT);

        final TableSizeController trs1 = new TableSizeController(validProteinsTable, protTableHeight);//resize tables 
        lowerLeftLayout.addComponent(trs1);
        lowerLeftLayout.setComponentAlignment(trs1, Alignment.BOTTOM_LEFT);

        exportAllPepLayout.setWidth(250,Unit.PIXELS);
        lowerRightLayout.addComponent(exportAllPepLayout);
        lowerRightLayout.setComponentAlignment(exportAllPepLayout, Alignment.BOTTOM_RIGHT);

        exportProtLayout.setWidth(230,Unit.PIXELS);
        lowerRightLayout.addComponent(exportProtLayout);
        lowerRightLayout.setComponentAlignment(exportProtLayout, Alignment.BOTTOM_RIGHT);

        selectionType.setImmediate(true);
        selectionType.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                searchField.setValue("");
                nextSearchBtn.setEnabled(false);
                protCounterLabel.setValue("");

                if (selectionType.isSelected("\t\tShow Validated Proteins Only")) {
                    protLabelLayout.removeAllComponents();
                    Label protLabel = new Label("<h4 style='font-family:verdana;color:black;font-weight:bold;'>Protein Groups (" + datasetProteinsNumber + ")</h4>");
                    protLabel.setContentMode(ContentMode.HTML);
                    protLabel.setHeight(40,Unit.PIXELS);
                    protLabelLayout.addComponent(protLabel);
                    protTableLayout.removeAllComponents();
                    protTableLayout.addComponent(validProteinsTable);
                    trs1.setTable(validProteinsTable);
                    validProteinsTable.setHeight(protTable.getHeight() + "");
                    protTable.removeValueChangeListener(listener);
                    validProteinsTable.addValueChangeListener(listener);
                    currentVisibleTable = validProteinsTable;

                } else {
                    protLabelLayout.removeAllComponents();
                    Label protLabel = new Label("<h4 style='font-family:verdana;color:black;font-weight:bold;'>Protein Groups (" + validProtNumber + "/" + datasetProteinsNumber + ")</h4>");
                    protLabel.setContentMode(ContentMode.HTML);
                    protLabel.setHeight(40,Unit.PIXELS);
                    protTable = new ProteinsTableComponent(proteinsList, fractionNumber);
                    protLabelLayout.addComponent(protLabel);
                    protTableLayout.removeAllComponents();
                    protTableLayout.addComponent(protTable);
                    trs1.setTable(protTable);
                    currentVisibleTable = protTable;
                    validProteinsTable.removeValueChangeListener(listener);
                    protTable.addValueChangeListener(listener);
                }
            }
        });

    }

    /**
     * get current visible protein table
     *
     * @return currentVisibleTable
     *
     */
    public ProteinsTableComponent getProteinTableComponent() {
        return currentVisibleTable;
    }

    /**
     * Get the pop-up view for export all peptides for the selected proteins
     *
     * @return datasetBtnProtAllPepTablePopup
     */
    public PopupView getDatasetBtnProtAllPepTable() {
        return datasetBtnProtAllPepTablePopup;
    }

    /**
     * set the popup view for export all peptides for the selected proteins
     *
     * @param datasetBtnProtAllPepTablePopup
     * @param datasetBtnProtPepTablePopup
     */
    public void setExpBtnProtAllPepTable(PopupView datasetBtnProtAllPepTablePopup, PopupView datasetBtnProtPepTablePopup) {
        ProteinsTableLayout.this.datasetBtnProtAllPepTablePopup = datasetBtnProtAllPepTablePopup;
        ProteinsTableLayout.this.datasetBtnProtPepTable = datasetBtnProtPepTablePopup;
        updateExportLayouts();

    }

    /**
     * update the layout for exporting buttons and reset the fields.
     */
    private void updateExportLayouts() {
        exportAllPepLayout.removeAllComponents();
        exportAllPepLayout.addComponent(datasetBtnProtAllPepTablePopup);
        exportAllPepLayout.setComponentAlignment(datasetBtnProtAllPepTablePopup, Alignment.MIDDLE_LEFT);
        exportProtLayout.removeAllComponents();
        exportProtLayout.addComponent(datasetBtnProtPepTable);
        exportProtLayout.setComponentAlignment(datasetBtnProtPepTable, Alignment.MIDDLE_LEFT);
    }

    /**
     * add value change listener for the proteins table
     *
     * @param listener value change listener
     */
    public void setListener(Property.ValueChangeListener listener) {
        this.listener = listener;
    }

    /**
     * get nextSearchBtn button
     *
     * @return nextSearchBtn button
     */
    public Button getNextSearch() {
        return nextSearchBtn;
    }

    /**
     * get label contains the found proteins numbers
     *
     * @return protCounterLabel
     */
    public Label getProtCounter() {
        return protCounterLabel;
    }

    /**
     * get valid proteins list (filter the proteins list)
     *
     * @param proteinsList
     * @return validProteinsList
     */
    private Map<String, ProteinBean> getValidatedList(Map<String, ProteinBean> proteinsList) {
        Map<String, ProteinBean> validProteinsList = new HashMap<String, ProteinBean>();
        for (String str : proteinsList.keySet()) {
            ProteinBean pb = proteinsList.get(str);
            if (pb.isValidated()) {
                validProteinsList.put(str, pb);
            }
        }
        return validProteinsList;
    }
}
