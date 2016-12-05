package net.tc.isma.data.referencepicker;
import java.util.*;
import net.tc.isma.persister.IsmaPersister;

import net.tc.isma.data.objects.areaBean;
import net.tc.isma.data.objects.area;
import net.tc.isma.utils.SynchronizedMap;


public class areaPicker
{
	public areaPicker()
	{
	}
    public static List getArea( Collection ids )
    {
        if(ids == null || ids.size()<=0)
            return null;

        List l = new ArrayList(ids.size());
        Iterator it = ids.iterator();

        while(it.hasNext())
        {
            l.add(getArea(it.next()));
        }
        return l;
    }
	public static areaBean getArea( Object id )
	{
		Map areas = null;
		try
		{
			areas = ( Map ) IsmaPersister.get( SynchronizedMap.class, "areas.all.objects" );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if( areas == null)
		{
			IsmaPersister.getLogDataAccess().error( " areas list is empty please lunch the load procedure first" );
			return null;
		}

		if(areas.containsKey(id) && areas.get( id ) != null)
        {
            return (areaBean)( ( area ) areas.get( id )).getAsBeanXml() ;
        }
		return null;
	}

}
