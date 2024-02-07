package learn.solar.data;
import learn.solar.models.*;

import java.util.List;

public interface PanelRepository {
    public List<Panel> findBySection(String section) throws DataException ;
    public Panel add(Panel panel) throws DataException ;
    public boolean update(Panel panel) throws DataException ;
    public boolean deleteById(int id) throws DataException ;
    public Panel findPanel(String section, int row, int column) throws DataException;
    public List<Panel> findAll() throws DataException;
}
