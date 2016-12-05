package net.tc.isma.utils;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Autocountx implements Serializable {

    /** identifier field */
    private String application;

    /** identifier field */
    private String id;

    /** identifier field */
    private String module;

    /** persistent field */
    private String val;

    /** full constructor */
    public Autocountx(java.lang.String application, java.lang.String id, java.lang.String module, java.lang.String val) {
        this.application = application;
        this.id = id;
        this.module = module;
        this.val = val;
    }

    /** default constructor */
    public Autocountx() {
    }

    public java.lang.String getApplication() {
        return this.application;
    }

    public void setApplication(java.lang.String application) {
        this.application = application;
    }
    public java.lang.String getId() {
        return this.id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }
    public java.lang.String getModule() {
        return this.module;
    }

    public void setModule(java.lang.String module) {
        this.module = module;
    }
    public java.lang.String getVal() {
        return this.val;
    }

    public void setVal(java.lang.String val) {
        this.val = val;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Autocountx) ) return false;
        Autocountx castOther = (Autocountx) other;
        return new EqualsBuilder()
            .append(this.application, castOther.application)
            .append(this.id, castOther.id)
            .append(this.module, castOther.module)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(application)
            .append(id)
            .append(module)
            .toHashCode();
    }
    public long getValLong()
    {
        return new Long(this.getVal()).longValue();
    }

    public void setValLong(long id)
    {
        this.setVal(new Long(id).toString());
    }

}
