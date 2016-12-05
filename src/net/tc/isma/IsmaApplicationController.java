package net.tc.isma;

import java.util.Map;
import net.tc.isma.actions.generic.results;
import net.tc.isma.request.generic.requestImpl;
import net.tc.isma.actions.Executioner;
import net.tc.isma.actions.generic.executionerApplicationImpl;
import net.tc.isma.utils.SynchronizedMap;
import net.tc.isma.persister.IsmaPersister;

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
public class IsmaApplicationController {

    long threadi = 10000;

    public IsmaApplicationController() {
    }

    public static void main(String[] args) {
        IsmaApplicationController ismaapplicationcontroller = new
                IsmaApplicationController();

        ismaapplicationcontroller.doExecute(args);


    }

    public void doExecute(String[] args)
    {

        if(IsmaPersister.getConfigParameterValueString("isma_configuration.ndbmgmhost") != null)
        {
            threadi = Long.parseLong(IsmaPersister.getConfigParameterValueString(
                    "isma_configuration.monitorpooling"));
        }

        Map argsMap = new SynchronizedMap(args.length);
        for (int i = 0; i < args.length; i++) {

            if(args[i].indexOf("=") < 0)
                continue;

            String[] KeyValue = args[i].split("=");
            argsMap.put(KeyValue[0],KeyValue[1]);


        }


        results result = new results();
        requestImpl requestWrapper = new requestImpl(argsMap);
        requestWrapper.setResult(result);

        Executioner exec = new executionerApplicationImpl(requestWrapper);

        if(IsmaPersister.getConfigParameterValueString("isma_configuration.ndbmgmautostart") != null
        && IsmaPersister.getConfigParameterValueString("isma_configuration.ndbmgmautostart").equals("1")
             )
        {

            while (1 == 1) {
                try {
                    if(IsmaPersister.getConfigParameterValueString("isma_configuration.ndbmgmautostart") != null
                       && IsmaPersister.getConfigParameterValueString("isma_configuration.ndbmgmautostart").equals("0")
                       )
                    {
                        break;
                    }

                    exec.execute();
                    Thread.sleep(threadi);
                    IsmaPersister.ReloadConfiguration();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


}
