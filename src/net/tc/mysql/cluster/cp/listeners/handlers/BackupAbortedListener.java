package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.BackupAborted;

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
public class BackupAbortedListener extends BackupAbortedTypeListener {
    public BackupAbortedListener() {
    }

    public static void main(String[] args) {
        BackupAbortedListener backupabortedlistener = new BackupAbortedListener();
    }

    @Override
    public void handleEvent(BackupAborted event) {
        System.out.println(TimeTools.GetCurrentTime() +
           " - Ndb Backup Aborted Node: "  + event.getSourceNodeId()   +
           " CPtr: " + event.getCPtr(event) +
           " Backup Id: " + event.getBackupId() +
           " Error: " + event.getError() +
           " Starting Node: " + event.getStartingNode()

        );



    }

}
