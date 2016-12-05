package net.tc.isma.manage;

import net.tc.isma.admin.AdminDatastoreAction;
import net.tc.isma.actions.RestrictedAction;
import net.tc.isma.actions.Results;
import net.tc.isma.utils.Text;
import net.tc.isma.auth.security.UserBase;
import java.util.Set;
import java.util.HashSet;
import net.tc.isma.data.*;
import net.tc.isma.data.hibernate.HSession;
import net.tc.isma.*;
import net.tc.isma.auth.security.*;
import java.io.Serializable;
import javax.servlet.ServletRequest;
import net.tc.isma.actions.Results;
import org.hibernate.Session;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.data.objects.Module;
import javax.servlet.http.HttpServletRequest;
import net.tc.isma.actions.generic.results;
//import net.tc.isma.data.hibernate.*;
import org.hibernate.Transaction;

public class SetRolesGroupAction extends AdminDatastoreAction implements  RestrictedAction
{
    /*
    *valid values for action
    * 1 = add - group
    * 2 = save - group
    * 3 = delete - group
    */
    int action = 0;
    public SetRolesGroupAction()
    {
    }

    public Results execute()
    {
        results resultsImpl = (results)this.getResult();
        HttpServletRequest request = this.getRequest().getRequest();
        HSession ds = IsmaPersister.getSessionFactory().openSession();
        Transaction tr = ds.beginTransaction();


        super.init();

        action = Integer.parseInt(Text.defaultTo(request.getParameter("groupaction"),"0"));
        if(action != 0)
        {
            int intCodeReturn = 0;
            switch (action)
            {
                case 1: intCodeReturn=saveGroup(request, resultsImpl, ds);break;
                case 2: intCodeReturn=saveGroup(request, resultsImpl, ds);break;
                case 3: intCodeReturn=deleteGroup(request, resultsImpl, ds);break;
            }
        }
        IsmaPersister.getSessionFactory().closeSession(ds);
        return this.getResult();
    }
//    private int addUser(ServletRequest request, Results results, Session ds)
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
    private int saveGroup(ServletRequest request, Results results, HSession ds)
    {
        Group group = fillGroupBase(request, ds);
        if(group != null)
        {
            try
            {
                ds.beginTransaction();
                ds.saveOrUpdate(group);
                ds.flush();
                ds.connection().commit();
                ds.close();
                if(request.getParameter("m") != null && !request.getParameter("m").equals(""))
                {
                    Module mod = null;
                    mod = (Module) getModule().get(((String)request.getParameter("m")).toUpperCase());
//                    if(mod != null)
//                        request.getApplication().reloadModule(mod);
                }
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
    private int deleteGroup(ServletRequest request, Results results, HSession ds)
    {
        if(request.getParameter("groupalias") != null && !request.getParameter("groupalias").equals(""))
        {
            Group group = getGroupFromRequest( request, ds);
            //    getUser((String)request.getParameter("useralias"), ds);
            deleteGroupBean(group, ds);
//            if(request.getParameter("m") != null && !request.getParameter("m").equals("")
//               && (Module)this.getModule().get(((String)request.getParameter("m")).toUpperCase()) != null)
//                request.getApplication().reloadModule((Module)this.getModule().get(((String)request.getParameter("m")).toUpperCase()));

        }

        return 0;
    }

    private void deleteGroupBean(Group group, HSession ds)
    {
            if(group != null)
            {
                try
                {
                    ds.delete(group);
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

    private Group fillGroupBase(ServletRequest request, HSession ds)
    {
        Group group = null;
        group = getGroupFromRequest(request, ds);
        if(group != null )
        {
            group.setId((String)Text.defaultTo(request.getParameter("groupalias"),null));
            if(Text.defaultTo((String)request.getParameter("rolesgroupslist"),null) != null)
            {
                group.setRole(getRoles(((String)request.getParameter("rolesgroupslist")).split(","), ds));
            }
        }
        return group;
    }
    private Group getGroupFromRequest(ServletRequest request, HSession ds)
    {
        Group group = null;

        if(request.getParameter("groupalias") != null && !request.getParameter("groupalias").equals(""))
        {

            String alias = (String) request.getParameter("groupalias");

            group = getGroup(alias, ds);
        }
        return group;
    }
    private Set getRoles(String[] roles, HSession ds)
    {
        Set rolesSet = null;
        if(roles != null && roles.length > 0)
        {
            rolesSet = new HashSet();
            for(int i = 0 ; i < roles.length ; i++)
            {
                try
                {
                    rolesSet.add(ds.load(Role.class, roles[i]));
                }
                catch (Exception ex)
                {
                    System.out.println("*************** error loading groups ******");
                    ex.printStackTrace();
                }
            }

        }
        return rolesSet;
    }
    private Group getGroup(Serializable id, HSession ds)
    {
        Group group = null;
        if(id != null)
        {
            try
            {
                group = (Group) ds.load(Group.class, id);
            }
            catch (Exception ex)
            {
                System.out.println("***** Error loading user " + id);
                //ex.printStackTrace();
            }
            if(group == null)
            {
                group = new Group();
                group.setId((String)id);
            }
        }
        return group;
    }

}
