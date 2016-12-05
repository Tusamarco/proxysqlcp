package net.tc.isma.admin;

import java.util.*;
import net.tc.isma.*;
import java.util.Map;
import java.util.HashMap;
import net.tc.isma.data.hibernate.*;
import net.tc.isma.utils.*;
import net.tc.isma.workflows.workflow;

import javax.servlet.ServletRequest;
import net.tc.isma.actions.generic.actionImpl;
import net.tc.isma.actions.RestrictedAction;
import net.tc.isma.actions.Results;
import net.tc.isma.data.objects.Module;
import org.hibernate.Session;
import net.tc.isma.actions.generic.results;
import net.tc.isma.persister.IsmaPersister;


public abstract class AdminDatastoreAction  extends actionImpl implements  RestrictedAction
{
    Map modules = null;

     workflow wf = null;
     public void init()
     {
         modules = (Map)IsmaPersister.getModulesMap();
     }

    public Map getModule()
    {
        return this.modules;
    }

    public workflow getWorkFlow(Module module)
    {
        if(module.getWorkflow() == null)
            return null;

        Iterator wfi = module.getWorkflow().iterator();
        while (wfi.hasNext())
        {
            wf = (workflow) wfi.next();
            break;
        }

        return wf;
    }

    public void loadExtent(results results, HSession ds,
                           Class cls, String sortProperty)
        throws Exception
    {
        String orderBy = "";
        if(sortProperty != null && !sortProperty.equals(""))
        {
            sortProperty = " order by " + sortProperty;
        }
        String search = "";
        search = "select distinct from obj in class " + cls.getName() + orderBy;
        List ext = ds.findDirect(search);
        results.put(cls, ext);
    }
    public void loadExtent(results results, HSession ds,
                           Class cls, String sortProperty, String where)
        throws Exception
    {
        String orderBy = "";
        String wherel = where;
        if(wherel != null && !wherel.equals(""))
        {
            wherel = " " + wherel;
        }

        if(sortProperty != null && !sortProperty.equals(""))
        {
            sortProperty = " order by " + sortProperty;
        }
        String search = "";
        search = "select distinct from obj in class " + cls.getName() + wherel + orderBy;
        List ext = ds.findDirect(search);
        results.put(cls, ext);
    }

    /**
     * Implement this method with logic that:<br>
     * 1) Performs the appropriate query based on the parameters in the request<br>
     * 2) Places the results into the result Map
     * @param request
     * @param result
     * @param ds
     * @throws Exception
     */
    public abstract Results execute();


}
