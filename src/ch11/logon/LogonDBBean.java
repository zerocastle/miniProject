package ch11.logon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class LogonDBBean {
   // LogonDBBean 전역 객체 생성 <- 한개의 객체만  생성해서 공유
   private static LogonDBBean instance = new LogonDBBean();
   
   // LogonDBBean 객체를 리턴하는 메소드
   public static LogonDBBean getInstance() {
      return instance;
   }
   
   private LogonDBBean() {}
   
   //커넥션 풀에서 커넥션 객체를 얻어내는 메소드
   private Connection getConnection() throws Exception{
      Context initCtx = new InitialContext();
      Context envCtx = (Context)initCtx.lookup("java:comp/env");
      DataSource ds = (DataSource)envCtx.lookup("jdbc/myoracle");
      return ds.getConnection();
   }
   
   // 회원가입처리에서 사용하는 새 레코드 추가메소드
   public int insertMember(LogonDataBean member2) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      int signal = 0;
      
      try {
         conn=getConnection();
         
         String orgPass = member2.getPasswd();
         
         pstmt=conn.prepareStatement("insert into member values(?,?,?,?,?,?)");
         pstmt.setString(1, member2.getId());
         pstmt.setString(2, orgPass);
         pstmt.setString(3, member2.getName());
         pstmt.setTimestamp(4, member2.getReg_date());
         pstmt.setString(5, member2.getAddress());
         pstmt.setString(6, member2.getTel());
         signal = pstmt.executeUpdate();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }finally {
         if(pstmt!=null) try { pstmt.close(); }catch(SQLException e){}
         if(conn!=null) try { conn.close(); }catch(SQLException e){}
      }
      
      return signal;
   }
   
   // 로그인 폼 처리(loginPro.jsp)페이지의 사용자 인증 처리 및 
   // 회원 정보 수정/탈퇴를 사용한 인증(memberCheck.jsp)에서 사용하는 메소드
   public int userCheck(String id, String passwd) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      int x=-1;
      
      try {
         conn=getConnection();
         
         String orgPass = passwd;
         
         pstmt=conn.prepareStatement("select passwd from member where id=?");
         pstmt.setString(1, id);
         rs=pstmt.executeQuery();
         
         if(rs.next()) { // 해당아이디가 있으면 수행
            String dbpasswd = rs.getString("passwd");
            if(orgPass.equals(dbpasswd))
               x=1; // 인증성공
            else
               x=0; // 비밀번호틀림
         }else // 해당아이디 없으면 수행
            x=-1;
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }finally {
         if(rs!=null) try { rs.close(); } catch(SQLException e) {}
         if(pstmt!=null) try { pstmt.close(); } catch(SQLException e) {}
         if(conn!=null) try {conn.close();}catch(SQLException e) {}
      }
      return x;
      
   }
   
   //아이디 중복 확인에서 아이디의 중복 여부를 확인하는 메소드
   public int confirmId(String id) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      int x=-1;
      
      try {
         conn=getConnection();
         
         pstmt=conn.prepareStatement("select id from member where id = ?");
         pstmt.setString(1, id);
         rs = pstmt.executeQuery();
         
         if(rs.next())//아이디 존재
            x=1;
         else
            x=-1;
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }finally {
         if(rs!=null) try { rs.close(); } catch(SQLException e) {}
         if(pstmt!=null) try { pstmt.close(); } catch(SQLException e) {}
         if(conn!=null) try {conn.close();}catch(SQLException e) {}
      }
      
      return x;
   }
   
   //회원 정보 수정 폼을 위한 기존 가입 정보를 가져오는 메소드
   public LogonDataBean getMember(String id, String passwd) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null; 
      LogonDataBean member2 = null;
      
      try {
         conn = getConnection();
         
         String orgPass = passwd;
         
         pstmt = conn.prepareStatement("select * from member where id = ?");
         pstmt.setString(1, id);
         rs = pstmt.executeQuery();
         
         if(rs.next()) { // 해당 아이디에 대한 레코드가 존재
            String dbpasswd = rs.getString("passwd");
            // 사용자가 입력한 비밀번호와 테이블의 비밀번호가 같으면 수행
            if(orgPass.equals(dbpasswd)){
               member2 = new LogonDataBean();
               member2.setId(rs.getString("id"));
               member2.setName(rs.getString("name"));
               member2.setReg_date(rs.getTimestamp("reg_date"));
               member2.setAddress(rs.getString("address"));
               member2.setTel(rs.getString("tel"));
            }
         }
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }finally {
         if(rs!=null) try { rs.close(); } catch(SQLException e) {}
         if(pstmt!=null) try { pstmt.close(); } catch(SQLException e) {}
         if(conn!=null) try {conn.close();}catch(SQLException e) {}
      }
      
      return member2; // 데이터 저장빈 객체 member2 리턴
   }
   
   // 회원 정보 수정 처리(modifyPro.jsp)에서 회원 정보 수정을 처리하는 메소드
   public int updateMember(LogonDataBean member2) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      int x = -1;
      
      try {
         conn = getConnection();
         
         String orgPass = member2.getPasswd();
         
         pstmt = conn.prepareStatement("select passwd from member where id = ?");
         pstmt.setString(1, member2.getId());
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            String dbpasswd = rs.getString("passwd");
            
               pstmt = conn.prepareStatement("update member set name = ?, passwd = ? , address = ? , tel =? , reg_date =? where id =?");
               pstmt.setString(1, member2.getName());
               pstmt.setString(2, member2.getPasswd());
               pstmt.setString(3, member2.getAddress());
               pstmt.setString(4, member2.getTel());
               pstmt.setTimestamp(5, member2.getReg_date());
               pstmt.setString(6, member2.getId());
               x = pstmt.executeUpdate();
         }
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }finally {
         if(rs!=null) try { rs.close(); } catch(SQLException e) {}
         if(pstmt!=null) try { pstmt.close(); } catch(SQLException e) {}
         if(conn!=null) try {conn.close();}catch(SQLException e) {}
      }
      
      return x;
   }
   
   //회원 탈퇴 처리(deletePro.jsp)에서 회원 정보를 삭제하는 메소드
   public int deleteMember(String id, String passwd) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      int x = -1;
      
      try {
         conn = getConnection();
         
         String orgPass = passwd;
         
         pstmt = conn.prepareStatement("select passwd from member where id = ?");
         pstmt.setString(1, id);
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            String dbpasswd = rs.getString("passwd");
            if(orgPass.equals(dbpasswd)) {
               pstmt = conn.prepareStatement("delete from member where id = ?");
               pstmt.setString(1, id);
               pstmt.executeUpdate();
               x=1;
            }else
               x=0;
         }
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }finally{
         if(rs!=null) try { rs.close(); } catch(SQLException e) {}
         if(pstmt!=null) try { pstmt.close(); } catch(SQLException e) {}
         if(conn!=null) try {conn.close();}catch(SQLException e) {}
      }
      
      return x;
   }
}