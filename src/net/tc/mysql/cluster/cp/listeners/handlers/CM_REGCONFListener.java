package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.CmRegConf;

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
public class CM_REGCONFListener extends CmRegConfTypeListener {
    public CM_REGCONFListener() {
    }

    public static void main(String[] args) {
        CM_REGCONFListener cm_regconflistener = new CM_REGCONFListener();
    }

    @Override
    public void handleEvent(CmRegConf event) {

        System.out.println(TimeTools.GetCurrentTime() +
                           " - Cm Reg Conf:" + event.getCPtr(event) +
                           " Own Id:" + event.getOwnId() +
                           " Own PresidentId:" + event.getPresidentId() +
                           " Node: "  + event.getSourceNodeId());


    }

}
