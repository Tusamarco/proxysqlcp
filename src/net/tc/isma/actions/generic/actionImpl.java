package net.tc.isma.actions.generic;

import net.tc.isma.actions.Action;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import java.io.Serializable;
import net.tc.isma.actions.Results;
import net.tc.isma.request.generic.requestImpl;

public class actionImpl
	implements Action, Serializable
{

	private String name;
	private String classname;
	private String classpkg;
	private int execorder;
	private boolean bounded;
	private Action boundedAction;
	private net.tc.isma.actions.generic.results result;
	public boolean reload = false;
        private requestImpl reqImpl = null;

	public actionImpl()
	{
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getClassname()
	{
		return classname;
	}

	public void setClassname( String classname )
	{
		this.classname = classname;
	}

	public String getClasspkg()
	{
		return classpkg;
	}

	public void setClasspkg( String classpkg )
	{
		this.classpkg = classpkg;
	}

	public int getExecorder()
	{
		return execorder;
	}

	public void setExecorder( int execorder )
	{
		this.execorder = execorder;
	}

	public boolean isBounded()
	{
		return bounded;
	}

	public void setBounded( boolean bounded )
	{
		this.bounded = bounded;
	}

	public Action getBoundedAction()
	{
		return boundedAction;
	}

	public void setBoundedAction( Action boundedAction )
	{
		this.boundedAction = boundedAction;
	}

	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof Action ) )return false;
		Action castOther = ( Action ) other;
		return new EqualsBuilder()
			.append( new Integer(this.getExecorder()), new Integer(castOther.getExecorder()) )
			.isEquals();
	}

	public int hashCode()
	{
		return new HashCodeBuilder()
			.append( new Integer(this.getExecorder()) )
			.toHashCode();
	}

	public Results getResult()
	{
		return result;
	}

	public void setResult( Results result )
	{
		this.result = ( results ) result;
	}

	public  Results execute(){return this.result;}

	public boolean reload()
	{
		return false;
	}
	public void setReload(boolean reloadIn)
	{
		reload = reloadIn;
	}

    public void setRequest( requestImpl request )
    {
        reqImpl = request;
    }
    public requestImpl getRequest()
    {
        return this.reqImpl;
    }

}
