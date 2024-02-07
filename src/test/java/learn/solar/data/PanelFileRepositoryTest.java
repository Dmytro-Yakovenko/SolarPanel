package learn.solar.data;

import learn.solar.models.Material;
import learn.solar.models.Panel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PanelFileRepositoryTest {


    static final String TEST_PATH = "./data/panels-test.csv";
    final Panel[] testPanels = new Panel[]{
            new Panel(1, "Main", 1, 1, 2024, Material.MULTICRYSTALLINE_SILLICON, true),
            new Panel(2, "Upper Hill", 2, 2, 2023, Material.CADMIUM_TELLURIDE, true),
            new Panel(3, "Lower Hill", 3, 3, 2022, Material.MONOCRYSTALLINE_SILLICON, false)
    };

    PanelRepository repository = new PanelFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws DataException {
        for (Panel p : repository.findAll()) {
            repository.deleteById(p.getPanelId());
        }

        for (Panel p : testPanels) {
            repository.add(p);
        }
    }

    @Test
    void shouldFindAll() throws DataException {
        List<Panel> panels = repository.findAll();
        Panel[] actual = panels.toArray(new Panel[panels.size()]);
        assertArrayEquals(testPanels, actual);

    }

    @Test
    void findBySection() throws DataException {
        List<Panel> panels = repository.findBySection("Main");

        assertEquals(panels.size(), 1);
    }


    @Test
    void findPanelShouldFound() throws DataException {
        Panel panel = repository.findPanel("Main", 1,1);
        assertNotNull(panel);
        assertEquals(panel.getMaterial(), Material.MULTICRYSTALLINE_SILLICON);
    }

    @Test
    void findPanelShouldNotFound() throws DataException {
        Panel panel = repository.findPanel("Main", 5,5);
        assertNull(panel);

    }



    @Test
    void update() throws DataException {
        List<Panel> panels =repository.findAll();
        Panel panel = panels.get(0);
        panel.setSection("test complete");
        assertTrue(repository.update(panel));
        panel=repository.findAll().get(0);
        assertEquals(panel.getSection() ,"test complete");
        Panel notPresentPanel = new Panel(1000,"Main", 2, 2,2020, Material.CADMIUM_TELLURIDE, true );
        assertFalse(repository.update(notPresentPanel));

    }

    @Test
    void deleteById() throws DataException {
        List<Panel> panels =repository.findBySection("Main");
        Panel panel = panels.get(0);

        assertTrue(repository.deleteById(panel.getPanelId()));
        int index =((PanelFileRepository)repository).findIndexById(panel.getPanelId(),repository.findBySection("Main"));
        assertEquals(index, -1);


        assertFalse(repository.deleteById(1000));

    }




    @Test
    void shouldAdd() throws DataException {
        Panel panel = new Panel();
        panel.setSection("Lower Hill ");
        panel.setRow(3);
        panel.setColumn(3);
        panel.setYearInstalled(2020);
        panel.setMaterial(Material.AMORPHOUS_SILLICON);
        panel.setTracking(false);


        Panel actual = repository.add(panel);

        assertNotNull(actual);
        assertEquals(4, actual.getPanelId());
    }

}
