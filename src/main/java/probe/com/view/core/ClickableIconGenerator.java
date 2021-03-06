package probe.com.view.core;

import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.ThemeResource;
import java.io.Serializable;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.PopupView.PopupVisibilityEvent;
import com.vaadin.ui.themes.Reindeer;

/**
 * This class responsible for generating different icons
 *
 * @author Yehia Farag
 *
 */
public class ClickableIconGenerator implements Serializable {

    private static final long serialVersionUID = 1L;
    private Resource res;

    /**
     * Generate help note
     *
     * @param label help note label
     * @return ready to view click-able icon
     */
    public HorizontalLayout generateHelpNote(Label label) {
        PopupView popup;
        res = new ThemeResource("../runo/icons/" + 16 + "/help.png");
        HorizontalLayout helpLayout = new HorizontalLayout();
        popup = new PopupView("HELP", label);
        popup.setHideOnMouseOut(true);
        popup.setWidth(40,Unit.PERCENTAGE);
        popup.addPopupVisibilityListener(new PopupView.PopupVisibilityListener() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void popupVisibilityChange(PopupVisibilityEvent event) {
                if (!event.isPopupVisible()) {
                }

            }
        });
        helpLayout.addComponent(popup);
        helpLayout.setComponentAlignment(popup, Alignment.BOTTOM_CENTER);

        Embedded e = new Embedded(null, res);
        e.setWidth(16,Unit.PIXELS);
        e.setHeight(16,Unit.PIXELS);
        helpLayout.addComponent(e);
        return helpLayout;

    }

    /**
     * Generate information note
     *
     * @param label information note label
     * @return ready to view click-able icon
     */
    public HorizontalLayout generateInformationNote(final Label label) {
        PopupView popup;
        HorizontalLayout helpLayout = new HorizontalLayout();
        PopupView.Content content = new PopupView.Content() {
            @Override
            public String getMinimizedValueAsHTML() {//Protein Standards
                return "<img src='VAADIN/themes/dario-theme/img/info.jpg' alt='Information'>";//https://fbcdn-sphotos-d-a.akamaihd.net/hphotos-ak-prn2/1175716_173022812881319_804705945_n.jpg
            }

            @Override
            public Component getPopupComponent() {
                return label;

            }
        };
        popup = new PopupView("", label);
        popup.setContent(content);
        popup.setHideOnMouseOut(true);
        popup.setWidth(40,Unit.PERCENTAGE);
        popup.setStyleName(Reindeer.LAYOUT_BLACK);
        popup.addPopupVisibilityListener(new PopupView.PopupVisibilityListener() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void popupVisibilityChange(PopupVisibilityEvent event) {
                if (!event.isPopupVisible()) {
                }

            }
        });
        helpLayout.addComponent(popup);
        helpLayout.setComponentAlignment(popup, Alignment.TOP_CENTER);
        return helpLayout;

    }

    /**
     * Generate dataset information note
     *
     * @param exportBtnLayout export button container
     * @param description dataset description
     * @param title dataset title
     * @return
     */
    public HorizontalLayout generateDatasetInformationNote(final CustomExportBtnLayout exportBtnLayout, final String description, final String title) {
        PopupView popup;
        HorizontalLayout helpLayout = new HorizontalLayout();
        PopupView.Content content = new PopupView.Content() {
            @Override
            public String getMinimizedValueAsHTML() {
                return "<img style='height:16px;margin-top:10px;' src='VAADIN/themes/dario-theme/img/save-file.png' alt='" + description + "' title='" + description + "'> ";
            }

            @Override
            public Component getPopupComponent() {
                return exportBtnLayout;

            }
        };

        popup = new PopupView(title, exportBtnLayout);
        popup.setCaption(title);
        popup.setContent(content);
        popup.setHideOnMouseOut(true);
        popup.setWidth(40, Sizeable.Unit.PERCENTAGE);
        popup.setStyleName(Reindeer.LAYOUT_BLACK);
        popup.addPopupVisibilityListener(new PopupView.PopupVisibilityListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void popupVisibilityChange(PopupVisibilityEvent event) {
                if (!event.isPopupVisible()) {
                }

            }
        });
        helpLayout.addComponent(popup);
        helpLayout.setComponentAlignment(popup, Alignment.TOP_CENTER);
        return helpLayout;

    }

}
