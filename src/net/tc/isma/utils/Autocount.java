package net.tc.isma.utils;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Autocount implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String val;

    /** full constructor */
    public Autocount(java.lang.String id, java.lang.String val) {
        this.id = id;
        this.val = val;
    }

    /** default constructor */
    public Autocount() {
    }

    /** minimal constructor */
    public Autocount(java.lang.String id) {
        this.id = id;
    }

    public java.lang.String getId() {
        return this.id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }
    public java.lang.String getVal() {
        return this.val;
    }

    public long getValLong() {

        return Long.parseLong(this.val.trim());
    }

    public void setVal(java.lang.String val) {
        this.val = val;
    }
    public void setValLong(long valLong) {
        this.val = Long.toString(valLong);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Autocount) ) return false;
        Autocount castOther = (Autocount) other;
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
