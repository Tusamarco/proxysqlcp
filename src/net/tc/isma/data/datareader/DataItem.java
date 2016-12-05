package net.tc.isma.data.datareader;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.io.Serializable;
import java.util.Collection;

public interface DataItem
{
	public Map getKey();

	public Set getKeyNames();

	public List getSubVaulesList( List l );

	public Data getValue( Serializable year );

	public Collection getValues();

	public Map getValuesMap();

	public Set getYears();

	public void setKey( Map key );

	public void setValues( Map values );
}
