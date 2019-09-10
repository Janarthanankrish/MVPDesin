package com.mvpdesign.addnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvpdesign.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewsActivity extends AppCompatActivity implements AddNewsView {

    @BindView(R.id.img_back)
    ImageView back;
    @BindView(R.id.lbl_title)
    TextView title;
    @BindView(R.id.et_name)
    EditText name;
    @BindView(R.id.et_eid)
    EditText eid;
    @BindView(R.id.et_idbar)
    EditText idbar;
    @BindView(R.id.et_unified_number)
    EditText unified_number;
    @BindView(R.id.et_number)
    EditText number;
    @BindView(R.id.btn_save)
    Button save;
    AddNewsPresenter addNewsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        ButterKnife.bind(this);
        addNewsPresenter = new AddNewsPresenter(AddNewsActivity.this, AddNewsActivity.this);
        save.setOnClickListener(new onClick());
        back.setOnClickListener(new onClick());
        title.setText(getResources().getString(R.string.add_news));
    }

    @Override
    public void showError(String type, String error) {
        if (type.equalsIgnoreCase("name")) {
            name.requestFocus();
            name.setError(error);
        } else if (type.equalsIgnoreCase("eid")) {
            eid.requestFocus();
            eid.setError(error);
        } else if (type.equalsIgnoreCase("idbar")) {
            idbar.requestFocus();
            idbar.setError(error);
        } else if (type.equalsIgnoreCase("unifiedNumber")) {
            unified_number.requestFocus();
            unified_number.setError(error);
        } else if (type.equalsIgnoreCase("mobileNo")) {
            number.requestFocus();
            number.setError(error);
        }
    }

    @Override
    public void successMessage(boolean status, String message) {
        Intent intent = getIntent();
        intent.putExtra("status", status);
        intent.putExtra("message", message);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


    private class onClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == save)
                addNewsPresenter.validateNews(name.getText().toString(), eid.getText().toString(), idbar.getText().toString(), unified_number.getText().toString(), number.getText().toString());
            else if (v == back)
                onBackPressed();
        }
    }
}
