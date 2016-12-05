package net.tc.mysql.cluster.cp.actions;

/**
 * <p>Title: NDBJ / API</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Marco Tusa Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Marco Tusa
 * @version 1.0
 */

import java.util.*;

import javax.servlet.http.*;

import net.tc.isma.*;
import net.tc.isma.actions.*;
import net.tc.isma.actions.generic.*;
import net.tc.isma.persister.*;
import net.tc.isma.request.generic.*;
import net.tc.isma.resources.*;
import net.tc.isma.utils.SynchronizedMap;
import net.tc.mysql.cluster.cp.*;
import net.tc.mysql.cluster.cp.objects.*;

import com.mysql.cluster.mgmj.NdbMgm;
import com.mysql.cluster.mgmj.NdbMgmImpl;

public class nodeHandler extends actionImpl {

    private results resultLocal = null;
    private int retry = 1;
    private SynchronizedMap listnerHandlers = new SynchronizedMap();
    private NdbMonitorHandler monHandler = null;

    public nodeHandler() {
    }

    public Results execute() {
        resultLocal = (results)this.getResult();

        if (!resultLocal.processSuccesflullyExecuted())
            return (Results) resultLocal;


        HttpServletRequest req = this.getRequest();
        String toDo = null;
        int nodeId = 0;
        NdbMgm mgm  = null;



        boolean monitorIsRunning = false;


        monHandler= (NdbMonitorHandler)IsmaPersister.get(NdbMonitorHandler.class, "NdbMonitorHandler");

        mgm = (NdbMgm)IsmaPersister.get(NdbMgmImpl.class, "NDBMGM");
        listnerHandlers = (SynchronizedMap)IsmaPersister.get(SynchronizedMap.class, "NDBMGMListeners");


//        String ndbMgmUri = (String) IsmaPersister.get(String.class, "NdbMgmUri");
        if (req.getParameter("nodedo") != null && !req.getParameter("nodedo").equals("") )
           toDo = req.getParameter("nodedo");

        if (req.getParameter("nodeid") != null && !req.getParameter("nodeid").equals("") )
           nodeId = Integer.parseInt(req.getParameter("nodeid"));


       try
       {

           if((monHandler == null || !monHandler.isMonitoring()) )
           {
                monHandler = new NdbMonitorHandler();
                if (monHandler.startMonitoring((Map) listnerHandlers))
                {
                    if(monHandler.getInternalNdbStatus() == null)
                        RaiseException();

                    PersistentObject po = new persistentObjectImpl(Resource.ETERNAL, monHandler);
                    po.setKey("NdbMonitorHandler");
                    IsmaPersister.set("NdbMonitorHandler", po);
                    resultLocal.put("NdbMonitorHandler", monHandler);
                }
           }

           else if(monHandler != null && monHandler.isMonitoring() && toDo.endsWith("stop") && nodeId > 0 && monHandler.checkForNodeFreeForAction("stop",nodeId) )
           {
               mgm.restart(new int[]{nodeId}, false, true,false);
           }
           else if(monHandler != null && monHandler.isMonitoring() && toDo.endsWith("start")  && nodeId > 0 && monHandler.checkForNodeFreeForAction("start",nodeId) )
           {
               mgm.start(new int[]{nodeId});
           }


       }
       catch (Exception ex)
       {

           IsmaPersister.getLogSystem().error(ex);
           IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex);
           resultLocal.processSuccesflullyExecuted(false, (Throwable)ex);

       }
       finally
       {
           return (Results) resultLocal;
       }



    }


    private void RaiseException() throws IsmaException {
        throw new IsmaException("--- ERROR --- Monitor Handler could not connect to nodes");
    }

}

