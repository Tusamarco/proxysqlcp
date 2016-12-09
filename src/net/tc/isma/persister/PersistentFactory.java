package net.tc.isma.persister;

import javax.servlet.ServletContext;
import net.tc.isma.IsmaException;
import java.util.Map;
import java.util.Iterator;
import java.io.PrintStream;
import net.tc.isma.IsmaController;
import java.util.ResourceBundle;
import java.util.Locale;
import net.tc.isma.actions.generic.actionFactoryImpl;
import net.tc.isma.actions.ActionFactory;
import net.tc.isma.resources.ConfigParams;
import net.tc.isma.resources.Resource;
import java.io.Serializable;
import net.tc.isma.resources.ConfigResource;
import net.tc.isma.resources.ConfigResourceImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.tc.isma.actions.Executioner;
import net.tc.isma.actions.generic.executionerImpl;
import net.tc.isma.actions.generic.results;
import net.tc.isma.data.db.DynamicSqlXmlStatments;


public class PersistentFactory
{
	public PersistentFactory()
	{

	}

	private static synchronized Loader getLoader(ServletContext ctx)
	{
		if(ctx != null)
		try
		{
			return new Loader( ctx );
		}
		catch( IsmaException ex )
		{
			System.out.print(ex);
			ex.printStackTrace();
			return null;
		}

		return null;
	}
        private static synchronized Loader getLoader(String path)
        {
                if(path != null)
                try
                {
                        return new Loader( path );
                }
                catch( IsmaException ex )
                {
                        System.out.print(ex);
                        ex.printStackTrace();
                        return null;
                }

                return null;
        }

	static synchronized IsmaPersister getFaostatPersister(Loader loader)
	{
		if(loader != null)
		 return new IsmaPersister(loader);

		return null;
	}
	public static synchronized void PersisterInizialize(ServletContext ctx) throws IsmaException
	{
		if(ctx == null)
			throw new IsmaException( "Invalid context while inizializing " );
		try
		{
			Loader l = getLoader( ctx );
			synchronized( l )
			{
				synchronized( l )
				{
					IsmaPersister.setLoader( l);
				}
			}
			IsmaPersister.getLogSystem();
			IsmaPersister.InizializeCacheLayer();

			String param = ctx.getInitParameter("mainconfigfile");
			IsmaPersister.setMainconfigfile(param);
			if(param == null)
				throw new IsmaException("Invalid configuration file or mainconfigfile parameter missed in the web.xml");

			Map configMap = (Map)IsmaPersister.getConfigParameterMap(param);
			IsmaPersister.LoadResourceBundle();
			IsmaPersister.initResourceBundle();
			ResourceBundle rsc = IsmaPersister.getResourceBundle(new Locale("en"));


			if(configMap !=null && configMap.size() > 0)
                        {
                            //load actionFactory
                            //inizialiazeActionFactory(configMap);
                            PersistentObject af = l.getActionFactory(configMap);
                            IsmaPersister.set((Serializable) af.getKey(), af);

//                            LoadUsers.loadFile(configMap);
//                            FaostatPersister.setInternalip(configMap);
                            //load connectionPools
                            if(IsmaPersister.getConfigParameterValueString("isma_configuration.usedatabase") != null
                              && Boolean.parseBoolean(IsmaPersister.getConfigParameterValueString("isma_configuration.usedatabase"))
                            )
                            {
                                IsmaPersister.setUseDatabase(true);
                            }
                            else
                                IsmaPersister.setUseDatabase(false);
                            
                            if(IsmaPersister.getConfigParameterValueString("isma_configuration.usehibernate") != null
                                    && Boolean.parseBoolean(IsmaPersister.getConfigParameterValueString("isma_configuration.usehibernate"))
                                  )
                                  {
                                      IsmaPersister.setUsehibernate(true);
                                  }
                                  else
                                      IsmaPersister.setUsehibernate(false);
                            

                            if(IsmaPersister.getConfigParameterValueString("isma_configuration.hibernate_conf") != null
                                    && !IsmaPersister.getConfigParameterValueString("isma_configuration.hibernate_conf").equals("")
                                  )
                                  {
                                      IsmaPersister.setHibernatConfigFile(IsmaPersister.getConfigParameterValueString("isma_configuration.hibernate_conf"));
                                  }
                                  else
                                      IsmaPersister.setHibernatConfigFile(null);
                            
                            
                            

                            if(IsmaPersister.isUseDatabase())
                            {
                                ComboPooledDataSource connectionIn = l.getConnectionPoolIn(configMap);
                                ComboPooledDataSource connectionOut = l.getConnectionPoolIn(configMap);
                                
                                if (connectionIn != null)
                                    IsmaPersister.setConnectionPoolIn(connectionIn);
                                if (connectionIn != null)
                                    IsmaPersister.setConnectionPoolOut(connectionOut);
                                /**
                                 * This section will load SQL Strings
                                 */
                                DynamicSqlXmlStatments.getSQLCommand("init",true);

                            }
                            IsmaController.logEntry(System.out, configMap);
                            
                            if(IsmaPersister.isUsehibernate() 
                            	&& IsmaPersister.getConfigParameterValueString("isma_configuration.hibernate_conf") != null
                            	&& !IsmaPersister.getConfigParameterValueString("isma_configuration.hibernate_conf").equals("")){
                            	
                            	
                            	
                            }
                            
                            
                        }
                        /**
                         * This section will load Hibernate and all related maps
                         */
//                       IsmaPersister.getSessionFactory();
                        /** @todo WARNING IS DISABLED */
//DISABLED FOR NOW                       IsmaPersister.setModulesMap(l.loadModules(true));

		}
		catch( Exception ex )
		{
			throw new IsmaException( ex );
		}


	}


