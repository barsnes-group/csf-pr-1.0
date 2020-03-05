package probe.com.view.core;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import java.io.Serializable;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.BaseTheme;

/**
 * This class represents table size controller
 *
 * @author Yehia Farag
 */
public class TableSizeController extends HorizontalLayout implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Table table;
    private final String SMALL_SIZE = "160px";
    private final String MEDIUM_SIZE = "267.5px";
    private final String LARGE_SIZE = "360px";
    private String currentSize;

    /**
     * Initialise table size controller
     *
     * @param table table to be resized
     * @param defaultSize default size
     */
    public TableSizeController(Table table, String defaultSize) {
        this.currentSize = defaultSize;
        this.table = table;
        TableSizeController.this.setStyleName("tableresizeicons");
        TableSizeController.this.setWidth(90,Unit.PIXELS);
        TableSizeController.this.setHeight(21,Unit.PIXELS);

        Button b1 = init("img/larg.jpg", SMALL_SIZE);
        Button b2 = init("img/med.jpg", MEDIUM_SIZE);
        Button b3 = init("img/small.jpg", LARGE_SIZE);

        b1.setDescription("Small Table Size");
        b2.setDescription("Medium Table Size");
        b3.setDescription("Large Table Size");

        TableSizeController.this.addComponent(b1);
        TableSizeController.this.addComponent(b2);
        TableSizeController.this.addComponent(b3);
        TableSizeController.this.setComponentAlignment(b1, Alignment.BOTTOM_RIGHT);
        TableSizeController.this.setComponentAlignment(b2, Alignment.BOTTOM_RIGHT);
        TableSizeController.this.setComponentAlignment(b3, Alignment.BOTTOM_RIGHT);
        MarginInfo m = new MarginInfo(false, true, false, false);
        TableSizeController.this.setMargin(m);

    }

    private Button init(String link, final String size) {
        Button b = new Button();
        b.setStyleName(BaseTheme.BUTTON_LINK);
        b.setIcon(new ThemeResource(link));
        b.addClickListener(new ClickListener() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {
                table.setHeight(size);
                currentSize = size;
            }
        });
        return b;
    }

    /**
     * Get current table size
     *
     * @return current table size
     */
    public String getCurrentSize() {
        return this.currentSize;
    }

    /**
     * Set table to be controlled
     *
     * @param table table to be controlled
     */
    public void setTable(Table table) {
        this.table = table;
        this.table.setHeight(currentSize);
    }
}
