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

import java.io.*;

import javax.servlet.http.*;

import net.tc.isma.actions.*;
import net.tc.isma.actions.generic.*;
import net.tc.isma.persister.*;
import net.tc.isma.resources.*;

import com.mysql.cluster.mgmj.NdbMgm;
import java.util.Map;
import net.tc.isma.utils.SynchronizedMap;
import net.tc.mysql.cluster.cp.CpInizializer;
import net.tc.isma.request.generic.requestImpl;
import com.mysql.cluster.mgmj.NdbMgmImpl;

public class checkInitialization extends actionImpl {

    private results resultLocal = null;


    public checkInitialization() {
    }

    public Results execute() {
        resultLocal = (results)this.getResult();

        if (!resultLocal.processSuccesflullyExecuted())
            return (Results) resultLocal;


        requestImpl req = this.getRequest();
        boolean reInit = false;
        NdbMgm mgm  = null;
        SynchronizedMap listnerHandlers = new SynchronizedMap();

        mgm = (NdbMgm)IsmaPersister.get(NdbMgmImpl.class, "NDBMGM");
        listnerHandlers = (SynchronizedMap)IsmaPersister.get(SynchronizedMap.class, "NDBMGMListeners");


        String ndbMgmUri = (String) IsmaPersister.get(String.class, "NdbMgmUri");
        if (req.getParameter("reinit") != null && !req.getParameter("reinit").equals("") && Integer.parseInt(req.getParameter("reinit")) > 0)
           reInit = true;

       if(mgm != null && listnerHandlers != null && !reInit)
       {
           resultLocal.put("ndbMgm.init", Boolean.valueOf(true));
           resultLocal.put("NDBMGMListeners", listnerHandlers);
           return (Results) resultLocal;
       }

       try
       {
           CpInizializer.init();


           mgm = (NdbMgm) IsmaPersister.get(NdbMgmImpl.class, "NDBMGM");
           listnerHandlers = (SynchronizedMap) IsmaPersister.get(SynchronizedMap.class,
                   "NDBMGMListeners");

           if (mgm != null && listnerHandlers != null && !reInit) {
               resultLocal.put("ndbMgm.init", Boolean.valueOf(true));
               resultLocal.put("NDBMGMListeners", listnerHandlers);
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


      /*

       String ndbMgmHostName = IsmaPersister.getConfigParameterValueString("isma_configuration.ndbmgmhost");
       String mgmHostPort = IsmaPersister.getConfigParameterValueString("isma_configuration.ndbmgmport");
       boolean enableNdbMgmLogsOnDisk = false;

        if(IsmaPersister.getConfigParameterValueString("isma_configuration.enableNdbMgmLogsOnDisk") != null
           && !IsmaPersister.getConfigParameterValueString("isma_configuration.enableNdbMgmLogsOnDisk").equals("")
           && Boolean.parseBoolean(IsmaPersister.getConfigParameterValueString("isma_configuration.enableNdbMgmLogsOnDisk"))
                )
            enableNdbMgmLogsOnDisk = true;

        if(ndbMgmHostName != null
           && !ndbMgmHostName.equals("")
           && mgmHostPort !=null
           && !mgmHostPort.equals("")
                )
        {
            ndbMgmUri = ndbMgmHostName + ":" + mgmHostPort;

            Long reportId = null;
            Long themeId = null;

            Long step = null;
            Long stepId = null;

            Integer level = null;

            Long objOrder = null;

            if (req.getParameter("reportid") != null &&
                !req.getParameter("reportid").equals(""))
                reportId = new Long(req.getParameter("reportid"));

            if (req.getParameter("themeid") != null &&
                !req.getParameter("themeid").equals(""))
                themeId = new Long(req.getParameter("themeid"));

            if (req.getParameter("step") != null &&
                !req.getParameter("step").equals(""))
                step = new Long(req.getParameter("step"));

            if (req.getParameter("stepid") != null &&
                !req.getParameter("stepid").equals(""))
                stepId = new Long(req.getParameter("stepid"));

            if (req.getParameter("level") != null &&
                !req.getParameter("level").equals(""))
                level = new Integer(req.getParameter("level"));

            if (!resultLocal.processSuccesflullyExecuted())
                return (Results) resultLocal;

            try {
                        Reports report = null;
                        if (reportId == null || themeId == null || level == null)
                            return (Results) resultLocal;

                        try {
                 report = (Reports) IsmaPersister.get(Reports.class, "CurrentReport");
                            if (report == null)
                                throw new Exception("Report null");

                            Themes th = report.getThemeById(themeId);
                            Tobjects to = null;
                            HybernateObject oh = null;

                            switch (level.intValue()) {
                            case 0:

                 th.setStep(this.defineXStep((Themes) th, step, stepId));
                                break;

                            }


                            resultLocal.put(Reports.class, report);
                 */


    }

    private File getConFile()
    {
        return null;

    }
}

