package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.NRCopyDict;

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
public class NR_CopyDictListener extends NRCopyDictTypeListener {
    public NR_CopyDictListener() {
    }

    public static void main(String[] args) {
        NR_CopyDictListener nr_copydictlistener = new NR_CopyDictListener();
    }

    @Override
    public void handleEvent(NRCopyDict event) {
        System.out.println(TimeTools.GetCurrentTime() +
                   " - Ndb NR Copy Dictionary Node: "  + event.getSourceNodeId()   +
                   " CPtr: " + event.getCPtr(event)

        );


    }



}
