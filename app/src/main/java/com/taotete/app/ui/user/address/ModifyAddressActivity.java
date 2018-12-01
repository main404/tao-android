package com.taotete.app.ui.user.address;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.maning.mndialoglibrary.MProgressDialog;
import com.taotete.app.R;
import com.taotete.app.api.ApiRetrofit;
import com.taotete.app.model.base.ResultBean;
import com.taotete.app.model.response.AddAddressResponse;
import com.taotete.app.model.response.AddressListResponse;
import com.taotete.app.ui.account.AccountHelper;
import com.taotete.app.ui.base.activities.BaseActivity;
import com.taotete.app.ui.user.address.bean.JsonBean;
import com.taotete.app.utils.GetJsonDataUtil;
import com.taotete.app.utils.LogUtils;
import com.taotete.app.utils.UIUtils;
import com.taotete.app.widget.TopBarView;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ModifyAddressActivity extends BaseActivity implements View.OnClickListener {

    static final int TYPE_ADD = 1;
    static final int TYPE_MODIFY = 2;
    private int mType;

    @Bind(R.id.topbar)
    TopBarView mTopBarView;

    @Bind(R.id.et_username)
    EditText mEtUsername;

    @Bind(R.id.iv_username_del)
    ImageView mIvUsernameDel;

    @Bind(R.id.et_phone)
    EditText mEtPhone;

    @Bind(R.id.iv_phone_del)
    ImageView mIvPhoneDel;

    @Bind(R.id.tv_area)
    TextView mTvArea;

    @Bind(R.id.iv_area_del)
    ImageView mIvAreaDel;

    @Bind(R.id.et_address)
    EditText mEtAddress;

    @Bind(R.id.iv_address_del)
    ImageView mIvAddressDel;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        Toast.makeText(ModifyAddressActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

    private AddressListResponse.ListBean mInfo;

    public static void show(Context context, AddressListResponse.ListBean info, int type) {
        Intent intent = new Intent(context, ModifyAddressActivity.class);
        intent.putExtra("address_info", info);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modify_address;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        StatusBarUtil.setLightMode(this);

        mInfo = (AddressListResponse.ListBean) getIntent().getSerializableExtra("address_info");
        mType = getIntent().getIntExtra("type", 0);
        if (mType == 0 || (mType == 2 && mInfo == null)) {
            finish();
            return;
        }
        if (mType == 2 && mInfo != null) {
            mEtUsername.setText(mInfo.getName());
            mEtPhone.setText(mInfo.getMobile());
            mTvArea.setText(mInfo.getArea());
            mEtAddress.setText(mInfo.getAddress());
        }

        mTopBarView.setTopBarToStatus(R.mipmap.btn_back_normal, -1, null,
                mType == 1 ? getString(R.string.commit) : getString(R.string.modify),
                mType == 1 ? getString(R.string.topbar_title_modify_address) : getString(R.string.topbar_title_modify_address), this);
        mEtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();

                if (length > 0) {
                    mIvUsernameDel.setVisibility(View.VISIBLE);
                } else {
                    mIvUsernameDel.setVisibility(View.INVISIBLE);
                }
            }
        });
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();

                if (length > 0) {
                    mIvPhoneDel.setVisibility(View.VISIBLE);
                } else {
                    mIvPhoneDel.setVisibility(View.INVISIBLE);
                }
            }
        });
        mTvArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();

                if (length > 0) {
                    mIvAreaDel.setVisibility(View.VISIBLE);
                } else {
                    mIvAreaDel.setVisibility(View.INVISIBLE);
                }
            }
        });
        mEtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();

                if (length > 0) {
                    mIvAddressDel.setVisibility(View.VISIBLE);
                } else {
                    mIvAddressDel.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

        initJsonData();
    }

    private void initJsonData() {

        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {
            ArrayList<String> CityList = new ArrayList<>();
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);
                ArrayList<String> City_AreaList = new ArrayList<>();

                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);
            }

            options2Items.add(CityList);

            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @OnClick({R.id.btn_left, R.id.text_right, R.id.ll_area, R.id.iv_username_del, R.id.iv_phone_del, R.id.iv_area_del, R.id.iv_address_del})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.text_right:
                requestAddAddress();
                break;
            case R.id.ll_area:
                if (isLoaded) {
                    UIUtils.hideSoftKeyboard(getCurrentFocus());
                    showPickerView();
                } else {
                    Toast.makeText(ModifyAddressActivity.this, "Please waiting until the data is parsed", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_username_del:
                mEtUsername.setText(null);
                break;
            case R.id.iv_phone_del:
                mEtPhone.setText(null);
                break;
            case R.id.iv_area_del:
                mTvArea.setText(null);
                break;
            case R.id.iv_address_del:
                mEtAddress.setText(null);
                break;
        }
    }

    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);

                mTvArea.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build()
                ;
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }

    @SuppressLint("CheckResult")
    private void requestAddAddress() {
        String username = mEtUsername.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        String area = mTvArea.getText().toString();
        String address = mEtAddress.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            UIUtils.showToast("请填写收件人姓名");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            UIUtils.showToast("请填写手机号码");
            return;
        }

        if (TextUtils.isEmpty(username)) {
            UIUtils.showToast("请选择所在地区");
            return;
        }

        if (TextUtils.isEmpty(username)) {
            UIUtils.showToast("请填写详细地址");
            return;
        }

        MProgressDialog.showProgress(this);
        if (mType == 1) {  // 新增
            ApiRetrofit.getInstance().addAddress(AccountHelper.getToken(), username, phone, area, address)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<AddAddressResponse>() {
                        @Override
                        public void accept(AddAddressResponse addAddressResponse) throws Exception {
                            MProgressDialog.dismissProgress();
                            if (addAddressResponse.getCode() == 0) {
                                UIUtils.showToast("添加成功");
                                finish();
                            } else {
                                UIUtils.showToast(addAddressResponse.getMsg());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            LogUtils.e(throwable.getLocalizedMessage());
                            UIUtils.showToast(throwable.getLocalizedMessage());
                            MProgressDialog.dismissProgress();
                        }
                    });
        } else {
            ApiRetrofit.getInstance().editAddress(AccountHelper.getToken(), mInfo.getId(), username, phone, area, address, 0)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResultBean>() {
                        @Override
                        public void accept(ResultBean resultBean) throws Exception {
                            MProgressDialog.dismissProgress();
                            if (resultBean.isOk()) {
                                UIUtils.showToast("修改成功");
                                finish();
                            } else {
                                UIUtils.showToast(resultBean.getMessage());
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            LogUtils.e(throwable.getLocalizedMessage());
                            UIUtils.showToast(throwable.getLocalizedMessage());
                            MProgressDialog.dismissProgress();
                        }
                    });
        }
    }

}
