package net.tc.isma.data.referencepicker ;

import java.util.* ;
import net.tc.isma.persister.IsmaPersister ;
import net.tc.isma.data.objects.itemBean ;
import net.tc.isma.data.objects.item ;
import net.tc.isma.utils.SynchronizedMap ;

public class itemPicker
{
    public itemPicker()
    {
    }

    public static List getItem( Collection ids )
    {
        if ( ids == null || ids.size() <= 0 )
            return null ;

        List l = new ArrayList( ids.size() ) ;
        Iterator it = ids.iterator();
        while(it.hasNext())
        {
            l.add(getItem(it.next()));
        }
        return l ;
    }

    public static itemBean getItem( Object id )
    {
        Map items = null ;
        try
        {
            items = ( Map ) IsmaPersister.get( SynchronizedMap.class ,
                                                  "items.all.objects" ) ;
        }
        catch ( Exception ex )
        {
            IsmaPersister.getLogDataAccess().error( "" , ex ) ;
        }

        if ( items == null )
        {
            IsmaPersister.getLogDataAccess().error(
                " items list is empty please lunch the load procedure first" ) ;
            return null ;
        }

        if ( items.containsKey( id ) )
            return ( itemBean ) ( ( item ) items.get( id ) ).getAsBeanXml() ;

        return null ;
    }


}
