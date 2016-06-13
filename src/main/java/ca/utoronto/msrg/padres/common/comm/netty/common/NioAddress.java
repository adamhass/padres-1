package ca.utoronto.msrg.padres.common.comm.netty.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.utoronto.msrg.padres.common.comm.CommunicationException;
import ca.utoronto.msrg.padres.common.comm.NodeAddress;
import ca.utoronto.msrg.padres.common.comm.CommSystem.CommSystemType;

public class NioAddress implements NodeAddress{

	public static final String NIO_REG_EXP = "nio://([^:/]+)(:(\\d+))?/(.+)";
	private final CommSystemType type;
    private String host;
    private int port;
    private String remoteID;
    
	public NioAddress(String nodeURI) throws CommunicationException {
		type = CommSystemType.NIO;
		parseURI(nodeURI);
	}

	void parseURI(String nodeURI) throws CommunicationException {
		// set the default values
		host = "localhost";
		port = 1099;
		remoteID = null;
		// get the actual values from the input string
		Matcher nioMatcher = Pattern.compile(NIO_REG_EXP).matcher(nodeURI);
		if (nioMatcher.find()) {
			host = nioMatcher.group(1);
			if (nioMatcher.group(3) != null) {
				port = Integer.parseInt(nioMatcher.group(3));
			}
			remoteID = nioMatcher.group(4);
		} else {
			throw new CommunicationException("Malformed remote broker socket URI: " + nodeURI);
		}
	}


	@Override
	public boolean equals(Object o) {
		if (!(o instanceof NioAddress))
			return false;
		NioAddress tempAddr = (NioAddress) o;
		if (tempAddr.host.equals(host) && tempAddr.port == port)
			return true;
		return false;
	}

	public boolean isEqual(String checkURI) throws CommunicationException {
		try {
			NioAddress checkAddr = new NioAddress(checkURI);
			InetAddress checkHost = InetAddress.getByName(checkAddr.host);
			checkURI = String.format("nio://%s:%d/%s", checkHost.getHostAddress(),
					checkAddr.port, checkAddr.remoteID);
			InetAddress thisHost = InetAddress.getByName(checkAddr.host);
			String thisURI = String.format("nio://%s:%d/%s", thisHost.getHostAddress(), port,
					remoteID);
			return thisURI.equalsIgnoreCase(checkURI);
		} catch (UnknownHostException e) {
			throw new CommunicationException(e);
		}
	}

	public String toString() {
		return String.format("nio://%s:%d/%s", host, port, remoteID);
	}

	@Override
	public CommSystemType getType() {
		return this.type;
	}

	@Override
    public String getNodeURI() {
        return this.toString();
    }

    @Override
    public String getNodeID() {
        return this.remoteID;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public int getPort() {
        return this.port;
    }
}