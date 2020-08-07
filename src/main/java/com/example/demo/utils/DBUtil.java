package com.example.demo.utils;

import com.example.demo.bean.Reduction;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.sql.*;
import java.util.Date;

/**
 * @author Administrator
 */
@Slf4j
public class DBUtil {

    private DBUtil() {
    }

    /**
     * 还原SQL Server数据库用MDF文件
     *
     * @param reduction
     * @return
     * @throws SQLException
     */
    public static  boolean reductionMDF(Reduction reduction, String ku, String localpath, String filename)
            throws Exception {
        boolean falg = false;
        Connection reductionConn = null;
        try {
            reductionConn = getSqlServerConnect(reduction.getIp(), reduction.getPort(),
                    "", reduction.getUsename(), reduction.getPassword());

            String realfile = (localpath + File.separator + filename).replaceAll("/", "\\\\");
            String sql = "CREATE DATABASE " + ku + " ON (FILENAME = '" + realfile + "') " + " FOR ATTACH;";
            Statement st = reductionConn.createStatement();
            st.execute(sql);
            falg = true;

        } catch (Exception e) {
            log.error(e.toString());
            log.info("MDF文件还原失败");
            throw new Exception("还原mdf文件错误" + e.getMessage());
        } finally {
            reductionConn.close();
        }
        return falg;
    }

    public static synchronized boolean reductionMsSql(Reduction reduction, String ku, String localpath, String filename, boolean has) throws Exception {
        boolean falg = false;
        Connection reductionConn = null;
        try {

            reductionConn = (Connection) DBUtil.getSqlServerConnect(reduction.getIp(), reduction.getPort(), "", reduction.getUsename(), reduction.getPassword());
            if (null == reductionConn) {
                throw new Exception("建立还原库连接失败");
            }
            int res = 0;
            //判断数据库是否存在
            String selectSql = "select  * from sys.databases where name= " + "'" + ku + "'";
            PreparedStatement preparedStatement = reductionConn.prepareStatement(selectSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                has = true;
                res = 1;
            }
            if (!has) {
                String createSql = "create database [" + ku + "]";
                log.info("还原数据库");
                res = createDataBase(reductionConn, createSql);
            }
            if (res >= 0) {
                if (!has) {
                    log.info("数据库[" + ku + "]创建成功");

                }
                falg = mssqlRedction(reductionConn, ku, localpath, filename);

            } else {

                log.info("数据库[" + ku + "]创建失败");

            }
        } catch (Exception e) {
            log.error(e.toString());
            log.info("数据还原失败");
            throw e;
        } finally {
            reductionConn.close();
        }

        return falg;
    }

