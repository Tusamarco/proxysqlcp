package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.NRCopyFragDone;

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
public class NR_CopyFragDoneListener extends NRCopyFragDoneTypeListener {
    public NR_CopyFragDoneListener() {
    }

    public static void main(String[] args) {
        NR_CopyFragDoneListener nr_copyfragdonelistener = new
                NR_CopyFragDoneListener();
    }

    @Override
    public void handleEvent(NRCopyFragDone event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb NR Copy Fragment Done Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Destination Node: " + event.getDestNode() +
                   " Fragment Id: " + event.getFragmentId() +
                   " Table Id: " + event.getTableId()

        );


    }

}
