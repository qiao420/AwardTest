package com.dao;

import com.entity.Book;
import com.entity.BookType;
import com.entity.User;
import com.utils.Druid;
import com.utils.toolUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 蹇小丽
 */
public class BookDao {
    // 图书添加
    public Integer add(Book book) throws Exception {
        String sql = "insert into book (book_name,author,publish,price,number,status,remark) values(?,?,?,?,?,?,?)";
        Connection con = null;
        con = Druid.getConnection();
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, book.getBookName());
//        pstmt.setInt(2, Integer.parseInt(book.getType()));
        pstmt.setString(2, book.getAuthor());
        pstmt.setString(3, book.getPublish());
        pstmt.setDouble(4, book.getPrice());
        pstmt.setInt(5, book.getNumber());
        pstmt.setInt(6, book.getStatus());
        pstmt.setString(7, book.getRemark());
        return pstmt.executeUpdate();
    }
    //图书修改
    public int update(Book book)throws Exception{
        Connection con = null;
        con = Druid.getConnection();
        String sql="update book set book_name=?,author=?,publish=?,price=?,number=?,status=?,remark=? where id=?";
        PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sql);
        pstmt.setString(1, book.getBookName());
        pstmt.setInt(2, Integer.parseInt(book.getType()));
        pstmt.setString(3, book.getAuthor());
        pstmt.setString(4, book.getPublish());
        pstmt.setDouble(5, book.getPrice());
        pstmt.setInt(6, book.getNumber());
        pstmt.setInt(7, book.getStatus());
        pstmt.setString(8, book.getRemark());
        pstmt.setInt(9, book.getBookId());
        return pstmt.executeUpdate();
    }
    //查询图书类别集合
    public ResultSet list(BookType bookType)throws Exception{
        Connection con = null;
        con = Druid.getConnection();
        StringBuffer sb=new StringBuffer("select * from book_type");
        if(!toolUtil.isEmpty(bookType.getType())){
            sb.append(" and type_name like '%"+bookType.getType()+"%'");
        }
        PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
//		PreparedStatement pstmt=con.prepareStatement("select * from book_type");
        return pstmt.executeQuery();
    }
    public ResultSet list (Book book) throws Exception {

        Connection conn = null;
        conn = Druid.getConnection();
        String sql = "select * from book";
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return rs;
    }
    public ResultSet find (Book book) throws Exception {
        StringBuffer sb = new StringBuffer("select * from book");
        if(!toolUtil.isEmpty(book.getBookName())){
            sb.append(" where book_name like '%"+book.getBookName()+"%'");
        }
        if(!toolUtil.isEmpty(book.getAuthor())){
            sb.append(" where author like '%"+book.getAuthor()+"%'");
        }
        Connection conn = null;
        conn = Druid.getConnection();
        String sql = String.valueOf(sb);
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return rs;
    }
}
