package learn.solar.domain;
import learn.solar.data.DataException;
import learn.solar.data.PanelRepositoryTestDouble;


import learn.solar.models.Material;
import learn.solar.models.Panel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PanelServiceTest {

    PanelService service = new PanelService(new PanelRepositoryTestDouble());

    @Test
    void shouldNotAddNull() throws DataException {
        PanelResult expected = makeResult("panel cannot be null");
        PanelResult actual = service.add(null);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddEmptySection() throws DataException {
        Panel panel = new Panel(3,"",3 ,3, 2018, Material.COPPER_INDIUM_GALLIUM_SELENIDE, true);
        PanelResult expected = makeResult("Section is required");
        PanelResult actual = service.add(panel);
        assertEquals(expected, actual);
    }


    @Test
    void shouldNotInRangeRow() throws DataException {
        Panel panel = new Panel(3,"Lower Hill",-5 ,2, 2018, Material.COPPER_INDIUM_GALLIUM_SELENIDE, true);
        PanelResult expected = makeResult("Row is a positive number less than or equal to 250.");
        PanelResult actual = service.add(panel);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddNullColumn() throws DataException {
        Panel panel = new Panel(3,"Main",2 ,255, 2018, Material.CADMIUM_TELLURIDE, true);
        PanelResult expected = makeResult("Column is a positive number less than or equal to 250.");
        PanelResult actual = service.add(panel);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddYearInstalledInFuture() throws DataException {
        Panel panel = new Panel(3,"Main",3 ,3, 2080, Material.CADMIUM_TELLURIDE, true);
        PanelResult expected = makeResult("Year Installed must be in the past");
        PanelResult actual = service.add(panel);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddNullMaterial() throws DataException {
        Panel panel = new Panel(1,"Main",3 ,3, 2020,null, true);
        PanelResult expected = makeResult("Material is required");
        PanelResult actual = service.add(panel);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() throws DataException {
        Panel panel = new Panel(1,"Main",3 ,3, 2000,Material.AMORPHOUS_SILLICON, true);
        PanelResult expected = new PanelResult();
        expected.setPayload(panel);

        PanelResult actual = service.add(panel);
        assertEquals(expected, actual);
    }

    private PanelResult makeResult(String message) {
        PanelResult result = new PanelResult();
        result.addErrorMessage(message);
        return result;
    }
    @Test
    void shouldNotUpdateNull() throws DataException {
        PanelResult expected = makeResult("panel cannot be null");
        PanelResult actual = service.update(null);
        assertEquals(expected, actual);
    }



    @Test
    void shouldNotUpdateEmptySection() throws DataException {
        Panel panel = new Panel(1,"",3 ,3, 2018, Material.COPPER_INDIUM_GALLIUM_SELENIDE, true);
        PanelResult expected = makeResult("Section is required");
        PanelResult actual = service.add(panel);
        assertEquals(expected, actual);
    }


    @Test
    void shouldUpdateNotInRangeRow() throws DataException {
        Panel panel = new Panel(2,"Lower Hill",-5 ,2, 2018, Material.COPPER_INDIUM_GALLIUM_SELENIDE, true);
        PanelResult expected = makeResult("Row is a positive number less than or equal to 250.");
        PanelResult actual = service.add(panel);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateNullColumn() throws DataException {
        Panel panel = new Panel(1,"Main",2 ,255, 2018, Material.CADMIUM_TELLURIDE, true);
        PanelResult expected = makeResult("Column is a positive number less than or equal to 250.");
        PanelResult actual = service.add(panel);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateYearInstalledInFuture() throws DataException {
        Panel panel = new Panel(1,"Main",3 ,3, 2080, Material.CADMIUM_TELLURIDE, true);
        PanelResult expected = makeResult("Year Installed must be in the past");
        PanelResult actual = service.add(panel);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotNullMaterial() throws DataException {
        Panel panel = new Panel(1,"Main",3 ,3, 2020,null, true);
        PanelResult expected = makeResult("Material is required");
        PanelResult actual = service.add(panel);
        assertEquals(expected, actual);
    }






    @Test
    void shouldNotUpdateDuplicate() throws DataException {

        Panel panel = service.findBySection("Main").get(0);
        Panel panel1 = service.findBySection("Lower Hill").get(0);
        panel.setPanelId(panel1.getPanelId());

        PanelResult expected = makeResult("encounter was duplicated");
        PanelResult actual = service.update(panel);
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdate() throws DataException {
        List<Panel> panels = service.findBySection("Main");
        Panel panel = panels.get(0);
        panel.setSection("Upper Hill");
        PanelResult expected = new PanelResult();
        expected.setPayload(panel);

        PanelResult actual = service.update(panel);
        assertEquals(expected, actual);
    }



}






