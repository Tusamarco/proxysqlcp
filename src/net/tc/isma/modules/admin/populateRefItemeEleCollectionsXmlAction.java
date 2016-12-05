package net.tc.isma.modules.admin;


import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;
import net.tc.isma.actions.Results;
import net.tc.isma.actions.generic.results;
import net.tc.isma.data.db.DynamicSqlXmlStatments;
import net.tc.isma.data.db.generic.ResultSetWrap;
import net.tc.isma.data.db.generic.actionLoadReferenceImplDb;
import net.tc.isma.data.objects.DataCollection;
import net.tc.isma.data.objects.collectionBean;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.persister.PersistentObject;
import net.tc.isma.persister.persistentObjectImpl;
import net.tc.isma.resources.Resource;
import net.tc.isma.utils.SynchronizedMap;


public class populateRefItemeEleCollectionsXmlAction extends actionLoadReferenceImplDb
{
	private results resultLocal = null;

	public populateRefItemeEleCollectionsXmlAction()
	{
	}

	public Results execute()
	{
		resultLocal = (results)this.getResult();
//		ResultSetWrap rsw = null;
		Map collections = null;
		int lifecycle = Resource.ETERNAL;

		try
		{
			collections = ( Map ) IsmaPersister.get( SynchronizedMap.class, "datacollections.all.objects" );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if(collections == null )
		    return null;

		try{

			Iterator it = collections.keySet().iterator();
			while(it.hasNext())
			{
				Object id = it.next();
				DataCollection colTemp = (DataCollection)collections.get(id);
				collectionBean colBean = (collectionBean)colTemp.getAsBeanXml();
                // IF this is an external collection and have a redirection
                // so it is not processed
                if(colBean.getRedirect() != null)
                    continue;
				colTemp.remove("items");
				colTemp.remove("elements");
				colBean = setItemColRef(colBean);
				colTemp.put("items",colBean.getItems());
				colTemp.put("elements",colBean.getElements());
				colTemp.put("areas", colBean.getAreas());
				//May be there is the need to refresh the object
				colTemp.setBean(colBean);
				collections.put(id,colTemp);

			}

		}
		catch(Exception ex1)
		{IsmaPersister.getLogDataAccess().error("",ex1);}


		try
		{
			PersistentObject poIn = new persistentObjectImpl( lifecycle, collections );
			poIn.setKey( "datacollections.all.objects" );
			IsmaPersister.set( ( Serializable ) poIn.getKey(), poIn );

		}
		catch( Exception ex )
		{
			IsmaPersister.getLogSystem().error( "", ex );
		}
		return (Results)resultLocal;
	}

	private collectionBean setItemColRef( collectionBean colBean )
	{

		resultLocal = ( results )this.getResult();

		ResultSetWrap rsw = null;

		String sqlItems = DynamicSqlXmlStatments.getSQLCommand("sql.commands.getcollectionitems", reload );
		String sqlElements = DynamicSqlXmlStatments.getSQLCommand("sql.commands.getcollectionelements", reload );
		String sqlAreas = DynamicSqlXmlStatments.getSQLCommand("sql.commands.getcollectionareas", reload );
		if(sqlItems == null || sqlElements == null)
			return null;

		Object id = getCollectionId( colBean.getId());
		if(id instanceof Integer)
		{
			sqlItems = sqlItems.replaceAll( "@", Integer.toString( ( ( Integer ) id ).intValue() ) );
			sqlElements = sqlElements.replaceAll( "@", Integer.toString( ( ( Integer ) id ).intValue() ) );
			sqlAreas = sqlAreas.replaceAll( "@", Integer.toString( ( ( Integer ) id ).intValue() ) );
		}
		else
		{
			sqlItems = sqlItems.replaceAll( "@", new String( "'" + ( String ) id + "'" ) );
			sqlElements = sqlElements.replaceAll( "@", new String( "'" + ( String ) id + "'" ) );
			sqlAreas = sqlAreas.replaceAll( "@", new String( "'" + ( String ) id + "'" ) );
		}

		Map items = new SynchronizedMap(0);
		Map elements = new SynchronizedMap(0);
		Map areas = new SynchronizedMap(0);

		SoftReference rswS = new SoftReference( getResultSet( sqlItems, true ) );
		SoftReference rswS2 = new SoftReference( getResultSet( sqlElements, true ) );
		SoftReference rswS3 = new SoftReference( getResultSet( sqlAreas, true ) );

		rsw = ( ResultSetWrap ) rswS.get();
		ResultSet rsIn = rsw.getResultSet();
		try{

			if(rsw.size() > 0)
			{
				IsmaPersister.getLogDataAccess().info( rsw.size() + " records for collection(item) id = " + id.toString());
				rsIn.first();
				while( !rsIn.isLast() )
				{
					Integer itemId = new Integer( rsIn.getInt( "ITEM_MEMB" ) );
					if( !items.containsKey( itemId ) )
						items.put( itemId, itemId );

					rsIn.next();
				}
				if( rsIn.isLast() )
				{
					Integer itemId = new Integer( rsIn.getInt( "ITEM_MEMB" ) );
					if( !items.containsKey( itemId ) )
						items.put( itemId, itemId );
				}
				rsIn.close();
			}
			else
				IsmaPersister.getLogDataAccess().info(" Empty res set for collection(item) id = " + id.toString());

			rsw = null;
			rswS.clear();

			rsw = ( ResultSetWrap ) rswS2.get();
			rsIn = rsw.getResultSet();
			if(rsw.size() > 0)
			{
				IsmaPersister.getLogDataAccess().info( rsw.size() + " records for collection(element) id = " + id.toString());
				rsIn.first();
				while( !rsIn.isLast() )
				{
                    /** @todo
                     * This needs to be change in order to work:
                     * instead of loading only the DIPLAY_ELE fields for the key
                     * the process must load also the ITEM_TYPE and the ELE fields
                     * all of them both in the KEY and values
                     * KEEP in mind that the elements collection use as id
                     * a ArrayList of 3 elements Item_type(1), Ele(2), DisplayEle(3)
                     * for each element
                     *  */
                    {
                        Map keys = new SynchronizedMap(0);
//                        keys.put("ITEM_TYP",new Integer( rsIn.getInt( "ITEM_TYP" )));
                        keys.put("ELE",new Integer( rsIn.getInt( "ELE" )));
                        keys.put("DISPLAY_ELE",new Integer( rsIn.getInt( "DISPLAY_ELE" )));
                        if( !((SynchronizedMap)elements).containsInternalKey( keys ) )
                            elements.put( keys , keys ) ;
                    }

					rsIn.next();
				}
				if( rsIn.isLast() )
				{
                    {
                        Map keys = new SynchronizedMap(0);
//                        keys.put("ITEM_TYP",new Integer( rsIn.getInt( "ITEM_TYP" )));
                        keys.put("ELE",new Integer( rsIn.getInt( "ELE" )));
                        keys.put("DISPLAY_ELE",new Integer( rsIn.getInt( "DISPLAY_ELE" )));
                        if( !((SynchronizedMap)elements).containsInternalKey( keys ) )
                            elements.put( keys , keys ) ;
                    }
				}
				rsIn.close();
				rsIn =null;
			}
			else
				IsmaPersister.getLogDataAccess().info(" Empty res set for collection(element) id = " + id.toString());
			rsw = null;
			rswS2.clear();

			rsw = ( ResultSetWrap ) rswS3.get();
			rsIn = rsw.getResultSet();

			if(rsw.size() > 0)
			{
				IsmaPersister.getLogDataAccess().info( rsw.size() + " records for collection(area) id = " + id.toString());
				rsIn.first();
				while( !rsIn.isLast() )
				{
					Integer areaId = new Integer( rsIn.getInt( "AREA" ) );
					if( !areas.containsKey( areaId ) )
						areas.put( areaId, areaId );

					rsIn.next();
				}
				if( rsIn.isLast() )
				{
					Integer areaId = new Integer( rsIn.getInt( "AREA" ) );
					if( !areas.containsKey( areaId ) )
						areas.put( areaId, areaId );
				}
				rsIn.close();
				rsIn =null;

			}
			else
				IsmaPersister.getLogDataAccess().info(" Empty res set for collection(Area) id = " + id.toString());

			rsw = null;
			rswS3.clear();


		}
		catch(Exception ex1)
		{IsmaPersister.getLogDataAccess().error("",ex1);}

		colBean.setItems(items);
		colBean.setElements(elements);
		colBean.setAreas(areas);
		return colBean;
	}

	private Object getCollectionId( Object object )
	{
		try{
			Integer id = new Integer( ( String ) object );
			return id;
		}
		catch(NumberFormatException nu)
		{
			String id = object.toString();
			return id;
		}
	}

}
