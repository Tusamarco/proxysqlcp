package net.tc.isma.workflows;

import java.io.Serializable;
import java.util.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import net.tc.isma.*;
import net.tc.isma.lang.LanguageSelector;
import org.hibernate.SessionFactory;
import net.tc.isma.persister.IsmaPersister;
import org.hibernate.Session;
import net.tc.isma.data.hibernate.*;
import org.hibernate.Transaction;

//import net.tc.isma.utils.LanguageSelector;
//import net.tc.isma.data.SessionFactory;
//import net.tc.isma.data.Session;
//import net.tc.isma.data.Exception;

/** @author Hibernate CodeGenerator */
public class workflow implements Serializable {

    /** identifier field */
    private String application;

    /** identifier field */
    private long idWf;

    /** identifier field */
    private String module;

    /** persistent field */
    private String nameEn;

    /** nullable persistent field */
    private String nameFr;

    /** nullable persistent field */
    private String nameEs;

    /** nullable persistent field */
    private String nameAr;

    /** nullable persistent field */
    private String nameZh;
    private java.util.Set steps;

    /** full constructor */
    public workflow(java.lang.String application, long idWf, java.lang.String module, java.lang.String nameEn, java.lang.String nameFr, java.lang.String nameEs, java.lang.String nameAr, java.lang.String nameZh) {
        this.application = application;
        this.idWf = idWf;
        this.module = module;
        this.nameEn = nameEn;
        this.nameFr = nameFr;
        this.nameEs = nameEs;
        this.nameAr = nameAr;
        this.nameZh = nameZh;
    }

    /** default constructor */
    public workflow() {
    }

    /** minimal constructor */
    public workflow(java.lang.String application, long idWf, java.lang.String module, java.lang.String nameEn) {
        this.application = application;
        this.idWf = idWf;
        this.module = module;
        this.nameEn = nameEn;
    }

    public java.lang.String getApplication() {
        return this.application;
    }

    public void setApplication(java.lang.String application) {
        this.application = application;
    }
    public long getIdWf() {
        return this.idWf;
    }

    public void setIdWf(long idWf) {
        this.idWf = idWf;
    }
    public java.lang.String getModule() {
        return this.module;
    }

    public void setModule(java.lang.String module) {
        this.module = module;
    }
    public java.lang.String getNameEn() {
        return this.nameEn;
    }

    public void setNameEn(java.lang.String nameEn) {
        this.nameEn = nameEn;
    }
    public java.lang.String getNameFr() {
        return this.nameFr;
    }

    public void setNameFr(java.lang.String nameFr) {
        this.nameFr = nameFr;
    }
    public java.lang.String getNameEs() {
        return this.nameEs;
    }

    public void setNameEs(java.lang.String nameEs) {
        this.nameEs = nameEs;
    }
    public java.lang.String getNameAr() {
        return this.nameAr;
    }

    public void setNameAr(java.lang.String nameAr) {
        this.nameAr = nameAr;
    }
    public java.lang.String getNameZh() {
        return this.nameZh;
    }

