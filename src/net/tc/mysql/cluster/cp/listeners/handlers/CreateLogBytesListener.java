package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.CreateLogBytes;

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
public class CreateLogBytesListener extends CreateLogBytesTypeListener {
    public CreateLogBytesListener() {
    }

    public static void main(String[] args) {
        CreateLogBytesListener createlogbyteslistener = new
                CreateLogBytesListener();
    }

    @Override
    public void handleEvent(CreateLogBytes event) {

        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb CreateLog source Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Node: " + event.getNode()

        );



    }

}
