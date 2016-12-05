package net.tc.isma.auth.security;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UserXApplication implements Serializable {

    /** identifier field */
    private String aname;

    /** identifier field */
    private String uname;
    private java.util.Set group;

    /** full constructor */
    public UserXApplication(java.lang.String aname, java.lang.String uname) {
        this.aname = aname;
        this.uname = uname;
    }

    /** default constructor */
    public UserXApplication() {
    }

    public java.lang.String getAname() {
        return this.aname;
    }

    public void setAname(java.lang.String aname) {
        this.aname = aname;
    }
    public java.lang.String getUname() {
        return this.uname;
    }

    public void setUname(java.lang.String uname) {
        this.uname = uname;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof UserXApplication) ) return false;
        UserXApplication castOther = (UserXApplication) other;
        return new EqualsBuilder()
            .append(this.aname, castOther.aname)
            .append(this.uname, castOther.uname)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(aname)
            .append(uname)
            .toHashCode();
    }
    public java.util.Set getGroup()
    {
        return group;
    }
    public void setGroup(java.util.Set group)
    {
        this.group = group;
    }

}
