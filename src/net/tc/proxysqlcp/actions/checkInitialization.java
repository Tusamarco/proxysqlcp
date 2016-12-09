package net.tc.proxysqlcp.actions;


/**
 * <p>Title: proxysql / API</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Marco Tusa Copyright (c) 2017</p>
 *
 * <p>Company: Tusacentral.net </p>
 *
 * @author Marco Tusa
 * @version 1.0
 */

import java.io.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.*;

import org.hibernate.Transaction;

import com.mysql.jdbc.Connection;

import net.tc.isma.actions.*;
import net.tc.isma.actions.generic.*;
import net.tc.isma.data.hibernate.HSession;
import net.tc.isma.persister.*;
import net.tc.isma.resources.*;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import net.tc.isma.utils.SynchronizedMap;
import net.tc.mysql.cluster.cp.CpInizializer;
import net.tc.proxysql.MysqlServers;
import net.tc.isma.request.generic.requestImpl;

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
        SynchronizedMap listnerHandlers = new SynchronizedMap();



//        Connection conn = (Connection) IsmaPersister.getConnectionlIn();
        try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        Statement stmt = null;
		  try {
			  Connection conn= (Connection) DriverManager.getConnection("jdbc:mysql://192.168.0.12:3310/?user=admin&password=admin&autoReconnect=true");		
//				conn.setAutoCommit(false);
				stmt = conn.createStatement();
//				stmt.execute("SET AUTOCOMMIT=0");

			  stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery("Select * from mysql_servers");
		        while (rs.next()) {
		            String hostgroup_id = rs.getString("hostgroup_id");
		            String hostname = rs.getString("hostname");
		            String port = rs.getString("port");
		            
		            System.out.println(hostgroup_id + "\t" + hostname +
		                               "\t" + port);
		            
		            
//		            
//		            ************************ 1. row ***************************
//		            hostgroup_id: 500
//		                hostname: 192.168.0.21
//		                    port: 3306
//		                  status: ONLINE
//		                  weight: 1000000
//		             compression: 0
//		         max_connections: 1000
//		     max_replication_lag: 0
//		                 use_ssl: 0
//		          max_latency_ms: 0
//		                 comment: 
//
		            
		            
		        }
		    } catch (SQLException e ) {
		       e.printStackTrace();
		    } finally {
		        if (stmt != null) { try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} }
		    }
        
        if (req.getParameter("reinit") != null && !req.getParameter("reinit").equals("") && Integer.parseInt(req.getParameter("reinit")) > 0)
           reInit = true;

        
        loadProxyServers(true);
        
//       if(mgm != null && listnerHandlers != null && !reInit)
//       {
// TO BE REMOVED FOR RELOAD           resultLocal.put("inizilized", Boolean.valueOf(true));
//           resultLocal.put("NDBMGMListeners", listnerHandlers);
           return (Results) resultLocal;
//       }

//       try
//       {
//           CpInizializer.init();
//
//
//           mgm = (NdbMgm) IsmaPersister.get(NdbMgmImpl.class, "NDBMGM");
//           listnerHandlers = (SynchronizedMap) IsmaPersister.get(SynchronizedMap.class,
//                   "NDBMGMListeners");
//
//           if (mgm != null && listnerHandlers != null && !reInit) {
//               resultLocal.put("ndbMgm.init", Boolean.valueOf(true));
//               resultLocal.put("NDBMGMListeners", listnerHandlers);
//           }
//       }
//       catch (Exception ex)
//       {
//           IsmaPersister.getLogSystem().error(ex);
//           IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex);
//           resultLocal.processSuccesflullyExecuted(false, (Throwable)ex);
//
//       }
//       finally
//       {
//           return (Results) resultLocal;
//       }


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
  public Map loadProxyServers(boolean reload)
  {
      try
      {
          Map serverMap = null;
          serverMap = (SynchronizedMap)IsmaPersister.getModulesMap();

          IsmaPersister.getLogByName("PROXYSYSTEM").info("**** Initializing Servers [Start]");
          if (serverMap != null && !reload)
          {
              return serverMap;
          }
          serverMap = new SynchronizedMap();
          try
          {
              HSession ds = IsmaPersister.getSessionFactory().openSession();
              Transaction tr = ds.beginTransaction();
              String hSql = "select mod from net.tc.proxysql.MysqlServers as mod where mod.id.hostgroupId=500 order by mod.id.hostname";
              List l = ds.findDirect(hSql);
              ListIterator it = l.listIterator();
              while (it.hasNext())
              {
                  MysqlServers cMod = (MysqlServers) it.next();
//                  cMod.setGroup(getGroups(cMod, ds));
                  String id =cMod.getId().getHostgroupId() + "_" + cMod.getId().getHostname()+"_"+cMod.getId().getPort();
                  serverMap.put(id, cMod);

                  IsmaPersister.getLogByName("PROXYSYSTEM").info("**** Initializing Servers [" + id + "] " + cMod.toString());
              }
              IsmaPersister.getLogByName("PROXYSYSTEM").info("**** Initializing Servers [End]");
              tr.commit();
              IsmaPersister.getSessionFactory().closeSession(ds);


              return serverMap;

          }
          catch (Throwable ex)
          {
              IsmaPersister.getLogDataAccess().error(ex);
              ex.printStackTrace();
          }
          finally
          {
              return serverMap;
          }
      }
      catch (Throwable ex)
      {
          IsmaPersister.getLogDataAccess().error(ex);
          return null;
      }

  }

}

