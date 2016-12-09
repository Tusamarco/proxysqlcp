
package net.tc.isma.persister;

import java.io.*;
import java.util.*;

import javax.servlet.*;

import com.mchange.v2.c3p0.*;
import net.tc.isma.*;
import net.tc.isma.actions.*;
import net.tc.isma.actions.generic.*;
import net.tc.isma.auth.security.*;
import net.tc.isma.data.hibernate.*;
import net.tc.isma.data.objects.*;
import net.tc.isma.resources.*;
import net.tc.isma.utils.*;
import net.tc.isma.utils.SynchronizedMap;
import org.hibernate.*;

public class Loader
{
	private static ServletContext context = null;
	protected Loader(ServletContext ctx) throws IsmaException
	{
		if(ctx == null)
			throw new IsmaException("Context could not be null");
		context = ctx;
		init();
	}
        protected Loader(String path) throws IsmaException
        {
                if(path == null)
                        throw new IsmaException("Main application Path could not be null");

                initApplication(path);
	}
	private synchronized void init()
	{
		String mainRoot = null;
		if(context.getRealPath( "WEB-INF" ).indexOf("\\") > 0)
		{
			mainRoot = context.getRealPath( "WEB-INF" ).replace( '\\', '/' );
		}
		else
		{
			mainRoot = context.getRealPath( "WEB-INF" ).replace( '\\', '/' );
		}
                if( mainRoot != null && !mainRoot.equals( "" ) )
                {
                        mainRoot = mainRoot.substring( 0, mainRoot.indexOf( "/WEB-INF" ) );

                        IsmaPersister.setMAINROOT( mainRoot );
		}



	}

        private synchronized void initApplication(String path) throws IsmaException
        {
            File f = new File( path);
            String localclassPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(5);
            IsmaPersister.setApplicationVariable("mainclassPath",localclassPath);
            if (!f.exists()) {
                throw new IsmaException("Main application Path could not be Invalid");
            }

                String mainRoot = path;
                mainRoot = mainRoot.replace('\\', '/');
                IsmaPersister.setMAINROOT(mainRoot);
        }

	public synchronized Object loadObject(String param)
	{

		return null;
	}

	synchronized ConfigParams loadMainConfig(String param)
	{
		PersistentObject resource = null;

		try
		{
			File f = new File( IsmaPersister.getMAINROOT() + "/WEB-INF/" + param );
			if( !f.exists() )
                        {
                            f = new File( IsmaPersister.getMAINROOT() + "/" + param );
                            if( !f.exists() )
                                return null;
                        }


			Map confParameter = ResourceBaseImp.getInizializationParameter( f );
			int resourcetype = 0;
			int lifecycle = 0;
			if( confParameter != null
				&& confParameter.get( "resourcetype" ) != null
				&& Integer.parseInt( ( String ) confParameter.get( "resourcetype" ) ) > 0 )
				resourcetype = Integer.parseInt( ( String ) confParameter.get( "resourcetype" ) );

			if( confParameter != null
				&& confParameter.get( "lifecycle" ) != null
				&& Integer.parseInt( ( String ) confParameter.get( "resourcetype" ) ) > 0 )
				lifecycle = Integer.parseInt( ( String ) confParameter.get( "lifecycle" ) );

			int handleAs = ResourceBaseImp.getResourceType( f );
			if( handleAs == Resource.INI )
			{
				resource = ( PersistentObject )new ConfigParams( f, resourcetype, lifecycle, handleAs );
			}
			else
			{
//					resource = new ConfigParams(f,resourcetype,lifecycle);
			}

			IsmaPersister.getLogSystem().info(confParameter.toString());
			return (ConfigParams)resource;

		}
		catch( Exception eex )
		{
			eex.printStackTrace();
			return null;
		}

	}
	synchronized Map loadResourceBundles() throws IsmaException
	{return null;}
	synchronized Map loadResourceBundles(Object[] crfs) throws IsmaException
	{
		Map newResBundle = null;
		IniFile ini = new IniFile();
		if(crfs != null && crfs.length > 0)
		{
			newResBundle = new HashMap();

			for(int i = 0 ; i < crfs.length ; i++)
			{
				ConfigResource crf = ( ConfigResource ) crfs[i];
				if( crf.getStringValue() != null && !crf.getStringValue().equals( "" ) )
				{
					newResBundle.put( crf.getKey(), getPersistentObject(crf) );
				}
			}
		}

		return newResBundle;
	}