        public static synchronized void PersisterInizialize(String path) throws IsmaException
        {
                if(path == null)
                        throw new IsmaException( "Invalid application path while inizializing " );
                try
                {
                        Loader l = getLoader( path );
                        synchronized( l )
                        {
                                synchronized( l )
                                {
                                        IsmaPersister.setLoader( l);
                                }
                        }
                        IsmaPersister.getLogSystem();
                        IsmaPersister.InizializeCacheLayer();



                        String param = (String)IsmaPersister.getApplicationVariable("mainconfigfile");
                        IsmaPersister.setMainconfigfile(param);
                        if(param == null)
                                throw new IsmaException("Invalid configuration file or mainconfigfile parameter missed in the web.xml");

                        Map configMap = (Map)IsmaPersister.getConfigParameterMap(param);
                        IsmaPersister.LoadResourceBundle();
                        IsmaPersister.initResourceBundle();
                        ResourceBundle rsc = null;
                        if(IsmaPersister.useResourceBundle())
                            rsc = IsmaPersister.getResourceBundle(new Locale("en"));


                        if(configMap !=null && configMap.size() > 0)
                        {
                            //load actionFactory
                            //inizialiazeActionFactory(configMap);
                            PersistentObject af = l.getActionFactory(configMap);
                            IsmaPersister.set((Serializable) af.getKey(), af);

//                            LoadUsers.loadFile(configMap);
//                            FaostatPersister.setInternalip(configMap);
                            //load connectionPools
                            if(IsmaPersister.getConfigParameterValueString("isma_configuration.usedatabase") != null
                              && Boolean.parseBoolean(IsmaPersister.getConfigParameterValueString("isma_configuration.usedatabase"))
                            )
                            {
                                IsmaPersister.setUseDatabase(true);
                            }
                            else
                                IsmaPersister.setUseDatabase(false);

                            if(IsmaPersister.isUseDatabase())
                            {
                                ComboPooledDataSource connectionIn = l.getConnectionPoolIn(configMap);
                                ComboPooledDataSource connectionOut = l.getConnectionPoolIn(configMap);
                                
                                if (connectionIn != null)
                                    IsmaPersister.setConnectionPoolIn(connectionIn);
                                if (connectionIn != null)
                                    IsmaPersister.setConnectionPoolOut(connectionOut);
                                /**
                                 * This section will load SQL Strings
                                 */
                                DynamicSqlXmlStatments.getSQLCommand("init",true);

                            }
                            IsmaController.logEntry(System.out, configMap);
                        }
                        /**
                         * This section will load Hibernate and all related maps
                         */
//                       IsmaPersister.getSessionFactory();
                        /** @todo WARNING IS DISABLED */
//DISABLED FOR NOW                       IsmaPersister.setModulesMap(l.loadModules(true));

                }
                catch( Exception ex )
                {
                        throw new IsmaException( ex );
                }


        }



}
