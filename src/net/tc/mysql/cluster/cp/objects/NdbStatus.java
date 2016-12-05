package net.tc.mysql.cluster.cp.objects;

import java.util.*;

/**
 * <p>Title: NDBJ / API</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Marco Tusa Copyright (c) 2008</p>
 *
 * <p>Company: MySQL</p>
 *
 * @author Marco Tusa
 * @version 1.0
 */
public class NdbStatus {
    private Map nodes;
    private boolean isRunning;
    private boolean isMonitored;
    private int state;

    public static int NDB_CLUSTER_STATE_OK = 0;
    public static int NDB_CLUSTER_STATE_DEGRADATED = 1;
    public static int NDB_CLUSTER_STATE_OFFLINE = 2;
    public static int NDB_CLUSTER_STATE_INIZIALIZING = 3;
    public static int NDB_CLUSTER_STATE_RECOVERING = 4;
    public static int NDB_CLUSTER_STATE_BACKUP = 5;
    private String connectedHost;
    private int connectedPort;
    private int lastErrorCode;
    private String lastErrorMsg;
    private String lastErrorDesc;


    public NdbStatus() {
    }

    public void setNodes(Map nodes) {
        this.nodes = nodes;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void setIsMonitored(boolean isMonitored) {
        this.isMonitored = isMonitored;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setConnectedHost(String connectedHost) {
        this.connectedHost = connectedHost;
    }

    public void setConnectedPort(int connectedPort) {
        this.connectedPort = connectedPort;
    }

    public void setLastErrorCode(int lastErrorCode) {
        this.lastErrorCode = lastErrorCode;
    }

    public void setLastErrorMsg(String lastErrorMsg) {
        this.lastErrorMsg = lastErrorMsg;
    }

    public void setLastErrorDesc(String lastErrorDesc) {
        this.lastErrorDesc = lastErrorDesc;
    }

    public Map getNodes() {
        return nodes;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isMonitored() {
        return isMonitored;
    }

    public int getState() {
        return state;
    }

    public String getConnectedHost() {
        return connectedHost;
    }

    public int getConnectedPort() {
        return connectedPort;
    }

    public int getLastErrorCode() {
        return lastErrorCode;
    }

    public String getLastErrorMsg() {
        return lastErrorMsg;
    }

    public String getLastErrorDesc() {
        return lastErrorDesc;
    }


}
