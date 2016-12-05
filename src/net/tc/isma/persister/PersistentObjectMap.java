package net.tc.isma.persister;

import java.util.HashMap;
import java.util.ArrayList;
import net.tc.isma.resources.Resource;
import java.util.List;
import java.util.Iterator;

public class PersistentObjectMap extends HashMap
{
	public PersistentObjectMap()
	{
	}

	public PersistentObject[] getResourceBoundlePo()
	{
		if(size() <= 0 )
			return null;

		ArrayList pol = new ArrayList();
		Iterator it = this.keySet().iterator();

		while(it.hasNext())
		{
			Object key = it.next();
			if(get(key) instanceof Resource)
			{
				Resource po = (Resource)get(key);
				if(po.getResourceType() == Resource.INI || po.getResourceType() == Resource.RESOURCEBUNDLE)
				{
					((PersistentObject)po).setKey(key);
					pol.add(po);
				}
			}

		}
		PersistentObject[] po = new PersistentObject[0];
		return (PersistentObject[])pol.toArray(po);

	}
}
