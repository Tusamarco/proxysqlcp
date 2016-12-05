package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;
import com.mysql.cluster.mgmj.events.LocalCheckpointStarted;

import net.tc.isma.resources.Resource;
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
public class LocalCheckpointStartedListener extends
        LocalCheckpointStartedTypeListener {

    private static String ParentName = "NDB_MGM_EVENT_CATEGORY_CHECKPOINT";

    public LocalCheckpointStartedListener() {
    }

    public static void main(String[] args) {
        LocalCheckpointStartedListener localcheckpointstartedlistener = new
                LocalCheckpointStartedListener();
    }


    @Override
    public void handleEvent(LocalCheckpointStarted event) {


        java.lang.ref.SoftReference Sf = new SoftReference(new ListnerEvent());

        ListnerEvent eventL = (ListnerEvent) Sf.get();

        eventL.setDate(TimeTools.getDate(TimeTools.GetCurrentDay(),
                                         TimeTools.getDayFormat()));
        eventL.setTime(TimeTools.getDate(TimeTools.GetCurrentTime(),
                                         TimeTools.getTimeFormat()));

        eventL.setSystemTime(System.currentTimeMillis()); //mandatory for ALL
        eventL.setEvent("LCP started:");
        eventL.setStatus(0);
        eventL.setCurrentvalue(String.valueOf(event.getLci()));
        eventL.setNodeId(event.getSourceNodeId());

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
        RangeHandler eventR = (RangeHandler) Sfr.get();

        eventR.setEventStart(eventL);
        eventR.setSystemTime(eventL.getSystemTime());
        eventR.setNodeId(event.getSourceNodeId());

        java.lang.ref.SoftReference Sfp = new SoftReference(new
                persistentObjectImpl(Resource.TRANSIENT, eventR));
        PersistentObject po = (PersistentObject) Sfp.get();

        po.setKey("LocalCheckpoint");
        IsmaPersister.set("LocalCheckpoint", po);

        IsmaPersister.getLogByName("NDBMGMLISTENER").info("LCP started: on " +
                TimeTools.GetFullDate(eventL.getSystemTime()));










    }

}
