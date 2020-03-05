package probe.com.view;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import probe.com.model.beans.ProteinBean;
import probe.com.model.beans.StandardProteinBean;
import probe.com.view.components.PlotsLayout;

/**
 * This class represents the plot component inside the fraction layout
 *
 * @author Yehia Farag
 */
public class FractionPlotLayout extends VerticalLayout implements Serializable {

    private final Map<String, List<StandardProteinBean>> standProtGroups;
    private final VerticalLayout leftSideLayout;
    private final VerticalLayout rightSideLayout;

    /**
     * Initialise fraction plot layout for the gel dataset
     *
     * @param protienFractionList main fractions list
     * @param mw the protein MW
     * @param standProtList standard protein objects that work as reference
     */
    public FractionPlotLayout(Map<Integer, ProteinBean> protienFractionList, double mw, List<StandardProteinBean> standProtList) {
        leftSideLayout = new VerticalLayout();
        rightSideLayout = new VerticalLayout();
        rightSideLayout.setStyleName(Reindeer.LAYOUT_WHITE);
        rightSideLayout.setSizeFull();
        rightSideLayout.setMargin(false);
        this.standProtGroups = initGroups(standProtList, mw);
        leftSideLayout.setWidth(100,Unit.PERCENTAGE);
        leftSideLayout.setStyleName(Reindeer.LAYOUT_WHITE);

        FractionPlotLayout.this.setSpacing(true);
        FractionPlotLayout.this.setMargin(false);
        FractionPlotLayout.this.setWidth(100,Unit.PERCENTAGE);
        FractionPlotLayout.this.setStyleName(Reindeer.PANEL_LIGHT);
        leftSideLayout.addComponent(new PlotsLayout("#Peptides", protienFractionList, standProtGroups, mw));
        leftSideLayout.addComponent(new PlotsLayout("#Spectra", protienFractionList, standProtGroups, mw));
        leftSideLayout.addComponent(new PlotsLayout("Avg. Precursor Intensity", protienFractionList, standProtGroups, mw));
        FractionPlotLayout.this.addComponent(leftSideLayout);
        FractionPlotLayout.this.addComponent(rightSideLayout);
    }

    private Map<String, List<StandardProteinBean>> initGroups(List<StandardProteinBean> standProtList, double mw) {
        Map<String, List<StandardProteinBean>> colorMap = new HashMap<String, List<StandardProteinBean>>();
        List<StandardProteinBean> blueList = new ArrayList<StandardProteinBean>();
        List<StandardProteinBean> redList = new ArrayList<StandardProteinBean>();
        List<StandardProteinBean> lowerList = new ArrayList<StandardProteinBean>();
        List<StandardProteinBean> upperList = new ArrayList<StandardProteinBean>();
        for (StandardProteinBean spb : standProtList) {
            if (spb.getMW_kDa() > mw) {
                upperList.add(spb);
            } else {
                lowerList.add(spb);
            }
        }
        StandardProteinBean closeLowe = new StandardProteinBean();
        closeLowe.setMW_kDa(-10000);
        StandardProteinBean closeUpper = new StandardProteinBean();
        closeUpper.setMW_kDa((10000 * 1000));
        for (StandardProteinBean spb : lowerList) {
            if (closeLowe.getMW_kDa() <= spb.getMW_kDa()) {
                closeLowe = spb;
            }
        }
        for (StandardProteinBean spb : upperList) {
            if (closeUpper.getMW_kDa() >= spb.getMW_kDa()) {
                closeUpper = spb;
            }
        }
        for (StandardProteinBean spb : standProtList) {
            if ((spb.getMW_kDa() == closeLowe.getMW_kDa() && spb.getName().equalsIgnoreCase(closeLowe.getName())) || (spb.getMW_kDa() == closeUpper.getMW_kDa() && spb.getName().equalsIgnoreCase(closeUpper.getName()))) {
                spb.setTheoretical(true);
                redList.add(spb);
            } else {
                spb.setTheoretical(false);
                blueList.add(spb);
            }
        }
        colorMap.put("#CDE1FF", blueList);
        colorMap.put("#79AFFF", redList);
        return colorMap;

    }

}
