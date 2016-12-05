package net.tc.mysql.cluster.cp.actions;

import javax.servlet.http.*;

import net.tc.isma.actions.*;
import net.tc.isma.actions.generic.*;
import net.tc.isma.persister.*;
import net.tc.isma.utils.*;
import net.tc.mysql.cluster.cp.objects.*;


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
public class getMainWE extends actionImpl {
    public getMainWE() {
    }


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
    private results resultLocal = null;




  public Results execute() {
      resultLocal = (results)this.getResult();

      if (!resultLocal.processSuccesflullyExecuted())
          return (Results) resultLocal;

      try {

          HttpServletRequest req = this.getRequest();

          SynchronizedMap catHandlers = new SynchronizedMap();
          SynchronizedMap listnerHandlers = new SynchronizedMap();
          boolean monitorIsRunning = false;

          catHandlers = ((SynchronizedMap) IsmaPersister.get(
                  SynchronizedMap.class,
                  "NDBMGMListeners"));

          CategoryListner cat = (CategoryListner)catHandlers.get("NDB_MGM_EVENT_CATEGORY_ERROR".toLowerCase());

//
//          NDB_LE_TransporterError			:= 1,com.mysql.cluster.mgmj.listeners.handlers,TransporterErrorListener,1
//          NDB_LE_TransporterWarning		:= 1,com.mysql.cluster.mgmj.listeners.handlers,TransporterWarningListener,2
//          NDB_LE_MissedHeartbeat			:= 1,com.mysql.cluster.mgmj.listeners.handlers,MissedHeartbeatListener,3
//          NDB_LE_DeadDueToHeartbeat		:= 1,com.mysql.cluster.mgmj.listeners.handlers,DeadDueToHeartbeatListener,4
//          NDB_LE_WarningEvent			:= 1,com.mysql.cluster.mgmj.listeners.handlers,WarningEventListener,5


          ListnerHandler lhTe = null;
          ListnerHandler lhTw = null;
          ListnerHandler lhHbM = null;
          ListnerHandler lhHbD = null;
          ListnerHandler lhWe = null;


          if(cat != null && cat.getListners().get("com.mysql.cluster.cp.listeners.handlers.TransporterErrorListener") != null)
          {
              lhTe = (ListnerHandler)cat.getListners().get("com.mysql.cluster.cp.listeners.handlers.TransporterErrorListener") ;
              lhTe.refreshReferences();
          }


          if(cat != null && cat.getListners().get("com.mysql.cluster.cp.listeners.handlers.TransporterWarningListener") != null)
          {
              lhTw = (ListnerHandler)cat.getListners().get("com.mysql.cluster.cp.listeners.handlers.TransporterWarningListener") ;
              lhTw.refreshReferences();
          }
          if(cat != null && cat.getListners().get("com.mysql.cluster.cp.listeners.handlers.MissedHeartbeatListener") != null)
          {
              lhHbM = (ListnerHandler)cat.getListners().get("com.mysql.cluster.cp.listeners.handlers.MissedHeartbeatListener") ;
              lhHbM.refreshReferences();
          }
          if(cat != null && cat.getListners().get("com.mysql.cluster.cp.listeners.handlers.DeadDueToHeartbeatListener") != null)
          {
              lhHbD = (ListnerHandler)cat.getListners().get("com.mysql.cluster.cp.listeners.handlers.DeadDueToHeartbeatListener") ;
              lhHbD.refreshReferences();
          }


          if(cat != null && cat.getListners().get("com.mysql.cluster.cp.listeners.handlers.WarningEventListener") != null)
          {
              lhWe = (ListnerHandler)cat.getListners().get("com.mysql.cluster.cp.listeners.handlers.WarningEventListener") ;
              lhWe.refreshReferences();
          }

// To Be reconsidered
//          cat.setStatus(lh.getStatus());

          RangeHandler eventR = (RangeHandler) IsmaPersister.get(RangeHandler.class,"GlobalCheckpoint");

          if (eventR != null)
          {
              resultLocal.put("GlobalCheckpoint", eventR);
          }

      } catch (Exception ex) {

          IsmaPersister.getLogSystem().error(ex);
          IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex);
          resultLocal.processSuccesflullyExecuted(false, (Throwable) ex);

      } finally {
          return (Results) resultLocal;
      }



  }


}
