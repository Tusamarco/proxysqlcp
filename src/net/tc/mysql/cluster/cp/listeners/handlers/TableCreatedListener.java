package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.TableCreated;

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
public class TableCreatedListener extends TableCreatedTypeListener {
    public TableCreatedListener() {
    }

    public static void main(String[] args) {
        TableCreatedListener tablecreatedlistener = new TableCreatedListener();
    }

    @Override
    public void handleEvent(TableCreated event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb Table Created Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Table ID: " + event.getTableId()

        );



    }

}
