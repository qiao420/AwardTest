package com.JFrame;

import com.dao.UserDao;
import com.entity.User;
import com.utils.Druid;
import com.utils.toolUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;
/**
 * @Description ����Ա�û�����ҳ��
 * @Author: �����m
 */
public class AdminUserInfo extends JFrame{
        private JFrame jf;
        private JTable table;
        private DefaultTableModel model;        //���ģ��
        Druid druid=new Druid();                //���ӳ�
        UserDao userDao=new UserDao();          //�ļ������ݿ���н���
        private JTextField textField_1;         //�޸ĵ��ı���
        private JTextField textField_2;
        private JTextField textField_3;
        private JTextField textField_4;
        private JTextField textField_5;
        private int id=0;                       //����idɾ��
        public AdminUserInfo(){
            jf=new JFrame("����Ա����");
            jf.setBounds(400,100,600,500);          //���ò���λ�ã����
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JMenuBar menuBar = new JMenuBar();                        //�˵���
            jf.setJMenuBar(menuBar);                                 //���˵���������jf�Ķ�����

            JMenu menu1 = new JMenu("�û�����");
            menuBar.add(menu1);                                      //��Ӳ˵�

            JMenuItem m1_item1=new JMenuItem("�û���Ϣ");
            menu1.add(m1_item1);                                    //��Ӳ˵���

            JMenuItem m1_item2=new JMenuItem("������Ϣ");
            m1_item2.addMouseListener(new MouseAdapter(){            //ͨ���̳�������MouseAdapter�ඨ�������
                public void mousePressed(MouseEvent e){             //��굥���¼�
                    jf.dispose();                                                     //�˳���ҳ��
                    new AdminBorrowInfo();                                            //��ת������Ϣ
                }
            } );
            menu1.add(m1_item2);                                        //��Ӳ˵���

            JMenu menu2=new JMenu("�鼮����");                      //�˵�
            menuBar.add(menu2);

            JMenuItem m2_item1=new JMenuItem("�鼮���");               //�˵���
            m2_item1.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    jf.dispose();
                   new AdminBookAdd();                               //��굥���¼����鼮���
                }
            });
            menu2.add(m2_item1);

            JMenuItem m2_item2=new JMenuItem("�鼮�޸�");
            m2_item2.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    jf.dispose();
                    new AdminBookEdit();                              //��굥���¼����ر�ҳ�棬��ת�鼮�޸�
                }
            });
            menu2.add(m2_item2);

            JMenu menu3=new JMenu("�˳�ϵͳ");
            menu3.addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e){         //�˳�ϵͳ�ر�
                    JOptionPane.showMessageDialog(null,"��ӭ�ٴ�ʹ��");
                    jf.dispose();
                }
            });
            menuBar.add(menu3);

            JPanel panel = new JPanel();                                 //�̶������
            //���ñ߿򼰱߿�����
            panel.setBorder(new TitledBorder(null, "�û���Ϣ", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
            panel.setBounds(20, 10, 540, 74);
            jf.getContentPane().add(panel);                             //�����ӵ�����
            panel.setLayout(null);                                      //��ʹ�ò��ֹ�����

            JLabel lblbook = new JLabel("�û�����");               //��ʾ��̬�ı�
            lblbook.setFont(new Font("����", Font.BOLD, 17));
            lblbook.setBounds(35, 22, 130, 32);
            panel.add(lblbook);                                          //��ӵ����

            JTextField jtextField = new JTextField();                    //�����ı���
            jtextField.setBounds(130, 27, 155, 25);
            panel.add(jtextField);
            jtextField.setColumns(10);                                      //���

            JButton btnSelect = new JButton("��ѯ");                     //������ѯ��ť
            btnSelect.addActionListener(new ActionListener() {              //��ӵ����¼�
                @Override
                public void actionPerformed(ActionEvent e) {                //��д�������
                    String userName = jtextField.getText();
                    User user = new User();
                    user.setUserName(userName);                             //��ȡ�ı������������
                    putDates(user);                                         //�ڱ�����ʾ
                }
            });
            btnSelect.setBounds(300,27,70,25);
            panel.add(btnSelect);

            JButton breturn=new JButton("����");
            breturn.setBounds(480,27,70,25);
            breturn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {                //�����󷵻أ���ʾ������Ϣ
                    putDates(new User());
                }
            });
            panel.add(breturn);

            //�û���Ϣ��
            String[] title={"�û����","�û���","����","�Ա�","�绰"};       //��ͷ
            String[][] userDates={};                                   //��������
            model=new DefaultTableModel(userDates,title);               //ʵ���������
            table=new JTable(model);
            putDates(new User());                                   //��ȡ���ݿ����ݵ�tableUser

            JScrollPane jscrollpane = new JScrollPane();                //���й����������
            jscrollpane.setBounds(30, 60, 515, 200);
            jscrollpane.setViewportView(table);                       //�������
            panel.add(jscrollpane);
            jf.getContentPane().add(panel);

            //��Ϣ��
            JLabel labelid=new JLabel("�û����");
            labelid.setFont(new Font("����", Font.BOLD, 17));
            labelid.setBounds(50, 270, 80, 45);
            panel.add(labelid);

            textField_1=new JTextField();
            textField_1.setBounds(120, 280, 120, 25);
            panel.add(textField_1);
            textField_1.setColumns(10);

            JLabel labelname=new JLabel("�û���");
            labelname.setFont(new Font("����", Font.BOLD, 17));
            labelname.setBounds(300, 270, 80, 45);
            panel.add(labelname);

            textField_2=new JTextField();
            textField_2.setBounds(355, 280, 130, 25);
            panel.add(textField_2);
            textField_2.setColumns(10);

            JLabel labelpass=new JLabel("����");
            labelpass.setFont(new Font("����", Font.BOLD, 17));
            labelpass.setBounds(50, 315, 80, 45);
            panel.add(labelpass);

            textField_3=new JTextField();
            textField_3.setBounds(120, 320, 120, 25);
            panel.add(textField_3);
            textField_3.setColumns(10);

            JLabel labelsex=new JLabel("�Ա�");
            labelsex.setFont(new Font("����", Font.BOLD, 17));
            labelsex.setBounds(300, 315, 80, 45);
            panel.add(labelsex);

            textField_4=new JTextField();
            textField_4.setBounds(355, 320, 120, 25);
            panel.add(textField_4);
            textField_4.setColumns(10);

            JLabel labelphone=new JLabel("�ֻ���");
            labelphone.setFont(new Font("����", Font.BOLD, 17));
            labelphone.setBounds(50, 355, 80, 45);
            panel.add(labelphone);

            textField_5=new JTextField();
            textField_5.setBounds(120, 365, 150, 25);
            panel.add(textField_5);
            textField_5.setColumns(10);

            JButton btnDelete = new JButton("ɾ��");
            btnDelete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int i=delDates(id);                             //ɾ��
                    User user = new User();
                    putDates(user);
                }
            });

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int rowDel=table.getSelectedRow();                  //ѡ�и���ɾ��
                    id=(int)table.getValueAt(rowDel,0);          //����-1ʱû��ѡ����������������,������ѡ��Ȼ�󽫱���е����ݣ����뵽��Ӧ���ı�����
                }
            });
            btnDelete.setBounds(390,27,70,25);
            panel.add(btnDelete);

            JButton buttonUpdate=new JButton("�޸�");
            buttonUpdate.addActionListener(new ActionListener() {           //�޸İ�ť�������¼�
                @Override
                public void actionPerformed(ActionEvent e) {
                    String userId = textField_1.getText();                  //��ȡ�ı�����Ϣ
                    String userName = textField_2.getText();
                    String password = textField_3.getText();
                    String sex=textField_4.getText();
                    String phone=textField_5.getText();
                    if (toolUtil.isEmpty(userName) || toolUtil.isEmpty(password)||toolUtil.isEmpty(sex)||toolUtil.isEmpty(phone)) {
                        JOptionPane.showMessageDialog(null, "�����������Ϣ");
                        return;                                                 //�ı���Ϊ����ʾ
                    }
                    User user = new User();                                     //�޸���Ϣ����
                    user.setUserId(Integer.parseInt(userId));
                    user.setUserName(userName);
                    user.setPassword(password);
                    user.setSex(sex);
                    user.setPhone(phone);
                    Connection con = null;
                    try {
                        con = Druid.getConnection();                            //��ȡ���ӣ��޸����ݿ�
                        int i = userDao.update(con, user);                      //���ز������ݿ���µ�����
                        if (i == 1) {
                            JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
                            putDates(new User());
                        } else {                                                //i=0
                            JOptionPane.showMessageDialog(null, "�޸�ʧ��");
                        }
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "�޸��쳣");
                    }finally{
                        try {
                            con.close();
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }
            });
            buttonUpdate.setFont(new Font("����",Font.BOLD,15));
            buttonUpdate.setBounds(355,370,70,25);
            panel.add(buttonUpdate);


            jf.setVisible(true);        //��ʾ
            jf.setResizable(true);      //��С���ɱ�
        }

        private void putDates(User user) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);                //ˢ��JTable

            Connection con = null;
            try {
                con = druid.getConnection();
                ResultSet list = userDao.list(con, user);
                while (list.next()) {
                    Vector rowData = new Vector();                 //VectorΪ�����࣬���Դ�Ų�ͬ���������ͣ�Ƶ���������ݡ��൱��һ������ʵ�ֶ�̬�洢������
                    rowData.add(list.getInt("id"));
                    rowData.add(list.getString("username"));
                    rowData.add(list.getString("password"));
                    rowData.add(list.getString("sex"));
                    rowData.add(list.getString("phone"));
                    model.addRow(rowData);              //�������ݵ����
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        public int delDates(int id){                            //����idɾ���û�
            int i=userDao.deldate(new Object[]{id});            //����Ŵ����id��Object���͵ĵ����鶨�幹����ʼ��һ��
            return i;                                           //i>0ɾ���ɹ�
        }
        public static void main(String[] args) {
            new AdminUserInfo();
        }
    }

