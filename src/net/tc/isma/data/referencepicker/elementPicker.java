package net.tc.isma.data.referencepicker ;

import java.util.* ;
import net.tc.isma.persister.IsmaPersister ;
import net.tc.isma.data.objects.elementBean ;
import net.tc.isma.data.objects.element ;
import net.tc.isma.utils.SynchronizedMap ;

public class elementPicker
{
    public elementPicker()
    {
    }

    public static List getElement( Collection ids )
    {
        if ( ids == null || ids.size() <= 0 )
            return null ;

        List l = new ArrayList(ids.size());
        Iterator it = ids.iterator();
        while(it.hasNext())
        {
            l.add(getElement(it.next()));
        }
        return l ;
    }

    public static elementBean getElement( Object id )
    {
        Map elements = null ;
        try
        {
            elements = ( Map ) IsmaPersister.get( SynchronizedMap.class ,"elements.all.objects" ) ;
        }
        catch ( Exception ex )
        {
            IsmaPersister.getLogDataAccess().error( "" , ex ) ;
        }

        if ( elements == null )
        {
            IsmaPersister.getLogDataAccess().error(
                " elements list is empty please lunch the load procedure first" ) ;
            return null ;
        }
        /** @todo
         * This needs to be change in order to work:
         * instead of searching only the DIPLAY_ELE fields
         * in the key, the refrence must containd all the 3 key fields
         * DIPLAY_ELE, ITEM_TYPE and the ELE.
         * Looks for all of them to match ot at least for DIPLAY_ELE, ITEM_TYPE
         * KEEP in mind that the elements collection use as id
         * an ArrayList of 3 elements Item_type(1), Ele(2), DisplayEle(3)
         * for each element
         * *  */
        if ( ((SynchronizedMap)elements).containsInternalKey( id ) )
        {
            element ele = (element)( ( SynchronizedMap ) elements ).getInternalKey( id );
            if(ele != null)
                return ( elementBean ) ele.getAsBeanXml();
        }
        return null ;
    }


}
