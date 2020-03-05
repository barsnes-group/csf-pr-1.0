package probe.com.view.components;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.base.elements.XYseries;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.data.Ticks;
import org.dussan.vaadin.dcharts.metadata.TooltipAxes;
import org.dussan.vaadin.dcharts.metadata.TooltipFadeSpeeds;
import org.dussan.vaadin.dcharts.metadata.TooltipMoveSpeeds;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.Yaxes;
import org.dussan.vaadin.dcharts.metadata.locations.TooltipLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.TickRenderers;
import org.dussan.vaadin.dcharts.metadata.ticks.TickLabelPositions;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Grid;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.Series;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.tick.CanvasAxisTickRenderer;
import probe.com.model.beans.ProteinBean;
import probe.com.model.beans.StandardProteinBean;

/**
 * This class represents plots layout
 *
 * @author Yehia Farag
 */
public class PlotsLayout extends VerticalLayout implements Serializable {

    /**
     * Initialise the chart
     *
     * @param title title
     * @param protienFractionList list of fraction data
     * @param standProtList list of the standard proteins
     * @param mw the protein MW
     */
    public PlotsLayout(String title, Map<Integer, ProteinBean> protienFractionList, Map<String, List<StandardProteinBean>> standProtList, double mw) {
        if (!protienFractionList.isEmpty()) {
            Label pepLable = new Label("<h5 style='font-family:verdana;color:#4d749f;text-align:left'>" + title + "</h5>");
            pepLable.setContentMode(ContentMode.HTML);
            pepLable.setHeight(30,Unit.PIXELS);
            pepLable.setWidth(150,Unit.PIXELS);
            PlotsLayout.this.addComponent(pepLable);
            PlotsLayout.this.setComponentAlignment(pepLable, Alignment.BOTTOM_LEFT);
            PlotsLayout.this.setMargin(false);
            List<StandardProteinBean> borderList = standProtList.get("#79AFFF");
            List<StandardProteinBean> standardList = standProtList.get("#CDE1FF");
            Double[] initRealValue = new Double[(protienFractionList.size() + borderList.size() + standardList.size())];
            Map<StandardProteinBean, Double[]> standardValuesList = new HashMap<StandardProteinBean, Double[]>();
            Map<StandardProteinBean, Double[]> tempStandardValuesList = new HashMap<StandardProteinBean, Double[]>();
            double lower = -1;
            double upper = -2;
            for (StandardProteinBean spb : borderList) {
                Double[] standardValues = new Double[initRealValue.length];
                standardValues[0] = 0d;
                spb.setColor("#79AFFF");
                spb.setTheoretical(true);

                if (upper < spb.getMW_kDa()) {
                    lower = upper;
                    upper = spb.getMW_kDa();
                } else {
                    lower = spb.getMW_kDa();
                }
                standardValuesList.put(spb, standardValues);
            }
            for (StandardProteinBean spb : standardList) {
                Double[] standardValues = new Double[initRealValue.length];
                standardValues[0] = 0d;
                spb.setColor("#CDE1FF");
                standardValuesList.put(spb, standardValues);
            }
            Object[] strArr = new String[initRealValue.length];
            int x = 0;
            int f = 1;
            double highScore = -1;
            initRealValue[0] = 0d;
            //for testing
            strArr[0] = "";
            String formater = "";
            int ang = 0;
            if (title.equalsIgnoreCase("#Peptides")) {
                for (int index : protienFractionList.keySet()) {
                    ProteinBean pb = protienFractionList.get(index);
                    initRealValue[x] = (double) pb.getNumberOfPeptidePerFraction();
                    strArr[x] = f + "";
                    for (StandardProteinBean spb : standardValuesList.keySet()) {
                        if (spb.getLowerFraction() == f) {
                            ++x;
                            initRealValue[x] = 0.0d;
                            strArr[x] = " ";
                            spb.setFractionIndicator(strArr[x].toString());
                            break;
                        }
                    }

                    if (highScore < pb.getNumberOfPeptidePerFraction()) {
                        highScore = pb.getNumberOfPeptidePerFraction();
                        if (highScore <= 10) {
                            highScore = 10d;
                        }
                    }
                    x++;
                    f++;
                }
                String str = "";
                if (highScore <= 60) {
                    str = StringUtils.leftPad("", 9);
                } else if (highScore <= 1000) {
                    str = StringUtils.leftPad("", 7);
                }
                formater = str + "%d";
            } else if (title.equalsIgnoreCase("#Spectra")) {
                ang = 0;
                for (int index : protienFractionList.keySet()) {
                    ProteinBean pb = protienFractionList.get(index);
                    initRealValue[x] = (double) pb.getNumberOfSpectraPerFraction();
                    strArr[x] = f + "";

                    for (StandardProteinBean spb : standardValuesList.keySet()) {
                        if (spb.getLowerFraction() == f) {
                            ++x;
                            initRealValue[x] = 0.0d;
                            strArr[x] = " ";
                            spb.setFractionIndicator(strArr[x].toString());
                            break;
                        }
                    }
                    if (highScore < pb.getNumberOfSpectraPerFraction()) {
                        highScore = pb.getNumberOfSpectraPerFraction();
                        if (highScore <= 10) {
                            highScore = 10d;
                        }
                    }
                    x++;
                    f++;
                }
                String str;
                if (highScore <= 60) {
                    str = StringUtils.leftPad("", 9);
                } else if (highScore <= 1000) {
                    str = StringUtils.leftPad("", 7);
                } else {
                    str = StringUtils.leftPad("", 5);
                }
                formater = str + "%d";
            } else if (title.equalsIgnoreCase("Avg. Precursor Intensity")) {
                ang = 0;
                for (int index : protienFractionList.keySet()) {
                    ProteinBean pb = protienFractionList.get(index);
                    double avg = (pb.getAveragePrecursorIntensityPerFraction());
                    initRealValue[x] = avg;
                    strArr[x] = f + "";
                    for (StandardProteinBean spb : standardValuesList.keySet()) {
                        if (spb.getLowerFraction() == f) {
                            ++x;
                            initRealValue[x] = 0.0d;
                            strArr[x] = " ";
                            spb.setFractionIndicator(strArr[x].toString());
                            break;
                        }
                    }
                    if (highScore < avg) {
                        highScore = avg;
                        if (highScore <= 10) {
                            highScore = 10d;
                        }
                    }
                    x++;
                    f++;
                }

                String str = "";
                if (highScore <= 500000) {
                    str = StringUtils.leftPad("", 2);
                }
                formater = str + "%d";
            }

            tempStandardValuesList.clear();
            for (int index = 1; index < strArr.length; index++) {
                int indexLable;
                try {
                    indexLable = Integer.valueOf(strArr[index].toString());
                } catch (NumberFormatException e) {
                    continue;
                }
                for (StandardProteinBean spb : standardValuesList.keySet()) {
                    if (spb.getLowerFraction() == indexLable) {
                        Double[] values = standardValuesList.get(spb);
                        for (int localx = 0; localx < values.length; localx++) {
                            if (localx == index) {
                                values[index] = 0d;
                                values[index + 1] = (double) highScore;
                                localx++;
                                continue;
                            }
                            values[localx] = 0d;

                        }
                        tempStandardValuesList.put(spb, values);
                        break;
                    }

                }
            }
            standardValuesList.clear();
            standardValuesList.putAll(tempStandardValuesList);
            tempStandardValuesList.clear();
            Object[] realValue = new Double[(initRealValue.length)];
            System.arraycopy(initRealValue, 0, realValue, 0, realValue.length);
            realValue[0] = initRealValue[0];

            DataSeries dataSeries = new DataSeries();
            dataSeries.add(realValue);
            String[] colours = new String[standardValuesList.size() + 1];
            colours[0] = "#50B747";

            String[] lables = new String[standardValuesList.size()];

            int z = 1;
            for (StandardProteinBean spb : standardValuesList.keySet()) {
                Double[] initValues = standardValuesList.get(spb);
                Object[] values = new Double[(initValues.length)];
                System.arraycopy(initValues, 0, values, 0, values.length);
                values[0] = initValues[0];
                dataSeries.add(values);
                colours[z] = spb.getColor();
                String lab;
                if (spb.getMW_kDa() == lower) {
                    lab = (int) spb.getMW_kDa() + " kDa " + spb.getName() + " [MW Standard] Lower Theoretical ";
                } else if (spb.getMW_kDa() == upper) {
                    lab = (int) spb.getMW_kDa() + " kDa " + spb.getName() + " [MW Standard] Upper Theoretical ";
                } else {
                    lab = (int) spb.getMW_kDa() + " kDa " + spb.getName() + " [MW Standard] ";
                }
                lables[z - 1] = lab;
                z++;
            }
            SeriesDefaults seriesDefaults = new SeriesDefaults()
                    .setFillToZero(true)
                    .setRenderer(SeriesRenderers.BAR)
                    .setLineWidth(0.2f)
                    .setGridBorderWidth(2.5f)
                    .setYaxis(Yaxes.Y);
            Series series = new Series()
                    .addSeries(new XYseries().setYaxis(Yaxes.Y).setIndex(0).setLabel(title).setShowLabel(false).setShadow(false).setDisableStack(false));

            for (int k = 0; k < lables.length; k++) {
                series.addSeries(new XYseries().setYaxis(Yaxes.Y).setIndex(k + 1).setLabel(lables[k]).setShowLabel(false).setShadow(false).setDisableStack(false).setShowMarker(true));
            }
            Highlighter highlighter = new Highlighter()
                    .setUseAxesFormatters(true)
                    .setShow(true)
                    .setTooltipFadeSpeed(TooltipFadeSpeeds.FAST)
                    .setDefault(true)
                    .setTooltipMoveSpeed(TooltipMoveSpeeds.FAST)
                    .setFadeTooltip(true)
                    .setShowTooltip(true)
                    .setKeepTooltipInsideChart(true)
                    .setBringSeriesToFront(false)
                    .setTooltipAxes(TooltipAxes.Y_BAR)
                    .setTooltipLocation(TooltipLocations.NORTH)
                    .setShowMarker(false);

            Axes axes = new Axes()
                    .addAxis(new XYaxis().setDrawMajorTickMarks(true).setAutoscale(false)
                            .setBorderColor("#CED8F6").setDrawMajorGridlines(false).setDrawMinorGridlines(false)
                            .setRenderer(AxisRenderers.CATEGORY).setTickSpacing(1).setTickInterval(0.1f)
                            .setTicks(new Ticks().add(strArr)).setTickRenderer(TickRenderers.CANVAS)
                            .setTickOptions(
                                    new CanvasAxisTickRenderer()
                                            .setFontSize("8pt")
                                            .setShowMark(true)
                                            .setShowGridline(true)))
                    .addAxis(
                            new XYaxis(XYaxes.Y)
                                    .setNumberTicks(3)
                                    .setAutoscale(false).setMax(highScore)
                                    .setTickOptions(
                                            new CanvasAxisTickRenderer().setLabelPosition(TickLabelPositions.END).setAngle(ang).setFormatString(formater).setShowLabel(true).setShowMark(true)
                                    ));

//           
            Grid grid = new Grid().setDrawBorder(false).setBackground("#FFFFFF").setBorderColor("#CED8F6").setGridLineColor("#CED8F6").setShadow(false);
            Options options = new Options()
                    .setSeriesDefaults(seriesDefaults)
                    .setSeries(series)
                    .setAxes(axes)
                    .setSyncYTicks(false)
                    .setHighlighter(highlighter)
                    .setSeriesColors(colours)
                    .setAnimate(false)
                    .setAnimateReplot(false)
                    .setStackSeries(true)
                    .setGrid(grid);

            DCharts chart = new DCharts().setDataSeries(dataSeries).setOptions(options).show();
            chart.setWidth(100,Unit.PERCENTAGE);
            chart.setHeight(120,Unit.PIXELS);
            chart.setMarginRight(10);          
            PlotsLayout.this.setWidth(100,Unit.PERCENTAGE);
            PlotsLayout.this.addComponent(chart);
            PlotsLayout.this.setComponentAlignment(chart, Alignment.MIDDLE_RIGHT);

        }
    }
}
