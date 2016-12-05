package net.tc.isma.data.hibernate;
import net.tc.isma.persister.IsmaPersister;
import org.hibernate.*;
//import org.hibernate.type.*;
//import java.sql.*;
//import java.util.Enumeration;
//import javax.servlet.*;
//import java.util.Properties;
//import org.hibernate.impl.SessionFactoryImpl;
//import org.hibernate.persister.ClassPersister;
//import org.hibernate.MappingException;
//import java.util.Map;
//import org.hibernate.impl.SessionImpl;
//import java.util.HashMap;
//import org.hibernate.connection.C3P0ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
//import org.hibernate.persister.EntityPersister;
//import org.hibernate.type.ComponentType;
import org.hibernate.cfg.Configuration;

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
public class HibernateDatastoreSessionFactory
{
    static SessionFactory sf = null;
    ConnectionProvider conP;
    public static final ThreadLocal session = new ThreadLocal();

    public HibernateDatastoreSessionFactory()
    {
        try{
            sf = (SessionFactory)new Configuration().configure().buildSessionFactory();
        }catch(Throwable ex)
        {
           IsmaPersister.getLogDataAccess().error(ex);
           ex.printStackTrace();
        }

    }

    public HibernateDatastoreSessionFactory(String jndiName)
    {
        try{
            sf = (SessionFactory)new Configuration().configure().
                 buildSessionFactory();
        }catch(Exception ex)
        {
           IsmaPersister.getLogDataAccess().error(ex);
        }
    }


//    public static Session  openSession()
//    {
//            Session s = (Session) session.get();
//            // Open a new Session, if this Thread has none yet
//            if (s == null)
//            {
//                try {
//                    s = sf.openSession();
//                } catch (HibernateException ex)
//                {
//                    FaostatPersister.getLogDataAccess().error(ex);
//                }
//                session.set(s);
//            }
//        return s;
//    }
//    public static void closeSession()
//    {
//            try
//            {
//              Session s = (Session) session.get();
//              session.set(null);
//              if (s != null)
//              s.close();
//            }
//            catch (Exception ex)
//            {
//                FaostatPersister.getLogDataAccess().error(ex);
//            }
//
//    }
    public static Session  openSession()
    {
            Session s = null;//(Session) session.get();
            // Open a new Session, if this Thread has none yet
            if (s == null)
            {
                try {
                    s = sf.openSession();
                } catch (HibernateException ex)
                {
                    IsmaPersister.getLogDataAccess().error(ex);
                }
            }
        return s;
    }
    public static void closeSession(Session s)
    {
            try
            {
              if (s != null)
              s.close();
            }
            catch (Exception ex)
            {
                IsmaPersister.getLogDataAccess().error(ex);
            }

    }

//    public static Session forceRefresh(org.hibernate.Session ses)
//    {
//            try
//            {
//                ses.flush();
//                ses.re
//                ses.connection().commit();
//            }
//            catch (Exception ex)
//            {
//               IsmaPersister.getLogDataAccess().error(ex);
//            }
//            finally
//            {
//                try{
//                    ses.disconnect();
//                    ses.reconnect();
//                }
//                catch(Exception ex1)
//                {
//                    IsmaPersister.getLogDataAccess().error(ex1);
//                }
//            }
//            return (Session)ses;
//    }

}
