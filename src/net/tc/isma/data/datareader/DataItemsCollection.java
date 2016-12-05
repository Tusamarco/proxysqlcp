package net.tc.isma.data.datareader;

import java.util.List;
import java.util.Map;

public interface DataItemsCollection
{
	public void addRecord( Object record );

	public void addReplaceRecord( Object record );

	public void clearCollection();

	public Object getKey();

	public List getRecords();

	public Double getRecordsCount();

	public void reFresh();

	public void removeRecord( int pos );

	public void setKey( Map key );

	public void setRecords( List records );

	public Object sortBy( List l );
}
