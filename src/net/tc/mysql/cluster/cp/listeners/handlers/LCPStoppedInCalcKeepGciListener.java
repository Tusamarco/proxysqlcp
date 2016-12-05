package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.LCPStoppedInCalcKeepGci;

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
public class LCPStoppedInCalcKeepGciListener extends
        LCPStoppedInCalcKeepGciTypeListener {
    public LCPStoppedInCalcKeepGciListener() {
    }

    public static void main(String[] args) {
        LCPStoppedInCalcKeepGciListener lcpstoppedincalckeepgcilistener = new
                LCPStoppedInCalcKeepGciListener();
    }


    @Override
    public void handleEvent(LCPStoppedInCalcKeepGci event) {
        System.out.println(TimeTools.GetCurrentTime() +
                           " - LCP StoppedInCalcKeepGci:" + event.getCPtr(event) + " Node: "  + event.getSourceNodeId());
  }

}
