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
 * @Description 用户注册页面：当前只实现了普通用户的注册功能
 * @Author: 乔世聪
 */
public class RegFrm extends JFrame {
    private JFrame jf;
    private JTextField textField;
    private JTextField textField1;
    private JTextField textField2;
    private JRadioButton radioButton;
    private JRadioButton radioButton1;
    public RegFrm(){
        jf=new JFrame("用户注册");
        jf.getContentPane().setFont(new Font("幼圆", Font.BOLD, 16));
        jf.setBounds(600, 300,510, 410);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(null);
        JLabel label = new JLabel("注 册");
        label.setFont(new Font("宋体",Font.BOLD,22));
        label.setBounds(200, 30, 75, 40);
        jf.getContentPane().add(label);

        JLabel label1 = new JLabel("用户名");
        label1.setFont(new Font("宋体",Font.BOLD,14));
        label1.setBounds(130, 75, 75, 40);
        jf.getContentPane().add(label1);
         textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(198, 83, 130, 25);
        jf.getContentPane().add(textField);

        JLabel usermsg = new JLabel();
        usermsg.setFont(new Font("Dialog", Font.BOLD, 13 ));//它接收Boolean，当为true时显示 Dialog
        usermsg.setBounds(330, 83,100,20);
        jf.getContentPane().add(usermsg);
        textField.addFocusListener(new FocusListener() {  //焦点监听器
            @Override
            public void focusGained(FocusEvent e) {  //得到焦点

            }

            @Override
            public void focusLost(FocusEvent e) {  //失去焦点
                String text = textField.getText();
                if(toolUtil.isEmpty(text)){
                    usermsg.setText("用户名不能为空");
                    usermsg.setForeground(Color.RED);
                }else {
                    usermsg.setText("√");
                    usermsg.setForeground(Color.GREEN);
                }
            }
        });

        JLabel label2 = new JLabel("密码");
        label2.setFont(new Font("宋体",Font.BOLD,14));
        label2.setBounds(140, 115, 75, 40);
        jf.getContentPane().add(label2);
         textField1 = new JTextField();
        textField1.setColumns(10);
        textField1.setBounds(198, 125, 130, 25);
        jf.getContentPane().add(textField1);
        JLabel pswmsg = new JLabel();
        pswmsg.setFont(new Font("Dialog", Font.BOLD, 13 ));//它接收Boolean，当为true时显示 Dialog
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
                    pswmsg.setText("密码不能为空");
                    pswmsg.setForeground(Color.RED);
                }else {
                    //检测字符串是否匹配给定的正则表达式。
//                    boolean flag = text.matches(" /^[1][3,4,5,7,8][0-9]{9}$/");
                    boolean flag = text.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{3,8}$");
                    if(flag){
                        pswmsg.setText("√");
                        pswmsg.setForeground(Color.GREEN);
                    }else {
                        JOptionPane.showMessageDialog(null,"密码为3-8位数字与字母组合");
                        pswmsg.setText("");
                    }


                }
            }
        });


        JLabel label3 = new JLabel("手机号");
        label3.setFont(new Font("宋体",Font.BOLD,14));
        label3.setBounds(125, 157, 75, 40);
        jf.getContentPane().add(label3);
        textField2 = new JTextField();
        textField2.setColumns(10);
        textField2.setBounds(198, 167, 130, 25);
        jf.getContentPane().add(textField2);
        JLabel phonemsg = new JLabel();
        phonemsg.setFont(new Font("Dialog", Font.BOLD, 13 ));//它接收Boolean，当为true时显示 Dialog
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
                    phonemsg.setText("手机号不能为空");
                    phonemsg.setForeground(Color.RED);
                }else {
                    //检测字符串是否匹配给定的正则表达式。
                    boolean flag = text.matches("^[1][3,4,5,7,8][0-9]{9}$");
//                    boolean flag = text.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{3,8}$");
                    if(flag){
                        phonemsg.setText("√");
                        phonemsg.setForeground(Color.GREEN);
                    }else {
                        JOptionPane.showMessageDialog(null,"请输入正确的手机号");
                        phonemsg.setText("");
                    }


                }
            }
        });



        JLabel label4 = new JLabel("性别");
        label4.setFont(new Font("宋体",Font.BOLD,14));
        label4.setBounds(140, 197, 75, 40);
        jf.getContentPane().add(label4);

        ButtonGroup group = new ButtonGroup();
         radioButton = new JRadioButton("男");
        radioButton.setBounds(198, 207, 58, 23);
        jf.getContentPane().add(radioButton);
         radioButton1 = new JRadioButton("女");
        radioButton1.setBounds(287, 207, 65, 23);
        jf.getContentPane().add(radioButton1);
        group.add(radioButton);
        group.add(radioButton1);

        JButton button = new JButton("注册");
        button.setBounds(150,247,60,26);
        jf.getContentPane().add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reg(e);
            }
        });

        JButton button1 = new JButton("返回");
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
        //获取JRadioButton的值
        if(radioButton.isSelected()){
            sex=radioButton.getText();
        }else{
            sex=radioButton1.getText();
        }
        if (toolUtil.isEmpty(username) || toolUtil.isEmpty(password)||toolUtil.isEmpty(phone)) {
            JOptionPane.showMessageDialog(null, "请输入相关信息");
            return;
        }
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setSex(sex);
        user.setPhone(phone);
        user.setRole(1);//用户
        Connection conn = null;
        try {
            conn = Druid.getConnection();
            UserDao userDao = new UserDao();
            int i = userDao.reg(conn, user);
            boolean flag = userDao.find(conn,user);
            if (flag) {
                JOptionPane.showMessageDialog(null, "该用户名已存在,请重新注册");
            } else {
                if (i == 0) {
                    JOptionPane.showMessageDialog(null, "注册失败");
                } else {
                    JOptionPane.showMessageDialog(null, "注册成功");
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
