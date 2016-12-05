package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.UndoLogBlocked;

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
public class UndoLogBlockedListener extends UndoLogBlockedTypeListener {
    public UndoLogBlockedListener() {
    }

    public static void main(String[] args) {
        UndoLogBlockedListener undologblockedlistener = new
                UndoLogBlockedListener();
    }

    @Override
    public void handleEvent(UndoLogBlocked event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb Undo Log Blocked Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Acc Count: " + event.getAccCount() +
                   " Tup Count: " + event.getTupCount()

        );



    }

}
