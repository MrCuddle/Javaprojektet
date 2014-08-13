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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Simon Dahlberg and Jesper Sahlin
 */
public class AdminWindow extends javax.swing.JFrame
{

    private ArrayList<Pun> mPunList;
    private final ArrayList<Pun> mPunListShown = new ArrayList<>();
    private Pun selectedPun;
    private ArrayList<User> mUserList;
    private final MainWindow mParent;

    /**
     * Creates new form AdminPanel
     *
     * @param parent used to send data back to the main window.
     */
    public AdminWindow(MainWindow parent)
    {
        mParent = parent;
        initComponents();
        InitializePuns();
        InitializeUsers();
        InitializeSQLInfo();
        setLocationRelativeTo(mParent);
        setLocation(getLocation().x + getWidth() / 2 + mParent.getWidth() / 2, getLocation().y);
        addWindowFocusListener(new WindowFocusListener()
        {

            @Override
            public void windowGainedFocus(WindowEvent e)
            {
                InitializeUsers();
            }

            @Override
            public void windowLostFocus(WindowEvent e)
            {
                //Do nothing
            }
        });

        ActionListener categoryCbActionListener;
        categoryCbActionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String s = (String) mCategoryComboBox.getSelectedItem();
                if (s.equals("Visa alla"))
                {
                    ChangeCategoryShown();
                    mNumberOfPunsField.setText(Integer.toString(GetTotalPunCount()));
                }
                else
                {
                    ChangeCategoryShown(s);

                    mNumberOfPunsField.setText(Integer.toString(GetCategoryPunCount(s)));
                }
            }
        };

        mCategoryComboBox.addActionListener(categoryCbActionListener);

    }

    /**
     * Initializes the list of categories as well as the list of puns shown in
     * the pun list. Populates the combobox mCategorycomboBox.
     */
    private void InitializePuns()
    {
        mCategoryComboBox.addItem("Visa alla");
        ResultSet categoriesFromDb = SQLHelper.GetResultSetFromQuery("SELECT Name from category");
        try
        {
            while (categoriesFromDb.next())
            {
                mCategoryComboBox.addItem(categoriesFromDb.getString("Name"));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

        mPunList = mParent.GetPunList();
        ChangeCategoryShown();

        mNumberOfPunsField.setText(Integer.toString(GetTotalPunCount()));
    }

    /**
     * Updates the list of categories. Should be used when a new category is
     * added while this window is still active.
     */
    private void UpdateCategoryList()
    {

        ResultSet categoriesFromDb = SQLHelper.GetResultSetFromQuery("SELECT Name from category");
        try
        {
            while (categoriesFromDb.next())
            {
                mCategoryComboBox.addItem(categoriesFromDb.getString("Name"));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    /**
     * Initializes the list of users by retrieving them from the database and
     * then populates the listbox mUserList.
     *
     */
    private void InitializeUsers()
    {
        ResultSet usersFromDb = SQLHelper.GetResultSetFromQuery("SELECT * from users order by UserName");
        mUserList = new ArrayList<>();
        try
        {
            while (usersFromDb.next())
            {
                mUserList.add(new User(usersFromDb.getInt("ID"), usersFromDb.getString("UserName"), usersFromDb.getString("Password"), usersFromDb.getBoolean("IsAdmin")));
            }

        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 0; i < mUserList.size(); i++)
        {
            listModel.addElement(mUserList.get(i).GetName());
        }
        mUserWindow.setModel(listModel);
    }

    /**
     * Retrieves information about the SQL-server and the database. Lists all
     * the tables in the database.
     */
    private void InitializeSQLInfo()
    {
        mServerField.setText(SQLHelper.GetHostIP());
        mDatabaseField.setText(SQLHelper.GetHostName());
        AppendStatusWindow("Tables in Database:");
        ArrayList<String> a = SQLHelper.GetTableNames();
        for (int i = 0; i < a.size(); i++)
        {
            AppendStatusWindow(a.get(i));
        }
    }

    /**
     * Shows puns from the selected Category in mCategoryComboBox in
     * mPunListWindow.
     */
    private void ChangeCategoryShown()
    {
        mPunListShown.clear();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 0; i < mPunList.size(); i++)
        {
            mPunListShown.add(mPunList.get(i));
            listModel.addElement(mPunList.get(i).GetTitle());
        }
        mPunListWindow.setModel(listModel);
    }

    /**
     * * Shows puns from the named category.
     *
     * @param category name of the category to be shown
     */
    private void ChangeCategoryShown(String category)
    {
        mPunListShown.clear();
        for (Pun pun : mPunList)
        {
            if (category.equals(pun.GetCategory()))
            {
                mPunListShown.add(pun);
            }
        }

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Pun pun : mPunListShown)
        {
            listModel.addElement(pun.GetTitle());
        }
        mPunListWindow.setModel(listModel);
    }

    /**
     * Updates the list of puns in this class and in the parent class. Should be
     * used when a pun is modified, removed or deleted, while this window is
     * still active.
     */
    public void UpdatePuns()
    {
        mParent.UpdatePuns();
        InitializePuns();
        String s = (String) mCategoryComboBox.getSelectedItem();
        if (s.equals("Visa alla"))
        {
            ChangeCategoryShown();
        }
        else
        {
            ChangeCategoryShown(s);
        }

    }

    /**
     * Creates a window with the ShowNewCategoryDialog method, with the provided
     * message. The window prompts the user to name a new category, which is
     * then added to the database.
     *
     * @param userMessage message in the window, a message to the user.
     *
     */
    public void AddCategory(String userMessage)
    {
        String newCategory = ShowNewCategoryDialog(userMessage);
        if (newCategory == null)
        {
            return;
        }

        ResultSet rs = SQLHelper.GetResultSetFromQuery("SELECT Name FROM category");
        boolean duplicateEntry = false;
        try
        {
            while (rs.next())
            {
                if (rs.getString(1).equals(newCategory))
                {
                    duplicateEntry = true;
                    break;
                }
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(AdminWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!duplicateEntry)
        {
            SQLHelper.ExecuteUpdate("INSERT INTO category VALUES ('" + newCategory + "', 0)");
            ResultSet categoriesFromDb = SQLHelper.GetResultSetFromQuery("SELECT Name from category WHERE Name='" + newCategory + "'");
            try
            {
                if (categoriesFromDb.next())
                {
                    mCategoryComboBox.addItem(categoriesFromDb.getString("Name"));
                }
                else
                {
                    System.out.println("Något gick snett");
                }
            }
            catch (SQLException e)
            {
                System.out.println(e);
            }
        }
        else
        {
            AddCategory("Kategorin finns redan");
        }

    }

    /**
     * Shows a window which allows the user to fill in a category name. If the
     * string is empty the method calls itself until the user has provided a
     * category name or the operation is canceled.
     *
     * @param message message in the window, a message to the user.
     * @return user defined category name.
     *
     */
    private String ShowNewCategoryDialog(String message)
    {
        String res;
        res = (String) JOptionPane.showInputDialog(null, message, "Ny Kategori", JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (res == null)
        {
            return null;
        }
        else if (res.equals(""))
        {
            res = ShowNewCategoryDialog("Namnet får inte vara tomt");
        }

        return res;
    }

    /**
     * Returns the number of puns in a specified category.
     *
     * @param category the category to be counted.
     */
    private int GetCategoryPunCount(String category)
    {
        ResultSet rs = SQLHelper.GetResultSetFromQuery("select count(Category) AS NrConatining From pun where category='" + category + "'");
        try
        {
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(AdminWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;

    }

    /**
     * Return the total number of puns in the database.
     */
    private int GetTotalPunCount()
    {
        ResultSet rs = SQLHelper.GetResultSetFromQuery("SELECT COUNT(*)FROM pun");
        try
        {
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(AdminWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;

    }

    /**
     * Set the text in the text field mSQLContentWindow.
     */
    private void SetStatusWindowText(String text)
    {
        mSQLContentWindow.setText(text);
    }

    /**
     * Appends the text in the text field mSQLContentWindow.
     */
    private void AppendStatusWindow(String text)
    {
        if (!mSQLContentWindow.getText().equals(""))
        {
            mSQLContentWindow.setText(mSQLContentWindow.getText() + "\n" + text);
        }
        else
        {
            mSQLContentWindow.setText(text);
        }

    }

    /**
     * Clears the text field mSQLContentWindow.
     */
    private void EmptyStatusWindow()
    {
        mSQLContentWindow.setText("");
    }
    
     /**
     * Generates a script in the form of a .txt file with which you can reset the database to the 
     * state at the time of calling.
     */
    private void ExportDatabaseToFile()
    {
        try
        {
            File file = new File("sql.txt");
            if(!file.exists())
            {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            String content = new String();
            try 
            {
                ResultSet rs = SQLHelper.GetResultSetFromQuery("show create table category");
                if(rs.next())
                {
                    bw.write(rs.getString(2) + ";\n");
                }
                rs = SQLHelper.GetResultSetFromQuery("show create table users");
                if(rs.next())
                {
                    bw.write(rs.getString(2) + ";\n");
                }
                rs = SQLHelper.GetResultSetFromQuery("show create table pun");
                if(rs.next())
                {
                    bw.write(rs.getString(2) + ";\n");
                }
                rs = SQLHelper.GetResultSetFromQuery("show create table rating");
                if(rs.next())
                {
                    bw.write(rs.getString(2) + ";\n");
                }
                
                rs = SQLHelper.GetResultSetFromQuery("select * from category");
                while(rs.next())
                {
                    bw.write("insert into category VALUES('" + rs.getString("Name") + "'," + rs.getInt("Offensive") + ");\n");
                }
                rs = SQLHelper.GetResultSetFromQuery("select * from users");
                while(rs.next())
                {
                    bw.write("insert into users VALUES(" + rs.getInt("ID") + ",'" + rs.getString("UserName") +
                            "','" + rs.getString("Password") + "'," + rs.getInt("IsAdmin") + ");\n");
                }
                rs = SQLHelper.GetResultSetFromQuery("select * from pun");
                while(rs.next())
                {
                    bw.write("insert into pun VALUES(" + rs.getInt("ID") + ",'" + rs.getString("Content") + 
                            "','" + rs.getString("Category") + "'," + rs.getInt("Adder") + ",'" + rs.getDate("Date")
                            + "'," + rs.getInt("Offensive") + ",'" + rs.getString("Title") + "');\n");
                }
                rs = SQLHelper.GetResultSetFromQuery("select * from rating");
                while(rs.next())
                {
                    bw.write("insert into rating VALUES(" + rs.getInt("UserID") + "," + rs.getInt("PunID") + "," + rs.getInt("Rating") + ");\n");
                }
            }
            catch(SQLException e)
            {
                System.out.println(e);
            }
            bw.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTabbedPane = new javax.swing.JTabbedPane();
        pnlUser = new javax.swing.JPanel();
        mNewUserButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mUserWindow = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        mUserInfoContent = new javax.swing.JTextArea();
        mRemoveUserButton = new javax.swing.JButton();
        pnlPun = new javax.swing.JPanel();
        mNewPunButton = new javax.swing.JButton();
        mCategoryComboBox = new javax.swing.JComboBox();
        jLabelCategory = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        mPunListWindow = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        mPunContentWindow = new javax.swing.JTextArea();
        mUserLabel = new javax.swing.JLabel();
        mUserField = new javax.swing.JTextField();
        mTitleLabel = new javax.swing.JLabel();
        mTitleField = new javax.swing.JTextField();
        mDateLabel = new javax.swing.JLabel();
        mDateField = new javax.swing.JTextField();
        mDeletePunButton = new javax.swing.JButton();
        mNewCategoryButton = new javax.swing.JButton();
        mNumberOfPunsLabel = new javax.swing.JLabel();
        mNumberOfPunsField = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        mUpdatePunButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        mSQLContentWindow = new javax.swing.JTextArea();
        mSQLQueryButton = new javax.swing.JButton();
        mSQLWarningLabel = new javax.swing.JLabel();
        mServerLabel = new javax.swing.JLabel();
        mDatabaseLabel = new javax.swing.JLabel();
        mServerField = new javax.swing.JTextField();
        mDatabaseField = new javax.swing.JTextField();
        mOutputDbButton = new javax.swing.JButton();

        jLabel3.setText("Titel");

        jTextField3.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administratör");
        setResizable(false);

        mNewUserButton.setText("Lägg Till Användare");
        mNewUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mNewUserButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(mUserWindow);

        mUserInfoContent.setEditable(false);
        mUserInfoContent.setColumns(20);
        mUserInfoContent.setRows(5);
        jScrollPane2.setViewportView(mUserInfoContent);

        mRemoveUserButton.setText("Ta bort användare");
        mRemoveUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mRemoveUserButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlUserLayout = new javax.swing.GroupLayout(pnlUser);
        pnlUser.setLayout(pnlUserLayout);
        pnlUserLayout.setHorizontalGroup(
            pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUserLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mRemoveUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mNewUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );
        pnlUserLayout.setVerticalGroup(
            pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUserLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mNewUserButton)
                    .addComponent(mRemoveUserButton)))
        );

        jTabbedPane.addTab("Användare", pnlUser);

        mNewPunButton.setText("Lägg Till Skämt");
        mNewPunButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mNewPunButtonActionPerformed(evt);
            }
        });

        jLabelCategory.setText("Kategori");

        mPunListWindow.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        mPunListWindow.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                mPunListWindowValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(mPunListWindow);

        mPunContentWindow.setColumns(20);
        mPunContentWindow.setLineWrap(true);
        mPunContentWindow.setRows(5);
        mPunContentWindow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                mPunContentWindowKeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(mPunContentWindow);

        mUserLabel.setText("Användare:");

        mUserField.setEditable(false);
        mUserField.setBackground(new java.awt.Color(204, 204, 255));
        mUserField.setFocusable(false);

        mTitleLabel.setText("Titel");

        mTitleField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                mTitleFieldKeyTyped(evt);
            }
        });

        mDateLabel.setText("Datum");

        mDateField.setEditable(false);
        mDateField.setBackground(new java.awt.Color(204, 204, 255));
        mDateField.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mDateField.setFocusable(false);

        mDeletePunButton.setText("Ta Bort Skämt");
        mDeletePunButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDeletePunButtonActionPerformed(evt);
            }
        });

        mNewCategoryButton.setText("Ny Kategori");
        mNewCategoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mNewCategoryButtonActionPerformed(evt);
            }
        });

        mNumberOfPunsLabel.setText("Antal skämt i kategorin:");

        mUpdatePunButton.setText("Uppdatera");
        mUpdatePunButton.setEnabled(false);
        mUpdatePunButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mUpdatePunButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPunLayout = new javax.swing.GroupLayout(pnlPun);
        pnlPun.setLayout(pnlPunLayout);
        pnlPunLayout.setHorizontalGroup(
            pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPunLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mDeletePunButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mNewPunButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlPunLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPunLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPunLayout.createSequentialGroup()
                                .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlPunLayout.createSequentialGroup()
                                        .addComponent(mTitleLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(mTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlPunLayout.createSequentialGroup()
                                        .addComponent(mDateLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(mDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlPunLayout.createSequentialGroup()
                                        .addComponent(mUserLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(mUserField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mUpdatePunButton))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)))
                    .addGroup(pnlPunLayout.createSequentialGroup()
                        .addComponent(jLabelCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mNewCategoryButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mNumberOfPunsLabel)
                        .addGap(2, 2, 2)
                        .addComponent(mNumberOfPunsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1))
                .addContainerGap())
        );
        pnlPunLayout.setVerticalGroup(
            pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPunLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCategory)
                    .addComponent(mNewCategoryButton)
                    .addComponent(mNumberOfPunsLabel)
                    .addComponent(mNumberOfPunsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPunLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlPunLayout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(mTitleLabel)
                                    .addComponent(mTitleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(mUpdatePunButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(mDateLabel)
                                    .addComponent(mDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(mUserLabel)
                                    .addComponent(mUserField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(pnlPunLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mNewPunButton)
                    .addComponent(mDeletePunButton)))
        );

        jTabbedPane.addTab("Skämt", pnlPun);

        mSQLContentWindow.setEditable(false);
        mSQLContentWindow.setColumns(20);
        mSQLContentWindow.setRows(5);
        jScrollPane5.setViewportView(mSQLContentWindow);

        mSQLQueryButton.setText("Test SQL Command");
        mSQLQueryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSQLQueryButtonActionPerformed(evt);
            }
        });

        mSQLWarningLabel.setText("<-- Obs! Endast för avancerade användare ;)");

        mServerLabel.setText("Server");

        mDatabaseLabel.setText("Databas");

        mServerField.setEditable(false);

        mDatabaseField.setEditable(false);

        mOutputDbButton.setText("Skriv databas till fil");
        mOutputDbButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mOutputDbButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(mDatabaseLabel)
                                .addGap(2, 2, 2)
                                .addComponent(mDatabaseField, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(mServerLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(mServerField))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(mSQLQueryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mSQLWarningLabel))
                            .addComponent(mOutputDbButton))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mServerLabel)
                            .addComponent(mServerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mDatabaseLabel)
                            .addComponent(mDatabaseField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mSQLQueryButton)
                    .addComponent(mSQLWarningLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mOutputDbButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("SQL", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mNewPunButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mNewPunButtonActionPerformed
    {//GEN-HEADEREND:event_mNewPunButtonActionPerformed
        CreatePunWindow pwnd = new CreatePunWindow(mParent, this);
        pwnd.setVisible(true);
    }//GEN-LAST:event_mNewPunButtonActionPerformed

    private void mNewUserButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mNewUserButtonActionPerformed
    {//GEN-HEADEREND:event_mNewUserButtonActionPerformed
        RegistrationWindow r = new RegistrationWindow(true);
        r.setVisible(true);
    }//GEN-LAST:event_mNewUserButtonActionPerformed

    private void mRemoveUserButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mRemoveUserButtonActionPerformed
    {//GEN-HEADEREND:event_mRemoveUserButtonActionPerformed
        String s = mUserWindow.getSelectedValue().toString();

        boolean deleted = false;
        for (int i = 0; i < mUserList.size(); i++)
        {
            if (mUserList.get(i).GetName().equals(s))
            {
                int id = mUserList.get(i).GetId();
                mUserList.remove(i);
                String removeQuery = "DELETE FROM Users WHERE ID = '" + id + "';";
                SQLHelper.ExecuteUpdate(removeQuery);
                deleted = true;
                break;
            }
        }

        if (!deleted)
        {
            System.out.println("User not found and could not be deleted");
        }

        InitializeUsers();

        mUserWindow.setSelectedIndex(0);

    }//GEN-LAST:event_mRemoveUserButtonActionPerformed

    private void mPunListWindowValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_mPunListWindowValueChanged
    {//GEN-HEADEREND:event_mPunListWindowValueChanged
        if (mPunListWindow.getSelectedIndex() >= 0)
        {
            selectedPun = mPunListShown.get(mPunListWindow.getSelectedIndex());

            mPunContentWindow.setText(selectedPun.GetContent());
            mTitleField.setText(selectedPun.GetTitle());
            mDateField.setText(selectedPun.GetDate().toString());
            mUserField.setText(selectedPun.GetAdder());

            mUpdatePunButton.setEnabled(false);

        }
    }//GEN-LAST:event_mPunListWindowValueChanged

    private void mDeletePunButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mDeletePunButtonActionPerformed
    {//GEN-HEADEREND:event_mDeletePunButtonActionPerformed
        String s = mPunListWindow.getSelectedValue().toString();

        boolean deleted = false;
        for (int i = 0; i < mPunList.size(); i++)
        {
            if (mPunList.get(i).GetTitle().equals(s))
            {
                int id = mPunList.get(i).GetID();
                int selectedIndex = mPunListWindow.getSelectedIndex();

                mPunList.remove(i);
                String removeQuery = "DELETE FROM pun WHERE ID = '" + id + "';";
                SQLHelper.ExecuteUpdate(removeQuery);
                removeQuery = "DELETE FROM rating WHERE PunID = '" + id + "';";
                SQLHelper.ExecuteUpdate(removeQuery);
                deleted = true;
                if (mCategoryComboBox.getSelectedItem().toString().equals("Visa alla"))
                {
                    ChangeCategoryShown();
                }
                else
                {
                    ChangeCategoryShown((String) mCategoryComboBox.getSelectedItem());
                }

                if (selectedIndex > 0)
                {
                    mPunListWindow.setSelectedIndex(selectedIndex - 1);
                }
                else
                {
                    mPunListWindow.setSelectedIndex(0);
                }

                break;
            }
        }

        if (!deleted)
        {
            System.out.println("Pun not found and could not be deleted");
        }

        mParent.UpdatePuns();
    }//GEN-LAST:event_mDeletePunButtonActionPerformed

    private void mSQLQueryButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mSQLQueryButtonActionPerformed
    {//GEN-HEADEREND:event_mSQLQueryButtonActionPerformed
        String res = JOptionPane.showInputDialog(null, "Enter SQL Query");
        if (res == null || res.equals(""))
        {
            return;
        }
        ResultSet rs = SQLHelper.GetResultSetFromQuery(res);
        if (rs == null)
        {
            SetStatusWindowText("Error executing query '" + res + "'");

            AppendStatusWindow(SQLHelper.GetStatus());
            return;
        }

        EmptyStatusWindow();

        try
        {
            ResultSetMetaData rsmd = rs.getMetaData();
            int n = 0;
            while (rs.next())
            {
                AppendStatusWindow("-----Result" + n++ + "-----");
                for (int i = 1; i < rsmd.getColumnCount(); i++)
                {
                    String out = rs.getString(i);
                    AppendStatusWindow(rsmd.getColumnName(i) + ": " + out);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
            AppendStatusWindow(e.toString());
        }
    }//GEN-LAST:event_mSQLQueryButtonActionPerformed

    private void mNewCategoryButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mNewCategoryButtonActionPerformed
    {//GEN-HEADEREND:event_mNewCategoryButtonActionPerformed
        AddCategory("Välj en titel");

    }//GEN-LAST:event_mNewCategoryButtonActionPerformed

    private void mUpdatePunButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mUpdatePunButtonActionPerformed
    {//GEN-HEADEREND:event_mUpdatePunButtonActionPerformed
        String newContent = mPunContentWindow.getText();
        String newTitle = mTitleField.getText();
        int punId = selectedPun.GetID();
        String query = "UPDATE pun SET Content = '" + newContent + "', Title = '" + newTitle + "' WHERE ID = " + punId;

        System.out.println(query);

        SQLHelper.ExecuteUpdate(query);

        UpdatePuns();
    }//GEN-LAST:event_mUpdatePunButtonActionPerformed

    private void mPunContentWindowKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_mPunContentWindowKeyTyped
    {//GEN-HEADEREND:event_mPunContentWindowKeyTyped
        mUpdatePunButton.setEnabled(true);
    }//GEN-LAST:event_mPunContentWindowKeyTyped

    private void mTitleFieldKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_mTitleFieldKeyTyped
    {//GEN-HEADEREND:event_mTitleFieldKeyTyped
        mUpdatePunButton.setEnabled(true);
    }//GEN-LAST:event_mTitleFieldKeyTyped

    private void mOutputDbButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mOutputDbButtonActionPerformed
        ExportDatabaseToFile();
    }//GEN-LAST:event_mOutputDbButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelCategory;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JComboBox mCategoryComboBox;
    private javax.swing.JTextField mDatabaseField;
    private javax.swing.JLabel mDatabaseLabel;
    private javax.swing.JTextField mDateField;
    private javax.swing.JLabel mDateLabel;
    private javax.swing.JButton mDeletePunButton;
    private javax.swing.JButton mNewCategoryButton;
    private javax.swing.JButton mNewPunButton;
    private javax.swing.JButton mNewUserButton;
    private javax.swing.JTextField mNumberOfPunsField;
    private javax.swing.JLabel mNumberOfPunsLabel;
    private javax.swing.JButton mOutputDbButton;
    private javax.swing.JTextArea mPunContentWindow;
    private javax.swing.JList mPunListWindow;
    private javax.swing.JButton mRemoveUserButton;
    private javax.swing.JTextArea mSQLContentWindow;
    private javax.swing.JButton mSQLQueryButton;
    private javax.swing.JLabel mSQLWarningLabel;
    private javax.swing.JTextField mServerField;
    private javax.swing.JLabel mServerLabel;
    private javax.swing.JTextField mTitleField;
    private javax.swing.JLabel mTitleLabel;
    private javax.swing.JButton mUpdatePunButton;
    private javax.swing.JTextField mUserField;
    private javax.swing.JTextArea mUserInfoContent;
    private javax.swing.JLabel mUserLabel;
    private javax.swing.JList mUserWindow;
    private javax.swing.JPanel pnlPun;
    private javax.swing.JPanel pnlUser;
    // End of variables declaration//GEN-END:variables

}
