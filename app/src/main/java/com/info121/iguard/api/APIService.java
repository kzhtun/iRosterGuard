package com.info121.iguard.api;



import com.info121.iguard.models.ObjectRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

//    @GET("checkundermaintenance")
//    Call<ObjectRes> checkUnderMaintenance();

    //validateuser/{guardcode},{password},{isNFC},{FCMToken},{secretkey},{mobilekey}
    @GET("validateuser/{guardcode},{password},{isNFC},{fcmToken},{secretkey},{mobilekey}")
    Call<ObjectRes> ValidateUser(@Path("guardcode") String username, @Path("password") String password, @Path("isNFC") String fcmToken, @Path("fcmToken") String isNFC, @Path("secretkey") String secretkey, @Path("mobilekey") String mobilekey);

    @GET("getUserProfile/{guardId},{secretkey},{mobileKey}")
    Call<ObjectRes> GetUserProfile(@Path("guardId") String guardId, @Path("secretkey") String secretkey, @Path("mobileKey") String mobileKey);

    // UpdatePassword/{guardcode},{password},{secretkey},{mobilekey}"
    @GET("UpdatePassword/{guardId},{password},{secretkey},{mobileKey}")
    Call<ObjectRes> UpdatePassword(@Path("guardId") String guardId, @Path("password") String password, @Path("secretkey") String secretkey, @Path("mobileKey") String mobileKey);

    //getGuardJobs/09-14-2020,09-20-2020,zTEST001,info12121092020,abajibjbjaa
    Call<ObjectRes> GetGuardJobs( @Path("sDate") String sDate, @Path("eDate") String eDate, @Path("guardId") String guardId, @Path("secretkey") String secretkey, @Path("mobileKey") String mobileKey);

}
