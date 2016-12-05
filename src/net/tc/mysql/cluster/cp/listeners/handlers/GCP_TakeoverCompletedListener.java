package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.GCPTakeoverCompleted;

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
public class GCP_TakeoverCompletedListener extends
        GCPTakeoverCompletedTypeListener {
    public GCP_TakeoverCompletedListener() {
    }

    public static void main(String[] args) {
        GCP_TakeoverCompletedListener gcp_takeovercompletedlistener = new
                GCP_TakeoverCompletedListener();
    }

    @Override
    public void handleEvent(GCPTakeoverCompleted event) {

        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb GCP Takeover Completed Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event)

        );

    }

}
