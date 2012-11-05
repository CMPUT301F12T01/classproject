package ca.ualberta.cs.c301f12t01.testing;

import android.test.AndroidTestCase;
import ca.ualberta.cs.c301f12t01.localStorage.DeviceStorage;

/**
 * Test class from the DatabaseTesting android project
 * 
 * This class just tests the basic functionality of 
 * opening and closing a database connection.
 * 
 * IMPORTANT!!!!!!
 * Because this class is from another project for testing
 * it should not run. It is only here to demonstrate that
 * testing was performed for the database
 * 
 * @author nborle
 *
 */

public class DeviceStorageTest extends AndroidTestCase
{
	public void test_get_database()
	{
		DeviceStorage ds = new DeviceStorage(getContext());
		ds.open();
		
		assertNotNull(ds);
		
		ds.close();
	}

}
