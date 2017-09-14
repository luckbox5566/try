package enjoyor.enjoyorzemobilehealth.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bben.view.BryzBbenActivity;
import com.example.my_xml.StringXmlParser;
import com.example.my_xml.entities.BRLB;
import com.example.my_xml.handlers.MyXmlHandler;
import com.jaeger.library.StatusBarUtil;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enjoyor.enjoyorzemobilehealth.R;
import enjoyor.enjoyorzemobilehealth.activity.danger.DangerActivity;
import enjoyor.enjoyorzemobilehealth.activity.infosearch.BrxxcxActivity;
import enjoyor.enjoyorzemobilehealth.activity.infosearch.JcjgcxActivity;
import enjoyor.enjoyorzemobilehealth.activity.infosearch.JyjgcxActivity;
import enjoyor.enjoyorzemobilehealth.activity.infosearch.XhcxActivity;
import enjoyor.enjoyorzemobilehealth.application.MyApplication;
import enjoyor.enjoyorzemobilehealth.entities.BRLBBC;
import enjoyor.enjoyorzemobilehealth.utlis.Constant;
import my_network.NetWork;
import my_network.ZhierCall;

import static enjoyor.enjoyorzemobilehealth.application.MyApplication.END;
import static enjoyor.enjoyorzemobilehealth.application.MyApplication.NODE;


public class BrlbActivity extends BaseActivity {

    @BindView(R.id.listView)
    SwipeMenuListView listView;
    //    @BindView(R.id.toolbar)
    //    Toolbar toolbar;
    @BindView(R.id.back)
    ImageView back;
    List<String> list = new ArrayList<>();
    SwipeMenuCreator creator;
    ZhierCall zhierCall;
    List<BRLB> listBRLB = new ArrayList<>();
    List<BRLBBC> listBRLBBC = new ArrayList<>();
    /* @BindView(R.id.picture)
     ImageView imageView;
     @BindView(R.id.name)
     TextView textView1;
     @BindView(R.id.number)
     TextView textView2;
     @BindView(R.id.content)
     TextView textView3;*/
    TextView textView;

    private Intent intent;
    private String sex = "";
    private String xingming = "";
    private String ruyuanDate = "";
    private String age = "";
    private String bingrenid = "";
    private String bingrenzyid = "";
    private String chuangweihao = "";

