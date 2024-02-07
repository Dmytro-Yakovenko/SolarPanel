package learn.solar.data;

import learn.solar.models.Material;
import learn.solar.models.Panel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;




public class PanelFileRepository implements PanelRepository{





    private static final String DELIMITER = ",";
    private static final String DELIMITER_REPLACEMENT = "@@@";
    private static final String HEADER = "panelId,section,row,column,yearInstalled,material,isTracking";

    private final String filePath;

    public PanelFileRepository(String filePath) {
        this.filePath = filePath;
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
    public Panel findPanel(String section, int row, int column) throws DataException{
        List<Panel> allPanels = findAll();
        for(int i=0; i<allPanels.size(); i++){
            Panel panel=allPanels.get(i);
            if(panel.getSection().equals(section) && panel.getRow()==row && panel.getColumn()==column){
                return panel;
            }
        }
        return null;
    }


    public int findIndexById(int id, List<Panel> allPanels) throws DataException {
       for(int i=0; i<allPanels.size(); i++){
           if(allPanels.get(i).getPanelId()==id){
               return i;
           }
       }
       return -1;
    }

    @Override
    public Panel add(Panel panel) throws DataException {
        List<Panel> allPanels = findAll();
        panel.setPanelId(getNextId(allPanels));
        allPanels.add(panel);
        writeAll(allPanels);
        return panel;
    }

    @Override
    public boolean update(Panel panel)throws DataException {
        List<Panel> allPanels = findAll();

        int index = findIndexById(panel.getPanelId(), allPanels);
        if(index<0){
            return false;
        }
        allPanels.set(index, panel);
        writeAll(allPanels);

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
        writeAll(allPanels);

        return true;

    }

    public int getNextId(List<Panel> allPanels){
        if(allPanels.size()==0){
            return 1;
        }
        return allPanels.get(allPanels.size()-1).getPanelId()+1;
    }


    @Override
    public List<Panel> findAll() throws DataException {

        ArrayList<Panel> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // skip header
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Panel  panel= deserialize(line);
                if (panel != null) {
                    result.add(panel);
                }
            }
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {
            throw new DataException(ex.getMessage(), ex);
        }

        return result;
    }


    private void writeAll(List<Panel> panels) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.println(HEADER);
            for (Panel p : panels) {
                writer.println(serialize(p));
            }
        } catch (IOException ex) {
            throw new DataException(ex.getMessage(), ex);
        }
    }


    private String serialize(Panel panel) {
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                panel.getPanelId(),
                clean(panel.getSection()),
                panel.getRow(),
                panel.getColumn(),
                panel.getYearInstalled(),
                panel.getMaterial(),
                panel.isTracking());



    }

    private Panel deserialize(String line) {
        String[] fields = line.split(DELIMITER, -1);
        if (fields.length == 7) {
            Panel panel = new Panel();
            panel.setPanelId(Integer.parseInt(fields[0]));
            panel.setSection(restore(fields[1]));
            panel.setRow(Integer.parseInt(fields[2]));
            panel.setColumn(Integer.parseInt(fields[3]));
            panel.setYearInstalled(Integer.parseInt(fields[4]));




            panel.setMaterial(Material.valueOf(fields[5]));


            panel.setTracking(Boolean.parseBoolean(fields[6]));
            return panel;
        }
        return null;
    }



    private String clean(String value) {
        return value.replace(DELIMITER, DELIMITER_REPLACEMENT);
    }

    private String restore(String value) {
        return value.replace(DELIMITER_REPLACEMENT, DELIMITER);
    }
}
