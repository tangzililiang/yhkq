/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaoqin.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import org.kaoqin.core.KaoqinManager;
import org.kaoqin.util.Configer;
import org.kaoqin.util.KqLoger;
import org.kaoqin.vo.User;

/**
 *
 * @author lenovo
 */
public class MainFrame extends javax.swing.JFrame {


    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        
        initComponents();
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        kaoqinServerHost = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();
        btnAddUser = new javax.swing.JButton();
        btnDeleteUser = new javax.swing.JButton();
        sbTime = new javax.swing.JFormattedTextField();
        xbTime = new javax.swing.JFormattedTextField();
        proxyEnable = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        port = new javax.swing.JSpinner();
        btnBegin = new javax.swing.JButton();
        proxy = new javax.swing.JTextField();
        checkInterval = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        btnKqImmediately = new javax.swing.JButton();
        modifyIP = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaMsg = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("YH考勤工具");
        setName("mainframe");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("配置选项"));

        jLabel1.setFont(jLabel1.getFont());
        jLabel1.setText("上班时间：");

        jLabel2.setText("下班时间:");

        jLabel3.setText("代理IP：");

        jLabel4.setText("代理端口：");

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/kaoqin/ui/save.gif"))); // NOI18N
        btnSave.setText("保存配置");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel6.setText("定时器：");

        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "用户名", "密码", "验证码", "员工验证码", "IP地址"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(usersTable);

        btnAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/kaoqin/ui/add.gif"))); // NOI18N
        btnAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUserActionPerformed(evt);
            }
        });

        btnDeleteUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/kaoqin/ui/candle.gif"))); // NOI18N
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });

        sbTime.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance())));

        xbTime.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance())));

        proxyEnable.setText("启用代理");
        proxyEnable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                proxyEnableActionPerformed(evt);
            }
        });

        jLabel7.setText("服务器IP:");

        port.setModel(new javax.swing.SpinnerNumberModel(1, 1, 65535, 1));

        btnBegin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/kaoqin/ui/tree-clock.png"))); // NOI18N
        btnBegin.setText("准备考勤");
        btnBegin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBeginActionPerformed(evt);
            }
        });

        checkInterval.setModel(new javax.swing.SpinnerNumberModel(1, 1, 20, 1));

        jLabel5.setForeground(new java.awt.Color(255, 153, 153));
        jLabel5.setText("分钟检查一次，如果符合考勤条件，则自动考勤");

        btnKqImmediately.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/kaoqin/ui/tree-bug.png"))); // NOI18N
        btnKqImmediately.setText("立即考勤");
        btnKqImmediately.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKqImmediatelyActionPerformed(evt);
            }
        });

        modifyIP.setText("修改本机IP地址");
        modifyIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyIPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDeleteUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(checkInterval, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(modifyIP, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sbTime)
                            .addComponent(proxy, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xbTime, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kaoqinServerHost, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(proxyEnable, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(btnSave)
                .addGap(40, 40, 40)
                .addComponent(btnBegin)
                .addGap(42, 42, 42)
                .addComponent(btnKqImmediately)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(sbTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xbTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(kaoqinServerHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(proxyEnable)
                    .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proxy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(checkInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(modifyIP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAddUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteUser)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnBegin)
                    .addComponent(btnKqImmediately))
                .addGap(307, 307, 307))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("日志信息"));
        jPanel2.setToolTipText("日志信息");

        jTextAreaMsg.setBackground(new java.awt.Color(204, 255, 255));
        jTextAreaMsg.setColumns(20);
        jTextAreaMsg.setRows(5);
        jScrollPane1.setViewportView(jTextAreaMsg);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
        );

        jMenu1.setText("文件(F)");

        jMenuItem1.setText("退出(E)");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("帮助(H)");

        jMenuItem2.setText("关于(A)");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("使用说明(U)");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.getAccessibleContext().setAccessibleName("日志");
        jPanel2.getAccessibleContext().setAccessibleDescription("日志");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        //保存
        try {
            if(usersTable.getCellEditor()!=null){
                usersTable.getCellEditor().stopCellEditing();//结束编辑
            }   
            Date sbDate = new SimpleDateFormat("HH:mm:ss").parse( this.sbTime.getText());
            Date xbDate = new SimpleDateFormat("HH:mm:ss").parse( this.xbTime.getText());
            if(!sbDate.before(xbDate)){
                JOptionPane.showMessageDialog(null, "上班时间必须小于下班时间", "保存配置信息错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //重置内存里的配置信息
            Configer.clear();
            //系统配置信息
            Configer.setProporty("sbTime", this.sbTime.getText());
            Configer.setProporty("xbTime", this.xbTime.getText());
            Configer.setProporty("kaoqinServerHost", this.kaoqinServerHost.getText());
            Configer.setProporty("proxy", this.proxy.getText());
            Configer.setProporty("port", this.port.getValue().toString());
            Configer.setProporty("checkInterval", this.checkInterval.getValue().toString());
            Configer.setProporty("proxyEnable", String.valueOf(this.proxyEnable.isSelected()));
            Configer.setProporty("modifyIP", String.valueOf(this.modifyIP.isSelected()));
            //用户配置信息
            DefaultTableModel tableModel = (DefaultTableModel) usersTable.getModel();
            int rowCount=tableModel.getRowCount();
            for(int i=0;i<rowCount;i++){
                User user=new User();
                user.setUsername((String)tableModel.getValueAt(i, 0));
                user.setPassword((String)tableModel.getValueAt(i, 1));
                user.setYzm((String)tableModel.getValueAt(i, 2));
                user.setYgyzm((String)tableModel.getValueAt(i, 3));
                user.setIp((String)tableModel.getValueAt(i, 4));
                Configer.addUser(user);
            }
            //保存配置
            Configer.persistent();
            JOptionPane.showMessageDialog(null, "配置信息已保存！", "提示信息", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "保存配置信息错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        //关于
        JOptionPane.showMessageDialog(null, "YH考勤工具  Version： 1.1 beta \r\n Author:  txx", "关于", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnBeginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBeginActionPerformed
        //开始考勤
        this.jTextAreaMsg.setText("");//清空输出
        new Thread(new Runnable() {
            public void run() {
                KaoqinManager.stop();
                KaoqinManager.startup();
            }
        }).start();

    }//GEN-LAST:event_btnBeginActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JOptionPane.showMessageDialog(null, "请参见[使用说明.DOC]文档", "使用说明", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void proxyEnableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_proxyEnableActionPerformed
        // “启用代理”复选框选择事件
    }//GEN-LAST:event_proxyEnableActionPerformed

    private void btnAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUserActionPerformed
        //添加用户
        DefaultTableModel tableModel = (DefaultTableModel) usersTable.getModel();
        tableModel.addRow(new Object[]{"", "", "",""});
    }//GEN-LAST:event_btnAddUserActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        // 删除用户
        int[] rowsIndex=usersTable.getSelectedRows();
        DefaultTableModel tableModel = (DefaultTableModel) usersTable.getModel();
        if(rowsIndex.length>0){//反起删，不然就数组越界了
            for(int i=rowsIndex.length-1;i>=0;i--){
                tableModel.removeRow(rowsIndex[i]);
            }
        }
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void btnKqImmediatelyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKqImmediatelyActionPerformed
        //开始考勤
        this.jTextAreaMsg.setText("");
        new Thread(new Runnable() {
            public void run() {
                KaoqinManager.runKaoqinForAllUsers();
            }
        }).start();
    }//GEN-LAST:event_btnKqImmediatelyActionPerformed

    private void modifyIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyIPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modifyIPActionPerformed
    
    /**
     * 初始化托盘图标
     */
    private void initTray() {
            try {
                    TrayIcon trayIcon = null;
                    
                    // 创建弹出菜单
                    PopupMenu popup = new PopupMenu();
                    MenuItem exitItem = new MenuItem("EXIT");
                    exitItem.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                    System.exit(0);
                            }
                    });
                    popup.add(exitItem);
                    // 创建系统托盘
                    SystemTray tray = SystemTray.getSystemTray();
                    Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/org/kaoqin/ui/tree-bug.png"));// 载入图片
                    trayIcon = new TrayIcon(image, "YH考勤", popup);// 创建trayIcon
                    trayIcon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MainFrame.this.setVisible(true);
				}
			});
                    tray.add(trayIcon);
            } catch (Exception e) {
                   JOptionPane.showMessageDialog(null, e.getMessage(), "创建系统托盘图标异常", JOptionPane.ERROR_MESSAGE);
            }

    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
