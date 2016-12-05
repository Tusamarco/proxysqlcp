package net.tc.mysql.cluster.cp.objects;

import java.util.*;

import com.mysql.cluster.mgmj.*;
import net.tc.isma.persister.*;
import net.tc.isma.utils.SynchronizedMap;
import net.tc.isma.comparators.OrderByTimeProperty;


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
public class NdbMonitorHandler {

    boolean iSmonitoring = false;
    NdbStatus ndbStatus = null;

    public NdbMonitorHandler() {

    }



    /**
     * startMonitoring
     *
     * @return boolean
     */
    public boolean startMonitoring(Map categoryListners) {

        if(categoryListners == null || categoryListners.size() == 0)
            return false;

        if(!registerActiveListners(categoryListners))
            return false;

        IsmaPersister.getLogByName("NDBMGMSYSTEM").info(" -= Ready to start the monitor =- ");
//        System.out.println(" -= Ready to start the monitor =- ");


        Iterator it = categoryListners.keySet().iterator();
        try{
            while (it.hasNext()) {

                CategoryListner cat = (CategoryListner) categoryListners.get(it.
                        next());
                if (cat.isActive()) {

                    System.out.println("Starting :  " + cat.getName() + " Log Level = " + cat.getLogLevel()+ " Pooling Time = " + cat.getPoolingTime());
                    Thread th = new Thread((Runnable) cat.getManager());
                    th.start();
                }
            }

            iSmonitoring = true;
        }
        catch(Throwable th)
        {
            while (it.hasNext()) {

                CategoryListner cat = (CategoryListner) categoryListners.get(it.next());
                if (cat.isActive())
                {
                    cat.getManager().Stop();
                }
            }
            iSmonitoring = false;

        }
        return iSmonitoring;

    }

    /**
     * registerActiveListners
     *
     * @param categoryListner Map
     * @return boolean
     *
     * this is the main method to register and load Category listners and Listner event
     */
    private boolean registerActiveListners(Map categoryListners) {

        Iterator it = categoryListners.keySet().iterator();
        NdbMgm mgm = (NdbMgm)IsmaPersister.get(NdbMgmImpl.class, "NDBMGM");

        boolean hasFilter = false;
        while(it.hasNext())
        {
            CategoryListner cat = (CategoryListner)categoryListners.get(it.next());
            NdbLogEventCategory ndlogcat = null;
            if(cat.isActive())
            {
                ndlogcat = getEventcategory(cat.getFullreferencename().toUpperCase());

                if(ndlogcat != null)
                {
                    cat.add(new NdbFilterItem(cat.getLogLevel(),ndlogcat ));
                    IsmaPersister.getLogByName("NDBMGMSYSTEM").info(
                            "Registering log event category Name:" +
                            cat.getName() + " Log Level=" + cat.getLogLevel());

                    NdbLogEventManager manager = null;
                    LogEventManager logEventManager = null;

                    try {
                        manager = mgm.createNdbLogEventManager((NdbFilterList)cat);

                        if (manager != null) {
                            logEventManager = new LogEventManager(manager);
                        }
/** @todo
 * to evaluate if need to be included or not
 */
//                        logEventManager.setEventBuffer(new EventBufferStatus(manager.getLogEvent(0)));
//                        logEventManager.getEventBuffer().getUsage();



                        Map listnersMap = (Map) cat.getListners();
                        Iterator itl = listnersMap.keySet().iterator();

                        List lList = new ArrayList();

                        while (itl.hasNext())
                        {

                            ListnerHandler lh = (ListnerHandler) listnersMap.get(itl.next());

                            NdbLogEventTypeListener listener = null;
                            if(lh.isActive())
                            {
                                try {
                                    lList.add(Class.forName(lh.getPackagename() + "." + lh.getClazz()).newInstance());

                                } catch (ClassNotFoundException ex) {
                                    IsmaPersister.getLogByName("NDBMGMSYSTEM").
                                            error(ex);
                                } catch (IllegalAccessException ex) {
                                    IsmaPersister.getLogByName("NDBMGMSYSTEM").
                                            error(ex);
                                } catch (InstantiationException ex) {
                                    IsmaPersister.getLogByName("NDBMGMSYSTEM").
                                            error(ex);
                                }
                            }
                        }


                        logEventManager.setRunnableListners(lList);
                        logEventManager.setPoolingTime(cat.getPoolingTime());

                        cat.setManager(logEventManager);

                    } catch (NdbMgmException ex1) {
                        IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex1);
                    }
                    hasFilter = true;
                }
            }

        }

