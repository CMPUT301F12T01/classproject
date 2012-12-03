/**
 * Copyright 2012 Neil Borle, Mitchell Home, Bronte Lee, Aaron
 * Padlesky, Eddie Santos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ca.ualberta.cs.c301f12t01.serverStorage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * 
 * @author home
 * 
 */
public class Server {

	private static final String serverName = "http://crowdsourcer.softwareprocess.es/F12/CMPUT301F12T01/?";

	public void get(String args) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(serverName + args);
		try {
			HttpResponse response1 = httpclient.execute(httpGet);
			// System.out.println(response1.getStatusLine());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param nvp
	 * 			NameValuePair to post to the server
	 * @return
	 * 			The json formatted string we got back
	 */
	public String post(List<BasicNameValuePair> nvp) {
		String jsonStringVersion = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpGet = new HttpPost(serverName);
		try {
			httpGet.setEntity(new UrlEncodedFormEntity(nvp));
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				//System.out.println(response.getStatusLine());
				InputStream is = entity.getContent();
				jsonStringVersion = ServerUtils.convertStreamToString(is);
			}

			return jsonStringVersion;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null; //hopefully we never get here
	}

}
