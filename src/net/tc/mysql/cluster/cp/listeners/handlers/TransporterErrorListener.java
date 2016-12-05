package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;
import com.mysql.cluster.mgmj.events.TransporterError;
import com.mysql.cluster.ndbj.NdbError;
import com.mysql.cluster.ndbj.NdbApiException;

import java.lang.ref.SoftReference;
import net.tc.isma.resources.Resource;
import net.tc.mysql.cluster.cp.objects.EventDispatcher;
import net.tc.mysql.cluster.cp.objects.ListnerEvent;
import net.tc.mysql.cluster.util.TimeTools;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.persister.persistentObjectImpl;
import net.tc.isma.persister.PersistentObject;

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
public class TransporterErrorListener extends TransporterErrorTypeListener {

    protected  NdbError errRef;
    private static String ParentName = "NDB_MGM_EVENT_CATEGORY_ERROR";

    public TransporterErrorListener() {
    }

    public static void main(String[] args) {
        TransporterErrorListener transportererrorlistener = new
                TransporterErrorListener();
    }

    @Override
    public void handleEvent(TransporterError event) {
        IsmaPersister.getLogByName("NDBMGMLISTENER").info(TimeTools.GetCurrentTime() +
                   " - Ndb Transporter Error Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Error Code: " + event.getCode() +
                   " From Node: " + event.getToNode()

        );

        java.lang.ref.SoftReference Sf = new SoftReference(new ListnerEvent());
        ListnerEvent eventL = (ListnerEvent)Sf.get();

        errRef = new NdbApiException( event.toString()).getErrorObj();
        //this is for change of the version
//        errRef = new NdbApiException( event.getCode()).getErrorObj();

        eventL.setDate(TimeTools.getDate(TimeTools.GetCurrentDay(),TimeTools.getDayFormat() ) );
        eventL.setTime(TimeTools.getDate(TimeTools.GetCurrentTime(),TimeTools.getTimeFormat() ) );
        eventL.setSystemTime(System.currentTimeMillis());
        eventL.setStatus(2); // SET STATUS to Error this will automatically skip check at dispatcher level and will set the whole Listner Handler as thr Status value given
        eventL.setEvent("Transporter Error:");
        eventL.setCurrentvalue("Error code:" + errRef.getCode() + " " + errRef.getMessage() + " on Node=" + event.getSourceNodeId() + " from node:" + event.getToNode());
        eventL.setNodeId(new Long( event.getSourceNodeId()).intValue());

/**
 * This Part is not needed here because this is a single value not a range to catch
 */
//        java.lang.ref.SoftReference Sfp = new SoftReference(new
//                persistentObjectImpl(Resource.TRANSIENT, eventL));
//        PersistentObject po = (PersistentObject) Sfp.get();
//        po.setKey("TransporterError");
//        IsmaPersister.set("TransporterError", po);

        /**
         * Dispatch the event to his ListnerHandler
         */
        EventDispatcher.Dispatch(ParentName, eventL, this.getClass().getName());



    }

}
