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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a pun in the database.
 * 
 * @author Simon Dahlberg and Jesper Sahlin
 */
public class Pun
{

    private final String mContent, mTitle, mCategory;
    private final int mId, mAdderId;
    private final Date mDateAdded;
    private float mRating;

    /**
     *
     * @param id
     * @param title
     * @param content
     * @param category
     * @param adder
     * @param dateAdded
     */
    public Pun(int id, String title, String content, String category, int adder, Date dateAdded)
    {
        mId = id;
        mTitle = title;
        mContent = content;
        mCategory = category;
        mAdderId = adder;
        mDateAdded = dateAdded;
    }

    /**
     *
     * @return Returns the content of the Pun object
     */
    public String GetContent()
    {
        return mContent;
    }

    /**
     * Queries the database for the user name of the adder of a specific pun based on the ID of the adder.
     * @return Returns the user name of the adder.
     */
    public String GetAdder()
    {
        String res = null;
        String query = "SELECT * FROM users WHERE id = " + mAdderId + ";";
        ResultSet rs = SQLHelper.GetResultSetFromQuery(query);
        try
        {
            if (rs.next())
            {
                res = rs.getString("UserName");
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(AdminWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    /**
     *
     * @return Returns the user name of the adder of the pun.
     */
    public int GetAdderId()
    {
        return mAdderId;
    }

    /**
     * @return Returns the date the pun was added to the database.
     */
    public Date GetDate()
    {
        return mDateAdded;
    }

    /**
     *
     * @return Returns the title of the pun.
     */
    public String GetTitle()
    {
        return mTitle;
    }

    /**
     *
     * @return Returns the category of the pun.
     */
    public String GetCategory() 
    {
        return mCategory;
    }

    /**
     *
     * @return Returns the ID of the pun.
     */
    public int GetID()
    {
        return mId;
    }

    /**
     *
     * @return Returns the rating of the pun.
     */
    public float GetRating()
    {
        return mRating;
    }

    /**
     * Sets the rating of the pun 
     * @param rating The new rating of the pun.
     */
    public void SetRating(float rating)
    {
        mRating = rating;
    }
}
