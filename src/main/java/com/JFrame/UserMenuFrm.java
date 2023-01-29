package com.JFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * @Description ��ͨ�û���ҳ�棺һ���˵���
 * @Author: ������
 */
public class UserMenuFrm extends JFrame {
    private JFrame jf;
    public UserMenuFrm(){
        jf = new JFrame("�û�����");
        jf.setBounds(600, 300, 600, 429);
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.getContentPane().setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        jf.setJMenuBar(menuBar);

        JMenu jMenu1 = new JMenu("������Ϣ");
        JMenu jMenu2 = new JMenu("�黹��Ϣ");
        JMenu jMenu3 = new JMenu("�����˻�");
        JMenu jMenu4 = new JMenu("�˳�");
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
            public void mousePressed(MouseEvent e){         //�˳�ϵͳ�ر�
                JOptionPane.showMessageDialog(null,"��ӭ�ٴ�ʹ��");
                jf.dispose();
            }
        });


        jf.setVisible(true);

    }

    public static void main(String[] args) {
        new UserMenuFrm();
    }

}