    private OnGetSelectedPatientInfoListener getSelectedPatientInfo;
    private boolean isFromHomePage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brlb);
        ButterKnife.bind(this);

        SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
        String name = preferences2.getString("id", "");
        final String canshu = preferences2.getString("bqdm", "");

        System.out.print("请求病人列表" + canshu + "\n");
        createXml();
        zhierCall = (new ZhierCall())
                .setId(name)
                .setNumber("0500101")
                .setMessage(NetWork.BINGREN_XX)
                .setCanshu(canshu)
                .setContext(this)
                .setPort(5000)
                .build();


        zhierCall.start(new NetWork.SocketResult() {
            @Override
            public void success(String data) {
                StringXmlParser parser = new StringXmlParser(xmlHandler,
                        new Class[]{BRLB.class});
                parser.parse(data);
            }

            @Override
            public void fail(String info) {
                // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
            }
        });


        setListView();
        //setToolBar();

        list.add("xxx");
        list.add("xxx");
        list.add("xxx");
        list.add("xxx");
        list.add("xxx");
        list.add("xxx");
        list.add("xxx");
        list.add("xxx");
        list.add("xxx");
        list.add("xxx");
        list.add("xxx");
        list.add("xxx");

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        SwipeMenuItem openItem = menu.getMenuItem(0);
                        openItem.setBackground(new ColorDrawable(Color.rgb(0x53, 0x9E,
                                0xF3)));
                        openItem.setTitleColor(Color.WHITE);
                        Drawable drawable1 = getResources().getDrawable(R.drawable.icon_pgfw66_02);
                        drawable1 = zoomDrawable(drawable1, 60, 60);
                        openItem.setIcon(drawable1);
                        SwipeMenu hh = menu;
                        //listView.setMenuCreator(null);
                        // handler.sendMessage(new Message());
                        Toast.makeText(BrlbActivity.this, openItem.getTitle(), Toast.LENGTH_SHORT).show();

                        listView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
                            @Override
                            public void onMenuOpen(int position) {

                            }

                            @Override
                            public void onMenuClose(int position) {

                            }
                        });
                        break;
                    case 1:
                        // delete
                        Intent intenttz = new Intent(BrlbActivity.this,ShengMingTiZhengLuRuActivity.class);
                        startActivity(intenttz);
                        finish();
                        break;
                    case 2:
                        Intent intent = new Intent(BrlbActivity.this, BryzBbenActivity.class);
                        intent.putExtra("xingming", listBRLB.get(position).getXINGMING());
                        intent.putExtra("xingbie", listBRLB.get(position).getXINGBIE());
                        intent.putExtra("chuanghao", listBRLB.get(position).getCHUANGWEIHAO());
                        startActivity(intent);
                        finish();
                        MyApplication.getInstance().setChoosebr(position);
                        break;
                    case 3:
                        Intent intent1 = new Intent(BrlbActivity.this, BrxxActivity.class);
                        MyApplication.getInstance().setOther_brlb(listBRLB.get(position));
                        intent1.putExtra("ccc", listBRLB.get(position).getXINGMING());
                        startActivity(intent1);
                        //finish();
                        break;


                }
                return true;
            }
        });

        setStatusBar();

        textView = (TextView) findViewById(R.id.nianlin);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = getIntent();
                BRLB brlb = listBRLB.get(position);
                sex = brlb.getXINGBIE();
                xingming = brlb.getXINGMING();
                ruyuanDate = brlb.getRUYUANSJ();
                age = brlb.getNIANLING();
                bingrenid = brlb.getBINGRENID();
                bingrenzyid = brlb.getBINGRENZYID();
                chuangweihao = brlb.getCHUANGWEIHAO();

                String x = null;
                x = intent.getStringExtra("which");
                if (x != null) {
                    switch (x) {
                        case "1":
                            MyApplication.getInstance().setOther_brlb(null);
                            MyApplication.getInstance().setOther_brlb(listBRLB.get(position));
                            startActivity(new Intent(BrlbActivity.this, BrxxActivity.class));
                            finish();
                            break;
                        case "2":
                            Intent intent1 = new Intent(BrlbActivity.this, BryzBbenActivity.class);
                            intent1.putExtra("xingming", listBRLB.get(position).getXINGMING());
                            intent1.putExtra("xingbie", listBRLB.get(position).getXINGBIE());
                            intent1.putExtra("chuanghao", listBRLB.get(position).getCHUANGWEIHAO());
                            startActivity(intent1);
                            finish();
                            MyApplication.getInstance().setChoosebr(position);
                            break;
                        case "3":
                            Intent intent2 = new Intent(BrlbActivity.this, BryzBbenActivity.class);
                            intent2.putExtra("ooo", listBRLB.get(position).getBINGRENID());
                            startActivity(intent2);
                            finish();
                            break;
                        case "4":
                            MyApplication.getInstance().setOther_brlb(null);
                            MyApplication.getInstance().setOther_brlb(listBRLB.get(position));
                            startActivity(new Intent(BrlbActivity.this,BrxxcxActivity.class));
                            finish();
                            break;
                        case "5":
                            MyApplication.getInstance().setOther_brlb(null);
                            MyApplication.getInstance().setOther_brlb(listBRLB.get(position));
                            Intent intent55=new Intent(BrlbActivity.this,JyjgcxActivity.class);
                            intent55.putExtra("BINGRENZYID",listBRLB.get(position).getBINGRENZYID());
                            intent55.putExtra("RUYUANSJ",listBRLB.get(position).getRUYUANSJ());
                            intent55.putExtra("CHUANGHAO",listBRLB.get(position).getCHUANGWEIHAO());
                            intent55.putExtra("XINGMING",listBRLB.get(position).getXINGMING());
                            intent55.putExtra("XINGBIE",listBRLB.get(position).getXINGBIE());

                            startActivity(intent55);
                            finish();
                            break;
                        case "6":
                            MyApplication.getInstance().setOther_brlb(null);
                            MyApplication.getInstance().setOther_brlb(listBRLB.get(position));
                            Intent intent6=new Intent(BrlbActivity.this,JcjgcxActivity.class);
                            intent6.putExtra("BINGRENZYID",listBRLB.get(position).getBINGRENZYID());
                            intent6.putExtra("RUYUANSJ",listBRLB.get(position).getRUYUANSJ());
                            intent6.putExtra("CHUANGHAO",listBRLB.get(position).getCHUANGWEIHAO());
                            intent6.putExtra("XINGMING",listBRLB.get(position).getXINGMING());
                            intent6.putExtra("XINGBIE",listBRLB.get(position).getXINGBIE());

                            startActivity(intent6);
                            finish();
                            break;
                        case "SMTZ":
                            Intent intent3 = new Intent(BrlbActivity.this, ShengMingTiZhengLuRuActivity.class);
                            intent3.putExtra("position", position);
                            startActivity(intent3);
                            finish();
                            break;
                        case "TWD":
                            Intent intent4 = new Intent(BrlbActivity.this, TemperatureChartActivity.class);
                            intent4.putExtra("position", position);
                            startActivity(intent4);
                            finish();
                            break;
                        case "HLD":
                            Intent intent5 = new Intent(BrlbActivity.this, HuLiDanActivity.class);
                            intent5.putExtra("position", position);
                            startActivity(intent5);
                            finish();
                            break;
                        case "danger":
                            intent = new Intent(BrlbActivity.this, DangerActivity.class);
                            intent.putExtra(Constant.TAG, "bingrenlist");

                            intent.putExtra(Constant.BRZYID, bingrenzyid);
                            intent.putExtra(Constant.RYSJ, ruyuanDate);
                            intent.putExtra(Constant.BRNAME, xingming);
                            intent.putExtra(Constant.BRID, bingrenid);
                            intent.putExtra(Constant.SEX, sex);
                            intent.putExtra(Constant.AGE, age);
                            intent.putExtra(Constant.BRCWH, chuangweihao);
                            startActivity(intent);
                            finish();
                            break;
                        case "XunHuiCX":
                            MyApplication.getInstance().setOther_brlb(null);
                            MyApplication.getInstance().setOther_brlb(listBRLB.get(position));
                            Intent intent7=new Intent(BrlbActivity.this,XhcxActivity.class);
                            intent7.putExtra("BINGRENZYID",listBRLB.get(position).getBINGRENZYID());
                            intent7.putExtra("CHUANGHAO",listBRLB.get(position).getCHUANGWEIHAO());
                            intent7.putExtra("XINGMING",listBRLB.get(position).getXINGMING());
                            intent7.putExtra("XINGBIE",listBRLB.get(position).getXINGBIE());
                            startActivity(intent7);
                            finish();
                            break;
                        case "HealthEdu":
                            Intent intent9=getIntent();
                            String eduStyleID =intent9.getStringExtra("eduStyleID");
                            MyApplication.getInstance().setOther_brlb(null);
                            MyApplication.getInstance().setOther_brlb(listBRLB.get(position));
                            Intent intent8=new Intent(BrlbActivity.this,RyjdActivity.class);
                            intent8.putExtra("BINGRENZYID",listBRLB.get(position).getBINGRENZYID());
                            intent8.putExtra("CHUANGHAO",listBRLB.get(position).getCHUANGWEIHAO());
                            intent8.putExtra("XINGMING",listBRLB.get(position).getXINGMING());
                            intent8.putExtra("XINGBIE",listBRLB.get(position).getXINGBIE());
                            intent8.putExtra("eduStyleID",eduStyleID);
                            startActivity(intent8);
                            finish();
                            break;

                        case "sscx":
                            Intent intentsx = new Intent(BrlbActivity.this, SscxActivity.class);
                            MyApplication.getInstance().setOther_brlb(listBRLB.get(position));
                            intentsx.putExtra("ccc", listBRLB.get(position).getXINGMING());
                            startActivity(intentsx);
                            break;
                        case "BloodSugar":

                            MyApplication.getInstance().setOther_brlb(null);
                            MyApplication.getInstance().setOther_brlb(listBRLB.get(position));
                            Intent intent10=new Intent(BrlbActivity.this,BloodSugarCheckActivity.class);
                            intent10.putExtra("BINGRENZYID",listBRLB.get(position).getBINGRENZYID());
                            intent10.putExtra("CHUANGHAO",listBRLB.get(position).getCHUANGWEIHAO());
                            intent10.putExtra("XINGMING",listBRLB.get(position).getXINGMING());
                            intent10.putExtra("XINGBIE",listBRLB.get(position).getXINGBIE());
                            startActivity(intent10);
                            finish();
                            break;
                        default:
                            break;
                    }
                }

            }
        });

    }

    @OnClick(R.id.back)
    public void back() {
        finish();
        startActivity(new Intent(BrlbActivity.this, HomePageActivity.class));
    }

    /**
     * 设置状态栏
     */
    private void setStatusBar() {
        int mColor = getResources().getColor(R.color.my_bule);
        StatusBarUtil.setColor(BrlbActivity.this, mColor, 0);
    }
    /*private void setToolBar() {
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(BrlbActivity.this, "xx", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.action_notifications:
                        Toast.makeText(BrlbActivity.this, "xx", Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(BrlbActivity.this, HomePageActivity.class));
            }
        });
    }*/

    private void setListView() {
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xDB, 0xDB,
                        0xDB)));
                openItem.setWidth(dp2px(BrlbActivity.this, 82));
                openItem.setTitle("评估单");
                openItem.setTitleSize(12);
                openItem.setTitleColor(Color.rgb(0x88, 0x88,
                        0x88));
                Drawable drawable1 = getResources().getDrawable(R.drawable.icon_pgfw66_01);
                drawable1 = zoomDrawable(drawable1, 100, 100);
                openItem.setIcon(drawable1);
                menu.addMenuItem(openItem);

                SwipeMenuItem openItem2 = new SwipeMenuItem(
                        getApplicationContext());
                openItem2.setBackground(new ColorDrawable(Color.rgb(0xDB, 0xDB,
                        0xDB)));
                openItem2.setWidth(dp2px(BrlbActivity.this, 82));
                openItem2.setTitle("生命体征");
                openItem2.setTitleSize(12);
                openItem2.setTitleColor(Color.rgb(0x88, 0x88,
                        0x88));
                Drawable drawable2 = getResources().getDrawable(R.drawable.icon_smtz66_01);
                drawable2 = zoomDrawable(drawable2, 100, 100);
                openItem2.setIcon(drawable2);
                menu.addMenuItem(openItem2);

                SwipeMenuItem openItem3 = new SwipeMenuItem(
                        getApplicationContext());
                openItem3.setBackground(new ColorDrawable(Color.rgb(0xDB, 0xDB,
                        0xDB)));
                openItem3.setWidth(dp2px(BrlbActivity.this, 82));
                openItem3.setTitle("医嘱");
                openItem3.setTitleSize(12);
                openItem3.setTitleColor(Color.rgb(0x88, 0x88,
                        0x88));
                Drawable drawable3 = getResources().getDrawable(R.drawable.icon_bryz66_01);
                drawable3 = zoomDrawable(drawable3, 100, 100);
                openItem3.setIcon(drawable3);
                menu.addMenuItem(openItem3);

                SwipeMenuItem openItem4 = new SwipeMenuItem(
                        getApplicationContext());
                openItem4.setBackground(new ColorDrawable(Color.rgb(0xDB, 0xDB,
                        0xDB)));
                openItem4.setWidth(dp2px(BrlbActivity.this, 82));
                openItem4.setTitle("病人");
                openItem4.setTitleSize(12);
                openItem4.setTitleColor(Color.rgb(0x88, 0x88,
                        0x88));
                Drawable drawable4 = getResources().getDrawable(R.drawable.icon_brlb66_01);
                drawable4 = zoomDrawable(drawable4, 100, 100);
                openItem4.setIcon(drawable4);
                menu.addMenuItem(openItem4);
            }
        };
        listView.setMenuCreator(creator);


    }

    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(null, newbmp);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(BrlbActivity.this, "x", Toast.LENGTH_SHORT).show();
            listView.setMenuCreator(creator);
            listView.setAdapter(new CommonAdapter<String>(BrlbActivity.this, R.layout.brlb_listview_item, list) {
                @Override
                protected void convert(ViewHolder holder, String s, int position) {
                    if (position == 3 || position == 6 || position == 2) {
                        holder.setImageResource(R.id.picture, R.drawable.icon_women);
                    }
                }
            });
        }
    };

    MyXmlHandler xmlHandler = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:
                    MyApplication.getInstance().setListBRLB(listBRLB);
                    listView.setAdapter(new CommonAdapter<BRLB>(BrlbActivity.this, R.layout.brlb_listview_item, listBRLB) {
                        @Override
                        protected void convert(ViewHolder viewHolder, BRLB item, int position) {
                            if (item.getXINGBIE().equals("女")) {
                                viewHolder.setImageResource(R.id.picture, R.drawable.icon_women);
                            } else {
                                viewHolder.setImageResource(R.id.picture, R.drawable.icon_men);
                            }
                            viewHolder.setText(R.id.name, item.getXINGMING());
                            viewHolder.setText(R.id.number, item.getCHUANGWEIHAO() + "床");
                            viewHolder.setText(R.id.content, item.getZHENDUAN());
                            viewHolder.setText(R.id.nianlin, item.getNIANLING());
                            //viewHolder.setText(R.id.huli,item.getHULIDJ());
                        }
                    });

                    //第二次网络请求
                    SharedPreferences preferences2 = getSharedPreferences("init", Context.MODE_PRIVATE);
                    String name = preferences2.getString("id", "");
                    String canshu = preferences2.getString("bqdm", "");
                    zhierCall = null;
                    zhierCall = (new ZhierCall())
                            .setId(name)
                            .setNumber("0500111")
                            .setMessage(NetWork.BINGREN_XX)
                            .setCanshu(canshu)
                            .setContext(BrlbActivity.this)
                            .setPort(5000)
                            .build();

                    zhierCall.start(new NetWork.SocketResult() {
                        @Override
                        public void success(String data) {
                            System.out.print("第二次请求结构" + data);
                            StringXmlParser parser = new StringXmlParser(xmlHandler2,
                                    new Class[]{BRLBBC.class});
                            parser.parse(data);
                        }

                        @Override
                        public void fail(String info) {
                            // Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG).show();
                        }
                    });

                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listBRLB.add((BRLB) msg.obj);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    MyXmlHandler xmlHandler2 = new MyXmlHandler(this, this) {
        @Override
        public void handlerMessage(Message msg) {
            switch (msg.what) {
                case END:

                    listView.setAdapter(new CommonAdapter<BRLBBC>(BrlbActivity.this, R.layout.brlb_listview_item, listBRLBBC) {
                        @Override
                        protected void convert(ViewHolder viewHolder, BRLBBC item, int position) {
                            if (!(item.getAmount().equals("0"))) {
                                //viewHolder.setImageResource(R.id.sffs,R.drawable.icon_bryz);
                            }
                            if (item.getIsFever().equals("True")) {
                                viewHolder.setText(R.id.sffs2, "发烧");
                            } else {
                                viewHolder.setVisible(R.id.fashaole, false);
                            }


                            if (listBRLB.get(position).getXINGBIE().equals("女")) {
                                viewHolder.setImageResource(R.id.picture, R.drawable.icon_women);
                            } else {
                                viewHolder.setImageResource(R.id.picture, R.drawable.icon_men);
                            }
                            viewHolder.setText(R.id.name, listBRLB.get(position).getXINGMING());
                            viewHolder.setText(R.id.number, listBRLB.get(position).getCHUANGWEIHAO() + "床");
                            viewHolder.setText(R.id.content, listBRLB.get(position).getZHENDUAN());
                            viewHolder.setText(R.id.nianlin, listBRLB.get(position).getNIANLING());
                            viewHolder.setText(R.id.sffs, "医嘱" + item.getAmount() + "个");
                            switch (listBRLB.get(position).getHULIDJ()) {
                                case "特级护理":
                                    viewHolder.setText(R.id.huli, "特");
                                    break;
                                case "一级护理":
                                    viewHolder.setText(R.id.huli, "一");
                                    break;
                                case "二级护理":
                                    viewHolder.setText(R.id.huli, "二");
                                    break;
                                case "三级护理":
                                    viewHolder.setText(R.id.huli, "三");
                                    break;
                            }

                            viewHolder.setText(R.id.huli, listBRLB.get(position).getHULIDJ());

                        }
                    });

                    break;
                case NODE:
                    switch (msg.arg1) {
                        case 0:
                            listBRLBBC.add((BRLBBC) msg.obj);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void createXml() {
        //1.头部
        StringBuilder s = new StringBuilder();
        s.append("<DS Name=\"59408853\" Num=\"1\">" + "\r\n");
        s.append("<DT Name=\"my_YiZhu\" Num=\"168\">");

        System.out.print(s.toString() + "xxxxx");
        //2.中部
        //3.尾部
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            /*if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }*/
            startActivity(new Intent(BrlbActivity.this, HomePageActivity.class));
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 回传已选择的病人的信息的接口
     */
    public interface OnGetSelectedPatientInfoListener {
        void getSelectPosition(int pos);
    }

    public void setOnGetSelectedPatientInfoListener(OnGetSelectedPatientInfoListener getSelectedPatientInfo) {
        this.getSelectedPatientInfo = getSelectedPatientInfo;
    }
}
