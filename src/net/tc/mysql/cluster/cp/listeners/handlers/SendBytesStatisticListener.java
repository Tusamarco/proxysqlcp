package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;
import com.mysql.cluster.mgmj.events.SendBytesStatistic;

import net.tc.isma.resources.Resource;
import net.tc.mysql.cluster.cp.objects.NdbMonitorHandler;
import net.tc.mysql.cluster.cp.objects.Node;
import net.tc.mysql.cluster.util.TimeTools;
import net.tc.isma.persister.IsmaPersister;
import java.util.Map;

import net.tc.isma.persister.persistentObjectImpl;
import net.tc.isma.persister.PersistentObject;

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
public class SendBytesStatisticListener extends SendBytesStatisticTypeListener {
    public SendBytesStatisticListener() {
    }

    public static void main(String[] args) {
        SendBytesStatisticListener sendbytesstatisticlistener = new
                SendBytesStatisticListener();
    }

    @Override
    public void handleEvent(SendBytesStatistic event) {

        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb Send Bytes Statistic Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Mean Sent Bytes: " + event.getMeanSentBytes() +
                   " To Node: " + event.getToNode()

        );

        /**
          * Get the nodes if not nulls
          */

         Node theNode = null;
         Map nodes = null;

        NdbMonitorHandler monHandler = null;
        monHandler = (NdbMonitorHandler) IsmaPersister.get(NdbMonitorHandler.class,
                "NdbMonitorHandler");

        if (monHandler != null && monHandler.getNdbStatus() != null) {
            nodes = monHandler.getNdbStatus().getNodes();

            if (nodes != null && nodes.size() > 0) {
                theNode = (Node) nodes.get(event.getSourceNodeId());

            }
        }

        /**
         * If the node is null the go away from here
         */
        if (theNode == null)
            return;

        theNode.setByteSent(theNode.getByteSent() + event.getMeanSentBytes());

        nodes.put(theNode.getId(), theNode);
        monHandler.getNdbStatus().setNodes(nodes);
        PersistentObject po = new persistentObjectImpl(Resource.ETERNAL, monHandler);
        po.setKey("NdbMonitorHandler");
        IsmaPersister.set("NdbMonitorHandler", po);


    }

}
