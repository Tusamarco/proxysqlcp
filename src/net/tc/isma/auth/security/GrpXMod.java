package net.tc.isma.auth.security;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class GrpXMod implements Serializable {

    /** identifier field */
    private String application;

    /** identifier field */
    private String groupn;

    /** identifier field */
    private String module;
  private String applicationp;
  private String modulep;
  private String groupnp;


    /** default constructor */
    public GrpXMod() {
    }

    public java.lang.String getApplication() {
        return this.application;
    }

    public void setApplication(java.lang.String application) {
        this.application = application;
    }
    public java.lang.String getGroupn() {
        return this.groupn;
    }

    public void setGroupn(java.lang.String groupn) {
        this.groupn = groupn;
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
        if ( !(other instanceof GrpXMod) ) return false;
        GrpXMod castOther = (GrpXMod) other;
        return new EqualsBuilder()
            .append(this.application, castOther.application)
            .append(this.groupn, castOther.groupn)
            .append(this.module, castOther.module)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(application)
            .append(groupn)
            .append(module)
            .toHashCode();
    }
  public String getApplicationp() {
    return applicationp;
  }
  public void setApplicationp(String applicationp) {
    this.applicationp = applicationp;
  }
  public String getModulep() {
    return modulep;
  }
  public void setModulep(String modulep) {
    this.modulep = modulep;
  }
  public String getGroupnp() {
    return groupnp;
  }
  public void setGroupnp(String groupnp) {
    this.groupnp = groupnp;
  }

}
