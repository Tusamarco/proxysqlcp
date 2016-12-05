package net.tc.isma.workflows;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import net.tc.isma.data.hibernate.*;
import org.hibernate.Session;


/** @author Hibernate CodeGenerator */
public class stepXObj extends HybernateObject implements Serializable, Comparable {

    /** identifier field */
    private String application;

    /** identifier field */
    private long idObj;

    /** identifier field */
    private long idStep;

    /** identifier field */
    private long idWf;

    /** identifier field */
    private String module;
    private Long id;

    /** nullable persistent field */

    /** full constructor */
    public stepXObj(Long idIn,java.lang.String application, long idObj, long idStep, long idWf, java.lang.String module) {
        this.id = idIn ;
        this.application = application;
        this.idObj = idObj;
        this.idStep = idStep;
        this.idWf = idWf;
        this.module = module;

    }

    /** default constructor */
    public stepXObj() {
    }

    /** minimal constructor */
    public stepXObj(java.lang.String application, long idObj, long idStep, long idWf, java.lang.String module) {
        this.application = application;
        this.idObj = idObj;
        this.idStep = idStep;
        this.idWf = idWf;
        this.module = module;
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

    public void setModule(java.lang.String module) {
        this.module = module;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof stepXObj) ) return false;
        stepXObj castOther = (stepXObj) other;
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
    public int compareTo(Object obj)
    {
        if(this.getClass() != obj.getClass())
            throw new ClassCastException();
        Long id1 = new Long(((stepXObj)obj).getIdObj());
        Long id2 = new Long(this.getIdObj());

        return id1.compareTo(id2);
    }
    public Object getId()
    {
        return id;
    }
    public void setId(Object idin)
    {
        this.id = (Long)idin;
    }

    public Long getOrder() {
        return null;
    }

    public void setOrder(Long order) {
    }

    public Long getType() {
        return null;
    }

    public void setType(Long type) {
    }

    public void setParent(HybernateObject obj) {
    }

    public void onDelete() {
    }

    public void onDelete(Session s) {
    }

    public Long getStatus() {
        return null;
    }

    public void setStatus(Long status) {
    }

    public HybernateObject getParent() {
        return null;
    }

    public void onSave(Session s) {
    }

    public void onUpdate(Session s) {
    }

    public void setXmlPath(String xmlpath) {
    }

}
