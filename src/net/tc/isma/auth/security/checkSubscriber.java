package net.tc.isma.auth.security ;

import net.tc.isma.actions.generic.actionImpl ;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.resources.Resource;
import net.tc.isma.actions.Results;
import java.util.Map;
import java.util.HashMap;
import net.tc.isma.data.db.DynamicSqlXmlStatments;
import java.io.Serializable;
import net.tc.isma.persister.PersistentObject;
import net.tc.isma.actions.generic.results;
import java.util.Vector;
import net.tc.isma.request.generic.requestImpl;
import net.tc.isma.utils.Text;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class checkSubscriber extends actionImpl
{
    public checkSubscriber()
    {
    }
    private results resultLocal = null;
    public Results execute()
    {
        resultLocal = (results)this.getResult();
        requestImpl reqImpl = getRequest();
        Vector subscribers = null;
        HttpSession session1 = reqImpl.getSession();

        Subscriber subscriberTocheck = new Subscriber();
        String userName = Text.defaultTo(reqImpl.getRequest().getParameter("user"),null);
        String password = Text.defaultTo(reqImpl.getRequest().getParameter("password"),null);
        boolean logOut = false;

        if(reqImpl.getRequest().getParameter("logout") != null
           && reqImpl.getRequest().getParameter("logout").equals("true"))
        {
            session1.removeAttribute(UserBase.class.toString());
            UserBase user = new UserBase();
            user.setId("0");
            user.setName("Anonym");
            user.setUsername("anonym");
            user.setSurname("Anonym");
            user.setAnonymous(true);
            user.setIp(reqImpl.getRequest().getRemoteAddr());

            reqImpl.setUserBean(user);
            return resultLocal;
        }

        subscriberTocheck.setUsername(userName);
        if(password != null)
            subscriberTocheck.setPasswordEncoded(password);

        subscriberTocheck.setIp(reqImpl.getRequest().getRemoteAddr());

        subscribers = ( Vector ) IsmaPersister.getUsers() ;


        if(subscriberTocheck.getUsername() != null && subscribers != null)
        {
            try
            {
                Subscriber subscriberAuthorized = (Subscriber)userValidator.validateUser(subscriberTocheck);
                if(subscriberAuthorized == null)
                     return (Results)resultLocal;

                 session1.setAttribute(UserBase.class.toString(),subscriberAuthorized);
                 reqImpl.setUserBean(subscriberAuthorized);
            }
            catch ( Exception ex )
            {
                IsmaPersister.getLogDataAccess().error( "" , ex ) ;
            }
        }
        return (Results)resultLocal;
    }
    public void checkSubscriberM(HttpServletRequest req)
    {
        requestImpl reqImpl = new requestImpl(req);
        Vector subscribers = null;
        HttpSession session1 = reqImpl.getSession();

        Subscriber subscriberTocheck = new Subscriber();
        String userName = Text.defaultTo(reqImpl.getRequest().getParameter("user"),null);
        String password = Text.defaultTo(reqImpl.getRequest().getParameter("password"),null);
        boolean logOut = false;

        if(reqImpl.getRequest().getParameter("logout") != null
           && reqImpl.getRequest().getParameter("logout").equals("true"))
        {
            session1.removeAttribute(UserBase.class.toString());
            UserBase user = new UserBase();
            user.setId("0");
            user.setName("Anonym");
            user.setUsername("anonym");
            user.setSurname("Anonym");
            user.setAnonymous(true);
            user.setIp(reqImpl.getRequest().getRemoteAddr());

            reqImpl.setUserBean(user);
        }

        subscriberTocheck.setUsername(userName);
        if(password != null)
            subscriberTocheck.setPasswordEncoded(password);

        subscriberTocheck.setIp(reqImpl.getRequest().getRemoteAddr());

        subscribers = ( Vector ) IsmaPersister.getUsers() ;


        if(subscriberTocheck.getUsername() != null && subscribers != null)
        {
            try
            {
                Subscriber subscriberAuthorized = (Subscriber)userValidator.validateUser(subscriberTocheck);
                if(subscriberAuthorized == null)
                     return ;

                 session1.setAttribute(UserBase.class.toString(),subscriberAuthorized);
                 reqImpl.setUserBean(subscriberAuthorized);
            }
            catch ( Exception ex )
            {
                IsmaPersister.getLogDataAccess().error( "" , ex ) ;
            }
        }
        return;
    }
    public void checkSubscriberByIp(HttpServletRequest req)
    {
        requestImpl reqImpl = new requestImpl(req);
        Vector subscribers = null;
        HttpSession session1 = reqImpl.getSession();

        Subscriber subscriberTocheck = new Subscriber();
        String IP = Text.defaultTo(reqImpl.getRequest().getRemoteAddr(),null);

        boolean logOut = false;

        if(reqImpl.getRequest().getParameter("logout") != null
           && reqImpl.getRequest().getParameter("logout").equals("true"))
        {
            session1.removeAttribute(UserBase.class.toString());
            UserBase user = new UserBase();
            user.setId("0");
            user.setName("Anonym");
            user.setUsername("anonym");
            user.setSurname("Anonym");
            user.setAnonymous(true);
            user.setIp(reqImpl.getRequest().getRemoteAddr());

            reqImpl.setUserBean(user);
        }

        subscriberTocheck.setUsername("Multiple");

        subscriberTocheck.setIp(reqImpl.getRequest().getRemoteAddr());

        subscribers = ( Vector ) IsmaPersister.getUsers() ;


        if( subscribers != null)
        {
            try
            {
                Subscriber subscriberAuthorized = (Subscriber)userValidator.validateByIp(IP);
                if(subscriberAuthorized == null)
                     return ;

                 session1.setAttribute(UserBase.class.toString(),subscriberAuthorized);
                 reqImpl.setUserBean(subscriberAuthorized);
            }
            catch ( Exception ex )
            {
                IsmaPersister.getLogDataAccess().error( "" , ex ) ;
            }
        }
        return;

    }

}
