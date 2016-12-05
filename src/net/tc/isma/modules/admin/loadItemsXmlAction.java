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
import net.tc.isma.data.objects.domain;
import org.dom4j.Element;
import net.tc.isma.xml.dom.StaticDocumentFactory;
import org.dom4j.Document;
import net.tc.isma.resources.ConfigResource;
import java.io.File;
import java.util.Map;
import net.tc.isma.data.generic.baseObject;
import java.util.HashMap;
import net.tc.isma.utils.SynchronizedMap;


public class loadItemsXmlAction extends actionLoadReferenceImplXml
{
	private results resultLocal = null;

	public loadItemsXmlAction()
	{
	}

	public Results execute()
	{
        resultLocal = (results)this.getResult();

        if(!resultLocal.processSuccesflullyExecuted())
            return (Results)resultLocal;

//		ResultSetWrap rsw = null;
		Map items = null;
		int lifecycle = Resource.ETERNAL;

		try
		{
			items = getReferenceFromPersister("items.all.objects" );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if(items == null || reload)
		{
			ConfigResource param1 = ( ConfigResource ) IsmaPersister.getConfigResource("isma_configuration.references.items" );
//			ConfigResource param2 = ( ConfigResource ) FaostatPersister.getConfigResource("isma_configuration.references.itemsexternal" );
			if(param1.getStringValue() == null || param1.getStringValue().equals(""))
				return null;

			File f = new File(IsmaPersister.getMAINROOT() + param1.getStringValue());
//			File fx = new File(FaostatPersister.getMAINROOT() + param2.getStringValue());

			Map docMap = StaticDocumentFactory.getDocument(f);
//			Map docMapx = StaticDocumentFactory.getDocument(fx);
			items = getXmlValues(docMap, item.class);
//			items.putAll(getXmlValues(docMapx, item.class));

			if(param1.getParameter() != null && param1.getParameter().containsKey("lifecycle")
			   && param1.getParameter().get("lifecycle")instanceof String)
				lifecycle = Integer.parseInt((String)param1.getParameter().get("lifecycle"));

			PersistentObject poIn = new persistentObjectImpl(lifecycle, items);
			poIn.setKey("items.all.objects");
			IsmaPersister.set((Serializable)poIn.getKey(),poIn);
			f = null;
//			fx = null;
//			docMapx = null;
			docMap = null;


		}

		try
		{
			if( items == null || items.size() == 0 )
				return this.getResult();

			resultLocal.put("items.all.objects",items);

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
