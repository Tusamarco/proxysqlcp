package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.InfoEvent;

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
public class InfoEventListener extends InfoEventTypeListener {
    public InfoEventListener() {
    }

    public static void main(String[] args) {
        InfoEventListener infoeventlistener = new InfoEventListener();
    }

    @Override
    public void handleEvent(InfoEvent event) {

        System.out.println(TimeTools.GetCurrentTime() +
           " - Ndb InfoEvent Node: "  + event.getSourceNodeId()   +
           " CPtr: " + event.getCPtr(event)
        );


    }

}
