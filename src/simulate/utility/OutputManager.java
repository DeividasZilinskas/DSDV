package simulate.utility;

import simulate.object.Router;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by deividas on 5/28/16.
 * This class prints routing table to console every second.
 */
public class OutputManager implements Runnable {

    private Thread print;
    private HashMap<Integer, Router> routers;

    public HashMap<Integer, Router> getRouters() {
        return routers;
    }

    public void setRouters(HashMap<Integer, Router> routers) {
        this.routers = routers;
    }

    @Override
    public void run() {
        while (true) {
            printTable();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printTable()
    {
        System.out.println("##Routing table");
        for (Map.Entry<Integer, Router> routerEntry : routers.entrySet()) {
            Router router = routerEntry.getValue();
            System.out.println(router.getDestName() +": "+ router.getDestPort() + " distance: " +
                    (router.getSeqNumber() % 2 != 0 ? "infinity" : router.getDistance()) + " seqNumber: " +
                    router.getSeqNumber());
        }
    }

    public void start ()
    {
        if (print == null)
        {
            System.out.println("Starting printing thread");
            print = new Thread (this, "print");
            print.start ();
        }
    }
}
