package simulate.utility;

import simulate.object.Link;
import simulate.object.Router;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by deividas on 5/28/16.
 */
public class ReceiveRouters implements Runnable {

    private Thread receive;
    private HashMap<Integer, Link> links;
    private HashMap<Integer, Router> routers;
    private HashMap<Integer, Date> updates = new HashMap<>();
    private OutputManager output = new OutputManager();

    public ReceiveRouters(HashMap<Integer, Link> links, HashMap<Integer, Router> routers) {
        this.links = links;
        this.routers = routers;
    }

    @Override
    public void run() {
        while(true) {
            try {
                DatagramSocket clientSocket;
                clientSocket = new DatagramSocket(links.get(1).getPort());
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                ByteArrayInputStream baos2 = new ByteArrayInputStream(receiveData);
                ObjectInputStream oos2 = new ObjectInputStream(baos2);
                HashMap<Integer, Router> receivedRouters = (HashMap<Integer, Router>) oos2.readObject();
                handleResponse(receivedRouters);
                clientSocket.close();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleResponse(HashMap<Integer, Router> receivedRouters)
    {
        Router sender = receivedRouters.get(1);
        HashMap<Integer, Router> routersCopy = routers;
        for (Map.Entry<Integer, Router> routerEntry : routers.entrySet()) {
            if(sender.getDestPort() == routerEntry.getValue().getDestPort()) {
                sender = routerEntry.getValue();
                updates.put(sender.getDestPort(), new Date());
                break;
            }
        }
        sender.setSeqNumber(receivedRouters.get(1).getSeqNumber() + 1);
        for (Map.Entry<Integer, Router> routerEntry : receivedRouters.entrySet()) {
            if(routerEntry.getKey() == 1)
                continue;
            boolean found = false;
            for (Map.Entry<Integer, Router> routerEntry2 : routers.entrySet()) {
                if(routerEntry.getValue().getDestPort() == routerEntry2.getValue().getDestPort()) {
                    found = true;
                    if(routerEntry.getValue().getSeqNumber() > routerEntry2.getValue().getSeqNumber())
                    {
                        routerEntry2.getValue().setSeqNumber(routerEntry.getValue().getSeqNumber());
                    }
                    if(sender.getDistance()+routerEntry.getValue().getDistance() < routerEntry2.getValue().getDistance())
                    {
                        routerEntry2.getValue().setDistance(sender.getDistance()+routerEntry.getValue().getDistance());
                    }
                    break;
                }
            }
            if(!found) {
                routersCopy.put(routersCopy.size()+1, routerEntry.getValue());
                Router newRouter =routersCopy.get(routersCopy.size());
                newRouter.setDistance(newRouter.getDistance()+sender.getDistance());
                System.out.println("New router connected on port: " + routerEntry.getValue().getDestPort());
            }
        }
        Date date = new Date();
        for (Map.Entry<Integer, Date> updatesEntry : updates.entrySet()) {
            int port = updatesEntry.getKey();
            Date updateDate = updatesEntry.getValue();
            if(updateDate.getTime() + 10000 < date.getTime()) {
                for (Map.Entry<Integer, Router> routerEntry : routers.entrySet()) {
                    Router router = routerEntry.getValue();
                    if(router.getDestPort() == port) {
                        router.setDistance(-1);
                        if(router.getSeqNumber() % 2 == 0) {
                            router.setSeqNumber(router.getSeqNumber() + 1);
                        }
                    }
                }
            }
        }
        routers = routersCopy;
        printTable();
        sender.setSeqNumber(sender.getSeqNumber() + 1);
    }

    public void printTable()
    {
        output.setRouters(routers);
        output.start();
        /*System.out.println("##Routing table");
        for (Map.Entry<Integer, Router> routerEntry : routers.entrySet()) {
            Router router = routerEntry.getValue();
            System.out.println(router.getDestName() +": "+ router.getDestPort() + " distance: " + router.getDistance() +
            " seqNumber: " + router.getSeqNumber());
        }*/
    }

    public void start ()
    {
        System.out.println("Starting receiving thread");
        if (receive == null)
        {
            receive = new Thread (this, "receive");
            receive.start ();
        }
    }
}
