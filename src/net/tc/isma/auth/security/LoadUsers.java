package net.tc.isma.auth.security ;

import java.io.* ;
import java.util.* ;

import jcifs.smb.SmbFile ;
import net.tc.isma.persister.IsmaPersister ;
import net.tc.isma.resources.ConfigResource ;
import net.tc.isma.utils.file.FileHandler ;

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
public class LoadUsers
{
    private static String userFilePathSrc = null;
    private static String userFilePathDest = null;
    private static String userFilePathOld = null;

    public LoadUsers()
    {
    }

    public static synchronized void loadFile( Map configMap )
    {

        ConfigResource param1 = null;
        param1 = ( ConfigResource ) configMap.get("isma_configuration.webusers_src" ) ;
        userFilePathSrc = param1.getStringValue();


        param1 = ( ConfigResource ) configMap.get("isma_configuration.webusers_bkup" ) ;
        userFilePathOld = IsmaPersister.getMAINROOT() + param1.getStringValue() ;

        param1 = ( ConfigResource ) configMap.get("isma_configuration.webusers_dest" ) ;
        userFilePathDest = IsmaPersister.getMAINROOT() + param1.getStringValue() ;

        if(copyUsersFile())
        {
            IsmaPersister.getLogSystem().info("User file Succesfully moved from " + userFilePathSrc  + " to "  + userFilePathDest);
        }
        else
        {
            IsmaPersister.getLogSystem().error("User file not moved from " + userFilePathSrc  + " to "  + userFilePathDest + " USING PREVIOUS version");
        }
        try
        {
            File f = new File( userFilePathDest ) ;
            if ( !f.exists() )
                return ;

            FileReader fr = new FileReader( f ) ;
            IsmaPersister.setUsers( readUsersFile( fr ) ) ;
            fr.close() ;
            fr = null ;
            f = null ;

        }
        catch ( Exception ex )
        {
            IsmaPersister.getLogSystem().error( "" , ex ) ;
        }

    }
    public static synchronized void reloadUsers()
    {
        if(userFilePathDest != null && !userFilePathDest.equals(""))
        {
            try
            {
                File f = new File( userFilePathDest ) ;
                if ( !f.exists() )
                    return ;

                FileReader fr = new FileReader( f ) ;
                IsmaPersister.setUsers( readUsersFile( fr ) ) ;
                fr.close() ;
                fr = null ;
                f = null ;

            }
            catch ( Exception ex )
            {
                IsmaPersister.getLogSystem().error( "" , ex ) ;
        }
        }

    }

