package net.tc.isma.data.hibernate;

import net.tc.isma.data.common.*;
import java.util.Map;
import java.util.Iterator;
import net.tc.isma.utils.SynchronizedMap;
import net.tc.isma.utils.Text;
import org.hibernate.Session;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import net.tc.isma.comparators.HibernateOrderComparator;
import java.util.Collection;
import net.tc.isma.utils.file.FileHandler;
import java.io.File;
import java.io.FileWriter;
import net.tc.isma.persister.IsmaPersister;
import java.lang.ref.SoftReference;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class HybernateObject extends HybernateObjectIdx {
    public HybernateObject() {
    }

    public abstract Object getId();
    public abstract void setId(Object id);
    public abstract Long getOrder();
    public abstract void setOrder(Long order);
    public abstract Long getType();
    public abstract void setType(Long type);
    public abstract Long getStatus();
    public abstract void setStatus(Long status);

    public static Object getHybernateObjById(Map m, Object id)
    {
        if(m != null && m.size() > 0 && id != null)
        {
            Iterator it = m.values().iterator();
            while(it.hasNext())
            {
                Object obj = it.next();
                if(((HybernateObject)obj).getId().equals(id))
                    return obj;
            }
        }

        return null;
    }
    public abstract void setParent(HybernateObject obj);
    public abstract HybernateObject getParent();
    public abstract void onDelete(Session s);
    /**
     * getMapAsStringCSV
     *
     * @param metaHeaderMap Map
     * @return String[]
     */
    public String[] getMapAsStringCSV(Map m)
    {
        return getMapAsStringCSV(m, true);
    }
    public String[] getMapAsStringCSV(Map m, boolean addCommaAtTheEnd)
    {
        if(m == null)
            return null;

        String[] mapAsString = new String[2];
        StringBuffer k = new StringBuffer();
        StringBuffer v = new StringBuffer();

        Object[] itk = ((SynchronizedMap)m).getKeyasOrderedArray();
        for(int i = 0 ; i < itk.length ; i++)
        {
            if(k.length() > 0)
            {
                k.append(",");
                v.append(",");
            }
            Object key = itk[i];
            Object value = m.get(key);
            k.append(key.toString());
            v.append(Text.defaultTo((String)value," "));

        }
        if(addCommaAtTheEnd)
        {
            mapAsString[0] = k.toString() + ",";
            mapAsString[1] = v.toString() + ",";
        }
        else
        {
            mapAsString[0] = k.toString();
            mapAsString[1] = v.toString();

        }
        return mapAsString;
    }
    public Map getStringCSVasMap(String keys, String values)
    {

        String[] k = keys.split(",");
        String[] v = values.split(",");
        Map m = new SynchronizedMap(k.length);

        for (int i = 0; i < k.length; i++) {
            m.put(k[i], v[i]);
        }
        if (m != null && m.size() > 0) {
            return m;
        }

        return null;

    }
    public Set internalIterateSet(HybernateObject obj, Set sOriginal)
    {

        Set s = new HashSet(0);
        boolean addItem = false;
        Iterator it = sOriginal.iterator();
        while(it.hasNext())
        {
            HybernateObject o = (HybernateObject)it.next();
            if(obj.getId() == null)
                addItem = true;
            else if(obj.getId().equals(o.getId()))
             {
                 addItem = true;
                 sOriginal.remove(o);
                 break;
             }
             else
                 addItem = false;
        }
        if(addItem)
            s.add(obj);

        sOriginal.addAll(s);
        return sOriginal;
    }
    public Set internalRemoveSet(HybernateObject obj, Set sOriginal)
    {

        Set s = new HashSet(0);
        Iterator it = sOriginal.iterator();
        while(it.hasNext())
        {
            HybernateObject o = (HybernateObject)it.next();
            if(obj.getId().equals(o.getId()))
                s.remove(obj);
            else
                s.add(o);
        }
        return s;
    }

    public HybernateObject internalIterateGet(Object id, Set sOriginal)
    {
        if(sOriginal == null)
            return null;

        Iterator it = sOriginal.iterator();
        while(it.hasNext())
        {
            HybernateObject o = (HybernateObject)it.next();
            if(o.getId().equals(id))
                return o;

        }
        return null;
    }

    public Set defineOrder(HybernateObject o , int order, Collection s)
    {
        if(s == null  || s.size() == 0 || s.size() == 1)
            return (Set)s;

        Set sr = new HashSet(0);
        if(o == null || s == null)
            return null;
        int currentPosition = o.getOrder().intValue();
        int length = s.size();
        List l = new ArrayList(s);
        Collections.sort(l, new HibernateOrderComparator());
        if((order == -1 || order ==1) && (currentPosition > 0 || currentPosition < length))
        {
            HybernateObject objNearby = (HybernateObject ) l.get(currentPosition + order);
            objNearby.setOrder(new Long(currentPosition));
            o.setOrder(new Long(currentPosition + order));
            l.set(currentPosition + order, o);
            l.set(currentPosition , objNearby);
            sr = new HashSet(l);
        }
        else if(order == 999999 || order == -999999)
        {
            List lr = new ArrayList(0);
            if(order == 999999)
            {

                for (int i = 0; i < length; i++) {
                    if (!l.get(i).equals(o)) {
                        HybernateObject to = (HybernateObject) l.get(i);
                        to.setOrder(new Long(i));
                        lr.add(i, to);
                    }

                }
                o.setOrder(new Long(lr.size()));
                lr.add(lr.size()-1, o );
                Collections.sort(lr, new HibernateOrderComparator());
                sr = new HashSet(lr);
            }
            else
            {
                o.setOrder(new Long(0));
                lr.add(o);
                for(int i = 0 ; i < l.size() ; i++)
                {
                    HybernateObject to = (HybernateObject) l.get(i);
                    if(!to.getId().equals(o.getId()))
                    {
                        to.setOrder(new Long(lr.size()));
                        lr.add(to);
                    }
                }
                Collections.sort(lr, new HibernateOrderComparator());
                sr = new HashSet(lr);
            }
        }
        return sr;
    }
    public abstract void onSave(Session s);
    public abstract void onUpdate(Session s);

    public boolean deleteXml(String path)
    {
        if(path == null || path.equals(""))
            return false;

        try{
            FileHandler fh = new FileHandler(path);
            if (fh.getIn().exists())
            {
                fh.deleteFile();
                fh.getIn().getParentFile().delete();

                fh = null;
                return  true;
            }
            else if(fh.getIn().getParentFile().exists())
                fh.getIn().getParentFile().delete();


        }
        catch(Exception ex)
        {
            IsmaPersister.getLogDataAccess().error(ex);
            ex.printStackTrace();
            return  false;
        }

        return  false;
    }

    public String readXml(String path)
    {
        String s2r = null;
        if(path == null || path.equals(""))
            return null;
        try{
            FileHandler fh = new FileHandler(path);
            if (!fh.getIn().exists())
                return null;

            s2r = fh.readXmlString();
        }
        catch(Exception ex)
        {
            IsmaPersister.getLogDataAccess().error(ex);
            ex.printStackTrace();
        }

        return s2r;

    }
    public void writeXml(String path, String xml, String charset)
    {
        if(path == null || path.equals("") || xml == null || xml.equals(""))
            return;
        try{

            FileHandler fh = new FileHandler(path);
            if(fh.getIn().exists())
                fh.deleteFile();

            fh.writeToFile(xml);
//
//            SoftReference sf = new SoftReference(new FileHandler());
//            File f = (File)sf.get();
//
//            if(f.exists())
//                f.delete();
//
//
//            SoftReference sf1 = new SoftReference(new FileWriter(path));
//            FileWriter fw = (FileWriter)sf1.get();
//            fw.write(xml);
//            fw.flush();
//            fw.close();
//            sf1.enqueue();
//            sf1 = null;

        }
        catch(Exception ex)
        {
            IsmaPersister.getLogDataAccess().error(ex);
            ex.printStackTrace();
        }


    }
    public String getXml(String path)
    {
        String xml = null;



        return xml;

    }
    public abstract void setXmlPath(String xmlpath);
}