	private PersistentObject getPersistentObject(Object crfin) throws IsmaException
	{
		if(crfin instanceof ConfigResource
		    && crfin != null
		    && ((ConfigResource)crfin).getStringValue() != null
			&& !((ConfigResource)crfin).getStringValue().equals("")
		)
		{
			PersistentObject po = new persistentObjectImpl((ConfigResource)crfin);
			return po;
		}
	    return null;
	}
	public PersistentObject getActionFactory(Map configMap)
	{
		ActionFactory af = null;
		PersistentObject po = null;
		PersistentObject poDest = null;

		ConfigResource param =  (ConfigResource)configMap.get("isma_configuration.actionFactory");

                if(param == null)
                {
                    String actionsPath = IsmaPersister.getMAINROOT() + "/actions/" ;

                    File f = new File(actionsPath);

                    if(!f.exists())
                    {
                        actionsPath = (String)IsmaPersister.getApplicationVariable("mainclassPath") + "/actions/";
                        f = new File(actionsPath);

                    }
                    if(f.exists())
                    {
                        IsmaPersister.getLogSystem().info(" Using Action path = " + f.getAbsoluteFile());
                        Map attribs = new HashMap(2);
                        attribs.put("resourcetype", "1");
                        attribs.put("lifecycle", "70");

                        param = new ConfigResourceImpl(attribs,actionFactoryImpl.class, actionsPath);
                    }
                    else
                    {
                        IsmaPersister.getLogSystem().error(" Unable to find Action path = " + f.getAbsoluteFile());
                    }
                }


		if(param != null)
		{
			try
			{
				po = new persistentObjectImpl( ( ConfigResource ) param );
			}
			catch( IsmaException ex )
			{
				IsmaPersister.getLogSystem().error("",ex);
			}
			af = actionFactoryImpl.getInstance( po );
		}

		if(af != null)
		{
			poDest = new persistentObjectImpl(po.objectLifeCycle(),af);
			poDest.setKey(actionFactoryImpl.class);
			return poDest;
		}
//		if(poDest != null)
//			FaostatPersister.set(ActionFactory.class,po);

		return null;
	}


	public ComboPooledDataSource getConnectionPoolIn(Map configMap)
	{
		ComboPooledDataSource cpds = null;
		PersistentObject poDest = null;
		PersistentObject poGosth = null;
		ConfigResource param1 =  (ConfigResource)configMap.get("isma_configuration.connectionin_driver_class");
		ConfigResource param2 =  (ConfigResource)configMap.get("isma_configuration.connectionin_url");
		ConfigResource user =  (ConfigResource)configMap.get("isma_configuration.connectionin_user");
		ConfigResource password =  (ConfigResource)configMap.get("isma_configuration.connectionin_password");


		if(param1 != null && param2 != null)
		{

			try
			{
				
				cpds = new ComboPooledDataSource();
				cpds.setDriverClass(param1.getStringValue()); //loads the jdbc driver
				cpds.setJdbcUrl( param2.getStringValue());
				cpds.setUser((String)user.getValue() );
				cpds.setPassword( (String)password.getValue() );

			}
			catch( Exception ex )
			{
				IsmaPersister.getLogSystem().error("",ex);
			}
		}

		return cpds;
	}

