package com.yanz.machine.shinva;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.yanz.machine.shinva.Adapter.Adapter_GridView;
import com.yanz.machine.shinva.Adapter.MyGridAdapter;
import com.yanz.machine.shinva.MyView.MyGridView;
import com.yanz.machine.shinva.entity.UpdateInfo;
import com.yanz.machine.shinva.logisticsRecive.LogisticsMenuActivity;
import com.yanz.machine.shinva.orderinfo.OrderInfoSearchActivity;
import com.yanz.machine.shinva.planSearch.InputPlanActivity;
import com.yanz.machine.shinva.planSearch.PlanTrackActivity;
import com.yanz.machine.shinva.rdRecord.RdRecordInActivity;
import com.yanz.machine.shinva.rdRecord.RdRecordOutActivity;
import com.yanz.machine.shinva.unqualifyPartDeal.UnqualifyPartDealActivity;
import com.yanz.machine.shinva.update.UpdateManager;
import com.yanz.machine.shinva.util.ClickUtil;
import com.yanz.machine.shinva.view.AbOnItemClickListener;
import com.yanz.machine.shinva.view.AbSlidingPlayView;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    //顶部标题栏
    private TextView tv_top_title;

    //第一个search操作
    private MyGridView menuGridViewSearch;
    //第二个menu操作
    private MyGridView menuGridView;
    //扫一扫
    private ImageView iv_shao;
    //输一输
    private ImageView iv_shu;
    //首页轮播
    private AbSlidingPlayView viewPage;
    //分类九宫格的资源文件

    //第一个search操作
    public int[] img_menu_classify_search = {
            R.drawable.menu_produce_track,
            R.drawable.menu_stock_search,
            R.drawable.menu_product_factory,
            R.drawable.menu_quality_accident,
            R.drawable.menu_warehouse_ledger,
            R.drawable.menu_delivery_ledger
    };
    public String[] img_menu_text_search = {
            "生产跟踪",
            "物料查询",
            "成品订单",
            "质量事故",
            "入库台账",
            "出库台账"
    };
    //第二个menu操作
    public int[] img_menu_classify = {
            R.drawable.menu_work_dispatching,
            R.drawable.menu_work_report,
            R.drawable.menu_work_logistics,
            R.drawable.menu_work_receive,
            R.drawable.menu_work_inventory
    };
    public String[] img_menu_text = {
            "二次派工",
            "检验汇报",
            "物流周转",
            "周转接收",
            "库存盘点"
    };

    //存储首页轮播的界面
    private ArrayList<View> allListView;
    //首页轮播的界面的资源
    private int[] resId = {
            R.drawable.viewpage1,
            R.drawable.viewpage4,
            R.drawable.viewpage5,
            R.drawable.viewpage7,
            R.drawable.viewpage8};

    //定义view接收后，初始化init



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);

        return view;
    }



    //初始化
    private void initView(View view){
        iv_shao = (ImageView) view.findViewById(R.id.iv_shao);
        iv_shao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到二维码扫描
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                //startActivity(intent);
                startActivityForResult(intent,0);
            }
        });
        tv_top_title= (TextView) view.findViewById(R.id.tv_top_title);
        tv_top_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*//跳转搜索界面
                Intent intent = new Intent(getActivity(),TestActivity.class);
                startActivity(intent);*/
                PackageManager pm = getContext().getPackageManager();
                PackageInfo pi = null;
                try {
                    pi = pm.getPackageInfo(getContext().getPackageName(),0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                String versionName = pi.versionName;
                new AlertDialog.Builder(getActivity())
                        .setIcon(getResources().getDrawable(R.drawable.waring_icon))
                        .setTitle("SHINVA")
                        .setMessage("新华医疗机械制造厂 \n生产管理系统\n版本信息: "+versionName)
                        .create().show();

            }
        });
        iv_shu = (ImageView) view.findViewById(R.id.iv_refresh);
        iv_shu.setOnClickListener(new ClickUtil() {
            @Override
            protected void onNoDoubleClick(View view) {
                Intent intent = new Intent(getActivity(), InputPlanActivity.class);
                startActivity(intent);
            }
        });

        /*gridView_classify = (GridView) view.findViewById(R.id.gv_menu_search);
        gridView_classify.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter_gridView_classify = new Adapter_GridView(getActivity(),pic_patch_classify);
        gridView_classify.setAdapter(adapter_gridView_classify);*/
        //第一个search操作
        menuGridViewSearch = (MyGridView) view.findViewById(R.id.gv_menu_search);
        menuGridViewSearch.setAdapter(new MyGridAdapter(getActivity(),img_menu_classify_search,img_menu_text_search));
        //第二个
        menuGridView = (MyGridView) view.findViewById(R.id.gv_menu);
        menuGridView.setAdapter(new MyGridAdapter(getActivity(),img_menu_classify,img_menu_text));
        //公告展示栏
        viewPage = (AbSlidingPlayView) view.findViewById(R.id.viewPager_menu);
        //设置播放方式为顺序播放
        viewPage.setPlayType(1);
        //设置播放间隔时间
        viewPage.setSleepTime(3000);
        menuGridViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position){
                    case 0:
//                        Toast.makeText(getActivity(),"生产跟踪功能功能暂未开放",Toast.LENGTH_SHORT).show();
                        intent.setClass(getActivity(), PlanTrackActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent.setClass(getActivity(), SearchConditionActivity.class);
                        intent.putExtra("webRequest","stock");
                        startActivity(intent);
                        break;
                    case 2:
                        intent.setClass(getActivity(), OrderInfoSearchActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
//                        Toast.makeText(getActivity(),"质量事故功能功能暂未开放",Toast.LENGTH_SHORT).show();
                        intent.setClass(getActivity(), UnqualifyPartDealActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent.setClass(getActivity(), RdRecordInActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent.setClass(getActivity(), RdRecordOutActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        menuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position){
                    case 0:
                        Toast.makeText(getActivity(),"二次派工功能暂未开放",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(),"检验汇报功能暂未开放",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(),"物流周转功能功能暂未开放",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //Toast.makeText(getActivity(),"周转接收功能功能暂未开放",Toast.LENGTH_SHORT).show();
                        intent.setClass(getActivity(), LogisticsMenuActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        Toast.makeText(getActivity(),"库存盘点功能功能暂未开放",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        /*gridView_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //点击事件
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position){
                    case 0:
                        intent.setClass(getActivity(), SearchConditionActivity.class);
                        intent.putExtra("webRequest","stock");
                        startActivity(intent);
                        break;
                    case 1:

                }
            }
        });*/

        initViewPager();
    }

    private void initViewPager(){
        if (allListView!=null){
            allListView.clear();
            allListView = null;
        }
        allListView = new ArrayList<View>();
        for (int i = 0;i<resId.length;i++){
            //导入ViewPage布局
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.pic_item,null);
            ImageView imageView = (ImageView)view.findViewById(R.id.pic_item);
            imageView.setImageResource(resId[i]);

            allListView.add(view);
        }
        viewPage.addViews(allListView);
        //轮播效果
        viewPage.startPlay();
        viewPage.setOnItemClickListener(new AbOnItemClickListener() {
            @Override
            public void onClick(int position) {
                //跳转
                Toast.makeText(getActivity(),"功能暂未开放",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //二维码扫描跳转到生产计划查询界面
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == -1){
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");

            if (result!=null){
                Intent intent = new Intent();
                intent.putExtra("planCode",result);
                //改动intent.setClass(getActivity(), PlanSearchActivity.class);
                intent.setClass(getActivity(), PlanSearchActivity.class);
                startActivity(intent);
            }
        }
    }
}
