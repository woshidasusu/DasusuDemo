package com.easiio.openrtspdemo.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class CommandUtils {

    /** 执行 shell 脚本命令 ,只能执行权限内的单条命令*/
    public static String simpleExec(String cmd) {
		/* 获取执行工具 */
        Process process = null;
		/* 存放脚本执行结果 */
        String result  = "";
        try {
        	/* 获取运行时环境 */
            Runtime runtime = Runtime.getRuntime();
        	/* 执行脚本 */
            process = runtime.exec(cmd);
            /* 获取脚本结果的输入流 */
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            /* 逐行读取脚本执行结果 */
            while ((line = br.readLine()) != null) {
                result += line + '\n';
            }
            br.close();

            br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = br.readLine()) != null) {
                result += line + '\n';
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null){
                process.destroy();
                process = null;
            }
        }
        return result;
    }

    /**
     * 支持多条命令执行，支持选择执行的路径
     * @param com
     * @param cdPath
     * @return
     */
    public static String complexExec(String com,String cdPath){
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec("/system/bin/sh", null, new File("/system/bin")); // android中使用
            // proc = Runtime.getRuntime().exec("/bin/bash", null, new File("/bin")); 			//Linux中使用
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (proc != null) {
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())),true);
            if (cdPath != null){
                out.println("cd " + cdPath);
                out.flush();
            }
            out.println(com);
            out.flush();
            out.println("exit");
            out.flush();
            out.close();

            String result = "";
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    result += line + '\n';
                }
                in.close();

                in = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
                while ((line = in.readLine()) != null) {
                    result += line + '\n';
                }
                System.out.println(result);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                proc.destroy();
                proc = null;
            }
            return result;
        }
        return null;
    }
}
