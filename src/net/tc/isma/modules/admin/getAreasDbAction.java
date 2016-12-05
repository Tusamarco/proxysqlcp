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
import java.util.Iterator;


public class getAreasDbAction extends actionLoadReferenceImplDb
{
	private results resultLocal = null;

	public getAreasDbAction()
	{
	}

	public Results execute()
	{
		resultLocal = (results)this.getResult();
		ResultSetWrap rsw = null;
		Map areas = null;

		try
		{
			areas = ( Map ) IsmaPersister.get( HashMap.class, "areas.all.records" );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if(areas == null || reload)
		{
			rsw = getResultSet( DynamicSqlXmlStatments.getSQLCommand("sql.commands.getareas", reload), true );
			areas = getResultsetValues(rsw, area.class);
			rsw.closeCpds();
			rsw = null;

			if(areas != null && areas.size() > 0)
			{
				areas = setSubareas(rsw, areas);
			}

			PersistentObject poIn = new persistentObjectImpl(Resource.ETERNAL, areas);
			poIn.setKey("areas.all.records");
			IsmaPersister.set((Serializable)poIn.getKey(),poIn);
		}

		try
		{
			if( areas == null || areas.size() == 0 )
				return this.getResult();

			resultLocal.put("areas.all.records",areas);

		}
		catch( Exception ex )
		{
			IsmaPersister.getLogSystem().error( "", ex );
		}
		return (Results)resultLocal;
	}

	private Map setSubareas( ResultSetWrap rsw, Map areas )
	{
		rsw = getResultSet( DynamicSqlXmlStatments.getSQLCommand("sql.commands.getareaall", reload), true );
		Map areaGroups = getResultSetAsMapOfMaps( rsw.getResultSet(), "AREA_GRP", new String[]{"AREA_MEMB"} );
		rsw.closeCpds();
		rsw = null;

		try
		{
			Iterator ita = areas.keySet().iterator();
			while( ita.hasNext() )
			{
				Object aId = ita.next();
				area areaTemp = ( area ) areas.get( aId );
				Map subareas = null;
				if( areaTemp == null || areaTemp.getId() == null )
					continue;
				if( areaGroups.containsKey( areaTemp.getId().toString() ) )
					subareas = ( Map ) areaGroups.get( areaTemp.getId().toString() );

				areaTemp.put( "subareas", subareas );
				areas.put( aId, areaTemp );
			}
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}

		return areas;
	}

}
