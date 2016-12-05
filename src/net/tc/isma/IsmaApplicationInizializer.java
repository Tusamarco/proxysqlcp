package net.tc.isma;

import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.persister.PersistentFactory;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.io.File;

/**
 * <p>Title: Isma Framework</p>
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
public class IsmaApplicationInizializer {

    private String path = null;

    public IsmaApplicationInizializer() {
    }

    public static void main(String[] args) {
        IsmaApplicationInizializer ismaapplicationinizializer = new IsmaApplicationInizializer();

        if(args == null
           || args.length < 1
           || args[0].equals("help")
           )
        {
            System.out.println("You _MUST_ provide the following informations:");
            System.out.println("<application path>");
            System.exit(1);
        }

        ismaapplicationinizializer.path = args[0];



        try{

            ismaapplicationinizializer.init();

            IsmaApplicationController.main(args);

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static final String CONTENT_TYPE = "text/xml";

    /**@todo set DTD*/
    private static final String DOC_TYPE = null;
    private static Logger log = null;


    //Initialize global variables
    public synchronized void init() throws Exception
    {
        File f = new File(path + "/WEB-INF");
        if (!f.exists()) {
            throw new IsmaException("Main application Path could not be Invalid");
        }

        f = null;

            try
            {
                    System.out.println("********************************") ;

                    Enumeration props = System.getProperties().propertyNames();
                    while (props.hasMoreElements())
                    {
                        String propName = (String) props.nextElement();
                        System.out.println(propName + " " + System.getProperty(propName)) ;
                    }


                    System.out.println("********************************") ;

            }
            catch( Exception exx )
            {
                    System.out.println( " --- No Information on the System Available --- " );
            }

            System.out.println(" step 0 inizialize");



            try {
                  f = new File(path);
                  synchronized (f)
                  {
                      synchronized (f)
                      {
                          PersistentFactory.PersisterInizialize(path);
                      }
                 }
            } catch (IsmaException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
            if(log == null)
                    log = IsmaPersister.getLogSystem();





    }


}
