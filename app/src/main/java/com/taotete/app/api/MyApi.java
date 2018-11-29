package com.taotete.app.api;

import com.taotete.app.model.base.ResultBean;
import com.taotete.app.model.request.GetAddressListRequest;
import com.taotete.app.model.response.AddAddressResponse;
import com.taotete.app.model.response.AddressListResponse;
import com.taotete.app.model.response.LoginResponse;
import com.taotete.app.model.response.RegisterResponse;
import com.taotete.app.model.response.ResetPwdResponse;
import com.taotete.app.model.response.SendCodeResponse;
import com.taotete.app.model.response.UserInfo;
import com.taotete.app.model.response.VerifyCodeResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * server端api
 */
public interface MyApi {

    public static final String BASE_URL = "http://118.24.222.80/";

    public static final int REGISTER_INTENT = 1;
    public static final int RESET_PWD_INTENT = 2;

    @POST("fivepeopledev/web/public/api_v1/Init/phone_send_code")
    Observable<SendCodeResponse> sendCode(@Body RequestBody body);

    @POST("fivepeopledev/web/public/api_v1/Init/account_register")
    Observable<RegisterResponse> register(@Body RequestBody requestBody);

    @POST("fivepeopledev/web/public/api_v1/Init/account_login")
    Observable<LoginResponse> login(@Body RequestBody body);

    @POST("fivepeopledev/web/public/api_v1/Init/phone_validate")
    Observable<VerifyCodeResponse> verifyCode(@Body RequestBody body);

    @POST("fivepeopledev/web/public/api_v1/Init/account_password_forgot")
    Observable<ResetPwdResponse> resetPwd(@Body RequestBody requestBody);

    @POST("fivepeopledev/web/public/api_v1/User/user_info")
    Observable<ResultBean<UserInfo>> getUserInfo(@Body RequestBody requestBody);

    @POST("fivepeopledev/web/public/api_v1/User/add_address")
    Observable<AddAddressResponse> addAddress(@Body RequestBody requestBody);

    @POST("fivepeopledev/web/public/api_v1/User/get_address_list")
    Observable<ResultBean<AddressListResponse>> getAddressList(GetAddressListRequest getAddressListRequest);

}
