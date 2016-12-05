package net.tc.isma.auth.security;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.Set;

/** @author Hibernate CodeGenerator */
public class Group implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String description;
    private java.util.Set role;
    private String application;

    /** full constructor */
    public Group(String id, String description) {
        this.id = id;
        this.description = description;
    }

    /** default constructor */
    public Group() {
    }

    /** minimal constructor */
    public Group(java.lang.String id) {
        this.id = id;
    }

    public java.lang.String getId() {
        return this.id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }
    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Group) ) return false;
        Group castOther = (Group) other;
        return new EqualsBuilder()
            .append(this.id, castOther.id)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .toHashCode();
    }
    public java.util.Set getRole()
    {
       //if(role == null)
            //loadRoles();
        return role;
    }
    public void setRole(java.util.Set role)
    {
        this.role = role;
    }
    public String getApplication()
    {
        return application;
    }
    public void setApplication(String application)
    {
        this.application = application;
    }
//    private void loadRoles()
//    {
//
//        try
//    {
//        SessionFactory dsf = FaostatPersister.getSessionFactory();
        //HibernateSessionFactory dsf = new HibernateSessionFactory("/jndi/HibernateSessionFactory_ISMA");
//        if (dsf == null)
//            throw new IsmaException(" SessionFactory is null!");
//        Session ds = dsf.openSession();
//
//        GrpXRole so = new GrpXRole();
//        so.setapplication(this.getApplication());
//        so.setGname(this.getId());
//        Set temp = new HashSet();
//
//        List l = ds.findHSQL("from obj in class net.tc.isma.auth.security.GrpXRole where obj.application ='"+ getApplication() +"' and obj.gname = '"+ getId() + "'");
       // List l = ds.find(new QueryByExample(so, new String[] {"application","gname"},  null));
//        Iterator it = l.iterator();
//
//        while(it.hasNext())
//        {
//            GrpXRole toLoad = (GrpXRole)it.next();
           // temp.add(ds.load(Role.class,toLoad.getRname()));
//        }
//        resetRole();
//        setRole(temp);
//        ds.close();
//       }
//    catch (Exception ex)
//    {
//        System.out.println("********* " + ex);
//    }
//    finally{}
//
//
//    }
    private void resetRole()
    { this.role = null;}

}
