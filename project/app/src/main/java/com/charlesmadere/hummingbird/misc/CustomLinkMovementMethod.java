package com.charlesmadere.hummingbird.misc;

import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

public final class CustomLinkMovementMethod extends LinkMovementMethod {

    private static CustomLinkMovementMethod sInstance;


    public static synchronized CustomLinkMovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new CustomLinkMovementMethod();
        }

        return sInstance;
    }

    /*
     * Borrowed from Tenpenchii!! <3
     */
    @Override
    public boolean onTouchEvent(final TextView widget, final Spannable buffer,
            final MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_UP) {
            return Touch.onTouchEvent(widget, buffer, event);
        }

        final float x = event.getX() - widget.getTotalPaddingLeft() + widget.getScrollX();
        final float y = event.getY() - widget.getTotalPaddingTop() + widget.getScrollY();

        final Layout layout = widget.getLayout();
        final int line = layout.getLineForVertical((int) y);
        final int off = layout.getOffsetForHorizontal(line, x);

        final ClickableSpan[] clickables = buffer.getSpans(off, off, ClickableSpan.class);
        if (clickables == null || clickables.length == 0) {
            return performClick(widget.getParent()) || Touch.onTouchEvent(widget, buffer, event);
        }

        final ClickableSpan clickable = clickables[0];

        if (clickable instanceof URLSpan) {
            final URLSpan url = (URLSpan) clickable;
            MiscUtils.openUrl(widget.getContext(), url.getURL());
        } else {
            clickable.onClick(widget);
        }

        return true;
    }

    private boolean performClick(final ViewParent viewParent) {
        if (viewParent instanceof View) {
            final View view = (View) viewParent;
            return view.performClick() || performClick(view.getParent());
        } else {
            return false;
        }
    }

}
