package net.tc.isma.persister;

import java.io.*;
import java.sql.*;
import java.util.*;

import net.tc.isma.*;
import net.tc.isma.actions.*;
import net.tc.isma.actions.generic.*;
import net.tc.isma.data.hibernate.*;
import net.tc.isma.lang.*;
import net.tc.isma.resources.*;
import net.tc.isma.utils.*;
import org.apache.log4j.*;
import com.mchange.v2.c3p0.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

/*
 This is the permanent Deposit for all the ISMA object

 */
public class IsmaPersister
{
    public static String SECURESERVER = "";
    private static Loader loader = null;
    private static java.util.Locale[] availableLocales = null;
    private static Map resourceBundleMap = null;
    private static Map resourceXmlBundleMap;
    private static String MAINROOT;
    private static String VERSION;
    private static String[] alowedLanguages = Language.alowedLanguages;
    private static Map configMap = new HashMap();
    private static Map configMapAll = new HashMap();
    private static PersistentObjectMap persistObjects = new PersistentObjectMap();
    private static PersistentObject[] persistentResourceBundleObjs = null;
    private static String[] LogsNames = new String[] {"SYSTEM", "CONTROLLER",
                                        "VISUALIZER", "DATAACCESS",
                                        "XMLXSLTRANSFORMATION", "PERFORMANCE",
                                        "REMOTEACCESS"};
    private static Map Logs = new HashMap();


    // Cache system
    private static CacheManager cacheManager = null;

    //internal objects
    private static ComboPooledDataSource connectionPoolIn;
    private static ComboPooledDataSource connectionPoolOut;
    private static String mainconfigfile;
    private static Vector users;
    private static int[] internalip = new int[] {0, 0, 0, 0};
    private static HSessionFactory sessionFactory;
    private static Map modulesMap;
    private static String APPLICATIONNAME = "ISMA";
    private static boolean useDatabase = false;
    private static boolean usehibernate = false;
    private static String mainIsmaConfigurationFile = null;
    private static Map applicationVariables = new Hashtable<String, Object>();

    protected IsmaPersister()
	{
	}
	protected IsmaPersister(Loader ld)
	{
		loader = ld;
	}

	static synchronized void setLoader(Loader ld)
	{
		if(ld != null)
			loader = ld;

	}
	static synchronized Loader getLoader()
	{
		if(loader != null)
			return loader;
		return null;
	}

	private static void setLogs()
	{
		Logger  l = null;
                String logFilePath = "/conf/ismalog.properties";
                if(IsmaPersister.getApplicationVariable("logConfigurationName") != null)
                    logFilePath =(String) IsmaPersister.getApplicationVariable("logConfigurationName");

                System.out.println("----===||| Using Log system from : " + IsmaPersister.MAINROOT+logFilePath +" |||===----");

//                org.apache.log4j.PropertyConfigurator.configure(IsmaPersister.MAINROOT+logFilePath);

                org.apache.log4j.PropertyConfigurator.configureAndWatch(IsmaPersister.MAINROOT+logFilePath, 20000);

		l = Logger.getRootLogger();



		Logs.put("ROOT", l);

		for(int i = 0 ; i < LogsNames.length ; i++)
		{
                        l = Logger.getLogger(LogsNames[i]);

			if(l != null)
				Logs.put(LogsNames[i], l);
			l = null;
		}
	}

	public static Logger getLogRoot()
	{
		if(Logs.size() < 1)
			setLogs();

		return (Logger)Logs.get("ROOT");
	}

	public static Logger getLogSystem()
	{
		if(Logs.size() < 1)
			setLogs();
		return ((Logger)Logs.get("SYSTEM"));
	}

	public static Logger getLogController()
	{
		if(Logs.size() < 1)
			setLogs();
		return ((Logger)Logs.get("CONTROLLER"));
	}
	public static Logger getLogPerformance()
	{
		if(Logs.size() < 1)
			setLogs();
		return ((Logger)Logs.get("PERFORMANCE"));
	}
	public static Logger getLogVisualizer()
	{
		if(Logs.size() < 1)
			setLogs();
		return ((Logger)Logs.get("VISUALIZER"));
	}
	public static Logger getLogDataAccess()
	{
		if(Logs.size() < 1)
			setLogs();
		return ((Logger)Logs.get("DATAACCESS"));
	}
	public static Logger getLogXmlXslTransformation()
	{
		if(Logs.size() < 1)
			setLogs();
		return ((Logger)Logs.get("XMLXSLTRANSFORMATION"));
	}

	public static Logger getLogRemoteAccess()
	{
		if(Logs.size() < 1)
			setLogs();
		return ((Logger)Logs.get("REMOTEACCESS"));
	}

