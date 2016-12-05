package net.tc.mysql.cluster.cp.objects;

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
public class Node {
    private String address;
    private int connetedCount;
    private int dynamicID;
    private int nodeGroup;
    private String status;
    private String type;
    private int startPhase;
    private long CPtr;
    private String version;
    private int ID;
    private MemoryElement memoryUsage;
    private String apiVersion;
    private MemoryElement memoryUsageIndex;
    private boolean modified = false;
    private int missedHBcounter = 0;
    private long lastMissedHB = 0 ;
    private long byteSent = 0;
    private long byteReceived = 0;
    private long transCount = 0;
    private long opCount = 0;

    Node(
            int IDIn,
            String addressIn,
            int connetedCountIn,
            int dynamicIDIn,
            int nodeGroupIn,
            String statusIn,
            String typeIn,
            int startPhaseIn,
            String versionIn

            )

    {

        ID = IDIn;
        address = addressIn;
        connetedCount = connetedCountIn;
        dynamicID = dynamicIDIn;
        nodeGroup = nodeGroupIn;
        status = statusIn;
        type = typeIn;
        startPhase = startPhaseIn;
//        CPtr = CPtrIn;
        version = versionIn;
    }

    public int getId()
    {
        return ID;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public void setConnetedCount(int connetedCount) {
        this.connetedCount = connetedCount;
    }

    public void setDynamicID(int dynamicID) {
        this.dynamicID = dynamicID;
    }

    public void setMemoryUsage(MemoryElement memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public void setMemoryUsageIndex(MemoryElement memoryUsageIndex) {
        this.memoryUsageIndex = memoryUsageIndex;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public void setMissedHBcounter(int missedHBcounter) {
        this.missedHBcounter = missedHBcounter;
    }

    public void setLastMissedHB(long lastMissedHB) {
        this.lastMissedHB = lastMissedHB;
    }

    public void setByteSent(long byteSent) {
        this.byteSent = byteSent;
    }

    public void setByteReceived(long byteReceived) {
        this.byteReceived = byteReceived;
    }

    public void setTransCount(long transCount) {
        this.transCount = transCount;
    }

    public void setOpCount(long opCount) {
        this.opCount = opCount;
    }

    public String getAddress() {
        return address;
    }

    public int getConnetedCount() {
        return connetedCount;
    }

    public int getDynamicID() {
        return dynamicID;
    }

    public int getNodeGroup() {
        return nodeGroup;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public int getStartPhase() {
        return startPhase;
    }

    public String getVersion() {
        return version;
    }

    public MemoryElement getMemoryUsage() {
        return memoryUsage;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public MemoryElement getMemoryUsageIndex() {
        return memoryUsageIndex;
    }

    public boolean isModified() {
        return modified;
    }

    public int getMissedHBcounter() {
        return missedHBcounter;
    }

    public long getLastMissedHB() {
        return lastMissedHB;
    }

    public long getByteSent() {
        return byteSent;
    }

    public long getByteReceived() {
        return byteReceived;
    }

    public long getCPtr() {
        return CPtr;
    }

    public long getOpCount() {
        return opCount;
    }

    public long getTransCount() {
        return transCount;
    }
}
