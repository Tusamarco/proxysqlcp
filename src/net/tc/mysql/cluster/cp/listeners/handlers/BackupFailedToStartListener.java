package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.BackupFailedToStart;

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
public class BackupFailedToStartListener extends
        BackupFailedToStartTypeListener {
    public BackupFailedToStartListener() {
    }

    public static void main(String[] args) {
        BackupFailedToStartListener backupfailedtostartlistener = new
                BackupFailedToStartListener();
    }

    @Override
    public void handleEvent(BackupFailedToStart event) {

        System.out.println(TimeTools.GetCurrentTime() +
           " - Ndb BackupFailedToStart Node: "  + event.getSourceNodeId()   +
           " CPtr: " + event.getCPtr(event) +
           " Error: " + event.getError() +
           " Starting Node: " + event.getStartingNode()

        );



    }

}
