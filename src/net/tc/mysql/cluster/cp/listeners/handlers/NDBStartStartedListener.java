package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.NdbStartStarted;

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
public class NDBStartStartedListener extends NdbStartStartedTypeListener {
    public NDBStartStartedListener() {
    }

    public static void main(String[] args) {
        NDBStartStartedListener ndbstartstartedlistener = new
                NDBStartStartedListener();
    }
    @Override
    public void handleEvent(NdbStartStarted event)
    {
        System.out.println(TimeTools.GetCurrentTime() +
                           " - Ndb Start Started:" + event.getVersion() +
                           " Node: "  + event.getSourceNodeId()
                );
    }

}
