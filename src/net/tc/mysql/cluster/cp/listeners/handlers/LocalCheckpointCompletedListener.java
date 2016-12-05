package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;
import com.mysql.cluster.mgmj.events.LocalCheckpointCompleted;

import net.tc.isma.resources.Resource;
import net.tc.mysql.cluster.cp.objects.EventDispatcher;
import net.tc.mysql.cluster.cp.objects.ListnerEvent;
import net.tc.mysql.cluster.cp.objects.RangeHandler;
import net.tc.mysql.cluster.util.TimeTools;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.persister.persistentObjectImpl;
import net.tc.isma.persister.PersistentObject;

import java.lang.ref.SoftReference;

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
public class LocalCheckpointCompletedListener extends
        LocalCheckpointCompletedTypeListener {

    private static String ParentName = "NDB_MGM_EVENT_CATEGORY_CHECKPOINT";

    public LocalCheckpointCompletedListener() {
    }

    public static void main(String[] args) {
        LocalCheckpointCompletedListener localcheckpointcompletedlistener = new
                LocalCheckpointCompletedListener();
    }

    @Override
    public void handleEvent(LocalCheckpointCompleted event) {

        /**
         * It takes from the persistent layer the object if the object is null then
         * write an error in the logs and return dropping the event.
         */
        RangeHandler eventR= (RangeHandler)IsmaPersister.get(RangeHandler.class, "LocalCheckpoint");

        if(eventR == null || eventR.getEventStart() == null)
        {
//            IsmaPersister.getLogByName("NDBMGMSYSTEM").error("LCP Error in close event at " + TimeTools.GetCurrent());
            IsmaPersister.getLogByName("NDBMGMLISTENER").error("LCP Error in close event at " + TimeTools.GetCurrent());
            return;
        }


        java.lang.ref.SoftReference Sf = new SoftReference(new ListnerEvent());
        ListnerEvent eventL = (ListnerEvent)Sf.get();

        eventL.setDate(TimeTools.getDate(TimeTools.GetCurrentDay(),TimeTools.getDayFormat() ) );
        eventL.setTime(TimeTools.getDate(TimeTools.GetCurrentTime(),TimeTools.getTimeFormat() ) );
        eventL.setSystemTime(System.currentTimeMillis());
        eventL.setEvent("LCP completed:");
        eventL.setStatus(0);
        eventL.setCurrentvalue(String.valueOf(event.getLci()));
        eventL.setNodeId(event.getSourceNodeId());


        eventR.setEventStop(eventL);

        java.lang.ref.SoftReference Sfp = new SoftReference(new persistentObjectImpl(Resource.TRANSIENT, eventR));
        PersistentObject po = (PersistentObject)Sfp.get();
        po.setKey("LocalCheckpoint");
        IsmaPersister.set("LocalCheckpoint", po);

        /**
         * Dispatch the event to his ListnerHandler
         */
        eventR.setCurrentvalue(new Long(eventR.getEventStop().getSystemTime() - eventR.getEventStart().getSystemTime()));
        EventDispatcher.Dispatch(ParentName, eventR, this.getClass().getName());

        IsmaPersister.getLogByName("NDBMGMLISTENER").info("LCP completed: on " + TimeTools.GetFullDate(eventL.getSystemTime()));

  }

}
