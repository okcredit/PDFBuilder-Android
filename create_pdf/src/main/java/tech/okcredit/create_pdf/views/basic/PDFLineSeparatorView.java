package tech.okcredit.create_pdf.views.basic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

public class PDFLineSeparatorView extends PDFView {
    public PDFLineSeparatorView(Context context) {
        super(context);

        View separatorLine = new View(context);
        LinearLayout.LayoutParams separatorLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        separatorLine.setPadding(0, 5, 0, 5);
        separatorLine.setLayoutParams(separatorLayoutParam);

        super.setView(separatorLine);
    }

    @Override
    protected PDFLineSeparatorView addView(@NonNull PDFView viewToAdd) throws IllegalStateException {
        throw new IllegalStateException("Cannot add subview to Line Separator");
    }

    @Override
    public PDFLineSeparatorView setLayout(@NonNull ViewGroup.LayoutParams layoutParams) {
        super.setLayout(layoutParams);
        return this;
    }

    public PDFLineSeparatorView setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        return this;
    }
}
