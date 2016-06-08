package simulate.utility;

import simulate.object.Link;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by deividas on 5/28/16.
 * This is CSV file parser.
 */
public class FileManager {

    private static final FileManager instance = new FileManager();

    public FileManager() {
        super();
    }

    public HashMap<Integer, Link> importCsv(String CSV_FILE) {
        HashMap<Integer, Link> links = new HashMap<>();
        BufferedReader br;
        String line;
        try {
            br = new BufferedReader(new FileReader(CSV_FILE));
            while ((line = br.readLine()) != null) {

                String[] car = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                try {
                    car = removeQuotes(car);
                    System.out.println(links.size()+1);
                    links.put(links.size()+1, new Link(car[0], Integer.parseInt(car[1]), Integer.parseInt(car[2])));
                } catch (NumberFormatException e) {
                    System.out.println("Line read failed.");
                }
            }
            System.out.println("Read from CSV file was successfull!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("CSV file not found!");
        } catch (IOException e) {
            System.out.println("Reading from CSV file failed!");
        } catch (Exception e) {
                System.out.println("Something went wrong while reading from CSV file.");
        }
        return links;
    }

    private String[] removeQuotes(String[] car) {
        String carName = car[1];
        if(carName.startsWith("\"") && carName.endsWith("\""))
        {
            car[1] = carName.substring(1, carName.length() - 1);
        }
        car[1] = removeDoubleQuotes(car[1]);
        return car;
    }

    private String removeDoubleQuotes(String carName) {
        for (int i = 0; i < carName.length(); ++i) {
            if (carName.charAt(i) == '"' && carName.charAt(i + 1) == '"') {
                carName = carName.substring(0, i) + carName.substring(i + 1, carName.length());
            }
        }
        return carName;
    }

    public static FileManager getInstance() {
        return instance;
    }
}
