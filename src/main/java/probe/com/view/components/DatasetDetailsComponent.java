package probe.com.view.components;

import java.io.Serializable;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.Runo;
import probe.com.handlers.MainHandler;
import probe.com.view.core.CustomExportBtnLayout;
import probe.com.view.core.ClickableIconGenerator;
import probe.com.view.core.ExpandCollapseIconLabel;

/**
 * This class represents the dataset details layout
 *
 * @author Yehia Farag
 */
@SuppressWarnings("serial")
public class DatasetDetailsComponent extends VerticalLayout implements Serializable, com.vaadin.event.LayoutEvents.LayoutClickListener {

    private final VerticalLayout mainComponentBodyLayout;
    private final Label datasetLabel;
    private final HorizontalLayout titleLayout;
    private final ClickableIconGenerator excelExporterIconGenerator = new ClickableIconGenerator();
    private final ExpandCollapseIconLabel show;

    /**
     * Initialise dataset overview layout
     *
     * @param expand expand or collapse the dataset overview
     * @param handler
     */
    public DatasetDetailsComponent(boolean expand, MainHandler handler) {

        DatasetDetailsComponent.this.setMargin(new MarginInfo(false, true, false, true));
        DatasetDetailsComponent.this.setWidth(100,Unit.PERCENTAGE);

        titleLayout = new HorizontalLayout();
        titleLayout.setHeight(45,Unit.PIXELS);
        titleLayout.setSpacing(true);
        show = new ExpandCollapseIconLabel();
        titleLayout.addComponent(show);
        titleLayout.setComponentAlignment(show, Alignment.BOTTOM_LEFT);

        datasetLabel = new Label("<h4  style='font-family:verdana;font-weight:bold;'><strong aligen='center' style='font-family:verdana;color:#00000;'>Dataset  Information </strong></h4>");
        datasetLabel.setContentMode(ContentMode.HTML);
        datasetLabel.setHeight(45,Unit.PIXELS);

        titleLayout.addComponent(datasetLabel);
        titleLayout.setComponentAlignment(datasetLabel, Alignment.TOP_RIGHT);
        titleLayout.addLayoutClickListener(DatasetDetailsComponent.this);
        DatasetDetailsComponent.this.addComponent(titleLayout);
        this.mainComponentBodyLayout = FormWithComplexLayout(handler);

        DatasetDetailsComponent.this.addComponent(mainComponentBodyLayout);
        if (expand) {
            this.expand();
        } else {
            this.collapse();
        }
    }

