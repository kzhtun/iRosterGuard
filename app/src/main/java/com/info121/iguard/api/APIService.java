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
    Call<ObjectRes> ValidateUser(@Path("guardcode") String username, @Path("password") String password, @Path("isNFC") String isNFC, @Path("fcmToken") String fcmToken, @Path("secretkey") String secretkey, @Path("mobilekey") String mobilekey);

    @GET("getUserProfile/{guardId},{secretkey},{mobileKey}")
    Call<ObjectRes> GetUserProfile(@Path("guardId") String guardId, @Path("secretkey") String secretkey, @Path("mobileKey") String mobileKey);

    // UpdatePassword/{guardcode},{password},{secretkey},{mobilekey}"
    @GET("UpdatePassword/{guardId},{password},{secretkey},{mobileKey}")
    Call<ObjectRes> UpdatePassword(@Path("guardId") String guardId, @Path("password") String password, @Path("secretkey") String secretkey, @Path("mobileKey") String mobileKey);

    //getGuardJobs/09-14-2020,09-20-2020,zTEST001,info12121092020,abajibjbjaa
    @GET("getGuardJobs/{sDate},{eDate},{guardId},{type},{secretkey},{mobileKey}")
    Call<ObjectRes> GetGuardJobs(@Path("sDate") String sDate, @Path("eDate") String eDate, @Path("guardId") String guardId,  @Path("type") String type, @Path("secretkey") String secretkey, @Path("mobileKey") String mobileKey);


   // confirmJob/{guardcode},{jobno},{secretkey},{mobilekey}
    // acknowledgeJob/{guardcode},{jobno},{secretkey},{mobilekey}

    @GET("confirmJob/{guardId},{jobno},{secretkey},{mobileKey}")
    Call<ObjectRes> ConfirmJob(@Path("guardId") String guardId, @Path("jobno") String jobno, @Path("secretkey") String secretkey, @Path("mobileKey") String mobileKey);

    @GET("acknowledgeJob/{guardId},{jobno},{secretkey},{mobileKey}")
    Call<ObjectRes> AcknowledgeJob(@Path("guardId") String guardId, @Path("jobno") String jobno, @Path("secretkey") String secretkey, @Path("mobileKey") String mobileKey);


//    @GET("confirmJob/{guardcode},{jobno},{secretkey},{mobilekey}")
//    Call<ObjectRes> ConfirmJob(@Path("guardcode") String guardcode, @Path("jobno") String jobno, @Path("secretkey") String secretkey, @Path("mobileKey") String mobileKey);

//    @GET("acknowledgeJob/{guardcode},{jobno},{secretkey},{mobilekey}")
//    Call<ObjectRes> AcknowledgeJob(@Path("guardcode") String guardcode, @Path("jobno") String jobno, @Path("secretkey") String secretkey, @Path("mobileKey") String mobileKey);

}
