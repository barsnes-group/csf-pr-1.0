/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package probe.com.view.components;

import com.vaadin.data.Property;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import java.io.Serializable;
import java.util.Map;
import probe.com.handlers.MainHandler;
import probe.com.model.beans.DatasetBean;
import probe.com.model.beans.ProteinBean;
import probe.com.view.core.CustomExportBtnLayout;
import probe.com.view.core.TableSizeController;

/**
 * This class work as container for search results table
 *
 * @author Yehia Farag
 */
public class SearchResultsTableLayout extends VerticalLayout implements Serializable {

    private SearchResultsTable searcheResultsTable, activeTable, validatedProteinsTable;
    private VerticalLayout searchResultsTableLayout = new VerticalLayout();
    private final VerticalLayout exportAllPepLayout = new VerticalLayout();
    private PopupView exportAllPeptidesTableBtn;
    private final VerticalLayout exportSearchTableLayout = new VerticalLayout();
    private final PopupView expBtnSearchResultsTable;
    private Property.ValueChangeListener listener;
    private Label searchResultstLabel = new Label();

    /**
     * Initialise search results container
     *
     * @param handler data handler
     * @param datasetDetailsList list of dataset objects
     * @param foundProteinsList found protein list
     * @param validatedOnly show validated results only
     */
    public SearchResultsTableLayout(MainHandler handler, final Map<Integer, DatasetBean> datasetDetailsList, final Map<Integer, ProteinBean> foundProteinsList, boolean validatedOnly) {

        SearchResultsTableLayout.this.setWidth(100,Unit.PERCENTAGE);
        SearchResultsTableLayout.this.setSpacing(true);
        SearchResultsTableLayout.this.setStyleName(Reindeer.LAYOUT_WHITE);
        searchResultstLabel.setContentMode(ContentMode.HTML);
        searchResultstLabel.setHeight(30,Unit.PIXELS);
        SearchResultsTableLayout.this.addComponent(searchResultstLabel);
        SearchResultsTableLayout.this.addComponent(searchResultsTableLayout);
        SearchResultsTableLayout.this.setComponentAlignment(searchResultsTableLayout, Alignment.MIDDLE_CENTER);

        final Map<Integer, ProteinBean> vProteinsList = handler.getValidatedProteinsList(foundProteinsList);
        searchResultstLabel.setValue("<h4 style='font-family:verdana;color:black;'> Search Results (" + vProteinsList.size() + ")</h4>");
        searcheResultsTable = new SearchResultsTable(datasetDetailsList, vProteinsList);
        searchResultsTableLayout.addComponent(searcheResultsTable);
        activeTable = searcheResultsTable;

        HorizontalLayout lowerLayout = new HorizontalLayout();
        lowerLayout.setWidth(100,Unit.PERCENTAGE);
        lowerLayout.setHeight(25,Unit.PIXELS);
        SearchResultsTableLayout.this.addComponent(lowerLayout);
        SearchResultsTableLayout.this.setComponentAlignment(lowerLayout, Alignment.TOP_CENTER);

        HorizontalLayout lowerLeftLayout = new HorizontalLayout();
        lowerLeftLayout.setSpacing(true);
        lowerLayout.addComponent(lowerLeftLayout);
        lowerLeftLayout.setMargin(new MarginInfo(false, false, false, false));
        lowerLayout.setComponentAlignment(lowerLeftLayout, Alignment.MIDDLE_LEFT);

        HorizontalLayout lowerRightLayout = new HorizontalLayout();
        lowerRightLayout.setSpacing(true);
        lowerLayout.addComponent(lowerRightLayout);
        lowerLayout.setComponentAlignment(lowerRightLayout, Alignment.BOTTOM_RIGHT);
        final OptionGroup selectionType = new OptionGroup();
        selectionType.setMultiSelect(true);
        Object itemId = selectionType.addItem("\t\tShow Validated Proteins Only");
        selectionType.select("\t\tShow Validated Proteins Only");
        selectionType.setReadOnly(validatedOnly);

        selectionType.setHeight(15,Unit.PIXELS);
        lowerLeftLayout.addComponent(selectionType);
        lowerLeftLayout.setComponentAlignment(selectionType, Alignment.BOTTOM_LEFT);

        final TableSizeController trs1 = new TableSizeController(activeTable, activeTable.getHeight() + "");//resize tables 
        lowerLeftLayout.addComponent(trs1);
        lowerLeftLayout.setComponentAlignment(trs1, Alignment.BOTTOM_CENTER);

        exportAllPepLayout.setWidth(300,Unit.PIXELS);
        lowerRightLayout.addComponent(exportAllPepLayout);
        lowerRightLayout.setComponentAlignment(exportAllPepLayout, Alignment.BOTTOM_RIGHT);
        exportAllPepLayout.setVisible(true);

        exportSearchTableLayout.setWidth(200,Unit.PIXELS);
        lowerRightLayout.addComponent(exportSearchTableLayout);
        lowerRightLayout.setComponentAlignment(exportSearchTableLayout, Alignment.BOTTOM_RIGHT);

        CustomExportBtnLayout ce2 = new CustomExportBtnLayout(handler, "searchResult", 0, null, null, null, null, 0, null, null, foundProteinsList, null);
        expBtnSearchResultsTable = new PopupView("Export CSF-PR Search Results", ce2);
        exportSearchTableLayout.removeAllComponents();
        expBtnSearchResultsTable.setHideOnMouseOut(false);
        exportSearchTableLayout.addComponent(expBtnSearchResultsTable);
        expBtnSearchResultsTable.setDescription("Export CSF-PR Search Results");
        exportSearchTableLayout.setComponentAlignment(expBtnSearchResultsTable, Alignment.MIDDLE_LEFT);

        selectionType.setImmediate(true);
        selectionType.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (!selectionType.isSelected("\t\tShow Validated Proteins Only")) {
                    searchResultstLabel.setValue("<h4 style='font-family:verdana;color:black;'> Search Results (" + vProteinsList.size() + "/" + foundProteinsList.size() + ")</h4>");
                    searchResultsTableLayout.removeAllComponents();
                    validatedProteinsTable = new SearchResultsTable(datasetDetailsList, foundProteinsList);
                    searchResultsTableLayout.addComponent(validatedProteinsTable);
                    trs1.setTable(validatedProteinsTable);
                    validatedProteinsTable.setHeight(getSearchTable().getHeight() + "");
                    getSearchTable().removeValueChangeListener(getListener());
                    validatedProteinsTable.addValueChangeListener(getListener());
                    activeTable = validatedProteinsTable;
                } else {
                    searchResultstLabel.setValue("<h4 style='font-family:verdana;color:black;'> Search Results (" + vProteinsList.size() + ")</h4>");

                    searchResultsTableLayout.removeAllComponents();
                    searchResultsTableLayout.addComponent(searcheResultsTable);
                    trs1.setTable(searcheResultsTable);
                    activeTable = searcheResultsTable;
                    validatedProteinsTable.removeValueChangeListener(getListener());
                    searcheResultsTable.addValueChangeListener(getListener());
                }
            }

        });
        if (validatedOnly) {
            selectionType.select(itemId);
            selectionType.setVisible(true);
            selectionType.setReadOnly(true);

        } else {
            selectionType.setVisible(true);
            selectionType.setReadOnly(false);
        }

    }

    /**
     * Get attached value change listener
     *
     * @return value change listener
     */
    public Property.ValueChangeListener getListener() {
        return listener;
    }

    /**
     * Set table value change listener
     *
     * @param listener value change listener
     */
    public void setListener(Property.ValueChangeListener listener) {
        SearchResultsTableLayout.this.listener = listener;
    }

    /**
     * Get table export buttons for exporting all peptides
     *
     * @return export all peptides button layout
     */
    public PopupView getExportAllPeptidesTableBtn() {
        return exportAllPeptidesTableBtn;
    }

    /**
     * Set table export buttons for exporting all peptides
     *
     * @param exportAllPeptidesTableBtn export all peptides button layout
     */
    public void setExportAllPeptidesTableBtn(PopupView exportAllPeptidesTableBtn) {
        SearchResultsTableLayout.this.exportAllPeptidesTableBtn = exportAllPeptidesTableBtn;
        updateExportLayouts();

    }

    private void updateExportLayouts() {
        exportAllPepLayout.removeAllComponents();
        exportAllPeptidesTableBtn.setHideOnMouseOut(false);
        exportAllPepLayout.addComponent(exportAllPeptidesTableBtn);
        exportAllPepLayout.setComponentAlignment(exportAllPeptidesTableBtn, Alignment.MIDDLE_LEFT);
    }

    /**
     * Get active search results table
     *
     * @return search results table
     */
    public SearchResultsTable getSearchTable() {
        return activeTable;
    }

}
