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
import net.tc.isma.utils.SynchronizedMap;


public class loadElementsXmlAction extends actionLoadReferenceImplXml
{
	private results resultLocal = null;

	public loadElementsXmlAction()
	{
	}

	public Results execute()
	{
        resultLocal = (results)this.getResult();

        if(!resultLocal.processSuccesflullyExecuted())
            return (Results)resultLocal;

//		ResultSetWrap rsw = null;
		Map elements = null;
		int lifecycle = Resource.ETERNAL;

		try
		{
			elements = getReferenceFromPersister("elements.all.objects" );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if(elements == null || reload)
		{
			ConfigResource param1 = ( ConfigResource ) IsmaPersister.getConfigResource("isma_configuration.references.elements" );
			if(param1.getStringValue() == null || param1.getStringValue().equals(""))
				return null;

			File f = new File(IsmaPersister.getMAINROOT() + param1.getStringValue());

			Map docMap = StaticDocumentFactory.getDocument(f);
			elements = getXmlValues(docMap, element.class);

			if(param1.getParameter() != null && param1.getParameter().containsKey("lifecycle")
			   && param1.getParameter().get("lifecycle")instanceof String)
				lifecycle = Integer.parseInt((String)param1.getParameter().get("lifecycle"));

			PersistentObject poIn = new persistentObjectImpl(lifecycle, elements);
			poIn.setKey("elements.all.objects");
			IsmaPersister.set((Serializable)poIn.getKey(),poIn);
			f = null;
			docMap = null;


		}

		try
		{
			if( elements == null || elements.size() == 0 )
				return this.getResult();

			resultLocal.put("elements.all.objects",elements);

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
//        else if(attributes.containsKey("itemtype") && attributes.containsKey("ele") && attributes.containsKey("displayele"))
        else if(attributes.containsKey("ele") && attributes.containsKey("displayele"))
        {
            id = new SynchronizedMap();
//            ((SynchronizedMap)id).put("itemtype", attributes.get("itemtype"));
            ((SynchronizedMap)id).put("ele", new Integer((String)attributes.get("ele")));
            ((SynchronizedMap)id).put("displayele",  new Integer((String)attributes.get("displayele")));
        }
        return id ;
    }

}
