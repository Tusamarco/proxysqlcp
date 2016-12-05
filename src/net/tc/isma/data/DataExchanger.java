package net.tc.isma.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DataExchanger
{

	public Object load(Object objectClassTemplate, Serializable id);
	public List get(Object objectClassTemplate, Object objByExample);
	public Map getMap(Object objectClassTemplate, Object objByExample);
	public boolean save(Object objectClassTemplate, Object id);
	public Map saveList(Object objectClassTemplate, List objects);
	public boolean update(Object objectClassTemplate, Object id);
	public Map update(Object objectClassTemplate, List objects);
	public boolean saveUpdate(Object objectClassTemplate, Object id);
	public Map saveUpdateList(Object objectClassTemplate, List objects);


}
