package net.tc.isma.modules.admin;


import net.tc.isma.actions.*;
import net.tc.isma.actions.generic.results;
import java.sql.*;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.data.db.generic.ResultSetWrap;
import net.tc.isma.data.db.generic.actionLoadReferenceImplDb;
import net.tc.isma.data.db.*;
import net.tc.isma.persister.PersistentObject;
import net.tc.isma.persister.persistentObjectImpl;
import net.tc.isma.resources.Resource;
import java.io.Serializable;
import java.util.List;
import net.tc.isma.data.objects.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class getElementsDbAction extends actionLoadReferenceImplDb
{
	private results resultLocal = null;

	public getElementsDbAction()
	{
	}

	public Results execute()
	{
		resultLocal = (results)this.getResult();
		ResultSetWrap rsw = null;
		Map elements = null;

		try
		{
			elements = ( Map ) IsmaPersister.get( HashMap.class, "elements.all.records" );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if(elements == null || reload)
		{
			rsw = getResultSet( DynamicSqlXmlStatments.getSQLCommand("sql.commands.getelements", reload), true );
			elements = getResultsetValues(rsw, element.class);

			PersistentObject poIn = new persistentObjectImpl(Resource.ETERNAL, elements);
			poIn.setKey("elements.all.records");
			IsmaPersister.set((Serializable)poIn.getKey(),poIn);
		}

		try
		{
			if( elements == null || elements.size() == 0 )
				return this.getResult();

			resultLocal.put("elements.all.records",elements);

		}
		catch( Exception ex )
		{
			IsmaPersister.getLogSystem().error( "", ex );
		}
		return (Results)resultLocal;
	}

}
