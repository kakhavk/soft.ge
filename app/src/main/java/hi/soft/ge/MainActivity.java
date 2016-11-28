package hi.soft.ge;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<SoftgePost> post;
    private PostAdapter adapter;
    private String url="http://api.soft.ge";
    ShareDialog shareDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        shareDialog = new ShareDialog(this);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        recyclerView=(RecyclerView) findViewById(R.id.softge_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    private void loadJSON(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request=retrofit.create(RequestInterface.class);
        Call<JSONResponse> call=request.getJSON();
        call.enqueue(new Callback<JSONResponse>(){
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response){
                JSONResponse jsonResponse=response.body();
                post=new ArrayList<>(Arrays.asList(jsonResponse.getPosts()));
                adapter=new PostAdapter(post);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t){
                Log.d("Error",t.getMessage());
            }
        });
    }

}
