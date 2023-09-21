package topphotoeditor.bokehphotoeditor.stickersView2;

import topphotoeditor.bokehphotoeditor.stickersView2.AbstractFlipEvent;
import topphotoeditor.bokehphotoeditor.stickersView2.StickerView;

public class FlipHorizontallyEvent extends AbstractFlipEvent {

  @Override @StickerView.Flip protected int getFlipDirection() {
    return StickerView.FLIP_HORIZONTALLY;
  }
}
