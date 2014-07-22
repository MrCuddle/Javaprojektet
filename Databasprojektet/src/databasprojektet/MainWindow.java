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

import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.List;
import javax.swing.DefaultListModel;
=======
>>>>>>> origin/master
import javax.swing.JOptionPane;

/**
 *
 * @author Simon Dahlberg and Jesper Sahlin
 */
public class MainWindow extends javax.swing.JFrame
{
    
    private String userName = "Gäst";
    private String welcomeMsg = "Välkommen ";
<<<<<<< HEAD
    ArrayList<Pun> mPunList = new ArrayList<Pun>();
=======
    private User mActiveUser = null;
>>>>>>> origin/master

    /**
     * Creates new form MainWindow
     */
    public MainWindow()
    {
        initComponents();
        lblMsg.setText(welcomeMsg + userName + "!");
<<<<<<< HEAD
        InitializeCategories();
        InitializePuns();
    }
    
    private void InitializeCategories()
    {
        mCategoryComboBox.addItem("Visa alla");
        ResultSet categoriesFromDb = SQLHelper.GetResultSetFromQuery("SELECT Name from category");
        try
        {
        while(categoriesFromDb.next())
            {
                mCategoryComboBox.addItem(categoriesFromDb.getString("Name"));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
=======

>>>>>>> origin/master
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtStatus = new javax.swing.JEditorPane();
        btnTestSQL = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        mCategoryComboBox = new javax.swing.JComboBox();
        jLabelCategory = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        mPunContentWindow = new javax.swing.JTextArea();
        lblMsg = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        mPunListWindow = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hej");
        setName("MainWindow"); // NOI18N
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jButton2.setText("Login");
        jButton2.setName("btnLogin"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2))
        );

        txtStatus.setEditable(false);
        txtStatus.setName("txtStatus"); // NOI18N
        jScrollPane1.setViewportView(txtStatus);
        txtStatus.getAccessibleContext().setAccessibleName("");

        btnTestSQL.setText("TestSQL");
        btnTestSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestSQLActionPerformed(evt);
            }
        });

        jButton1.setText("Exit");
        jButton1.setName("btnExit"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabelCategory.setText("Kategori");

        mPunContentWindow.setColumns(20);
        mPunContentWindow.setRows(5);
        jScrollPane2.setViewportView(mPunContentWindow);

        lblMsg.setText("lblMsg");

        mPunListWindow.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(mPunListWindow);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMsg))
                        .addGap(372, 372, 372)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTestSQL))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTestSQL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mCategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelCategory))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                        .addComponent(lblMsg)
                        .addGap(100, 100, 100))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void InitializePuns()
    {
        ResultSet punsFromDb = SQLHelper.GetResultSetFromQuery("SELECT * from pun");
        try
        {
            while(punsFromDb.next())
             mPunList.add(new Pun(punsFromDb.getString("Content"), punsFromDb.getString("Title")));
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(int i = 0; i < mPunList.size(); i++)
        {
            listModel.addElement(mPunList.get(i).GetTitle());
        }
        mPunListWindow.setModel(listModel);
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        LoginWindow login = new LoginWindow(this);
        login.setVisible(true);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnTestSQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestSQLActionPerformed
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
    }//GEN-LAST:event_btnTestSQLActionPerformed

    public void AppendStatusWindow(String text)
    {
        if (!txtStatus.getText().equals(""))
        {
            txtStatus.setText(txtStatus.getText() + "\n" + text);
        }
        else
        {
             txtStatus.setText(text);
        }
        
    }

    public void SetStatusWindowText(String text)
    {
        txtStatus.setText(text);
    }

    public void UpdateUsername(String newUsername)
    {
        userName = newUsername;
        lblMsg.setText(welcomeMsg + userName + "!");
    }

    public void EmptyStatusWindow()
    {
        txtStatus.setText("");
    }
    
    public void RegisterUser(User user)
    {
        mActiveUser = user;
        UpdateUsername(mActiveUser.getName());
        if (mActiveUser.isAdmin())
        {
            System.out.println("Välkommen admin");
            AdminWindow a = new AdminWindow();
            a.setVisible(true);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTestSQL;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabelCategory;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblMsg;
    private javax.swing.JComboBox mCategoryComboBox;
    private javax.swing.JTextArea mPunContentWindow;
    private javax.swing.JList mPunListWindow;
    private javax.swing.JEditorPane txtStatus;
    // End of variables declaration//GEN-END:variables
}
