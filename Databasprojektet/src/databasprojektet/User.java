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

/**
 *
 * @author Simon Dahlberg and Jesper Sahlin
 */
class User
{

    private final int mId;
    private final String mName, mPassword;
    private boolean mAdmin = false;

    public User(int id, String name, String password, boolean admin)
    {
        mId = id;
        mName = name;
        mPassword = password;
        mAdmin = admin;
    }

    /**
     * @return Returns the Id.
     */
    public int GetId()
    {
        return mId;
    }

    /**
     * @return Returns the Name.
     */
    public String GetName()
    {
        return mName;
    }

    /**
     * @return the Password.
     */
    public String GetPassword()
    {
        return mPassword;
    }

    /**
     * @return Returns true if the user has admin rights, false otherwise.
     */
    public boolean IsAdmin()
    {
        return mAdmin;
    }

    @Override
    public String toString()
    {
        return GetName() + " " + GetPassword() + " " + IsAdmin();
    }

}
