package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.NdbStopAborted;

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
public class NDBStopAbortedListener extends NdbStopAbortedTypeListener {
    public NDBStopAbortedListener() {
    }

    public static void main(String[] args) {
        NDBStopAbortedListener ndbstopabortedlistener = new
                NDBStopAbortedListener();
    }

    @Override
    public void handleEvent(NdbStopAborted event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb Stop ABorted Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event)

        );


    }

}
