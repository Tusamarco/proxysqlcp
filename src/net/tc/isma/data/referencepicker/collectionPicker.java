package net.tc.isma.data.referencepicker ;

import net.tc.isma.data.objects.collectionBean ;
import net.tc.isma.resources.Resource ;
import java.util.* ;
import java.util.ArrayList ;
import net.tc.isma.persister.IsmaPersister ;
import net.tc.isma.data.objects.DataCollection ;
import net.tc.isma.utils.SynchronizedMap ;

public class collectionPicker
{
    public collectionPicker()
    {
    }

    public static List getCollection( Collection ids, int pos )
    {
        if ( ids == null || ids.size() <= 0 )
            return null ;

        List l = new ArrayList( ids.size() ) ;
        Iterator it = ids.iterator() ;
        while ( it.hasNext() )
        {
            l.add( getCollection( it.next(), pos ) ) ;
        }
        return l ;
    }

    public static collectionBean getCollection( String id )
    {
        collectionBean collection = new collectionBean() ;

        Map dataCollection = null ;
        try
        {
            dataCollection = ( Map ) IsmaPersister.get( SynchronizedMap.class ,
                "datacollections.all.objects" ) ;
        }
        catch ( Exception ex )
        {
            IsmaPersister.getLogDataAccess().error( "" , ex ) ;
        }

        if ( dataCollection == null )
        {
            IsmaPersister.getLogDataAccess().error(
                " collection list is empty please lunch the load procedure first" ) ;
            return null ;
        }

        if ( dataCollection.containsKey( id ) )
        {
            DataCollection col = ( DataCollection ) dataCollection.get( id ) ;
            if ( col != null )
                return ( collectionBean ) col.getAsBeanXml() ;
        }

        return null ;

    }

    public static List getCollection( Object id , int pos )
    {
        Map collection = null ;
        try
        {
            collection = ( Map ) IsmaPersister.get( SynchronizedMap.class ,
                "datacollections.all.objects" ) ;
        }
        catch ( Exception ex )
        {
            IsmaPersister.getLogDataAccess().error( "" , ex ) ;
        }

        if ( collection == null )
        {
            IsmaPersister.getLogDataAccess().error(
                " Collection list is empty please lunch the load procedure first" ) ;
            return null ;
        }

        if ( collection instanceof SynchronizedMap )
        {
            if ( ( ( SynchronizedMap ) collection ).containsSubKey( id , pos ) )
            {
                List l = ( List ) ( ( SynchronizedMap ) collection ).get( id ,
                    pos ) ;
                List outl = new ArrayList( 0 ) ;
                if ( l != null && l.size() > 0 )
                {
                    for ( int i = 0 ; i < l.size() ; i++ )
                    {
                        outl.add( i ,
                                  ( ( DataCollection ) l.get( i ) ).getAsBeanXml() ) ;
                    }
                }
                return outl ;
            }
        }
        return null ;
    }

}
