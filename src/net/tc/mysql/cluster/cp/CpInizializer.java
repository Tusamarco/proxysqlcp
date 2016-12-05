package net.tc.mysql.cluster.cp;

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

import java.io.*;
import java.util.*;

import com.mysql.cluster.mgmj.*;
import net.tc.isma.persister.*;
import net.tc.isma.resources.*;
import net.tc.isma.utils.SynchronizedMap;
import net.tc.mysql.cluster.cp.objects.CategoryListner;

import org.apache.log4j.*;
import net.tc.isma.utils.IniFile;
import net.tc.isma.IsmaException;

/**
 * The CpInizializer inizialize all the NDB MG main activities
 * open the connection to the Cluster MGM
 *
 * require the following parameters to exist in the con file
 * From web.xml section:
 *  <context-param>    <param-name>mainconfigfile</param-name>    <param-value>ndbcpconf.xml</param-value>  </context-param>
 *
 * in the conf file
 *    <ndbmgmhost>192.168.0.7</ndbmgmhost>
 *    <ndbmgmport>1187</ndbmgmport>
 *    <ndbconnectionretry>6</ndbconnectionretry>
 *    <ndbconnectionretrydelay>4</ndbconnectionretrydelay>
 *    <enableNdbMgmLogsOnDisk>true</enableNdbMgmLogsOnDisk>
 *    <ndbconnectiontimeout>4000</ndbconnectiontimeout>
 *
 * in the <app_path>/conf/reference/listners_handlers.ini
 * the listner declaration set all active by default
 * you can edit directly the file if you want to add or exclude something
 *
 *
 * Main method init() no argument required STATIC
 * you don't need to instanziate it
 *
 * It loads NDB MGM interface and all listner defined in the config file
 *
 */


public class CpInizializer {
    public CpInizializer() {
        super();
    }

