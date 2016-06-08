package simulate.object;

import java.io.Serializable;

/**
 * Created by deividas on 5/28/16.
 */
public class Router implements Serializable {

    private int destPort;
    private String destName;
    private int bypassPort;
    private String bypassName;
    private int seqNumber;
    private int distance;

    public Router(int destPort, String destName, int bypassPort, String bypassName, int seqNumber, int distance) {
        this.destPort = destPort;
        this.destName = destName;
        this.bypassPort = bypassPort;
        this.bypassName = bypassName;
        this.seqNumber = seqNumber;
        this.distance = distance;
    }

    public int getDestPort() {
        return destPort;
    }

    public void setDestPort(int destPort) {
        this.destPort = destPort;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    public int getBypassPort() {
        return bypassPort;
    }

    public void setBypassPort(int bypassPort) {
        this.bypassPort = bypassPort;
    }

    public String getBypassName() {
        return bypassName;
    }

    public void setBypassName(String bypassName) {
        this.bypassName = bypassName;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Router{" +
                "destPort=" + destPort +
                ", destName='" + destName + '\'' +
                ", bypassPort=" + bypassPort +
                ", bypassName='" + bypassName + '\'' +
                ", seqNumber=" + seqNumber +
                ", distance=" + distance +
                '}';
    }
}
