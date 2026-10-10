/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import jsoftware.com.jblue.model.fab.ConnectionFactory;
import jsoftware.com.jblue.sys.app.AppConfig;
import jsoftware.com.jblue.sys.app.AppFiles;
import jsoftware.com.jutil.db.JDBConnection;
import jsoftware.com.jutil.db.JDBConnectionBuilder;

/**
 *
 * @author juanp
 */
public class TestUtils {

    public static JDBConnection connection() throws FileNotFoundException, IOException, SQLException {
        String database_url = "jdbc:%s://%s:%s/%s";
        Properties properties = new Properties();
        properties.loadFromXML(new FileInputStream(AppFiles.FIL_ARC_CONFIG));
        properties.put(AppConfig.DB_URL, database_url.formatted(properties.getProperty(AppConfig.DB_MOTOR),
                properties.getProperty(AppConfig.DB_HOST),
                properties.getProperty(AppConfig.DB_PORT),
                properties.getProperty(AppConfig.DB_NAME)
        ));
        System.out.println(properties.toString());
        JDBConnectionBuilder builder = new JDBConnectionBuilder();
        builder.setUser(properties.getProperty(AppConfig.DB_USER));
        builder.setPassword(properties.getProperty(AppConfig.DB_PASSWORD));
        String url = database_url.formatted(properties.getProperty(AppConfig.DB_MOTOR),
                properties.getProperty(AppConfig.DB_HOST),
                properties.getProperty(AppConfig.DB_PORT),
                properties.getProperty(AppConfig.DB_NAME)
        );
        builder.setUrl(url);
        builder.setTimeOut(5000);
        builder.setMinimumIdle(5000);
        builder.setMaxPollSize(20);
        return ConnectionFactory.getIntance(builder).getMainConnection();
    }
}
