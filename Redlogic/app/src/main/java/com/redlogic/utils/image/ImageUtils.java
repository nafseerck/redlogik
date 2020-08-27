package com.redlogic.utils.image;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;
import android.view.View;

import com.redlogic.utils.core.CoreUtils;


public class ImageUtils {

    public static void setRoundedBackground(Context context, int[] colors, float radius, View view) {
        setRoundedBackground(context, colors, radius, view, GradientDrawable.Orientation.LEFT_RIGHT);
    }

    public static void setRoundedBackground(Context context, int[] colors, float radius, View view, GradientDrawable.Orientation orientation) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(CoreUtils.convertPixelsToDp(radius, context));
        shape.setOrientation(orientation);
        shape.setColors(colors);
        view.setBackground(shape);
    }

    public static void setRoundedBackground(Context context, int[] colors, int[] selectedColor, float radius, View view) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(CoreUtils.convertPixelsToDp(radius, context));
        shape.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        shape.setColors(colors);

        GradientDrawable shape1 = new GradientDrawable();
        shape1.setCornerRadius(CoreUtils.convertPixelsToDp(radius, context));
        shape1.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        shape1.setColors(selectedColor);

        StateListDrawable out = new StateListDrawable();
        out.addState(new int[]{android.R.attr.state_pressed}, shape1);
        out.addState(StateSet.NOTHING, shape);
        view.setBackground(out);
    }

    public static void setRoundedBackgroundWithBorder(Context context, int[] colors, int[] selectedColor, float radius,
                                                      int strokeColor, int strokeRadius, View view) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(CoreUtils.convertPixelsToDp(radius, context));
        shape.setStroke(strokeRadius, strokeColor);
        shape.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        shape.setColors(colors);

        GradientDrawable shape1 = new GradientDrawable();
        shape1.setCornerRadius(CoreUtils.convertPixelsToDp(radius, context));
        shape1.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        shape1.setStroke(strokeRadius, strokeColor);
        shape1.setColors(selectedColor);

        StateListDrawable out = new StateListDrawable();
        out.addState(new int[]{android.R.attr.state_pressed}, shape1);
        out.addState(StateSet.NOTHING, shape);
        view.setBackground(out);
    }

    public static int[] convertToIntArray(int... items) {
        return items;
    }

    public static void setRoundedBackground(Context context, int color, float radius, View view) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(CoreUtils.convertPixelsToDp(radius, context));
        shape.setColor(color);
        view.setBackground(shape);
    }

    public static void setRoundedBorder(Context context, int strokeColor, int strokeRadius, float radius, View view) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(CoreUtils.convertPixelsToDp(radius, context));
        shape.setStroke(strokeRadius, strokeColor);
        view.setBackground(shape);
    }

    public static void setRoundedBackgroundWithBorder(Context context, int background,
                                                      int strokeColor, int strokeRadius, float radius, View view) {
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(CoreUtils.convertPixelsToDp(radius, context));
        shape.setColor(background);
        shape.setStroke(strokeRadius, strokeColor);
        view.setBackground(shape);
    }

    public static void setRoundedBackground2Side(Context context, int color, float radius, View view, Types type) {
        GradientDrawable shape = new GradientDrawable();
        float value = CoreUtils.convertPixelsToDp(radius, context);
        float[] rad = new float[]{0, 0, 0, 0, 0, 0, 0, 0};
        if (type == Types.LEFT_TOP_RIGHT_TOP) {
            rad = new float[]{value, value, value, value, 0, 0, 0, 0};
        } else if (type == Types.LEFT_BOTTOM_RIGHT_BOTTOM) {
            rad = new float[]{0, 0, 0, 0, value, value, value, value};
        } else if (type == Types.LEFT_TOP_LEFT_BOTTOM) {
            rad = new float[]{value, value, 0, 0, 0, 0, value, value};
        } else if (type == Types.RIGHT_TOP_RIGHT_BOTTOM) {
            rad = new float[]{0, 0, value, value, value, value, 0, 0};
        } else if (type == Types.LEFT_TOP) {
            rad = new float[]{value, value, 0, 0, 0, 0, 0, 0};
        } else if (type == Types.RIGHT_TOP) {
            rad = new float[]{0, 0, value, value, 0, 0, 0, 0};
        } else if (type == Types.RIGHT_BOTTOM) {
            rad = new float[]{0, 0, 0, 0, value, value, 0, 0};
        } else if (type == Types.LEFT_BOTTOM) {
            rad = new float[]{0, 0, 0, 0, 0, 0, value, value};
        }
        shape.setCornerRadii(rad);
        shape.setColor(color);
        view.setBackground(shape);
    }

    public enum Types {
        LEFT_TOP,
        RIGHT_TOP,
        LEFT_BOTTOM,
        RIGHT_BOTTOM,
        LEFT_TOP_RIGHT_TOP,
        LEFT_BOTTOM_RIGHT_BOTTOM,
        LEFT_TOP_LEFT_BOTTOM,
        RIGHT_TOP_RIGHT_BOTTOM
    }
}
