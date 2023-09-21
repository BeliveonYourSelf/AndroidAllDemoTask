package topphotoeditor.bokehphotoeditor.stickersView2;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

import static topphotoeditor.bokehphotoeditor.editor.util.Constant.isTextEdit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import topphotoeditor.bokehphotoeditor.R;
import topphotoeditor.bokehphotoeditor.editor.util.SystemUtil;

public class TextEditDialogFragment extends DialogFragment {
    public static final String TAG = TextEditDialogFragment.class.getSimpleName();
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String EXTRA_COLOR_CODE = "extra_color_code";
    private InputMethodManager mInputMethodManager;
    private AppCompatTextView tvDialogCancel, tvDialogDone;
    private CustomEditText edText;
    private TextEditor textEditor;
    AddTextProperties addTextProperties;
    private boolean newSticker = false;
    private static TextView tvOperation;
    private String previousString = "";

    private void initView(View view) {
        tvDialogCancel = (AppCompatTextView) view.findViewById(R.id.tvDialogCancel);
        tvDialogDone = (AppCompatTextView) view.findViewById(R.id.tvDialogDone);
        edText = (CustomEditText) view.findViewById(R.id.edText);

    }

    public interface TextEditor {
        void onDone(AddTextProperties addTextProperties);

        void onBackButton();
    }

    //Show dialog with provide text and text color
    public static TextEditDialogFragment show(@NonNull AppCompatActivity appCompatActivity,
                                              @NonNull String inputText,
                                              @ColorInt int colorCode) {
        Bundle args = new Bundle();
        args.putString(EXTRA_INPUT_TEXT, inputText);
        args.putInt(EXTRA_COLOR_CODE, colorCode);
        TextEditDialogFragment fragment = new TextEditDialogFragment();
        fragment.setArguments(args);
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return fragment;
    }

    public static TextEditDialogFragment show(@NonNull AppCompatActivity appCompatActivity, AddTextProperties addTextProperties,
                                              TextView tvCurrent) {
        TextEditDialogFragment fragment = new TextEditDialogFragment();
        fragment.setAddTextProperties(addTextProperties);
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        tvOperation = tvCurrent;
        return fragment;
    }

    //Show dialog with default text input as empty and text color white
    public static TextEditDialogFragment show(@NonNull AppCompatActivity appCompatActivity, TextView tvCurrent) {
        tvOperation = tvCurrent;
        return show(appCompatActivity,
                "Test", ContextCompat.getColor(appCompatActivity, R.color.white));
    }

    public void setOnTextEditorListener(TextEditor textEditor) {
        this.textEditor = textEditor;
    }

    public void setAddTextProperties(AddTextProperties addTextProperties) {
        this.addTextProperties = addTextProperties;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //Make dialog full screen with transparent background
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            dialog.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
//            dialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.text_edit_dialog, container, false);
        return inflate;
    }

    public void dismissKeyBoard() {
        textEditor.onBackButton();
        dismiss();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        if (addTextProperties == null) {
            addTextProperties = AddTextProperties.getDefaultProperties();
            newSticker = true;
        } else {
            newSticker = false;
        }

        if (addTextProperties.getText() != null) {
            previousString = addTextProperties.getText();
        }
        edText.setOnEditText(this);

        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        setDefaultStyleForEdittext();
        mInputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);


        tvDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTextEdit = false;
                mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                if (tvOperation != null) {
                    tvOperation.setText(previousString);
                }
                addTextProperties.setText(previousString);
                textEditor.onBackButton();
                dismiss();
            }
        });
        tvDialogDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addTextProperties.getText() == null || addTextProperties.getText().length() == 0) {
                    mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    textEditor.onBackButton();
                    dismiss();
                    return;
                }
                addTextProperties.setTextWidth(tvOperation.getMeasuredWidth());
                addTextProperties.setTextHeight(tvOperation.getMeasuredHeight());
                mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                textEditor.onDone(addTextProperties);
                dismiss();
//                isTextEdit = false;
            }
        });

        edText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tvOperation != null) {
                    tvOperation.setText(s.toString());
                }
                addTextProperties.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (tvOperation != null) {
            initTextView();
        }
    }

    private void setDefaultStyleForEdittext() {
        edText.requestFocus();
        edText.setTextSize(20);
        edText.setTextAlignment(TEXT_ALIGNMENT_CENTER);
    }

    @SuppressLint("WrongConstant")
    public void initTextView() {


        if (addTextProperties.isFullScreen()) {
            tvOperation.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            tvOperation.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        if (addTextProperties.isShowBackground()) {
            if (addTextProperties.getBackgroundColor() != 0)
                tvOperation.setBackgroundColor(addTextProperties.getBackgroundColor());
            if (addTextProperties.getBackgroundAlpha() < 255) {
                int red = Color.red(addTextProperties.getBackgroundColor());
                int green = Color.green(addTextProperties.getBackgroundColor());
                int blue = Color.blue(addTextProperties.getBackgroundColor());

                tvOperation.setBackgroundColor(Color.argb(addTextProperties.getBackgroundAlpha(), red, green, blue));
            }

            if (addTextProperties.getBackgroundBorder() > 0) {
                GradientDrawable shape = new GradientDrawable();
                shape.setCornerRadius(SystemUtil.dpToPx(requireContext(), addTextProperties.getBackgroundBorder()));
                int red = Color.red(addTextProperties.getBackgroundColor());
                int green = Color.green(addTextProperties.getBackgroundColor());
                int blue = Color.blue(addTextProperties.getBackgroundColor());
                shape.setColor(Color.argb(addTextProperties.getBackgroundAlpha(), red, green, blue));
                tvOperation.setBackground(shape);
            }
        }

        if (addTextProperties.getPaddingHeight() > 0) {
            tvOperation.setPadding(tvOperation.getPaddingLeft(), addTextProperties.getPaddingHeight(), tvOperation.getPaddingRight(), addTextProperties.getPaddingHeight());
        }
        if (addTextProperties.getPaddingWidth() > 0) {
            tvOperation.setPadding(addTextProperties.getPaddingWidth(), tvOperation.getPaddingTop(), addTextProperties.getPaddingWidth(), tvOperation.getPaddingBottom());
        }

        if (addTextProperties.getText() != null) {
            tvOperation.setText(addTextProperties.getText());
            edText.setText(addTextProperties.getText());
        }

        if (newSticker) {
            tvOperation.setPadding(SystemUtil.dpToPx(requireContext(), addTextProperties.getPaddingWidth()), 3, SystemUtil.dpToPx(requireContext(), addTextProperties.getPaddingWidth()), 3);
        } else {
            tvOperation.setPadding(SystemUtil.dpToPx(requireContext(), addTextProperties.getPaddingWidth()), tvOperation.getPaddingTop(), SystemUtil.dpToPx(requireContext(), addTextProperties.getPaddingWidth()), tvOperation.getPaddingBottom());
        }

        tvOperation.setLetterSpacing(addTextProperties.getTextSpacing());
        tvOperation.setLineSpacing(addTextProperties.getLineSpacingAddition(), addTextProperties.getLineSpacingMultiply());
        tvOperation.setTextColor(addTextProperties.getTextColor());
        tvOperation.setTextAlignment(addTextProperties.getTextAlign());
        tvOperation.setTextSize(addTextProperties.getTextSize());
        tvOperation.setTypeface(addTextProperties.getFontName());

        tvOperation.invalidate();
    }
}