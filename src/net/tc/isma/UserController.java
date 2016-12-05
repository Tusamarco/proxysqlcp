package net.tc.isma;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.*;
import net.tc.isma.auth.security.*;
import net.tc.isma.persister.*;
import net.tc.isma.request.generic.*;
import net.tc.isma.utils.*;
import net.tc.isma.data.hibernate.HSession;
import org.hibernate.Transaction;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class UserController extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml";

    /**@todo set DTD*/
    private static final String DOC_TYPE = null;
    private static Logger log = null;
    private static long reqServed = 0;
    private static String reqServedSince = (Integer.parseInt(Utility.
            getDayNumber()) - 1) + "/" + Utility.getMonth() + "/" +
                                           Utility.getYear();


    //Initialize global variables
    public synchronized void init() throws ServletException {
    }

    public static synchronized void initStatic() throws ServletException {

    }

//	//Process the HTTP Get request
//	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
//	{
//	}
//
//	//Process the HTTP Put request
//	public void doPut( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
//	{
//	}

    //Clean up resources
    public void destroy() {
    }

    public static void logEntry(PrintStream outp, Map m) {
        if (log == null)
            setLog();

        Map configMap = m;
        if (configMap != null && configMap.size() > 0) {
            Iterator it = configMap.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object value = configMap.get(key);
                log.info("*** " + key + " = " + value);
            }
        }

    }

    public static void logEntry(PrintStream out, Serializable serObj) {
        if (log == null)
            setLog();

        if (serObj != null) {
            log.info(serObj.toString());
//			System.out.println("*** " + serObj.toString());
        }

    }

    public static void logEntry(PrintStream out, Serializable[] serObj) {
        if (log == null)
            setLog();

        if (serObj != null && serObj.length > 0) {
            for (int i = 0; i < serObj.length; i++)
                log.info(serObj.toString());

//				System.out.println("*** " + serObj[i].toString());
        }

    }

    private static void setLog() {
        log = IsmaPersister.getLogSystem();
    }

    public void service(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
//		String actionChainParameter = Text.defaultTo(req.getParameter("ac"), null);

        UserBase user = checkRetrieveUser(req);
        requestImpl requestWrapper = new requestImpl(req, getServletConfig());
        requestWrapper.setUserBean(user);
        requestWrapper.setResponse(resp);
        requestWrapper.setDataStoreFactory(IsmaPersister.getSessionFactory());

        RequestDispatcher disp = null;
        ServletContext context = this.getServletContext();
        String controllerName = IsmaPersister.getConfigParameterValueString("isma_configuration.httpcontroller");

        if(user != null && !user.isAnonymous())
        {


            if(controllerName == null)
            {
                System.out.println("-------- APPLICATION IS GOING TO STOP JAVA VM--- ");
                System.out.println("-------- NO httpcontroller define in the webxml file --- ");
                System.exit(0);
            }
            disp = context.getRequestDispatcher(controllerName);
            if (!resp.isCommitted())
                disp.forward(req, resp);

            resp.flushBuffer();

        }
        else
        {
            disp = context.getRequestDispatcher("/loginf.jsp");
            if (!resp.isCommitted())
                disp.forward(req, resp);

            resp.flushBuffer();

        }

        if (!resp.isCommitted()) {
            resp.getOutputStream().flush();
            resp.getOutputStream().close();
        }
        return;
    }

    private static UserBase checkRetrieveUser(HttpServletRequest req) {
        if (req == null)
            return null;

        String userIp = req.getRemoteAddr();
        String user = null;
        String password = null;
        String domain = null;

        if(req.getParameter("user") != null && !req.getParameter("user").equals(""))
            user = req.getParameter("user");

        if(req.getParameter("password") != null && !req.getParameter("password").equals(""))
        {
            password = req.getParameter("password");

        }
        if(req.getParameter("domain") != null && !req.getParameter("domain").equals(""))
            domain = req.getParameter("domain");

        if(
            user != null && !user.equals("")
            && password != null && !password.equals("")
                )
        {
            getUser(req, new Object[]{user,password,domain});
        }

        HttpSession session1 = req.getSession(true);
        UserBase userb = (UserBase) session1.getAttribute(UserBase.class.toString());
        if (userb != null)
            return userb;

        userb = new UserBase();
        userb.setId("0");
        userb.setName("Anonym");
        userb.setUsername("anonym");
        userb.setSurname("Anonym");
        userb.setAnonymous(true);
        userb.setIp(req.getRemoteAddr());
        return userb;
    }

    /**
     * getUser
     *
     * @param req HttpServletRequest
     * @param objects Object[]
     */
    private static void getUser(HttpServletRequest req, Object[] parameters) {

        String domain = (String)parameters[2];
        if(domain != null && !domain.equals(""))
        {
            doNtSecurityCheck(req, parameters);
            return;
        }
        else
        {
            doLocalSecurityCheck(req, parameters);
        }

    }

    /**
     * doLocalSecurityCheck
     *
     * @param req HttpServletRequest
     * @param parameters Object[]
     */
    private static void doLocalSecurityCheck(HttpServletRequest req,
                                             Object[] parameters) {
        try
        {
            String user = (String)parameters[0];
            String password = (String)parameters[1];
            UserBase userB = null;


            IsmaPersister.getLogSystem().info("**** checking user [Start]");
            if (user != null && !user.equals("") && password != null && !password.equals(""))
            {
                try {
                    HSession ds = IsmaPersister.getSessionFactory().
                                  openSession();
                    Transaction tr = ds.beginTransaction();
                    String hSql = "select us from net.tc.isma.auth.security.UserBase as us where us.id='"+ user +"' and us.password='"+ password +"' ";
                    List l = ds.findDirect(hSql);
                    ListIterator it = l.listIterator();

                    while (it.hasNext()) {
                         userB = (UserBase) it.next();


                    }
                    IsmaPersister.getLogSystem().info("**** checking user [End]");
                    tr.commit();
                    IsmaPersister.getSessionFactory().closeSession(ds);

                    if(userB != null)
                    {
                        req.getSession(true).setAttribute(UserBase.class.toString(),userB);

                    }
                    return;

                } catch (Throwable ex) {
                    IsmaPersister.getLogDataAccess().error(ex);
                    ex.printStackTrace();
                } finally {
                    return ;
                }
            }
        }
        catch (Throwable ex)
        {
            IsmaPersister.getLogDataAccess().error(ex);
            return ;
        }


    }

    /**
     * doSecurityCheck
     *
     * @param req HttpServletRequest
     * @param parameters Object[]
     */
    private static void doNtSecurityCheck(HttpServletRequest req, Object[] parameters) {
    }

}