        public static Map getLogsMap()
        {
            if(Logs != null && Logs.size() > 0)
                return Logs;

            return null;
        }

        public static void setLogsMap(Map LogsIn)
        {
            if(LogsIn != null && LogsIn.size() > 0 )
            {
                Logs = LogsIn;
            }

        }

        public static Logger getLogByName(String LogName)
        {
            if(Logs.size() < 1)
                    setLogs();
            if(Logs.get(LogName) == null)
            {
                Logger l = getLogRoot();
                l.error("Log " + LogName + " NOT FOUND in configuration file use ROOT log");
                return l;
            }

            return ((Logger)Logs.get(LogName));

        }

        public static void setLogByName(String logName, Logger logIn)
        {
            if(logIn != null)
            {
                Logs.put(logName, logIn);
            }

        }
	public static java.util.Locale[] getAvailableLocales()
	{
		if(availableLocales != null)
			return availableLocales;

		if(getAlowedLanguages() != null && getAlowedLanguages().length > 0)
		{
			setLocales();
		}

		return availableLocales;
	}


	private static void setLocales()
	{

		Locale[] als = new Locale[alowedLanguages.length];

		for( int i = 0; i < alowedLanguages.length; i++ )
		{
			als[i] = new Locale( alowedLanguages[i] );
		}

		availableLocales = als;
	}

	public void setResourceBundles( Set resourceBundles )
	{
		resourceBundleMap = new HashMap();
		Iterator iter = resourceBundles.iterator();
		while( iter.hasNext() )
		{
			ResourceBundle rsc = ( ResourceBundle ) iter.next();
			resourceBundleMap.put( rsc.getLocale(), rsc );
		}
	}
        public static boolean useResourceBundle( ) throws IsmaException
        {
            if(((ConfigParams)configMap).getResorceBundleResources() != null
               && ((ConfigParams)configMap).getResorceBundleResources().length > 0
               && ((ConfigParams)configMap).getResorceBundleResources()[0] != null
               )
                return true;

            return false;


	}
	public static ResourceBundle getResourceBundle( Locale locale ) throws IsmaException
	{
		if(locale == null)
			return null;

		if(resourceBundleMap == null || resourceBundleMap.size() < 0)
		{
			LoadResourceBundle();
			initResourceBundle();
		}

		if(checkLastModify())
        {
            resourceBundleMap = null;
            initResourceBundle( );
        }
		IniStyleResourceBundle iniRes = ( IniStyleResourceBundle ) resourceBundleMap.get( locale );

		return( ResourceBundle ) iniRes;
	}

	static synchronized void LoadResourceBundle( ) throws IsmaException
	{
            Map dummyMap = loader.loadResourceBundles(((ConfigParams)configMap).getResorceBundleResources());
            if(dummyMap != null)
                persistObjects.putAll(dummyMap);
	}
	static synchronized void initResourceBundle( ) throws IsmaException
	{
		getLogSystem().info("**** Ini File Inizialization ***************" );

                if(persistObjects.getResourceBoundlePo() == null)
                {
                    getLogSystem().info("**** NO RESOURCE Bundle Loaded *****" );
                    System.out.println("**** NO RESOURCE Bundle Loaded *****");
                    return;
                }
		persistentResourceBundleObjs =  persistObjects.getResourceBoundlePo();
		if(checkLastModify( ) || resourceBundleMap == null)
		{
			resourceBundleMap = new HashMap();

			IniFile ini = new IniFile();

			for(int i = 0 ; i < persistentResourceBundleObjs.length ; i++)
			{
				if(persistentResourceBundleObjs[i] instanceof Resource)
				{
					Resource res = (Resource)persistentResourceBundleObjs[i];


					if(res.getResource() instanceof File)
					{
						try
						{
							if(res.getResourceType() != Resource.INI)
							{
								if(res.getResourceType() != Resource.RESOURCEBUNDLE)
								{
									ini.loadXml( ( File ) res.getResource(), true );
								}
								else
								{
									String mainKey = (String)((PersistentObject)res).getKey();
									mainKey = mainKey.toLowerCase().replaceAll("isma_configuration.resourcebundle.","") + ".";
									ini.loadXmlRb( ( File ) res.getResource() , true, mainKey );
								}
							}
							else
							{
								String mainKey = (String)((PersistentObject)res).getKey();
								mainKey = mainKey.toLowerCase().replaceAll("isma_configuration.resourcebundle.","") + ".";

								ini.load( ( ( File ) res.getResource() ).getAbsolutePath(), true, mainKey );
							}
						}
						catch( Exception ex )
						{
							ex.printStackTrace();
						}
					}
				}
			}


			Locale[] availableLocales = IsmaPersister.getAvailableLocales();
			for( int il = 0; il < availableLocales.length; il++ )
			{
				Locale l = availableLocales[il];
				getLogSystem().info("Creating ResourceBundle for Locale: " + l.toString() );
				IniStyleResourceBundle rsc = new IniStyleResourceBundle( ini, l );

				rsc.setIsInizialized( true );
				resourceBundleMap.put( l, rsc );
			}

		}
	}

