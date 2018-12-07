package com.taotete.app.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taotete.app.api.base.BaseApiRetrofit;
import com.taotete.app.model.Order;
import com.taotete.app.model.base.ResultBean;
import com.taotete.app.model.request.AddAddressRequest;
import com.taotete.app.model.request.GetCategoryRequest;
import com.taotete.app.model.request.DeleteAddressRequest;
import com.taotete.app.model.request.EditAddressRequest;
import com.taotete.app.model.request.GetAddressListRequest;
import com.taotete.app.model.request.GetProductDetailRequest;
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
import com.taotete.app.model.response.ProductResponse;
import com.taotete.app.model.response.RegisterResponse;
import com.taotete.app.model.response.ResetPwdResponse;
import com.taotete.app.model.response.SendCodeResponse;
import com.taotete.app.model.response.UserInfo;
import com.taotete.app.model.response.VerifyCodeResponse;
import com.taotete.app.ui.product.bean.Product;
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

    //////////////////////////////////// 用户模块 业务接口 ////////////////////////////////////////////

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

    //////////////////////////////////// 收货地址 业务接口 ////////////////////////////////////////////

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
     */
    public Observable<ResultBean<CategoryResponse>> getCategoryList() {
        return mApi.getCategoryList(getRequestBody(new GetCategoryRequest()));
    }

    /**
     * 添加商品购物车
     */
    public void addCart() {

    }

    /**
     * 获取购物车列表
     */
    public void getCartList() {

    }

    /**
     * 删除购物车商品
     */
    public void deleteCartList() {

    }

    /**
     * 提交购物车商品
     */
    public void submitCart() {

    }

    //////////////////////////////////// 商品 业务层 接口 ////////////////////////////////////////////

    /**
     * 获取商品详情
     */
    public Observable<ResultBean<ProductResponse>> getProductDetail(int productId) {
        return mApi.getProductDetail(getRequestBody(new GetProductDetailRequest(productId)));
    }

    ////////////////////////////////////// 订单业务接口 //////////////////////////////////////////////

    /**
     * 根据ID查询订单
     */
    public void getOrderById(int orderId) {

    }

    /**
     * 提交订单
     */
    public void submitOrder(Order order) {

    }

    /**
     * 根据状态查询订单列表
     */
    public void getOrderList() {

    }

    /**
     * 取消订单
     */
    public void cancelOrder(int orderId) {

    }

    /**
     * 确认订单
     */
    public void confirmOrder(int orderId) {

    }

    ////////////////////////////////////// 支付业务接口 //////////////////////////////////////////////

    ////////////////////////////////////// 消息业务接口 //////////////////////////////////////////////

    /**
     * 获取消息列表
     */
    public void getMessageList() {

    }
}
