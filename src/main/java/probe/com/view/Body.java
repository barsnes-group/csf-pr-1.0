package probe.com.view;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
import java.io.Serializable;
import probe.com.handlers.MainHandler;

/**
 * This class represents the main body of the application
 *
 * @author Yehia Farag
 */
public class Body extends VerticalLayout implements Button.ClickListener, Serializable {

    private TabSheet.Tab homeTab;//tabs for Experiments Editor,Proteins, Search
    private final TabSheet mainTabSheet;//tab sheet for first menu (Experiments Editor,Proteins, Search)
    private VerticalLayout searchLayout;
    private WelcomeLayout welcomeLayout;
    private ProteinsLayout proteinsLayout;
    private final MainHandler handler;

    /**
     * initialise body layout
     *
     * @param handler main dataset handler
     *
     *
     */
    public Body(MainHandler handler) {
        Body.this.setWidth(100,Unit.PERCENTAGE);
        this.handler = handler;

        mainTabSheet = new TabSheet();
        Body.this.addComponent(mainTabSheet);
        mainTabSheet.setHeight(100,Unit.PERCENTAGE);
        mainTabSheet.setWidth(100,Unit.PERCENTAGE);

        initBodyLayout();
    }

    /**
     * initialise body components layout
     */
    private void initBodyLayout() {
//        home layout
        welcomeLayout = new WelcomeLayout();
        welcomeLayout.setWidth(100,Unit.PERCENTAGE);
//        Tab 2 content
        proteinsLayout = new ProteinsLayout(handler);
//      Tab 3 content
        searchLayout = new VerticalLayout();
        searchLayout.setMargin(true);
        SearchLayout searchLayout1 = new SearchLayout(handler);
        this.searchLayout.addComponent(searchLayout1);

        homeTab = mainTabSheet.addTab(welcomeLayout, "Home", null);
        mainTabSheet.addTab(proteinsLayout, "Proteins", null);
        Tab searchTab = mainTabSheet.addTab(this.searchLayout, "Search");
        mainTabSheet.setSelectedTab(homeTab);
        mainTabSheet.markAsDirty();

        String requestSearching = VaadinService.getCurrentRequest().getPathInfo();
        if (!requestSearching.trim().endsWith("/")) {
            mainTabSheet.setSelectedTab(searchTab);
        }

    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        mainTabSheet.markAsDirty();
    }

}
