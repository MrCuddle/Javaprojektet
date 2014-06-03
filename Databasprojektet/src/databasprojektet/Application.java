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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Simon Dahlberg and Jesper Sahlin
 */
public class Application{
    private final String lookAndFeelPath = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
    private MainWindow mainWindow;
    
    
    public Application()
    {
        Initialize();
        
    }

    private void Initialize() {
        try
        {
            UIManager.setLookAndFeel(lookAndFeelPath);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }
    }
    
    public void Run()
    {
        System.out.println("Started");        
        
        ConnectToDatabase();
        mainWindow = new MainWindow();
        
        mainWindow.setVisible(true);
        
    }

    private void ConnectToDatabase()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                SQLHelper.Connect();
                mainWindow.AppendStatusWindow(SQLHelper.GetStatus());
                try {
                   ResultSet rs = SQLHelper.GetResultSetFromQuery("Select Content from pun");
                   while(rs.next())
                   {
                        String outputPun = rs.getString("Content");
                        mainWindow.AppendStatusWindow(outputPun);
                   } 
                }
                   catch (SQLException e) {
                   System.out.println(e);
                }
            }
        }.start();
    }
}
