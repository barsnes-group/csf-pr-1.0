package probe.com.view;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 * this class represents the main header
 *
 * @author Yehia Farag the layout of the top part of the web page
 */
public class Header extends VerticalLayout {

    private final HorizontalLayout header = new HorizontalLayout();//Title and logo layout (top layout)

    /**
     * Initialise top body layout for the web page.
     *
     *
     */
    public Header() {

        Header.this.setWidth(100,Unit.PERCENTAGE);
        Header.this.setStyleName(Reindeer.LAYOUT_WHITE);
        //init header
        header.setWidth(1300,Unit.PIXELS);
        header.setHeight(60,Unit.PIXELS);
        header.setStyleName(Reindeer.LAYOUT_WHITE);

        //Coronetscript, cursive Helvetica
        Label csfLable = new Label("<a href='' style='text-decoration:none'><p   align='left' ; style='margin-left:40px;font-family:verdana;color:#4d749f;font-weight:bold !important;text-decoration:none ; font: 300 14px/1.55 'Helvetica Neue', Helvetica, Arial, 'Microsoft YaHei New', 'Microsoft Yahei', SimSun, STXihei, sans-serif;'><FONT SIZE='5'><font style='color: white;\n" +
"    background: #4e739e;\n" +
"    padding-left: 5px;\n" +
"    padding-right: 5px;\n" +
"    border-radius: 5px;\n" +
"    margin-right: 2px;\n" +
"    font-weight: bold;\n" +
"    font-size: 24px;'>C</font>SF Proteome Resource <font size='2'>v1.0</font> </FONT></p></a>");
        csfLable.setContentMode(ContentMode.HTML);
        csfLable.setStyleName(Reindeer.LABEL_SMALL);
        csfLable.setWidth(100,Unit.PERCENTAGE);

        header.addComponent(csfLable);
        header.setComponentAlignment(csfLable, Alignment.MIDDLE_LEFT);

        VerticalLayout logoLayout = new VerticalLayout();
        logoLayout.setStyleName(Reindeer.LAYOUT_WHITE);
        HorizontalLayout hlo = new HorizontalLayout();

        Link probe_ico = new Link(null, new ExternalResource("http://www.uib.no/rg/probe"));
        probe_ico.setIcon(new ThemeResource("img/probe-updated.png"));
        probe_ico.setTargetName("_blank");
        probe_ico.setWidth(237,Unit.PIXELS);
        probe_ico.setHeight(58,Unit.PIXELS);
        probe_ico.addStyleName("probelogo");
        hlo.addComponent(probe_ico);

        Link uib_ico = new Link(null, new ExternalResource("http://www.uib.no/"));
        uib_ico.setIcon(new ThemeResource("img/uib-logo.svg"));
        uib_ico.addStyleName("uiblogo");
        uib_ico.setTargetName("_blank");
        uib_ico.setWidth(87,Unit.PIXELS);
        uib_ico.setHeight(58,Unit.PIXELS);
        hlo.addComponent(uib_ico);

        Link kgj_ico = new Link(null, new ExternalResource("http://www.uib.no/en/rg/kgj-ms"));
        kgj_ico.setIcon(new ThemeResource("img/kgj.svg"));
        kgj_ico.setTargetName("_blank");
        kgj_ico.setHeight(58,Unit.PIXELS);
        kgj_ico.addStyleName("kgjlogo");
        hlo.addComponent(kgj_ico);
        Label spacer = new Label("<p   align='left' ; style='margin-left:40px;font-family:verdana;color:#4d749f;font-weight:bold;text-decoration:none '></p>");
        spacer.setContentMode(ContentMode.HTML);
        spacer.setStyleName(Reindeer.LABEL_SMALL);

        hlo.addComponent(spacer);

        hlo.setComponentAlignment(probe_ico, Alignment.MIDDLE_RIGHT);
        hlo.setComponentAlignment(uib_ico, Alignment.MIDDLE_RIGHT);
        hlo.setComponentAlignment(kgj_ico, Alignment.MIDDLE_RIGHT);

        hlo.setComponentAlignment(spacer, Alignment.MIDDLE_RIGHT);

        hlo.setHeight(60,Unit.PIXELS);

        logoLayout.addComponent(hlo);
        logoLayout.setComponentAlignment(hlo, Alignment.MIDDLE_RIGHT);

        logoLayout.setWidth(100,Unit.PERCENTAGE);

        header.addComponent(logoLayout);
        header.setComponentAlignment(logoLayout, Alignment.MIDDLE_RIGHT);
        header.setWidth(100,Unit.PERCENTAGE);
        header.setHeight(60,Unit.PIXELS);
        header.setSpacing(true);
        Header.this.addComponent(header);

    }
}
