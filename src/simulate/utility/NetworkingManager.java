package simulate.utility;

import simulate.object.Link;
import simulate.object.Router;

import java.util.HashMap;

/**
 * Created by deividas on 5/28/16.
 */
public class NetworkingManager {

    public void startNetworking(HashMap<Integer, Link> links, HashMap<Integer, Router> routers)
    {
        SendRouters send = new SendRouters(links, routers);
        send.start();
        ReceiveRouters receive = new ReceiveRouters(links, routers);
        receive.start();
    }

}
