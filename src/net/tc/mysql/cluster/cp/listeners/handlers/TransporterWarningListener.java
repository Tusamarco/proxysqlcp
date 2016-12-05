package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;
import com.mysql.cluster.mgmj.events.TransporterWarning;
import com.mysql.cluster.ndbj.NdbApiException;

import java.lang.ref.SoftReference;
import com.mysql.cluster.ndbj.NdbError;
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
public class TransporterWarningListener extends TransporterWarningTypeListener {
    protected  NdbError errRef;
    private static String ParentName = "NDB_MGM_EVENT_CATEGORY_ERROR";


    public TransporterWarningListener() {
    }

    public static void main(String[] args) {
        TransporterWarningListener transporterwarninglistener = new
                TransporterWarningListener();
    }

    @Override
    public void handleEvent(TransporterWarning event) {

        IsmaPersister.getLogByName("NDBMGMLISTENER").info(TimeTools.GetCurrentTime() +
                   " - Ndb Transporte rWarning Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event) +
                   " Warning Code: " + event.getCode() +
                   " To Node: " + event.getToNode()

        );


        java.lang.ref.SoftReference Sf = new SoftReference(new ListnerEvent());
        ListnerEvent eventL = (ListnerEvent)Sf.get();

        errRef = new NdbApiException(event.toString()).getErrorObj();
//        this is here because they change something in the api
//        errRef = new NdbApiException(event.getCode()).getErrorObj();



        eventL.setDate(TimeTools.getDate(TimeTools.GetCurrentDay(),TimeTools.getDayFormat() ) );
        eventL.setTime(TimeTools.getDate(TimeTools.GetCurrentTime(),TimeTools.getTimeFormat() ) );
        eventL.setSystemTime(System.currentTimeMillis());
        eventL.setStatus(1); // SET STATUS to warning this will automatically skip check at dispatcher level and will set the whole Listner Handler as thr Status value given
        eventL.setEvent("Transporter Warning:");
        eventL.setCurrentvalue("Warning code:" + errRef.getCode() + " " + errRef.getMessage() + " on Node=" + event.getSourceNodeId() + " from node:" + event.getToNode());
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
