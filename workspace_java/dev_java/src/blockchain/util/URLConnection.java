package blockchain.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLConnection {
	// 싱글톤
	private URLConnection() {
	}

	private static class LazyHolder {
		public static final URLConnection INSTANCE = new URLConnection();
	}

	public static URLConnection getInstance() {
		return LazyHolder.INSTANCE;
	}

	private static BufferedReader in = null;

	public void connectAddBlock() {
		try {

			// 호출할 url
			URL url = new URL("http://192.168.0.211:8000/chain/addBlock.ftbc");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
}