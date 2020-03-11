package com.lancoo.cpk12.cplibrary.utils;

import android.app.Activity;
import android.content.Context;

import com.lancoo.cpk12.cplibrary.R;

import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.SinglePicker;

/**
 * @Author 葛雪磊
 * @Email 1739037474@qq.com
 * @Date 2019/11/28
 * @Description
 */
public class PickerUtil {
    public static void showPicker(Activity context, final List<String> list, int selectIndex, final OnItemPickListener listener) {
        final SinglePicker<String> picker = new SinglePicker<String>(context, list);

        picker.setSelectedIndex(selectIndex);
        picker.setOnItemPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                listener.onOptionPicked(picker, index, item);
            }
        });
        picker.setTopLineColor(context.getResources().getColor(R.color.cpbase_top_line_color));
        picker.setCancelTextColor(context.getResources().getColor(R.color.cpbase_cancel_text_color));
        picker.setSubmitTextColor(context.getResources().getColor(R.color.cpbase_submit_text_color));
        picker.setLineSpaceMultiplier(2);
        picker.setDividerVisible(false);
        picker.setCancelText("取消");
        picker.setSubmitText("确定");
        picker.setBackgroundColor(context.getResources().getColor(R.color.cpbase_picker_bg_color));
        picker.setTopBackgroundColor(context.getResources().getColor(R.color.cpbase_picker_bg_color));
        picker.setTextColor(context.getResources().getColor(R.color.cpbase_text_color));
        picker.setTextSize(20);
        picker.setShadowColor(context.getResources().getColor(R.color.cpbase_picker_shade_color));
        picker.show();
    }

    public interface OnItemPickListener {
        void onOptionPicked(SinglePicker picker, int index, String item);
    }
}
