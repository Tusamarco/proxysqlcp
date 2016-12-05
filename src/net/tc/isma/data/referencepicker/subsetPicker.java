package net.tc.isma.data.referencepicker;

import net.tc.isma.data.objects.*;
import java.util.*;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.data.objects.*;
import net.tc.isma.utils.SynchronizedMap;


public class subsetPicker
{
	public subsetPicker()
	{
	}

	public static subsetBean getSubset( String id )
	{
		Map subsets = null;
		try
		{
			subsets = ( Map ) IsmaPersister.get( SynchronizedMap.class, "subsets.all.objects" );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if( subsets == null)
		{
			IsmaPersister.getLogDataAccess().error( " Subsets list is empty please lunch the load procedure first" );
			return null;
		}

		if(subsets.containsKey(id))
			return (subsetBean)subsets.get(id);

		return null;
	}

}
