package com.JFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * @Description 普通用户主页面：一个菜单栏
 * @Author: 乔世聪
 */
public class UserMenuFrm extends JFrame {
    private JFrame jf;
    public UserMenuFrm(){
        jf = new JFrame("用户界面");
        jf.setBounds(600, 300, 600, 429);
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.getContentPane().setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        jf.setJMenuBar(menuBar);

        JMenu jMenu1 = new JMenu("借阅信息");
        JMenu jMenu2 = new JMenu("归还信息");
        JMenu jMenu3 = new JMenu("个人账户");
        JMenu jMenu4 = new JMenu("退出");
        menuBar.add(jMenu1);
        menuBar.add(jMenu2);
        menuBar.add(jMenu3);
        menuBar.add(jMenu4);

        jMenu1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new UserBorrow();
            }
        });
        jMenu2.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jf.dispose();
                new UserReturn();
            }
        });

        jMenu4.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){         //退出系统关闭
                JOptionPane.showMessageDialog(null,"欢迎再次使用");
                jf.dispose();
            }
        });


        jf.setVisible(true);

    }

    public static void main(String[] args) {
        new UserMenuFrm();
    }

}
