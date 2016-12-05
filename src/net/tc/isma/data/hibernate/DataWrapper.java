package net.tc.isma.data.hibernate;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
//import org.hibernate.expression.Expression;
//import org.hibernate.expression.Order;
import org.hibernate.type.Type;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;




/**
 * This class will not be overwritten once it has been created. You may change
 * any code here if it does not suit your needs. This is the superclass of all
 * DAOs.
 */
public class DataWrapper
{

    private   SessionFactory sessionFactory = null;

//    private DynaObjectPersister dynaPersister;

    /**
     * configure the session factory by reading hibernate.cfg.xml
     */
    public void initialize() throws Exception
    {
        if (null == sessionFactory)
        {
            Configuration cfg = new Configuration();
            cfg.configure();
            sessionFactory = cfg.buildSessionFactory();
//            dynaPersister = new DynaObjectPersister();
        }

    }

    /**
     * Return the SessionFactory that is to be used by these DAOs. Change this
     * and implement your own strategy if you, for example, want to pull the
     * SessionFactory from the JNDI tree.
     */
    protected  Object getSessionFactory() throws Exception //SessionFactory
    {
        if (null == sessionFactory)
            throw new RuntimeException(
                    "You must call initialize() the DataWrapper before requesting the Data SessionFactory");
        else
            return sessionFactory;

    }

//
//    /**
//     * Return a new Session object that must be closed when the work has been
//     * completed.
//     *
//     * @return the active Session
//     */
//    public Session getSession() throws HibernateException
//    {
//        return getSessionFactory().openSession();
//    }
//
//    /**
//     * Execute a query.
//     *
//     * @param query
//     *            a query expressed in Hibernate's query language
//     * @return a distinct list of instances (or arrays of instances)
//     */
//    public java.util.List find(String query) throws HibernateException
//    {
//        Session s = null;
//        try
//        {
//            s = getSession();
//            return find(query, s);
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Perform a find but use the session given instead of creating a new one.
//     * @param query a query expressed in Hibernate's query language
//     * @s the Session to use
//     */
//    public java.util.List find(String query, Session s)
//            throws HibernateException
//    {
//        return s.find(query);
//    }
//
//    /**
//     * Return all objects related to the implementation of this DAO with no
//     * filter.
//     */
//    public java.util.List findAll(Class refClass) throws HibernateException
//    {
//        Session s = null;
//        try
//        {
//            s = getSession();
//            return findAll(refClass, s);
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Return all objects related to the implementation of this DAO with no
//     * filter. Use the session given.
//     *
//     * @param s
//     *            the Session
//     */
//    public java.util.List findAll(Class refClass, Session s)
//            throws HibernateException
//    {
//        return findAll(refClass, s, getDefaultOrderProperty());
//    }
//
//    /**
//     * Return all objects related to the implementation of this DAO with no
//     * filter.
//     */
//    public java.util.List findAll(Class refClass, String orderProperty)
//            throws HibernateException
//    {
//        Session s = null;
//        try
//        {
//            s = getSession();
//            return findAll(refClass, s, orderProperty);
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Return all objects related to the implementation of this DAO with no
//     * filter. Use the session given.
//     *
//     * @param s
//     *            the Session
//     */
//    public java.util.List findAll(Class refClass, Session s,
//            String orderProperty) throws HibernateException
//    {
//        Criteria crit = s.createCriteria(refClass);
//        if (null != orderProperty) crit.addOrder(Order.asc(orderProperty));
//        return crit.list();
//    }
//
//    /**
//     * Return all objects related to the implementation of this DAO with a
//     * filter. Use the session given.
//     *
//     * @param propName
//     *            the name of the property to use for filtering
//     * @param filter
//     *            the value of the filter
//     */
//    public java.util.List findFiltered(Class refClass, String propName,
//            Object filter) throws HibernateException
//    {
//        return findFiltered(refClass, propName, filter,
//                getDefaultOrderProperty());
//    }
//
//    /**
//     * Return all objects related to the implementation of this DAO with a
//     * filter. Use the session given.
//     *
//     * @param propName
//     *            the name of the property to use for filtering
//     * @param filter
//     *            the value of the filter
//     * @param orderProperty
//     *            the name of the property used for ordering
//     */
//    public java.util.List findFiltered(Class refClass, String propName,
//            Object filter, String orderProperty) throws HibernateException
//    {
//        Session s = null;
//        try
//        {
//            s = getSession();
//            return findFiltered(s, propName, filter, getDefaultOrderProperty(),
//                    refClass);
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    public java.util.List findFiltered(Class refClass, String propName, Object[] filter) throws HibernateException
//    {
//        return findFiltered(refClass, propName, filter, getDefaultOrderProperty());
//    }
//
//    public java.util.List findFiltered(Class refClass, String propName, Object[] filter, String orderProperty) throws HibernateException
//    {
//        Session s = null;
//        try
//        {
//            s = getSession();
//            return findFiltered(s, propName, filter, getDefaultOrderProperty(),
//                    refClass);
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Return all objects related to the implementation of this DAO with a
//     * filter. Use the session given.
//     *
//     * @param s
//     *            the Session
//     * @param propName
//     *            the name of the property to use for filtering
//     * @param filter
//     *            the value of the filter
//     * @param orderProperty
//     *            the name of the property used for ordering
//     */
//    public java.util.List findFiltered(Session s, String propName,
//            Object filter, String orderProperty, Class refClass)
//            throws HibernateException
//    {
//        Criteria crit = s.createCriteria(refClass);
//        crit.add(Expression.eq(propName, filter));
//        if (null != orderProperty) crit.addOrder(Order.asc(orderProperty));
//        return crit.list();
//    }
//
//    public java.util.List findFiltered(Session s, String propName,
//            Object[] filter, String orderProperty, Class refClass)
//            throws HibernateException
//    {
//        Criteria crit = s.createCriteria(refClass);
//        crit.add(Expression.in(propName, filter));
//        if (null != orderProperty) crit.addOrder(Order.asc(orderProperty));
//        return crit.list();
//    }
//
//    /**
//     * Obtain an instance of Query for a named query string defined in the
//     * mapping file.
//     *
//     * @param name
//     *            the name of a query defined externally
//     * @return Query
//     */
//    public java.util.List getNamedQuery(String name) throws HibernateException
//    {
//        Session s = null;
//        try
//        {
//            s = getSession();
//            return getNamedQuery(name, s);
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Obtain an instance of Query for a named query string defined in the
//     * mapping file. Use the session given.
//     *
//     * @param name
//     *            the name of a query defined externally
//     * @param s
//     *            the Session
//     * @return Query
//     */
//    public java.util.List getNamedQuery(String name, Session s)
//            throws HibernateException
//    {
//        Query q = s.getNamedQuery(name);
//        return q.list();
//    }
//
//    /**
//     * Obtain an instance of Query for a named query string defined in the
//     * mapping file. Use the parameters given.
//     *
//     * @param name
//     *            the name of a query defined externally
//     * @param params
//     *            the parameter array
//     * @return Query
//     */
//    public java.util.List getNamedQuery(String name, Serializable[] params)
//            throws HibernateException
//    {
//        Session s = null;
//        try
//        {
//            s = getSession();
//            return getNamedQuery(name, params, s);
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Obtain an instance of Query for a named query string defined in the mapping file.
//     * Use the parameters given and the Session given.
//     * @param name the name of a query defined externally
//     * @param params the parameter array
//     * @s the Session
//     * @return Query
//     */
//    public java.util.List getNamedQuery(String name, Serializable[] params,
//            Session s) throws HibernateException
//    {
//        Query q = s.getNamedQuery(name);
//        if (null != params)
//        {
//            for (int i = 0; i < params.length; i++)
//            {
//                setParameterValue(q, i, params[i]);
//            }
//        }
//        return q.list();
//    }
//
//    /**
//     * Obtain an instance of Query for a named query string defined in the
//     * mapping file. Use the parameters given.
//     *
//     * @param name
//     *            the name of a query defined externally
//     * @param params
//     *            the parameter Map
//     * @return Query
//     */
//    public java.util.List getNamedQuery(String name, Map params)
//            throws HibernateException
//    {
//        Session s = null;
//        try
//        {
//            s = getSession();
//            return getNamedQuery(name, params, s);
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Obtain an instance of Query for a named query string defined in the mapping file.
//     * Use the parameters given and the Session given.
//     * @param name the name of a query defined externally
//     * @param params the parameter Map
//     * @s the Session
//     * @return Query
//     */
//    public java.util.List getNamedQuery(String name, Map params, Session s)
//            throws HibernateException
//    {
//        Query q = s.getNamedQuery(name);
//        if (null != params)
//        {
//            for (Iterator i = params.entrySet().iterator(); i.hasNext();)
//            {
//                Map.Entry entry = (Map.Entry) i.next();
//                setParameterValue(q, (String) entry.getKey(), entry.getValue());
//            }
//        }
//        return q.list();
//    }
//
//    /**
//     * Execute a query.
//     *
//     * @param query
//     *            a query expressed in Hibernate's query language
//     * @return a distinct list of instances (or arrays of instances)
//     */
//    public java.util.List find(String query, Object obj, Type type)
//            throws HibernateException
//    {
//        Session s = null;
//        try
//        {
//            s = getSession();
//            return find(query, obj, type, s);
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Perform a find but use the session given instead of creating a new one.
//     * @param query a query expressed in Hibernate's query language
//     * @s the Session to use
//     */
//    public java.util.List find(String query, Object obj, Type type, Session s)
//            throws HibernateException
//    {
//        return s.find(query, obj, type);
//    }
//
//    /**
//     * Execute a query.
//     *
//     * @param query
//     *            a query expressed in Hibernate's query language
//     * @return a distinct list of instances (or arrays of instances)
//     */
//    public java.util.List find(String query, Object[] obj, Type[] type)
//            throws HibernateException
//    {
//        Session s = null;
//        try
//        {
//            s = getSession();
//            return find(query, obj, type, s);
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Perform a find but use the session given instead of creating a new one.
//     * @param query a query expressed in Hibernate's query language
//     * @s the Session to use
//     */
//    public java.util.List find(String query, Object[] obj, Type[] type,
//            Session s) throws HibernateException
//    {
//        return s.find(query, obj, type);
//    }
//
//    /**
//     * Used by the base DAO classes but here for your modification Load object
//     * matching the given key and return it.
//     */
//    public Object load(Class refClass, Serializable key)
//            throws HibernateException, DynaObjectException
//    {
//        Session s = null;
//        try
//        {
//            s = getSession();
//            return load(refClass, key, s);
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Used by the base DAO classes but here for your modification Load object
//     * matching the given key and return it.
//     */
//    public Object get(Class refClass, Serializable key)
//            throws HibernateException, DynaObjectException
//    {
//        Session s = null;
//        try
//        {
//            s = getSession();
//            return get(refClass, key, s);
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Used by the base DAO classes but here for your modification Load object
//     * matching the given key and return it.
//     */
//    public Object get(Class refClass, Serializable key, Session s)
//            throws HibernateException, DynaObjectException
//    {
//        Object obj = s.get(refClass, key);
//
//        if (obj instanceof DynaObject
//                && dynaPersister.isAutoLoad(obj.getClass()))
//        {
//            ((DynaObject) obj).clearDynaProps();
//            dynaPersister.load(s.connection(), (DynaObject) obj);
//        }
//
//        return obj;
//    }
//    /**
//     * Used by the base DAO classes but here for your modification Load object
//     * matching the given key and return it.
//     */
//    public Object load(Class refClass, Serializable key, Session s)
//            throws HibernateException, DynaObjectException
//    {
//        Object obj = s.load(refClass, key);
//
//        if (obj instanceof DynaObject
//                && dynaPersister.isAutoLoad(obj.getClass()))
//        {
//            ((DynaObject) obj).clearDynaProps();
//            dynaPersister.load(s.connection(), (DynaObject) obj);
//        }
//
//        return obj;
//    }
//
//    public Serializable save(Object obj) throws HibernateException,
//    	DynaObjectException
//	{
//        return save(obj, dynaPersister.isAutoSave(obj.getClass()));
//    }
//
//    /**
//     * Used by the base DAO classes but here for your modification Persist the
//     * given transient instance, first assigning a generated identifier. (Or
//     * using the current value of the identifier property if the assigned
//     * generator is used.)
//     */
//    public Serializable save(Object obj, boolean saveDynaProps) throws HibernateException,
//            DynaObjectException
//    {
//        Transaction t = null;
//        Session s = null;
//        try
//        {
//            s = getSession();
//            t = s.beginTransaction();
//
//            Serializable rtn = save(obj, saveDynaProps, s);
//
//            t.commit();
//            return rtn;
//        }
//        catch (HibernateException e)
//        {
//            if (null != t) t.rollback();
//            throw e;
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Used by the base DAO classes but here for your modification Persist the
//     * given transient instance, first assigning a generated identifier. (Or
//     * using the current value of the identifier property if the assigned
//     * generator is used.)
//     */
//    public Serializable save(Object obj, boolean saveDynaProps, Session s) throws HibernateException,
//            DynaObjectException
//    {
//        Serializable rtn = s.save(obj);
//        if (obj instanceof DynaObject && saveDynaProps)
//        {
//            s.flush();
//            DynaObject dyn = (DynaObject) obj;
//            dynaPersister.setId(dyn, rtn);
//            dynaPersister.save(s.connection(), dyn);
//        }
//
//        return rtn;
//    }
//    public void saveOrUpdate(Object obj) throws HibernateException,
//    	DynaObjectException
//    {
//        saveOrUpdate(obj, dynaPersister.isAutoUpdate(obj.getClass()));
//    }
//    /**
//     * Used by the base DAO classes but here for your modification Either save()
//     * or update() the given instance, depending upon the value of its
//     * identifier property.
//     */
//    public void saveOrUpdate(Object obj, boolean saveDynaProps) throws HibernateException,
//            DynaObjectException
//    {
//        Transaction t = null;
//        Session s = null;
//        try
//        {
//            s = getSession();
//            t = s.beginTransaction();
//            saveOrUpdate(obj, saveDynaProps, s);
//            t.commit();
//        }
//        catch (HibernateException e)
//        {
//            if (null != t) t.rollback();
//            throw e;
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Used by the base DAO classes but here for your modification Either save()
//     * or update() the given instance, depending upon the value of its
//     * identifier property.
//     */
//    public void saveOrUpdate(Object obj, boolean saveDynaProps, Session s) throws HibernateException,
//            DynaObjectException
//    {
//        s.saveOrUpdate(obj);
//
//        if (obj instanceof DynaObject && saveDynaProps)
//        {
//            s.flush();
//            dynaPersister.saveOrUpdate(s.connection(), (DynaObject) obj);
//        }
//    }
//
//    /**
//     * Used by the base DAO classes but here for your modification Update the
//     * persistent state associated with the given identifier. An exception is
//     * thrown if there is a persistent instance with the same identifier in the
//     * current session.
//     *
//     * @param obj
//     *            a transient instance containing updated state
//     */
//    public void update(Object obj) throws HibernateException,
//            DynaObjectException
//    {
//        Transaction t = null;
//        Session s = null;
//        try
//        {
//            s = getSession();
//            t = s.beginTransaction();
//            update(obj, s);
//            t.commit();
//        }
//        catch (HibernateException e)
//        {
//            if (null != t) t.rollback();
//            throw e;
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Used by the base DAO classes but here for your modification Update the
//     * persistent state associated with the given identifier. An exception is
//     * thrown if there is a persistent instance with the same identifier in the
//     * current session.
//     *
//     * @param obj
//     *            a transient instance containing updated state
//     * @param s
//     *            the Session
//     */
//    public void update(Object obj, Session s) throws HibernateException,
//            DynaObjectException
//    {
//        s.update(obj);
//
//        if (obj instanceof DynaObject
//                && dynaPersister.isAutoSave(obj.getClass()))
//        {
//            s.flush();
//            dynaPersister.save(s.connection(), (DynaObject) obj);
//        }
//    }
//
//    /**
//     * Used by the base DAO classes but here for your modification Remove a
//     * persistent instance from the datastore. The argument may be an instance
//     * associated with the receiving Session or a transient instance with an
//     * identifier associated with existing persistent state.
//     */
//    public void delete(Object obj) throws HibernateException
//    {
//        Transaction t = null;
//        Session s = null;
//        try
//        {
//            s = getSession();
//            t = s.beginTransaction();
//            delete(obj, s);
//            t.commit();
//        }
//        catch (HibernateException e)
//        {
//            if (null != t) t.rollback();
//            throw e;
//        }
//        finally
//        {
//            if (null != s) s.close();
//        }
//    }
//
//    /**
//     * Used by the base DAO classes but here for your modification Remove a
//     * persistent instance from the datastore. The argument may be an instance
//     * associated with the receiving Session or a transient instance with an
//     * identifier associated with existing persistent state.
//     */
//    public void delete(Object obj, Session s) throws HibernateException
//    {
//        if (obj instanceof DynaObject
//                && dynaPersister.isAutoDelete(obj.getClass()))
//        {
//            ((DynaObject) obj).clearDynaProps();
//        }
//
//        s.delete(obj);
//    }
//
//    /**
//     * Used by the base DAO classes but here for your modification Re-read the
//     * state of the given instance from the underlying database. It is
//     * inadvisable to use this to implement long-running sessions that span many
//     * business tasks. This method is, however, useful in certain special
//     * circumstances.
//     */
//    public void refresh(Object obj, Session s) throws HibernateException,
//            DynaObjectException
//    {
//        if (obj instanceof DynaObject
//                && dynaPersister.isAutoLoad(obj.getClass()))
//        {
//            ((DynaObject) obj).clearDynaProps();
//            dynaPersister.load(s.connection(), (DynaObject) obj);
//        }
//
//        s.refresh(obj);
//    }
//
//    /**
//     * Return the property of the class you would like to use for default
//     * ordering
//     *
//     * @return the property name
//     */
//    public String getDefaultOrderProperty()
//    {
//        return null;
//    }
//
//    /**
//     * Convenience method to set paramers in the query given based on the actual
//     * object type in passed in as the value. You may need to add more
//     * functionaly to this as desired (or not use this at all).
//     *
//     * @param query
//     *            the Query to set
//     * @param position
//     *            the ordinal position of the current parameter within the query
//     * @param value
//     *            the object to set as the parameter
//     */
//    protected void setParameterValue(Query query, int position, Object value)
//    {
//        if (null == value)
//        {
//            return;
//        }
//        else if (value instanceof Boolean)
//        {
//            query.setBoolean(position, ((Boolean) value).booleanValue());
//        }
//        else if (value instanceof String)
//        {
//            query.setString(position, (String) value);
//        }
//        else if (value instanceof Integer)
//        {
//            query.setInteger(position, ((Integer) value).intValue());
//        }
//        else if (value instanceof Long)
//        {
//            query.setLong(position, ((Long) value).longValue());
//        }
//        else if (value instanceof Float)
//        {
//            query.setFloat(position, ((Float) value).floatValue());
//        }
//        else if (value instanceof Double)
//        {
//            query.setDouble(position, ((Double) value).doubleValue());
//        }
//        else if (value instanceof BigDecimal)
//        {
//            query.setBigDecimal(position, (BigDecimal) value);
//        }
//        else if (value instanceof Byte)
//        {
//            query.setByte(position, ((Byte) value).byteValue());
//        }
//        else if (value instanceof Calendar)
//        {
//            query.setCalendar(position, (Calendar) value);
//        }
//        else if (value instanceof Character)
//        {
//            query.setCharacter(position, ((Character) value).charValue());
//        }
//        else if (value instanceof Timestamp)
//        {
//            query.setTimestamp(position, (Timestamp) value);
//        }
//        else if (value instanceof Date)
//        {
//            query.setDate(position, (Date) value);
//        }
//        else if (value instanceof Short)
//        {
//            query.setShort(position, ((Short) value).shortValue());
//        }
//    }
//
//    /**
//     * Convenience method to set paramers in the query given based on the actual
//     * object type in passed in as the value. You may need to add more
//     * functionaly to this as desired (or not use this at all).
//     *
//     * @param query
//     *            the Query to set
//     * @param key
//     *            the key name
//     * @param value
//     *            the object to set as the parameter
//     */
//    protected void setParameterValue(Query query, String key, Object value)
//    {
//        if (null == key || null == value)
//        {
//            return;
//        }
//        else if (value instanceof Boolean)
//        {
//            query.setBoolean(key, ((Boolean) value).booleanValue());
//        }
//        else if (value instanceof String)
//        {
//            query.setString(key, (String) value);
//        }
//        else if (value instanceof Integer)
//        {
//            query.setInteger(key, ((Integer) value).intValue());
//        }
//        else if (value instanceof Long)
//        {
//            query.setLong(key, ((Long) value).longValue());
//        }
//        else if (value instanceof Float)
//        {
//            query.setFloat(key, ((Float) value).floatValue());
//        }
//        else if (value instanceof Double)
//        {
//            query.setDouble(key, ((Double) value).doubleValue());
//        }
//        else if (value instanceof BigDecimal)
//        {
//            query.setBigDecimal(key, (BigDecimal) value);
//        }
//        else if (value instanceof Byte)
//        {
//            query.setByte(key, ((Byte) value).byteValue());
//        }
//        else if (value instanceof Calendar)
//        {
//            query.setCalendar(key, (Calendar) value);
//        }
//        else if (value instanceof Character)
//        {
//            query.setCharacter(key, ((Character) value).charValue());
//        }
//        else if (value instanceof Timestamp)
//        {
//            query.setTimestamp(key, (Timestamp) value);
//        }
//        else if (value instanceof Date)
//        {
//            query.setDate(key, (Date) value);
//        }
//        else if (value instanceof Short)
//        {
//            query.setShort(key, ((Short) value).shortValue());
//        }
//    }
//
//    /**
//     * @return
//     */
//    public DynaObjectPersister getDynaObjectPersister()
//    {
//        return dynaPersister;
//    }
//
//    /**
//     * @param o
//     * @param req
//     */
//    public static void setFromRequest(Object o, HttpServletRequest req)
//    	throws DynaObjectException
//    {
//        setFromParameterMap(o, req.getParameterMap());
//    }
//
//    /**
//     * @param o
//     * @param map
//     */
//    public static void setFromParameterMap(Object o, Map map)
//    	throws DynaObjectException
//    {
//        Map params = new HashMap();
//
//        try
//        {
//            // Set nulls first
//            for (Iterator iter = map.keySet().iterator(); iter.hasNext();)
//            {
//                String name = (String) iter.next();
//                char c = name.charAt(0);
//
//                if ( StringUtil.isAlpha(c) )
//                {
//                    String[] vals = (String[]) map.get(name);
//
//                    if (StringUtil.isNullOrEmpty(vals))
//                    {
//                        PropertyUtils.setProperty(o, name, null);
////                        o.getClass().getMethod("set"+StringUtil.capitalise(name), );
//                        //BeanUtils.setProperty(o, name, null);
//                    }
//                    else
//                    {
//                        params.put(name, vals);
//                    }
//
////                    String[] vals = (String[]) params.get(name);
////                    BeanUtils.setProperty(o, name, vals[0]);
//                }
//            }
//
//            // Then set remaining properties
//            BeanUtils.populate(o, params);
//
//            if ( o instanceof DynaObject )
//            {
//                DynaObjectPersister.setFromParameterMap((DynaObject)o, map);
//            }
//        }
//        catch (IllegalAccessException e)
//        {
//            throw new DynaObjectException("Error setting bean properties.",e);
//        }
//        catch (InvocationTargetException e)
//        {
//            throw new DynaObjectException("Error setting bean properties.",e);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            throw new DynaObjectException("Error setting bean properties.",e);
//        } catch (SecurityException e) {
//            e.printStackTrace();
//            throw new DynaObjectException("Error setting bean properties.",e);
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//            throw new DynaObjectException("Error setting bean properties.",e);
//        }
//    }

}
