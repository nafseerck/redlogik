package com.redlogic.utils.api;


import com.redlogic.dashboard.customer.request.CustomerExecutionRequestModel;
import com.redlogic.dashboard.customer.request.WorkOrderApprovalRequestModel;
import com.redlogic.dashboard.customer.request.WorkOrderRequestModel;
import com.redlogic.dashboard.driver.request.AcceptOrRejectRequestModel;
import com.redlogic.dashboard.driver.request.ExecutionChecklistRequestModel;
import com.redlogic.dashboard.driver.request.ExecutionChecklistSubmitRequestModel;
import com.redlogic.dashboard.driver.request.GeneratePodRequestModel;
import com.redlogic.dashboard.driver.request.GenerateTimeSheetRequestModel;
import com.redlogic.dashboard.driver.request.InitialChecklistRequestModel;
import com.redlogic.dashboard.driver.request.JobsRequestModel;
import com.redlogic.dashboard.driver.request.ReportDamageRequestModel;
import com.redlogic.dashboard.driver.request.UpdateLocationeRequestModel;
import com.redlogic.language.request.SettingsRequestModel;
import com.redlogic.login.request.LoginRequestModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiServices {

    @GET
    Call<ResponseBody> getUrl(@Url String url);

    @POST("login")
    Call<ResponseBody> callLogin(@Body LoginRequestModel requestModel);

    @POST("settings")
    Call<ResponseBody> callSettings(@Body SettingsRequestModel requestModel);

    @POST("dashboard")
    Call<ResponseBody> callDashboard();

    @POST("customer-dashboard")
    Call<ResponseBody> callCustomerDashboard();

    @POST("jobs")
    Call<ResponseBody> callJobs(@Body JobsRequestModel requestModel);

    @POST("customer-execution-details")
    Call<ResponseBody> callCustomerExecution(@Body CustomerExecutionRequestModel requestModel);

    @POST("customer-execution-lists")
    Call<ResponseBody> callCustomerExecutionList(@Body WorkOrderRequestModel requestModel);

    @POST("workorders")
    Call<ResponseBody> callWorkOrders(@Body JobsRequestModel requestModel);

    @POST("workorder-details")
    Call<ResponseBody> callWorkOrderDetails(@Body WorkOrderRequestModel requestModel);

    @POST("workorder-approval")
    Call<ResponseBody> callWorkOrderapproval(@Body WorkOrderApprovalRequestModel requestModel);

    @POST("acceptorreject")
    Call<ResponseBody> callAcceptOrReject(@Body AcceptOrRejectRequestModel requestModel);

//    @POST("initialchecklist")
//    Call<ResponseBody> callInitialChecklist(@Body InitialChecklistRequestModel requestModel);

//    @POST("initialchecklistsubmit")
//    Call<ResponseBody> callInitialChecklistSubmit(@Body ExecutionChecklistSubmitRequestModel requestModel);

    @POST("executionchecklist")
    Call<ResponseBody> callExecutionChecklist(@Body InitialChecklistRequestModel requestModel);

    @POST("executionchecklistsubmit")
    Call<ResponseBody> callExecutionChecklistSubmit(@Body ExecutionChecklistRequestModel requestModel);

    @POST("generate_timesheet")
    Call<ResponseBody> callGenerateTimeSheet(@Body GenerateTimeSheetRequestModel requestModel);

    @POST("generate_pod")
    Call<ResponseBody> callGeneratePod(@Body GeneratePodRequestModel requestModel);

    @POST("close_job")
    Call<ResponseBody> callCloseJob(@Body InitialChecklistRequestModel requestModel);

    @POST("report_damage")
    Call<ResponseBody> callReportDamage(@Body ReportDamageRequestModel requestModel);

    @POST("update_location")
    Call<ResponseBody> callUpdateLocation(@Body UpdateLocationeRequestModel requestModel);

    @POST("notification")
    Call<ResponseBody> callUpdateLocation();

//    @POST("shedules")
    @POST("customer-shedules")
    Call<ResponseBody> callSchedules();
}
