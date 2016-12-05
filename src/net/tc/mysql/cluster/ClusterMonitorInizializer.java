package net.tc.mysql.cluster;

import net.tc.isma.IsmaApplicationInizializer;
import net.tc.isma.persister.IsmaPersister;
import java.io.File;
import net.tc.isma.persister.PersistentFactory;
import net.tc.isma.IsmaException;
import java.util.Enumeration;
import org.apache.log4j.Logger;
import net.tc.isma.IsmaApplicationController;
import java.util.Map;
import net.tc.isma.utils.SynchronizedMap;

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
public class ClusterMonitorInizializer {

    private static String aplicationPath = null;
    private static String mainconfigfile = null;
    private static String configPath = null;
    private static String mysqlname = "test";
    private static String mgmhosts = null;
    private static String mgmports = null;
    private static String snmphost = null;
    private static String snmppath = null;




    private static final String CONTENT_TYPE = "text/xml";

    /**@todo set DTD*/
    private static final String DOC_TYPE = null;
    private static Logger log = null;


    public ClusterMonitorInizializer() {
    }

    public static void main(String[] args) {
//        ClusterMonitorInizializer clustermonitorinizializer = new
//                ClusterMonitorInizializer(args);

        ClusterMonitorInizializer clusterMonitorinizializer = new
                ClusterMonitorInizializer();

        if (args == null
            || args.length < 1
            || args[0].equals("help")
                ) {
            showHelp();
        }


//        clusterMonitorinizializer.aplicationPath = args[0];

        Map argsMap = new SynchronizedMap(args.length);
        for (int i = 0; i < args.length; i++) {

            if (args[i].indexOf("=") < 0)
                continue;

            String[] KeyValue = args[i].split("=");
            argsMap.put(KeyValue[0], KeyValue[1]);

        }



        try {



            if(argsMap.get("mainconfigpath") != null)
            {
                clusterMonitorinizializer.aplicationPath = (String)argsMap.get("mainconfigpath");
                IsmaPersister.setApplicationVariable("mainpath", aplicationPath);
            }
            else
                showHelp();

            if(argsMap.get("mainconfigfile") != null)
            {
                clusterMonitorinizializer.mainconfigfile = (String)argsMap.get("mainconfigfile");
                IsmaPersister.setApplicationVariable("mainconfigfile", mainconfigfile);
            }
            else
                showHelp();


            if(argsMap.get("mysqlname") != null)
            {
                clusterMonitorinizializer.mysqlname = (String) argsMap.get("mysqlname");
                IsmaPersister.setApplicationVariable("mysqlname", mysqlname);
                IsmaPersister.setApplicationVariable("logConfigurationName", "/" + mysqlname + "_log.properties" );
            }


            if(argsMap.get("snmphost") != null)
            {
                clusterMonitorinizializer.snmphost = (String) argsMap.get("snmphost");
                IsmaPersister.setApplicationVariable("snmphost", snmphost);
            }

            if(argsMap.get("snmppath") != null)
            {
                clusterMonitorinizializer.snmppath = (String) argsMap.get("snmppath");
                IsmaPersister.setApplicationVariable("snmppath", snmppath);
            }

            if(argsMap.get("mgmhosts") != null)
            {
                clusterMonitorinizializer.mgmhosts = (String) argsMap.get("mgmhosts");
                IsmaPersister.setApplicationVariable("mgmhosts", mgmhosts);
            }

            if(argsMap.get("mgmports") != null)
            {
                clusterMonitorinizializer.mgmports = (String) argsMap.get("mgmports");
                IsmaPersister.setApplicationVariable("mgmports", mgmports);
            }





            clusterMonitorinizializer.init();

            IsmaApplicationController.main(args);

            System.exit(0);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private static void showHelp() {
        System.out.println("You _MUST_ provide the following informations:");
        System.out.println("mainconfigpath=<path>");
        System.out.println("mainconfigfile=<filename>");
        System.out.println("ac=<action>");
        System.out.println("snmphost=<host>");
        System.out.println("snmppath=<path>");

        System.out.println("mysqlname=<name>");
        System.out.println("mgmhosts=<ip(,ip)>");
        System.out.println("mgmports=<port(,port)>");

        System.out.println("----------------------");
        System.out.println("Following are optional");
        System.out.println("do=<action params>");
        System.out.println("configPath=<path>");


        System.exit(1);
    }
//    public ClusterMonitorInizializer(String[] args) {
//        if(args == null
//            || args.length < 1
//            || args[0].equals("help")
//            )
//         {
//             System.out.println("You _MUST_ provide the following informations:");
//             System.out.println("<application path>");
//             System.exit(1);
//        }
//
//        this.init();
//
//    }

    public synchronized void init() throws Exception{


//        IsmaApplicationInizializer.main(args);

        File f = new File(aplicationPath + "/WEB-INF");
        if (!f.exists()) {
            f = new File(aplicationPath);
            if (!f.exists()) {
                throw new IsmaException(
                        "Main application Path could not be Invalid");
            }
        }

        f = null;

        try {
            System.out.println("********************************");

            Enumeration props = System.getProperties().propertyNames();
            while (props.hasMoreElements()) {
                String propName = (String) props.nextElement();
                System.out.println(propName + " " + System.getProperty(propName));
            }

            System.out.println("********************************");
            String localclassPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString().substring(5);

        } catch (Exception exx) {
            System.out.println(" --- No Information on the System Available --- ");
        }

        System.out.println(" step 0 inizialize");

        try {
            f = new File(aplicationPath);
            synchronized (f) {
                synchronized (f) {
                    PersistentFactory.PersisterInizialize(aplicationPath);
                }
            }
        } catch (IsmaException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }
        if (log == null)
            log = IsmaPersister.getLogSystem();





    }

}
