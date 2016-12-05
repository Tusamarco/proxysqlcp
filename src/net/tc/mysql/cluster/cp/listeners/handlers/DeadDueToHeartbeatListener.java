package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;
import com.mysql.cluster.mgmj.events.DeadDueToHeartbeat;

import java.lang.ref.SoftReference;
import net.tc.isma.persister.IsmaPersister;
import net.tc.mysql.cluster.cp.objects.EventDispatcher;
import net.tc.mysql.cluster.cp.objects.ListnerEvent;
import net.tc.mysql.cluster.cp.objects.NdbMonitorHandler;
import net.tc.mysql.cluster.cp.objects.Node;
import net.tc.mysql.cluster.util.TimeTools;

import java.util.Map;

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
public class DeadDueToHeartbeatListener extends DeadDueToHeartbeatTypeListener {

    private static String ParentName = "NDB_MGM_EVENT_CATEGORY_ERROR";

    public DeadDueToHeartbeatListener() {
    }

    public static void main(String[] args) {
        DeadDueToHeartbeatListener deadduetoheartbeatlistener = new
                DeadDueToHeartbeatListener();
    }

    @Override
    public void handleEvent(DeadDueToHeartbeat event) {

        IsmaPersister.getLogByName("NDBMGMLISTENER").info(TimeTools.GetCurrentTime() +
                   " - Ndb Dead Due To Heartbeat Source Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Node: " + event.getNode()

        );

        java.lang.ref.SoftReference Sf = new SoftReference(new ListnerEvent());
        ListnerEvent eventL = (ListnerEvent)Sf.get();


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
                  theNode = (Node)nodes.get(event.getSourceNodeId());

              }
          }

          if(theNode == null)
             return;

        eventL.setDate(TimeTools.getDate(TimeTools.GetCurrentDay(),TimeTools.getDayFormat() ) );
        eventL.setTime(TimeTools.getDate(TimeTools.GetCurrentTime(),TimeTools.getTimeFormat() ) );
        eventL.setSystemTime(System.currentTimeMillis());
        eventL.setStatus(2); // SET STATUS to warning this will automatically skip check at dispatcher level and will set the whole Listner Handler as thr Status value given
        eventL.setEvent("Dead Due To Heartbeat: on Node=" + event.getNode() + " Notified by from node:" + event.getSourceNodeId());

        if(theNode == null)
            eventL.setCurrentvalue("1.618");
        else if(theNode != null && theNode.getMissedHBcounter() > 0)
            eventL.setCurrentvalue(theNode.getMissedHBcounter());
        else
            eventL.setCurrentvalue("1.618");

        eventL.setNodeId(new Long( event.getNode()).intValue());


        /**
         * Dispatch the event to his ListnerHandler
         */
       EventDispatcher.Dispatch(ParentName, eventL, this.getClass().getName());


    }


}
