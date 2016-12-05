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
import java.util.List;
import net.tc.isma.data.objects.item;
import java.util.ArrayList;
import net.tc.isma.data.objects.*;
import org.dom4j.Element;
import net.tc.isma.xml.dom.StaticDocumentFactory;
import org.dom4j.Document;
import net.tc.isma.resources.ConfigResource;
import java.io.File;
import java.util.Map;
import net.tc.isma.data.generic.baseObject;
import java.util.HashMap;
import java.util.Hashtable;
import net.tc.isma.utils.SynchronizedMap;


public class loadCollectionsXmlAction extends actionLoadReferenceImplXml
{
	private results resultLocal = null;

	public loadCollectionsXmlAction()
	{
	}

	public Results execute()
	{
        resultLocal = (results)this.getResult();

        if(!resultLocal.processSuccesflullyExecuted())
            return (Results)resultLocal;

//		ResultSetWrap rsw = null;
		Map dataCollection = null;
		Map dataCollectionExternal = null;
		int lifecycle = Resource.ETERNAL;

		try
		{
			dataCollection = getReferenceFromPersister(  "datacollections.all.objects" );
//			dataCollectionExternal = ( Map ) FaostatPersister.get( SynchronizedMap.class, "datacollectionsexternal.all.objects" );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if(dataCollection == null || reload)
		{
			try
			{
				dataCollection = new SynchronizedMap( 0, 1 );
				dataCollectionExternal = new SynchronizedMap( 0, 1 );

				ConfigResource param1 = ( ConfigResource ) IsmaPersister.getConfigResource("isma_configuration.references.collections" );
				ConfigResource param2 = ( ConfigResource ) IsmaPersister.getConfigResource("isma_configuration.references.collectionsexternal" );
				if( param1.getStringValue() == null || param1.getStringValue().equals( "" ) )
					return null;

				File f = new File( IsmaPersister.getMAINROOT() + param1.getStringValue() );
				File fx = new File( IsmaPersister.getMAINROOT() + param2.getStringValue() );

				Map docMap = StaticDocumentFactory.getDocument( f );
				Map docMapx = StaticDocumentFactory.getDocument( fx );
				dataCollection.putAll( getXmlValues( docMap, DataCollection.class ) );
				dataCollection.putAll( getXmlValues( docMapx, DataCollection.class ) );
//				dataCollectionExternal.putAll( getXmlValues( docMapx, DataCollection.class ) );

				if( param1.getParameter() != null && param1.getParameter().containsKey( "lifecycle" ) && param1.getParameter().get( "lifecycle" ) instanceof String )
					lifecycle = Integer.parseInt( ( String ) param1.getParameter().get( "lifecycle" ) );

				PersistentObject poIn = new persistentObjectImpl( lifecycle, dataCollection );
				poIn.setKey( "datacollections.all.objects" );
				IsmaPersister.set( ( Serializable ) poIn.getKey(), poIn );

//				PersistentObject poInExternal = new persistentObjectImpl( lifecycle, dataCollectionExternal );
//				poInExternal.setKey( "datacollectionsexternal.all.objects" );
//				FaostatPersister.set( ( Serializable ) poInExternal.getKey(), poIn );


				f = null;
				fx = null;
				docMapx = null;
				docMap = null;
			}catch(Exception ex1){IsmaPersister.getLogDataAccess().error("",ex1);}


		}

		try
		{
			if( dataCollection == null || dataCollection.size() == 0 )
				return this.getResult();

			resultLocal.put("datacollections.all.objects",dataCollection);
//			resultLocal.put("datacollectionsexternal.all.objects",dataCollectionExternal);

		}
        catch( Exception ex )
        {
            resultLocal.processSuccesflullyExecuted(false);
            IsmaPersister.getLogSystem().error( "", ex );
            return (Results)resultLocal;
        }
		return (Results)resultLocal;
	}
    public Object getIdFromAttributes( Map attributes , Object id )
    {
        if(attributes.containsKey("id") && !attributes.containsKey("order"))
            id = attributes.get( "id" );
        else if(attributes.containsKey("id") && attributes.containsKey("order"))
        {
            id = new SynchronizedMap();
            ((SynchronizedMap)id).put("id", attributes.get("id"));
            ((SynchronizedMap)id).put("order", attributes.get("order"));
        }
        return id ;
    }

}