    private static synchronized Vector readUsersFile( FileReader fr )
    {
        try{
            Vector users = new Vector( 0 ) ;
            LineNumberReader r = new LineNumberReader( fr ) ;
            String line ;
            String tabValue = "\t" ;
            Hashtable cols = new Hashtable(0) ; // Key = ColumnName - Value = NumberOfColumn
            // We suppose that the first line of file is the line of name of columns
            if ( ( line = r.readLine() ) != null )
            {
                // Here we fill up the cols Hastable
                StringTokenizer st = new StringTokenizer( line , tabValue ) ;
                int counter = 0 ;
                while ( st.hasMoreTokens() )
                {
                    cols.put( st.nextToken() , new Integer( counter ) ) ;
                    counter++ ;
                }
            }


            while ( ( line = r.readLine() ) != null )
            {
                Subscriber subscrUser = new Subscriber() ;
                StringTokenizer st = new StringTokenizer( line , tabValue , true ) ;
                Vector tokens = new Vector() ;
                line = line.replaceAll("\\t","\\\t ");
                String[] as = line.split("\\t");

//                String previousToken = tabValue ;
//                while ( st.hasMoreTokens() )
//                {
//                    String tok = st.nextToken() ;
//                    if ( tok.equals( tabValue ) )
//                    {
//                        if ( previousToken.equals( tabValue ) ) //StringTokenizer don't read a empty value of a column
//                        {
//                            tokens.addElement( "" ) ;
//                        }
//                        else
//                        {
//                            previousToken = tabValue ;
//                        }
//                    }
//                    else
//                    {
//                        tokens.addElement( tok ) ;
//                        previousToken = "" ;
//                    }
//                }
//
//                if ( previousToken.equals( tabValue ) ) //The last column of this record is empty and StringTokenizer don't read it
//                {
//                    tokens.addElement( "" ) ;
//                }

                String IP = "";
                String name = "";
                String groups = "";
                String limit = "";
                String userName = "";
                String psw = "";
                String email = "";
                try{
//                    IP = ( String ) tokens.elementAt( ( ( Integer ) cols.get("IP ADDRESS" ) ).intValue() ) ;
//                    name = ( String ) tokens.elementAt( ( ( Integer ) cols.get( "NAME" ) ).intValue() ) ;
//                    groups = ( String ) tokens.elementAt( ( ( Integer ) cols.get("AUTH GROUP" ) ).intValue() ) ;
//                    limit = ( String ) tokens.elementAt( ( ( Integer ) cols.get("AUTH LIMIT" ) ).intValue() ) ;
//                    userName = ( String ) tokens.elementAt( ( ( Integer ) cols.get("USERNAME" ) ).intValue() ) ;
//                    psw = ( String ) tokens.elementAt( ( ( Integer ) cols.get("PASSWORD" ) ).intValue() ) ;
//                    email = ( String ) tokens.elementAt( ( ( Integer ) cols.get( "EMAIL" ) ).intValue() ) ;
                    IP = ( String ) as[( ( Integer ) cols.get("IP ADDRESS" ) ).intValue() ] ;
                    name = ( String ) as[ ( ( Integer ) cols.get( "NAME" ) ).intValue() ] ;
                    groups = ( String ) as[ ( ( Integer ) cols.get("AUTH GROUP" ) ).intValue() ] ;
                    limit = (( String ) as[ ( ( Integer ) cols.get("AUTH LIMIT" ) ).intValue() ]).trim() ;
                    userName = ( String ) as[ ( ( Integer ) cols.get("USERNAME" ) ).intValue() ] ;
                    psw = ( String ) as[( ( Integer ) cols.get("PASSWORD" ) ).intValue() ] ;
                    email = ( String ) as[( ( Integer ) cols.get( "EMAIL" ) ).intValue() ] ;

                }
                catch(Exception ex)
                {
                    System.out.println(name);
//                    ex.printStackTrace();
                }
                subscrUser.setName( name.trim() ) ;
                subscrUser.setUsername( userName.trim() ) ;
                subscrUser.setPassword( psw.trim() ) ;
                subscrUser.setIp( IP.trim() ) ;
                subscrUser.setEmail(email.trim());

                if ( limit != null && !limit.equals( "" ) && !limit.equals( " " ) )
                {
                    try{
                        subscrUser.setLimit( Integer.parseInt( limit ) ) ;
                    }
                    catch(NumberFormatException eex)
                    {
                        subscrUser.setLimit( 5000);
                    }
                }
                subscrUser.setGroupString( groups ) ;

                users.add(subscrUser);
//            if ( name != null && name.length() > 0 )
//            {
//                StringTokenizer groupTok = new StringTokenizer( groups , "," , true ) ;
//                try
//                {
//                    int limitValue = limit.equals( "" ) ? 1000000000 :
//                                     Integer.parseInt( limit ) ;
//                    if ( "*".equals( IP ) &&
//                         ( userName == null || userName.length() == 0 ) &&
//                         limitValue > 500 )
//                    {
//                        System.err.println( "invalid webusers line ignored: " +
//                                            line ) ;
//                    }
//                    else
//                    {
//                        User user = new User( IP , userName , limitValue , psw ,
//                                              name ) ;
//                        users.add( user ) ;
//                        // Only For Debug. logger.finest(user.toString());
//                    }
//                }
//                catch ( NumberFormatException e )
//                {
//                    System.err.println( "bad limit number: " + line ) ;
//                }
//            }
            }
            return users;
        }
        catch(Exception ex)
        {IsmaPersister.getLogSystem().error("",ex);}
        return null ;
    }
    private static synchronized boolean copyUsersFile()
    {
        if(
        userFilePathSrc != null && !userFilePathSrc.equals("") &&
//        userFilePathDest != null && !userFilePathDest.equals("") &&
        userFilePathOld != null &&  !userFilePathOld.equals("")
        )
        {

            try
            {

                System.out.println( "--------- " +new Date( System.currentTimeMillis() ).toString() + " -- " + userFilePathSrc ) ;

                File toFile = new File( userFilePathDest ) ;
                File bckFile = new File( userFilePathOld ) ;


                SmbFile inputDir = new SmbFile( userFilePathSrc ) ;
                if ( inputDir.exists() )
                {
                    FileHandler fh = new FileHandler(toFile,bckFile);
                    if(fh.copyFile())
                    {
                        fh.deleteFile();
                        fh.setOut(toFile);
                        fh.setIns(inputDir.getInputStream());
                        if(fh.copyFileStream())
                        {
                            fh.close();
                            fh = null;
                            return true;
                            //logger.info("File "+toFile.toString()+" updated!");

                        }
                        else
                        {
                            IsmaPersister.getLogSystem().error("Impossible copy file " +toFile.toString() +" to backup file: " + bckFile.toString()
                                                +". So the file " +toFile.toString() +" was not updated!!!" ) ;
                            return false;
                        }
                    }
                }
                return false;

            }
            catch ( Exception e )
            {
                System.err.println( "File for WEBUSERS was not updated!!! " +
                                    e.getMessage() ) ;
                return false;
            }
        }
        return false;
    }
    public static UserBase getUserByUserName(String userName)
    {
        if(IsmaPersister.getUsers() == null)
            reloadUsers();

        Vector users = IsmaPersister.getUsers();

        for(int i = 0 ; i < users.size() ; i++)
        {
          if(((UserBase)users.get(i)).getUsername().toLowerCase().equals(userName.toLowerCase()))
              return (UserBase)users.get(i);
        }
        return null;
    }
    public static UserBase getUserByIp(String ip)
    {
        String[] ips = ip.split("\\.");
        int[] ipToFind = new int[]{0,0,0,0};

        for(int i = 0 ; i < ips.length ; i++)
        {
            ipToFind[i] = Integer.parseInt(ips[i]);
        }
        return getUserByIp(ipToFind);
    }

