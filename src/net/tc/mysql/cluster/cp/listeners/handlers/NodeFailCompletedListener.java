package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.NodeFailCompleted;

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
public class NodeFailCompletedListener extends NodeFailCompletedTypeListener {
    public NodeFailCompletedListener() {
    }
    /* (non-Javadoc)
     * @see com.mysql.cluster.mgmj.NdbLogEventCategoryListener#handleEvent(com.mysql.cluster.mgmj.NdbLogEvent)
     */
    @Override
    public void handleEvent(NodeFailCompleted event) {
            System.out.println( TimeTools.GetCurrentTime()+" - failed node:" + event.getFailedNode());
	}
}
