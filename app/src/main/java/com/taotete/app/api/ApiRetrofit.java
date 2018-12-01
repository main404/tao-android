package com.taotete.app.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taotete.app.api.base.BaseApiRetrofit;
import com.taotete.app.model.base.ResultBean;
import com.taotete.app.model.request.AddAddressRequest;
import com.taotete.app.model.request.GetCategoryRequest;
import com.taotete.app.model.request.DeleteAddressRequest;
import com.taotete.app.model.request.EditAddressRequest;
import com.taotete.app.model.request.GetAddressListRequest;
import com.taotete.app.model.request.LoginRequest;
import com.taotete.app.model.request.RegisterRequest;
import com.taotete.app.model.request.ResetPwdRequest;
import com.taotete.app.model.request.SendCodeRequest;
import com.taotete.app.model.request.UserInfoRequest;
import com.taotete.app.model.request.VerifyCodeRequest;
import com.taotete.app.model.response.AddAddressResponse;
import com.taotete.app.model.response.AddressListResponse;
import com.taotete.app.model.response.CategoryResponse;
import com.taotete.app.model.response.LoginResponse;
import com.taotete.app.model.response.RegisterResponse;
import com.taotete.app.model.response.ResetPwdResponse;
import com.taotete.app.model.response.SendCodeResponse;
import com.taotete.app.model.response.UserInfo;
import com.taotete.app.model.response.VerifyCodeResponse;
import com.taotete.app.utils.LogUtils;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 使用Retrofit对网络请求进行配置
 */
public class ApiRetrofit extends BaseApiRetrofit {

    public MyApi mApi;
    public static ApiRetrofit mInstance;

    private ApiRetrofit() {
        super();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // 在构造方法中完成对Retrofit接口的初始化
        mApi = new Retrofit.Builder()
                .baseUrl(MyApi.BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MyApi.class);
    }

    public static ApiRetrofit getInstance() {
        if (mInstance == null) {
            synchronized (ApiRetrofit.class) {
                if (mInstance == null) {
                    mInstance = new ApiRetrofit();
                }
            }
        }
        return mInstance;
    }

    private RequestBody getRequestBody(Object obj) {
        String route = new Gson().toJson(obj);
        LogUtils.d("api", route);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), route);
        return body;
    }

    /**
     * 发送验证码
     * @param phone
     * @param intent 1: 注册， 2：找回密码
     * @return
     */
    public Observable<SendCodeResponse> sendCode(String phone, int intent) {
        return mApi.sendCode(getRequestBody(new SendCodeRequest(phone, intent)));
    }

    /**
     * register user info
     * @param phone
     * @param pwd
     * @param smsCode
     */
    public Observable<RegisterResponse> register(String phone, String pwd, String smsCode) {
        return mApi.register(getRequestBody(new RegisterRequest(phone, pwd, smsCode)));
    }

    /**
     * 验证验证码是否正确(必选先用手机号码调sendcode)
     * @param phoneNumber
     * @param smsCode
     */
    public Observable<VerifyCodeResponse> verifyCode(String phoneNumber, String smsCode, int intent) {
        return mApi.verifyCode(getRequestBody(new VerifyCodeRequest(phoneNumber, smsCode, intent)));
    }

    /**
     * 登录
     * @param username
     * @param pwd
     * @return
     */
    public Observable<LoginResponse> login(String username, String pwd) {
        return mApi.login(getRequestBody(new LoginRequest(username, pwd)));
    }

    /**
     * 重置密码
     * @param phoneNumber
     * @param pwd
     * @param smsCode
     */
    public Observable<ResetPwdResponse> resetPwd(String phoneNumber, String pwd, String smsCode) {
        return mApi.resetPwd(getRequestBody(new ResetPwdRequest(phoneNumber, pwd, smsCode)));
    }

    /**
     * 获取个人信息
     * @param token
     */
    public Observable<ResultBean<UserInfo>> getUserInfo(String token) {
        return mApi.getUserInfo(getRequestBody(new UserInfoRequest(token)));
    }

    /**
     * 增（收货地址）
     * @param area      地区
     * @param address   详细地址
     */
    public Observable<AddAddressResponse> addAddress(String token, String username, String mobile, String area, String address) {
        return mApi.addAddress(getRequestBody(new AddAddressRequest(token, username, mobile, area, address)));
    }

    /**
     * 删（收货地址）
     * @param id
     */
    public Observable<ResultBean> deleteAddress(String token, int id) {
        return mApi.deleteAddress(getRequestBody(new DeleteAddressRequest(token, id)));
    }

    /**
     * 改（收货地址）
     * @param id
     * @param userName
     * @param mobile
     * @param area
     * @param address
     * @param isDefault
     */
    public Observable<ResultBean> editAddress(String token, int id, String userName, String mobile, String area, String address, int isDefault) {
        return mApi.editAddress(getRequestBody(new EditAddressRequest(token, id, userName, mobile, area, address, isDefault)));
    }

    /**
     * 查（收货地址列表）
     */
    public Observable<ResultBean<AddressListResponse>> getAddressList(String token) {
        return mApi.getAddressList(getRequestBody(new GetAddressListRequest(token)));
    }

    /**
     * 分类列表
     * @return
     */
    public Observable<ResultBean<CategoryResponse>> getCategoryList() {
        return mApi.getCategoryList(getRequestBody(new GetCategoryRequest()));
    }

    // 综合 销量 价格 新品

    /**
     *
     * @param order   0  | 按默认排序方式相关度
     *                1  | 销量
     *                2  | 按热度排序（评论、销量数）
     *                3  | 按最新时间排序
     * @param keyword 关键字
     * @return
     */
}
