package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;
import com.mysql.cluster.mgmj.events.ReceiveBytesStatistic;

import net.tc.isma.persister.IsmaPersister;
import java.util.Map;
import net.tc.isma.persister.persistentObjectImpl;
import net.tc.isma.persister.PersistentObject;
import net.tc.isma.resources.Resource;
import net.tc.mysql.cluster.cp.objects.NdbMonitorHandler;
import net.tc.mysql.cluster.cp.objects.Node;
import net.tc.mysql.cluster.util.TimeTools;

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
public class ReceiveBytesStatisticListener extends
        ReceiveBytesStatisticTypeListener {
    public ReceiveBytesStatisticListener() {
    }

    public static void main(String[] args) {
        ReceiveBytesStatisticListener receivebytesstatisticlistener = new
                ReceiveBytesStatisticListener();
    }

    @Override
    public void handleEvent(ReceiveBytesStatistic event) {

        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb Receive Bytes Statistic Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " From Node: " + event.getFromNode() +
                   " Mean Received Bytes: " + event.getMeanReceivedBytes()


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

        theNode.setByteReceived(theNode.getByteReceived()+ event.getMeanReceivedBytes());

        nodes.put(theNode.getId(), theNode);
        monHandler.getNdbStatus().setNodes(nodes);
        PersistentObject po = new persistentObjectImpl(Resource.ETERNAL, monHandler);
        po.setKey("NdbMonitorHandler");
        IsmaPersister.set("NdbMonitorHandler", po);



    }
}
