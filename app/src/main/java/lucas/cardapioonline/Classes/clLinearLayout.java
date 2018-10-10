package lucas.cardapioonline.Classes;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import lucas.cardapioonline.R;

public class clLinearLayout {

    private static clUtil util;
    private static Activity poActivity;

    public clLinearLayout(Activity a) {
        poActivity = a;
        util = new clUtil(poActivity);
    }

    public static View createViewMenus(){
        View view = new View(poActivity);
        view.setBackground(poActivity.getBaseContext().getDrawable(R.color.bootstrap_gray));
        LinearLayout.LayoutParams paramsView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, R.dimen.EspessuraViewMenu);
        paramsView.bottomMargin = util.IntToDP(10);
        view.setLayoutParams(paramsView);
        return view;
    }

    public static LinearLayout createLinearLayout(clpropertyLinearLayout poProperty) {
        Integer margimTop = 0, margimBottom = 0, margimLeft = 0, margimRight = 0;
        LinearLayout layout = new LinearLayout(poActivity);
        layout.setOrientation(poProperty.getOrientation());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(poProperty.getLayout_width(), poProperty.getLayout_height());

        if (poProperty.getGravity() != 0){
            params.gravity = poProperty.getGravity();
        }

        margimTop = util.IntToDP(poProperty.getMargimTop());
        margimBottom = util.IntToDP(poProperty.getMargimBottom());
        margimLeft = util.IntToDP(poProperty.getMargimLeft());
        margimRight = util.IntToDP(poProperty.getMargimRight());

        if ((margimTop != 0) &&
                (margimBottom != 0) &&
                (margimLeft != 0) &&
                (margimRight != 0)) {
            params.setMargins(margimLeft, margimTop, margimRight, margimBottom);
        } else if (margimTop != 0) {
            params.topMargin = margimTop;
        } else if (margimBottom != 0) {
            params.bottomMargin = margimBottom;
        } else if (margimLeft != 0) {
            params.leftMargin = margimLeft;
        } else if (margimRight != 0) {
            params.rightMargin = margimRight;
        }

        layout.setLayoutParams(params);
        return layout;
    }
}
