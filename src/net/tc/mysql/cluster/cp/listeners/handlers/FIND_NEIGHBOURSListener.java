package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.FindNeighbours;

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
public class FIND_NEIGHBOURSListener extends FindNeighboursTypeListener {
    public FIND_NEIGHBOURSListener() {
    }

    public static void main(String[] args) {
        FIND_NEIGHBOURSListener find_neighbourslistener = new
                FIND_NEIGHBOURSListener();
    }

    @Override
    public void handleEvent(FindNeighbours event) {
        System.out.println(TimeTools.GetCurrentTime() +
                           " - Ndb Find Neighbours Node: " + event.getSourceNodeId() +
                           " Own ID: " + event.getOwnId() +
                           " Left ID: " + event.getLeftId() +
                           " Right ID: " + event.getRightId()

                );
    }
}
