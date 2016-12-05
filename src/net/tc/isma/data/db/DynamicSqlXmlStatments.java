package net.tc.isma.data.db;

import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.*;
import net.tc.isma.resources.ConfigResource;
import java.io.File;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Element;
import org.dom4j.Node;
import java.util.Map;
import org.dom4j.Attribute;
import java.util.HashMap;
import java.io.IOException;
import net.tc.isma.resources.ResourceBaseImp;
import net.tc.isma.xml.dom.StaticDocumentFactory;
import net.tc.isma.persister.PersistentObject;
import net.tc.isma.persister.persistentObjectImpl;
import net.tc.isma.resources.Resource;
import java.util.Iterator;

public class DynamicSqlXmlStatments
{
	static Map sqlCommands = null;
	static Map domMap = null;
	static String encoding = null;

	public DynamicSqlXmlStatments()
	{
	}

	private static void init()
	{
		try
		{
//			sqlCommands = ( HashMap ) FaostatPersister.get( persistentObjectImpl.class, "isma_configuration.sqlcommands" );
//
//			if( sqlCommands != null )
//				return;
                        /** @todo
                         * Add the loading of the sql commands as a directory instead a file
                         *  */

			ConfigResource param1 = ( ConfigResource ) IsmaPersister.getConfigResource("isma_configuration.sqlcommands" );
			PersistentObject poSqlCommands = new persistentObjectImpl(param1);
			PersistentObject poSqlCommandsIn = new persistentObjectImpl(Resource.ETERNAL,poSqlCommands);
			poSqlCommandsIn.setKey(poSqlCommands.getKey());
			sqlCommands = (Map)poSqlCommands;


			if(poSqlCommands != null)
				IsmaPersister.set("isma_configuration.sqlcommands",poSqlCommandsIn);
		}
		catch( IsmaException ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
		}

	}

	public static String getSQLCommand( String name )
	{
		return getSQLCommand( name , false);
	}
	public static String getSQLCommand( String name, boolean reload )
	{
		if( sqlCommands == null || reload)
			init();

		if( sqlCommands != null && sqlCommands.size() > 0 )
		{
			sqlCommands = ( HashMap ) IsmaPersister.get( persistentObjectImpl.class, "isma_configuration.sqlcommands" );
			String sql = null;
			if(!sqlCommands.containsKey(name))
				return null;

			try
			{
				ConfigResource cr =  (ConfigResource)sqlCommands.get( name );
				sql = (String)cr.getValue();
			}
			catch( Exception ex )
			{
				ex.printStackTrace();
			}
			return sql;
		}

		return null;
	}

}
