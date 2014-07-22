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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowStateListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Simon Dahlberg and Jesper Sahlin
 */
public class AdminWindow extends javax.swing.JFrame
{

    private ArrayList<Pun> mPunList;
    private ArrayList<Pun> mPunListShown = new ArrayList<>();
    private ArrayList<User> mUserList;
    private final MainWindow mParent;

    /**
     * Creates new form AdminPanel
     * @param parent
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
                }
                else
                {
                    ChangeCategoryShown(s);
                }
            }
        };

        mCategoryComboBox.addActionListener(categoryCbActionListener);

    }

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
    }

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
        jListUsers.setModel(listModel);
    }

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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTabbedPane = new javax.swing.JTabbedPane();
        pnlUser = new javax.swing.JPanel();
        btnNewUser = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListUsers = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtUserInfo = new javax.swing.JTextArea();
        btnRemoveUser = new javax.swing.JButton();
        pnlPun = new javax.swing.JPanel();
        btnNewPun = new javax.swing.JButton();
        mCategoryComboBox = new javax.swing.JComboBox();
        jLabelCategory = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        mPunListWindow = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        mPunContentWindow = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        mUserContent = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        mTitleContent = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        mDateContent = new javax.swing.JTextField();
        btnDeletePun = new javax.swing.JButton();
        btnNewCategory = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        mSQLContentWindow = new javax.swing.JTextArea();
        mSQLQueryButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        mServerField = new javax.swing.JTextField();
        mDatabaseField = new javax.swing.JTextField();

        jLabel3.setText("Titel");

        jTextField3.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administratör");
        setResizable(false);

        btnNewUser.setText("Ny Användare");
        btnNewUser.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnNewUserActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jListUsers);

        txtUserInfo.setColumns(20);
        txtUserInfo.setRows(5);
        jScrollPane2.setViewportView(txtUserInfo);

        btnRemoveUser.setText("Ta bort användare");
        btnRemoveUser.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnRemoveUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlUserLayout = new javax.swing.GroupLayout(pnlUser);
        pnlUser.setLayout(pnlUserLayout);
        pnlUserLayout.setHorizontalGroup(
            pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnlUserLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnRemoveUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNewUser))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewUser)
                    .addComponent(btnRemoveUser)))
        );

        jTabbedPane.addTab("Användare", pnlUser);

        btnNewPun.setText("Nytt Skämt");
        btnNewPun.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnNewPunActionPerformed(evt);
            }
        });

        jLabelCategory.setText("Kategori");

        mPunListWindow.setModel(new javax.swing.AbstractListModel()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        mPunListWindow.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                mPunListWindowValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(mPunListWindow);

        mPunContentWindow.setColumns(20);
        mPunContentWindow.setLineWrap(true);
        mPunContentWindow.setRows(5);
        jScrollPane4.setViewportView(mPunContentWindow);

        jLabel1.setText("Användare:");

        mUserContent.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mUserContentActionPerformed(evt);
            }
        });

        jLabel2.setText("Titel");

        jLabel4.setText("Datum");

        btnDeletePun.setText("Radera Skämt");
        btnDeletePun.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnDeletePunActionPerformed(evt);
            }
        });

        btnNewCategory.setText("Ny Kategori");

        javax.swing.GroupLayout pnlPunLayout = new javax.swing.GroupLayout(pnlPun);
        pnlPun.setLayout(pnlPunLayout);
        pnlPunLayout.setHorizontalGroup(
            pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPunLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDeletePun)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNewPun, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnlPunLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPunLayout.createSequentialGroup()
                        .addComponent(jLabelCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNewCategory)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlPunLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPunLayout.createSequentialGroup()
                                .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlPunLayout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(mTitleContent, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlPunLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(mDateContent, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlPunLayout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(mUserContent, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pnlPunLayout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                                .addContainerGap())))))
        );
        pnlPunLayout.setVerticalGroup(
            pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPunLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCategory)
                    .addComponent(btnNewCategory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPunLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNewPun)
                            .addComponent(btnDeletePun)))
                    .addGroup(pnlPunLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(mTitleContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(mDateContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlPunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(mUserContent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        jTabbedPane.addTab("Skämt", pnlPun);

        mSQLContentWindow.setColumns(20);
        mSQLContentWindow.setRows(5);
        jScrollPane5.setViewportView(mSQLContentWindow);

        mSQLQueryButton.setText("Test SQL Command");
        mSQLQueryButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mSQLQueryButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("<-- Obs! Endast för avancerade användare ;)");

        jLabel6.setText("Server");

        jLabel7.setText("Databas");

        mServerField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mServerFieldActionPerformed(evt);
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
                        .addComponent(mSQLQueryButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(2, 2, 2)
                                .addComponent(mDatabaseField, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(mServerField)))))
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
                            .addComponent(jLabel6)
                            .addComponent(mServerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(mDatabaseField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mSQLQueryButton)
                    .addComponent(jLabel5))
                .addContainerGap(31, Short.MAX_VALUE))
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

    private void btnNewPunActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnNewPunActionPerformed
    {//GEN-HEADEREND:event_btnNewPunActionPerformed

    }//GEN-LAST:event_btnNewPunActionPerformed

    private void btnNewUserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnNewUserActionPerformed
    {//GEN-HEADEREND:event_btnNewUserActionPerformed
        RegistrationWindow r = new RegistrationWindow(true);
        r.setVisible(true);
    }//GEN-LAST:event_btnNewUserActionPerformed

    private void btnRemoveUserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnRemoveUserActionPerformed
    {//GEN-HEADEREND:event_btnRemoveUserActionPerformed
        String s = jListUsers.getSelectedValue().toString();

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
        
        jListUsers.setSelectedIndex(0);

    }//GEN-LAST:event_btnRemoveUserActionPerformed

    private void mUserContentActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mUserContentActionPerformed
    {//GEN-HEADEREND:event_mUserContentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mUserContentActionPerformed

    private void mPunListWindowValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_mPunListWindowValueChanged
    {//GEN-HEADEREND:event_mPunListWindowValueChanged
        if (mPunListWindow.getSelectedIndex() >= 0)
        {
            mPunContentWindow.setText(mPunListShown.get(mPunListWindow.getSelectedIndex()).GetContent());
            mTitleContent.setText(mPunListShown.get(mPunListWindow.getSelectedIndex()).GetTitle());
            mDateContent.setText(mPunListShown.get(mPunListWindow.getSelectedIndex()).GetDate().toString());
            mUserContent.setText(mPunListShown.get(mPunListWindow.getSelectedIndex()).GetAdder());
        }
    }//GEN-LAST:event_mPunListWindowValueChanged

    private void btnDeletePunActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDeletePunActionPerformed
    {//GEN-HEADEREND:event_btnDeletePunActionPerformed
        String s = mPunListWindow.getSelectedValue().toString();

        boolean deleted = false;
        for (int i = 0; i < mPunList.size(); i++)
        {
            if (mPunList.get(i).GetTitle().equals(s))
            {
                int id = mPunList.get(i).GetId();
                int selectedIndex = mPunListWindow.getSelectedIndex();
                
                mPunList.remove(i);
                String removeQuery = "DELETE FROM pun WHERE ID = '" + id + "';";
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

                if(selectedIndex > 0)
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
    }//GEN-LAST:event_btnDeletePunActionPerformed

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

    private void mServerFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_mServerFieldActionPerformed
    {//GEN-HEADEREND:event_mServerFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mServerFieldActionPerformed

    public void SetStatusWindowText(String text)
    {
        mSQLContentWindow.setText(text);
    }
    
    public void AppendStatusWindow(String text)
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
    
    public void EmptyStatusWindow()
    {
        mSQLContentWindow.setText("");
    }
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[])
//    {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try
//        {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
//            {
//                if ("Nimbus".equals(info.getName()))
//                {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        }
//        catch (ClassNotFoundException ex)
//        {
//            java.util.logging.Logger.getLogger(AdminWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        catch (InstantiationException ex)
//        {
//            java.util.logging.Logger.getLogger(AdminWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        catch (IllegalAccessException ex)
//        {
//            java.util.logging.Logger.getLogger(AdminWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        catch (javax.swing.UnsupportedLookAndFeelException ex)
//        {
//            java.util.logging.Logger.getLogger(AdminWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable()
//        {
//            public void run()
//            {
//                new AdminWindow().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeletePun;
    private javax.swing.JButton btnNewCategory;
    private javax.swing.JButton btnNewPun;
    private javax.swing.JButton btnNewUser;
    private javax.swing.JButton btnRemoveUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelCategory;
    private javax.swing.JList jListUsers;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JComboBox mCategoryComboBox;
    private javax.swing.JTextField mDatabaseField;
    private javax.swing.JTextField mDateContent;
    private javax.swing.JTextArea mPunContentWindow;
    private javax.swing.JList mPunListWindow;
    private javax.swing.JTextArea mSQLContentWindow;
    private javax.swing.JButton mSQLQueryButton;
    private javax.swing.JTextField mServerField;
    private javax.swing.JTextField mTitleContent;
    private javax.swing.JTextField mUserContent;
    private javax.swing.JPanel pnlPun;
    private javax.swing.JPanel pnlUser;
    private javax.swing.JTextArea txtUserInfo;
    // End of variables declaration//GEN-END:variables

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

}
