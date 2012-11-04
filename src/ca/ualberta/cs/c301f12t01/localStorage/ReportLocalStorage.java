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
package ca.ualberta.cs.c301f12t01.localStorage;

import java.util.ArrayList;
import java.util.UUID;

import android.database.sqlite.SQLiteDatabase;
import ca.ualberta.cs.c301f12t01.common.Report;


/**
 * This class is responsible for decomposing 
 * responses by attributes to be stored into 
 * the database as well as reconstructing
 * the Reports from the database.
 * 
 * This includes assembling the Responses
 * associated with each Report and 
 * disassembling those Responses
 * 
 * @author Neil Borle
 *
 */
public class ReportLocalStorage
{
	public static void storeReport(SQLiteDatabase db, Report reportToStore) {
		
	}
	
	public static ArrayList<Report> getReports(SQLiteDatabase db, UUID taskid, boolean global) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
