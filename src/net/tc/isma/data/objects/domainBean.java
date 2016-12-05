package net.tc.isma.data.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import net.tc.isma.data.referencepicker.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import net.tc.isma.data.common.MultiLanguage;
import java.util.HashMap;
import java.util.Iterator;
import net.tc.isma.utils.SynchronizedMap;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class domainBean extends MultiLanguage
	implements Serializable
{

	private Object id;
	private String nameEn;
	private String nameFr;
	private String nameEs;
	private String nameAr;
	private String nameZh;
	private String legacy;
	private String lastUpdate;
	private String nextUpdate;
	private java.util.Map collections;
	private java.util.Map messages;
	private Map notes;
    private String internalName;

	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof domainBean ) )return false;
		domainBean castOther = ( domainBean ) other;
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

	public void setId( Object idIn )
	{
		this.id = idIn;
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

	public String getLegacy()
	{
		return legacy;
	}

	public void setLegacy( String legacy )
	{
		this.legacy = legacy;
	}

	public String getLastUpdate()
	{
		return lastUpdate;
	}

	public void setLastUpdate( String lastUpdate )
	{
		this.lastUpdate = lastUpdate;
	}

	public String getNextUpdate()
	{
		return nextUpdate;
	}

	public void setNextUpdate( String nextUpdate )
	{
		this.nextUpdate = nextUpdate;
	}

	public java.util.Map getCollections()
	{
		return collections;
	}

	public void setCollections( java.util.Map collections )
	{
		this.collections = collections;
	}

	public Map getNotes()
	{
		return notes;
	}

	public void setNotes( Map notes )
	{
		this.notes = notes;
	}
    public String getInternalName()
    {
	return internalName;
    }
    public void setInternalName(String internalName)
    {
	this.internalName = internalName;
    }

	public void loadCollections( Map object )
	{
		if(object == null || object.size() <= 0)
			return;

		collections = new SynchronizedMap(0);
		Iterator it = object.keySet().iterator();
		while(it.hasNext())
		{
			String key = (String)it.next();
			Map keys = new SynchronizedMap(0);
			if(key.indexOf(".version") > 0 || key.indexOf(".id") > 0)
			{
				String version = "";
				String id= "";
				String order = "0";
				String[] kar = key.replaceAll("\\.id\\.","\\.id@").split("\\.");
				for(int i = 0 ; i < kar.length ; i++)
				{
					if(kar[i].startsWith("version"))
						version = kar[i].replaceAll("version","");
					if(kar[i].startsWith("id@"))
						id = kar[i].replaceAll("id@","");
					if(kar[i].startsWith("order"))
						order = kar[i].replaceAll("order","");

				}
				keys.put("id",id);
				keys.put("version",version);
				keys.put("order",order);


			}
			collections.put(keys, ( String ) object.get( key ) );

		}
		collections = DataCollection.sort(getCollections());
		setCollections(collections);

	}


	public void loadNotes( Map object )
	{
		if(object == null || object.size() <= 0)
			return;

		notes = new SynchronizedMap(0);
		notes.put("noteEn", (String)object.get("note.fileEn"));
		notes.put("noteFr", (String)object.get("note.fileFr"));
		notes.put("noteEs", (String)object.get("note.fileEs"));
		notes.put("noteAr", (String)object.get("note.fileAr"));
		notes.put("noteZh", (String)object.get("note.fileZh"));
	}
	public void loadMessages( Map object )
	{
		if(object == null || object.size() <= 0)
			return;
		messages = new SynchronizedMap(0);
		Iterator it = object.keySet().iterator();
		while(it.hasNext())
		{
			String key = (String)it.next();
			messages.put(key.replaceAll("messages.",""), (String)object.get(key));
		}
	}
    public java.util.Map getMessages()
    {
		return messages;
    }
    public void setMessages(java.util.Map messages)
    {
		this.messages = messages;
    }

}
