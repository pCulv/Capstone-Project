package com.annoyedandroid.ancora.ui;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annoyedandroid.ancora.R;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;


@Layout(R.layout.drawer_item)
public class DrawerMenuItem {

    //create static variables for menu items here
    public static final int DRAWER_MENU_ITEM_MY_TIMERS = 1;
    public static final int DRAWER_MENU_ITEM_UPGRADE = 2;
    public static final int DRAWER_MENU_ITEM_SETTINGS = 3;
    public static final int DRAWER_MENU_ITEM_LOGOUT = 4;


    private int mMenuPosition;
    private Context mContext;
    private DrawerCallBack mCallBack;

    @View(R.id.itemNameTxt)
    private TextView itemNameTxt;

    @View(R.id.itemIcon)
    private ImageView itemIcon;

    public DrawerMenuItem(Context context, int menuPosition) {
        mContext = context;
        mMenuPosition = menuPosition;
    }

    @Resolve
    private void onResolved() {
        switch (mMenuPosition) {
            case DRAWER_MENU_ITEM_MY_TIMERS:
                itemNameTxt.setText("My Timers");
                itemIcon.setImageDrawable(mContext.getDrawable(R.drawable.ic_alarm_black_24dp));
                itemIcon.setColorFilter(R.color.colorAccent);
                break;
            case DRAWER_MENU_ITEM_UPGRADE:
                itemNameTxt.setText("Upgrade To Pro");
                itemIcon.setImageDrawable(mContext.getDrawable(R.drawable.ic_shop_black_24dp));
                itemIcon.setColorFilter(R.color.colorAccent);
                break;
        }
    }

    @Click(R.id.mainView)
    private void onMenuItemClick() {
        switch (mMenuPosition) {
            case DRAWER_MENU_ITEM_MY_TIMERS:
                Toast.makeText(mContext, "My Timers", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onProfileMenuSelected();
                break;
            case DRAWER_MENU_ITEM_UPGRADE:
                Toast.makeText(mContext, "Upgrade", Toast.LENGTH_SHORT).show();
                if (mCallBack != null) mCallBack.onUpgradeMenuSelected();
                break;
        }
    }

    public void setDrawerCallBack(DrawerCallBack callBack) {
        mCallBack = callBack;
    }

    public interface DrawerCallBack {

        void onProfileMenuSelected();
        void onUpgradeMenuSelected();
    }
}
