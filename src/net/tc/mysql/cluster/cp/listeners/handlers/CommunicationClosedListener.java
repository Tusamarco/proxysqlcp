package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;
import com.mysql.cluster.mgmj.events.CommunicationClosed;

import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.resources.Resource;
import net.tc.mysql.cluster.cp.objects.EventDispatcher;
import net.tc.mysql.cluster.cp.objects.ListnerEvent;
import net.tc.mysql.cluster.cp.objects.NdbMonitorHandler;
import net.tc.mysql.cluster.cp.objects.Node;
import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.NodeStatus;

import net.tc.isma.persister.persistentObjectImpl;
import net.tc.isma.persister.PersistentObject;

import java.lang.ref.SoftReference;
import java.util.Map;

/**
 * <p>Title: NDBJ / API</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Marco Tusa Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Marco Tusa
 * @version 1.0
 */
public class CommunicationClosedListener extends
        CommunicationClosedTypeListener {

    private static String ParentName = "NDB_MGM_EVENT_CATEGORY_CONNECTION";

    public CommunicationClosedListener() {


    }
    /* (non-Javadoc)
     * @see com.mysql.cluster.mgmj.NdbLogEventCategoryListener#handleEvent(com.mysql.cluster.mgmj.NdbLogEvent)
     */
    @Override
    public void handleEvent(CommunicationClosed event) {
            IsmaPersister.getLogByName("NDBMGMLISTENER").info(TimeTools.GetCurrentTime()+" - Comm closed to node:" + event.getNode());


            NdbMonitorHandler monHandler = null;
                monHandler = (NdbMonitorHandler) IsmaPersister.get(NdbMonitorHandler.class,"NdbMonitorHandler");

                if (monHandler == null)
                    return;

                Node node = null;

                try {
                    Map nodes = monHandler.getNdbStatus().getNodes();
                    Integer id = new Integer(new Long(event.getNode()).intValue());
                    node = (Node) nodes.get(id);

                    node.setStatus(NodeStatus.NDB_MGM_NODE_STATUS_SHUTTING_DOWN.toString());
                    node.setModified(true);
                    monHandler.getNdbStatus().getNodes().put(node.getId(), node);

                    PersistentObject po = new persistentObjectImpl(Resource.ETERNAL,monHandler);
                    po.setKey("NdbMonitorHandler");
                    IsmaPersister.set("NdbMonitorHandler", po);

                    System.out.println("aaaa modified com closed" + node.getId());

                } catch (Exception ex) {
                    ex.printStackTrace();
                    IsmaPersister.getLogByName("NDBMGMSYSTEM").error("Invalid call");
                }

                /**
                 *
                 * OK so we are inserting some info here anyhow to have the possibility to get some historical data
                 *
                 *
                 */

                java.lang.ref.SoftReference Sf = new SoftReference(new ListnerEvent());
                ListnerEvent eventL = (ListnerEvent) Sf.get();

                eventL.setDate(TimeTools.getDate(TimeTools.GetCurrentDay(),TimeTools.getDayFormat()));
                eventL.setTime(TimeTools.getDate(TimeTools.GetCurrentTime(),TimeTools.getTimeFormat()));
                eventL.setSystemTime(System.currentTimeMillis());
                eventL.setStatus(1);
                eventL.setEvent("Connected node:" + event.getSourceNodeId() + ": " + event.getNode());
                eventL.setCurrentvalue(NodeStatus.NDB_MGM_NODE_STATUS_SHUTTING_DOWN.toString());
                eventL.setNodeId(new Long(event.getSourceNodeId()).intValue());

               EventDispatcher.Dispatch(ParentName, eventL, this.getClass().getName());

    }

}
