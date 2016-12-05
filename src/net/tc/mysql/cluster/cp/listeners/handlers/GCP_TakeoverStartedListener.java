package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.GCPTakeoverStarted;

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
public class GCP_TakeoverStartedListener extends GCPTakeoverStartedTypeListener {
    public GCP_TakeoverStartedListener() {
    }

    public static void main(String[] args) {
        GCP_TakeoverStartedListener gcp_takeoverstartedlistener = new
                GCP_TakeoverStartedListener();
    }

    @Override
    public void handleEvent(GCPTakeoverStarted event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb GCP Takeover Started Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event)
        );


    }

}
