package com.supets.pet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.supets.pet.R;

public class MainActivity extends BaseActivity implements
        OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.auth).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.login:
                startActivity(new Intent(this, ThreeLoginActivity.class));
                break;
            case R.id.share:
                startActivity(new Intent(this, ThreeShareActivity.class));
                break;
            case R.id.auth:
                startActivity(new Intent(this, ThreeAuthActivity.class));
                break;
            default:
                break;
        }
    }

}
