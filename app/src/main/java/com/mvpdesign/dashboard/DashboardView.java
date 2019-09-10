package com.mvpdesign.dashboard;

import java.util.ArrayList;

public interface DashboardView {
    void logoutAlert();
    void loadError(String error);
    void loadSuccess(ArrayList<DashboardPojo> dashBoardList);
    void movetoAddNews();
}
