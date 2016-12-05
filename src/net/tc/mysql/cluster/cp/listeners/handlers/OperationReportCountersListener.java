package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;
import com.mysql.cluster.mgmj.events.OperationReportCounters;

import java.lang.ref.SoftReference;
import net.tc.isma.persister.IsmaPersister;

import java.util.Map;
import net.tc.isma.persister.PersistentObject;
import net.tc.isma.persister.persistentObjectImpl;
import net.tc.isma.resources.Resource;
import net.tc.mysql.cluster.cp.objects.EventDispatcher;
import net.tc.mysql.cluster.cp.objects.ListnerEvent;
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
public class OperationReportCountersListener extends OperationReportCountersTypeListener {

    private static String ParentName = "NDB_MGM_EVENT_CATEGORY_STATISTIC";

    public OperationReportCountersListener() {
    }

    public static void main(String[] args) {
        OperationReportCountersListener operationreportcounterslistener = new OperationReportCountersListener();
    }

    @Override
    public void handleEvent(OperationReportCounters event) {

        IsmaPersister.getLogByName("NDBMGMLISTENER").info(TimeTools.GetCurrentTime() +
                " - Ndb Operation Report Counters Node: " + event.getSourceNodeId() +
                " CPtr: " + event.getCPtr(event) +
                " Ops: " + event.getOps());

        java.lang.ref.SoftReference Sf = new SoftReference(new ListnerEvent());
        ListnerEvent eventL = (ListnerEvent) Sf.get();

        eventL.setDate(TimeTools.getDate(TimeTools.GetCurrentDay(),
                TimeTools.getDayFormat()));
        eventL.setTime(TimeTools.getDate(TimeTools.GetCurrentTime(),
                TimeTools.getTimeFormat()));
        eventL.setSystemTime(System.currentTimeMillis());
        eventL.setStatus(0);
        eventL.setEvent("Operation Report Counters for Node:" + event.getSourceNodeId() + ": " + event.getOps());
        eventL.setCurrentvalue(event.getOps());
        eventL.setNodeId(new Long(event.getSourceNodeId()).intValue());

        EventDispatcher.Dispatch(ParentName, eventL, this.getClass().getName());

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
        theNode.setOpCount(event.getOps());

        nodes.put(theNode.getId(), theNode);
        monHandler.getNdbStatus().setNodes(nodes);
        PersistentObject po = new persistentObjectImpl(Resource.ETERNAL, monHandler);
        po.setKey("NdbMonitorHandler");
        IsmaPersister.set("NdbMonitorHandler", po);
    }
}
