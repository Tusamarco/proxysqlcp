package net.tc.isma.auth.security;


import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UserXGroup implements Serializable {

    /** persistent field */
    private String uname;

    /** persistent field */
    private String gname;

    /** persistent field */
    private String application;

    /** full constructor */
    public UserXGroup(java.lang.String uname, java.lang.String gname, java.lang.String application) {
        this.uname = uname;
        this.gname = gname;
        this.application = application;
    }

    /** default constructor */
    public UserXGroup() {
    }

    public java.lang.String getUname() {
        return this.uname;
    }

    public void setUname(java.lang.String uname) {
        this.uname = uname;
    }
    public java.lang.String getGname() {
        return this.gname;
    }

    public void setGname(java.lang.String gname) {
        this.gname = gname;
    }
    public java.lang.String getApplication() {
        return this.application;
    }

    public void setApplication(java.lang.String application) {
        this.application = application;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
