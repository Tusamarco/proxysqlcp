package net.tc.isma.auth.security ;
import net.tc.isma.persister.IsmaPersister;
import java.util.Vector;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class userValidator
{
    public userValidator()
    {
    }
    private static int[] internalIp = IsmaPersister.getInternalip();

    public static UserBase validateUser(UserBase user)
    {
        Vector users =  ( Vector ) IsmaPersister.getUsers() ;
        if(users == null || users.size() <1)
            return null;
        try
        {
            if ( user instanceof Subscriber )
            {
                Subscriber subIn = ( Subscriber ) user ;
                for ( int i = 0 ; i < users.size() ; i++ )
                {
                    if ( users.get( i ) instanceof Subscriber
                         && ( ( Subscriber ) users.get( i ) ).getUsername() != null
                         &&
                         !( ( Subscriber ) users.get( i ) ).getUsername().
                         equals( "" )
                        )
                    {
                        Subscriber subValid = ( Subscriber ) users.get( i ) ;
//                        System.out.println( i + " ** usernameIN = " +
//                                            subIn.getUsername().toLowerCase() +
//                                            " ? " +
//                                            subValid.getUsername().toLowerCase() ) ;
//                        System.out.println( i + " ** passwordIn = " +
//                                            subIn.getPassword() + " ? " +
//                                            subValid.getPassword() ) ;
                        if ( subIn.getPassword().equals( subValid.getPassword() )
                             &&
                             subIn.getUsername().toLowerCase().equals( subValid.
                            getUsername().toLowerCase() )
                            )
                            return subValid ;
                    }
                }
            }
            else
            {
                UserBase subIn = ( UserBase ) user ;
                for ( int i = 0 ; i < users.size() ; i++ )
                {
                    if ( users.get( i ) instanceof UserBase )
                    {
                        UserBase subValid = ( UserBase ) users.get( i ) ;
                        if ( subIn.getPassword().equals( subValid.getPassword() )
                             &&
                             subIn.getUsername().equals( subValid.getUsername() )
                            )
                            return subValid ;
                    }
                }

            }
        }
        catch(Exception ex)
        {
            IsmaPersister.getLogSystem().error(ex);
        }
        return null;
    }
    public static synchronized  Subscriber validateByIp(String ip)
    {
        if(ip == null || ip.equals(""))
            return null;

//        int[] ipTovalidate = new int[]{0,0,0,0};

        String[] ips = ip.split("\\.");
        boolean isInternal = true;
        for(int i = 0 ; i < ips.length ; i++)
        {
            if(Integer.parseInt( ips[i] ) != internalIp[i] && internalIp[i] != 999)
            {
               isInternal = false ;
               break;
            }
        }
        if(isInternal)
        {
            UserBase ub = LoadUsers.getUserByIp( internalIp );

            if(ub != null)
            {
                ub.setAnonymous( true ) ;
                ub.setIsInternal(true);
            }
            return ( Subscriber ) ub;
        }
        return (Subscriber)LoadUsers.getUserByIp(ip);
    }




}
