package learn.solar.data;

import learn.solar.models.Panel;
import learn.solar.models.Material;

import java.util.ArrayList;
import java.util.List;

public class PanelRepositoryTestDouble implements PanelRepository {


    @Override
    public List<Panel> findAll() throws DataException {
        return List.of(new Panel(1, "Main", 2, 2, 2019,learn.solar.models.Material.COPPER_INDIUM_GALLIUM_SELENIDE, true),
                new Panel(2, "Lower Hill", 2,3, 2021, Material.AMORPHOUS_SILLICON,true  ));
    }


    @Override
    public  Panel findPanel(String section, int row, int column)throws DataException{
        List<Panel> allPanels = findAll();
        for(int i=0; i<allPanels.size(); i++){
            Panel panel=allPanels.get(i);
            if(panel.getSection().equals(section) && panel.getRow()==row && panel.getColumn()==column){
                return panel;
            }
        }
        return null;

    }

    @Override
    public List<Panel> findBySection(String section) throws DataException {

        List<Panel> allPanels = findAll();

        List<Panel> result=new ArrayList<>();

        for(int i=0; i<allPanels.size(); i++){
            if(allPanels.get(i).getSection().equals(section)){
                result.add(allPanels.get(i));
            }
        }

        return result;
    }

    @Override
    public Panel add(Panel panel) throws DataException {
        panel.setPanelId(3);
        return panel;
    }
    @Override
    public boolean update(Panel panel)throws DataException {
        List<Panel> allPanels = findAll();

        int index = findIndexById(panel.getPanelId(), allPanels);
        if(index<0){
            return false;
        }
       // allPanels.set(index, panel);


        return true;
    }

    @Override
    public boolean deleteById(int id)throws DataException {
        List<Panel> allPanels = findAll();
        int index = findIndexById(id, allPanels);
        if(index<0){
            return false;
        }
        allPanels.remove(index);

        return true;

    }


    public int findIndexById(int id, List<Panel> allPanels) throws DataException {
        for(int i=0; i<allPanels.size(); i++){
            if(allPanels.get(i).getPanelId()==id){
                return i;
            }
        }
        return -1;
    }

}

