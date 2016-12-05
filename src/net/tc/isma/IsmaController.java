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
import net.tc.isma.request.generic.requestImpl;
import net.tc.isma.utils.Utility;
import net.tc.isma.auth.security.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class IsmaController extends HttpServlet {
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

    protected void service(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
		String actionChainParameter = Text.defaultTo(req.getParameter("ac"), null);

        UserBase user = CreateRetrieveUser(req);
        requestImpl requestWrapper = new requestImpl(req, getServletConfig());
        requestWrapper.setUserBean(user);
        results result = new results();
        result.put("reqServed", new Long(reqServed));
        result.put("reqServedSince", reqServedSince);

        requestWrapper.setResponse(resp);
        requestWrapper.setResult(result);

        if(IsmaPersister.isUsehibernate())
            requestWrapper.setDataStoreFactory(IsmaPersister.getSessionFactory());

        if (reqServed == 0) {
            synchronized (requestWrapper) {
                synchronized (requestWrapper) {
                    inizializeReference(req, resp, result);
                }
            }

        }
        reqServed++;
        Executioner exec = new executionerImpl(requestWrapper);
        exec.execute();
        if (!resp.isCommitted()) {
            resp.getOutputStream().flush();
            resp.getOutputStream().close();
        }
        return;
    }

    private synchronized void inizializeReference(HttpServletRequest req,
                                                  HttpServletResponse resp,
                                                  results result) {
        req.setAttribute("ac", "initall");
        requestImpl requestWrapperInit = new requestImpl(req);
        requestWrapperInit.setSystem(true);
        requestWrapperInit.setResult(result);
        requestWrapperInit.setResponse(resp);
        Executioner execInit = new executionerImpl(requestWrapperInit);
        execInit.execute();
        results resultInit = (results) requestWrapperInit.getResult();
        IsmaPersister.getLogSystem().info(
                " REFERENCE INIZIALIZATION COMPLETED [YES|NO] = " +
                (Boolean) resultInit.get("inizilized"));
    }

    private UserBase CreateRetrieveUser(HttpServletRequest req) {
        if (req == null)
            return null;

        String userIp = req.getRemoteAddr();

        HttpSession session1 = req.getSession(true);
        UserBase user = (UserBase) session1.getAttribute(UserBase.class.
                toString());
        if (user != null)
            return user;

        user = new UserBase();
        user.setId("0");
        user.setName("Anonym");
        user.setUsername("anonym");
        user.setSurname("Anonym");
        user.setAnonymous(true);
        user.setIp(req.getRemoteAddr());
        return user;
    }

}
