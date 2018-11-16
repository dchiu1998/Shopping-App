package com.b07.database.helper;

import com.b07.database.DatabaseAndroidHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class to serialize a database and output its contents into a file called database_copy.ser.
 *
 */
// TO DO: When passing Serialized objects between Java programs, each 
// program must have the byte code for all Objects involved available, otherwise, a 
// **ClassNotFoundException** will be thrown.
public class DatabaseSerializer {
	/**
	 * 
	 */
	// TO DO: requires parameter?
	public void serializeDatabase() {
		try {
			FileOutputStream fos = new FileOutputStream("database_copy.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			try {
				// Write each table's information to the file with a special string indicator in between tables. (advantages: can abstract to easily include future tables)
				// We chose this method of writing the database over combining different tables' information, like user details and passwords, because
				// that would require a more complicated algorithm for reading the file that would be specific to the current layout of the database and we would
				// we would have a hard time changing it if we had to.
					oos.writeBytes("*start");
					writeDbTableToFile(oos, "ROLES", "getRolesTable");
					writeDbTableToFile(oos, "USERS", "getUsersTable");
					writeDbTableToFile(oos, "USERROLE", "getUserRoleTable");
					writeDbTableToFile(oos, "USERPW", "getUserPwTable");
					writeDbTableToFile(oos, "SALES", "getSalesTable");
					writeDbTableToFile(oos, "ITEMIZEDSALES", "getItemizedSalesTable");
					writeDbTableToFile(oos, "Account", "getAccountTable");
					writeDbTableToFile(oos, "ACCOUNTSUMMARY", "getAccountSummaryTable");
					writeDbTableToFile(oos, "ITEMS", "getItemsTable");
					writeDbTableToFile(oos, "INVENTORY", "getInventoryTable");
					//End Of File
					oos.writeBytes("*eof");
			// TO DO: add exception messages?
			} catch(IOException iOExc) {
				iOExc.printStackTrace();
			} catch(IllegalAccessException iAccExc) {
				iAccExc.printStackTrace();
			} catch(IllegalArgumentException iArgExc) {
				iArgExc.printStackTrace();
			} catch(InvocationTargetException iInvTarExc) {
				iInvTarExc.printStackTrace();
			} catch(NoSuchMethodException nSMExc) {
				nSMExc.printStackTrace();
			} catch(SecurityException secExc) {
				secExc.printStackTrace();
			} catch(SQLException sQLExc) {
				sQLExc.printStackTrace();
			} finally {
				oos.close();
			}
		} catch(IOException iOExc) {
			// An I/O error has occurred while writing stream header.
			iOExc.printStackTrace();
		} catch(NullPointerException nPointerExc) {
			// The file output stream is null.
			nPointerExc.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param outputStream
	 * @param selectorMethodName
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws SQLException 
	 */
	// TO DO : make sure the writeBytes is writing on a new line each time!
	private void writeDbTableToFile(ObjectOutputStream outputStream, String tableTitle, String selectorMethodName) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		// Write the table's title
		outputStream.writeBytes(tableTitle);
		
		// Call database helper method to retrieve result set of the table in question
		Method selectorMethod = DatabaseAndroidHelper.class.getDeclaredMethod(selectorMethodName, (Class<?>[]) null);
		ResultSet resultSet = (ResultSet) selectorMethod.invoke(null, (Object[]) null);
		
		// Find the total number of columns
		int columnCount = resultSet.getMetaData().getColumnCount();
		
		// Transfer data inside result set to the file, writing the contents of each column on one line, separated by commas
		// This loop writes the contents of a column, separated by commas, into one line, then moves to the next column
		// TO DO: Determine if the object type in a database column is stored as a string all the time? Or is it sometimes an int, or other object
		for(int i = 0; i < columnCount; i++) {
			// Write the title of the column first
			outputStream.writeBytes(resultSet.getMetaData().getColumnName(i) + ", ");
			// Moves down the rows of the resultSet
			while(resultSet.next()) {
				outputStream.writeObject(i);
				outputStream.writeBytes(", ");
			}
			//End Of Line
			outputStream.writeBytes("*eol");
			//moves cursor to the first row of the resultSet
			resultSet.first();
		}
		
		// Write the indicator string signalling a separation between tables
		outputStream.writeBytes("--separate--");
	}
	
}