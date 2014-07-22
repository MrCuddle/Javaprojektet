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

/**
 *
 * @author Simpa
 */
public class Pun {
    
    private String mContent, mAdder, mTitle, mCategory;
    private int mNumOfOffenders, mId;
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
    
    public String GetContent()
    {
        return mContent;
    }
    
    public String GetAdder()
    {
        return mAdder;
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
