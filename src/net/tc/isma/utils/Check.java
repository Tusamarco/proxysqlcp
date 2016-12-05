package net.tc.isma.utils;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;
import net.tc.isma.persister.IsmaPersister;

public class Check {
   private  JEP jep = new JEP();
   String  _expression;


//    public class EqualsIgnoreCase extends PostfixMathCommand {
//        public EqualsIgnoreCase() {
//            numberOfParameters = 2;
//        }
//
//        public void run(Stack inStack) throws ParseException {
//            // get the parameter from the stack
//            Object param1 = inStack.pop();
//            Object param2 = inStack.pop();
//
//            // check whether the argument is of the right type
//            if ( (param1 instanceof String) && (param2 instanceof String) ){
//                inStack.push( new Double( param1.toString().equalsIgnoreCase( param2.toString() ) ? 1 : 0 ) );
//            } else {
//                throw new ParseException("Invalid Parameter Type");
//            }
//        }
//    }



//    public static Check( String expression ){
//        _expression = expression;
//    }

    public boolean setCondition( Hashtable OutputParams ) {

        Enumeration keys = OutputParams.keys();
        while ( keys.hasMoreElements() ) {
            String paramName = (String)keys.nextElement();
            Object paramValue = OutputParams.get( paramName );
            jep.addVariable( paramName, paramValue );
        }

        return true;

    }

    public boolean setSingleCondition(String paramName, Object value)
    {
        if(paramName == null || paramName.equals("") || value == null )
        {
            IsmaPersister.getLogSystem().error("Jep invalid param or value");
            return false;
        }
        jep.addVariable(paramName, value);
        return true;
    }
    public boolean setExpression(String expression)
    {
        if(expression == null || expression.equals(""))
        {
            IsmaPersister.getLogSystem().error("Jep invalid expression");
            return false;
        }

        _expression = expression;

        return true;
    }

    public boolean check()
    {
//        jep.getFunctionTable().put( "EqualsIgnoreCase", new EqualsIgnoreCase() );

        try {

            if(_expression == null || _expression.endsWith("")  )
            {
                IsmaPersister.getLogSystem().debug("Jep invalid expression");
                return false;
            }

//            System.out.println( _expression + " -----------------");
            Node n = null;
            Object result = null;
            try{
                n = jep.parse(_expression);
                result = jep.evaluate( n );

            }
            catch(Throwable th)
            {
                IsmaPersister.getLogSystem().error("Jep invalid parse:" + th.getMessage());

            }

            if(result == null)
                return false;

//            System.out.println( "00000000000 [" + result + "]" );
            return ((Double)result).intValue() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occured: " + e.getMessage());
            return false;
        }


    }

}
