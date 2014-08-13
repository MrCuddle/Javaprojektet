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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * The main application class.
 * 
 * @author Simon Dahlberg and Jesper Sahlin
 */
public class Application
{

    private final String mLookAndFeelPath = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
    private MainWindow mMainWindow;

    /**
     * This is the main application.
     */
    public Application()
    {
        Initialize();

    }

    /**
    * Sets the graphical theme for all forms.
    */
    private void Initialize()
    {
        try
        {
            UIManager.setLookAndFeel(mLookAndFeelPath);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            System.out.println(e);
        }
    }

    /**
     * Starts the application and connects to the database.
     */
    public void Run()
    {
        System.out.println("Started");
        SQLHelper.Connect();
        mMainWindow = new MainWindow();

        mMainWindow.setVisible(true);

    }

    /**
     *
     * @return the system date.
     */
    public static Date GetCurrentDate()
    {
        return new Date(System.currentTimeMillis());
    }
}
