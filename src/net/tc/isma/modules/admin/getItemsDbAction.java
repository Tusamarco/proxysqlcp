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
import net.tc.isma.data.objects.item;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;


public class getItemsDbAction extends actionLoadReferenceImplDb
{
	private results resultLocal = null;

	public getItemsDbAction()
	{
	}

	public Results execute()
	{
		resultLocal = (results)this.getResult();
		ResultSetWrap rsw = null;
		Map items = null;

		try
		{
			items = ( Map ) IsmaPersister.get( HashMap.class, "items.all.records" );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if(items == null || reload)
		{
			rsw = getResultSet( DynamicSqlXmlStatments.getSQLCommand("sql.commands.getitems", reload), true );
			items = getResultsetValues(rsw, item.class);

			if(items != null && items.size() > 0)
			{
				items = setGroups(rsw, items);
			}


			PersistentObject poIn = new persistentObjectImpl(Resource.ETERNAL, items);
			poIn.setKey("items.all.records");
			IsmaPersister.set((Serializable)poIn.getKey(),poIn);
		}

		try
		{
			if( items == null || items.size() == 0 )
				return this.getResult();

			resultLocal.put("items.all.records",items);

		}
		catch( Exception ex )
		{
			IsmaPersister.getLogSystem().error( "", ex );
		}
		return (Results)resultLocal;
	}

	private Map setGroups( ResultSetWrap rsw, Map items )
	{
		String sql = DynamicSqlXmlStatments.getSQLCommand("sql.commands.getitemsgroups", reload);
		if(sql == null || sql.equals(""))
			return null;
		rsw = getResultSet( sql, true );
		Map itemGroups = getResultSetAsMapOfMaps( rsw.getResultSet(), "item_GRP", new String[]{"item_MEMB","FACTOR"} );
		rsw.closeCpds();
		rsw = null;

		try
		{
			Iterator ita = items.keySet().iterator();
			while( ita.hasNext() )
			{
				Object aId = ita.next();
				item itemTemp = ( item ) items.get( aId );
				Map groups = null;
				if( itemTemp == null || itemTemp.getId() == null )
					continue;
				if( itemGroups.containsKey( itemTemp.getId().toString() ) )
					groups = ( Map ) itemGroups.get( itemTemp.getId().toString() );

				itemTemp.put( "groups", groups );
				items.put( aId, itemTemp );
			}
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}

		return items;
	}

}
