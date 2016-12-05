package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.ArbitResult;

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
public class ArbitResultListener extends ArbitResultTypeListener {
    public ArbitResultListener() {
    }

    public static void main(String[] args) {
        ArbitResultListener arbitresultlistener = new ArbitResultListener();
    }

    @Override
    public void handleEvent(ArbitResult event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb Arbit Result Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Arbit Node: " + event.getArbitNode() +
                   " Code: " + event.getCode()

        );

    }

}
