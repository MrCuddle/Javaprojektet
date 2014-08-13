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
 *
 * @author Simon Dahlberg and Jesper Sahlin
 */
public class Pun
{

    private final String mContent, mTitle, mCategory;
    private final int mId, mAdderId;
    private final float mOffensiveThreshold = 0.2f;
    private final Date mDateAdded;
    private int mNumOfOffenders = 0;
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
     * @return
     */
    public String GetContent()
    {
        return mContent;
    }

    /**
     *
     * @return
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
     * @return
     */
    public int GetAdderId()
    {
        return mAdderId;
    }

    /**
     *
     * @return
     */
    public Date GetDate()
    {
        return mDateAdded;
    }

    /**
     *
     * @return
     */
    public String GetTitle()
    {
        return mTitle;
    }

    /**
     *
     * @return
     */
    public String GetCategory()
    {
        return mCategory;
    }

    /**
     *
     * @return
     */
    public int GetID()
    {
        return mId;
    }

    /**
     *
     * @return
     */
    public float GetRating()
    {
        return mRating;
    }

    /**
     *
     * @param rating
     */
    public void SetRating(float rating)
    {
        mRating = rating;
    }

    //Bör skickas till databasen också/istället

    /**
     *
     */
        public void ThisIsOffensive()
    {
        mNumOfOffenders++;
    }

    /*Om denna bool är true så borde browsern endast visa
     den om man har markerat att man är man nog för't!*/

    /**
     *
     * @param numberOfUsers
     * @return
     */
    
    public boolean IsItOffensive(int numberOfUsers)
    {
        return mNumOfOffenders / numberOfUsers > mOffensiveThreshold;
    }
}
