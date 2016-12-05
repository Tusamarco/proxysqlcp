package net.tc.isma.data.datareader.tserial;

import net.tc.isma.data.datareader.Data;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class tserialData
	implements Data, Serializable
{
	private Object value;
	private Object description;
	public tserialData()
	{
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue( Object value )
	{
		this.value = value;
	}

	public Object getDescription()
	{
		return description;
	}

	public void setDescription( Object description )
	{
		this.description = description;
	}

	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof Data ) )return false;
		Data castOther = ( Data ) other;
		return new EqualsBuilder()
			.append( this, castOther)
			.isEquals();
	}

	public int hashCode()
	{
		return new HashCodeBuilder()
			.append( this.getValue())
			.toHashCode();
	}

}