//        Font f = new Font("Tahoma", Font.PLAIN, 11);
//        UIManager.put("TextField.font", f);
//        UIManager.put("Label.font", f);
//        UIManager.put("ComboBox.font", f);
//        UIManager.put("MenuBar.font", f);
//        UIManager.put("Menu.font", f);
//        UIManager.put("ToolTip.font", f);
//        UIManager.put("MenuItem.font", f);

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            //设置界面风格为windows风格
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                final MainFrame mainFrame = new MainFrame();
                mainFrame.setIconImage(Toolkit.getDefaultToolkit().createImage("icon/kq.png"));
                mainFrame.setVisible(true);
                mainFrame.setTitle("YH考勤工具 V1.1 beta");
                mainFrame.setLocation(350, 80);
                //设置当点击右上角关闭按钮最小化，不退出程序
                mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                mainFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        mainFrame.setVisible(false);
                    }
                });
                KqLoger.setMainFrame(mainFrame);
                loadConfigToUI(mainFrame);//加载配置信息到界面
                if (SystemTray.isSupported()) // 判断系统是否支持系统托盘
		{
			mainFrame.initTray();
		}

            }
            /**
             *  加载配置信息到界面
             */
            public void loadConfigToUI(MainFrame mainFrame){
                try {
                    //系统配置
                    mainFrame.sbTime.setText(Configer.getProporty("sbTime"));
                    mainFrame.xbTime.setText(Configer.getProporty("xbTime"));
                    mainFrame.kaoqinServerHost.setText(Configer.getProporty("kaoqinServerHost"));
                    mainFrame.proxy.setText(Configer.getProporty("proxy"));
                    mainFrame.port.setValue(Integer.parseInt(Configer.getProporty("port")));
                    mainFrame.checkInterval.setValue(Integer.parseInt(Configer.getProporty("checkInterval")));
                    mainFrame.proxyEnable.setSelected(Boolean.parseBoolean(Configer.getProporty("proxyEnable")));
                    mainFrame.modifyIP.setSelected(Boolean.parseBoolean(Configer.getProporty("modifyIP")));
                    //用户配置
                    List<User> userlist= Configer.getAllUser();
                    DefaultTableModel tableModel = (DefaultTableModel)  mainFrame.usersTable.getModel();
                    for(int i=0;i<userlist.size();i++){
                        User user=userlist.get(i);
                        tableModel.addRow(new String[]{user.getUsername(),user.getPassword(),user.getYzm(),user.getYgyzm(),user.getIp()});
                    }
                   
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "加载配置信息异常", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddUser;
    private javax.swing.JButton btnBegin;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnKqImmediately;
    private javax.swing.JButton btnSave;
    private javax.swing.JSpinner checkInterval;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextArea jTextAreaMsg;
    public javax.swing.JTextField kaoqinServerHost;
    private javax.swing.JCheckBox modifyIP;
    private javax.swing.JSpinner port;
    public javax.swing.JTextField proxy;
    private javax.swing.JCheckBox proxyEnable;
    private javax.swing.JFormattedTextField sbTime;
    private javax.swing.JTable usersTable;
    private javax.swing.JFormattedTextField xbTime;
    // End of variables declaration//GEN-END:variables
}
