package net.tc.isma.workflows;

import java.io.*;

import net.tc.isma.*;
import org.apache.commons.lang.builder.*;

/** @author Hibernate CodeGenerator */
public class stepXObjInput implements Serializable {

   private String application;

    /** identifier field */
    private long idObj;

    private long idStep;

    private long idWf;

    private String module;

    /**
     * super obj identifier
     */
     public Long id;
    private long idorig;

    /** default constructor */
    public stepXObjInput() {
    }
    /** Step constructor */
    public stepXObjInput(stepXObj so)throws IsmaException
    {
        if(so == null)
            throw new IsmaException(" stepXObj is null!");
        this.application = so.getApplication();
        this.idObj = so.getIdObj();
        this.idStep = so.getIdStep();
        this.idWf = so.getIdWf();
        this.module = so.getModule();
        this.id = (Long)so.getId();
//        this.role = so.getRole();
    }


    public java.lang.String getApplication() {
        return this.application;
    }

    public void setApplication(java.lang.String application) {
        this.application = application;
    }
    public long getIdObj() {
        return this.idObj;
    }

    public void setIdObj(long idObj) {
        this.idObj = idObj;
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

    public long getIdorig() {
        return idorig;
    }

    public void setModule(java.lang.String module) {
        this.module = module;
    }

    public void setIdorig(long idorig) {
        this.idorig = idorig;
    }


    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof stepXObjInput) ) return false;
        stepXObjInput castOther = (stepXObjInput) other;
        return new EqualsBuilder()
            .append(this.application, castOther.application)
            .append(this.idObj, castOther.idObj)
            .append(this.idStep, castOther.idStep)
            .append(this.idWf, castOther.idWf)
            .append(this.module, castOther.module)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(application)
            .append(idObj)
            .append(idStep)
            .append(idWf)
            .append(module)
            .toHashCode();
    }

}
