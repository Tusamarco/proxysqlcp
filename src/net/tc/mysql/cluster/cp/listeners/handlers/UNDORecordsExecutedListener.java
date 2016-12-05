package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.UNDORecordsExecuted;

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
public class UNDORecordsExecutedListener extends
        UNDORecordsExecutedTypeListener {
    public UNDORecordsExecutedListener() {
    }

    public static void main(String[] args) {
        UNDORecordsExecutedListener undorecordsexecutedlistener = new
                UNDORecordsExecutedListener();
    }

    @Override
    public void handleEvent(UNDORecordsExecuted event) {
        System.out.println(TimeTools.GetCurrentTime() +
                     " - Ndb UNDO Records Executed Node: "  + event.getSourceNodeId()   +
                     " CPtr: " + event.getCPtr(event) +
                     " Block: " + event.getBlock()+
                     " Data1: " + event.getData1() +
                     " Data2: " + event.getData2() +
                     " Data3: " + event.getData3() +
                     " Data4: " + event.getData4() +
                     " Data5: " + event.getData5() +
                     " Data6: " + event.getData6() +
                     " Data7: " + event.getData7() +
                     " Data8: " + event.getData8() +
                     " Data9: " + event.getData9() +
                     " Data10: " + event.getData10()

          );



    }

}
