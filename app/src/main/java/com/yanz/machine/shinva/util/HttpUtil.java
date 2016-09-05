package com.yanz.machine.shinva.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yanzi on 2016-04-20.
 */
public class HttpUtil {
        //创建连接服务器的连接
//        public static final String BASE_URL =
//                "http://192.168.4.244:8080/graduation";
        public static final String BASE_URL =
            "http://192.168.107.49:8080/graduation";
        public static final String MAKERS[]=
                {"不限","11687|董强","11012|孙红云","11873|孙培霖","11503|王栋","11601|杨建强","11608|杨玉学","11908|张镇","11985|朱小明"};
        public static final String DEPARTMENTS[]=
                {"不限","01110405|焊工一班","01110406|焊工二班","01110410|钣金","01110411|配料","01110413|机加工",
            "01110415|组焊班","01110416|零件班","01110420|电抛光班","0111045301|一部半成品仓库","01110422|冻干机班",
                        "01110499|外邪一","01110599|外协二","01110497|外协三"};

        public static final String CHECKERS[]={
                "不限","11022|李明","11028|贾衍成","11039|徐先斌",
                "11164|赵刚","11192|徐博","11278|袁遵训","11315|杨勇",
                "11328|李庆涛","11391|张继孝","11396|蒋国春","11419|闫勇","11497|褚凤坤",
                "11527|刘斐","11534|耿涛","11599|高国庆","11805|朱明军","11892|孙庆磊"
        };
        public static final String TECHNOS[]={
                "不限","11008|牛克","111030|孙功克","111031|曾祥瑞","111032|田超","111034|张海青",
                "111154|张毓成","111155|伊鸿儒","111157|任宇风","111158|马福园","111163|邢树楠",
                "111185|贺春晓","111188|马本瑞","11390|苏立国","11402|王金刚","11405|石斌","11411|滕金广",
                "11414|吴新生","11418|刘国忠","11444|张建波","11498|陈洪文","11702|孙龙","11703|王波",
                "11726|谷晓冬","11728|高小明","11806|袁文举","11814|徐铭","11916|张金星","11917|贾小龙"
        };
        public static final String WHOMES[] = {
                "不限",
                "011101|不锈钢板材库",
                "011102|不锈钢型材库",
                "011103|黑材库",
                "011104|外购件库",
                "011105|耗材库",
                "011106|焊材库",
                "011107|刀具库",
                "011121|半成品库",
                "011123|小型灭菌器半成品库"};
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