	private static boolean checkLastModify( ) throws IsmaException
	{
            if(persistentResourceBundleObjs != null
               && persistObjects != null
               )
            {
                for (int i = 0; i < persistentResourceBundleObjs.length; i++) {
                    if (((PersistentObject) persistentResourceBundleObjs[i]).
                        checkLastModify()) {
                        PersistentObject po = persistentResourceBundleObjs[i];
                        po.reFresh();
                        persistentResourceBundleObjs[i] = po;
                        persistObjects.put(po.getKey(), po);
                        return true;
                    }

                }
                return false;
            }
            return false;
	}

	private static synchronized void loadResourceBundles() throws IsmaException
	{
		try
		{

			resourceBundleMap = loader.loadResourceBundles();
		}
		catch( IsmaException ex )
		{
			getLogSystem().error("Error loading resource bundles", ex );

		}
	}

	public static synchronized String getMAINROOT()
	{
//		if(MAINROOT == null)
//			FaostatController.
		return MAINROOT;
	}

	static synchronized void setMAINROOT( String mainroot )
	{
		MAINROOT = mainroot;
	}

	public static String getVERSION()
	{
		return VERSION;
	}

	static void setVERSION( String version )
	{
		VERSION = version;
	}

        public static String getConfigParameterValueString( String key )
        {
            try{
                ConfigResource cr = getConfigParameterValue(key);
                if (cr != null && cr.getStringValue() != null &&
                    !cr.getStringValue().equals("")) {
                    return cr.getStringValue();
                }
            }
            catch(IsmaException fex)
            {
                getLogSystem().error(fex);
            }
            return null;

        }

	public static ConfigResource getConfigParameterValue( String key ) throws IsmaException
	{

		if(configMap != null && configMap.size() > 0 && configMap.containsKey(key))
		{
			try{
				Object obj = configMap.get( key );
				return( ConfigResource ) obj;
			}catch(Exception ex){getLogSystem().error(configMap.get( key ).getClass().getName(),ex);}
	    }
		return null;
	}
	public static Object getConfigParameterMap( String mainIsmaConfigurationFileIn ) throws IsmaException
	{
		String param  = mainIsmaConfigurationFileIn;
		if( param == null || param.equals( "" ) )
			return null;
		if(MAINROOT == null || MAINROOT.equals(""))
			throw new IsmaException("IsmaPersister Main Application Path not inizialized!");


		if( cacheManager.getEternalManager() != null && configMap != null && configMap.size() > 0 )
		{
			return configMap;
		}
		else
		{
			LoadConfig(param);
                       mainIsmaConfigurationFile = mainIsmaConfigurationFileIn;
			return getConfigParameterMap(param);
		}
	}

        public static void ReloadConfiguration()
        {
            LoadConfig(mainIsmaConfigurationFile);
        }
        private static void LoadConfig(String param) {
            Map m = loader.loadMainConfig(param);

                                 /* Set Main parameter for application */
                                 if(((ConfigParams)m).isInizialized())
                                 {
                                         if(m.containsKey("isma_configuration.versions.default")
                                         && m.get("isma_configuration.versions.default") != null
                                         && ((ConfigResource)m.get("isma_configuration.versions.default")).getStringValue() != null)
                                                 setVERSION(((ConfigResource)m.get("isma_configuration.versions.default")).getStringValue());

                                         if(m.containsKey("isma_configuration.languages")
                                         && m.get("isma_configuration.languages") != null
                                         &&((ConfigResource)m.get("isma_configuration.languages")).getStringValue() != null)
                                         {
                                                 Map langMap = (Map)((ConfigResource)m.get("isma_configuration.languages")).getValue();
                                                 String[] languages = null;
                                                 languages =(String[]) langMap.values().toArray(new String[0]);
                                                 setAlowedLanguages(languages);
                                         }
                                         configMap = m;
                                         configMapAll.putAll(m);
                                 }
        }
	public static Set getConfigParameterNames( ) throws IsmaException
	{
		if( configMap != null &&  configMap.size() < 0)
		{
			return configMap.keySet();
		}
		return null;
	}

	 static void setConfigParameter(Object key, ConfigParams cp) throws IsmaException
	{
		if(cp.objectLifeCycle() == Resource.ETERNAL)
		{
			configMap.put(key, cp);
			configMapAll.putAll(configMap);
		}
		else
		{
			throw new IsmaException("Configuration parameters needs to be define as ETERNAL");
		}
	}

