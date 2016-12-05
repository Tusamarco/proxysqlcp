package net.tc.mysql.cluster.cp.listeners.handlers;

import java.lang.ref.*;

import com.mysql.cluster.mgmj.events.*;
import com.mysql.cluster.mgmj.listeners.*;

import net.tc.isma.persister.*;
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
public class MemoryUsageListener extends MemoryUsageTypeListener {
    private static String ParentName = "NDB_MGM_EVENT_CATEGORY_STATISTIC";

    public MemoryUsageListener() {

    }

    public static void main(String[] args) {
        MemoryUsageListener memoryusagelistener = new MemoryUsageListener();
    }


    @Override
    public void handleEvent(MemoryUsage event) {

        String forLog = TimeTools.GetCurrentTime() +
           " - Ndb MemoryUsage Node: "  + event.getSourceNodeId()   +
           " CPtr: " + event.getCPtr(event) +
           " Gth: " + event.getGth() +
           " Block: " + event.getBlock() +
           " PageSizeKb: " + event.getPageSizeKb() +
           " Pages Total: " + event.getPagesTotal() +
           " Pages Used: " + event.getPagesUsed();


        java.lang.ref.SoftReference SfMem = new SoftReference(
                    new MemoryElement(
                                      event.getBlock(),
                                      event.getPageSizeKb(),
                                      event.getPagesTotal(),
                                      event.getPagesUsed())
                                  );
        MemoryElement memEle = (MemoryElement)SfMem.get();


        memEle.setDate(TimeTools.getDate(TimeTools.GetCurrentDay(),
                                         TimeTools.getDayFormat()));
        memEle.setTime(TimeTools.getDate(TimeTools.GetCurrentTime(),
                                         TimeTools.getTimeFormat()));
        memEle.setSystemTime(System.currentTimeMillis());
        memEle.setEvent("GCP completed:");
        memEle.setStatus(0);

//        java.lang.ref.SoftReference SfMem = new SoftReference(
//                new MemoryElement(
//                                  event.getBlock(),
//                                  event.getPageSizeKb(),
//                                  event.getPagesTotal(),
//                                  event.getPagesUsed())
//                                  );


        memEle.setCurrentvalue(memEle.getFreeMemoryMb());
        memEle.setNodeId(event.getSourceNodeId());


        /**
         * Dispatch the event to his ListnerHandler
         */
        EventDispatcher.Dispatch(ParentName, memEle, this.getClass().getName());

        IsmaPersister.getLogByName("NDBMGMLISTENER").info(forLog);


    }

}

