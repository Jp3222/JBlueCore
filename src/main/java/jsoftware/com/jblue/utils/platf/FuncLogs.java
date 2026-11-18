/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jsoftware.com.jblue.utils.platf;

import jsoftware.com.jblue.sys.app.AppFiles;
import jsoftware.com.jutil.util.JFuncLogs;

/**
 *
 * @author juanp
 */
public class FuncLogs {

    public static void log(String modulo, String mensaje) {
        JFuncLogs.logError(AppFiles.DIR_PROG_LOG_TODAY, modulo, mensaje);
    }

    public static void log(String modulo, String metodo, String mensaje) {
        JFuncLogs.logError(AppFiles.DIR_PROG_LOG_TODAY, modulo, metodo, mensaje);
    }

    public static void log(Exception e, String modulo, String metodo, String mensaje) {
        JFuncLogs.logError(AppFiles.DIR_PROG_LOG_TODAY, e, modulo, metodo, mensaje);
    }

    public static void log(Class<?> cls, Exception e, String modulo, String metodo, String mensaje) {
        JFuncLogs.logError(AppFiles.DIR_PROG_LOG_TODAY, cls, e, modulo, metodo, mensaje);
    }

}
