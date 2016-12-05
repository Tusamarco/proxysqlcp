package net.tc.mysql.cluster.cp.objects;

import com.mysql.cluster.mgmj.*;
import com.mysql.cluster.mgmj.events.*;
import java.util.List;
import java.io.PrintWriter;

import net.tc.isma.persister.*;
import java.io.StringWriter;

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
public class LogEventManager implements Runnable {
    private NdbLogEventManager manager;
    private boolean isRunning;
    private EventBufferStatus eventBuffer;
    private boolean KeepRunning = false;
    private int poolingTime;
    private List runnableListners;

    public LogEventManager(NdbLogEventManager managerIn) {

        this.setManager(managerIn);

 }

    public static void main(String[] args) {
    }

    public void run() {

        KeepRunning = true;
        for (int i = 0; i < runnableListners.size(); i++) {
            try {
                manager.registerListener((NdbLogEventTypeListener)runnableListners.get(i));
            } catch (NdbMgmException ex1)
            {
                ex1.printStackTrace();
            }
        }


        try {
            while (KeepRunning) {

//                try{Thread.sleep(poolingTime);}catch(Throwable th){};

                manager.pollEvents(poolingTime);

            }
            return;

        } catch (NdbMgmException ex)
        {
             StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw);

             IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex);
             ex.printStackTrace(pw);
             IsmaPersister.getLogByName("NDBMGMSYSTEM").error(sw.toString());
        }

    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void setEventBuffer(EventBufferStatus eventBuffer) {
        this.eventBuffer = eventBuffer;
    }

    public void setPoolingTime(int poolingTime) {
        this.poolingTime = poolingTime;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public EventBufferStatus getEventBuffer() {
        return eventBuffer;
    }

    public int getPoolingTime() {
        return poolingTime;
    }

    public boolean Stop()
    {
        KeepRunning = false;
        return KeepRunning;
    }
    private void setManager(NdbLogEventManager managerIn)
    {
        manager = managerIn;
    }

    public void setRunnableListners(List runnableListners) {
        this.runnableListners = runnableListners;
    }

    public NdbLogEventManager getManager()
    {
        return manager;
    }

    public List getRunnableListners() {
        return runnableListners;
    }
}
