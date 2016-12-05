package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.StartPhaseCompleted;

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
public class StartPhaseCompletedListener extends
        StartPhaseCompletedTypeListener {
    public StartPhaseCompletedListener() {
    }

    /* (non-Javadoc)
     * @see com.mysql.cluster.mgmj.NdbLogEventCategoryListener#handleEvent(com.mysql.cluster.mgmj.NdbLogEvent)
     */
    @Override
    public void handleEvent(StartPhaseCompleted event) {
            System.out.println( TimeTools.GetCurrentTime()+" - Node " + event.getSourceNodeId() );
            //				   + ": start phase" + event.getPhase());
    }

}
