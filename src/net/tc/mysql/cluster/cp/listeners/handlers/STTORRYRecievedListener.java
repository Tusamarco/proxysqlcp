package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.STTORRYRecieved;

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
public class STTORRYRecievedListener extends STTORRYRecievedTypeListener {
    public STTORRYRecievedListener() {
    }

    public static void main(String[] args) {
        STTORRYRecievedListener sttorryrecievedlistener = new
                STTORRYRecievedListener();
    }

    @Override
    public void handleEvent(STTORRYRecieved event) {
        System.out.println(TimeTools.GetCurrentTime() +
                          " - STTORRYRecieved:" + event.getCPtr(event) + " Node: "  + event.getSourceNodeId());

    }


}
