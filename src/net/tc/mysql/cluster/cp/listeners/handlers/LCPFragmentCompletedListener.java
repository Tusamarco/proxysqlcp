package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.LCPFragmentCompleted;

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
public class LCPFragmentCompletedListener extends
        LCPFragmentCompletedTypeListener {
    public LCPFragmentCompletedListener() {
    }

    public static void main(String[] args) {
        LCPFragmentCompletedListener lcpfragmentcompletedlistener = new
                LCPFragmentCompletedListener();
    }

    @Override
    public void handleEvent(LCPFragmentCompleted event) {

        System.out.println(TimeTools.GetCurrentTime() +
                           " - LCP FragmentCompleted:" + event.getCPtr(event) + " Node: "  + event.getSourceNodeId());
    }

}
