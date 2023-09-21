
package topphotoeditor.bokehphotoeditor.stickersView2;

import android.view.MotionEvent;


public class AlignHorizontallyEvent implements StickerIconEvent {
    @Override
    public void onActionDown(StickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionMove(StickerView stickerView, MotionEvent event) {

    }

    @Override
    public void onActionUp(StickerView stickerView, MotionEvent event) {
        stickerView.alignHorizontally();
    }
}
