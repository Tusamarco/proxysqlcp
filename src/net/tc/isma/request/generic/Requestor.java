package net.tc.isma.request.generic ;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
public interface  Requestor
{
    /**
     * setRequest(Servlet request)
     */
    public void setRequest( HttpServletRequest request );
    public HttpServletRequest getRequest( int i );
    public void removeRequest(int i);
    public int size(  );
    public void clear();
    public void moveDown(int idx);
    public void moveUp(int idx);



}
