package net.tc.mysql.cluster.cp.objects;

import java.io.*;
import java.lang.ref.*;
import java.util.*;

import net.tc.isma.comparators.*;
import net.tc.isma.persister.*;
import net.tc.isma.utils.*;
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
public class ListnerHandler implements Serializable, SortableByOrder {
    private String name;
    private String clazz;
    private String packagename;
    private String category;
    private String type;
    private boolean active;
    private int order;
    private Object value_ok;
    private Object value_warning;
    private Object value_error;
    private String action_warning;
    private String action_error;
    private String message_ok;
    private String message_warning;
    private String message_error;
    private SynchronizedMap listnerEvents;

    /**
     * Status could be:
     * 0 = OK
     * 1 = WARNING
     * 2 = ERROR
     */
    private int status = 0 ;
    private String key;
    private int override;
    public ListnerHandler() {

    }
    /**
     * the listner handler is created passing an array with the desired values and the parent category
     *
     * e.g.
     *
     * String s = new String("nw1,com.mysql.cluster.mgmj.listeners.handlers,ConnectedListener,1")
     * String ref = s.split(",");
     *
     * setPackagename(ref[1]);
     * setClazz(ref[2]);
     * setName(ref[1] + "." + ref[2]);
     * setCategory(categoryName);
     *
     *
     */
    public ListnerHandler(String[] ref, String categoryName, String key) {

        if(ref == null || ref.length == 0)
            return;

        if(ref[0] != null && Integer.parseInt(ref[0]) > 0)
            this.setActive(true);
        else
            this.setActive(false);

        setPackagename(ref[1]);
        setClazz(ref[2]);
        setName(ref[1] + "." + ref[2]);
        setCategory(categoryName);

        setKey(key);

        loadInternalReferences(ref);


        if(ref[3] != null && !ref[3].equals(""))
            setOrder(Integer.parseInt(ref[3]));

        this.setListnerEvents(new SynchronizedMap(0));



    }