    public static UserBase getUserByIp(int[] ipToFind)
    {
        if(IsmaPersister.getUsers() == null)
            reloadUsers();

        Vector users = IsmaPersister.getUsers();


        for(int i = 0 ; i < users.size() ; i++)
        {
            UserBase ub = (UserBase)users.get(i);
            int[] ubIp = ub.getIp();

            boolean isMatching = matchIp(ipToFind,ubIp);
            if(isMatching)
                return ub;
        }


        return null;
    }

    /**
     * matchIp
     *
     * @param ipToFind int[]
     * @param ubIp int[]
     * @return boolean
     */
    private static boolean matchIp( int[] ipToFind , int[] ipMask )
    {
        boolean match = false;
        boolean[] ipMatch = new boolean[]{false,false,false,false};
        for(int i = 0 ; i < ipToFind.length ; i++)
        {
            if(i < 3)
            {
                int ip2F = ipToFind[i] ;
                int ipM = ipMask[i] ;
                if(ip2F == ipM || ipM == 999)
                    ipMatch[i] = true;
            }
            else if(i >= 3 && ipMask[4] != 0)
            {
                int ip2F = ipToFind[i] ;
                if(ip2F >= ipToFind[3] && ip2F <= ipToFind[4])
                    ipMatch[i] = true;
            }
            else
            {
                int ip2F = ipToFind[i] ;
                int ipM = ipMask[i] ;
                if(ip2F == ipM || ipM == 999)
                    ipMatch[i] = true;

            }

        }

        for(int i = 0 ; i < ipMatch.length ; i++)
        {
            match = ipMatch[i];
            if(!match)
                return false;
        }


        return match ;
    }

}
