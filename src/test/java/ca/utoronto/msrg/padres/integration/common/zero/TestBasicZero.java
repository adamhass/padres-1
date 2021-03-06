package ca.utoronto.msrg.padres.integration.common.zero;

import ca.utoronto.msrg.padres.broker.brokercore.BrokerConfig;
import ca.utoronto.msrg.padres.broker.brokercore.BrokerCore;
import ca.utoronto.msrg.padres.broker.brokercore.BrokerCoreException;
import ca.utoronto.msrg.padres.client.Client;
import ca.utoronto.msrg.padres.client.ClientConfig;
import ca.utoronto.msrg.padres.client.ClientException;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by chris on 25.09.15.
 */
public class TestBasicZero extends TestCase  {

    //@Test
    public void broker_with_zero_should_initialize() throws BrokerCoreException {
        BrokerConfig cfg = new BrokerConfig();
        cfg.setBrokerURI("zero-tcp://127.0.0.1:5555/broker1");

        BrokerCore bc = new BrokerCore(cfg);
        bc.initialize();
    }

    //@Test
    public void client_should_connect() throws ClientException {
        ClientConfig cc = new ClientConfig();
        //cc.connectBrokerList.add("zero-tcp://127.0.0.1:5555/broker1");

        Client cl = new Client(cc);
        cl.connect("zero-tcp://127.0.0.1:5555/broker1");
    }
}