	public static Object get(Class cls, Serializable id)
	{
		Object obj = cacheManager.get(cls , id);
	    return obj;
	}
	public static void remove(Serializable id, PersistentObject obj)
	{
		cacheManager.remove(id, obj);
	}

	public static void set(Serializable id, PersistentObject obj)
	{
		cacheManager.set(id, obj);
	}

	public static void set(Class cls, Serializable id, Object obj, int LifeCycle)
	{
		cacheManager.set(cls, id, obj, LifeCycle);
	}

	static public String[] getAlowedLanguages()
	{
		return alowedLanguages;
	}

	static void setAlowedLanguages( String[] alowedLanguagesIn )
	{
		alowedLanguages = alowedLanguagesIn;
	}
	static void InizializeCacheLayer()
	{
		if(cacheManager == null)
			try{
				cacheManager = new CacheManager();
			}catch(Exception ex)
			{
				getLogSystem().error(ex.getMessage(),ex);
			}
	}
	public static ActionFactory getActionFactory(Class cls)
	{
		if(cls == null)
			cls = actionFactoryImpl.class;

		ActionFactory af = (ActionFactory)cacheManager.get(cls, cls);
		if(af != null)
			return af;

		return null;
	}

	public static Connection getConnectionlIn()
	{
		if(connectionPoolIn == null)
			return null;
		try{
			return connectionPoolIn.getConnection();
		}catch(Exception ex){IsmaPersister.getLogSystem().error("", ex ); return null;}
	}

	static void  setConnectionPoolIn( com.mchange.v2.c3p0.ComboPooledDataSource connectionPoolInIn )
	{
		connectionPoolIn = connectionPoolInIn;
	}

	public static Connection getConnectionOut()
	{
		if(connectionPoolOut == null)
			return null;
		try{
			return connectionPoolOut.getConnection();
		}catch(Exception ex){IsmaPersister.getLogSystem().error("", ex ); return null;}
	}

	static void setConnectionPoolOut( com.mchange.v2.c3p0.ComboPooledDataSource connectionPoolOutIn )
	{
		connectionPoolOut = connectionPoolOutIn;
	}

	public static Map getConfigMapAll()
	{
		return configMapAll;
	}
	public static ConfigResource getConfigResource(Object key)
	{
		Object obj = configMapAll.get(key);
		return (ConfigResource)obj;
	}
    private static String getMainconfigfile()
    {
		return mainconfigfile;
    }

    public static Vector getUsers()
    {
        return users ;
    }

    public static int[] getInternalip()
    {
        return internalip ;
    }


    public static Object getDefaultConfigParameterMap()
	{
		try
		{
			return getConfigParameterMap( getMainconfigfile() );
		}
		catch( IsmaException ex )
		{
			return null;
		}
	}

	static void setMainconfigfile(String mainconfigfileIn)
    {
	 mainconfigfile = mainconfigfileIn;
    }

    public static void setUsers( Vector usersIn )
    {
        users = usersIn ;
    }

    private static void setSessionFactory( HSessionFactory sessionFactoryIn )
    {
        sessionFactory = sessionFactoryIn ;
    }

    public static void setModulesMap(Map modulesMapIn) {
        modulesMap = modulesMapIn;
    }

    public static void  setUseDatabase(boolean useDatabaseIn) {
        useDatabase = useDatabaseIn;
    }

    public static HSessionFactory getSessionFactory()
    {
        try{
            if (sessionFactory == null)
            {
                setSessionFactory(new HSessionFactory());
                return sessionFactory;
            }
        }
        catch(Exception ex)
        {
            getLogDataAccess().error(ex);
            return null;
        }
        return sessionFactory ;


    }

    public static String getAPPLICATIONNAME() {
        return APPLICATIONNAME;
    }

    public static Map getModulesMap() {
        return modulesMap;
    }

    public static boolean isUseDatabase() {
        return useDatabase;
    }

    public static void setInternalip( Map configMap )
    {
        if(internalip[0] != 0)
            return;

        ConfigResource param1 = null;
        param1 = ( ConfigResource ) configMap.get("isma_configuration.internaliprange" ) ;
        String[] ips = param1.getStringValue().split("\\.");
        for(int i = 0 ; i < ips.length ; i++)
        {
            internalip[i] =Integer.parseInt(ips[i]);
        }
    }

    public static void setApplicationVariable(String key , Object value)
    {
        applicationVariables.put(key,value);

    }

    public static Object getApplicationVariable(String key)
    {
        if(key == null)
            return null;

        return applicationVariables.get(key);

    }
	public static boolean isUsehibernate() {
		return usehibernate;
	}
	public static void setUsehibernate(boolean usehibernate) {
		IsmaPersister.usehibernate = usehibernate;
	}

}
