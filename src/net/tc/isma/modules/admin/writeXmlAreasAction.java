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
import net.tc.isma.comparators.AreasOrderComparator;
import net.tc.isma.utils.file.FileHandler;
import net.tc.isma.utils.SynchronizedMap;

public class writeXmlAreasAction extends actionLoadReferenceImplXml
{
	private results resultLocal = null;

	public writeXmlAreasAction()
	{
	}

	public Results execute()
	{
		resultLocal = (results)this.getResult();
//		ResultSetWrap rsw = null;
		Map areas = null;
		int lifecycle = Resource.ETERNAL;

		try
		{
			areas = ( Map ) resultLocal.get("areas.all.records");
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

		if(areas != null || reload)
		{
			ConfigResource param1 = ( ConfigResource ) IsmaPersister.getConfigResource("isma_configuration.references.areas" );
			if(param1.getStringValue() == null || param1.getStringValue().equals(""))
				return null;

			//File f = new File(FaostatPersister.getMAINROOT() + param1.getStringValue());
			// Pretty print the document to System.out

			Document document = getDocument(areas);


			writeDocumentToFile(param1, document);

		}

		try
		{
			if( areas == null || areas.size() == 0 )
			{
				resultLocal.put("areas.all.records", areas);
				return resultLocal;
			}


		}
		catch( Exception ex )
		{
			IsmaPersister.getLogSystem().error( "", ex );
		}
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

	private Document getDocument( Map areas )
	{
		Document document = DocumentHelper.createDocument();

				 Element root = document.addElement( "areas" );
				 root.addAttribute("lastupdate",Utility.getDayNumber() + "_" + Utility.getMonthNumber() + "_" + Utility.getYear() );

				 List l = null;
				 try
				 {
					 l = new ArrayList(0);
					 l.addAll((Collection)areas.keySet());
					 Collections.sort(l,(Comparator)new AreasOrderComparator());
				 }
				 catch(Exception ex){ex.printStackTrace();}
				 Iterator itArea = l.iterator();
				 try{
					 while( itArea.hasNext() )
					 {
						 Object id = itArea.next();
						 area areaTemp = ( area ) areas.get( id );

						 Element areaElement = root.addElement( "area" );
						 if( areaTemp.get( "ENCODING" ) == null )
							 areaTemp.put( "ENCODING", "UTF-8" );

						 /**
						  * Refresh the object status (Bean) and load it in themai collection
						  */

						 areaBean ab = ( areaBean ) areaTemp.getAsBeanXml();

						 if(ab.getId() != null)
						 {
							 areaElement.addAttribute( "id", ( String ) ab.getId().toString() );
							 areaElement.addAttribute( "iso3", Text.defaultTo( (String ) ab.getISO3(),"") );
							 areaElement.addAttribute( "startyear", ( String ) ab.getStartYear() );
							 areaElement.addAttribute( "endyear", ( String ) ab.getEndYear() );
							 areaElement.addAttribute( "grpind", ( String ) ab.getGrpInd() );

							 for( int iLang = 0; iLang < IsmaPersister.getAlowedLanguages().length; iLang++ )
							 {
								 String lang = IsmaPersister.getAlowedLanguages()[iLang];

								 Element nameS = areaElement.addElement( "name" );
								 nameS.addAttribute( "lang", lang );
								 String s = ( String )ab.getName( new LanguageSelector( lang) );
								 String ns = Text.getUTFStringFromDb(s,lang);
								 nameS.addText(ns);

								 nameS = areaElement.addElement( "namelong" );
								 nameS.addAttribute( "lang", lang );
								 s = ( String ) ab.getNameLong( new LanguageSelector( lang ) );
								 ns = Text.getUTFStringFromDb(s,lang);
								 nameS.addText(ns);
							 }

							 Element subareas = areaElement.addElement( "subareas" );
							 if( ab.getSubAreas() != null && ab.getSubAreas().size() > 0 )
							 {
								 Iterator itSub = ab.getSubAreas().values().iterator();
								 while( itSub.hasNext() )
								 {
									 String[] values = ( String[] ) itSub.next();
									 String sAreaId = null;
									 if(values != null && values.length > 0 && values[0] != null)
										 sAreaId = values[0];
									 Element subareaE = subareas.addElement( "subarea" );
									 subareaE.addAttribute( "id", sAreaId );
								 }

							 }
						 }
						 areas.put( id, areaTemp );
					 }
				 }catch(Exception ex)
				 {
					 ex.printStackTrace();
				 }

				 /**
				  * Reload the whole collection in to the persister
				  */
				 PersistentObject poIn = new persistentObjectImpl(Resource.ETERNAL, areas);
				 poIn.setKey("areas.all.records");
				 IsmaPersister.set((Serializable)poIn.getKey(),poIn);
		return document;
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
