package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.NRCopyFragsCompleted;

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
public class NR_CopyFragsCompletedListener extends
        NRCopyFragsCompletedTypeListener {
    public NR_CopyFragsCompletedListener() {
    }

    public static void main(String[] args) {
        NR_CopyFragsCompletedListener nr_copyfragscompletedlistener = new
                NR_CopyFragsCompletedListener();
    }

    @Override
    public void handleEvent(NRCopyFragsCompleted event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb NR Copy Frags Completed Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Destination Node " + event.getDestNode()

        );


    }
}
