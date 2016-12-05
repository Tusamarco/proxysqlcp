package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.CmRegRef;

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
public class CM_REGREFListener extends CmRegRefTypeListener {
    public CM_REGREFListener() {
    }

    public static void main(String[] args) {
        CM_REGREFListener cm_regreflistener = new CM_REGREFListener();
    }

    @Override
    public void handleEvent(CmRegRef event) {
        System.out.println(TimeTools.GetCurrentTime() +
                      " - Cm Reg Conf:" + event.getCPtr(event) +
                      " Own Id:" + event.getOwnId() +
                      " Own OtherId:" + event.getOtherId() +
                      " Node: "  + event.getSourceNodeId());



    }



}
