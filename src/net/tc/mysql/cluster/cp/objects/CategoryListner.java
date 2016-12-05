package net.tc.mysql.cluster.cp.objects;

import java.io.*;
import java.util.*;

import com.mysql.cluster.mgmj.*;
import net.tc.isma.comparators.*;
import net.tc.isma.utils.SynchronizedMap;

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

/**
 * Extends NdbFilterList
 * CategoryListner is the top most representation  of the
 * event it correspond to the Ndb_logevent_type categories
 * http://dev.mysql.com/doc/ndbapi/en/ndb-logevent-type.html
 *
 * these objects are stored in a Map and handled by the Cacheing system
 *
 * You should NOT inizialize them by yourself
 * instead take them from IsmaPersister.get(SynchronizedMap.class, "NDBMGMListeners");
 *
 * e.g.
 * HashMap listnerHandlers = (HashMap)IsmaPersister.get(SynchronizedMap.class, "NDBMGMListeners");
 *
 *
 */
public class CategoryListner extends NdbFilterList implements Serializable, SortableByOrder  {
    private String name;
    private boolean active;
    private int order;
    private SynchronizedMap listners;
    private String fullreferencename;
    private int logLevel;
    private LogEventManager manager;
    private int poolingTime;
    /**
     * Status could be:
     * 0 = OK
     * 1 = WARNING
     * 2 = ERROR
     */
    private int status =0 ;
    public CategoryListner() {
    }

    private void readObject(ObjectInputStream ois) throws IOException,
            ClassNotFoundException {
        ois.defaultReadObject();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setListners(Map listners) {
        this.listners = (SynchronizedMap)listners;
    }

    public void setFullreferencename(String fullreferencename) {
        this.fullreferencename = fullreferencename;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public void setManager(LogEventManager manager) {
        this.manager = manager;
    }

    public void setPoolingTime(int poolingTime) {
        this.poolingTime = poolingTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public int getOrder() {
        return order;
    }

    public Map getListners() {
        if(listners == null)
            listners = new SynchronizedMap(1);

        return listners;
    }

    public String getFullreferencename() {
        return fullreferencename;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public LogEventManager getManager() {
        return manager;
    }

    public int getPoolingTime() {
        return poolingTime;
    }

    public int getStatus() {
        return status;
    }


    public void loadListnerReferences(Map ref)
    {
        if(ref == null || ref.size() == 0)
        {
            return;
        }

        SynchronizedMap mListners = (SynchronizedMap) this.getListners();

        Iterator it = ref.keySet().iterator();
        while(it.hasNext())
        {
            String s = (String)it.next();
            String[] sa = s.split("\\.");
            if(sa[0].equals(this.getFullreferencename()))
            {
                ListnerHandler lh = new ListnerHandler(((String)ref.get(s)).split(","), this.getName(), s );
                mListners.put(lh.getClazz(),lh);

            }

        }

        setListners(sort(mListners, false));


    }
    public static Map sort(Map mapToSort, boolean desc) {
    if (mapToSort != null) {
        List l = new ArrayList(mapToSort.values());
        Collections.sort(l,(Comparator)new OrderByOrderProperty());

        if (desc)
            Collections.reverse(l);

        Map nsubset = new SynchronizedMap(l.size());
        for (int i = 0; i < l.size(); i++) {
            Object k = l.get(i);
            if(k instanceof SortableByOrder)
                nsubset.put(((SortableByOrder) k).getName(), k);
        }
        mapToSort = nsubset;
    }
    return mapToSort;
}

}
