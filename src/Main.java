import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.jivesoftware.smack.XMPPException;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {

		Scanner in = new Scanner(System.in);

		System.out.print("Enter your user name: ");
		String userName = in.next();

		System.out.print("Enter your password: ");
		String passWord = in.next();

		Sender app = new Sender();

		try {
			app.connect();
			if (!app.login(userName, passWord)) {
				System.err.println("Access Denied...");
				System.exit(1);
			}

			System.out.print("Enter your friend's userId:");
			String userId = in.next();
			System.out.print("Say something...:");

			String text = readInput();

			System.out.print("how many time would you want to send ?");
			int _time = in.nextInt();

			for (int i = 0; i < _time; i++) {
				app.sendMessage(userId, text + " " + i);
				System.out.println("Sending..." + (i + 1));
				Thread.sleep(1500);
			}
			
			System.exit(1);

		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String readInput() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();
	}

}
