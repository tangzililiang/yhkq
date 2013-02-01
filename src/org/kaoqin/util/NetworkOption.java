/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kaoqin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author lenovo
 */
public class NetworkOption {
      
      public static void modifyIPAddress(String ip) throws IOException {
         String command = "netsh  interface ip set address name=\""+Configer.getProporty("adapterName") +"\" source=static addr="+ip+" mask="+Configer.getProporty("mask") +" gateway="+Configer.getProporty("gateWay");
         Process pro = Runtime.getRuntime().exec(command);
      }
      public static void restore() throws IOException{
         String command = "netsh  interface ip set address name=\""+Configer.getProporty("adapterName") +"\" source=static addr="+Configer.getProporty("localIP")+" mask="+Configer.getProporty("mask") +" gateway="+Configer.getProporty("gateWay");
         Process pro = Runtime.getRuntime().exec(command);
      }
//    public static void main(String[] args) throws IOException {
//        Process pro = Runtime.getRuntime().exec("ipconfig");
//        BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
//        List<String> rowList = new ArrayList();
//        String temp;
//        while ((temp = br.readLine()) != null) {
//            rowList.add(temp);
//        }
//        for (String str : rowList) {
//            if (str.contains("Subnet Mask")||str.contains("子网掩码")) {
//                Matcher mc = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}").matcher(str);
//                if (mc.find()) {
//                    System.out.println("子网掩码：" + mc.group());
//                } else {
//                    System.out.println("子网掩码为空");
//                }
//            };
//            if (str.contains("Default Gateway")||str.contains("默认网关")) {
//                Matcher mc = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}").matcher(str);
//                if (mc.find()) {
//                    System.out.println("默认网关：" + mc.group());
//                } else {
//                    System.out.println("默认网关为空");
//                }
//                return;
//            };
//        }
//
//    }
}
