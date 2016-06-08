package simulate.object;

/**
 * Created by deividas on 5/28/16.
 */
public class Link {

    private String name;
    private int distance;
    private int port;

    public Link(String name, int distance, int port) {
        this.name = name;
        this.distance = distance;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Link{" +
                "name='" + name + '\'' +
                ", distance=" + distance +
                ", port=" + port +
                '}';
    }
}