    public static boolean mssqlRedction(Connection conn, String databasename, String localpath, String filename) throws Exception {
        boolean falg = false;
        Statement st = null;
        try {
            st = conn.createStatement();
            String sql = "use master;";
            st.execute(sql);
            sql = "ALTER DATABASE [" + databasename + "] SET OFFLINE WITH ROLLBACK IMMEDIATE;";
            st.execute(sql);
            executeRestoreBak(conn, localpath, filename, databasename);
            sql = "ALTER  database  [" + databasename + "]  set  online;";
            st.execute(sql);
            sql = "use [" + databasename + "];";
            st.execute(sql);
            falg = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return falg;
    }

    private static void executeRestoreBak(Connection conn, String filepath, String filename, String databaseName)
            throws Exception {
        Statement st = null;
        if (ObjectUtil.isEmpty(filepath)) {
            throw new RuntimeException("数据库还原文件不存在");
        }
        String mdfFilePath = (filepath + File.separator + databaseName + ".mdf").replaceAll("/", "\\\\");
        String logFilePath = (filepath + File.separator + databaseName + "_log.LDF").replaceAll("/", "\\\\");
        String realfile = (filepath + File.separator + filename).replaceAll("/", "\\\\");
        String ji = "";
        String old = "";
        try {
            String mdfName = "";
            String logName = "";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery("RESTORE HEADERONLY FROM DISK='" + realfile + "'");
            while (rs.next()) {
                if (StringHandler.isEmptyOrNull(old)) {
                    old = rs.getString(12);
                    ji = rs.getString(6);
                }
                String date = rs.getString(12);
                java.util.Date olddate = DateHandler.stringToDates(old, "yyyy-MM-dd HH:mm:ss");
                Date newdate = DateHandler.stringToDates(date, "yyyy-MM-dd HH:mm:ss");
                if ((newdate.getTime() - olddate.getTime()) >= 0) {
                    ji = rs.getString(6);
                    old = date;
                }
            }
            st = conn.createStatement();
            rs = st.executeQuery("restore filelistonly from disk='" + realfile + "'");
            while (rs.next()) {
                String name = rs.getString(1);
                String type = rs.getString(3);
                if ("D".equalsIgnoreCase(type)) {
                    // 数据文件
                    mdfName = name;
                } else if ("L".equalsIgnoreCase(type)) {
                    // 日志文件
                    logName = name;
                }
            }
            // 注：在还原时，必须使用'\' 来分割数据文件与日志文件的路径，
            // 若用'/'则出现文件激活错误。（物理文件名 E:/wps/20170307094545重庆wps测试安徽.mdf
            // 可能不正确。请诊断并更正其他错误，然后重试此操作）
            String sql = "restore database [" + databaseName + "] from disk='" + realfile + "' WITH FILE = " + ji + ","
                    + "	MOVE '" + mdfName + "' TO '" + mdfFilePath + "'," + " MOVE '" + logName + "' TO '" + logFilePath
                    + "'," + " REPLACE;";
            // 返回页面的sql与保存在数据库中的sql
            st = conn.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            throw e;
        }

    }

    public static int createDataBase(Connection conn, String sql) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            int i = ps.executeUpdate();
            return i;
        } catch (Exception e) {
            throw new Exception("创建数据库失败" + e.getMessage());
        }
    }

    /**
     * 获取数据库连接
     *
     * @param driverClass
     * @param url
     * @param userName
     * @param password
     * @return
     */
    public static Connection getConnection(String driverClass, String url, String userName, String password) {
        try {
            Class.forName(driverClass).newInstance();
            Connection connection = (Connection) DriverManager.getConnection(url, userName, password);
            return connection;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取oracle的数据库连接
     *
     * @param ip       数据库ip地址
     * @param port     端口
     * @param instance 实例名称
     * @param userName 用户名
     * @param password 用户密码
     * @return
     */
    public static Connection getOracleConnection(String ip, int port, String instance, String userName, String password) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + instance;
            Connection conn = DriverManager.getConnection(url, userName, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取sqlserver的数据库连接
     *
     * @param ip           数据库ip
     * @param port         数据库端口
     * @param databaseName 数据库名称
     * @param userName     用户名
     * @param password     用户密码
     * @return
     */
    public static Connection getSqlServerConnect(String ip, String port, String databaseName, String userName, String password) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://" + ip + ":" + port + ";DatabaseName=" + databaseName;
            Connection conn = DriverManager.getConnection(url, userName, password);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "");
            return null;
        }
    }

    /**
     * 获取mysql的数据库连接
     *
     * @param ip           数据库ip
     * @param port         数据库端口
     * @param databaseName 数据库名称
     * @param userName     数据库用户名
     * @param password     数据库密码
     * @return
     */
    public static Connection getMySqlConnection(String ip, int port, String databaseName, String userName, String password) {
        try {
            String driverClass = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://" + ip + ":" + port + "/" + databaseName + "?useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&allowMultiQueries=true";
            Connection conn = DBUtil.getConnection(driverClass, url, userName, password);
            return conn;
        } catch (Exception e) {
            return null;
        }
    }

    public static Connection getPostgreSqlConnection(String ip, Integer port, String databaseName, String instance, String usename, String pwd) {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://" + ip + ":" + port + "/" + databaseName;
            if (instance != null && !instance.equals("")) {
                url = url + "?currentSchema=" + instance;
            }
            Connection conn = DriverManager.getConnection(url, usename, pwd);
            return conn;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 关闭数据库资源
     *
     * @param conn
     * @param ps
     * @param rs
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        closeResultSet(rs);
        closePreparedStatement(ps);
        closeConn(conn);
    }

    /**
     * 关闭数据库的资源
     *
     * @param conn
     * @param
     * @param rs
     */
    public static void close(Connection conn, Statement st, ResultSet rs) {
        closeResultSet(rs);
        closeStatement(st);
        closeConn(conn);
    }

    /**
     * 关闭数据库链接
     *
     * @param conn
     */
    public static void closeConn(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 关闭Statement
     *
     * @param st
     */
    public static void closeStatement(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 关闭PreparedStatement
     *
     * @param ps
     */
    public static void closePreparedStatement(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 关闭ResultSet
     *
     * @param rs
     */
    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
        }
    }


}
