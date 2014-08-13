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

    public static String GetHostURL()
    {
        try
        {
            return mConnection.getMetaData().getURL();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String GetHostIP()
    {
        return mHostIp;
    }

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

    //returnerar huruvida con finns och con.close() INTE blivit kallad
    //observera att anslutningen fortfarande kan ha avbrutits på annat sätt
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

    public static String GetStatus()
    {
        return mSqlHelperPrefix + "Connected:" + IsConnected() + " HostIP:" + mHostIp + " Port:" + mHostPort;
    }

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

    public static int GetColumnCount(ResultSet rs)
    {
        int res = 0;
        try
        {
            res = rs.getMetaData().getColumnCount();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    public static void PrintResultSet(ResultSet rs)
    {
        if (rs != null)
        {

            try
            {
                ResultSetMetaData rsmd = rs.getMetaData();
                int n = rsmd.getColumnCount();
                while (rs.next())
                {
                    for (int i = 1; i < n; i++)
                    {
                        String out = rs.getString(i);
                        System.out.println(out);
                    }

                }
            }
            catch (SQLException e)
            {
                System.out.println(e);
            }
        }
        else
        {
            System.out.println(mSqlHelperPrefix + "ResultSet==NULL");
        }

    }

}
