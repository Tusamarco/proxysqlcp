package net.tc.isma.data.objects;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import java.io.*;
import net.tc.isma.data.common.MultiLanguage;
import java.util.Map;
import net.tc.isma.data.generic.baseObject;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import net.tc.isma.persister.IsmaPersister;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.Iterator;
import net.tc.isma.utils.Text;
import java.util.Collections;
import java.util.Comparator;
import net.tc.isma.comparators.DomainsOrderComparator;
import net.tc.isma.utils.SynchronizedMap;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class domain extends baseObject
	implements Serializable
{

	private String[] ExcludedProperty = new String[]{""};
	private static Map RetrivableFields = new HashMap();
	private boolean isGroup;
	private domainBean bean = null;

	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof domain ) )return false;
		domain castOther = ( domain ) other;
		return new EqualsBuilder()
			.append( this, castOther )
			.isEquals();
	}



	public void setIsGroup( boolean isGroup )
	{
		this.isGroup = isGroup;
	}

	public String[] getExcludedProperty()
	{
		return this.ExcludedProperty;
	}

	public List getRetrivableFields()
	{
		if( RetrivableFields == null )
			RetrivableFields = initRetrivableFields();

		return new ArrayList( RetrivableFields.keySet() );
	}

	public Map getRetrivableFieldsMap()
	{
		if( RetrivableFields == null )
			RetrivableFields = initRetrivableFields();

		return RetrivableFields;
	}

	public void init( Map rowValues )
	{

	}

	public Class itemClass()
	{
		return this.getClass();
	}

/**
	 * This method is a temporary solution for the definition of the mapping between
	 * db and properties
	 * it should be moved to xml file
	 */
	private Map initRetrivableFields()
	{
		RetrivableFields = new HashMap();
		// This method is not used if the object is loaded from XML files

		return RetrivableFields;
	}

/**
	 * Used to return all the values as a bean
	 */
	public Object getAsBean()
	{

		try
		{
			return this.getAsBean( this, (Map)this );
		}
		catch( Exception ex )
		{
			IsmaPersister.getLogDataAccess().error( "", ex );
			return null;
		}
	}

	public boolean isIsGroup()
	{
		if( super.values() == null )
			return false;

		return isGroup;
	}

	public Object getAsBeanXml()
	{
		if(bean != null)
			return bean;

		try
		{
			bean = new domainBean();
			String encoding = (String)get("ENCODING");
			if(encoding == null)
				return null;


			bean.setId(get("domain.id"));
			bean.setLastUpdate((String)get("domain.lastupdate"));
			bean.setNextUpdate((String)get("domain.nextupdate"));
			bean.setLegacy((String)get("legacy.name"));
			bean.setInternalName((String)get("internalname"));
			bean.setNameEn((String)get("nameEn"));
			bean.setNameFr((String)get("nameFr"));
			bean.setNameEs((String)get("nameEs"));
			bean.setNameAr((String)get("nameAr"));
			bean.setNameZh((String)get("nameZh"));

			bean.loadCollections((Map)get("collections"));
			bean.loadCollections((Map)get("collectionsexternal"));
			bean.loadNotes((Map)get("notes"));
			bean.loadMessages((Map)get("messages"));

			this.bean = bean;
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}

		return bean;
	}
	public Object getId()
	{
		return get("domain.id");

	}
	public static  Map sort( Map domains )
	{
		if(domains != null)
		 {
			 List l = new ArrayList(domains.keySet());
			 Collections.sort( l, ( Comparator )new DomainsOrderComparator() );
			 Collections.reverse(l);

			 Map nsubset = new SynchronizedMap();
			 for(int i = 0 ; i < l.size() ; i++)
			 {
				 Object k = l.get(i);
				 nsubset.put(k,domains.get(k));
			 }
			 domains = nsubset;
		 }
		return domains;
	}

}
