package net.tc.mysql.cluster.cp.objects;

import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.utils.SynchronizedMap;
import java.util.Map;
import net.tc.isma.utils.Text;
import net.tc.isma.utils.Check;
/**
 * <p>Title: NDBJ / API</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Marco Tusa Copyright (c) 2008</p>
 *
 * <p>Company: MySQL</p>
 *
 * @author Marco Tusa
 * @version 1.0
 */
public class EventDispatcher {

    /**
     * This class is the link between the generated events and the Listner handling them
     *
     */
    private static Check check = new Check();

    public EventDispatcher() {
    }

    public static void Dispatch(String CategoryListnerName, ListnerEvent event, String className )
    {
        Check check = new Check();

        Map CategoryListners  = (Map)IsmaPersister.get(SynchronizedMap.class, "NDBMGMListeners");

        if(CategoryListners != null && CategoryListners.size() > 0)
        {
            try{

                Map listners = (Map) ((CategoryListner) CategoryListners.get(
                        CategoryListnerName.toLowerCase())).getListners();

                if (listners != null && listners.size() > 0) {
                    ListnerHandler lh = (ListnerHandler) listners.get(className);

                    /**
                     * Add the last two events to temp area.
                     * for trapping
                     */
                    ListnerEvent lev1 = null;
                    if(lh.getListnerEvents() != null
                       && lh.getListnerEvents().values() != null
                       && lh.getListnerEvents().values().size() > 0)
                    {
                        lev1 = (ListnerEvent)lh.getListnerEvents().values().toArray()[lh.getListnerEvents().values().size() - 1];
                    }
                    IsmaPersister.setApplicationVariable(className,new Object[]{lev1,event});


                    /**
                     * check the event for status and value
                     */
                    if (lh != null && event.getStatus()==0) {
                        checkEventStatus(lh,event);
                    }
                    else if(lh != null && event.getStatus() != 0 )
                    {
                        lh.setStatus(event.getStatus());
                        lh.setEvent(event.getSystemTime(),event);
                    }
                }

            }
            catch(Exception ex)
            {
                IsmaPersister.getLogByName("NDBMGMSYSTEM").error(ex);
            }


        }


    }
    /**
     * this method checks if the event match a specified condition
     * and set the status level ok - warning - error
     * at event level and Listner
     *
     */
    private static ListnerHandler checkEventStatus(ListnerHandler lh, ListnerEvent event )
    {



        if(
                (lh.getValue_ok() != null
                 && lh.getValue_warning() !=null
                 && lh.getValue_error() !=null
                )
                &&
                lh.getValue_ok() instanceof Long
          )
        {

            if(event.getChekableValue()!= null && Text.isNumeric(event.getCurrentvalue()))
            {
                long ok = ((Long)lh.getValue_ok()).longValue();
                long warning = ((Long)lh.getValue_warning()).longValue();
                long error = ((Long)lh.getValue_error()).longValue();


                long current =   ((Long)event.getChekableValue()).longValue();
                if (current <= ok)
                {
                    lh.setStatus(0);
                } else if (current > ok && current <= warning )
                {
                    lh.setStatus(1);
                }
                else if (current > warning && current >= error )
                {
                    lh.setStatus(2);
                }
                event.setStatus(lh.getStatus());
                lh.setEvent(event.getSystemTime(),event);

            }



        }
        else if (
                 lh.getValue_ok() != null
                 && lh.getValue_warning() !=null
                 && lh.getValue_error() !=null)
        {

            check.setSingleCondition("curval",event.getChekableValue());
            /**
             * check for ok by expression
             */

            if(check.setExpression((String)lh.getValue_ok()) && check.check() && lh.getStatusOverride(0))
            {
                lh.setStatus(0);
            }
            /**
             * check for warning by expression
             */
            else if(check.setExpression((String)lh.getValue_warning()) && check.check() && lh.getStatusOverride(1))
            {
                lh.setStatus(1);
            }
            /**
             * check for error by expression
             */
            else if(check.setExpression((String)lh.getValue_error()) && check.check() && lh.getStatusOverride(2))
            {
                lh.setStatus(2);
            }

            event.setStatus(lh.getStatus());
            lh.setEvent(event.getSystemTime(),event);

        }


        return lh;
    }
}
