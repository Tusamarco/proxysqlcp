package net.tc.isma.workflows;


import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.*;
//import net.tc.isma.data.SessionFactory;

//import net.tc.isma.data.Session;
import net.tc.isma.IsmaException;
import net.tc.isma.data.*;
import net.tc.isma.auth.security.UserBase;

import net.tc.isma.auth.security.Role;
import net.tc.isma.auth.security.Action;
import net.tc.isma.auth.security.Permission;
import net.tc.isma.lang.LanguageSelector;
import org.hibernate.*;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.data.objects.Module;
import net.tc.isma.data.hibernate.HSessionFactory;
import net.tc.isma.data.hibernate.*;
import net.tc.isma.auth.security.permissionBean;
import net.tc.isma.auth.security.permissionBase;
import net.tc.isma.auth.security.RoleV;
import org.hibernate.criterion.Restrictions;
/** @author Hibernate CodeGenerator */

public class stepImpl extends permissionBase implements Serializable,Step,Comparable,Permission {

    /** identifier field */
    private String application;

    /** identifier field */
    private long idStep;

    /** identifier field */
    private long idWf;

    /** identifier field */
    private String module;

    /** nullable persistent field */
    private String nameEn;

    /** nullable persistent field */
    private String nameFr;

    /** nullable persistent field */
    private String nameEs;

    /** nullable persistent field */
    private String nameAr;

    /** nullable persistent field */
    private String nameZh;
    private java.util.SortedMap stepxobj;
    private java.util.Set roles;

    /** full constructor */
    public stepImpl(java.lang.String application, long idStep, long idWf, java.lang.String module, java.lang.String nameEn, java.lang.String nameFr, java.lang.String nameEs, java.lang.String nameAr, java.lang.String nameZh) {
        this.application = application;
        this.idStep = idStep;
        this.idWf = idWf;
        this.module = module;
        this.nameEn = nameEn;
        this.nameFr = nameFr;
        this.nameEs = nameEs;
        this.nameAr = nameAr;
        this.nameZh = nameZh;
    }

    /** default constructor */
    public stepImpl() {
    }

    /** minimal constructor */
    public stepImpl(java.lang.String application, long idStep, long idWf, java.lang.String module) {
        this.application = application;
        this.idStep = idStep;
        this.idWf = idWf;
        this.module = module;
    }

    public java.lang.String getApplication() {
        return this.application;
    }
    public java.lang.String getApplicationName() {
        return this.application;
    }

    public void setApplication(java.lang.String application) {
        this.application = application;
    }
    public long getIdStep() {
        return this.idStep;
    }

