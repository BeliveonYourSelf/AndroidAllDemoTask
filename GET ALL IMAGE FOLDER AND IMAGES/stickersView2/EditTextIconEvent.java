package topphotoeditor.bokehphotoeditor.stickersView2;

import static topphotoeditor.bokehphotoeditor.editor.util.Constant.isTextEdit;

import android.view.MotionEvent;


public class EditTextIconEvent implements StickerIconEvent {
    @Override
    public void onActionDown(StickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionMove(StickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionUp(StickerView stickerView, MotionEvent event) {
        isTextEdit = true;
        stickerView.editTextSticker();
    }
}
