package ir.alishi.vpn.presenter;

import android.app.Dialog;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.spongycastle.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.blinkt.openvpn.OpenVpnApi;
import ir.alishi.vpn.R;
import ir.alishi.vpn.Retrofit.RetrofitClient;
import ir.alishi.vpn.adapter.ServerListRecyclerAdapter;
import ir.alishi.vpn.interfaces.RandomServer;
import ir.alishi.vpn.interfaces.ServersValue;
import ir.alishi.vpn.model.ServerListModel;
import ir.alishi.vpn.view.IBaseView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BasePresenter {
    public static class MainP {
        IBaseView.MainV view;
        AppCompatActivity appCompatActivity;
        ServerListRecyclerAdapter adapter;
        RecyclerView recyclerView;
        LottieAnimationView animationView;
        LinearLayoutManager manager;
        Dialog dialog;
        Random random;
        int rsi;

        public MainP(IBaseView.MainV view, AppCompatActivity appCompatActivity) {
            this.view = view;
            this.appCompatActivity = appCompatActivity;

            view.init();
            view.setupListeners();


        }

        public void startVpn(int servId) {

            RetrofitClient.getRetrofitClient()
                    .getApi()
                    .getTakServer(servId)
                    .enqueue(new Callback<ServerListModel>() {
                        @Override
                        public void onResponse(Call<ServerListModel> call, Response<ServerListModel> response) {
                            try {
                                assert response.body() != null;
                                OpenVpnApi.startVpn(appCompatActivity, response.body().getConfig(), response.body().getUs(), response.body().getPas());
                            } catch (RemoteException e) {
                                Toast.makeText(appCompatActivity, "connection failed", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ServerListModel> call, Throwable t) {
                            Toast.makeText(appCompatActivity, t.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
        }

        public void getRandomServerId(final RandomServer randomServer) {
            RetrofitClient
                    .getRetrofitClient()
                    .getApi()
                    .getServers()
                    .enqueue(new Callback<List<ServerListModel>>() {
                        @Override
                        public void onResponse(Call<List<ServerListModel>> call, Response<List<ServerListModel>> response) {
                            ArrayList<ServerListModel> models = (ArrayList<ServerListModel>) response.body();
                            random = new Random();
                            int index = random.nextInt(models.indexOf(models.get(models.size() - 1)) - models.indexOf(models.get(0)) + models.indexOf(models.get(0)));
                            rsi = (int) response.body().get(index).getId();
                            randomServer.gerRandomServerId(rsi);
                        }

                        @Override
                        public void onFailure(Call<List<ServerListModel>> call, Throwable t) {

                        }
                    });
        }

        private void getServerList(final ServersValue value) {


            RetrofitClient.getRetrofitClient()
                    .getApi()
                    .getServers()
                    .enqueue(new Callback<List<ServerListModel>>() {
                        @Override
                        public void onResponse(Call<List<ServerListModel>> call, Response<List<ServerListModel>> response) {
                            value.setValue((ArrayList<ServerListModel>) response.body());
                        }

                        @Override
                        public void onFailure(Call<List<ServerListModel>> call, Throwable t) {
                            Log.e(BasePresenter.MainP.class.getSimpleName(), "onFailure:getServerList()==> " + t.getMessage());
                        }
                    });


        }


        public void showServerList() {

            dialog = new Dialog(appCompatActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_sl);
            recyclerView = dialog.findViewById(R.id.rv_sl);
            animationView = dialog.findViewById(R.id.lav_loading__);
            dialog.show();

            getServerList(new ServersValue() {
                @Override
                public void setValue(ArrayList<ServerListModel> models) {

                    recyclerView.setVisibility(View.VISIBLE);
                    manager = new LinearLayoutManager(appCompatActivity, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(manager);
                    adapter = new ServerListRecyclerAdapter(appCompatActivity, models);
                    adapter.setClickListener(new ServerListRecyclerAdapter.ItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Constants.ServerConnectionModel = adapter.getItem(position);
                            dialog.dismiss();
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    animationView.setVisibility(View.GONE);
                }
            });

        }
    }
}
