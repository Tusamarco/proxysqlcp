package net.tc.mysql.cluster.cp.listeners.handlers;

import com.mysql.cluster.mgmj.listeners.*;

import net.tc.mysql.cluster.util.TimeTools;

import com.mysql.cluster.mgmj.events.JobStatistic;

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
public class JobStatisticListener extends JobStatisticTypeListener {
    public JobStatisticListener() {
    }

    public static void main(String[] args) {
        JobStatisticListener jobstatisticlistener = new JobStatisticListener();
    }

    @Override
    public void handleEvent(JobStatistic event) {

        System.out.println(TimeTools.GetCurrentTime() +
           " - Ndb Job Statistic  Node: "  + event.getSourceNodeId()   +
           " CPtr: " + event.getCPtr(event) +
           " Mean Loop Count: " + event.getMeanLoopCount()

);



    }

}
