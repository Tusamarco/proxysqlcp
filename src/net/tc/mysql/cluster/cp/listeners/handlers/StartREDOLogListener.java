package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.StartREDOLog;

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
public class StartREDOLogListener extends StartREDOLogTypeListener {
    public StartREDOLogListener() {
    }

    public static void main(String[] args) {
        StartREDOLogListener startredologlistener = new StartREDOLogListener();
    }

    @Override
    public void handleEvent(StartREDOLog event) {

        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb Start REDO Log Node: "  + event.getSourceNodeId()   +
                   " Completed Gci: " + event.getCompletedGci() +
                   " CPtr: " + event.getCPtr(event) +
                   " Keep Gci: " + event.getKeepGci() +
                   " Restorable Gci: " + event.getRestorableGci() +
                   " Node: " + event.getNode()

        );


    }

}
