package a.f.utils;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.TypedValue;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.regex.Pattern;

import a.f.base.BaseApp;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 16:33
 * 描述：文本 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class TYTextUtils {

    /** 验证 正则表达式 【regex = 正则表达式】【value = 需要验证的字符串】 */
    public static boolean regEx(String regex, String value) {
        return Pattern.compile(regex).matcher(value).find();
    }

    /** 改变指定文本 颜色&大小 */
    public static SpannableStringBuilder changeSpecifiedString(String needChangeS, String startS, String endS, int colorR, int sizeSP) {
        needChangeS = TextUtils.isEmpty(needChangeS) ? "" : needChangeS;
        startS = TextUtils.isEmpty(startS) ? "" : startS;
        endS = TextUtils.isEmpty(endS) ? "" : endS;
        ColorStateList colorStateList = AppCompatResources.getColorStateList(BaseApp.getI(), colorR);
        sizeSP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sizeSP, Resources.getSystem().getDisplayMetrics());

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(startS + needChangeS + endS);
        spannableStringBuilder.setSpan(new TextAppearanceSpan(null, Typeface.NORMAL, sizeSP, colorStateList, colorStateList), startS.length(), startS.length() + needChangeS.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    /**
     * 改变文本颜色
     * <p>
     * 常用标签【<font>：设置颜色和字体】【<big>：设置字体大号】【<small>：设置字体小号】【<i><b>：斜体粗体】【<a>：连接网址】【<img>：图片】
     */
    public static String changeTextColor(String text, String color) {
        return "<font color=\"" + color + "\">" + text + "</font>";
    }

}
