package net.tc.isma.data.hibernate;

import java.util.*;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import java.sql.*;

//import org.hibernate.persister.ClassPersister;
import org.hibernate.*;
import java.lang.InstantiationException;
//import org.hibernate.expression.Example;
import java.io.Serializable;
import net.tc.isma.data.*;
import net.tc.isma.data.hibernate.*;
import net.tc.isma.*;
import org.hibernate.type.Type;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.NativeQuery;

import net.tc.isma.data.jdbc.JdbcDatastoreSession;
import org.hibernate.stat.SessionStatistics;
import org.hibernate.criterion.Example;


/**
 * Implementation of DatastoreSession specific to Hibernate.  Wraps main
 * store/query mechanisms with a thin wrapper.  Also uses reflection to find
 * implementation of queries.
 *
 * <p>Title: </p>
 * <p>Description: Information System Modular Architecture</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FAO of the UN</p>
 * @author
 * @version 1.0
 */
public class HSession  extends JdbcDatastoreSession implements Session
{
    Session ses;
    Connection conn;

    HSessionFactory dsf;

    HSession(HSessionFactory dsf, Session ses)
    {
        try
        {
            this.conn = ((SessionImpl)ses).connection();
            this.dsf = dsf;
            this.ses = ses;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void close()
    {
        try
        {
            if(ses.isConnected())
            {
                ((HSession)ses).connection().close();
                ses.disconnect();
                ses.close();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        
    }

    public boolean isOpen()
    {
        return ses.isOpen();
    }


    public void flush()
    {
        try
        {
            ses.flush();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public Object load(Class cls, Serializable id)
    {
        try
        {
            return ses.load(cls, id);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
      return null;
    }

    public Object load(Class cls, Serializable id, LockMode lockMode)
    {
        try
        {
            return ses.load(cls, id, lockMode);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public void reconnect()
    {
        try
        {
            ses.reconnect(this.connection());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public Connection disconnect()
    {
        try
        {
            ses.disconnect();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

//    public List findHSQL(String hsql)throws DatastoreException
//    {
//        if(hsql == null || hsql.equals(""))
//            throw new DatastoreException("Query is null");
//        try
//        {
//                return ses.find(hsql);
//        }
//        catch (SQLException ex)
//        {
//            throw new DatastoreException(ex);
//        }
//        catch (HibernateException ex)
//        {
//            throw new DatastoreException(ex);
//        }
//    }

    public List findDirect(String query)
    {
        try
        {
            return ses.createQuery(query).list();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public List find(HybernateObject query, Class clazz)
    {
        List l = null;

        try
        {
            if (query != null && clazz != null)
            {
                Criteria crit = ses.createCriteria(clazz);
                l = crit.add(Example.create(query)).list();

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return l;
    }

//    public Iterator iterate(DataQueryBean query) throws DatastoreException
//    {
//        List res = find(query);
//
//        return res.iterator();
//    }

    public int delete(String toDelete)
    {

        try
        {

           ((SessionImpl)ses).delete(toDelete);
           return 0;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return 1;
        }
       
    }

    public Serializable save(Object object)
    {
        try
        {
            if (object instanceof HybernateObject)
                ((HybernateObject) object).onSave(ses);

            return ses.save(object);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public void save(Object object, Serializable id)
    {
        try
        {
//            if(object instanceof HybernateObject)
//                ((HybernateObject)object).onDelete(ses);
             if(id == null)
             {
                 id = net.tc.isma.utils.DbCounter.getCount("NCP","REPORTER", this);
                 ((HybernateObject)object).setId(id);
             }
             if (object instanceof HybernateObject)
                 ((HybernateObject) object).onSave(ses);

             ((HSession)ses).save(object, id);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public void saveOrUpdateFlush(Object object)
    {
        try
            {
                ses.beginTransaction();
                ses.saveOrUpdate(object);
                ses.flush();
                ((HSession)ses).connection().commit();
//                ses.close();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void saveOrUpdate(Object object)
    {
        try
        {
            if (object instanceof HybernateObject)
                ((HybernateObject) object).onSave(ses);
            ses.saveOrUpdate(object);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void update(Object object)
    {
        try
        {
            if (object instanceof HybernateObject)
                ((HybernateObject) object).onUpdate(ses);
            ses.update(object);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void delete(Object object)
    {
        try
        {
            if(object instanceof HybernateObject)
                ((HybernateObject)object).onDelete(ses);
            ses.delete(object);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


   public Transaction beginTransaction()
   {
       try
       {
           Transaction tx = ses.beginTransaction();
           return new HTransaction(tx);
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
       }
       return null;
   }
   public Connection connection()
   {
       try
       {
           return ((HSession)ses).connection();
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
           return null;
       }
   }

   /**
    * FOR DEBUG PURPOSES ONLY!! NOT TO BE USED FOR IMPLEMENTATION...
    * @return
    */
   public Session getHibernateSession()
   {
       return this.ses;
   }

    public void setFlushMode(FlushMode flushMode) {
    }

    public FlushModeType getFlushMode() {
        return null;
    }

    public SessionFactory getSessionFactory() {
        return null;
    }

    public void reconnect(Connection connection) throws HibernateException {
    }

    public void cancelQuery() throws HibernateException {
    }

    public boolean isConnected() {
        return false;
    }

    public boolean isDirty() throws HibernateException {
        return false;
    }

    public Serializable getIdentifier(Object object) throws HibernateException {
        return null;
    }

    public boolean contains(Object object) {
        return false;
    }

    public void evict(Object object) throws HibernateException {
    }

    public void load(Object object, Serializable serializable) throws
            HibernateException {
    }

    public void replicate(Object object, ReplicationMode replicationMode) throws
            HibernateException {
    }

    public void update(Object object, Serializable serializable) throws
            HibernateException {
    }

    public Object saveOrUpdateCopy(Object object) throws HibernateException {
        return null;
    }

    public Object saveOrUpdateCopy(Object object, Serializable serializable) throws
            HibernateException {
        return null;
    }

    public List find(String string) throws HibernateException {
        return null;
    }

    public List find(String string, Object object, Type type) throws
            HibernateException {
        return null;
    }

    public List find(String string, Object[] objectArray, Type[] typeArray) throws
            HibernateException {
        return null;
    }

    public Iterator iterate(String string) throws HibernateException {
        return null;
    }

    public Iterator iterate(String string, Object object, Type type) throws
            HibernateException {
        return null;
    }

    public Iterator iterate(String string, Object[] objectArray,
                            Type[] typeArray) throws HibernateException {
        return null;
    }

    public Collection filter(Object object, String string) throws
            HibernateException {
        return null;
    }

    public Collection filter(Object object, String string, Object object2,
                             Type type) throws HibernateException {
        return null;
    }

    public Collection filter(Object object, String string, Object[] objectArray,
                             Type[] typeArray) throws HibernateException {
        return null;
    }

    public int delete(String string, Object object, Type type) throws
            HibernateException {
        return 0;
    }

    public int delete(String string, Object[] objectArray, Type[] typeArray) throws
            HibernateException {
        return 0;
    }

    public void lock(Object object, LockMode lockMode) throws
            HibernateException {
    }

    public void refresh(Object object) throws HibernateException {
    }

    public void refresh(Object object, LockMode lockMode) throws
            HibernateException {
    }

    public LockMode getCurrentLockMode(Object object) throws HibernateException {
        return null;
    }

    public Criteria createCriteria(Class _class) {
        Criteria c =  ses.createCriteria(_class);
        return c;
    }

    public org.hibernate.query.Query createQuery(String string) throws HibernateException {
        return ses.createQuery(string);
    }

    public org.hibernate.query.Query createFilter(Object object, String string) throws
            HibernateException {
        return null;
    }

    public org.hibernate.query.Query getNamedQuery(String string) throws HibernateException {
        return null;
    }

    public Query createSQLQuery(String string, String string1, Class _class) {
        return null;
    }

    public Query createSQLQuery(String string, String[] stringArray,
                                Class[] classArray) {
        return null;
    }

    public void clear() {
    }

    public Object get(Class _class, Serializable serializable) throws
            HibernateException {
        return null;
    }

    public Object get(Class _class, Serializable serializable,
                      LockMode lockMode) throws HibernateException {
        return null;
    }

    public EntityMode getEntityMode() {
        return null;
    }

    public Session getSession(EntityMode entityMode) {
        return null;
    }

    public void setCacheMode(CacheMode cacheMode) {
    }

    public CacheMode getCacheMode() {
        return null;
    }

    public Object load(String string, Serializable serializable,
                       LockMode lockMode) throws HibernateException {
        return null;
    }

    public Object load(String string, Serializable serializable) throws
            HibernateException {
        return null;
    }

    public void replicate(String string, Object object,
                          ReplicationMode replicationMode) throws
            HibernateException {
    }

    public Serializable save(String string, Object object) throws
            HibernateException {
        return null;
    }

    public void saveOrUpdate(String string, Object object) throws
            HibernateException {
    }

    public void update(String string, Object object) throws HibernateException {
    }

    public Object merge(Object object) throws HibernateException {
        return null;
    }

    public Object merge(String string, Object object) throws HibernateException {
        return null;
    }

    public void persist(Object object) throws HibernateException {
    }

    public void persist(String string, Object object) throws HibernateException {
    }

    public void delete(String string, Object object) throws HibernateException {
    }

    public void lock(String string, Object object, LockMode lockMode) throws
            HibernateException {
    }

    public Transaction getTransaction() {
        return null;
    }

    public Criteria createCriteria(Class _class, String string) {
        return null;
    }

    public Criteria createCriteria(String string) {
        return null;
    }

    public Criteria createCriteria(String string, String string1) {
        return null;
    }

    public NativeQuery createSQLQuery(String string) throws HibernateException {
        return null;
    }

    public Object get(String string, Serializable serializable) throws
            HibernateException {
        return null;
    }

    public Object get(String string, Serializable serializable,
                      LockMode lockMode) throws HibernateException {
        return null;
    }

    public String getEntityName(Object object) throws HibernateException {
        return "";
    }

    public Filter enableFilter(String string) {
        return null;
    }

    public Filter getEnabledFilter(String string) {
        return null;
    }

    public void disableFilter(String string) {
    }

    public SessionStatistics getStatistics() {
        return null;
    }

    public void setReadOnly(Object object, boolean _boolean) {
    }


	@Override
	public ProcedureCall createStoredProcedureCall(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcedureCall createStoredProcedureCall(String arg0, Class... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcedureCall createStoredProcedureCall(String arg0, String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getJdbcBatchSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcedureCall getNamedProcedureCall(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTenantIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJdbcBatchSize(Integer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public org.hibernate.query.Query createNamedQuery(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeQuery createNativeQuery(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeQuery createNativeQuery(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeQuery getNamedNativeQuery(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> EntityGraph<T> createEntityGraph(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityGraph<?> createEntityGraph(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoredProcedureQuery createNamedStoredProcedureQuery(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeQuery<String> createNativeQuery(String arg0, Class arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String arg0, Class... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String arg0, String... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void detach(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, Map<String, Object> arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2, Map<String, Object> arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDelegate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityGraph<?> getEntityGraph(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LockModeType getLockMode(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Metamodel getMetamodel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getReference(Class<T> arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isJoinedToTransaction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lock(Object arg0, LockModeType arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lock(Object arg0, LockModeType arg1, Map<String, Object> arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object arg0, Map<String, Object> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object arg0, LockModeType arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object arg0, LockModeType arg1, Map<String, Object> arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFlushMode(FlushModeType arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProperty(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T unwrap(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addEventListeners(SessionEventListener... arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LockRequest buildLockRequest(LockOptions arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdentifierLoadAccess byId(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> IdentifierLoadAccess<T> byId(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> MultiIdentifierLoadAccess<T> byMultipleIds(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiIdentifierLoadAccess byMultipleIds(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NaturalIdLoadAccess byNaturalId(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> NaturalIdLoadAccess<T> byNaturalId(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SimpleNaturalIdLoadAccess bySimpleNaturalId(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> SimpleNaturalIdLoadAccess<T> bySimpleNaturalId(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> org.hibernate.query.Query<T> createNamedQuery(String arg0, Class<T> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> org.hibernate.query.Query<T> createQuery(CriteriaQuery<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public org.hibernate.query.Query createQuery(CriteriaUpdate arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public org.hibernate.query.Query createQuery(CriteriaDelete arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> org.hibernate.query.Query<T> createQuery(String arg0, Class<T> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disableFetchProfile(String arg0) throws UnknownProfileException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T doReturningWork(ReturningWork<T> arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doWork(Work arg0) throws HibernateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableFetchProfile(String arg0) throws UnknownProfileException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T get(Class<T> arg0, Serializable arg1, LockOptions arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(String arg0, Serializable arg1, LockOptions arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlushMode getHibernateFlushMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LobHelper getLobHelper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeHelper getTypeHelper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDefaultReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFetchProfileEnabled(String arg0) throws UnknownProfileException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReadOnly(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T load(Class<T> arg0, Serializable arg1, LockOptions arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object load(String arg0, Serializable arg1, LockOptions arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void refresh(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object arg0, LockOptions arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(String arg0, Object arg1, LockOptions arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SharedSessionBuilder sessionWithOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefaultReadOnly(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHibernateFlushMode(FlushMode arg0) {
		// TODO Auto-generated method stub
		
	}
}
