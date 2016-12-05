package net.tc.isma.data.datareader.tserial;

import java.util.Set;
import java.util.Collection;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import net.tc.isma.data.datareader.DataItem;
import net.tc.isma.data.datareader.Data;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class tserialItem
	implements DataItem, Serializable
{
	/* in the specific case of faostat and tserial the key
	is composed by the following objects = AREA ; ITEM ; ELEMENT
	each one composed by a tserial data where values is the code and description is the text (Map with all languages)
   */
	private java.util.Map key;
	private java.util.Map values;
	public tserialItem()
	{
	}

	public java.util.Map getKey()
	{
		return key;
	}

	public void setKey( java.util.Map key )
	{
		this.key = key;
	}

	public java.util.Map getValuesMap()
	{
		return values;
	}

	public void setValues( java.util.Map values )
	{
		this.values = values;
	}

	public Set getKeyNames()
	{
		if(key == null || key.size()==0)
			return null;

		return key.keySet();
	}
	public Collection getValues()
	{
		if(values == null || values.size()==0)
			return null;

		return values.values();
	}
	public Set getYears()
	{
		if(values == null || values.size()==0)
			return null;

			return values.keySet();
	}
	public Data getValue(Serializable year)
	{
		if(
			values == null
			|| values.size()==0
			|| year == null)
			return null;

		return (tserialData)values.get(year);
	}
	public List getSubVaulesList(List l)
	{
		if(
		    values == null
			|| values.size() == 0
			|| l == null
			|| l.size() ==0)
			return null;

		List returnlist = new ArrayList();
		for(int i = 0 ; i < l.size() ; i++)
		{
			Object o = l.get(i);
			if(o != null)
				returnlist.add(values.get(o));
		}
		return returnlist;

	}
	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof DataItem ) )return false;
		DataItem castOther = ( DataItem ) other;
		return new EqualsBuilder()
			.append( this.getKey(), castOther.getKey())
			.isEquals();
	}

	public int hashCode()
	{
		return new HashCodeBuilder()
			.append( this.getKey())
			.toHashCode();
	}

}
