package com.yanz.machine.shinva.update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.yanz.machine.shinva.R;
import com.yanz.machine.shinva.entity.UpdateInfo;
import com.yanz.machine.shinva.util.FileUtils;
import com.yanz.machine.shinva.util.HttpUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;


/**
 * Created by yanzi on 2016-05-16.
 */
public class UpdateManager {
    public static AsyncHttpClient client = new AsyncHttpClient();
    final Uri uri = Uri.parse(HttpUtil.BASE_URL+"/uploadFile/shinva.apk");
    final Intent it = new Intent(Intent.ACTION_VIEW,uri);
    /*下载中*/
    private static final int DOWNLOAD = 1;
    /*下载结束*/
    private static final int DOWNLOAD_FINISH =2;
    /*跳转浏览器下载*/
    private static final int DOWNLOAD_E = 3;
    /*保存解析的XML信息*/
    //HashMap<String,String> mHashMap;
    UpdateInfo updateInfo;
    /*下载保存路径*/
    private String mSavePath;
    /*记录进度条数量*/
    private int progress;
    /*是否取消更新*/
    private boolean cancelUpdate = false;
    private Context mContext;
    /*更新进度条*/
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;
    private ProgressDialog pBar;

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                //正在下载
                case DOWNLOAD:
                    //设置进度条位置
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    //安装文件
                    installApk();
                    break;
                case DOWNLOAD_E:
                    //跳转浏览器实现下载
                    Uri uri = Uri.parse(HttpUtil.BASE_URL+"/uploadFile/shinva.apk");
                    final Intent it = new Intent(Intent.ACTION_VIEW,uri);
                    final Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            mContext.startActivity(it);
                        }
                    };
                    timer.schedule(task,1);
                    //退出程序
                    System.exit(1000);
                    break;
//                default:
//                    break;

            }
        }
    };
    public UpdateManager(Context context){
        this.mContext = context;
    }
    /*
    * 检测软件更新
    * */
    public void checkUpdate()throws  IOException{
        if (isUpdate()){
            //显示提示对话框
            //showNoticeDialog();
            //直接进行下载，不进行选择
            Toast.makeText(mContext,"正在启动下载...",Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessage(DOWNLOAD_E);

        }else {
            Toast.makeText(mContext,"已经是最新版本",Toast.LENGTH_SHORT).show();

        }
    }
    /*
    * 检查软件是否有更新版本
    *
    * @return
    * */
    private boolean isUpdate() throws IOException {
        //获取当前版本
        int versionCode = getVersionCode(mContext);
        //version.xml在服务器中的路径
        String urlStr = HttpUtil.BASE_URL+"/uploadFile/version.xml";
        System.out.println("version路径："+urlStr);
        InputStream inStream = getInputStreamFromUrl(urlStr);
        //解析xml文件
        UpdateInfoService service = new UpdateInfoService();
        try {
            //mHashMap = service.parseXml(inStream);
            updateInfo = service.parseXml(inStream);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (null!=updateInfo.getVersion()){
            int serviceCode = Integer.valueOf(updateInfo.getVersion());
            //进行版本判断
            if (serviceCode>versionCode){
                return true;
            }
        }
        return false;
    }
    /*
    * 根据URL获取输入流
    * instream
    * */
    public InputStream getInputStreamFromUrl(String urlStr)
        throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
        return urlConn.getInputStream();
    }

    /*
    * 获取软件版本号
    * */
    private static int getVersionCode(Context context){
        PackageInfo pi;
        try {
            PackageManager pm= context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),PackageManager.GET_CONFIGURATIONS);
            return pi.versionCode;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    /*
    * 显示软件更新对话框
    * */
    private void showNoticeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件更新");
        builder.setMessage("检测到新版本，立即更新吗");
        //更新
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //显示下载对话框
                //showDownloadDialog();
                mHandler.sendEmptyMessage(DOWNLOAD_E);

            }
        });
        //稍后更新
        builder.setNegativeButton("稍后更新~", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }
    /*
    * 显示软件下载对话框
    * */
    private void showDownloadDialog(){
        //构造软件下载对话框
        AlertDialog.Builder  builder = new AlertDialog.Builder(mContext);
        builder.setTitle("正在更新");
        //增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress,null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        //取消更新
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置取消状态
                cancelUpdate = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        //下载文件
        downloadApk(mContext);
    }
    /*
    * 下载apk文件
    * */
    private void downloadApk(final Context context){
        //启动新线程下载软件
        //new downloadApkThread().start();
        updateInfo.getUrl();
        System.out.println("apk路径的url地址:"+updateInfo.getUrl());
        //进度条
        /*pBar = new ProgressDialog(LoginActivity.class);
        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pBar.setTitle("正在下载");
        pBar.setMessage("请稍后...");
        pBar.setProgress(0);
        pBar.show();*/
        String[] allowedContentTypes = new String[]{".*"};
        //获取二进制apk数据
        client.get(updateInfo.getUrl(), new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                //文件夹地址
                String tempPath = "Download";
                //文件地址
                String filePath = tempPath+"/"+updateInfo.getName();
                //下载成功后需要做的工作
                Log.e("binaryData:","共下载："+binaryData.length);
                FileUtils fileUtils = new FileUtils();
                //判断文件夹是否存在
                if (!fileUtils.isFileExist((tempPath))){
                    fileUtils.createSDDir(tempPath);
                }
                //删除已下载文件
                if (fileUtils.isFileExist(filePath)){
                    fileUtils.deleteFile(filePath);
                }
                InputStream inputStream = new ByteArrayInputStream(binaryData);
                if (inputStream!=null){
                    fileUtils.write2SDFromInput(filePath,inputStream);
                    mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                    try {
                        inputStream.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                Log.i("http",error.getMessage());
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                int count = (int)((bytesWritten*1.0/totalSize)*100);

                //progress.setProgress(count);

                Log.e("下载 Progress》》》》》",bytesWritten+"/"+totalSize);
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
            }
        });
    }
    /*
    * 下载文件线程
    *
    * @author yanz
    *
    * */
   /* private class downloadApkThread extends Thread{
        @Override
        public void run(){
            try {
                //判断sd卡是否存在，是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    //获得存储卡路径
                    String sdpath = Environment.getExternalStorageDirectory()+"/";
                    mSavePath = sdpath+"download";
                    URL url = new URL(updateInfo.getUrl());
                    //创建链接
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.connect();
                    //获取文件大小
                    int length = conn.getContentLength();
                    //创建输入流
                    InputStream is = conn.getInputStream();
                    File file = new File(mSavePath);
                    //判断文件目录是否存在
                    if (!file.exists()){
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath,updateInfo.getName());
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count =0;
                    //缓存
                    byte buf[] = new byte[1024];
                    //写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        //计算进度条位置
                        progress = (int)(((float)count/length)*100);
                        //更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread<=0){
                            //下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        //写入文件
                        fos.write(buf,0,numread);
                    }while (!cancelUpdate);//点击取消终止下载
                    fos.close();
                    is.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            //取消下载对话框显示
            mDownloadDialog.dismiss();
        }
    }*/
    /*
    *安装apk
    */
    private void installApk(){
        File apkfile = new File(mSavePath,updateInfo.getName());
        if (!apkfile.exists()){
            return;
        }
        //通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://"+apkfile.toString()),"application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

}
