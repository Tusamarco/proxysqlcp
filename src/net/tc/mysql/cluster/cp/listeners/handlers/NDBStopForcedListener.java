package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.NdbStopForced;

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
public class NDBStopForcedListener extends NdbStopForcedTypeListener {
    public NDBStopForcedListener() {
    }

    public static void main(String[] args) {
        NDBStopForcedListener ndbstopforcedlistener = new NDBStopForcedListener();
    }


    @Override
    public void handleEvent(NdbStopForced event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb Stop Forced (Signal):" + event.getSignum() +
                   " Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Action: " + event.getAction() +
                   " Error: " + event.getError() +
                   " Extra: " + event.getExtra() +
                   " Sphase: " + event.getSphase()

        );


    }

}
