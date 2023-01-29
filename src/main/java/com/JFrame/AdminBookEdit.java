package com.JFrame;



import com.dao.BookDao;
import com.entity.Book;
import com.entity.BookType;
import com.utils.toolUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Vector;
/**
 * @Description 管理员书籍管理页面
 * @Author: 蹇小丽
 */
public class AdminBookEdit extends JFrame {

	private JTextField textField;
	private JTable table;
	private DefaultTableModel model;
	BookDao bookDao=new BookDao();
	private JTextField txtId;//编号

	private JTextField txtBooksName;// 书名
	private JTextField txtAuthor;// 作者
	private JTextField txtNumber;// 库存
	private  JTextField txtpress;//出版社
	private JTextField txtBooksPrice;// 书籍价格
//	private JTextField txtIeibie;//类别
	private JTextField txtRemark;//书籍描述

	private JComboBox comboBox;
	private JComboBox comboBox_2;
	private JComboBox comboBox_1;
	public AdminBookEdit(){
		JFrame homepage=new JFrame("管理员界面");
		homepage.setBounds(400, 50, 600, 672);
		homepage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		homepage.setJMenuBar(menuBar);
		//菜单


		JMenu bt2 = new JMenu("书籍管理");
		menuBar.add(bt2);

		JMenuItem mbt3 = new JMenuItem("书籍添加");
		mbt3.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				homepage.dispose();
				new AdminBookAdd();
			}
		});
		bt2.add(mbt3);

		JMenuItem mbt4 = new JMenuItem("书籍修改");
		bt2.add(mbt4);
		JMenu bt3 = new JMenu("用户管理");
		menuBar.add(bt3);

		JMenuItem mbt5= new JMenuItem("用户信息");
		mbt5.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				homepage.dispose();
				new AdminUserInfo();
			}
		});
		bt3.add(mbt5);

		JMenuItem mbt6 = new JMenuItem("借阅信息");
		mbt6.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				homepage.dispose();
				new AdminBorrowInfo();
			}
		});
		bt3.add(mbt6);

		JMenu mbt7 = new JMenu("退出系统");
		mbt7.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				JOptionPane.showMessageDialog(null, "欢迎再次使用");
				homepage.dispose();
			}
		});
		menuBar.add(mbt7);
	
		homepage.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u4E66\u76EE\u67E5\u8BE2", TitledBorder.LEADING, TitledBorder.TOP, null, Color.blue));
		panel.setBounds(20, 10, 541, 78);
		homepage.getContentPane().add(panel);
		panel.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("新宋体", Font.PLAIN, 15));
		comboBox.setBounds(55, 28, 109, 24);
		comboBox.addItem("书籍名称");
		comboBox.addItem("书籍作者");
		panel.add(comboBox);
		
		textField = new JTextField();
		textField.setBounds(185, 28, 146, 24);
		panel.add(textField);
		textField.setColumns(10);
		//查询
		JButton btnNewButton = new JButton("查询");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboBox.getSelectedIndex();
				if(index==0){
					String bookName = textField.getText();
					Book book = new Book();
					book.setBookName(bookName);
					putDates(book);
				}else{
					String authoerName = textField.getText();
					Book book = new Book();
					book.setAuthor(authoerName);
					putDates(book);
				}
			}
		});
		btnNewButton.setFont(new Font("新宋体", Font.PLAIN, 15));
		btnNewButton.setBounds(352, 28, 81, 25);
		panel.add(btnNewButton);
		//
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u4E66\u7C4D\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, Color.blue));
		panel_1.setBounds(20, 105, 541, 195);
		
