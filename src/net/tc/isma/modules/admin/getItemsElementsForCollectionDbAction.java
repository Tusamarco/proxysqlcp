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
import java.lang.ref.SoftReference;
import net.tc.isma.utils.SynchronizedMap;


public class getItemsElementsForCollectionDbAction extends actionLoadReferenceImplDb
{
	private results resultLocal = null;

	public getItemsElementsForCollectionDbAction()
	{
	}

	public Results execute()
	{
		resultLocal = (results)this.getResult();

		ResultSetWrap rsw = null;
		Map itemsElements4Coll = null;

		SoftReference rswS = new SoftReference(getResultSet( DynamicSqlXmlStatments.getSQLCommand("sql.commands.getcollectionitems", reload), true ));
		rsw = (ResultSetWrap)rswS.get();
		itemsElements4Coll = getResultSetAsMapOfMaps( rsw.getResultSet(), new String[]{"ITEM_GRP","ITEM_MEMB","ITEM_TYP,ELE"}, new String[]{"*"} );

//			PersistentObject poIn = new persistentObjectImpl(Resource.ETERNAL, itemsElements4Coll);
//			poIn.setKey("itemsElements4Coll.all.records");
//			FaostatPersister.set((Serializable)poIn.getKey(),poIn);

		try
		{
			if( itemsElements4Coll == null || itemsElements4Coll.size() == 0 )
				return this.getResult();

			resultLocal.put("itemsElements4Coll.all.records",itemsElements4Coll);

		}
		catch( Exception ex )
		{
			IsmaPersister.getLogSystem().error( "", ex );
		}
		rsw = null;
		rswS.clear();

		return (Results)resultLocal;
	}

	/**
	 * Return the full record set as a LIST for the specified field
	 */
	public Map getResultSetAsMapOfMaps( ResultSet rsIn, String[] id, String[] fields )
	{
		try
		{
			if( rsIn == null || ( !rsIn.isFirst() && !rsIn.isLast() ) )
			{
				return null;
			}
		}
		catch( SQLException ex1 )
		{
		}
		java.lang.ref.SoftReference mS = new SoftReference(new SynchronizedMap());
		Map m = (Map)mS.get();

		try
		{

			rsIn.first();
			while( !rsIn.isLast() )
			{
				String[] idMap = new String[id.length];
				for(int idi = 0 ; idi < idMap.length ;idi++)
				{
					idMap[idi] = rsIn.getString( id[idi]);
				}

				String valueId = rsIn.getString( fields[0] );
				String[] values = new String[fields.length];
				Map mvalues = new SynchronizedMap();
				if( m.containsKey( idMap ) )
				{
					mvalues = ( Map ) m.get( idMap );
				}

				for( int idf = 0; idf < fields.length; idf++ )
				{
					//rsIn.getString(field);
					values[idf] = rsIn.getString( fields[idf] );
				}
				mvalues.put( valueId, values );
				m.put( idMap, mvalues );
				rsIn.next();
			}

		}
		catch( Exception ex )
		{
			IsmaPersister.getLogSystem().error( "", ex );
		}

		return m;

	}

}
