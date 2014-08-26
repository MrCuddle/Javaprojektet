/*
 * Copyright (C) 2014 Simon Dahlberg and Jesper Sahlin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package databasprojektet;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper-class for communication with the SQL-Database.
 * 
 * @author Simon Dahlberg and Jesper Sahlin
 */
public class SQLHelper
{

    private static Connection mConnection;
    private static Statement mStatement;
    private static final String mUserName = "ab4784", mUserPassword = "gruvfyllo";
    private static final String mHostIp = "195.178.232.7", mHostPort = "4040";
    private static final String mConnectionFormat = "jdbc:mysql://" + mHostIp + ":" + mHostPort + "/" + mUserName;
    private static final String mSqlHelperPrefix = "SQLHelper: ";


    /**
     * @return the IP address of the database host.
     */
    public static String GetHostIP()
    {
        return mHostIp;
    }

    /**
     * @return Returns the name of the host of the database
     */
    public static String GetHostName()
    {
        try
        {
            return mConnection.getMetaData().getUserName();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @return Returns the name of the tables in the database
     */
    public static ArrayList<String> GetTableNames()
    {
        ArrayList<String> res = new ArrayList<>();
        try
        {
            ResultSet rs = mConnection.getMetaData().getTables(null, null, "%", null);
            while (rs.next())
            {
                res.add(rs.getString(3));
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    /**
     * Makes sure that the correct database driver is present in the users system and
     * and connects to the database
     * @return Returns the boolean value true if a connection is established and false otherwise.
     */
    public static boolean Connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println(mSqlHelperPrefix + "Databas-driver hittades ej " + e);
            return false;
        }

        try
        {
            mConnection = DriverManager.getConnection(mConnectionFormat, mUserName, mUserPassword);
            mStatement = mConnection.createStatement();
        }
        catch (SQLException e)
        {
            System.out.println(mSqlHelperPrefix + "Kunde inte ansluta till databasen " + e);
            return false;
        }
        return true;
    }

    /**
     * Closes the connection to the database.
     * @return Returns true if the connection is successfully closed, false otherwise
     */
    public static boolean Disconnect()
    {
        if (mConnection != null)
        {
            try
            {
                mConnection.close();
            }
            catch (SQLException e)
            {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if mConnection exists and if mConnection.close() hasn't been called. 
     * Observe that the connection could still be broken by other means.
     * @return Returns the boolean value true if mConnection is not null and mConnection.close() hasn't been called,
     * false otherwise.
     */
        public static boolean IsConnected()
    {
        if (mConnection != null)
        {
            try
            {
                return !mConnection.isClosed();
            }
            catch (SQLException e)
            {
                System.out.println(mSqlHelperPrefix + e);
            }
        }
        return false;
    }

    /**
     * Preforms an SQL query and returns the result set.
     * @param command SQL command to be executed.
     * @return the result set received from the query above.
     */
    public static ResultSet GetResultSetFromQuery(String command)
    {
        if (mStatement != null)
        {
            try
            {
                return mStatement.executeQuery(command);
            }
            catch (SQLException e)
            {
                System.out.println(mSqlHelperPrefix + e);
                return null;
            }
        }
        System.out.println("SQLHelper: statement==null");
        return null;
    }

    /**
     * Updates an existing table. 
     * @param command The SQL query with which to update the table with.
     */
    public static void ExecuteUpdate(String command)
    {
        try
        {
            mStatement.executeUpdate(command);
        }
        catch (SQLException e)
        {
            System.out.println(mSqlHelperPrefix + e);
        }
    }

    /**
     * Executes a SQL query without returning the result set.
     * @param command the SQL query to be executed.
     */
    public static void ExecuteQuery(String command)
    {
        try
        {
            mStatement.executeQuery(command);
        }
        catch (SQLException e)
        {
            System.out.println(mSqlHelperPrefix + e);
        }
    }

    /**
     *
     * @return Returns a concatenated string consisting of the connection status, the host IP and the host port.
     */
    public static String GetStatus()
    {
        return mSqlHelperPrefix + "Connected:" + IsConnected() + " HostIP:" + mHostIp + " Port:" + mHostPort;
    }

    /**
     * Gets the row count of a specific result set.
     * @param rs the result set of which rows to count.
     * @return the number of rows in the result set.
     */
    public static int GetRowCount(ResultSet rs)
    {
        int res = 0;
        try
        {
            rs.last();
            res = rs.getRow();
            rs.beforeFirst();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }
}
