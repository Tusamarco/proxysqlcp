package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.TransReportCounters;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TransReportListener extends TransReportCountersTypeListener {
    public TransReportListener() {}

        @Override
        public void handleEvent(TransReportCounters event) {
            if ((event.getTransCount() != 0)
                || (event.getReadCount() != 0)
                || (event.getScanCount() != 0)
                || (event.getRangeScanCount() != 0)) {
                System.out.println(TimeTools.GetCurrentTime() +
                                   " - Listner Trans count:" + event.getTransCount());
                System.out.println(TimeTools.GetCurrentTime() +
                                   " - Listner Read count:" + event.getReadCount());
                System.out.println(TimeTools.GetCurrentTime() +
                                   " - Listner Scan count:" + event.getScanCount());
                System.out.println(TimeTools.GetCurrentTime() +
                                   " - Listner Range Scan count:" +
                                   event.getRangeScanCount());
            }

        }
    }
