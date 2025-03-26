package com.grupo8.role.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class OracleConnectionUtil {

    public static Connection getConnection() throws Exception {
        String walletPath = "/home/site/wwwroot/wallet";
        
        System.setProperty("oracle.net.tns_admin", walletPath);
        System.setProperty("oracle.net.ssl_server_dn_match", "true");

        String jdbcUrl = "jdbc:oracle:thin:@pb06o46xmpfju1b9_tp?TNS_ADMIN=" + walletPath;
        String username = "APICONSUMER";
        String password = "!&fddE>X1hMagb3";
        
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

}
