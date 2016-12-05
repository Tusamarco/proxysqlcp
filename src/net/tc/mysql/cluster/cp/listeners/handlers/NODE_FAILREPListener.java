package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.NodeFailReported;

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
public class NODE_FAILREPListener extends NodeFailReportedTypeListener {
    public NODE_FAILREPListener() {
    }

    public static void main(String[] args) {
        NODE_FAILREPListener node_failreplistener = new NODE_FAILREPListener();
    }

    @Override
    public void handleEvent(NodeFailReported event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb Node Fail Reported Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Failed Node: " + event.getFailedNode() +
                   " Failure State: " + event.getFailureState()

        );


    }

}
