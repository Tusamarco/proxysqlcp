package net.tc.isma.manage;

import net.tc.isma.admin.AdminDatastoreAction;
import net.tc.isma.utils.Text;
import net.tc.isma.auth.security.UserBase;
import java.util.Set;
import java.util.HashSet;
import net.tc.isma.data.*;
import net.tc.isma.*;
import net.tc.isma.auth.security.*;
import java.io.Serializable;
import net.tc.isma.actions.RestrictedAction;
import javax.servlet.ServletRequest;
import org.hibernate.Session;
import net.tc.isma.actions.generic.results;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.actions.Results ;
import javax.servlet.http.HttpServletRequest;
import net.tc.isma.data.hibernate.*;
import org.hibernate.Transaction;

public class SetUserGroupAction extends AdminDatastoreAction implements  RestrictedAction
{
    /*
    *valid values for action
    * 1 = add - user
    * 2 = save - user
    * 3 = delete - user
    */
    int action = 0;
    public SetUserGroupAction()
    {
    }

    public Results execute()
    {
        results resultsImpl = (results)this.getResult();
        HttpServletRequest request = this.getRequest().getRequest();
        HSession ds = IsmaPersister.getSessionFactory().openSession();
        Transaction tr = ds.beginTransaction();

        super.init();

        action = Integer.parseInt(Text.defaultTo(request.getParameter("useraction"),"0"));
        if(action != 0)
        {
            int intCodeReturn = 0;
            switch (action)
            {
                case 1: intCodeReturn=saveUser(request, resultsImpl, ds);break;
                case 2: intCodeReturn=saveUser(request, resultsImpl, ds);break;
                case 3: intCodeReturn=deleteUser(request, resultsImpl, ds);break;
            }
        }
        IsmaPersister.getSessionFactory().closeSession(ds);
        return this.getResult();
    }
//    private int addUser(ServletRequest request, Results results, HSession ds)
//    {
//        UserBase user = fillUserBase(request, ds);
//        if(user != null)
//        {
//            try
//            {
//                ds.beginTransaction();
//                ds.save(user);
//                ds.flush();
//                ds.connection().commit();
//                ds.close();
//
//            }
//            catch (Exception ex)
//            {
//                try{
//                    ds.connection().rollback();
//                    FaostatPersister.getLogSystem().error("**** " + this.getClass().getName() + " " + ex);
//                    ex.printStackTrace();
//                }catch(Exception eex){eex.printStackTrace();}
//            }
//        }
//        return 0;
//    }
    private int saveUser(ServletRequest request, results resultsImpl, HSession ds)
    {
        UserBase user = fillUserBase(request, ds);
        if(user != null)
        {
            try
            {
                ds.beginTransaction();
                ds.saveOrUpdate(user);
                ds.flush();
                ds.connection().commit();
                ds.close();
            }
            catch (Exception ex)
            {
                try{
                    ds.connection().rollback();
                    IsmaPersister.getLogSystem().error("**** " + this.getClass().getName() + " " + ex);
                    ex.printStackTrace();
                }catch(Exception eex){eex.printStackTrace();}
            }
        }
        return 0;
    }
    private int deleteUser(ServletRequest request, results resultsImpl, HSession ds)
    {
        if(request.getParameter("useralias") != null && !request.getParameter("useralias").equals(""))
        {
            UserBase user = getUserFromRequest( request, ds);
            //    getUser((String)request.getParameter("useralias"), ds);
            deleteUserBean(user, ds);
        }

        return 0;
    }

    private void deleteUserBean(UserBase user, HSession ds)
    {
            if(user != null)
            {
                try
                {
                    ds.delete(user);
                    ds.flush();
                    ds.connection().commit();

                }
                catch (Exception ex)
                {
                    try{
                        ds.connection().rollback();
                        IsmaPersister.getLogSystem().error("**** " + this.getClass().getName() + " " + ex);
                        ex.printStackTrace();
                    }catch(Exception eex){eex.printStackTrace();}
                }
            }
    }

    private UserBase fillUserBase(ServletRequest request,  HSession ds)
    {
        UserBase user = null;
        user = getUserFromRequest(request, ds);
        if(user != null )
        {
            user.setPassword((String)request.getParameter("userpassword"));
            user.setName((String)Text.defaultTo(request.getParameter("username"),null));
            user.setSurname((String)Text.defaultTo(request.getParameter("userlastname"),null));
            user.setEmail((String)Text.defaultTo(request.getParameter("useremail"),null));
            if(Text.defaultTo((String)request.getParameter("usergroupslist"),null) != null)
            {
                user.setGroup(getGroups(((String)request.getParameter("usergroupslist")).split(","), ds));
            }
        }
        return user;
    }
    private UserBase getUserFromRequest(ServletRequest request, HSession ds)
    {
        UserBase user = null;

        if(request.getParameter("useralias") != null && !request.getParameter("useralias").equals("") &&
           request.getParameter("userpassword") != null && !request.getParameter("userpassword").equals("")
           )
        {

            String alias = (String) request.getParameter("useralias");
            String domain = Text.defaultTo( (String) request.getParameter("useraccountdomain"), "");
            String domainOriginal = Text.defaultTo( (String) request.getParameter("userdomain"), "");
            if (!domain.equals(domainOriginal))
            {
                String prefix = "";
                if (!domainOriginal.equals(""))
                    prefix = domainOriginal + "/";
                alias = prefix + alias;
            }
            else
            {
                String prefix = "";
                if (!domain.equals(""))
                    prefix = domain + "/";
                alias = prefix + alias;
            }

            user = getUser(alias, ds);
            if (!domain.equals(domainOriginal))
            {
                deleteUserBean(user, ds);
                user = null;
                String prefix = "";
                alias = "";
                if (!domain.equals(""))
                    prefix = domain + "/";
                alias = prefix + (String) request.getParameter("useralias");
                user = getUser(alias, ds);
            }
        }
        return user;
    }
    private Set getGroups(String[] groups, HSession ds)
    {
        Set groupSet = null;
        if(groups != null && groups.length > 0)
        {
            groupSet = new HashSet();
            for(int i = 0 ; i < groups.length ; i++)
            {
                try
                {
                    groupSet.add(ds.load(Group.class, groups[i]));
                }
                catch (Exception ex)
                {
                    System.out.println("*************** error loading groups ******");
                    ex.printStackTrace();
                }
            }

        }
        return groupSet;
    }
    private UserBase getUser(Serializable id, HSession ds)
    {
        UserBase user = null;
        if(id != null)
        {
            try
            {
                user = (UserBase) ds.load(UserBase.class, id);
            }
            catch (Exception ex)
            {
                System.out.println("***** Error loading user " + id);
                //ex.printStackTrace();
            }
            if(user == null)
            {
                user = new UserBase();
                user.setId((String)id);
            }
        }
        return user;
    }

}
