package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.BackupCompleted;

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
public class BackupCompletedListener extends BackupCompletedTypeListener {
    public BackupCompletedListener() {
    }

    public static void main(String[] args) {
        BackupCompletedListener backupcompletedlistener = new
                BackupCompletedListener();
    }

    @Override
    public void handleEvent(BackupCompleted event) {

        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb BackupCompleted Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Backup Id: " + event.getBackupId() +
                   " Num Bytes: " + event.getNumBytes() +
                   " Num Log Bytes: " + event.getNumLogBytes() +
                   " Num Log Records: " + event.getNumLogRecords() +
                   " Num Records: " + event.getNumRecords() +
                   " Start Gci: " + event.getStartGci() +
                   " Starting Node: " + event.getStartingNode() +
                   " Stop Gci: " + event.getStopGci()

        );

    }


}