    public void setNameZh(java.lang.String nameZh) {
        this.nameZh = nameZh;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Step getStep(Long id)
    {
        Step step = null;
        try
        {
            HashSet steps = new HashSet(this.getSteps());
            Iterator it = steps.iterator();
            while (it.hasNext())
            {
                Step temp = (Step) it.next();
                if (temp.getIdStep() == id.longValue())
                {
                    step = temp;
                    break;
                }
            }
            return step;
        }
        catch (IsmaException ex)
        {
            return null;
        }
    }
    public boolean equals(Object other) {
        if ( !(other instanceof workflow) ) return false;
        workflow castOther = (workflow) other;
        return new EqualsBuilder()
            .append(this.application, castOther.application)
            .append(this.idWf, castOther.idWf)
            .append(this.module, castOther.module)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(application)
            .append(idWf)
            .append(module)
            .toHashCode();
    }
    public java.util.Set getSteps()
    {
        return steps;
    }
    public void setSteps(java.util.Set steps)
    {
        this.steps = steps;
    }
    public String getName(LanguageSelector lang)
    {
        return (String) lang.getMultilingualProperty(this, "name");
    }
    public stepXObj movePrevious(Step step,Long objId)
    {
        try
        {
            Step previous = this.getPrevious(step);
            if(previous != null)
            {
                HashMap mt = new HashMap(step.getStepxobj(objId));
                stepXObj tochange = (stepXObj) mt.get(objId);
//                stepXObjInput so = new stepXObjInput(tochange);
                tochange.setIdStep(previous.getIdStep());

                HSession session = IsmaPersister.getSessionFactory().openSession();
                Transaction tr = session.beginTransaction();

                try
                {
                    session.update(tochange);
                    session.flush();
                    session.connection().commit();
                    step.resetstepXObj();
                    previous.resetstepXObj();
                    IsmaPersister.getSessionFactory().closeSession(session);
//                    dsf = null;
                }catch(Exception de)
                {
                    session.connection().rollback();
                    session.close();
//                    dsf = null;
                    IsmaPersister.getLogSystem().info(" error " + this.getClass().getName() + ": rollBack executed "+ tochange.getIdObj()+ " not modified");
                    return tochange;
                }
                IsmaPersister.getSessionFactory().closeSession(session);
                return tochange;
            }
        }
        catch (Exception ex)
        {
            IsmaPersister.getLogSystem().info(" error " + this.getClass().getName() + ": "+ ex );
        }
        return null;
    }
    public stepXObj moveNext(Step step,Long objId)
    {
        try
        {
            Step next = this.getNext(step);
            if(next != null)
            {
                HashMap mt = new HashMap(step.getStepxobj(objId));
                stepXObj tochange = (stepXObj) mt.get(objId);
//                stepXObjInput so = new stepXObjInput(todelete);
                tochange.setIdStep(next.getIdStep());
//                HibernateDatastoreSessionFactory dsf = FaostatPersister.getSessionFactory();
//                if(dsf == null)
//                    throw new IsmaException(" SessionFactory is null!");

                HSession session = IsmaPersister.getSessionFactory().openSession();
                Transaction tr = session.beginTransaction();


                try
                {
                    session.update(tochange);
 //                   session.delete(todelete);
                    session.flush();
                    tr.commit();
                    step.resetstepXObj();
                    next.resetstepXObj();
                    IsmaPersister.getSessionFactory().closeSession(session);
//                    dsf = null;
                }catch(Exception de)
                {
                    session.connection().rollback();
                    IsmaPersister.getSessionFactory().closeSession(session);
//                    dsf = null;
                    IsmaPersister.getLogSystem().error(" error " + this.getClass().getName() + ": rollBack executed "+ tochange.getIdObj()+ " not modified");
                    return tochange ;
                }
                return tochange;
            }
        }
        catch (Exception ex)
        {
            IsmaPersister.getLogSystem().error(" error " + this.getClass().getName() + ": "+ ex );
        }
        return null;
    }
    public void movePreviousSet(Step step,Set objIds)
    {
        try
        {
            Long objId = null;
            Step previous = this.getPrevious(step);

            Iterator it = objIds.iterator();
//            HibernateDatastoreSessionFactory dsf = FaostatPersister.getSessionFactory();
//            if (dsf == null)
//                throw new IsmaException(" SessionFactory is null!");

            if(previous != null)
            {
                stepXObj tochange = null;
                HSession session = null;
                try
                {


                    session = IsmaPersister.getSessionFactory().openSession();
                    Transaction tr = session.beginTransaction();

                    while(it.hasNext())
                    {
                        objId = (Long) it.next();
                        HashMap mt = new HashMap(step.getStepxobj(objId));
                        tochange = (stepXObj) mt.get(objId);
                        tochange.setIdStep(previous.getIdStep());
                        session.update(tochange);
                    }
                    session.flush();
                    tr.commit();
                    step.resetstepXObj();
                    previous.resetstepXObj();
                    IsmaPersister.getSessionFactory().closeSession(session);
//                    dsf = null;
                }
                catch (Exception de)
                {
                    session.connection().rollback();
                    IsmaPersister.getSessionFactory().closeSession(session);
//                    dsf = null;
                    IsmaPersister.getLogSystem().info(" error " + this.getClass().getName() + ": rollBack executed " + tochange.getIdObj() + " not modified");
                }
                session.close();
            }
        }
        catch (Exception ex)
        {
            IsmaPersister.getLogSystem().info(" error " + this.getClass().getName() + ": "+ ex );
        }
    }

    public void moveNextSet(Step step,Set objIds)
    {
        try
        {
            Long objId = null;
            Step next = this.getNext(step);
            Iterator it = objIds.iterator();
            if(next != null)
            {
                HSession session = null;
                stepXObj tochange = null;
                try
                {
//                    HibernateDatastoreSessionFactory dsf = FaostatPersister.getSessionFactory();
//                    if (dsf == null)
//                        throw new IsmaException(" SessionFactory is null!");

                    session = IsmaPersister.getSessionFactory().openSession();
                    Transaction tr = session.beginTransaction();

                    while(it.hasNext())
                    {
                        objId =(Long) it.next();
                        HashMap mt = new HashMap(step.getStepxobj(objId));
                        tochange = (stepXObj) mt.get(objId);

                        tochange.setIdStep(next.getIdStep());
                        session.update(tochange);
                    }
                    session.flush();
                    session.connection().commit();
                    step.resetstepXObj();
                    next.resetstepXObj();
                    IsmaPersister.getSessionFactory().closeSession(session);

                }catch(Exception de)
                {
                    session.connection().rollback();
                    IsmaPersister.getSessionFactory().closeSession(session);
//                    dsf = null;
                    IsmaPersister.getLogSystem().info(" error " + this.getClass().getName() + ": rollBack executed "+ tochange.getIdObj()+ " not modified");
                }
            }
        }
        catch (Exception ex)
        {
            IsmaPersister.getLogSystem().info(" error " + this.getClass().getName() + ": "+ ex );
        }

    }

    public Step getNext(Step step)
    {
        Step stepReturn = null;
        try
        {
            long id = step.getIdStep();
            stepReturn = null;
            Iterator its = steps.iterator();
            Step[] arS = new Step[steps.size()];
            int ii = 0;
            while (its.hasNext())
            {
                arS[ii] = (Step) its.next();
                ii++;
            }

            long inext = 0;
            for (int i = 0; i < arS.length; i++)
            {
                if (arS[i].getIdStep() == id)
                {
                    if (i < (arS.length - 1))
                    {
                        stepReturn = (Step)arS[i + 1];
                    }
                    break;
                }
            }
        }
        catch (IsmaException ex)
        {
            IsmaPersister.getLogSystem().info(" error " + this.getClass().getName() + ": "+ ex );
        }


        return stepReturn;
    }

    public Step getPrevious(Step step)
    {
        Step stepReturn = null;
        try
        {
            long id = step.getIdStep();
            stepReturn = null;
            Iterator its = steps.iterator();
            Step[] arS = new Step[steps.size()];
            int ii = 0;
            while (its.hasNext())
            {
                arS[ii] = (Step) its.next();
                ii++;
            }

            long inext = 0;
            for (int i = 0; i < arS.length; i++)
            {
                if(arS[i].getIdStep() == id)
                {
                    if (i > 0)
                    {
                        stepReturn = (Step) arS[i - 1];
                    }
                    break;

                }

            }
        }
        catch (IsmaException ex)
        {
            IsmaPersister.getLogSystem().info(" error " + this.getClass().getName() + ": " + ex);
        }

        return stepReturn;

    }


}
