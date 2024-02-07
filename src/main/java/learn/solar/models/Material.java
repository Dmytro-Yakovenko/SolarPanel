package learn.solar.models;

public enum Material {

    MULTICRYSTALLINE_SILLICON("Multicrystalline silicon", "PolySi"),

    MONOCRYSTALLINE_SILLICON("Monocrystalline silicon", "MonoSi"),
    AMORPHOUS_SILLICON("Amorphous silicon", "GIGS"),
    CADMIUM_TELLURIDE("Cadmium telluride", "CdTe"),

    COPPER_INDIUM_GALLIUM_SELENIDE("Copper indium gallium selenide", "CIGS");



    private String message;

    private String abbreviation;

    Material(String name, String abbreviation) {

        this.message = name;
        this.abbreviation= abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getMessage() {
        return message;
    }

}




