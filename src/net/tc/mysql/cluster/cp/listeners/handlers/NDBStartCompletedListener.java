package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.NdbStartCompleted;

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
public class NDBStartCompletedListener extends NdbStartCompletedTypeListener {
    public NDBStartCompletedListener() {
    }

    public static void main(String[] args) {
        NDBStartCompletedListener ndbstartcompletedlistener = new
                NDBStartCompletedListener();
    }

    @Override
    public void handleEvent(NdbStartCompleted event) {
        System.out.println(TimeTools.GetCurrentTime() +
                           " - Ndb Start Completed:" + event.getVersion() + " Node: "  + event.getSourceNodeId());


  }


}
