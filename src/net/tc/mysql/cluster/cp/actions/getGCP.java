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
public class getGCP extends actionImpl {
    public getGCP() {

    }
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

          CategoryListner cat = (CategoryListner)catHandlers.get("NDB_MGM_EVENT_CATEGORY_CHECKPOINT".toLowerCase());


          ListnerHandler lh = null;

          if(cat != null && cat.getListners().get("com.mysql.cluster.cp.listeners.handlers.GlobalCheckpointCompletedListener") != null)
          {
              lh = (ListnerHandler)cat.getListners().get("com.mysql.cluster.cp.listeners.handlers.GlobalCheckpointCompletedListener") ;
              lh.refreshReferences();
          }

          cat.setStatus(lh.getStatus());

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
