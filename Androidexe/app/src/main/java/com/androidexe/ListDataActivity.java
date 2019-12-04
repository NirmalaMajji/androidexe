package com.androidexe;

import android.os.Bundle;

import com.androidexe.databinding.ActivityListDataBinding;
import com.androidexe.handlers.ListDataAdapter;
import com.androidexe.model.ListData;
import com.androidexe.rest.RestApis;
import com.androidexe.rest.RetroClient;
import com.androidexe.rest.RetroResponse;
import com.androidexe.utlis.AppMethods;
import com.androidexe.utlis.AppStrings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import retrofit2.Call;

public class ListDataActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener, RetroResponse.RetroResponseListener {

   private ActivityListDataBinding binding;
   private ArrayList<ListData> arrayList;
   private ListDataAdapter adapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      binding = DataBindingUtil.setContentView(this, R.layout.activity_list_data);
      intUI();
   }

   private void intUI() {
      arrayList = new ArrayList<>();
      adapter = new ListDataAdapter(arrayList, this);
      binding.listDataRv.setAdapter(adapter);
      binding.listDataRv.setLayoutManager(new LinearLayoutManager(this));

      binding.listDataSrl.setOnRefreshListener(this);

      binding.listDataSrl.post(() -> onRefresh());

      binding.listDataToolbar.setTitleTextColor(getResources().getColor(R.color.white_clr));
      binding.listDataToolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));

      binding.listDataToolbar.setNavigationOnClickListener(v -> finish());

   }

   private void setListDataApi() {
      if (AppMethods.isConnectingToInternet(this)) {

         RestApis restApi = RetroClient.getRetrofitInstance().create(RestApis.class);
         Call<Object> call = restApi.getListData();
         RetroResponse response = new RetroResponse(this, false);
         response.getResponse(AppStrings.baseURL, call);

      } else {
         AppMethods.alertForNoInternet(this);
      }
   }

   private void setListDataResponse(String response) {
      binding.listDataSrl.setRefreshing(false);
      try {
         JSONObject jo = new JSONObject(response);
         JSONArray jsonArray = new JSONArray(jo.optString(AppStrings.responseData.rows));
         binding.listDataToolbar.setTitle(jo.optString(AppStrings.responseData.title));

         for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject job = jsonArray.getJSONObject(i);
            ListData model = new ListData();
            String title = job.optString(AppStrings.responseData.title);
            String description = job.optString(AppStrings.responseData.description);
            String imageHref = job.optString(AppStrings.responseData.imageHref);

            if (!job.toString().equals("{}")) { // if data is not empty adding data in model
               model.setTitle(title);
               model.setDescription(description);
               model.setImageHref(imageHref);
               arrayList.add(model);
            }

         }
         adapter.notifyDataSetChanged();

      } catch (JSONException e) {
         e.printStackTrace();
      }
   }

   @Override
   public void onRefresh() {
      try {
         binding.listDataSrl.setRefreshing(true);
         if (!arrayList.isEmpty()) {
            arrayList.clear();
            setListDataApi();
            adapter.notifyDataSetChanged();
         } else {
            setListDataApi();
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @Override
   public void onRetroResponseGet(String response, String URL) throws JSONException {
      switch (URL) {
         case AppStrings.baseURL:
            setListDataResponse(response);
            break;
      }
   }
}
