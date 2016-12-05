package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;
import com.mysql.cluster.mgmj.events.TransReportCounters;

import java.util.Map;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.persister.PersistentObject;
import net.tc.isma.persister.persistentObjectImpl;
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
public class TransReportCountersListener extends TransReportCountersTypeListener {

    public TransReportCountersListener() {
    }

    public static void main(String[] args) {
        TransReportCountersListener transreportcounterslistener = new TransReportCountersListener();
    }

    @Override
    public void handleEvent(TransReportCounters event) {

        System.out.println(TimeTools.GetCurrentTime() +
                " - Ndb Transaction Report Counters Node: " + event.getSourceNodeId() +
                " CPtr: " + event.getCPtr(event) +
                " Abort Count: " + event.getAbortCount() +
                " Attr info Count: " + event.getAttrinfoCount() +
                " Commit Count: " + event.getCommitCount() +
                " Concurrent Op Count: " + event.getConcOpCount() +
                " Range Scan Count: " + event.getRangeScanCount() +
                " Read Count: " + event.getReadCount() +
                " Scan Count: " + event.getScanCount() +
                " Simple Read Count: " + event.getSimpleReadCount() +
                " Trans Count: " + event.getTransCount() +
                " Write Count: " + event.getWriteCount());

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
        if (theNode == null) {
            return;
        }
        theNode.setTransCount(event.getTransCount());

        nodes.put(theNode.getId(), theNode);
        monHandler.getNdbStatus().setNodes(nodes);
        PersistentObject po = new persistentObjectImpl(Resource.ETERNAL, monHandler);
        po.setKey("NdbMonitorHandler");
        IsmaPersister.set("NdbMonitorHandler", po);

    }
}
