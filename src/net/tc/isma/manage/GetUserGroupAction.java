package net.tc.isma.manage;

import java.util.Hashtable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import net.tc.isma.*;
import java.util.Map;
import java.util.HashMap;
import net.tc.isma.data.*;
import net.tc.isma.utils.*;
import net.tc.isma.admin.AdminDatastoreAction;
import net.tc.isma.workflows.workflow;
import net.tc.isma.auth.security.*;
import java.util.ArrayList;
import java.util.List;
import net.tc.isma.actions.RestrictedAction;
import javax.servlet.ServletRequest;
import net.tc.isma.actions.generic.results;
import org.hibernate.Session;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.actions.Results;
import net.tc.isma.data.hibernate.*;
import org.hibernate.Transaction;

public abstract class GetUserGroupAction  extends AdminDatastoreAction implements  RestrictedAction
{

    /**
     * Implement this method with logic that:<br>
     * 1) Performs the appropriate query based on the parameters in the request<br>
     * 2) Places the results into the result Map
     * @param request
     * @param result
     * @param ds
     * @throws Exception
     */
    public  Results execute()
    {
            results resultsImpl = (results)this.getResult();
            HttpServletRequest request = this.getRequest().getRequest();
            HSession ds = IsmaPersister.getSessionFactory().openSession();
            Transaction tr = ds.beginTransaction();

            try
            {
                super.init();
            this.loadExtent(resultsImpl, ds, Group.class,"id");
            this.loadExtent(resultsImpl, ds, UserBase.class,"id");
            this.loadExtent(resultsImpl, ds, GrpXMod.class,"groupn", "where application='"+ IsmaPersister.getAPPLICATIONNAME() +"' ");
            this.loadExtent(resultsImpl, ds, GrpXRole.class,"gname", "where application='"+ IsmaPersister.getAPPLICATIONNAME() +"' ");
            this.loadExtent(resultsImpl, ds, Role.class,"id");

            List GrpXModList = new ArrayList();
            List GrpXRoleList = new ArrayList();
            List tpm = null;

            if(resultsImpl.get(GrpXMod.class) != null && ((List)resultsImpl.get(GrpXMod.class)).size() > 0)
            {
                tpm = (List)resultsImpl.get(GrpXMod.class);
                for(int i = 0 ; i < tpm.size() ; i++)
                {
                    GrpXModList.add(((GrpXMod)tpm.get(i)).getModule() + "_" + ((GrpXMod)tpm.get(i)).getGroupn());
                }
                resultsImpl.remove(GrpXMod.class);
                resultsImpl.put(GrpXMod.class,GrpXModList);
                tpm.clear();
                tpm = null;
            }

            if(resultsImpl.get(GrpXRole.class) != null && ((List)resultsImpl.get(GrpXRole.class)).size() > 0)
            {
                tpm = (List)resultsImpl.get(GrpXRole.class);
                for(int i = 0 ; i < tpm.size() ; i++)
                {
                    GrpXRoleList.add(((GrpXRole)tpm.get(i)).getGname() + "_" + ((GrpXRole)tpm.get(i)).getRname());
                }
                resultsImpl.remove(GrpXRole.class);
                resultsImpl.put(GrpXRole.class,GrpXRoleList);
                tpm.clear();
                tpm = null;
            }

            IsmaPersister.getSessionFactory().closeSession(ds);
            return resultsImpl;
        }catch(Exception ex)
        {
            IsmaPersister.getSessionFactory().closeSession(ds);
            ex.printStackTrace();
        }

        return this.getResult();
    }





}
