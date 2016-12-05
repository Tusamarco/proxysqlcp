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

public class monitoringManager extends actionImpl {

    private results resultLocal = null;
    private int retry = 1;
    private SynchronizedMap listnerHandlers = new SynchronizedMap();
    private NdbMonitorHandler monHandler = null;

    public monitoringManager() {
    }

    public Results execute() {
        resultLocal = (results)this.getResult();

        if (!resultLocal.processSuccesflullyExecuted())
            return (Results) resultLocal;


        HttpServletRequest req = this.getRequest();
        String toDo = null;
//        NdbMgm mgm  = null;



        boolean monitorIsRunning = false;


        monHandler= (NdbMonitorHandler)IsmaPersister.get(NdbMonitorHandler.class, "NdbMonitorHandler");

//        mgm = (NdbMgm)IsmaPersister.get(NdbMgmImpl.class, "NDBMGM");
        listnerHandlers = (SynchronizedMap)IsmaPersister.get(SynchronizedMap.class, "NDBMGMListeners");


//        String ndbMgmUri = (String) IsmaPersister.get(String.class, "NdbMgmUri");
        if (req.getParameter("do") != null && !req.getParameter("do").equals("") )
           toDo = req.getParameter("do");


       try
       {

           if((monHandler == null || !monHandler.isMonitoring())  && (toDo.endsWith("start") || toDo.endsWith("refresh")))
           {
                monHandler = new NdbMonitorHandler();
                if (monHandler.startMonitoring((Map) listnerHandlers))
                {
                    if(monHandler.getInternalNdbStatus() == null)
                        RaiseException();

                    PersistentObject po = new persistentObjectImpl(Resource.
                            ETERNAL, monHandler);
                    po.setKey("NdbMonitorHandler");
                    IsmaPersister.set("NdbMonitorHandler", po);
                    resultLocal.put("NdbMonitorHandler", monHandler);
                }
           }
           else if(monHandler != null && monHandler.isMonitoring() && toDo.endsWith("stop"))
           {
               monHandler.stopMonitoring((Map) listnerHandlers);
           }
           else if(monHandler != null && monHandler.isMonitoring() && toDo.endsWith("refresh"))
           {
               if(monHandler.getInternalNdbStatus() == null)
                   RaiseException();

               PersistentObject po = new persistentObjectImpl(Resource.
                       ETERNAL, monHandler);
               po.setKey("NdbMonitorHandler");
               IsmaPersister.set("NdbMonitorHandler", po);
               resultLocal.put("NdbMonitorHandler", monHandler);

           }


       }
       catch (Exception ex)
       {

           IsmaPersister.getLogSystem().error(ex);
           IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex);
           resultLocal.processSuccesflullyExecuted(false, (Throwable)ex);

           resultLocal = (results)errorHandling(resultLocal);


       }
       finally
       {
           return (Results) resultLocal;
       }



    }

    private Results errorHandling(Results resultLocal)
    {
        int maxRetry = 3;
        int pauseFor = 10000;

        if(IsmaPersister.getConfigParameterValueString("isma_configuration.ndb_mgm_connectionretry") != null
          && Integer.parseInt(IsmaPersister.getConfigParameterValueString("isma_configuration.ndb_mgm_connectionretry")) > 0)
            maxRetry = Integer.parseInt(IsmaPersister.getConfigParameterValueString("isma_configuration.ndb_mgm_connectionretry"));

        if(IsmaPersister.getConfigParameterValueString("isma_configuration.ndb_mgm_connectionwaitfor") != null
          && Integer.parseInt(IsmaPersister.getConfigParameterValueString("isma_configuration.ndb_mgm_connectionwaitfor")) > 0)
            pauseFor = Integer.parseInt(IsmaPersister.getConfigParameterValueString("isma_configuration.ndb_mgm_connectionwaitfor"));


        try{
            if (retry <= maxRetry) {
                IsmaPersister.getLogByName("NDBMGMSYSTEM").error(" Trying to re-inizialize the monitoring system for "+ maxRetry +" times with a pause of "+pauseFor+" seconds");

                IsmaPersister.getLogByName("NDBMGMSYSTEM").error(" Try number = " +
                        (retry));

                monHandler.stopMonitoring((Map) listnerHandlers);
                CpInizializer.init();
                Thread.sleep(pauseFor);
                retry++ ;

                ((results) resultLocal).processSuccesflullyExecuted(true);
                this.setResult(resultLocal);
                this.setRequest(this.getRequest());

                /** @todo ADD check for restriction if class is Restricted */
                Results locRes = this.execute();

            } else {
                IsmaPersister.getLogByName("NDBMGMSYSTEM").error(
                        " It was NOT possible to fix the error monitor shutting down");



                ((results) resultLocal).processSuccesflullyExecuted(false);

                requestImpl req = this.getRequest();

                this.setResult(resultLocal);
                this.setRequest(this.getRequest());

                /** @todo ADD check for restriction if class is Restricted */
                Results locRes = this.execute();
                ((results)resultLocal).put("ndbMgm.init", Boolean.valueOf(false));
                return resultLocal;
//                System.exit(1);
            }

            ((results) resultLocal).processSuccesflullyExecuted(true);
            return resultLocal;
        }
        catch(Exception ex)
        {
            IsmaPersister.getLogByName("NDBMGMSYSTEM").error("No way to fix it shutting down");
            IsmaPersister.getLogSystem().error("No way to fix it stopping the monitor ");
            ((results)resultLocal).put("ndbMgm.init", Boolean.valueOf(false));

            System.exit(1);
        }
        return resultLocal;
    }

    private void RaiseException() throws IsmaException {
        throw new IsmaException("--- ERROR --- Monitor Handler could not connect to nodes");
    }

}

