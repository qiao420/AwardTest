package com.JFrame;

import com.alibaba.druid.sql.ast.statement.SQLDropUserStatement;
import com.dao.UserDao;
import com.entity.User;
import com.utils.Druid;
import com.utils.toolUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
/**
 * @Description �û�ע��ҳ�棺��ǰֻʵ������ͨ�û���ע�Ṧ��
 * @Author: ������
 */
public class RegFrm extends JFrame {
    private JFrame jf;
    private JTextField textField;
    private JTextField textField1;
    private JTextField textField2;
    private JRadioButton radioButton;
    private JRadioButton radioButton1;
    public RegFrm(){
        jf=new JFrame("�û�ע��");
        jf.getContentPane().setFont(new Font("��Բ", Font.BOLD, 16));
        jf.setBounds(600, 300,510, 410);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(null);
        JLabel label = new JLabel("ע ��");
        label.setFont(new Font("����",Font.BOLD,22));
        label.setBounds(200, 30, 75, 40);
        jf.getContentPane().add(label);

        JLabel label1 = new JLabel("�û���");
        label1.setFont(new Font("����",Font.BOLD,14));
        label1.setBounds(130, 75, 75, 40);
        jf.getContentPane().add(label1);
         textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(198, 83, 130, 25);
        jf.getContentPane().add(textField);

        JLabel usermsg = new JLabel();
        usermsg.setFont(new Font("Dialog", Font.BOLD, 13 ));//������Boolean����Ϊtrueʱ��ʾ Dialog
        usermsg.setBounds(330, 83,100,20);
        jf.getContentPane().add(usermsg);
        textField.addFocusListener(new FocusListener() {  //���������
            @Override
            public void focusGained(FocusEvent e) {  //�õ�����

            }

            @Override
            public void focusLost(FocusEvent e) {  //ʧȥ����
                String text = textField.getText();
                if(toolUtil.isEmpty(text)){
                    usermsg.setText("�û�������Ϊ��");
                    usermsg.setForeground(Color.RED);
                }else {
                    usermsg.setText("��");
                    usermsg.setForeground(Color.GREEN);
                }
            }
        });

        JLabel label2 = new JLabel("����");
        label2.setFont(new Font("����",Font.BOLD,14));
        label2.setBounds(140, 115, 75, 40);
        jf.getContentPane().add(label2);
         textField1 = new JTextField();
        textField1.setColumns(10);
        textField1.setBounds(198, 125, 130, 25);
        jf.getContentPane().add(textField1);
        JLabel pswmsg = new JLabel();
        pswmsg.setFont(new Font("Dialog", Font.BOLD, 13 ));//������Boolean����Ϊtrueʱ��ʾ Dialog
        pswmsg.setBounds(330, 125,100,20);
        jf.getContentPane().add(pswmsg);
        textField1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = textField1.getText();
                if(toolUtil.isEmpty(text)){
                    pswmsg.setText("���벻��Ϊ��");
                    pswmsg.setForeground(Color.RED);
                }else {
                    //����ַ����Ƿ�ƥ�������������ʽ��
//                    boolean flag = text.matches(" /^[1][3,4,5,7,8][0-9]{9}$/");
                    boolean flag = text.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{3,8}$");
                    if(flag){
                        pswmsg.setText("��");
                        pswmsg.setForeground(Color.GREEN);
                    }else {
                        JOptionPane.showMessageDialog(null,"����Ϊ3-8λ��������ĸ���");
                        pswmsg.setText("");
                    }


                }
            }
        });


        JLabel label3 = new JLabel("�ֻ���");
        label3.setFont(new Font("����",Font.BOLD,14));
        label3.setBounds(125, 157, 75, 40);
        jf.getContentPane().add(label3);
        textField2 = new JTextField();
        textField2.setColumns(10);
        textField2.setBounds(198, 167, 130, 25);
        jf.getContentPane().add(textField2);
        JLabel phonemsg = new JLabel();
        phonemsg.setFont(new Font("Dialog", Font.BOLD, 13 ));//������Boolean����Ϊtrueʱ��ʾ Dialog
        phonemsg.setBounds(330, 167,100,20);
        jf.getContentPane().add(phonemsg);
        textField2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = textField2.getText();
                if(toolUtil.isEmpty(text)){
                    phonemsg.setText("�ֻ��Ų���Ϊ��");
                    phonemsg.setForeground(Color.RED);
                }else {
                    //����ַ����Ƿ�ƥ�������������ʽ��
                    boolean flag = text.matches("^[1][3,4,5,7,8][0-9]{9}$");
//                    boolean flag = text.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{3,8}$");
                    if(flag){
                        phonemsg.setText("��");
                        phonemsg.setForeground(Color.GREEN);
                    }else {
                        JOptionPane.showMessageDialog(null,"��������ȷ���ֻ���");
                        phonemsg.setText("");
                    }


                }
            }
        });



        JLabel label4 = new JLabel("�Ա�");
        label4.setFont(new Font("����",Font.BOLD,14));
        label4.setBounds(140, 197, 75, 40);
        jf.getContentPane().add(label4);

        ButtonGroup group = new ButtonGroup();
         radioButton = new JRadioButton("��");
        radioButton.setBounds(198, 207, 58, 23);
        jf.getContentPane().add(radioButton);
         radioButton1 = new JRadioButton("Ů");
        radioButton1.setBounds(287, 207, 65, 23);
        jf.getContentPane().add(radioButton1);
        group.add(radioButton);
        group.add(radioButton1);

        JButton button = new JButton("ע��");
        button.setBounds(150,247,60,26);
        jf.getContentPane().add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reg(e);
            }
        });

        JButton button1 = new JButton("����");
        button1.setBounds(260,247,60,26);
        jf.getContentPane().add(button1);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new LoginFrm();
            }
        });



        jf.setVisible(true);

    }
    protected void Reg(ActionEvent e) {
        String username=textField.getText();
        String password=textField1.getText();
        String phone=textField2.getText();
        String sex="";
        //��ȡJRadioButton��ֵ
        if(radioButton.isSelected()){
            sex=radioButton.getText();
        }else{
            sex=radioButton1.getText();
        }
        if (toolUtil.isEmpty(username) || toolUtil.isEmpty(password)||toolUtil.isEmpty(phone)) {
            JOptionPane.showMessageDialog(null, "�����������Ϣ");
            return;
        }
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setSex(sex);
        user.setPhone(phone);
        user.setRole(1);//�û�
        Connection conn = null;
        try {
            conn = Druid.getConnection();
            UserDao userDao = new UserDao();
            int i = userDao.reg(conn, user);
            boolean flag = userDao.find(conn,user);
            if (flag) {
                JOptionPane.showMessageDialog(null, "���û����Ѵ���,������ע��");
            } else {
                if (i == 0) {
                    JOptionPane.showMessageDialog(null, "ע��ʧ��");
                } else {
                    JOptionPane.showMessageDialog(null, "ע��ɹ�");
                    jf.dispose();
                    new LoginFrm();
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                Druid.close(conn);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }
    public static void main(String[] args) {
        new RegFrm();
    }
}
