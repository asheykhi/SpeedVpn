package ir.alishi.vpn.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

import ir.alishi.vpn.R;
import ir.alishi.vpn.adapter.ServerListRecyclerAdapter;
import ir.alishi.vpn.model.ServerListModel;

public class ViewDialog {
    static RecyclerView recyclerView;
    static LottieAnimationView animationView;

    public static void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_sl);
        init(dialog);
        dialog.show();

    }

    private static void init(Dialog dialog) {
        recyclerView = dialog.findViewById(R.id.rv_sl);
        animationView = dialog.findViewById(R.id.lav_loading__);
    }

    public static void setupRecyclerServerList(Context context, ArrayList<ServerListModel> models) {
        recyclerView.setAdapter(new ServerListRecyclerAdapter(context, models));
    }

    public static void ternOffAnimation() {
        animationView.setVisibility(View.GONE);
    }


}
