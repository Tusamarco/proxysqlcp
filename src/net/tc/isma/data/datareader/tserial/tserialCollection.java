package net.tc.isma.data.datareader.tserial;

import java.io.Serializable;
import net.tc.isma.data.datareader.DataItem;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import net.tc.isma.data.datareader.DataItemsCollection;

import net.tc.isma.persister.persistentObjectImpl;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class tserialCollection extends persistentObjectImpl
	implements DataItemsCollection, Serializable
{
	private java.util.Map key;
	private java.util.List records;
	private Double recordsCount;
	public tserialCollection()
	{
	}

	public Object getKey()
	{
		return (Object)key;
	}

	public void setKey( java.util.Map key )
	{
		this.key = key;
	}

	public java.util.List getRecords()
	{
		return records;
	}

	public void setRecords( java.util.List records )
	{
		this.records = records;
	}

	public Double getRecordsCount()
	{
		return recordsCount;
	}
	public void addRecord(Object record)
	{
		if(record instanceof DataItem)
		{
			if(!records.contains(record))
				addReplaceRecord(record);
		}
	}
	public void addReplaceRecord(Object record)
	{
		if(record instanceof DataItem)
		{
			records.add(record);
		}
	}
	public void removeRecord(int pos)
	{
		if(records != null && records.size() > pos)
		{
			records.remove(pos);
		}
	}
	public void clearCollection()
	{
		if(records != null)
			records.clear();
	}

	public void reFresh()
	{
		/** @todo REFRESH da rifare */
	}
	/**
  * This is the key method for the sort by that will be used also for ROTATE the axis
  * KEEP in mind that the item at
  * position 0 is the X axis
  * position 1 is the Y axis
  * position 2 is  the Z axis
  * position 3 is the T dimention
  */
	public Object sortBy(List l)
	{
		if( records == null || records.size() == 0 )
			return null;

		/** @todo NOT YET IMPLEMENTED TO DO */
		return null;

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
