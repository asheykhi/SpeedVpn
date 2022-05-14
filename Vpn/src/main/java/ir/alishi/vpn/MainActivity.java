package ir.alishi.vpn;

import static de.blinkt.openvpn.core.VpnStatus.mLastLevel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.spongycastle.util.Constants;
import org.spongycastle.util.Utils;

import de.blinkt.openvpn.core.VpnStatus;
import ir.alishi.vpn.interfaces.RandomServer;
import ir.alishi.vpn.presenter.BasePresenter;
import ir.alishi.vpn.view.App;
import ir.alishi.vpn.view.IBaseView;


public class MainActivity extends AppCompatActivity implements IBaseView.MainV, View.OnClickListener {
    public static BasePresenter.MainP presenter;
    public static Button connect;
    public static TextView Locked;
    public static LottieAnimationView connectedAnim, failedAnim, load, earth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.requestFullScreenActivity(this);
        setContentView(R.layout.content_main);
        presenter = new BasePresenter.MainP(this, this);
        App.appCompatActivity = this;
        VpnStatus.initLogCache(getApplicationContext().getCacheDir());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_connect:
                connect.setText(R.string.connecting);
                failedAnim.setVisibility(View.GONE);
                if (Constants.ServerConnectionModel == null) {

                    presenter.getRandomServerId(new RandomServer() {
                        @Override
                        public void gerRandomServerId(int id) {
                            presenter.startVpn(id);
                        }
                    });
                } else {
                    MainActivity.load.setVisibility(View.VISIBLE);
                    MainActivity.load.playAnimation();
                    connect.setBackgroundColor(getColor(R.color.color_connecting));
                    presenter.startVpn((int) Constants.ServerConnectionModel.getId());
                }
                break;

            case R.id.item_rate_app:
               /* try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException ignore) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }*/
                break;
            case R.id.item_more_apps:
               /* try {
                    Intent intent1 = new Intent(Intent.ACTION_VIEW);
                    intent1.setData(Uri.parse("market://search?q=pub:wangxingyu"));
                    startActivity(intent1);
                } catch (ActivityNotFoundException ignore) {
                    Toast.makeText(this, "Not found Google Play!", Toast.LENGTH_SHORT).show();
                }*/
                break;
            case R.id.item_contact:

                break;
            case R.id.item_private_policy:
                /*try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://rawgit.com/yuger/app_policy/master/VPN_2017_Policy.html")));
                } catch (android.content.ActivityNotFoundException ignore) {
                    Toast.makeText(this,*//*R.string.no_browser*//*"No browser found", Toast.LENGTH_SHORT).show();
                }*/
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.regin:
                if (mLastLevel.equals(VpnStatus.ConnectionStatus.LEVEL_CONNECTED)) {
                    alertUser("Disconnect Vpn First !");
                } else {
                    presenter.showServerList();
                    connect.setBackgroundColor(getColor(android.R.color.holo_red_dark));
                    connect.setText(getString(R.string.tapTpConnect));
                    connect.setClickable(true);
                    connectedAnim.setVisibility(View.GONE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void alertUser(String Message) {
        // Toasty.info(this , Message).show();
        Toast.makeText(this, Message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void init() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        connect = findViewById(R.id.btn_connect);
        Locked = findViewById(R.id.locked);

        connectedAnim = findViewById(R.id.connected);
        failedAnim = findViewById(R.id.failed);
        load = findViewById(R.id.loadd);
        earth = findViewById(R.id.earth);

    }

    @Override
    public void setupListeners() {
        connect.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLastLevel.equals(VpnStatus.ConnectionStatus.LEVEL_CONNECTED)) {
            MainActivity.earth.playAnimation();
            MainActivity.Locked.setVisibility(View.GONE);
            MainActivity.connect.setText(R.string.state_connected);
            MainActivity.load.setVisibility(View.GONE);
            MainActivity.load.pauseAnimation();
            MainActivity.connectedAnim.setVisibility(View.GONE);
            MainActivity.connect.setBackgroundColor(App.appCompatActivity.getResources().getColor(android.R.color.holo_green_light));
            MainActivity.connect.setClickable(false);

        } else if (mLastLevel.equals(VpnStatus.ConnectionStatus.LEVEL_VPNPAUSED)) {
            MainActivity.earth.pauseAnimation();
            MainActivity.Locked.setVisibility(View.VISIBLE);
            MainActivity.connect.setBackgroundColor(App.appCompatActivity.getResources().getColor(R.color.color_F57C00));
            MainActivity.connect.setText(R.string.pausedByUser);
            MainActivity.connectedAnim.setVisibility(View.GONE);
            MainActivity.load.setVisibility(View.GONE);
            MainActivity.load.pauseAnimation();
            MainActivity.connect.setClickable(false);

        }
    }
}
