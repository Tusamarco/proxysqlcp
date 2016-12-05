package net.tc.mysql.cluster.cp.listeners.handlers;

import java.lang.ref.*;

import com.mysql.cluster.mgmj.events.*;
import com.mysql.cluster.mgmj.listeners.*;

import net.tc.isma.persister.*;
import java.util.Map;
import net.tc.isma.resources.Resource;
import net.tc.mysql.cluster.cp.objects.*;
import net.tc.mysql.cluster.util.*;

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
public class MissedHeartbeatListener extends MissedHeartbeatTypeListener {
    private static String ParentName = "NDB_MGM_EVENT_CATEGORY_ERROR";

    public MissedHeartbeatListener() {
    }

    public static void main(String[] args) {
        MissedHeartbeatListener missedheartbeatlistener = new
                MissedHeartbeatListener();
    }

    @Override
    public void handleEvent(MissedHeartbeat event) {
         IsmaPersister.getLogByName("NDBMGMLISTENER").info(TimeTools.GetCurrentTime() +
                   " - Ndb Missed Heartbeat Source Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Node: " + event.getNode()

        );

         java.lang.ref.SoftReference Sf = new SoftReference(new ListnerEvent());
         ListnerEvent eventL = (ListnerEvent)Sf.get();

         /**
          * the missed hbs are stored as counter (incremental) on the node obj
          * each time a Missed HB is detected a  procedure check for the counter and the last missed HB
          * if the time elapsed is less then 10 minutes then the mhb is added following the counter rules.
          * if the time elapsed is more then 10 minutes the counter is resetted and the event added.
          */


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

         /**
          * If the node is null the go away from here
          */
         if(theNode == null)
             return;

         long lastMhbTime = theNode.getLastMissedHB();
         long thisTime = System.currentTimeMillis();

         /**
          * if the time is less then 10 minutes
          * reset the mHB counter
          */

         /** @todo the time between HB reset MUST be parametrized
          * Now is fixed to 10 minutes
          * */
         if(lastMhbTime > 0 && thisTime > lastMhbTime )
         {
             if(((thisTime - lastMhbTime)/100) > 600)
                 theNode.setMissedHBcounter(0);

         }
         theNode.setMissedHBcounter(theNode.getMissedHBcounter() + 1);
         theNode.setLastMissedHB(thisTime);

         eventL.setDate(TimeTools.getDate(TimeTools.GetCurrentDay(),TimeTools.getDayFormat() ) );
         eventL.setTime(TimeTools.getDate(TimeTools.GetCurrentTime(),TimeTools.getTimeFormat() ) );
         eventL.setSystemTime(System.currentTimeMillis());
         eventL.setStatus(0); // SET STATUS to warning this will automatically skip check at dispatcher level and will set the whole Listner Handler as thr Status value given
         eventL.setEvent("Missed HeartBeat:");
         eventL.setCurrentvalue(theNode.getMissedHBcounter());

         eventL.setNodeId(new Long( event.getSourceNodeId()).intValue());

         nodes.put(theNode.getId(),theNode);
         monHandler.getNdbStatus().setNodes(nodes);
         PersistentObject po = new persistentObjectImpl(Resource.ETERNAL, monHandler);
         po.setKey("NdbMonitorHandler");
         IsmaPersister.set("NdbMonitorHandler", po);



         /**
          * Dispatch the event to his ListnerHandler
          */
        EventDispatcher.Dispatch(ParentName, eventL, this.getClass().getName());

    }
}