    public void setIdStep(long idStep) {
        this.idStep = idStep;
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
    public java.lang.String getModuleName() {
        return this.module;
    }

    public void setModule(java.lang.String module) {
        this.module = module;
    }
    public java.lang.String getNameEn() {
        return this.nameEn;
    }
    public String getName(LanguageSelector lang)
    {
        return (String) lang.getMultilingualProperty(this, "name");
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

    public boolean equals(Object other) {
        if ( !(other instanceof stepImpl) ) return false;
        stepImpl castOther = (stepImpl) other;
        return new EqualsBuilder()
            .append(this.application, castOther.application)
            .append(this.idStep, castOther.idStep)
            .append(this.idWf, castOther.idWf)
            .append(this.module, castOther.module)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(application)
            .append(idStep)
            .append(idWf)
            .append(module)
            .toHashCode();
    }
    public java.util.SortedMap getStepxobj()
    {
        try
        {
            if (stepxobj == null)
            {
                loadStepxobj();
            }
        }
        catch (IsmaException ex)
        {
        }
        return stepxobj;
    }
    public java.util.Map getStepxobj(Long id)
    {
        HashMap m = null;
        if(stepxobj == null)
            try {
                loadStepxobj();
            } catch (IsmaException ex) {
                ex.printStackTrace();
            }
        if(!stepxobj.containsKey(id))
            return m;
        m = new HashMap();
        m.put(id, stepxobj.get(id));
        return m;
    }

    public void setStepxobj(java.util.SortedMap stepxobj)
    {
        this.stepxobj = stepxobj;
    }
    public int compareTo(Object obj)
    {
        if (this.getClass() != obj.getClass())
            throw new ClassCastException();
        Long  val1 = new Long(  ((stepImpl)obj).getIdStep());
        Long  val2 = new Long(this.getIdStep());

        return val2.compareTo(val1);
    }

    private void loadStepxobj()throws IsmaException
    {
        try
        {
            HSession ds = IsmaPersister.getSessionFactory().openSession();
            Transaction tr = ds.beginTransaction();

            stepXObj so = new stepXObj();
            so.setIdWf(this.getIdWf());
            so.setIdStep(this.getIdStep());
            so.setApplication(this.getApplicationName());
            so.setModule(this.getModuleName());
            SortedMap temp = new TreeMap();
            List l = ds.createCriteria(stepXObj.class)
                     .add(Restrictions.eq("application",this.getApplicationName()))
                     .add(Restrictions.eq("module",this.getModuleName()))
                     .add(Restrictions.eq("idWf",new Long(this.getIdWf())))
                     .add(Restrictions.eq("idStep",new Long(this.getIdStep())))
                     .list();

            IsmaPersister.getSessionFactory().closeSession(ds);


            Iterator it = l.iterator();

            while(it.hasNext())
            {
                stepXObj toLoad = (stepXObj)it.next();
                temp.put(new Long(toLoad.getIdObj()),toLoad);
            }
            this.setStepxobj(temp);
        }
        catch (Exception ex)
        {

        }
//            printMapList( (SortedMap)this.getStepxobj());
    }
    private void printMapList(SortedMap map)
    {
        Iterator it = map.keySet().iterator();
        while(it.hasNext())
        {
            Long aa = (Long)it.next();
            System.out.println("********* " + aa);
        }
    }
    public void resetstepXObj()
    {
        this.stepxobj = null;
    }
    public java.util.Set getRoles()
    {
        if(roles != null)
            return roles;

        try
        {
            HSession ds = IsmaPersister.getSessionFactory().openSession();
            Transaction tr = ds.beginTransaction();

            List l = ds.createCriteria(RoleV.class)
                     .add(Restrictions.eq("application",this.getApplicationName()))
                     .add(Restrictions.eq("module",this.getModuleName()))
                     .add(Restrictions.eq("idWf",new Long(this.getIdWf())))
                     .add(Restrictions.eq("idStep",new Long(this.getIdStep())))
                     .list();

            IsmaPersister.getSessionFactory().closeSession(ds);
            if(l == null || l.size() == 0 )
                return null;

            Iterator ir = l.iterator();
            Set s = new HashSet(0);
            while(ir.hasNext())
            {
                RoleV rt = (RoleV) ir.next();
                Role r = (Role) rt;

                s.add(r);
            }
            if(s != null && s.size() > 0)
                this.setRoles(s);

        }
        catch (Exception ex)
        {
            IsmaPersister.getLogSystem().error(this,ex);
            return null;

        }


        return roles;
    }
    public void setRoles(java.util.Set roles)
    {
        this.roles = roles;
    }


    /**
     * section implementing the Permission interface
     */
    public boolean canAdd(UserBase user, Object modIn)
    {
        if(modIn instanceof Module)
        {}
        else
            return false;

        return getPermissionByname(user, modIn, "add");
    }

    private boolean getPermissionByname(UserBase user, Object modIn, String action) {
        Module mod = (Module)modIn;
        Set userRoles = user.getRoles(mod);
        if(getRoles() == null)
                return false;

        Iterator it = getRoles().iterator();
        while(it.hasNext())
        {
            Role r = (Role)it.next();
            String roleId = r.getId();

            Iterator itUr = userRoles.iterator();

            while(itUr.hasNext())
            {
                Role ur = (Role)itUr.next();

                if (ur.getId().equals(roleId))
                {
                    Iterator ait = r.getAction().iterator();
                    while (ait.hasNext()) {

                        Action ac = (Action) ait.next();
                        if (ac.getId().equals(action))
                            return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean canSecurity(UserBase user, Object modIn)
    {
        if(modIn instanceof Module)
        {}
        else
            return false;

        return getPermissionByname(user, modIn, "security");

    }

    public boolean canDelete(UserBase user, Object modIn)
    {
        if(modIn instanceof Module)
        {}
        else
            return false;

        return getPermissionByname(user, modIn, "delete");

    }
    public boolean canEdit(UserBase user, Object modIn)
    {
        if(modIn instanceof Module)
        {}
        else
            return false;

        return getPermissionByname(user, modIn, "edit");

    }

    public boolean canRead(UserBase user, Object modIn)
    {
        if(modIn instanceof Module)
        {}
        else
            return false;

        return getPermissionByname(user, modIn, "read");

    }

    public boolean canPublish(UserBase user, Object modIn)
    {
        if(modIn instanceof Module)
        {}
        else
            return false;

        return getPermissionByname(user, modIn, "publish");
     }
     public boolean canUnpublish(UserBase user, Object modIn)
    {
        if(modIn instanceof Module)
        {}
        else
            return false;

        return getPermissionByname(user, modIn, "unpublish");

      }
      public boolean canPromote(UserBase user, Object modIn)
      {
        if(modIn instanceof Module)
        {}
        else
            return false;

        return getPermissionByname(user, modIn, "promote");

       }
       public boolean canDemote(UserBase user, Object modIn)
       {
           if (modIn instanceof Module) {} else
               return false;

           return getPermissionByname(user, modIn, "demote");

        }

//    public boolean canAdd( UserBase user , Object mod )
//    {
//        return false ;
//    }
//
//    public boolean canDelete( UserBase user , Object mod )
//    {
//        return false ;
//    }
//
//    public boolean canEdit( UserBase user , Object mod )
//    {
//        return false ;
//    }
//
//    public boolean canRead( UserBase user , Object mod )
//    {
//        return false ;
//    }
//
//    public boolean canPublish( UserBase user , Object mod )
//    {
//        return false ;
//    }
//
//    public boolean canUnpublish( UserBase user , Object mod )
//    {
//        return false ;
//    }
//
//    public boolean canPromote( UserBase user , Object mod )
//    {
//        return false ;
//    }
//
//    public boolean canDemote( UserBase user , Object mod )
//    {
//        return false ;
//    }
//
//    public boolean canSecurity( UserBase user , Object mod )
//    {
//        return false ;
//    }


}
