package simulate.utility;

import simulate.object.Link;
import simulate.object.Router;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by deividas on 5/28/16.
 * This class keeps sending router table to other routers
 */
public class SendRouters implements Runnable {

    private Thread send;
    private HashMap<Integer, Link> links;
    private HashMap<Integer, Router> routers;

    public SendRouters(HashMap<Integer, Link> links, HashMap<Integer, Router> routers) {
        this.links = links;
        this.routers = routers;
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                DatagramSocket clientSocket;
                clientSocket = new DatagramSocket();
                InetAddress IPAddress = InetAddress.getByName("localhost");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(routers);
                oos.flush();
                byte[] Buf = baos.toByteArray();

                Router router = routers.get(1);
                if(router.getSeqNumber() % 2 == 0) {
                    router.setSeqNumber(router.getSeqNumber() + 1);
                }
                for (Map.Entry<Integer, Link> linkEntry : links.entrySet()) {
                    if(linkEntry.getKey() == 1)
                        continue;
                    Link link = linkEntry.getValue();
                    DatagramPacket sendPacket = new DatagramPacket(Buf, Buf.length, IPAddress, link.getPort());
                    clientSocket.send(sendPacket);
                }
                if(router.getSeqNumber() % 2 == 1) {
                    router.setSeqNumber(router.getSeqNumber() + 1);
                }
                clientSocket.close();
                Thread.sleep(1000);
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start ()
    {
        System.out.println("Starting sending thread");
        if (send == null)
        {
            send = new Thread (this, "send");
            send.start ();
        }
    }
}
