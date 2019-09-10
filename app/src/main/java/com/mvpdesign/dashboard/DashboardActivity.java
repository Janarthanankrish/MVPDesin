package com.mvpdesign.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvpdesign.R;
import com.mvpdesign.addnews.AddNewsActivity;
import com.mvpdesign.login.LoginActivity;
import com.mvpdesign.splash.SplashActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity implements DashboardView, SwipeRefreshLayout.OnRefreshListener {


	@BindView(R.id.img_back)
	ImageView back;
	@BindView(R.id.lbl_title)
	TextView title;
	@BindView(R.id.tv_error)
	TextView error;
	@BindView(R.id.img_logout)
	ImageView logout;
	@BindView(R.id.rv_list)
	RecyclerView recyclerView;
	@BindView(R.id.spl_refresh)
	SwipeRefreshLayout refresh;
	DashboardAdapter dashboardAdapter;
	ArrayList<DashboardPojo> dashBoardList = new ArrayList<>();

	DashboardPresenter dashboardPresenter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		ButterKnife.bind(this);
		LinearLayoutManager layoutManager = new LinearLayoutManager(DashboardActivity.this);
		dashboardAdapter = new DashboardAdapter(DashboardActivity.this, dashBoardList);
		recyclerView.setAdapter(dashboardAdapter);
		recyclerView.setLayoutManager(layoutManager);
		dashboardPresenter = new DashboardPresenter(DashboardActivity.this, DashboardActivity.this);
		dashboardPresenter.getNewsList(true);
		title.setText(getResources().getString(R.string.news));
		logout.setVisibility(View.VISIBLE);
		refresh.setOnRefreshListener(this);
		back.setImageResource(R.drawable.ic_add);
		back.setOnClickListener(new Onclick());
		logout.setOnClickListener(new Onclick());
	}

	@Override
	public void logoutAlert() {
		startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
		finish();
	}

	@Override
	public void loadError(String error_msg) {
		refresh.setRefreshing(false);
		error.setVisibility(View.VISIBLE);
		recyclerView.setVisibility(View.GONE);
		error.setText(error_msg);
	}

	@Override
	public void loadSuccess(ArrayList<DashboardPojo> list) {
		refresh.setRefreshing(false);
		if (list.size() > 0) {
			error.setVisibility(View.GONE);
			recyclerView.setVisibility(View.VISIBLE);
			dashBoardList.clear();
			dashBoardList.addAll(list);
			dashboardAdapter.notifyDataSetChanged();
		} else {
			error.setVisibility(View.VISIBLE);
			recyclerView.setVisibility(View.GONE);
			error.setText(getResources().getString(R.string.news_error));
		}
	}

	@Override
	public void movetoAddNews() {
		startActivityForResult(new Intent(DashboardActivity.this, AddNewsActivity.class), 7);
	}

	@Override
	public void onRefresh() {
		dashboardPresenter.getNewsList(false);
	}

	private class Onclick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (v == logout)
				dashboardPresenter.showLogoutAlert();
			else if (v == back) dashboardPresenter.movetoNextActivity();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 7 && resultCode == Activity.RESULT_OK) {
			boolean status = data.getBooleanExtra("status", false);
			String message = data.getStringExtra("message");
			if (status) dashboardPresenter.getNewsList(true);
			if (message != null && !message.equals(""))
				Snackbar.make(recyclerView, message, 1000).show();

		}
	}
}
