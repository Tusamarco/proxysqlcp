package net.tc.isma.auth.security;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Action implements Serializable {

    /** identifier field */
    private String id;

    /** persistent field */
    private String active;

    /** full constructor */
    public Action(String id, java.lang.String active) {
        this.id = id;
        this.active = active;
    }

    /** default constructor */
    public Action() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public java.lang.String getActive() {
        return this.active;
    }

    public void setActive(java.lang.String active) {
        this.active = active;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Action) ) return false;
        Action castOther = (Action) other;
        return new EqualsBuilder()
            .append(this.id, castOther.id)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .toHashCode();
    }

}
