package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.events.*;
import com.mysql.cluster.mgmj.listeners.*;

import net.tc.isma.persister.*;
import java.lang.ref.SoftReference;
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
public class GlobalCheckpointStartedListener extends
        GlobalCheckpointStartedTypeListener {

    private static String ParentName = "NDB_MGM_EVENT_CATEGORY_CHECKPOINT";

    public GlobalCheckpointStartedListener() {
    }

    public static void main(String[] args) {
        GlobalCheckpointStartedListener globalcheckpointstartedlistener = new
                GlobalCheckpointStartedListener();
    }


    @Override
    public void handleEvent(GlobalCheckpointStarted event)
    {
        /**
         * Crate a soft reference for the event
         * store the time and other references
         *
         */
        java.lang.ref.SoftReference Sf = new SoftReference(new ListnerEvent());

        ListnerEvent eventL = (ListnerEvent)Sf.get();

        eventL.setDate(TimeTools.getDate(TimeTools.GetCurrentDay(),TimeTools.getDayFormat() ) );
        eventL.setTime(TimeTools.getDate(TimeTools.GetCurrentTime(),TimeTools.getTimeFormat() ) );
        eventL.setSystemTime(System.currentTimeMillis()); //mandatory for ALL
        eventL.setEvent("GCP started:");
        eventL.setStatus(0);
        eventL.setCurrentvalue(String.valueOf(event.getGci()));


        /**
         * Create a sf to a Range object then store the
         * listner event into the start method
         *
         * the Range object will be store in memory as not completed waiting for the CPCompleted event
         *
         * the object will be discarded from the memory after the value
         * specified in the jcs.region.transient.elementattributes.MaxLifeSeconds=1200 in cache.ccf
         */
        java.lang.ref.SoftReference Sfr = new SoftReference(new RangeHandler());
        RangeHandler eventR = (RangeHandler)Sfr.get();

        eventR.setEventStart(eventL);
        eventR.setSystemTime(eventL.getSystemTime());

        java.lang.ref.SoftReference Sfp = new SoftReference(new persistentObjectImpl(Resource.TRANSIENT, eventR));
        PersistentObject po = (PersistentObject)Sfp.get();

        po.setKey("GlobalCheckpoint");
        IsmaPersister.set("GlobalCheckpoint", po);

//        EventDispatcher.Dispatch(ParentName, eventL, this.getClass().getName());
        IsmaPersister.getLogByName("NDBMGMLISTENER").info("GCP started: on " + TimeTools.GetFullDate(eventL.getSystemTime()));


//        System.out.println(TimeTools.GetCurrentTime() + " - GCP started:" + event.getCPtr(event));

    }

}
