package learn.solar.ui;

import learn.solar.domain.PanelResult;
import learn.solar.models.Material;
import learn.solar.models.Panel;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class View {

    private Scanner console = new Scanner(System.in);



    public void printHeader(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }


    public  String readString(String message){
        System.out.println(message);
        return console.nextLine();
    }

    public String readRequiredString(String message){
        String result;
        do {
            result = readString(message);
            if (result.trim().length() == 0) {
                System.out.println("Value is required.");
            }
        }while(result.trim().length()==0);
        return  result;



    }

    public String readRequiredString(String message, String def){
        String result;
        result = readString(message+"("+def+")");
        if (result.trim().length() == 0) {
            return def;
        }
        return  result;
    }





    public int readInt(String message) {
        String input = null;
        int result = 0;
        boolean isValid = false;
        while (true){
            try {
                input = readRequiredString(message);
                result = Integer.parseInt(input);
                return result;
            } catch (NumberFormatException ex) {
                System.out.printf("%s is not a valid number.%n", input);
            }
        }
    }


    public int readInt(String message, int min, int max) {
        int result;
        do {
            result = readInt(message);
            if (result < min || result > max) {
                System.out.printf("Value must be between %s and %s.%n", min, max);
            }
        } while (result < min || result > max);
        return result;
    }


    public int readInt(String message, int min, int max, int def) {

        String input = null;
        int result = 0;
        boolean isValid = false;
        while (true){
            try {
                input = readString(message+"("+def+"):");
                if(input.length()==0){
                    return def;
                }
                result = Integer.parseInt(input);
                if (result < min || result > max) {
                    System.out.printf("Value must be between %s and %s.%n", min, max);
                    continue;
                }
                return result;
            } catch (NumberFormatException ex) {
                System.out.printf("%s is not a valid number.%n", input);
            }
        }
    }


    public MenuOption displayMenuGetOption() {
        printHeader("Main Menu");

        MenuOption[] options = MenuOption.values();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%s. %s%n", i + 1, options[i].getMessage());
        }

        String msg = String.format("Select [%s-%s]:", 1, options.length);
        int value = readInt(msg, 1, options.length);
        return options[value - 1];
    }
    public Panel makePanel() {
        printHeader(MenuOption.ADD.getMessage());
        Panel panel = new Panel();
        panel.setSection(readRequiredString("Section :"));
        panel.setRow(readInt("Row:",1,250));
        panel.setColumn(readInt("Column:",1,250));
        panel.setMaterial(readMaterial("Material:"));
        panel.setYearInstalled(readInt("Installation Year:", 1980, LocalDate.now().getYear() ));
        panel.setTracking(readBoolean("Tracked [y/n]:"));
        return panel;
    }

    private Material readMaterial(String message){
        printHeader(message);

        Material[] material = Material.values();
        for (int i = 0; i < material.length; i++) {
            System.out.printf("%s. %s%n", i + 1, material[i].getMessage());
        }

        String msg = String.format("Select [%s-%s]:", 1, material.length);
        int value = readInt(msg, 1, material.length);
        return material[value - 1];
    }


    private Material readMaterial(String message, Material def){
        printHeader(message+"("+def.getMessage()+")");

        Material[] material = Material.values();
        int defInt=-1;
        for (int i = 0; i < material.length; i++) {
            System.out.printf("%s. %s%n", i + 1, material[i].getMessage());
            if(def==material[i]){
                defInt=i+1;
            }
        }

        String msg = String.format("Select [%s-%s]:", 1, material.length);
        int value = readInt(msg, 1, material.length,defInt);
        return material[value - 1];
    }


    private Boolean readBoolean(String message){
        System.out.println(message);
        String input = console.nextLine();
        if(input.toLowerCase().trim().equals("y") || input.toLowerCase().trim().equals("yes") ){
            return true;
        }else{
            return false;
        }
    }

    private Boolean readBoolean(String message, Boolean def){
        System.out.println(message+" ("+boolToYorN(def)+" ):");
        String input = console.nextLine();
        if(input.length()==0){
            return def;
        }
        if(input.toLowerCase().trim().equals("y") || input.toLowerCase().trim().equals("yes") ){
            return true;
        }else{
            return false;
        }
    }




    public void printResult(PanelResult result) {
        if (result.isSuccess()) {
            if (result.getPayload() != null) {
                System.out.printf("Panel Id %s added.%n", result.getPayload().getPanelId());
            }
        } else {
            printHeader("Errors");
            for (String msg : result.getMessages()) {
                System.out.printf("- %s%n", msg);
            }
        }
    }

    public void printPanels(List<Panel> panels,String section){
        System.out.println("Panels in The "+section);
        System.out.println("Row Col Year Material Tracking");

        for(int i=0; i< panels.size(); i++){
            printPanel(panels.get(i));
        }
    }


    public void printPanel(Panel panel){
        System.out.printf("%3d %3d %4d %8s %8s\n",panel.getRow(), panel.getColumn(), panel.getYearInstalled(), panel.getMaterial().getAbbreviation(), boolToYorN(panel.isTracking()));
    }

    private String boolToYorN(boolean bool){
        if(bool){
            return "yes";
        }else{
            return "no";
        }
    }


    public void displayMessage(String message) {
        System.out.println(message);
    }


    public Panel edit(Panel panel){
        System.out.printf("Editing %s-%d-%d", panel.getSection(), panel.getRow(), panel.getColumn());
        System.out.println("Press [Enter] to keep original value.");

        panel.setSection(readRequiredString("Section ", panel.getSection()));

        panel.setRow(readInt("Row ",1,250,panel.getRow()));

        panel.setColumn(readInt("Row ",1,250,panel.getColumn()));

        panel.setMaterial(readMaterial("Material",panel.getMaterial()));


        panel.setYearInstalled(readInt("Year Installed ",1,250,panel.getYearInstalled()));

       panel.setTracking(readBoolean("Tracked",panel.isTracking()));

        return panel;
    }
}
