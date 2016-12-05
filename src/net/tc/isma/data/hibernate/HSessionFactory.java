package net.tc.isma.data.hibernate;

import javax.naming.*;

//import org.hibernate.persister.ClassPersister;
import org.hibernate.*;
import org.hibernate.type.*;
import java.sql.*;
import java.util.Enumeration;
import javax.servlet.*;
import net.tc.isma.data.*;
import java.util.Properties;
import org.hibernate.internal.SessionFactoryImpl;

import java.util.Map;
import org.hibernate.internal.SessionImpl;
import java.util.HashMap;
import org.hibernate.c3p0.internal.*;
import org.hibernate.persister.entity.EntityPersister;

import org.hibernate.type.ComponentType;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import java.io.Serializable;
import org.hibernate.exception.spi.SQLExceptionConverter;

/**
 * Factory for HibernateDatastoreSession. Hibernate reads the configuration and
 * mappings found in hibernate.cfg.xml (in the classpath) at instantiation of
 * this factory.  Factory should be kept as a singletonand used to create
 * new sessions as required.
 *
 * <p>Title: ISMA</p>
 * <p>Description: Information System Modular Architecture</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FAO of the UN</p>
 * @author
 * @version 1.0
 */
public class HSessionFactory
{
    Configuration configuration;
    SessionFactory sf;

    HSession session;
    ConnectionProvider conP;

    public HSessionFactory()
    {
        configuration = new Configuration();
        try {
            sf = configuration.configure().buildSessionFactory();
        } catch (HibernateException ex)
        {
            ex.printStackTrace();
        }

    }

//    public Databinder openDatabinder()
//    {
//        try
//        {
//            return sf.openDatabinder();
//        }
//        catch (HibernateException ex)
//        {
//            return null;
//        }
//    }

    public HSession openSession()
    {
//        if(session != null && session.isOpen())
//            return session;
        try
        {
        
            session  =  new HSession(this, sf.openSession());
            return session;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    public void closeSession(HSession session)
    {
        if(session.isOpen())
        {
            try
            {
                if(session.isOpen() && session.connection() != null && !session.connection().isClosed())
                  {
                      session.connection().commit();
                      session.connection().close();
                  }
                session.close();
                session = null;
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }
//    public ClassPersister getPersister(Class clazz) throws MappingException
//    {
//        return ( (SessionFactoryImpl) sf).getPersister(clazz);
//    }

    public HSession forceRefresh(HSession ses)
    {
            ses.flush();
            try
            {
                ses.connection().commit();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return null;

            }
            ses.disconnect();
            ses.reconnect();

            return (HSession)ses;
    }

    public Reference getReference() throws NamingException {
        return null;
    }

    public Session openSession(Connection connection) {
        return null;
    }

    public Session openSession(Interceptor interceptor) throws
            HibernateException {
        return null;
    }

    public Session openSession(Connection connection, Interceptor interceptor) {
        return null;
    }

    public ClassMetadata getClassMetadata(Class _class) throws
            HibernateException {
        return null;
    }

    public CollectionMetadata getCollectionMetadata(String string) throws
            HibernateException {
        return null;
    }

    public Map getAllClassMetadata() throws HibernateException {
        return null;
    }

    public Map getAllCollectionMetadata() throws HibernateException {
        return null;
    }

    public void close() throws HibernateException {
        sf.close();
    }

    public void evict(Class _class) throws HibernateException {
        ((Session) sf).evict(_class);    }

    public void evict(Class _class, Serializable serializable) throws
            HibernateException {
        ((HSessionFactory) sf).evict(_class, serializable);
    }

    public void evictCollection(String string) throws HibernateException {
        ((HSessionFactory) sf).evictCollection(string);
    }

    public void evictCollection(String string, Serializable serializable) throws
            HibernateException {
        ((HSessionFactory) sf).evictCollection(string, serializable);
    }

    public void evictQueries() throws HibernateException {
        ((HSessionFactory) sf).evictQueries();
    }

    public void evictQueries(String string) throws HibernateException {
        ((HSessionFactory) sf).evictQueries(string);
    }

//    public SQLExceptionConverter getSQLExceptionConverter() {
//       return  sf.getS.getSQLExceptionConverter();
//    }

    public Object getSingleObject( Class cls, Serializable id)
    {
        HSession s = openSession();
        Object o = null;
        if(cls != null && id != null)
        {

            try {
                 o = s.load(cls, id,LockMode.UPGRADE_NOWAIT);
                 closeSession(s);
            } catch (Exception ex) {
                closeSession(s);
                ex.printStackTrace();
            }
            finally
            {
                closeSession(s);
            }
        }

      return o;
    }
    public void deleteSingleObject(Object object)
    {
        HSession s = openSession();
        if(object != null)
        {

            try {
                s.beginTransaction();
                s.delete(object);
                s.connection().commit();
                closeSession(s);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void deleteSingleObject(Class clazz, Serializable id)
    {
        HSession s = openSession();
        Object object = getSingleObject(clazz, id);
        if(object != null)
        {

            try {
                Transaction t = s.beginTransaction();
                s.delete(object);
                t.commit();
                closeSession(s);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public Object getSingleObject( Class cls, Serializable id, LockMode lock)
    {
        HSession s = openSession();
        Object o = null;
        if(cls != null && id != null)
        {

            try {
                 o = s.load(cls, id, lock);
                 closeSession(s);
            } catch (Exception ex) {
                closeSession(s);
                ex.printStackTrace();
            }
        }

      return o;
    }


}
