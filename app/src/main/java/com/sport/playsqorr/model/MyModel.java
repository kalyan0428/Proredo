package com.sport.playsqorr.model;

import com.sport.playsqorr.contracts.MyContracts;

public class MyModel implements MyContracts.Model {

    @Override
    public String getData() {

        String msg = "Welcome to my World";


        return msg;
    }
}
