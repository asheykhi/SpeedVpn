package ir.alishi.vpn.Retrofit;

import java.util.List;

import ir.alishi.vpn.model.ServerListModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("Default.aspx?metod=server_list")
    Call<List<ServerListModel>> getServers();


    @GET("Default.aspx?metod=tak_server")
    Call<ServerListModel> getTakServer(@Query("token") int token);

}
