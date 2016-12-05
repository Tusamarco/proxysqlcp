package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.BackupStarted;

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
public class BackupStartedListener extends BackupStartedTypeListener {
    public BackupStartedListener() {
    }

    public static void main(String[] args) {
        BackupStartedListener backupstartedlistener = new BackupStartedListener();
    }

    @Override
    public void handleEvent(BackupStarted event) {

        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb BackupStarted Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Backup Id: " + event.getBackupId() +
                   " Starting Node: " + event.getStartingNode()

        );
    }

}
