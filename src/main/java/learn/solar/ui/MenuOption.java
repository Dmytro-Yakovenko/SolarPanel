package learn.solar.ui;

public enum MenuOption {
    EXIT("Exit"),
//    DISPLAY_ALL("Display All Panels"),


    ADD("Add An Panel"),

    DISPLAY_BY_SECTION("Display Panels By Section"),

    UPDATE("Update A Panel"),

    DELETE ("Delete A Panel");



    private String message;

    MenuOption(String name) {
        this.message = name;
    }

    public String getMessage() {
        return message;
    }
}
