package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.NRCopyFragsStarted;

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
public class NR_CopyFragsStartedListener extends NRCopyFragsStartedTypeListener {
    public NR_CopyFragsStartedListener() {
    }

    public static void main(String[] args) {
        NR_CopyFragsStartedListener nr_copyfragsstartedlistener = new
                NR_CopyFragsStartedListener();
    }

    @Override
    public void handleEvent(NRCopyFragsStarted event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb NR Copy Frags Started Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Destination Node: " + event.getDestNode()

        );



    }


}
