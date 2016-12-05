package net.tc.isma.data.objects;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;
import net.tc.isma.persister.*;


/** @author Hibernate CodeGenerator */
public class Module implements Serializable {

    /** identifier field */
    private String application;

    /** identifier field */
    private String moduleName;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String active;

    /** nullable persistent field */
    private String datastoreClass;

    /** nullable persistent field */
    private String webRoot;
    private String tgroup;
    private Map pages;
    private String packageRoot;
    private java.util.Set workflow;
    private java.util.Set group;
    private java.util.Map attribs;
    private String moduleNamep;

    /** full constructor */
    public Module(java.lang.String application, java.lang.String moduleName, java.lang.String description, java.lang.String active, java.lang.String datastoreClass, java.lang.String webRoot) {
        this.application = application;
        this.moduleName = moduleName;
        this.description = description;
        this.active = active;
        this.datastoreClass = datastoreClass;
        this.webRoot = webRoot;
    }

    /** default constructor */
    public Module() {
    }

    /** minimal constructor */
    public Module(java.lang.String application, java.lang.String moduleName) {
        this.application = application;
        this.moduleName = moduleName;
    }

    public java.lang.String getApplication() {
        return this.application;
    }

    public void setApplication(java.lang.String application) {
        this.application = application;
    }
    public java.lang.String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(java.lang.String moduleName) {
        this.moduleName = moduleName;
    }
    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }
    public java.lang.String getActive() {
        return this.active;
    }

    public void setActive(java.lang.String active) {
        this.active = active;
    }
    public java.lang.String getDatastoreClass() {
        return this.datastoreClass;
    }

    public void setDatastoreClass(java.lang.String datastoreClass) {
        this.datastoreClass = datastoreClass;
    }
    public java.lang.String getWebRoot() {
        return this.webRoot;
    }

    public void setWebRoot(java.lang.String webRoot) {
        this.webRoot = webRoot;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Module) ) return false;
        Module castOther = (Module) other;
        return new EqualsBuilder()
            .append(this.application, castOther.application)
            .append(this.moduleName, castOther.moduleName)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(application)
            .append(moduleName)
            .toHashCode();
    }

    public String getTgroup()
    {
        return tgroup;
    }

    public void setTgroup(String tgroup)
    {
        this.tgroup = tgroup;
    }

    Map getPages()
    {
        return pages;
    }


    public String getPackageRoot()
    {
        return packageRoot;
    }

    public void setPackageRoot(String packageRoot)
    {
        this.packageRoot = packageRoot;
    }

    public java.util.Set getWorkflow()
    {
        return workflow;
    }

    public void setWorkflow(java.util.Set workflow)
    {
        this.workflow = workflow;
    }

    public java.util.Set getGroup()
    {
        return group;
    }

    public void setGroup(java.util.Set group)
    {
        this.group = group;
    }

    public java.util.Map getAttribs()
    {
        return attribs;
    }

    public String getModuleNamep() {
        return moduleNamep;
    }

    public void setAttribs(java.util.Map attribs)
    {
        this.attribs = attribs;
    }

    public void setModuleNamep(String moduleNamep) {
        this.moduleNamep = moduleNamep;
    }

    public String resolveClassName(String shortName)
    {
        String cls;

        if ( shortName.startsWith(".") )
        {
//            actionCls = packageRoot + ".modules." + moduleName.toLowerCase() + actionName + "Action";
            cls = getPackageRoot() + shortName;
         }
        else
        {
            cls = shortName;
        }

        return cls;
    }

    public Class resolveClass(String shortName)
    {
        try
        {
            return Class.forName(resolveClassName(shortName));
        }
        catch (ClassNotFoundException ex)
        {
            IsmaPersister.getLogDataAccess().error(ex);
            return null;
        }
    }

    public Class getAttribClass(String attribName)
    {
        String val = (String) attribs.get(attribName);
        if ( attribName == null  )
            return null;
        else
            return resolveClass(val);
    }
}
