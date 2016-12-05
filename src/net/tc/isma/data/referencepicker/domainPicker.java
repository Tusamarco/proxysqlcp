package net.tc.isma.data.referencepicker;

import net.tc.isma.data.objects.domainBean;
import java.util.*;
import net.tc.isma.persister.IsmaPersister;
import java.util.ArrayList;
import net.tc.isma.data.objects.domain;
import net.tc.isma.utils.SynchronizedMap;


public class domainPicker
{
	public domainPicker()
	{
	}

	public static domainBean getDomain( Object id )
	{
		Map domains = null;
		try
		{
			domains = ( Map ) IsmaPersister.get( SynchronizedMap.class, "domains.all.objects" );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if( domains == null)
		{
			IsmaPersister.getLogDataAccess().error( " Domains list is empty please lunch the load procedure first" );
			return null;
		}

		if( domains.containsKey( id ) )
		{
			domain d = ( domain ) domains.get( id );
			if( d != null && d.getAsBeanXml() != null )
				return( domainBean ) d.getAsBeanXml();
		}


		return null;
	}
	public static List getDomain( Object id, int pos )
	{
		Map domains = null;
		try
		{
			domains = ( Map ) IsmaPersister.get( SynchronizedMap.class, "domains.all.objects" );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if( domains == null)
		{
			IsmaPersister.getLogDataAccess().error( " Domains list is empty please lunch the load procedure first" );
			return null;
		}

		if(domains instanceof SynchronizedMap)
		{
			if( ((SynchronizedMap)domains).containsSubKey(id, pos ) )
			{
				List l = ( List ) ((SynchronizedMap)domains).get( id , pos );
				List outl = new ArrayList(0);
				if( l != null && l.size() > 0)
				{
					for(int i = 0 ; i < l.size() ; i++)
					{
						outl.add(i,((domain)l.get(i)).getAsBeanXml());
					}
				}
				return outl;
			}
		}
		return null;
	}

}
