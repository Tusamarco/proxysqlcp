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
import net.tc.isma.comparators.SubsetOrderComparator;
import net.tc.isma.utils.SynchronizedMap;
import net.tc.isma.comparators.*;


public class subset  extends baseObject
	implements Serializable
{

	private String[] ExcludedProperty = new String[]{""};
	private static Map RetrivableFields = new HashMap();
	private boolean isGroup;
	private subsetBean bean = null;

	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof subset ) )return false;
		subset castOther = ( subset ) other;
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

	public Object getAsBeanXml( )
	{
		if(bean != null)
			return bean;

		try
		{
			bean = new subsetBean();
			String encoding = (String)get("ENCODING");
			if(encoding == null)
				return null;


			bean.setId(get("subset.id"));
			bean.setGrpind((String)get("subset.grpind"));

			bean.setEndyear((String)get("subset.endyear"));
			bean.setStartyear((String)get("subset.startyear"));
			bean.setNameEn((String)get("nameEn"));
			bean.setNameFr((String)get("nameFr"));
			bean.setNameEs((String)get("nameEs"));
			bean.setNameAr((String)get("nameAr"));
			bean.setNameZh((String)get("nameZh"));
			bean.setDivisionlink((String)get("division-link"));
			bean.setDivisionlinkText((String)get("division-link-text"));
			if(get("banner") instanceof Map)
				bean.loadBanner((Map)get("banner"));
			if(get("domains") instanceof Map)
				bean.loadDomains((Map)get("domains"));
			if(get("domainsexternal") instanceof Map)
				bean.loadDomains((Map)get("domainsexternal"));


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
		return get( "subset.id" );

	}
	public static Map sort( Map subsets )
	{
		if(subsets != null)
		 {
			 List l = new ArrayList(subsets.keySet());
			 Collections.sort( l, ( Comparator )new SubsetOrderComparator() );
			 Collections.reverse(l);
			 Map nsubset = new SynchronizedMap();
			 for(int i = 0 ; i < l.size() ; i++)
			 {
				 Object k = l.get(i);
				 nsubset.put(k,subsets.get(k));
			 }
			 subsets = nsubset;
		 }

		return subsets;
	}

}
