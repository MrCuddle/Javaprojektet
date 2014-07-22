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

    private String mName;
    private String mPassword;
    private boolean mAdmin = false;

    public User(String name, String password, boolean admin)
    {
        mName = name;
        mPassword = password;
        mAdmin = admin;
    }

    /**
     * @return the Name
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @return the Password
     */
    public String getPassword()
    {
        return mPassword;
    }

    /**
     * @return the Admin
     */
    public boolean isAdmin()
    {
        return mAdmin;
    }

}
