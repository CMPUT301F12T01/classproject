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

import android.os.AsyncTask;
import ca.ualberta.cs.c301f12t01.common.Report;
import ca.ualberta.cs.c301f12t01.common.Sharing;
import ca.ualberta.cs.c301f12t01.common.Task;
import ca.ualberta.cs.c301f12t01.util.Message;

/**
 * 
 * @author Neil Borle
 *
 */
public class ServerUpdate extends AsyncTask<Object, Void, Void>
{
	
	private ServerStorage serverStorageInstance;
	
	public ServerUpdate (ServerStorage serverStorage) {
		serverStorageInstance = serverStorage;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(Object... OArray)
	{
		
		Message newMessage = (Message) OArray[0];

		if (newMessage.getPayload() instanceof Task) {
			
			Task newTask = (Task) newMessage.getPayload();

			switch (newMessage.getAction()) {
			case ADDED:
				serverStorageInstance.storeTask(newTask);
				break;
			case REMOVED:
				serverStorageInstance.removeTask(newTask);
				break;
			case MODIFIED:
				serverStorageInstance.updateTask(newTask);
				break;
			}
		} else if (newMessage.getPayload() instanceof Report) {

			// Reports that come in may be any sharing setting
			Report newReport = (Report) newMessage.getPayload();

			if (newReport.getSharing() != Sharing.LOCAL) {
				switch (newMessage.getAction()) {
					case ADDED:
						serverStorageInstance.storeReport(newReport);
						break;
					case REMOVED:
						serverStorageInstance.removeReport(newReport);
						break;
					case MODIFIED:
						serverStorageInstance.updateReport(newReport);
						break;
				}	
			}
		}
		
		return null;
		
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void result)
	{
		
//		Context context = TaskSourceApplication.getApplicationContext();
//		CharSequence text = "Send to the server!";
//		int duration = Toast.LENGTH_SHORT;
//
//		Toast toast = Toast.makeText(context, text, duration);
//		toast.show();

		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}