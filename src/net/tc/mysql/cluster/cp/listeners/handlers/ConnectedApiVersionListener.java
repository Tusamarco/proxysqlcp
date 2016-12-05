package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.events.*;
import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.*;

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
public class ConnectedApiVersionListener extends
        ConnectedApiVersionTypeListener {
    public ConnectedApiVersionListener() {
    }

    public static void main(String[] args) {
        ConnectedApiVersionListener connectedapiversionlistener = new
                ConnectedApiVersionListener();
    }


    @Override
    public void handleEvent(ConnectedApiVersion event) {

        System.out.println(TimeTools.GetCurrentTime() +
                           " - Connected API node = " + event.getNode() + " version:" +
                                          + (short)(event.getVersion()>>16 & 0xFF)
                                          + "."
                                          + (short)(event.getVersion()>>8 & 0xFF)
                                          + "."
                                          + (short)(event.getVersion()>>0 & 0xFF)
                               );


    }

}
