package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;
import com.mysql.cluster.mgmj.events.GlobalCheckpointCompleted;

import net.tc.isma.persister.IsmaPersister;

import java.lang.ref.SoftReference;

import net.tc.isma.persister.persistentObjectImpl;
import net.tc.isma.persister.PersistentObject;
import net.tc.isma.resources.Resource;
import net.tc.mysql.cluster.cp.objects.EventDispatcher;
import net.tc.mysql.cluster.cp.objects.ListnerEvent;
import net.tc.mysql.cluster.cp.objects.RangeHandler;
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
public class GlobalCheckpointCompletedListener extends
        GlobalCheckpointCompletedTypeListener {
    private static String ParentName = "NDB_MGM_EVENT_CATEGORY_CHECKPOINT";

    public GlobalCheckpointCompletedListener() {
    }

    public static void main(String[] args) {
        GlobalCheckpointCompletedListener globalcheckpointcompletedlistener = new
                GlobalCheckpointCompletedListener();
    }

    @Override
    public void handleEvent(GlobalCheckpointCompleted event) {


        /**
         * It takes from the persistent layer the object if the object is null then
         * write an error in the logs and return dropping the event.
         */
        RangeHandler eventR= (RangeHandler)IsmaPersister.get(RangeHandler.class, "GlobalCheckpoint");

        if(eventR == null || eventR.getEventStart() == null)
        {
//            IsmaPersister.getLogByName("NDBMGMSYSTEM").error("GCP Error in close event at " + TimeTools.GetCurrent());
            IsmaPersister.getLogByName("NDBMGMLISTENER").error("GCP Error in close event at " + TimeTools.GetCurrent());
            return;
        }


        java.lang.ref.SoftReference Sf = new SoftReference(new ListnerEvent());
        ListnerEvent eventL = (ListnerEvent)Sf.get();

        eventL.setDate(TimeTools.getDate(TimeTools.GetCurrentDay(),TimeTools.getDayFormat() ) );
        eventL.setTime(TimeTools.getDate(TimeTools.GetCurrentTime(),TimeTools.getTimeFormat() ) );
        eventL.setSystemTime(System.currentTimeMillis());
        eventL.setEvent("GCP completed:");
        eventL.setStatus(0);
        eventL.setCurrentvalue(String.valueOf(event.getGci()));

        eventR.setEventStop(eventL);

        java.lang.ref.SoftReference Sfp = new SoftReference(new persistentObjectImpl(Resource.TRANSIENT, eventR));
        PersistentObject po = (PersistentObject)Sfp.get();
        po.setKey("GlobalCheckpoint");
        IsmaPersister.set("GlobalCheckpoint", po);

        /**
         * Dispatch the event to his ListnerHandler
         */
        eventR.setCurrentvalue(new Long(eventR.getEventStop().getSystemTime() - eventR.getEventStart().getSystemTime()));
        EventDispatcher.Dispatch(ParentName, eventR, this.getClass().getName());

        IsmaPersister.getLogByName("NDBMGMLISTENER").info("GCP completed: on " + TimeTools.GetFullDate(eventL.getSystemTime()));


//        System.out.println(TimeTools.GetCurrentTime() + " - GCP completed:" + event.getCPtr(event));

  }

}
