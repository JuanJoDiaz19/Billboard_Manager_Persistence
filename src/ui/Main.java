package ui;

import model.Billboard;
import model.BillboardManager;

import java.util.Scanner;

public class Main {
    private static BillboardManager billboardManager;
    private static Scanner sc;

    public static void main(String[] args) {
        billboardManager = new BillboardManager();
        sc = new Scanner(System.in);
        Main main =new Main();
        main.execute();
    }

    public void execute() {
        System.out.println("=================================\n"+
                         "||       BILLBOARD MANAGER      ||\n" +
                "=================================\n");
        showOptions();
    }

    public void showOptions() {
        System.out.println("Elija una de las opciones del menu: ");
        int option = -1;
        do {
            System.out.println();
            System.out.println("1. Importar datos CSV\n2. Agragar una valla publicitaria\n3. Mostrar vallas publicitarias\n4. Exportar un reporte de peligrosidad\n5. Salir");
            option = Integer.parseInt(sc.nextLine());
            executeOption(option);
        } while (option != 5);
    }

    public void executeOption(int option) {
        switch (option) {
            case 1:
                System.out.println("Porfavor ingrese la ruta de la que quiere cargar datos: ");
                String path = sc.nextLine();
                billboardManager.loadExternalData(path);
                break;
            case 2:
                System.out.println("Porfavor ingrese la informacion para crear un Billboard");
                String info = sc.nextLine();
                String parts[] = info.split("\\++");
                billboardManager.addBillboard(new Billboard(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Boolean.parseBoolean(parts[2]), parts[3]));
                break;
            case 3:
                System.out.println("Estas son las vallas publicitarias que estan en el sistema: ");
                System.out.println(billboardManager.showBillboards());
                break;
            case 4:
                billboardManager.exportHazardReport();
                System.out.println("Se ha generado el reporte correctamente :)");
                break;
            case 5:
                System.out.println("Bye :)))");
                break;
        }
    }
}


