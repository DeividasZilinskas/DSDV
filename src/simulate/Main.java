package simulate;

import simulate.object.Link;
import simulate.object.Router;
import simulate.utility.FileManager;
import simulate.utility.NetworkingManager;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private HashMap<Integer, Link> links = new HashMap<>();
    private HashMap<Integer, Router> routers = new HashMap<>();

    public Main(String[] args) {
        //Atempting to import data
        if(args.length > 0) {
            links = FileManager.getInstance().importCsv(args[0]);
            System.out.println("Reading from file: " + args[0]);
        } else {
            links = FileManager.getInstance().importCsv("a.csv");
        }
        //Building routing table
        for (Map.Entry<Integer, Link> linkEntry : links.entrySet()) {
            Link link = linkEntry.getValue();
            routers.put(routers.size() + 1, new Router(link.getPort(), link.getName(), link.getPort(), link.getName(), 0, link.getDistance()));
            System.out.println(routers.get(routers.size()).toString());
        }
        //Start sending routing table
        NetworkingManager network = new NetworkingManager();
        network.startNetworking(links, routers);
    }

    public static void main(String[] args) {
        Main main = new Main(args);
    }
}
