package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.LCPTakeoverCompleted;

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
public class LCP_TakeoverCompletedListener extends
        LCPTakeoverCompletedTypeListener {
    public LCP_TakeoverCompletedListener() {
    }

    public static void main(String[] args) {
        LCP_TakeoverCompletedListener lcp_takeovercompletedlistener = new
                LCP_TakeoverCompletedListener();
    }

    @Override
    public void handleEvent(LCPTakeoverCompleted event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb LCP Takeover Completed Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " State: " + event.getState()

        );


    }

}
