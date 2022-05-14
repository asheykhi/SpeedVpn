package ir.alishi.vpn.view;

public interface IBaseView {

    void alertUser(String Message);

    void init();

    void setupListeners();

    interface MainV extends IBaseView {

    }

}
