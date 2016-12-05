package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.StartLog;

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
public class StartLogListener extends StartLogTypeListener {
    public StartLogListener() {
    }

    public static void main(String[] args) {
        StartLogListener startloglistener = new StartLogListener();
    }


    @Override
    public void handleEvent(StartLog event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb Start Log Listener Node: "  + event.getSourceNodeId()   +
                   " Gci: " + event.getGci() +
                   " CPtr: " + event.getCPtr(event) +
                   " Log Part: " + event.getLogPart() +
                   " Start Mb: " + event.getStartMb() +
                   " Stop Mb: " + event.getStopMb()

        );


    }

}