        return hasFilter;
    }

    /**
     * getEventcategory
     *
     * @param string String
     * @return NdbLogEventCategory
     */
    private NdbLogEventCategory getEventcategory(String catName) {


          if(catName.equals("NDB_MGM_ILLEGAL_EVENT_CATEGORY")) {return NdbLogEventCategory.NDB_MGM_ILLEGAL_EVENT_CATEGORY;}
          if(catName.equals("NDB_MGM_EVENT_CATEGORY_STARTUP")) {return NdbLogEventCategory.NDB_MGM_EVENT_CATEGORY_STARTUP;}
          if(catName.equals("NDB_MGM_EVENT_CATEGORY_SHUTDOWN")) {return NdbLogEventCategory.NDB_MGM_EVENT_CATEGORY_SHUTDOWN;}
          if(catName.equals("NDB_MGM_EVENT_CATEGORY_STATISTIC")) {return NdbLogEventCategory.NDB_MGM_EVENT_CATEGORY_STATISTIC;}
          if(catName.equals("NDB_MGM_EVENT_CATEGORY_CHECKPOINT")) {return NdbLogEventCategory.NDB_MGM_EVENT_CATEGORY_CHECKPOINT;}
          if(catName.equals("NDB_MGM_EVENT_CATEGORY_NODE_RESTART")) {return NdbLogEventCategory.NDB_MGM_EVENT_CATEGORY_NODE_RESTART;}
          if(catName.equals("NDB_MGM_EVENT_CATEGORY_CONNECTION")) {return NdbLogEventCategory.NDB_MGM_EVENT_CATEGORY_CONNECTION;}
          if(catName.equals("NDB_MGM_EVENT_CATEGORY_BACKUP")) {return NdbLogEventCategory.NDB_MGM_EVENT_CATEGORY_BACKUP;}
          if(catName.equals("NDB_MGM_EVENT_CATEGORY_CONGESTION")) {return NdbLogEventCategory.NDB_MGM_EVENT_CATEGORY_CONGESTION;}
          if(catName.equals("NDB_MGM_EVENT_CATEGORY_DEBUG")) {return NdbLogEventCategory.NDB_MGM_EVENT_CATEGORY_DEBUG;}
          if(catName.equals("NDB_MGM_EVENT_CATEGORY_DEBUG")) {return NdbLogEventCategory.NDB_MGM_EVENT_CATEGORY_DEBUG;}
          if(catName.equals("NDB_MGM_EVENT_CATEGORY_INFO")) {return NdbLogEventCategory.NDB_MGM_EVENT_CATEGORY_INFO;}
          if(catName.equals("NDB_MGM_EVENT_CATEGORY_ERROR")) {return NdbLogEventCategory.NDB_MGM_EVENT_CATEGORY_ERROR;}
          if(catName.equals("NDB_MGM_MIN_EVENT_CATEGORY")) {return NdbLogEventCategory.NDB_MGM_MIN_EVENT_CATEGORY;}
          if(catName.equals("NDB_MGM_MAX_EVENT_CATEGORY")) {return NdbLogEventCategory.NDB_MGM_MAX_EVENT_CATEGORY;}

        return null;
    }

    /**
     * stopMonitoring
     *
     * @return boolean
     */
    public boolean stopMonitoring(Map categoryListners) {


        Iterator it = categoryListners.keySet().iterator();
        try {
            while (it.hasNext()) {

                CategoryListner cat = (CategoryListner) categoryListners.get(it.
                        next());
                if (cat.isActive()) {

                    cat.getManager().Stop();
                }
            }

            iSmonitoring = false;
        } catch (Throwable th) {

            IsmaPersister.getLogByName("NDBMGMSYSTEM").error(th);
//            iSmonitoring = false;

        }
        return iSmonitoring;

    }

    /**
     * isMonitoring
     *
     * @return boolean
     */
    public boolean isMonitoring() {
        return iSmonitoring;
    }


    private void setNdbStatus (NdbStatus ndbStatusIn)
    {
        ndbStatus = ndbStatusIn;
    }

    public NdbStatus getInternalNdbStatus(){

//        if(ndbStatus == null)
//        {

        NdbMgm mgm = (NdbMgm)IsmaPersister.get(NdbMgmImpl.class, "NDBMGM");
        if(mgm != null)
        {
            try {
                ClusterState cs = mgm.getStatus();
                if(cs != null && cs.getNoOfNodes() > 0)
                {
                    ndbStatus =new NdbStatus();
                    ndbStatus.setState(ndbStatus.NDB_CLUSTER_STATE_OK);
                    ndbStatus.setIsRunning(true);

                    ndbStatus.setConnectedHost(mgm.getConnectedHost());
                    ndbStatus.setConnectedPort(mgm.getConnectedPort());
                    ndbStatus.setLastErrorCode(mgm.getLatestErrorCode());
                    ndbStatus.setLastErrorMsg(mgm.getLatestErrorMsg());
                    ndbStatus.setLastErrorDesc(mgm.getLatestErrorDesc());


                    Map nodes =  this.getNodeStatus(cs);
                    ndbStatus.setNodes(nodes);


                    this.setNdbStatus(ndbStatus);

                }

            } catch (NdbMgmException ex)
            {
                ex.printStackTrace();
                IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex);

                return null;
            }
            return ndbStatus;

        }
        else{
            return null;
        }
