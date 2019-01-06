package com.chris.esb.common.utils;

import com.chris.esb.common.model.JDBCParam;
import com.chris.esb.common.service.SQLParamInjector;
import org.apache.log4j.Logger;

import java.sql.*;

public final class JDBCUtils4SQLServer {

    private static Logger log = Logger.getLogger(JDBCUtils4SQLServer.class);

    private static Connection conn = null;

    private static PreparedStatement ps = null;

    private static ResultSet rs = null;


    private static Connection getConnection(JDBCParam jdbcParam) {
        try {
            Class.forName(jdbcParam.getDriver());
            conn = DriverManager.getConnection(jdbcParam.getUrl(), jdbcParam.getUser(), jdbcParam.getPassword());// 获得连接对象
            log.info("成功加载 SQL Server 驱动程序");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("找不到 SQL Server 驱动程序, 原因：" + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("创建 SQL Server 连接异常, 原因：" + e.getMessage());
        }
        return conn;
    }

    public static ResultSet select(JDBCParam jdbcParam) throws Exception {
        try {
            conn = getConnection(jdbcParam);
            ps = conn.prepareStatement(jdbcParam.getSql());
            rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询 SQL 异常!原因：" + e.getMessage());
            throw new Exception("查询 SQL 异常!");
        }
    }

    public static void saveOrUpdate(JDBCParam jdbcParam, SQLParamInjector injector) throws Exception {
        try {
            conn = getConnection(jdbcParam);
            ps = conn.prepareStatement(jdbcParam.getSql());
            injector.injectSQLParam(ps);
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("更新 SQL 操作异常，原因：" + e.getMessage());
            throw new SQLException("update data Exception: " + e.getMessage());
        } finally {
            close();
        }
    }

    private static void close() throws Exception {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ps close exception: " + e.getMessage());
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("conn close exception: " + e.getMessage());
        }
    }
}