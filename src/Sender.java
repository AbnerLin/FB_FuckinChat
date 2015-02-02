import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

public class Sender {
	public static final String FB_XMPP_HOST = "chat.facebook.com";
	public static final int FB_XMPP_PORT = 5222;

	private ConnectionConfiguration config;
	private XMPPConnection connection;

	public String connect() throws XMPPException {
		config = new ConnectionConfiguration(FB_XMPP_HOST, FB_XMPP_PORT);
		SASLAuthentication.registerSASLMechanism("DIGEST-MD5",
				CustomSASLDigestMD5Mechanism.class);
		config.setSASLAuthenticationEnabled(true);
		config.setDebuggerEnabled(false);
		connection = new XMPPConnection(config);
		connection.connect();
		return connection.getConnectionID();
	}

	public void disconnect() {
		if ((connection != null) && (connection.isConnected())) {
			Presence presence = new Presence(Presence.Type.unavailable);
			presence.setStatus("offline");
			connection.disconnect(presence);
		}
	}

	public boolean login(String userName, String password) throws XMPPException {
		if ((connection != null) && (connection.isConnected())) {
			connection.login(userName, password);
			return true;
		}
		return false;
	}

	public void sendMessage(String receiverID, String text)
			throws XMPPException {
		if ((connection != null) && (connection.isConnected())) {
			ChatManager chatManager = connection.getChatManager();
			Chat chat = chatManager.createChat("-" + receiverID + "@chat.facebook.com", null);

			chat.sendMessage(text);
		}
	}

}
