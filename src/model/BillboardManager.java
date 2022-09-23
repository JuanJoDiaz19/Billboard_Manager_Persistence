package model;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class BillboardManager {
    private ArrayList<Billboard> bilbords;
    public BillboardManager() {
        bilbords = new ArrayList<Billboard>();
        loadDataBase();
    }
    public void loadDataBase() {
        try {
            File file = new File("DataBase.json");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String json = "";
            String line;
            while ((line = reader.readLine()) != null) {
                json += line;
            }
            if(!json.equals("")) {
                Gson gson = new Gson();
                Billboard[] bills = gson.fromJson(json, Billboard[].class);
                bilbords.addAll(Arrays.asList(bills));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadExternalData(String path) {
        File archivo = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(archivo);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                String parts[] = line.split("\\|");
                Billboard p = new Billboard(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Boolean.parseBoolean(parts[2]), parts[3]);
                bilbords.add(p);
            }
            fis.close();
            /*System.out.println("Tamaño del arreglo: " + people.size());
            System.out.println(people.get(2).name);*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        automaticSave();
    }

    public String showBillboards() {
        if (bilbords.size() == 0) {
            return "No hay billboards en el sistema :(";
        }
        String out = "W\tH\tinUse\tBrand\n";
        for(Billboard b: bilbords) {
            out += b.getWidth() + "\t" + b.getHeight() + "\t" + b.isInUse() + "\t"+ b.getBrand() + "\n";
        }
        return out;
    }

    public void automaticSave(){
        Gson gson = new Gson();
        String json = gson.toJson(bilbords);
        //System.out.println(json);
        try {
            FileOutputStream fos = new FileOutputStream(new File("DataBase.json"));
            fos.write(json.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addBillboard(Billboard b) {
        bilbords.add(b);
        automaticSave();
    }

    public void exportHazardReport() {
        String out = "===========================\n" +
                "DANGEROUS BILLBOARD REPORT\n" +
                "===========================\n" +
                "The dangerous billboard are:\n";
        int cont = 0;
        ArrayList<Billboard> hazardBilboards = new ArrayList<>();
        for(Billboard b: bilbords) {
            int area = b.getWidth() * b.getHeight();
            if(area >=200000) {
                out += cont + ". Billboard <" + b.getBrand() + "> with area <" + area+">" + "\n";
            }
            cont++;
        }
        File file = new File("report.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(out.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
