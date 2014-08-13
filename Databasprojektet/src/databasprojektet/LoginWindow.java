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

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Simon Dahlberg and Jesper Sahlin
 */
public class LoginWindow extends javax.swing.JFrame
{

    MainWindow mParent;

    /**
     * Creates new form LoginWindow
     *
     * @param parent the MainWindow which creates this form.
     */
    public LoginWindow(MainWindow parent)
    {
        this.mParent = parent;
        initComponents();
        this.setLocationRelativeTo(null);
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

        mOkButton = new javax.swing.JButton();
        mNameField = new javax.swing.JTextField();
        mCancelButton = new javax.swing.JButton();
        mNameLabel = new javax.swing.JLabel();
        mPasswordLabel = new javax.swing.JLabel();
        mCreateProfileButton = new javax.swing.JButton();
        lbLoginResult = new javax.swing.JLabel();
        mPasswordField = new javax.swing.JPasswordField();
        lblLoginResults = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login");
        setBackground(new java.awt.Color(0, 0, 0));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(java.awt.Color.pink);
        setName("frLogin"); // NOI18N
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        mOkButton.setText("OK");
        mOkButton.setName("mOkButton"); // NOI18N
        mOkButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mOkButtonActionPerformed(evt);
            }
        });

        mNameField.setText("bob");
        mNameField.setName("mNameField"); // NOI18N

        mCancelButton.setText("Avbryt");
        mCancelButton.setName("mCancelButton"); // NOI18N
        mCancelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mCancelButtonActionPerformed(evt);
            }
        });

        mNameLabel.setText("Namn");
        mNameLabel.setName("mNameLabel"); // NOI18N

        mPasswordLabel.setText("Lösenord");
        mPasswordLabel.setName("mPasswordLabel"); // NOI18N

        mCreateProfileButton.setText("Skapa profil");
        mCreateProfileButton.setName("mCreateProfileButton"); // NOI18N
        mCreateProfileButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                mCreateProfileButtonActionPerformed(evt);
            }
        });

        mPasswordField.setText("123");
        mPasswordField.setName("mPasswordField"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(mPasswordField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mNameLabel, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mPasswordLabel, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mNameField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(mCreateProfileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLoginResults)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mOkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mCancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbLoginResult)
                        .addGap(0, 183, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mNameLabel)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mCreateProfileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mPasswordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbLoginResult)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblLoginResults)
                        .addGap(40, 40, 40))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mCancelButton)
                            .addComponent(mOkButton))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mOkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mOkButtonActionPerformed

        String cmd = "SELECT * FROM users WHERE UserName = '" + mNameField.getText() + "'";
        ResultSet rs = SQLHelper.GetResultSetFromQuery(cmd);
        lblLoginResults.setText("");
        try
        {
            if (rs.next())
            {
                String passInDatabase = rs.getString("Password");
                //Då getPassword returnerar en char[] och inte en String så får man göra som nedan istället för att direkt jämföra.
                String passInField = new String(mPasswordField.getPassword());
                if (passInDatabase.equals(passInField))
                {
                    int id = rs.getInt("ID");
                    String name = rs.getString("UserName");
                    boolean admin = rs.getBoolean("IsAdmin");
                    User u = new User(id, name, passInDatabase, admin);
                    mParent.RegisterActiveUser(u);

                    setVisible(false);
                }
                else
                {
                    lblLoginResults.setText("Fel lösenord");
                }
            }
            else
            {
                lblLoginResults.setText("Användarnamnet existerar inte");
            }

        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

    }//GEN-LAST:event_mOkButtonActionPerformed

    private void mCreateProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mCreateProfileButtonActionPerformed
        RegistrationWindow registration = new RegistrationWindow(false);
        registration.setVisible(true);
    }//GEN-LAST:event_mCreateProfileButtonActionPerformed

    private void mCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mCancelButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_mCancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbLoginResult;
    private javax.swing.JLabel lblLoginResults;
    private javax.swing.JButton mCancelButton;
    private javax.swing.JButton mCreateProfileButton;
    private javax.swing.JTextField mNameField;
    private javax.swing.JLabel mNameLabel;
    private javax.swing.JButton mOkButton;
    private javax.swing.JPasswordField mPasswordField;
    private javax.swing.JLabel mPasswordLabel;
    // End of variables declaration//GEN-END:variables

}
