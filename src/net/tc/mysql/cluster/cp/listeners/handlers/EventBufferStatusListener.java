package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.EventBufferStatus;

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
public class EventBufferStatusListener extends EventBufferStatusTypeListener {
    public EventBufferStatusListener() {
    }

    public static void main(String[] args) {
        EventBufferStatusListener eventbufferstatuslistener = new
                EventBufferStatusListener();
    }

    @Override
    public void handleEvent(EventBufferStatus event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb EventBufferStatus Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Alloc: " + event.getAlloc() +
                   " Apply GciH: " + event.getApplyGciH() +
                   " Apply GciL: " + event.getApplyGciL() +
                   " Latest GciH: " + event.getLatestGciH() +
                   " Latest GciL: " + event.getLatestGciL() +
                   " Apply GciL: " + event.getApplyGciL()
        );

    }

}