    @SuppressWarnings("deprecation")
    private VerticalLayout FormWithComplexLayout(MainHandler handler) {
        VerticalLayout vlo1 = new VerticalLayout();
        vlo1.setSpacing(true);
        vlo1.setMargin(false);
        vlo1.setSizeFull();
        HorizontalLayout hlo1 = new HorizontalLayout();
        VerticalLayout topSpacer = new VerticalLayout();
        topSpacer.setHeight(2,Unit.PIXELS);
        topSpacer.setMargin(false);
        topSpacer.setStyleName(Reindeer.LAYOUT_BLACK);
        vlo1.addComponent(topSpacer);
        vlo1.addComponent(hlo1);
        VerticalLayout buttomSpacer = new VerticalLayout();
        buttomSpacer.setHeight(2,Unit.PIXELS);
        buttomSpacer.setStyleName(Reindeer.LAYOUT_BLACK);
        buttomSpacer.setMargin(false);
        vlo1.addComponent(buttomSpacer);
        VerticalLayout l1 = new VerticalLayout();

        Label ExpLable1_1 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'>Dataset  Name:</strong><br/>" + handler.getMainDataset().getName() + "</h5>");
        ExpLable1_1.setContentMode(ContentMode.HTML);
        ExpLable1_1.setHeight(45,Unit.PIXELS);

        Label ExpLable1_2 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'>Species:</strong><br/>" + handler.getMainDataset().getSpecies() + "</h5>");
        ExpLable1_2.setContentMode(ContentMode.HTML);
        ExpLable1_2.setHeight(45,Unit.PIXELS);

        Label ExpLable1_3 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'>Sample Type:</strong><br/>" + handler.getMainDataset().getSampleType() + "</h5>");
        ExpLable1_3.setContentMode(ContentMode.HTML);
        ExpLable1_3.setHeight(45,Unit.PIXELS);

        Label ExpLable1_4 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'>Sample Processing:</strong><br/>" + handler.getMainDataset().getSampleProcessing() + "</h5>");
        ExpLable1_4.setContentMode(ContentMode.HTML);
        ExpLable1_4.setHeight(45,Unit.PIXELS);

        String href;
        Label ExpLable1_5;
        if (handler.getMainDataset().getPublicationLink().equalsIgnoreCase("NOT AVAILABLE") || handler.getMainDataset().getPublicationLink().equalsIgnoreCase("")) {
            ExpLable1_5 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'>No Publication Link Available </strong></h5>");
            ExpLable1_5.setHeight(45,Unit.PIXELS);
        } else {
            href = handler.getMainDataset().getPublicationLink().toLowerCase();
            if ((!href.contains("http://")) && (!href.contains("https://"))) {
                href = "http://" + href;
            }
            ExpLable1_5 = new Label("<h5><a href='" + href + "'  target='_blank'>Publication Link</a></h5>");
            ExpLable1_5.setHeight(45,Unit.PIXELS);

        }

        ExpLable1_5.setContentMode(ContentMode.HTML);
        l1.addComponent(ExpLable1_1);
        if (handler.getMainDataset().getDescription().length() <= 100) {
            Label ExpLable2_1 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'>Description:</strong><br/>" + handler.getMainDataset().getDescription() + "</h5>");
            ExpLable2_1.setContentMode(ContentMode.HTML);
            ExpLable2_1.setHeight(45,Unit.PIXELS);
            l1.addComponent(ExpLable2_1);
        } else {
            Label ExpLable2_1 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'>Description:</strong></h5>");
            ExpLable2_1.setContentMode(ContentMode.HTML);
            ExpLable2_1.setHeight(30,Unit.PIXELS);

            Label ExpLable2_2 = new Label("<h5 style='font-family:verdana;color:gray;'>" + handler.getMainDataset().getDescription() + "</h5>");
            ExpLable2_2.setContentMode(ContentMode.HTML);

            VerticalLayout lTemp = new VerticalLayout();
            lTemp.addComponent(ExpLable2_2);
            lTemp.setMargin(true);
            ExpLable2_2.setSizeFull();
            Panel p = new Panel();
            p.setContent(lTemp);
            p.setWidth(80,Unit.PERCENTAGE);
            p.setHeight(80,Unit.PIXELS);
            p.setScrollTop(20);
            p.setScrollLeft(50);

            p.setStyleName(Runo.PANEL_LIGHT);

            l1.addComponent(ExpLable2_1);
            l1.addComponent(p);
        }

        VerticalLayout l2 = new VerticalLayout();

        Label ExpLable2_3 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'>Instrument Type:</strong><br/>" + handler.getMainDataset().getInstrumentType() + "</h5>");
        ExpLable2_3.setContentMode(ContentMode.HTML);
        ExpLable2_3.setHeight(45,Unit.PIXELS);

        Label ExpLable2_4 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'>Frag Mode:</strong><br/>" + handler.getMainDataset().getFragMode() + "</h5>");
        ExpLable2_4.setContentMode(ContentMode.HTML);
        ExpLable2_4.setHeight(45,Unit.PIXELS);
        Label ExpLable2_5 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'>Uploaded By:</strong><br/>" + handler.getMainDataset().getUploadedByName() + "</h5>");
        ExpLable2_5.setContentMode(ContentMode.HTML);
        ExpLable2_5.setHeight(45,Unit.PIXELS);

        Label ExpLable2_6 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'>Email:</strong><br/>" + handler.getMainDataset().getEmail() + "</h5>");
        ExpLable2_6.setContentMode(ContentMode.HTML);
        ExpLable2_6.setHeight(45,Unit.PIXELS);

        Label ExpLable2_7 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'># Fractions</strong><br/>" + handler.getMainDataset().getFractionsNumber() + "</h5>");
        ExpLable2_7.setContentMode(ContentMode.HTML);
        ExpLable2_7.setHeight(45,Unit.PIXELS);
        Label ExpLable2_8 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'># Protein Groups:</strong><br/>" + handler.getMainDataset().getNumberValidProt()/* handler.getMainDataset().getProteinsNumber()*/ + "</h5>");
        ExpLable2_8.setContentMode(ContentMode.HTML);
        ExpLable2_8.setDescription("Number of validated proteins");
        ExpLable2_8.setHeight(45,Unit.PIXELS);

        HorizontalLayout pepHlo = new HorizontalLayout();
        Label ExpLable2_9 = new Label("<h5  style='font-family:verdana;color:gray;'><strong style='font-family:verdana;color:#424242;'># Peptides</strong><br/>" + handler.getMainDataset().getPeptidesNumber() + "</h5>");
        ExpLable2_9.setContentMode(ContentMode.HTML);
        ExpLable2_9.setDescription("Number of validated peptides");
        ExpLable2_9.setHeight(45,Unit.PIXELS);

        HorizontalLayout expIcon = excelExporterIconGenerator.generateDatasetInformationNote(new CustomExportBtnLayout(handler, "allPep", handler.getMainDataset().getDatasetId(), handler.getMainDataset().getName(), null, null, null, 0, null, null, null, null), "Export All Peptides for " + handler.getMainDataset().getName(), "");

        pepHlo.addComponent(ExpLable2_9);
        pepHlo.addComponent(expIcon);

        l1.addComponent(ExpLable2_3);

        l2.addComponent(ExpLable1_2);
        l2.addComponent(ExpLable2_4);
        l2.addComponent(ExpLable1_4);

        l2.addComponent(ExpLable1_3);
        l2.addComponent(pepHlo);
        pepHlo.setComponentAlignment(expIcon, Alignment.MIDDLE_CENTER);
        pepHlo.setSpacing(true);

        VerticalLayout l3 = new VerticalLayout();

        l3.addComponent(ExpLable2_7);

        l3.addComponent(ExpLable2_8);
        l3.addComponent(ExpLable2_5);
        l3.addComponent(ExpLable2_6);
        l3.addComponent(ExpLable1_5);

        hlo1.setWidth(100,Unit.PERCENTAGE);
        hlo1.addComponent(l1);
        hlo1.addComponent(l2);
        hlo1.addComponent(l3);
        hlo1.setExpandRatio(l1, 3);
        hlo1.setExpandRatio(l2, 3);
        hlo1.setExpandRatio(l3, 1);

        hlo1.setComponentAlignment(l3, Alignment.TOP_RIGHT);
        return vlo1;
    }

    @Override
    public void layoutClick(LayoutClickEvent event) {

        if (mainComponentBodyLayout.isVisible()) {
            DatasetDetailsComponent.this.collapse();

        } else {

            DatasetDetailsComponent.this.expand();
        }

    }

    private void expand() {
        show.setExpandLabel(true);
        mainComponentBodyLayout.setVisible(true);
    }

    /**
     * Hide dataset overview layout.
     */
    public final void collapse() {
        show.setExpandLabel(false);
        mainComponentBodyLayout.setVisible(false);
    }

    /**
     * Check if collapse or expanded dataset overview layout
     *
     * @return expanded dataset overview layout
     */
    public boolean isExpanded() {
        return mainComponentBodyLayout.isVisible();
    }

    /**
     * Expand or collapse dataset overview layout
     *
     * @param expandLayout
     */
    public void setExpand(boolean expandLayout) {
        if (expandLayout) {
            DatasetDetailsComponent.this.expand();
        } else {
            DatasetDetailsComponent.this.collapse();
        }
    }
}
