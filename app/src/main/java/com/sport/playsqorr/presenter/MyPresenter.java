package com.sport.playsqorr.presenter;

import android.view.View;

import com.sport.playsqorr.contracts.MyContracts;
import com.sport.playsqorr.model.MyModel;

public class MyPresenter implements MyContracts.Presenter {

    private MyContracts.View mView;
    private MyContracts.Model mModel;

    public MyPresenter(MyContracts.View v) {
        mView = v;

        initPresenter();
    }

    private void initPresenter() {
        mModel = new MyModel();
        mView.initView();
    }

    @Override
    public void onClick(View view) {
        String data = mModel.getData();
        mView.setViewData(data);
    }
}