	public ComboPooledDataSource getConnectionPoolOut(Map configMap)
	{
		ComboPooledDataSource cpds = null;
		PersistentObject poDest = null;
		PersistentObject poGosth = null;
		ConfigResource param1 =  (ConfigResource)configMap.get("isma_configuration.connectionout_driver_class");
		ConfigResource param2 =  (ConfigResource)configMap.get("isma_configuration.connectionout_url");

		if(param1 != null && param2 != null)
		{

			try
			{
		
				cpds = new ComboPooledDataSource();
				cpds.setDriverClass(param1.getStringValue()); //loads the jdbc driver
				cpds.setJdbcUrl( param2.getStringValue());
//				cpds.setUser( "dbuser" );
//				cpds.setPassword( "dbpassword" );

			}
			catch( Exception ex )
			{
				IsmaPersister.getLogSystem().error("",ex);
			}
		}
		return cpds;
	}

//        public Map loadModules(boolean reload)
//        {
//            try
//            {
//                Map moduleMap = null;
//                moduleMap = (SynchronizedMap)IsmaPersister.getModulesMap();
//
//                IsmaPersister.getLogSystem().info("**** Initializing Modules [Start]");
//                if (moduleMap != null && !reload)
//                {
//                    return moduleMap;
//                }
//                moduleMap = new SynchronizedMap();
//                try
//                {
//                    HSession ds = IsmaPersister.getSessionFactory().openSession();
//                    Transaction tr = ds.beginTransaction();
//                    String hSql = "select mod from net.tc.isma.data.objects.Module as mod where mod.active='Y' order by mod.moduleNamep";
//                    List l = ds.findDirect(hSql);
//                    ListIterator it = l.listIterator();
//                    while (it.hasNext())
//                    {
//                        Module cMod = (Module) it.next();
//                        cMod.setGroup(getGroups(cMod, ds));
//
//                        moduleMap.put(cMod.getModuleName() + "_" + cMod.getApplication(), cMod);
//
//                        IsmaPersister.getLogSystem().info("**** Initializing Module [" + cMod.getModuleName() + "] " + cMod.getTgroup());
//                    }
//                    IsmaPersister.getLogSystem().info("**** Initializing Modules [End]");
//                    tr.commit();
//                    IsmaPersister.getSessionFactory().closeSession(ds);
//
//
//                    return moduleMap;
//
//                }
//                catch (Throwable ex)
//                {
//                    IsmaPersister.getLogDataAccess().error(ex);
//                    ex.printStackTrace();
//                }
//                finally
//                {
//                    return moduleMap;
//                }
//            }
//            catch (Throwable ex)
//            {
//                IsmaPersister.getLogDataAccess().error(ex);
//                return null;
//            }
//
//        }

//        private Set getGroups(Module mod,HSession ds)throws IsmaException
//        {
//            try
//            {
//                IsmaPersister.getLogSystem().info("**** Initializing Groups [Start]");
//                Set groups = new HashSet();
////                Session ds = null;
//                try
//                {
////                  ds = FaostatPersister.getSessionFactory().openSession();
//                  Group gp = null;
//                  gp = (Group) ds.load(Group.class, "inputer");
//                  groups.add(gp);
//                  gp = (Group) ds.load(Group.class, "editor");
//                  groups.add(gp);
//                  gp = (Group) ds.load(Group.class, "approver");
//                  groups.add(gp);
//
//                  List l = ds.findDirect("select GXM from net.tc.isma.auth.security.GrpXMod as GXM where GXM.applicationp ='"+ mod.getApplication() +
//                                "' and GXM.modulep = '" + mod.getModuleName() + "' order by GXM.groupnp");
//                   Iterator it = l.listIterator();
//                    while (it.hasNext())
//                    {
//                        GrpXMod groupX2 = (GrpXMod) it.next();
//                        Group grp = (Group)ds.load(Group.class,groupX2.getGroupnp());
//                        groups.add(grp);
//                        IsmaPersister.getLogSystem().info("**** Initializing Modules " + mod.getModuleName() + " adding group: " + grp.getId());
//                    }
////                    FaostatPersister.getSessionFactory().closeSession();
//                    IsmaPersister.getLogSystem().info("**** Initializing Modules [End]");
//
//                    return groups;
//
//                }
//                catch (Exception ex)
//                {
//                    IsmaPersister.getLogDataAccess().error(ex);
//                }
//            }
//            catch (Exception ex)
//            {
//                IsmaPersister.getLogDataAccess().error(ex);
//                return null;
//            }
//            return null;
//        }

}
