package net.tc.isma.modules.admin;


import net.tc.isma.actions.*;
import net.tc.isma.actions.generic.results;
import java.sql.*;
import net.tc.isma.persister.IsmaPersister;

import net.tc.isma.data.xml.generic.*;
import net.tc.isma.persister.PersistentObject;
import net.tc.isma.persister.persistentObjectImpl;
import net.tc.isma.resources.Resource;
import java.io.Serializable;
import net.tc.isma.data.objects.*;
import net.tc.isma.data.objects.domain;
import org.dom4j.Element;
import net.tc.isma.xml.dom.StaticDocumentFactory;
import org.dom4j.Document;
import net.tc.isma.resources.ConfigResource;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import net.tc.isma.actions.generic.actionImpl;


public class removeAreasDbRecordsFromPersister extends actionImpl
{
	public removeAreasDbRecordsFromPersister()
	{
	}

	public Results execute()
	{
		Map areas = null;
		int lifecycle = Resource.ETERNAL;

		try
		{
			areas = ( Map ) IsmaPersister.get( HashMap.class, "areas.all.records" );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if(areas != null )
		{
			PersistentObject poIn = new persistentObjectImpl(lifecycle, areas);
			poIn.setKey("areas.all.records");
			IsmaPersister.remove((Serializable)poIn.getKey(),poIn);
		}
		return (results)this.getResult();
	}

}
