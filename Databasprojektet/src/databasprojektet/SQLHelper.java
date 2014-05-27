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

/**
 *
 * @author Simon Dahlberg and Jesper Sahlin
 */
public class SQLHelper {
    private static Connection con;
    private static Statement statement;
    private static String userName = "ab4784", userPass = "gruvfyllo";
    private static final String hostIP = "195.178.232.7", hostPort = "4040";
    private static final String connectionFormat = "jdbc:mysql://" + hostIP + ":" + hostPort + "/" + userName;
    private static String status;
    
    
    public static boolean Connect()
    {
        System.out.println(SQLCMD.SelectFrom("Name, Date", "user"));
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
        } 
        catch (ClassNotFoundException e) 
        {
            status = "Databas-driver hittades ej " + e;
            System.out.println(status);
            return false;
        }
        
        try 
        {
                con = DriverManager.getConnection(connectionFormat,userName,userPass);
                statement = con.createStatement();
        } 
        catch (SQLException e) 
        {
                status = "Kunde inte ansluta till databasen " + e;
                System.out.println(status);
                return false;
        } 
        
        status = "Ansluten till databasen";
        return true;
    }
    
    public static boolean Disconnect()
    {
        if(con != null)
        {
            try 
            {
                con.close();
                status = "Ej ansluten";
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
        if(con != null)
        {
            try 
            {
                return !con.isClosed();
            }
            catch (SQLException e) 
            {
                System.out.println(e);
            }
        }       
        return false;
        
    }
    
    public static void SendSQLQuery(String comman)
    {
        if(IsConnected())
        {
            ///Do SQL
            
        }
    }
    
    public static ResultSet ExecuteSQL(String command)
    {
        try
        {
            statement = con.createStatement();
            return statement.executeQuery(command);
        }
        catch(SQLException e)
        {
            System.out.println(e);
            return null;
        }
    }
    
    public static String GetStatus()
    {
        return status;
    }
    
    private static class SQLCMD
    {
        private static String SelectAll(String table){return "SELECT * FROM " + table;}
        private static String SelectFrom(String values, String table){return "SELECT " + values + " FROM " + table;}
    }
    
}

