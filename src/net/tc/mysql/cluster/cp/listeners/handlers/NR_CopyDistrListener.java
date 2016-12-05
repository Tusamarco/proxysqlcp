package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.NRCopyDistr;

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
public class NR_CopyDistrListener extends NRCopyDistrTypeListener {
    public NR_CopyDistrListener() {
    }

    public static void main(String[] args) {
        NR_CopyDistrListener nr_copydistrlistener = new NR_CopyDistrListener();
    }

    @Override
    public void handleEvent(NRCopyDistr event) {

        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb NR Copy Distr Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event)

        );

    }

}