//        }
//        else{
//            return ndbStatus;
//        }
//        return null;
}


    public NdbStatus getNdbStatus(){

        if(ndbStatus != null)
        {
                return ndbStatus;

        }
            else{
                return null;
            }
    }

    private Map getNodeStatus(ClusterState cs) {
        Map nodes = new SynchronizedMap();
        for(int i=0; i< cs.getNoOfNodes(); i++)
        {

            NodeState ns = cs.getNodeState(i);

            String version = getInternalVersion(ns.getVersion());

            try{
                Node node = new Node(
                        ns.getNodeID(),
                        ns.getConnectAddress(),
                        ns.getConnectCount(),
                        (int) ns.getDynamicID(),
                        (int) ns.getNodeGroup(),
                        (String) ns.getNodeStatus().toString(),
                        (String) ns.getNodeType().toString(),
                        (int) ns.getStartPhase(),
                        (String) version);

                node.setApiVersion(getInternalVersion(ns.getMysqlVersion()).equals("")?null:"MySQL version " + getInternalVersion(ns.getMysqlVersion()));
//                node.setMemoryUsage(getMemoryElement(node));
                node = getMemoryElement(node);
                nodes.put(node.getId(),node);
            }
            catch(Exception ex1)
            {
                IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex1);
                return null;
            }
        }
        return nodes;
    }

    private String getInternalVersion(int vi) {
        if(vi == 0)
            return "";

        String version =  + (short)(vi>>16 & 0xFF)
                       + "."
                       + (short)(vi>>8 & 0xFF)
                       + "."
                       + (short)(vi>>0 & 0xFF);
        return version;
    }

    /**
     * getMemoryElement
     *
     * @return MemoryElement
     */
    private Node getMemoryElement(Node node) {
        Map CategoryListners = (Map) IsmaPersister.get(SynchronizedMap.class,
                "NDBMGMListeners");

        if (CategoryListners != null && CategoryListners.size() > 0) {
            try {

                Map listners = (Map) ((CategoryListner) CategoryListners.get(
                        "NDB_MGM_EVENT_CATEGORY_STATISTIC".toLowerCase())).
                               getListners();

                if (listners != null && listners.size() > 0) {
                    ListnerHandler lh = (ListnerHandler) listners.get(
                            "com.mysql.cluster.cp.listeners.handlers.MemoryUsageListener");

                    if (lh != null && lh.getListnerEvents() != null &&
                        lh.getListnerEvents().size() > 0)
                    {
                        ArrayList c = new ArrayList(lh.getListnerEvents().values());

                       /**
                        * get the memory info from the events collection and if the info exists for the
                        * current node it will associate it
                        */
                        Collections.sort(c,(Comparator)new OrderByTimeProperty());
                        Collections.reverse(c);

                        try{
                            for (int i = 0; i < c.size(); i++) {
                                if (node.getMemoryUsage() != null && node.getMemoryUsageIndex() != null)
                                    return node;
                                if (((ListnerEvent) c.get(i)).getNodeId() == node.getId()
                                    && ((MemoryElement) ((ListnerEvent) c.get(i))).getBlocks() == 249)
                                {
                                    node.setMemoryUsage((MemoryElement) ((
                                            ListnerEvent) c.get(i)));
                                } else if (((ListnerEvent) c.get(i)).getNodeId() == node.getId()
                                           &&
                                           ((MemoryElement) ((ListnerEvent) c.get(i))).getBlocks() == 248)
                                {
                                    node.setMemoryUsageIndex((MemoryElement) ((
                                            ListnerEvent) c.get(i)));
                                }
                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }

                }

            } catch (Exception ex) {
                IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex);
            }

        }

        return node;

    }
    public boolean checkForNodeFreeForAction(String action, int NodeId)
    {
        if (this.getNdbStatus() !=null && this.getNdbStatus().getNodes() !=null)
        {
            Map nodes = this.getNdbStatus().getNodes();

            if(nodes == null)
                return false;

            Node currentNode = (Node)nodes.get(NodeId);

            if(currentNode == null)
                return false;

            if(action.equals("stop"))
            {
                int nodeGroup = currentNode.getNodeGroup();
                Iterator it = nodes.keySet().iterator();

                while (it.hasNext()) {
                    Node partnerNode = (Node) nodes.get(it.next());
                    if (partnerNode.getNodeGroup() == nodeGroup && partnerNode.getId() != NodeId) {
                        if (partnerNode.getStatus().equals("NDB_MGM_NODE_STATUS_STARTED"))
                            return true;
                    }

                }
            }
            if(action.equals("start") && currentNode != null && !currentNode.getStatus().equals("NDB_MGM_NODE_STATUS_STARTED"))
            {
                return true;
            }

        }

        return false;
    }
    public Node getNode(int id)
    {
        if(this.getNdbStatus().getNodes() != null)
        {
            return (Node)this.getNdbStatus().getNodes().get(id);
        }

        return null;
    }
    public Map getNodeGroup(int id)
    {
        if(this.getNdbStatus().getNodes() != null && id > 0)
        {
                int nodeGroup = id;
                Map nodes = this.getNdbStatus().getNodes();
                Map nodesGroup = new SynchronizedMap(2);

                Iterator it = nodes.keySet().iterator();

                while (it.hasNext()) {
                    Node partnerNode = (Node) nodes.get(it.next());
                    if (partnerNode.getNodeGroup() == id) {
                        nodesGroup.put(partnerNode.getId(),partnerNode);
                    }

                }
                return nodesGroup;

        }

        return null;
    }

}
