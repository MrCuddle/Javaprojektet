/*
 * Copyright (C) 2014 Simpa
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
 * @author Simpa
 */
public class Pun {
    
    private String mContent, mAdder, mTitle, mCategory;
    private int mNumOfOffenders, mId, mAdderId;
    private final float mOffensiveThreshold = 0.2f;
    private Date mDateAdded;
    
    //För ny pun
    public Pun(String content, String adder, Date dateAdded)
    {
        mContent = content;
        mAdder = adder;
        mDateAdded = dateAdded;
    }
    
    public Pun(String content, String title, String category, int id)
    {
        mContent = content;
        mTitle = title;
        mCategory = category;
        mId = id;
    }
    
    public Pun(String content, String adder, Date dateAdded, int numOfOffenders)
    {
        mContent = content;
        mAdder = adder;
        mDateAdded = dateAdded;
        mNumOfOffenders = numOfOffenders;
    }
    
    public Pun(int id, String title, String content, String category, int adder, Date dateAdded)
    {
        mId = id;
        mTitle = title;
        mContent = content;
        mCategory = category;
        mAdderId = adder;
        mDateAdded = dateAdded;
    }
    
    public String GetContent()
    {
        return mContent;
    }
    
    public String GetAdder()
    {
        String res = null;
        String query = "SELECT * FROM users WHERE id = " + mAdderId + ";";
        ResultSet rs = SQLHelper.GetResultSetFromQuery(query);
        try
        {
            if(rs.next())
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
    
    public int GetId()
    {
        return mId;
    }
    
    public int GetAdderId()
    {
        return mAdderId;
    }
    
    public Date GetDate()
    {
        return mDateAdded;
    }
    
    public String GetTitle()
    {
        return mTitle;
    }
    
    public String GetCategory()
    {
        return mCategory;
    }
    
    public int GetID()
    {
        return mId;
    }
    //Bör skickas till databasen också/istället
    public void ThisIsOffensive()
    {
        mNumOfOffenders++;
    }
    
    /*Om denna bool är true så borde browsern endast visa
    den om man har markerat att man är man nog för't!*/
    public boolean IsItOffensive(int numberOfUsers)
    {
        return mNumOfOffenders / numberOfUsers > mOffensiveThreshold;
    }
}
