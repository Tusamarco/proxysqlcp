package net.tc.isma.data.hibernate;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.apache.commons.logging.*;
import org.hibernate.internal.*;

import javax.naming.*;
import net.tc.isma.IsmaException;
import net.tc.isma.persister.IsmaPersister;
import org.apache.log4j.Logger;

/**
 * Basic Hibernate helper class, handles SessionFactory, Session and Transaction.
 * <p>
 * Uses a static initializer for the initial SessionFactory creation
 * and holds Session and Transactions in thread local variables. All
 * exceptions are wrapped in an unchecked IsmaException.
 *
 * @author christian@hibernate.org
 */
public class HibernateUtil {

    private static Logger log = IsmaPersister.getLogDataAccess();

	private static Configuration configuration;
	private static SessionFactory sessionFactory;
	private static final ThreadLocal threadSession = new ThreadLocal();
	private static final ThreadLocal threadTransaction = new ThreadLocal();
	private static final ThreadLocal threadInterceptor = new ThreadLocal();

	// Create the initial SessionFactory from the default configuration files
	static {
		try {
			configuration = new Configuration();
			sessionFactory = configuration.configure().buildSessionFactory();
			// We could also let Hibernate bind it to JNDI:
			// configuration.configure().buildSessionFactory()
		} catch (Throwable ex) {
			// We have to catch Throwable, otherwise we will miss
			// NoClassDefFoundError and other subclasses of Error
			log.error("Building SessionFactory failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Returns the SessionFactory used for this static class.
	 *
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		/* Instead of a static variable, use JNDI:
		SessionFactory sessions = null;
		try {
			Context ctx = new InitialContext();
			String jndiName = "java:hibernate/HibernateFactory";
			sessions = (SessionFactory)ctx.lookup(jndiName);
		} catch (NamingException ex) {
			throw new IsmaException(ex);
		}
		return sessions;
		*/
		return sessionFactory;
	}

	/**
	 * Returns the original Hibernate configuration.
	 *
	 * @return Configuration
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * Rebuild the SessionFactory with the static Configuration.
	 *
	 */
	 public static void rebuildSessionFactory()
		throws IsmaException {
		synchronized(sessionFactory) {
			try {
				sessionFactory = getConfiguration().buildSessionFactory();
			} catch (Exception ex) {
				throw new IsmaException(ex);
			}
		}
	 }

	/**
	 * Rebuild the SessionFactory with the given Hibernate Configuration.
	 *
	 * @param cfg
	 */
	 public static void rebuildSessionFactory(Configuration cfg)
		throws IsmaException {
		synchronized(sessionFactory) {
			try {
				sessionFactory = cfg.buildSessionFactory();
				configuration = cfg;
			} catch (Exception ex) {
				throw new IsmaException(ex);
			}
		}
	 }

	/**
	 * Retrieves the current Session local to the thread.
	 * <p/>
	 * If no Session is open, opens a new Session for the running thread.
	 *
	 * @return Session
	 */
	public static Session getSession()
		{
		Session s = (Session) threadSession.get();
		try {
			if (s == null) {
				log.debug("Opening new Session for this thread.");
				if (getInterceptor() != null) {
					log.debug("Using interceptor: " + getInterceptor().getClass());
					s = getSessionFactory().openSession();
				} else {
					s = getSessionFactory().openSession();
				}
				threadSession.set(s);
			}
		} catch (HibernateException ex) {
			log.equals(ex);
		}
		return s;
	}

	/**
	 * Closes the Session local to the thread.
	 */
	public static void closeSession()
		{
		try {
			Session s = (Session) threadSession.get();
			threadSession.set(null);
			if (s != null && s.isOpen()) {
				log.debug("Closing Session of this thread.");
				s.close();
			}
		} catch (HibernateException ex) {
			log.error(ex);
		}
	}

	/**
	 * Start a new database transaction.
	 */
	public static void beginTransaction()
		throws IsmaException {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			if (tx == null) {
				log.debug("Starting new database transaction in this thread.");
				tx = getSession().beginTransaction();
				threadTransaction.set(tx);
			}
		} catch (HibernateException ex) {
			throw new IsmaException(ex);
		}
	}

	/**
	 * Commit the database transaction.
	 */
	public static void commitTransaction()
		throws IsmaException {
		net.tc.isma.data.hibernate.HTransaction tx = (HTransaction) threadTransaction.get();
		try {
			if ( tx != null && !tx.wasCommitted()
							&& !tx.wasRolledBack() ) {
				log.debug("Committing database transaction of this thread.");
				tx.commit();
			}
			threadTransaction.set(null);
		} catch (HibernateException ex) {
			rollbackTransaction();
			throw new IsmaException(ex);
		}
	}

	/**
	 * Commit the database transaction.
	 */
	public static void rollbackTransaction()
		throws IsmaException {
		net.tc.isma.data.hibernate.HTransaction   tx = (HTransaction) threadTransaction.get();
		try {
			threadTransaction.set(null);
			if ( tx != null && !tx.wasCommitted() && !tx.wasRolledBack() ) {
				log.debug("Tyring to rollback database transaction of this thread.");
				tx.rollback();
			}
		} catch (HibernateException ex) {
			throw new IsmaException(ex);
		} finally {
			closeSession();
		}
	}

	/**
	 * Reconnects a Hibernate Session to the current Thread.
	 *
	 * @param session The Hibernate Session to be reconnected.
	 */
	public static void reconnect(HSession session)
		throws IsmaException {
		try {
			session.reconnect(session.connection());
			threadSession.set(session);
		} catch (HibernateException ex) {
			throw new IsmaException(ex);
		}
	}

	/**
	 * Disconnect and return Session from current Thread.
	 *
	 * @return Session the disconnected Session
	 */
	public static Session disconnectSession()
		throws IsmaException {

		Session session = getSession();
		try {
			threadSession.set(null);
			if (session.isConnected() && session.isOpen())
				session.disconnect();
		} catch (HibernateException ex) {
			throw new IsmaException(ex);
		}
		return session;
	}

	/**
	 * Register a Hibernate interceptor with the current thread.
	 * <p>
	 * Every Session opened is opened with this interceptor after
	 * registration. Has no effect if the current Session of the
	 * thread is already open, effective on next close()/getSession().
	 */
	public static void registerInterceptor(Interceptor interceptor) {
		threadInterceptor.set(interceptor);
	}

	private static Interceptor getInterceptor() {
		Interceptor interceptor =
			(Interceptor) threadInterceptor.get();
		return interceptor;
	}

}

