package learn.solar.domain;

import learn.solar.data.DataException;
import learn.solar.data.PanelRepository;
import learn.solar.models.Panel;

import java.util.List;
import java.util.Objects;
import java.time.LocalDate;



public class PanelService {

    private PanelRepository repository;

    public PanelService(PanelRepository repository) {
        this.repository = repository;
    }


    public List<Panel> findBySection(String section) throws DataException {
        return repository.findBySection(section);
    }

    public Panel  findPanel(String section,int row,int  column) throws  DataException {
        return repository.findPanel(section, row, column);
    }


    public PanelResult add(Panel panel) throws DataException{
        PanelResult result = validate(panel);
        if (!result.isSuccess()) {
            return result;
        }

        // check for duplicate

        if(findPanel(panel.getSection(), panel.getRow(), panel.getColumn())!=null){
            result.addErrorMessage("duplicate panels is not allowed");
            return result;
        }

        panel = repository.add(panel);
        result.setPayload(panel);
        return result;
    }



    public PanelResult update(Panel panel) throws DataException{
        PanelResult result = validate(panel);
        if(!result.isSuccess()){
            return result;
        }
        if( panel.getPanelId()<=0){
            result.addErrorMessage(" Panel `id` is required.");
        }
        List<Panel> panels = repository.findAll();
        for(Panel p: panels){
            if (p.getSection() == panel.getSection() &&
                    p.getRow()==panel.getRow() &&
                    p.getColumn()==panel.getColumn() &&
                    p.getPanelId() != panel.getPanelId()) {
                result.addErrorMessage("panel was duplicated");
            }
        }

        if(!result.isSuccess()){
            return result;
        }

        if(!repository.update(panel)){
            result.addErrorMessage("Panel was not found");
        }else{
            result.setPayload(panel);
        }
        return result;


    }

    public PanelResult deleteById(int id) throws DataException {
        PanelResult result = new PanelResult();
        if(!repository.deleteById(id)){
            String message  = String.format ("Encounter id %s was not found.", id);
            result.addErrorMessage(message);
        }

        return result;
    }


    private PanelResult validate(Panel panel) {

        PanelResult result = new PanelResult();
        if (panel == null) {
            result.addErrorMessage("panel cannot be null");
            return result;
        }

        if (panel.getSection() == null || panel.getSection().trim().length() == 0) {
            result.addErrorMessage("Section is required");
        }

        if (panel.getRow() <=0 || panel.getRow()>250) {
            result.addErrorMessage("Row is a positive number less than or equal to 250.");
        }

        if (panel.getColumn() <=0 || panel.getColumn()>250) {
            result.addErrorMessage("Column is a positive number less than or equal to 250.");
        }

        if (panel.getYearInstalled() > LocalDate.now().getYear() ) {
            result.addErrorMessage("Year Installed must be in the past");
        }

        if (panel.getMaterial() == null ) {
            result.addErrorMessage("Material is required");
        }

        return result;
    }
}
