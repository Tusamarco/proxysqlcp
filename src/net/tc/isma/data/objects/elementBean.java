package net.tc.isma.data.objects;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import java.io.*;
import net.tc.isma.data.common.MultiLanguage;
import net.tc.isma.lang.LanguageSelector;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class elementBean extends MultiLanguage
	implements Serializable
{

	private String nameEn;
	private String nameFr;
	private String nameEs;
	private String nameAr;
	private String nameZh;
	private String nameLongEn;
	private String nameLongFr;
	private String nameLongEs;
	private String nameLongAr;
	private String nameLongZh;
	private String itemType;
	private java.util.Map groups;
	private String stdUnitEn;
	private String stdUnitFr;
	private String stdUnitEs;
	private String stdUnitAr;
	private String stdUnitZh;
	private Object id;
	private String displayele;
	private String stdUnitGeneric;
	private String ele;

	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof elementBean ) )return false;
		elementBean castOther = ( elementBean ) other;
		return new EqualsBuilder()
			.append( this.id, castOther.id )
			.isEquals();
	}

	public int hashCode()
	{
		return new HashCodeBuilder()
			.append( id )
			.toHashCode();
	}

	private void writeObject( ObjectOutputStream oos ) throws IOException
	{
		oos.defaultWriteObject();
	}

	private void readObject( ObjectInputStream ois ) throws ClassNotFoundException, IOException
	{
		ois.defaultReadObject();
	}

	public Object getId()
	{
		return id;
	}

	public void setId( Object id )
	{
		this.id = id;
	}

	public String getNameEn()
	{
		return nameEn;
	}

	public void setNameEn( String nameEn )
	{
		this.nameEn = nameEn;
	}

	public String getNameFr()
	{
		return nameFr;
	}

	public void setNameFr( String nameFr )
	{
		this.nameFr = nameFr;
	}

	public String getNameEs()
	{
		return nameEs;
	}

	public void setNameEs( String nameEs )
	{
		this.nameEs = nameEs;
	}

	public String getNameAr()
	{
		return nameAr;
	}

	public void setNameAr( String nameAr )
	{
		this.nameAr = nameAr;
	}

	public String getNameZh()
	{
		return nameZh;
	}

	public void setNameZh( String nameZh )
	{
		this.nameZh = nameZh;
	}

	public String getNameLongEn()
	{
		return nameLongEn;
	}

	public void setNameLongEn( String nameLongEn )
	{
		this.nameLongEn = nameLongEn;
	}

	public String getNameLongFr()
	{
		return nameLongFr;
	}

	public void setNameLongFr( String nameLongFr )
	{
		this.nameLongFr = nameLongFr;
	}

	public String getNameLongEs()
	{
		return nameLongEs;
	}

	public void setNameLongEs( String nameLongEs )
	{
		this.nameLongEs = nameLongEs;
	}

	public String getNameLongAr()
	{
		return nameLongAr;
	}

	public void setNameLongAr( String nameLongAr )
	{
		this.nameLongAr = nameLongAr;
	}

	public String getNameLongZh()
	{
		return nameLongZh;
	}

	public void setNameLongZh( String nameLongZh )
	{
		this.nameLongZh = nameLongZh;
	}

	public String getItemType()
	{
		return itemType;
	}

	public void setItemType( String itemType )
	{
		this.itemType = itemType;
	}

	public java.util.Map getGroups()
	{
		return groups;
	}

	public void setGroups( java.util.Map groups )
	{
		this.groups = groups;
	}

	public String getStdUnitEn()
	{
		return stdUnitEn;
	}

	public void setStdUnitEn( String stdUnitEn )
	{
		this.stdUnitEn = stdUnitEn;
	}

	public String getStdUnitFr()
	{
		return stdUnitFr;
	}

	public void setStdUnitFr( String stdUnitFr )
	{
		this.stdUnitFr = stdUnitFr;
	}

	public String getStdUnitEs()
	{
		return stdUnitEs;
	}

	public void setStdUnitEs( String stdUnitEs )
	{
		this.stdUnitEs = stdUnitEs;
	}

	public String getStdUnitAr()
	{
		return stdUnitAr;
	}

	public void setStdUnitAr( String stdUnitAr )
	{
		this.stdUnitAr = stdUnitAr;
	}

	public String getStdUnitZh()
	{
		return stdUnitZh;
	}

	public void setStdUnitZh( String stdUnitZh )
	{
		this.stdUnitZh = stdUnitZh;
	}

	public String getDisplayele()
	{
		return displayele;
	}

	public void setDisplayele( String displayele )
	{
		this.displayele = displayele;
	}

	public String getStdUnitGeneric()
	{
		return stdUnitGeneric;
	}

	public void setStdUnitGeneric( String stdUnitGeneric )
	{
		this.stdUnitGeneric = stdUnitGeneric;
	}

	public String getEle()
	{
		return ele;
	}

	public void setEle( String ele )
	{
		this.ele = ele;
	}

	///////// MULTI-LINGUAL ACCESSORS & MUTATORS //////////

	public String getStdUnit( LanguageSelector lang )
	{
		return( String ) lang.getMultilingualProperty( this, "name" );
	}

}