    public static synchronized void init()
    {
        try{
            NdbMgm mgm = null;
            int ndbconnectionretry = 5;
            int ndbconnectionretrydelay = 3;
            int ndbconnectiontimeout = 4000;

            String ndbMgmUri = null;
            String ndbMgmHostName = IsmaPersister.getConfigParameterValueString("isma_configuration.ndbmgmhost");
            String mgmHostPort = IsmaPersister.getConfigParameterValueString("isma_configuration.ndbmgmport");
            Boolean enableNdbMgmLogsOnDisk = new Boolean(false);

            if (
                    IsmaPersister.getConfigParameterValueString("isma_configuration.ndbconnectiontimeout") != null
                    &&
                    !IsmaPersister.getConfigParameterValueString("isma_configuration.ndbconnectiontimeout").equals("")
                    &&
                    Integer.parseInt(IsmaPersister.getConfigParameterValueString("isma_configuration.ndbconnectiontimeout")) > 0
                )
                ndbconnectiontimeout = Integer.parseInt(IsmaPersister.getConfigParameterValueString("isma_configuration.ndbconnectiontimeout"));

            if (
                    IsmaPersister.getConfigParameterValueString("isma_configuration.ndbconnectionretry") != null
                    &&
                    !IsmaPersister.getConfigParameterValueString("isma_configuration.ndbconnectionretry").equals("")
                    &&
                    Integer.parseInt(IsmaPersister.getConfigParameterValueString("isma_configuration.ndbconnectionretry")) > 0
                )
                ndbconnectionretry = Integer.parseInt(IsmaPersister.getConfigParameterValueString("isma_configuration.ndbconnectionretry"));


            if (
                    IsmaPersister.getConfigParameterValueString("isma_configuration.ndbconnectionretrydelay") != null
                    &&
                    !IsmaPersister.getConfigParameterValueString("isma_configuration.ndbconnectionretrydelay").equals("")
                    &&
                    Integer.parseInt(IsmaPersister.getConfigParameterValueString("isma_configuration.ndbconnectionretrydelay")) > 0
                )
                ndbconnectionretrydelay = Integer.parseInt(IsmaPersister.getConfigParameterValueString("isma_configuration.ndbconnectionretrydelay"));

            if (
                    IsmaPersister.getConfigParameterValueString("isma_configuration.enableNdbMgmLogsOnDisk") != null
                    &&
                    !IsmaPersister.getConfigParameterValueString("isma_configuration.enableNdbMgmLogsOnDisk").equals("")
                    &&
                    Boolean.parseBoolean(IsmaPersister.getConfigParameterValueString("isma_configuration.enableNdbMgmLogsOnDisk"))
                )
            {
                enableNdbMgmLogsOnDisk = true;
                enableLogs();
            }


            /**
             * If I get the mgmhost/port from line I will use them
             *
             */

            if(IsmaPersister.getApplicationVariable("mgmhosts") != null)
            {
                ndbMgmHostName = (String)IsmaPersister.getApplicationVariable("mgmhosts");
            }
            else if(IsmaPersister.getApplicationVariable("mgmhosts") == null && ndbMgmHostName != null)
            {
                IsmaPersister.setApplicationVariable("mgmhosts",ndbMgmHostName);
            }

            if(IsmaPersister.getApplicationVariable("mgmports") != null )
            {
                mgmHostPort = (String)IsmaPersister.getApplicationVariable("mgmports");
            }
            else if(IsmaPersister.getApplicationVariable("mgmports") == null && mgmHostPort != null)
            {
                IsmaPersister.setApplicationVariable("mgmports",mgmHostPort);
            }


            if (ndbMgmHostName != null
                && !ndbMgmHostName.equals("")
                && mgmHostPort != null
                && !mgmHostPort.equals("")
                    )
            {
                String[] ndbMgmHostNames = ndbMgmHostName.split(",");
                String[] mgmHostPorts = mgmHostPort.split(",");



                try
                {
                    String port = null;
                    for(int iUri = 0 ; iUri < ndbMgmHostNames.length; iUri++)
                    {
//                        System.out.println("URI Length = " + ndbMgmHostNames.length);
//                        System.out.println("URI Length = " + ndbMgmHostNames.length);


                        if(iUri < mgmHostPorts.length )
                            port = mgmHostPorts[iUri];

                        ndbMgmUri = ndbMgmHostNames[iUri] + ":" + port;
                        IsmaPersister.getLogByName("NDBMGMSYSTEM").info("URI  = " + ndbMgmUri);

                        try{
                            mgm = NdbMgmFactory.createNdbMgm(ndbMgmUri);
                        }
                        catch (Exception exMgm)
                        {
                            exMgm.printStackTrace();
                        }

                        mgm.setConnectTimeout(ndbconnectiontimeout);
                        try{
                            mgm.connect(ndbconnectionretry,
                                        ndbconnectionretrydelay, true);
                        }
                        catch(Exception exx )
                        {
                            if(iUri == ndbMgmHostNames.length)
                                throw new IsmaException(exx.getMessage());
                        }
                        try{
                            if (mgm.isConnected())
                                break;
                        }
                        catch(Throwable th)
                        {
                            th.printStackTrace();
                        }


                    }

                }
                catch(Exception ex)
                {
                    IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex);
                    PersistentObject poIn = new persistentObjectImpl(Resource.ETERNAL, mgm);
                    poIn.setKey("NDBMGM");
                    IsmaPersister.remove((Serializable) poIn.getKey(), poIn);

                }
                finally{
                    if(mgm == null)
                    {
                        System.out.println("**********  mgm NULL  **********");
                        System.out.println(" A HOST = " + ndbMgmHostName);
                        System.out.println(" A PORT = " + mgmHostPort);
                        System.out.println(" A URI = " + ndbMgmUri);

                    }
                    if(mgm.isConnected())
                    {
                        PersistentObject poIn = new persistentObjectImpl(Resource.ETERNAL, mgm);
                        poIn.setKey("NDBMGM");
                        IsmaPersister.set((Serializable) poIn.getKey(), poIn);

                        IsmaPersister.getLogByName("NDBMGMSYSTEM").info("Management console connected - stored as NDBMGM ");

                    }
                    else
                    {
                        PersistentObject poIn = new persistentObjectImpl(Resource.ETERNAL, mgm);
                        poIn.setKey("NDBMGM");
                        IsmaPersister.remove((Serializable) poIn.getKey(), poIn);

                    }

                }

            }



            Map categorieslistnerHandlers = new SynchronizedMap();
            categorieslistnerHandlers = getCategoryListnerHandlers();

            PersistentObject poIn = new persistentObjectImpl(Resource.ETERNAL, categorieslistnerHandlers);
            poIn.setKey("NDBMGMListeners");
            IsmaPersister.set((Serializable) poIn.getKey(), poIn);
            IsmaPersister.getLogByName("NDBMGMSYSTEM").info("All listner references loaded stored as NDBMGMListener");


        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            IsmaPersister.getLogSystem().error(ex);
        }
    }

    /**
     * getListnerHandlers
     * reads the <app_path>/conf/reference/listners_handlers.ini file
     * and loads the information  into objects of type ListnerHandler
     *
     *
     * @param listnerHandlers Map
     * @return Map
     */
    private static Map getCategoryListnerHandlers() {


       File nF = null;

//       if(IsmaPersister.getConfigParameterValueString("isma_configuration.listnerHandlerReference") != null
//          && !IsmaPersister.getConfigParameterValueString("isma_configuration.listnerHandlerReference").equals("")
//               )
//       {
//           nF = new File(IsmaPersister.getConfigParameterValueString("isma_configuration.listnerHandlerReference"));
//
//       }

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
               IniFile ini = new IniFile();
               ini.load(new FileReader(nF), true, "lh");
               Enumeration e = ini.keys();
               List lCat = new Vector(1);
               SynchronizedMap mCat = new SynchronizedMap(0);
               CategoryListner cat = null;

               while(e.hasMoreElements())
               {
                   String s = (String)e.nextElement();
                   if(("@" + s).indexOf("categories") > 0)
                   {
                       cat = new CategoryListner();
                       String[] sa = s.split("\\.");
                       if(sa.length > 0)
                       {
                           cat.setFullreferencename(sa[2]);
                           if(ini.getProperty(s) != null)
                           {
                               String[] prop = ini.getProperty(s).split(",");

                               if (prop[0] != null &&
                                   Integer.parseInt(prop[0]) > 0)
                                   cat.setActive(true);
                               else
                                   cat.setActive(false);

                               if(prop[1] != null)
                                   cat.setLogLevel(Integer.parseInt(prop[1]));

                               if(prop[2] != null)
                                   cat.setPoolingTime(Integer.parseInt(prop[2]));


                           }
                           cat.setName(sa[2].substring(sa[2].indexOf("NDB_MGM_EVENT_CATEGORY_".toLowerCase())));

                           cat.loadListnerReferences((Map)ini.getValuesMap());

                           mCat.put(cat.getName(),cat);
                       }


                   }

//                   System.out.println(s);
               }
               if(mCat.size() > 0 )
                   return mCat;


           } catch (NumberFormatException ex) {
               IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex);
               ex.printStackTrace();
           } catch (IOException ex) {
               IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex);
           }
       }

       return null;
    }

    /**
     * enableLogs
     */
    private static void enableLogs() {

       Logger l = Logger.getLogger("NDBMGMLISTENER");
       if (l != null)
           IsmaPersister.setLogByName("NDBMGMLISTENER",l);

       l = Logger.getLogger("NDBMGMSYSTEM");
       if (l != null)
        IsmaPersister.setLogByName("NDBMGMSYSTEM",l);

    }


}
