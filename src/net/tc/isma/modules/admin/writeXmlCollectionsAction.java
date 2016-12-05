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
import net.tc.isma.utils.Text;
import org.dom4j.Document;
import net.tc.isma.resources.ConfigResource;
import java.io.File;
import java.util.Map;
import java.util.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import java.io.FileWriter;
import org.dom4j.DocumentHelper;
import net.tc.isma.utils.Utility;
import org.dom4j.tree.AbstractElement;
import net.tc.isma.lang.LanguageSelector;
import net.tc.isma.utils.faostatOldCompatibilityTool;
import java.io.FileOutputStream;
import net.tc.isma.utils.file.FileHandler;
import net.tc.isma.comparators.collectionsOrderComparator;
import net.tc.isma.utils.SynchronizedMap;

public class writeXmlCollectionsAction extends actionLoadReferenceImplXml
{
	private results resultLocal = null;

	public writeXmlCollectionsAction()
	{
	}

	public Results execute()
	{
		resultLocal = (results)this.getResult();

		Map dataCollection = null;
		int lifecycle = Resource.ETERNAL;

		try
		{
			dataCollection = ( Map ) IsmaPersister.get( SynchronizedMap.class, "datacollections.all.objects" );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if(dataCollection != null || reload)
		{
			ConfigResource param1 = ( ConfigResource ) IsmaPersister.getConfigResource("isma_configuration.references.collections" );
			if(param1.getStringValue() == null || param1.getStringValue().equals(""))
				return null;

			Document document = getDocument(dataCollection);
			writeDocumentToFile(param1, document);

		}

//		try
//		{
//			if( dataCollection == null || dataCollection.size() == 0 )
//			{
//				resultLocal.put("collections.all.records", dataCollection);
//				return resultLocal;
//			}
//
//
//		}
//		catch( Exception ex )
//		{
//			FaostatPersister.getLogSystem().error( "", ex );
//		}
		return this.getResult();
	}

	private void writeDocumentToFile( ConfigResource param1, Document document )
	{
		try
		{
			File f = new File( IsmaPersister.getMAINROOT() + param1.getStringValue() );
			if( f.exists() )
			{
				FileHandler.copyFile( IsmaPersister.getMAINROOT() + param1.getStringValue(),
									  f.getPath().replaceAll( f.getName(), "" ), "OLD_" + f.getName() );
				f.delete();
			}
			f = null;

			OutputFormat format = new OutputFormat( "  ", true, "UTF-8" );

			XMLWriter writer = new XMLWriter( new FileOutputStream( IsmaPersister.getMAINROOT() +
				param1.getStringValue() ), format );
			writer.write( document );
			writer.flush();
			writer.close();
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}
	}

	private Document getDocument( Map collections )
	{
		Document document = DocumentHelper.createDocument();

		 Element root = document.addElement( "datacollections" );
		 root.addAttribute("lastupdate",Utility.getDayNumber() + "_" + Utility.getMonthNumber() + "_" + Utility.getYear() );

		 List l = null;
		 try
		 {
			 l = new ArrayList(0);
			 l.addAll((Collection)collections.keySet());
			 Collections.sort(l,(Comparator)new collectionsOrderComparator());
		 }
		 catch(Exception ex){ex.printStackTrace();}
		 Iterator itcollections = l.iterator();
		 try{
			 while( itcollections.hasNext() )
			 {
				 Object id = itcollections.next();
				 DataCollection collectionTemp = ( DataCollection ) collections.get( id );
                 collectionBean ab = ( collectionBean ) collectionTemp.getAsBeanXml();
                 if(ab.getRedirect() != null && !ab.getRedirect().equals("") && ab.getRedirect().length() > 1)
                     continue;

				 Element collectionElement = root.addElement( "datacollection" );
				 if( collectionTemp.get( "ENCODING" ) == null )
					 collectionTemp.put( "ENCODING", "UTF-8" );

				 /**
				  * Refresh the object status (Bean) and load it in themai collection
				  */
				 if(ab.getId() != null)
				 {
					 collectionElement.addAttribute( "id", ( String ) ab.getId().toString() );
					 collectionElement.addAttribute( "type", Text.defaultTo( (String ) ab.getType(),"") );
					 collectionElement.addAttribute( "startyear", ( String ) ab.getStartYear() );
					 collectionElement.addAttribute( "endyear", ( String ) ab.getEndYear() );
					 collectionElement.addAttribute( "grpind", ( String ) ab.getGrpind() );

					 for( int iLang = 0; iLang < IsmaPersister.getAlowedLanguages().length; iLang++ )
					 {
						 String lang = IsmaPersister.getAlowedLanguages()[iLang];

						 Element nameS = collectionElement.addElement( "name" );
						 nameS.addAttribute( "lang", lang );
						 String s = ( String )ab.getName( new LanguageSelector( lang) );
						 String ns = s;
						 nameS.addText(ns);
					 }


					Element elx = null;
					elx = collectionElement.addElement( "first-key" );
					if( ab.getFirstkey() != null && ab.getFirstkey().equals( "" ) )
						elx.addText( ab.getFirstkey() );

					elx = collectionElement.addElement( "second-key-text" );
					if( ab.getSecondkeytex() != null && ab.getSecondkeytex().equals( "" ) )
						elx.addText( ab.getSecondkeytex() );

					elx = collectionElement.addElement( "third-key-text" );
					if( ab.getThirdkey()!= null && ab.getThirdkey().equals( "" ) )
						elx.addText( ab.getThirdkey());

					elx = collectionElement.addElement( "select-text" );
					if( ab.getSelecttext()!= null && ab.getSelecttext().equals( "" ) )
						elx.addText( ab.getSelecttext());

					elx = collectionElement.addElement( "copyright-link" );
					if( ab.getCopyrightlink()!= null && ab.getCopyrightlink().equals( "" ) )
						elx.addText( ab.getCopyrightlink());

					elx = collectionElement.addElement( "copyright-text" );
					if( ab.getCopyrighttext()!= null && ab.getCopyrighttext().equals( "" ) )
						elx.addText( ab.getCopyrighttext());

					elx = collectionElement.addElement( "axis-element" );
					if( ab.getAxisElement()!= null && ab.getAxisElement().equals( "" ) )
						elx.addText( ab.getAxisElement());


					 Element notes = collectionElement.addElement( "notes" );
					 if( ab.getNotes() != null && ab.getNotes().size() > 0 )
					 {

						 Iterator itNotes = ab.getNotes().keySet().iterator();
						 while( itNotes.hasNext() )
						 {
							Map key = (Map)itNotes.next();
							String version = Text.defaultTo(key.get("version"),"");
							String index = Text.defaultTo(key.get("index"),"");;
							String value = ( String ) ab.getNotes().get(key);

							if(value != null && !value.equals(""))
							{
								Element note = notes.addElement( "note" );
								note.addAttribute( "version", version );
								note.addAttribute( "index", index );
								note.addText(value);
							}


						 }

					 }
					 Element items = collectionElement.addElement( "items" );
					 if( ab.getItems() != null && ab.getItems().size() > 0 )
					 {
						 Iterator it = ab.getItems().values().iterator();
						 while(it.hasNext())
						 {

							 String value = ( String ) getIdasString(it.next());
							 Element item = items.addElement( "item" );
							 item.addAttribute( "id", value );
						 }
				    }
                    /** @todo
                     * This needs to be change in order to work:
                     * instead of loading only the DIPLAY_ELE fields
                     * in the reference, the refrence must containd all the 3 key fields
                     * DIPLAY_ELE, ITEM_TYPE and the ELE.
                     * anf print all of them as follow
                     * <element itemtype="1" ele="2" displayele="99"/>
                     * all of them both in the KEY and values
                     *  */

                    Element elements = collectionElement.addElement("elements" ) ;
                    if ( ab.getElements() != null &&
                         ab.getElements().size() > 0 )
                    {
                        Iterator it = ab.getElements().values().iterator() ;
                        while ( it.hasNext() )
                        {
                            Map elementMap = ( Map ) it.next() ;

                            if ( elementMap != null && elementMap.size() > 0 )
                            {
//                                String value = ( String ) getIdasString( it.next() ) ;
                                Element element = elements.addElement("element" ) ;
//                                element.addAttribute( "itemType" , ((Integer)elementMap.get("ITEM_TYP")).toString() ) ;
                                element.addAttribute( "ele" , ((Integer)elementMap.get("ELE")).toString() ) ;
                                element.addAttribute( "displayele" , ((Integer)elementMap.get("DISPLAY_ELE")).toString() ) ;
                            }
                        }
                    }

				   Element areas = collectionElement.addElement( "areas" );
				   if( ab.getAreas() != null && ab.getAreas().size() > 0 )
				   {
					   Iterator it = ab.getAreas().values().iterator();
					   while(it.hasNext())
					   {
						   String value = ( String ) getIdasString(it.next());
						   Element element = areas.addElement( "area" );
						   element.addAttribute( "id", value );
					   }
				  }


				 }
				 collections.put( id, collectionTemp );
			 }
		 }catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }

		 /**
		  * Reload the whole collection in to the persister
		  */
		 PersistentObject poIn = new persistentObjectImpl(Resource.ETERNAL, collections);
		 poIn.setKey("datacollections.all.objects");
		 IsmaPersister.set((Serializable)poIn.getKey(),poIn);
		return document;
	}
	private String getIdasString(Object id)
	{
		String ids= "";
		if(id instanceof Integer )
			ids = String.valueOf(((Integer)id).intValue());
		if(id instanceof Long)
			ids = String.valueOf(((Integer)id).intValue());
		if(id instanceof String)
			ids = (String)id;

		return ids;
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
