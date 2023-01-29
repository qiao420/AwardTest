package com.dao;

import com.entity.User;
import com.utils.Druid;
import com.utils.toolUtil;

import java.sql.*;

public class UserDao {
    /**
     * 乔世聪
     * 登录：查询用户
     */
    public User login (User user) throws Exception {
        User userR = null;
        Connection conn = null;
            conn = Druid.getConnection();
            String sql = "select * from user where username = ? and password = ? and role = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1,user.getUserName());
        pstm.setString(2,user.getPassword());
        pstm.setInt(3,user.getRole());
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            userR = new User();
            userR.setUserId(rs.getInt("id"));
            userR.setUserName(rs.getString("username"));
            userR.setSex(rs.getString("sex"));
            userR.setPhone(rs.getString("phone"));
        }
return userR;}
/**
 * 乔世聪
 * 查找用户是否存在
 */
    public boolean find(Connection conn ,User user) throws SQLException {
        String sql = "select username from user where username =?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1,user.getUserName());
        ResultSet rs = psmt.executeQuery();
        if(rs.next()){
            return true; //存在返回true
        }else {
            return false;
        }
    }

    /**
     * 乔世聪
     *注册：添加用户
     */
    public int reg(Connection conn,User user) throws SQLException {
        String sql = "insert into user(username,password,role,sex,phone) values (?,?,?,?,?)";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1,user.getUserName());
        psmt.setString(2,user.getPassword());
        psmt.setInt(3,user.getRole());
        psmt.setString(4,user.getSex());
        psmt.setString(5,user.getPhone());
        int rs = psmt.executeUpdate();
        return rs;

        }

    /**
     * 尹科蘭
      * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public int update(Connection con,User user)throws Exception{
        String sql="update user set username=?,password=?,sex=?,phone=? where id=?";        //根据id修改
        PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sql);
        pstmt.setString(1, user.getUserName());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getSex());
        pstmt.setString(4, user.getPhone());
        pstmt.setInt(5, user.getUserId());
        return pstmt.executeUpdate();           //返回操作数据库更新的行数
    }

    /**\
     * 尹科蘭
     * @param params
     * @return
     */
    public int deldate(Object[] params){
        try {
            Connection conn=Druid.getConnection();
            String sql="delete from user where id=?";           //根据id删除
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            for(int i=0;i<params.length;i++){
                preparedStatement.setObject(i+1,params[i]);         //填充占位符，批量删除
            }
            int i=preparedStatement.executeUpdate();
            return i;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     * 尹科蘭
     * @param con
     * @param user
     * @return
     * @throws Exception
     */
    public ResultSet list(Connection con,User user)throws Exception{
        StringBuffer sb=new StringBuffer("select * from user where role = 1");      //追加语句用sb
        if(!toolUtil.isEmpty(user.getUserName())){
            sb.append(" and username like '%"+user.getUserName()+"%'");
        }
        PreparedStatement pstmt=con.prepareStatement(sb.toString());
        return pstmt.executeQuery();
    }
}
