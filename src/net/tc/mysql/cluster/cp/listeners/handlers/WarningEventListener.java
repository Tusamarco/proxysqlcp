package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;
import com.mysql.cluster.mgmj.events.Warning;

import java.lang.ref.SoftReference;
import net.tc.isma.persister.IsmaPersister;
import net.tc.mysql.cluster.cp.objects.EventDispatcher;
import net.tc.mysql.cluster.cp.objects.ListnerEvent;
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
public class WarningEventListener extends WarningTypeListener {
    public WarningEventListener() {
    }

    private static String ParentName = "NDB_MGM_EVENT_CATEGORY_ERROR";

    public static void main(String[] args) {
        WarningEventListener warningeventlistener = new WarningEventListener();
    }

    @Override
    public void handleEvent(Warning event) {

        IsmaPersister.getLogByName("NDBMGMLISTENER").info(TimeTools.GetCurrentTime() +
           " - Ndb Warning Node: "  + event.getSourceNodeId()   +
           " CPtr: " + event.getCPtr(event)
        );

        java.lang.ref.SoftReference Sf = new SoftReference(new ListnerEvent());
        ListnerEvent eventL = (ListnerEvent) Sf.get();

        eventL.setDate(TimeTools.getDate(TimeTools.GetCurrentDay(),TimeTools.getDayFormat()));
        eventL.setTime(TimeTools.getDate(TimeTools.GetCurrentTime(),TimeTools.getTimeFormat()));
        eventL.setSystemTime(System.currentTimeMillis());
        eventL.setStatus(1); // SET STATUS to warning this will automatically skip check at dispatcher level and will set the whole Listner Handler as thr Status value given
        eventL.setEvent("Ndb Warning Node:");
        eventL.setCurrentvalue(" on Node=" + event.getSourceNodeId() +
                               " Generic warning: " + event.getEventCategory().toString());
        eventL.setNodeId(new Long(event.getSourceNodeId()).intValue());

        EventDispatcher.Dispatch(ParentName, eventL, this.getClass().getName());

    }


}
