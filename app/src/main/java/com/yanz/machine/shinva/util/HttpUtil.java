package com.yanz.machine.shinva.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yanzi on 2016-04-20.
 */
public class HttpUtil {
        //创建连接服务器的连接
        public static final String BASE_URL =
                "http://192.168.4.244:8080/graduation";
        /*public static final String BASE_URL =
            "http://192.168.1.107:8080/graduation";*/
         public static String sendPost(String url,String params) throws IOException {
              URL realUrl = null;
              BufferedReader in = null;
              HttpURLConnection conn = null;
             JSONObject jsonObj = new JSONObject();
                PrintWriter out = null;
             String result = "";
              try {
                  //根据String——url转换成URL对象
                  realUrl = new URL(url);
                  //根据URL对象打开链接
                   conn = (HttpURLConnection) realUrl.openConnection();
                  //POST请求设置

                   conn.setRequestMethod("POST");
                  //设置请求的超时时间
                  conn.setReadTimeout(5000);
                  conn.setConnectTimeout(5000);
                  //设置请求的方法
                  conn.setRequestMethod("POST");
                  //设置请求的头
                  conn.setRequestProperty("accept","*/*");
                  conn.setRequestProperty("Connection","keep-alive");
                  conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                  conn.setRequestProperty("Content-Length",String.valueOf(params.getBytes().length));
                  conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");
                  conn.setRequestProperty("charset", "utf-8");
                  conn.setUseCaches(false);
                  //发送POST请求必须允许输出
                  conn.setDoOutput(true);
                  //设置允许输入,doinput默认值就为true，可以不设置
                  conn.setDoInput(true);
                    if (!params.trim().equals("")){
                        //获取URLConnection对象对应的输出流
                        out = new PrintWriter(conn.getOutputStream());
                        //发送请求参数
                        out.print(params);
                        //flush输出流的缓冲
                        out.flush();
                    }
                   //定义BufferedReader输入流来读取URL的响应
                  in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                  String line ;
                  while ((line=in.readLine())!=null){
                      result+=line;
                  }


              }catch (Exception e){
                e.printStackTrace();
              }
             //使用finally关闭输出流/输入流
             finally {
                  try {
                      if (out!=null){
                          out.close();
                      }
                      if (in!=null){
                          in.close();
                      }
                  }catch (IOException ex){
                      ex.printStackTrace();
                  }
              }
              return result;

         }

     }



