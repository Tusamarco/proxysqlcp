package net.tc.isma.resources;

import java.util.Map;
import java.io.Serializable;
import java.util.HashMap;

public class ConfigResourceImpl
	implements ConfigResource, Serializable
{
	private Map parameter = new HashMap();
	private Object key = null;
	private Object value = null;


	public ConfigResourceImpl(Map parameterin, Object keyin, Object valuein)
	{
		setParameter(parameterin);
		setKey(keyin);
		setValue(valuein);
	}

	public Object getKey()
	{
		return key;
	}

	public Map getParameter()
	{
		return parameter;
	}

	public Object getValue()
	{
		return value;
	}

	public void setKey( Object keyin )
	{
		key = keyin;
	}

	public void setParameter( Map parameterin )
	{
		if(parameterin != null)
			parameter.putAll(parameterin);
	}

	public void setValue( Object valuein )
	{
		value = valuein;
	}
	public String getStringValue()
	{
		if(value != null)
			return value.toString();
		return null;
	}
	public String toString()
	{
		String  out = key.toString();
		String parameterstr = "";
		if(getParameter() != null)
		{
			parameterstr = getParameter().toString();
	    }

		if(value != null)
			out = out + " Attributes= "+ parameterstr + " value = " + getStringValue();

		return out;
	}
}
