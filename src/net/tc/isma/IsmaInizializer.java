package net.tc.isma;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import net.tc.isma.resources.*;

import net.tc.isma.persister.PersistentFactory;
import org.apache.log4j.Logger;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.utils.Text;
import net.tc.isma.auth.security.UserBase;
import net.tc.isma.actions.Results;
import net.tc.isma.actions.generic.results;
import net.tc.isma.actions.Executioner;
import net.tc.isma.actions.generic.executionerImpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class IsmaInizializer extends HttpServlet
{
	private static final String CONTENT_TYPE = "text/xml";

	/**@todo set DTD*/
	private static final String DOC_TYPE = null;
	private static Logger log = null;


	//Initialize global variables
	public synchronized void init() throws ServletException
	{
		try
		{
                        System.out.println("********************************") ;

                        Enumeration props = System.getProperties().propertyNames();
                        while (props.hasMoreElements())
                        {
                            String propName = (String) props.nextElement();
                            System.out.println(propName + " " + System.getProperty(propName)) ;
                        }

//			String system = System.getProperty( "os.name" );
//			System.out.println( "System: " + system );
//			String javaversion = System.getProperty( "java.version" );
//			System.out.println( "Java Version: " + javaversion );
//			String javarunversion = System.getProperty( "java.runtime.version" );
//			System.out.println( "Java Run Version: " + javarunversion );

                        System.out.println("********************************") ;

		}
		catch( Exception exx )
		{
			System.out.println( " --- No Information on the System Available --- " );
		}

		System.out.println(" step 0 inizialize");



                try {
                      ServletContext context = getServletContext();
                      synchronized (context)
                      {
                          synchronized (context)
                          {
                              PersistentFactory.PersisterInizialize(context);
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
