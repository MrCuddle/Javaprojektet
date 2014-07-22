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

    private static Connection con;
    private static Statement statement;
    private static final String userName = "ab4784", userPass = "gruvfyllo";
    private static final String hostIP = "195.178.232.7", hostPort = "4040";
    private static final String connectionFormat = "jdbc:mysql://" + hostIP + ":" + hostPort + "/" + userName;
    private static final String sqlHelperPrefix = "SQLHelper: ";

    public static String GetHostURL()
    {
        try
        {
            return con.getMetaData().getURL();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(SQLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String GetHostIP()
    {
        return hostIP;
    }

    public static String GetHostName()
    {
        try
        {
            return con.getMetaData().getUserName();
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
            ResultSet rs = con.getMetaData().getTables(null, null, "%", null);
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
            System.out.println(sqlHelperPrefix + "Databas-driver hittades ej " + e);
            return false;
        }

        try
        {
            con = DriverManager.getConnection(connectionFormat, userName, userPass);
            statement = con.createStatement();
        }
        catch (SQLException e)
        {
            System.out.println(sqlHelperPrefix + "Kunde inte ansluta till databasen " + e);
            return false;
        }

        return true;
    }

    public static boolean Disconnect()
    {
        if (con != null)
        {
            try
            {
                con.close();
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
        if (con != null)
        {
            try
            {
                return !con.isClosed();
            }
            catch (SQLException e)
            {
                System.out.println(sqlHelperPrefix + e);
            }
        }
        return false;

    }

    public static ResultSet GetResultSetFromQuery(String command)
    {
        if (statement != null)
        {
            try
            {
                return statement.executeQuery(command);
            }
            catch (SQLException e)
            {
                System.out.println(sqlHelperPrefix + e);
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
            statement.executeUpdate(command);
        }
        catch (SQLException e)
        {
            System.out.println(sqlHelperPrefix + e);
        }
    }

    public static void ExecuteQuery(String command)
    {
        try
        {
            statement.executeQuery(command);
        }
        catch (SQLException e)
        {
            System.out.println(sqlHelperPrefix + e);
        }
    }

    public static String GetStatus()
    {
        return sqlHelperPrefix + "Connected:" + IsConnected() + " HostIP:" + hostIP + " Port:" + hostPort;
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
            System.out.println(sqlHelperPrefix + "ResultSet==NULL");
        }

    }

}