    /**
     * loadInternalReferences
     *
     * @param ref String[]
     */
    private void loadInternalReferences(String[] ref) {

        if(ref.length >= 5 && ref[4] != null)
           {
               this.setValue_ok(Text.isNumeric(ref[4])?Long.parseLong(ref[4]):ref[4]);
           }
           if(ref.length >= 6 && ref[5] != null)
           {
               this.setValue_warning(Text.isNumeric(ref[5])?Long.parseLong(ref[5]):ref[5]);
           }
           if(ref.length >= 7 && ref[6] != null)
           {
               this.setValue_error(Text.isNumeric(ref[6])?Long.parseLong(ref[6]):ref[6]);
           }

           if(ref.length >= 8 && ref[7] != null)
           {
               this.setOverride(Integer.parseInt(ref[7]));
           }


           if(ref.length >= 9 && ref[8] != null)
           {
               this.setMessage_ok(ref[8]);
           }

           if(ref.length >= 10 && ref[9] != null)
           {
               this.setMessage_warning(ref[9]);
           }
           if(ref.length >= 11 && ref[10] != null)
           {
               this.setMessage_error(ref[10]);
           }

           if(ref.length >= 12 && ref[11] != null)
           {
               this.setAction_warning(ref[11]);
           }
           if(ref.length >= 13 && ref[12] != null)
           {
               this.setAction_error(ref[12]);
           }


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

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setValue_ok(Object value_ok) {
        this.value_ok = value_ok;
    }

    public void setValue_warning(Object value_warning) {
        this.value_warning = value_warning;
    }

    public void setValue_error(Object value_error) {
        this.value_error = value_error;
    }

    public void setAction_warning(String action_warning) {
        this.action_warning = action_warning;
    }

    public void setAction_error(String action_error) {
        this.action_error = action_error;
    }

    public void setMessage_ok(String message_ok) {
        this.message_ok = message_ok;
    }

    public void setMessage_warning(String message_warning) {
        this.message_warning = message_warning;
    }

    public void setMessage_error(String message_error) {
        this.message_error = message_error;
    }

    public void setStatus(int statusIn) {

//        if(statusIn != this.status)
//        {
//            Map cats = (SynchronizedMap)IsmaPersister.get(SynchronizedMap.class, "NDBMGMListeners");
//            CategoryListner myCat = (CategoryListner)cats.get(this.getCategory());
//            if(myCat != null)
//                myCat.setStatus(this.getStatus());
//        }
        this.status = statusIn;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOverride(int override) {
        this.override = override;
    }

    public void setListnerEvents(Map listnerEventsIn) {
        listnerEvents = (SynchronizedMap)listnerEventsIn;
    }

    public String getName() {
        return name;
    }

    public String getClazz() {
        return clazz;
    }

    public String getPackagename() {
        return packagename;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    public int getOrder() {
        return order;
    }

    public Object getValue_ok() {
        return value_ok;
    }

    public Object getValue_warning() {
        return value_warning;
    }

    public Object getValue_error() {
        return value_error;
    }

    public String getAction_warning() {
        return action_warning;
    }

    public String getAction_error() {
        return action_error;
    }

    public String getMessage_ok() {
        return message_ok;
    }

    public String getMessage_warning() {
        return message_warning;
    }

    public String getMessage_error() {
        return message_error;
    }

    public int getStatus() {
        return status;
    }

    public String getKey() {
        return key;
    }

    public int getOverride() {
        return override;
    }

    public  Map getListnerEvents() {
        return listnerEvents;
    }

    /**
     * Event key is the System time of the event in milliseconds
     */
   public  ListnerEvent getEvent(Long eventKey)
   {
       if(eventKey == null)
           return null;

       return (ListnerEvent)listnerEvents.get(eventKey);
   }

   public  void setEvent(Long eventTime, ListnerEvent event)
   {
       if(eventTime == null || event == null)
           return;
       if(listnerEvents == null)
           this.setListnerEvents(new SynchronizedMap(1));
       listnerEvents.put(eventTime, event);
       this.purgeEvents();
   }

    public int purgeEvents()
    {
        Object purgeTime = IsmaPersister.getConfigParameterValueString("isma_configuration.ndbcpeventmemoryloglife");
        if(purgeTime != null)
        {
            purgeTime = (Long) Long.parseLong((String)purgeTime);
            Map events = this.getListnerEvents();
            Iterator it = events.keySet().iterator();
            int prevLength = events.size();
            int purged = 0;

            while (it.hasNext())
            {
                Long eventTime = (Long)it.next();
                long currentTime = System.currentTimeMillis();
                if(
                        ((currentTime - eventTime.longValue())/1000) > ((Long)purgeTime).longValue()
                  )
                {
                    events.remove(eventTime);
                    ++purged;
                    IsmaPersister.getLogByName("NDBMGMSYSTEM").debug("--- PURGE EVENT -- : Events purged = " + purged + " from "
                            + this.category
                            + " PREVIOUS LEN = " + prevLength
                            + " CURRENT LEN = " + events.size());

                }

            }

            this.setListnerEvents((Map)this.sort(events, true));

        }

        return 0;
    }

    public void refreshReferences()
    {
        File nF = null;

       if(IsmaPersister.getConfigParameterValueString("isma_configuration.listnerHandlerReference") != null
          && !IsmaPersister.getConfigParameterValueString("isma_configuration.listnerHandlerReference").equals("")
               )
       {
           nF = new File(IsmaPersister.getConfigParameterValueString("isma_configuration.listnerHandlerReference"));
           if(!nF.exists())
           {
               nF = new File(IsmaPersister.getMAINROOT() + "/" + IsmaPersister.getConfigParameterValueString("isma_configuration.listnerHandlerReference"));
           }

       }
       if(nF.exists() && nF.getName().indexOf("ini") > 0)
       {

           try {

               SoftReference sf = new SoftReference(new IniFile());
               IniFile ini = (IniFile)sf.get();
               ini.load(new FileReader(nF), true, "lh");

               if(ini.get(this.getKey()) != null)
                   this.loadInternalReferences(((String)ini.get(this.getKey())).split(","));

               ini =null;
               sf = null;


           } catch (NumberFormatException ex) {
               IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex);
               ex.printStackTrace();
           } catch (IOException ex) {
               IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex);
           }
       }



    }
    /**
     * Sort in asc or desc order any map from key value, key value MUST be Long
     */
    public  Map sort(Map mapToSort, boolean desc) {
        if (mapToSort != null) {
            List l = new ArrayList(mapToSort.keySet());
            Collections.sort(l,
                             (Comparator)new OrderBySystemTime());

            if (desc)
                Collections.reverse(l);

            Map nsubset = new SynchronizedMap(l.size());
            for (int i = 0; i < l.size(); i++) {
                Object k = l.get(i);
                nsubset.put(k, mapToSort.get(k));
            }
            mapToSort = nsubset;
        }
        return mapToSort;
    }


    public boolean getStatusOverride(int limit)
    {
//        System.out.println("override = " + getOverride() + " " + (override = 0));
        if(override == 0 )
            return true;

        if( getStatus() <= limit)
            return true;

        return false;
    }


}
