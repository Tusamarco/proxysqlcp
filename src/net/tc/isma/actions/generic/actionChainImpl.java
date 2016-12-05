package net.tc.isma.actions.generic;

import java.io.*;

import org.apache.commons.lang.builder.*;
import net.tc.isma.actions.*;
import net.tc.isma.persister.*;
import net.tc.isma.views.*;

public class actionChainImpl extends persistentObjectImpl
	implements ActionChain, Serializable
{
	private Object[][] actions = null;
	private String name;
	private View view;
	private boolean active;
	private int execOrder;
	private Results results;
    private String security;

    public actionChainImpl()
	{
		actions = null;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public View getView()
	{
		return view;
	}

	public void setView( View view )
	{
		this.view = view;
	}

	public int getExecOrder()
	{
		return execOrder;
	}

	public void setExecOrder( int execOrder )
	{
		this.execOrder = execOrder;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive( boolean active )
	{
		this.active = active;
	}

	public Results getResults()
	{
		return results;
	}

	public void setResults( Results results )
	{
		this.results = results;
	}
	public void putAction(Object key, Action action)
	{
		if(actions == null)
		{
			actions = new Object[1][2];
			actions[0][0] = key;
			actions[0][1] = action;

		}
		else
		{
			Object[][] actionNew = new Object[actions.length + 1][2];
			for(int aa = 0 ; aa < actions.length ; aa++)
			{
				actionNew[aa][0] = actions[aa][0];
				actionNew[aa][1] = actions[aa][1];
			}
			actionNew[actionNew.length - 1][0] = key;
			actionNew[actionNew.length - 1][1] = action;
			actions = actionNew;
		}

	}
	public Action getAction(Object key)
	{
		for(int aa = 0 ; aa < actions.length ; aa++)
		{
			if(actions[aa][0].equals(key))
				return (Action)actions[aa][1];
		}
		return null;
	}
	public void setActions(Object[][] actionsIn)
	{
		if(actionsIn != null && actionsIn.length > 0)
		{
			for(int aa = 0 ; aa < actionsIn.length ; aa++)
			{
				this.putAction(actionsIn[aa][0], (Action)actionsIn[aa][1]);
			}
		}
	}

    public void setSecurity(String security) {
        this.security = security;
    }


    public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public boolean equals(Object other) {
		if ( !(other instanceof ActionChain) ) return false;
		ActionChain castOther = (ActionChain) other;
		return new EqualsBuilder()
			.append(this.name, castOther.getName())
			.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(name)
			.toHashCode();
	}

	public Object[][] getActions()
	{
		if(actions == null || actions.length == 0 )
			return null;

		return actions;
	}

    public String getSecurity() {
        return security;
    }
}
