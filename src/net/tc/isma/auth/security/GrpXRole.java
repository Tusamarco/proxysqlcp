package net.tc.isma.auth.security;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class GrpXRole implements Serializable {

    /** identifier field */
    private String application;

    /** identifier field */
    private String gname;

    /** identifier field */
    private String rname;
    private Role role;

    /** full constructor */
    public GrpXRole(java.lang.String application, java.lang.String gname, java.lang.String rname) {
        this.application = application;
        this.gname = gname;
        this.rname = rname;
    }

    /** default constructor */
    public GrpXRole() {
    }

    public java.lang.String getapplication() {
        return this.application;
    }

    public void setapplication(java.lang.String application) {
        this.application = application;
    }
    public java.lang.String getGname() {
        return this.gname;
    }

    public void setGname(java.lang.String gname) {
        this.gname = gname;
    }
    public java.lang.String getRname() {
        return this.rname;
    }

    public void setRname(java.lang.String rname) {
        this.rname = rname;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof GrpXRole) ) return false;
        GrpXRole castOther = (GrpXRole) other;
        return new EqualsBuilder()
            .append(this.application, castOther.application)
            .append(this.gname, castOther.gname)
            .append(this.rname, castOther.rname)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(application)
            .append(gname)
            .append(rname)
            .toHashCode();
    }
    public Role getRole()
    {
        return role;
    }
    public void setRole(Role role)
    {
        this.role = role;
    }

}
