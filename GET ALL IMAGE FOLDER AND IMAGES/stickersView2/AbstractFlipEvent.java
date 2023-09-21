package topphotoeditor.bokehphotoeditor.stickersView2;

import android.view.MotionEvent;

import topphotoeditor.bokehphotoeditor.stickersView2.StickerIconEvent;
import topphotoeditor.bokehphotoeditor.stickersView2.StickerView;

public abstract class AbstractFlipEvent implements StickerIconEvent {

  @Override public void onActionDown(StickerView stickerView, MotionEvent event) {

  }

  @Override public void onActionMove(StickerView stickerView, MotionEvent event) {

  }

  @Override public void onActionUp(StickerView stickerView, MotionEvent event) {
    stickerView.flipCurrentSticker(getFlipDirection());
  }

  @StickerView.Flip protected abstract int getFlipDirection();
}