//		// 做一个表头栏数据  一位数组
		 String[] title={"编号", "书名",  "作者", "价格", "库存", "状态"};
		//数组占位
		 String[][] dates={};
		 /*然后实例化 上面2个控件对象*/
		 model=new DefaultTableModel(dates,title);
		 table=new JTable(model);
		 putDates(new Book());//获取数据库数据放置table中		 
		  panel_1.setLayout(null);
		  JScrollPane pane = new JScrollPane();
		  pane.setBounds(20, 22, 496, 154);
		  pane.setViewportView(table);
			panel_1.add(pane);
			homepage.getContentPane().add(panel_1);
		homepage.getContentPane().add(panel_1);
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {

				tableMousePressed(evt);
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(20, 310, 541, 292);
		homepage.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel id= new JLabel("编号：");
		id.setFont(new Font("新宋体", Font.PLAIN, 15));
		id.setBounds(58, 10, 45, 27);
		panel_2.add(id);

		txtId=new JTextField();
		txtId.setColumns(10);
		txtId.setBounds(101, 10, 129, 27);
		panel_2.add(txtId);
		
		JLabel name = new JLabel("书名：");
		name.setFont(new Font("新宋体", Font.PLAIN, 15));
		name.setBounds(294, 10, 45, 27);
		panel_2.add(name);

		txtBooksName = new JTextField();
		txtBooksName.setColumns(10);
		txtBooksName.setBounds(338, 10, 128, 27);
		panel_2.add(txtBooksName);
		
		JLabel author = new JLabel("作者：");
		author.setFont(new Font("新宋体", Font.PLAIN, 15));
		author.setBounds(58, 58, 45, 27);
		panel_2.add(author);

		txtAuthor = new JTextField();
		txtAuthor.setColumns(10);
		txtAuthor.setBounds(101, 58, 129, 27);
		panel_2.add(txtAuthor);
		
		JLabel price = new JLabel("价格：");
		price.setFont(new Font("新宋体", Font.PLAIN, 15));
		price.setBounds(58, 104, 45, 27);
		panel_2.add(price);

		txtBooksPrice = new JTextField();
		txtBooksPrice.setColumns(10);
		txtBooksPrice.setBounds(101, 104, 129, 27);
		panel_2.add(txtBooksPrice);
		
		JLabel pulish = new JLabel("出版：");
		pulish.setFont(new Font("新宋体", Font.PLAIN, 15));
		pulish.setBounds(294, 58, 45, 27);
		panel_2.add(pulish);

		txtpress = new JTextField();
		txtpress.setColumns(10);
		txtpress.setBounds(337, 58, 129, 27);
		panel_2.add(txtpress);
//
//		JLabel leibie = new JLabel("类别：");
//		leibie.setFont(new Font("新宋体", Font.PLAIN, 15));
//		leibie.setBounds(58, 189, 45, 27);
//		panel_2.add(leibie);
//
//		comboBox_1 = new JComboBox();
//		comboBox_1.setBounds(102, 190, 128, 26);
//		//获取类别
//
//		panel_2.add(comboBox_1);
		
		JLabel number = new JLabel("库存：");
		number.setFont(new Font("新宋体", Font.PLAIN, 15));
		number.setBounds(294, 104, 45, 27);
		panel_2.add(number);

		txtNumber = new JTextField();
		txtNumber.setColumns(10);
		txtNumber.setBounds(337, 104, 129, 27);
		panel_2.add(txtNumber);
		
		JLabel remark = new JLabel("描述：");
		remark.setFont(new Font("新宋体", Font.PLAIN, 15));
		remark.setBounds(58, 152, 45, 27);
		panel_2.add(remark);

		txtRemark = new JTextField();
		txtRemark.setColumns(10);
		txtRemark.setBounds(101, 152, 365, 27);
		panel_2.add(txtRemark);
		
		JLabel status= new JLabel("状态：");
		status.setFont(new Font("新宋体", Font.PLAIN, 15));
		status.setBounds(294, 190, 45, 27);
		panel_2.add(status);
		
		 comboBox_2 = new JComboBox();
		comboBox_2.setBounds(338, 191, 128, 26);
		comboBox_2.addItem("上架");
		comboBox_2.addItem("下架");
		panel_2.add(comboBox_2);
		//
		JButton btnNewButton_1 = new JButton("修改");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookName = txtBooksName.getText();
				String author = txtAuthor.getText();
				String publish = txtpress.getText();
				String priceStr = txtBooksPrice.getText();
				String numberStr = txtNumber.getText();
				String remark = txtRemark.getText();
				String bookId = txtId.getText();
				if (toolUtil.isEmpty(bookId) || toolUtil.isEmpty(bookName)
						|| toolUtil.isEmpty(author) || toolUtil.isEmpty(publish)
						|| toolUtil.isEmpty(priceStr)
						|| toolUtil.isEmpty(numberStr) || toolUtil.isEmpty(remark)) {
					JOptionPane.showMessageDialog(null, "请输入相关内容");
					return;
				}
				BookType selectedItem = (BookType) comboBox_1.getSelectedItem();
				Integer typeId = Integer.valueOf(selectedItem.getType());
				Integer index = comboBox_2.getSelectedIndex();

				Integer number;
				double price;
				try {
					number = Integer.parseInt(numberStr);
					price = new BigDecimal(priceStr).setScale(2, BigDecimal.ROUND_DOWN)
							.doubleValue();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "参数错误");
					return;
				}
				Book book = new Book();
				book.setBookId(Integer.parseInt(bookId));
				book.setBookName(bookName);
				book.setAuthor(author);
				book.setType(String.valueOf(typeId));
				book.setNumber(number);
				book.setPrice(price);
				book.setPublish(publish);
				book.setRemark(remark);
				book.setStatus(1);
				if (index == 0) {
					book.setStatus(1);
				} else if (index == 1) {
					book.setStatus(2);
				}
				try {
					int i = bookDao.update(book);
					if (i == 1) {
						JOptionPane.showMessageDialog(null, "修改成功");
					} else {
						JOptionPane.showMessageDialog(null, "修改失败");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "修改异常");
				}

				putDates(new Book());
			}
		});
		btnNewButton_1.setFont(new Font("新宋体", Font.PLAIN, 15));
		btnNewButton_1.setBounds(304, 235, 93, 35);
		panel_2.add(btnNewButton_1);

		homepage.setVisible(true);
		homepage.setResizable(true);
	}

	//点击表格获取数据
	public void tableMousePressed(MouseEvent evt) {
		int row = table.getSelectedRow();
		Integer bookId = (Integer) table.getValueAt(row, 0);
		Book book = new Book();
		book.setBookId(bookId);
		try {
			ResultSet list = bookDao.list( book);
			if (list.next()) {
				textField.setText(list.getString("id"));
				txtBooksName.setText(list.getString("book_name"));
				txtAuthor.setText(list.getString("author"));
				txtpress.setText(list.getString("publish"));
				txtpress.setText(list.getString("price"));
				txtNumber.setText(list.getString("number"));
				txtRemark.setText(list.getString("remark"));
				int status = list.getInt("status");
				if (status == 1) {
					comboBox_2.setSelectedIndex(0);
				} else {
					comboBox_2.setSelectedIndex(1);
				}
				Integer typeId = list.getInt("type_id");
				Integer count = comboBox_1.getItemCount();
				for (int i = 0; i < count; i++) {
					BookType bookType = (BookType) comboBox_1.getItemAt(i);
					if (bookType.getId() == typeId) {
						comboBox_1.setSelectedIndex(i);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void putDates(Book book) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		try {

			ResultSet resultSet = bookDao.list( book);
			while (resultSet.next()) {
				Vector Data = new Vector();
				Data.add(resultSet.getInt("id"));
				Data.add(resultSet.getString("book_name"));
			    Data.add(resultSet.getString("author"));
				Data.add(resultSet.getDouble("price"));
				Data.add(resultSet.getInt("number"));
				if (resultSet.getInt("status") == 1) {
					Data.add("上架");
				} else {
					Data.add("下架");
				}
				model.addRow(Data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	public static void main(String[] args) {

		new AdminBookEdit();

	}
}
