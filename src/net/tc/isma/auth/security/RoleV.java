package net.tc.isma.auth.security;

import java.io.*;

import org.apache.commons.lang.builder.*;

/** @author Hibernate CodeGenerator */
public class RoleV extends Role implements Serializable {

    /** identifier field */
    private String id;

    /** nullable persistent field */
    private String application;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private short recordstatus;
    private java.util.Set action;
    private String module;
    private Long idWf;
    private Long idStep;

    /** full constructor */
    public RoleV(String id, java.lang.String application, java.lang.String description, short recordstatus) {
        this.id = id;
        this.application = application;
        this.description = description;
        this.recordstatus = recordstatus;
    }

    /** default constructor */
    public RoleV() {
    }

    /** minimal constructor */
    public RoleV(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public java.lang.String getApplication() {
        return this.application;
    }

    public void setApplication(java.lang.String application) {
        this.application = application;
    }
    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    public short getRecordstatus() {
        return this.recordstatus;
    }

    public void setRecordstatus(short recordstatus) {
        this.recordstatus = recordstatus;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof RoleV) ) return false;
        RoleV castOther = (RoleV) other;
        return new EqualsBuilder()
            .append(this.id, castOther.id)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(id)
            .toHashCode();
    }
    public java.util.Set getAction()
    {
        return action;
    }

    public String getModule() {
        return module;
    }

    public Long getIdWf() {
        return idWf;
    }

    public Long getIdStep() {
        return idStep;
    }

    public void setAction(java.util.Set action)
    {
        this.action = action;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setIdWf(Long idWf) {
        this.idWf = idWf;
    }

    public void setIdStep(Long idStep) {
        this.idStep = idStep;
    }

}
