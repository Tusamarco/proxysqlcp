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
public class MemoryElement extends ListnerEvent implements Checkable {
    private long blocks = 0;
    private double pageSizekb = 0;
    private double pageTotal = 0;
    private double pageUsed = 0;
    private double freeMemory = 0 ;
    private double totMemory = 0;
    private double freePerct = 0 ;

    public MemoryElement(long blocksIn, long pageSizekbIn,long pageTotalIn,long pageUsedIn) {

        this.blocks = blocksIn;
        this.pageSizekb = pageSizekbIn;
        this.pageTotal = pageTotalIn;
        this.pageUsed = pageUsedIn;

        try{
            if (this.blocks == 249) {
                this.freeMemory = ((this.pageTotal - this.pageUsed) * 32);
                this.totMemory = (this.pageTotal * 32);
            } else {
                this.freeMemory = ((this.pageTotal - this.pageUsed) * 8);
                this.totMemory = (this.pageTotal * 8);
            }
            this.freePerct = 100 - ((this.pageUsed / this.pageTotal) * 100);
        }
        catch(Exception ex)
        {

            ex.printStackTrace();
        }
    }

    public void setBlocks(long blocks) {
        this.blocks = blocks;
    }

    public void setPageSizekb(long pageSizekb) {
        this.pageSizekb = pageSizekb;
    }

    public void setPageTotal(long pageTotal) {
        this.pageTotal = pageTotal;
    }

    public void setPageUsed(long pageUsed) {
        this.pageUsed = pageUsed;
    }

    public long getBlocks() {
        return blocks;
    }

    public double getPageSizekb() {
        return pageSizekb;
    }

    public double getPageTotal() {
        return pageTotal;
    }

    public double getPageUsed() {
        return pageUsed;
    }

    public double getFreeMemoryMb()
    {

        return  freeMemory;

    }

    public double getTotMemoryMb()
    {

        return totMemory;

    }
    public double getFreeMemoryPrct()
    {

        return freePerct;

    }

    public Object getChekableValue() {
        return new Long(Double.doubleToLongBits(this.getFreeMemoryPrct()));
    }

}
