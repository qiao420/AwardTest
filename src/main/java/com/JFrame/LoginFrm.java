package com.JFrame;

import com.dao.UserDao;
import com.entity.User;
import com.utils.toolUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Description 用户登录页面
 * @Author: 乔世聪
 */
public class LoginFrm extends JFrame {

    public static User currentUser;
//    private JFrame jf;
    private JTextField userNameText;
    private JTextField passwordText;
    private JComboBox<String> comboBox;
    JFrame jf = new JFrame("图书管理");
    public void init(){


//        jf = new JFrame("图书管理");
//        jf.setSize(600,600);

        jf.getContentPane().setFont(new Font("宋体",Font.BOLD,14));
        jf.setBounds(600,250,500,467);
        jf.setVisible(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.getContentPane().setLayout(null);
        JLabel jlabel = new JLabel();
        jlabel.setBounds(24,10,430,218);
        jf.getContentPane().add(jlabel);

        JLabel label3 = new JLabel("登  录");
        label3.setFont(new Font("宋体", Font.PLAIN, 22));
        label3.setBounds(210, 40, 115, 36);
        jf.getContentPane().add(label3);

        JLabel label = new JLabel("用户名");
        label.setFont(new Font("宋体",Font.BOLD,14));
        label.setBounds(129,120,60,29);
        jf.getContentPane().add(label);
        userNameText = new JTextField();
        userNameText.setBounds(199,122,127,25);
        jf.getContentPane().add(userNameText);
        userNameText.setColumns(10);

        JLabel label1 = new JLabel("密码");
        label1.setFont(new Font("宋体",Font.BOLD,14));
        label1.setBounds(144,159,45,29);
        jf.getContentPane().add(label1);
        passwordText = new JPasswordField();
        passwordText.setBounds(199,161,127,25);
        passwordText.setColumns(10);
        jf.getContentPane().add(passwordText);

        JLabel label2 = new JLabel("权限");
        label2.setFont(new Font("宋体",Font.BOLD,14));
        label2.setBounds(144,198,45,29);
        jf.getContentPane().add(label2);
        comboBox = new JComboBox();
        comboBox.setBounds(199,202,127,25);
        comboBox.addItem("用户");
        comboBox.addItem("管理员");
        jf.getContentPane().add(comboBox);

        JButton button = new JButton("登录");
        button.setBounds(153,247,65,29);
        jf.getContentPane().add(button);

        JButton button1 = new JButton("注册");
        button1.setBounds(253,247,65,29);
        jf.getContentPane().add(button1);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new RegFrm();   //关闭当前页面，跳转到注册页面
            }
        });
        jf.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkLogin(e);   //内容太多，抽取出来作为一个函数
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            UserDao userDao = new UserDao();
            public void checkLogin(ActionEvent e) throws Exception {
                String userName = userNameText.getText();
                String password = passwordText.getText();

                if (toolUtil.isEmpty(userName) || toolUtil.isEmpty(password)) {
                    JOptionPane.showMessageDialog(null, "用户名和密码不能为空");
                    return;
                }
                int index = comboBox.getSelectedIndex();
                User user = new User();
                user.setUserName(userName);
                user.setPassword(password);
                if(index == 0){
                    user.setRole(1);//普通用户

                }else {
                    user.setRole(2);//管理员
                }
                try {
                    User login = userDao.login(user);  //查询该用户的所有信息
                    currentUser = login;
                    if (login == null) {
                        JOptionPane.showMessageDialog(null, "登录失败");
                    } else {
                        if (index == 0) {
                            jf.dispose();
                            new UserMenuFrm();
                        } else {
                            jf.dispose();
                            new AdminUserInfo();
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "登录异常");
            } finally {
                try {

                } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        new LoginFrm().init();
    }




}
