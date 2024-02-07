package learn.solar.ui;

import learn.solar.data.DataException;
import learn.solar.domain.PanelResult;
import learn.solar.domain.PanelService;
import learn.solar.models.Panel;

import java.util.List;

public class Controller {

    private PanelService panelService;

    private View view;

    public Controller(PanelService panelService, View view) {
        this.panelService = panelService;
        this.view = view;
    }


    public void run() {
        view.printHeader("Welcome to Solar Farm.");

        try {
            runMenuLoop();
        } catch (DataException ex) {
            view.printHeader("CRITICAL ERROR:" + ex.getMessage());
        }

        view.printHeader("Goodbye");
    }

    private void runMenuLoop() throws DataException {
        MenuOption option;
        do {
            option = view.displayMenuGetOption();
            switch (option) {
//                case DISPLAY_ALL:
//                    displayAllPanelss();
//                    break;
                case ADD:
                    addPanel();
                    break;

                case DISPLAY_BY_SECTION:
                    displayBySection();
                    break;
                case UPDATE:
                       update();
                    break;
                case DELETE:
                    delete();
            }
        } while (option != MenuOption.EXIT);
    }

//    private void displayAllPanels() throws DataException {
//        List<Panel> panels = panelService.findAll();
//        view.printAllPanels(panels);
//    }

    private void addPanel() throws DataException {
        Panel panel = view.makePanel();
        PanelResult result = panelService.add(panel);
        view.printResult(result);
    }

    private void displayBySection() throws DataException {
        view.printHeader("Find Panels by Section");

        String section = view.readRequiredString("choose section");

        List<Panel> panels = panelService.findBySection(section);

        view.printPanels(panels,section);

    }

    private void  update() throws DataException {
        view.printHeader("Update a Panel");
        String section = view.readRequiredString("Section: ");
        int row = view.readInt("Row: ",1,250);
        int column = view.readInt("Column: ",1,250);

        Panel panel = panelService.findPanel(section, row, column);



        if(panel ==null){
            view.displayMessage("Panel not found.");
            return;
        }

        panel = view.edit(panel);
        PanelResult result = panelService.update(panel);

        if(result.isSuccess()){
            view.displayMessage("[Success]");
            view.displayMessage("Panel "+panel.getSection()+"-"+panel.getRow()+"-"+panel.getColumn()+ " updated.");
        }else{
            view.printResult(result);
        }
    }


    private void delete() throws DataException{
        view.printHeader("Delete a Panel");
        String section = view.readRequiredString("Section: ");
        int row = view.readInt("Row: ",1,250);
        int column = view.readInt("Column: ",1,250);

        Panel panel = panelService.findPanel(section, row, column);



        if(panel ==null){
            view.displayMessage("[Err]");
            view.displayMessage("There is no panel " + section+"-"+row+"-"+column+".");
            return;
        }


        PanelResult result = panelService.deleteById(panel.getPanelId());




        if(result.isSuccess()){
            view.displayMessage("[Success]");
            view.displayMessage("Panel "+ section+"-"+row+"-"+column+"removed ");
        }else{
            view.printResult(result);
        }

    }


}
