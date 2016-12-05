package net.tc.isma.data.datareader;

import java.io.Serializable;

public interface Data
{
	public Object getDescription();

	public Object getValue();

	public void setDescription( Object description );

	public void setValue( Object value );
}
