package learn.solar.models;

import java.util.Objects;

public class Panel {
    private int panelId;
    private String section;
    private int row;
    private int column;

    int yearInstalled;

    private Material material;

    private boolean isTracking;


    public Panel() {
    }

    public Panel(int id, String section, int row, int column, int yearInstalled, Material material, boolean isTracking) {
        this.panelId = id;
        this.section = section;
        this.row = row;
        this.column = column;
        this.yearInstalled = yearInstalled;
        this.material = material;
        this.isTracking = isTracking;
    }

    public int getPanelId() {
        return panelId;
    }

    public void setPanelId(int panelId) {
        this.panelId = panelId;
    }

    public String getSection() {
        return section;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getYearInstalled() {
        return yearInstalled;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isTracking() {
        return isTracking;
    }


    public void setSection(String section) {
        this.section = section;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setYearInstalled(int yearInstalled) {
        this.yearInstalled = yearInstalled;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setTracking(boolean tracking) {
        isTracking = tracking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Panel panel = (Panel) o;
        return panelId == panel.panelId &&
                row  == panel.row &&
                column == panel.column &&
                yearInstalled==panel.yearInstalled &&
                material== panel.material &&
                isTracking == panel.isTracking() &&
                Objects.equals(section, panel.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(panelId, section, row, column, yearInstalled, material);
    }

}

