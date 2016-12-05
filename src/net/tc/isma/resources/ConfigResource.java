package net.tc.isma.resources;

import java.util.Map;

public interface ConfigResource
{

	public static Map parameter = null;
	public static Object key = null;
	public static Object value = null;
    public Object getKey();
    public Map getParameter();
    public Object getValue();
    public void setKey(Object key);
    public void setParameter(Map parameter);
    public void setValue(Object value);
	public String getStringValue();

}
