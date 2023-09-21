package com.tenoku.hornsound.audiotool.Thomsan_pranksound;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.iten.tenoku.ad.AdShow;
import com.iten.tenoku.ad.Qureka.QuerkaNative;
import com.iten.tenoku.utils.AdConstant;
import com.iten.tenoku.utils.AdUtils;
import com.iten.tenoku.utils.MyApplication;
import com.tenoku.hornsound.audiotool.Comman.BaseActivity;
import com.tenoku.hornsound.audiotool.R;
import com.tenoku.hornsound.audiotool.databinding.ActivityCreateOwnPrankSoundBinding;
import com.tenoku.hornsound.audiotool.utils.GlobalVariable;
import com.tenoku.hornsound.audiotool.utils.MarkerView;
import com.tenoku.hornsound.audiotool.utils.SamplePlayer;
import com.tenoku.hornsound.audiotool.utils.SongMetadataReader;
import com.tenoku.hornsound.audiotool.utils.SoundFile;
import com.tenoku.hornsound.audiotool.utils.WaveformView;

import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.util.Calendar;

public class Thomsan_TrimPrankSoundActivity extends BaseActivity implements MarkerView.MarkerListener, WaveformView.WaveformListener {

    Activity activity;
    ActivityCreateOwnPrankSoundBinding x;
    File outFile;
    private SamplePlayer mPlayer;
    private boolean mIsPlaying;
    private AlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;
    private SoundFile mSoundFile;
    private Thread mLoadSoundFileThread;
    private Thread mRecordAudioThread;
    private Thread mSaveSoundFileThread;
    private boolean mKeyDown;
    private Handler mHandler;
    private int mStartPos;
    private int mLastDisplayedStartPos;
    private int mLastDisplayedEndPos;
    private int mEndPos;
    private boolean mTouchDragging;
    private int mWidth;
    private int mPlayEndMsec;
    private int mFlingVelocity;
    private int mOffset;
    private int mOffsetGoal;
    private int mMaxPos;
    private String mCaption = "";
    private float mDensity;
    private int mPlayStartMsec;
    private int mMarkerLeftInset = 0;
    private int mMarkerRightInset = 0;
    private int mMarkerTopOffset = 0;
    private int mMarkerBottomOffset = 0;
    private boolean mStartVisible;
    private boolean mEndVisible;
    private float mTouchStart;
    private int mTouchInitialStartPos;
    private int mTouchInitialEndPos;
    private int mTouchInitialOffset;
    private long mWaveformTouchStartMsec;
    private boolean mRecordingKeepGoing;
    private boolean mLoadingKeepGoing;
    private File mFile;
    private TextWatcher mTextWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        public void onTextChanged(CharSequence s,
                                  int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (x.editTextStartTime.hasFocus()) {
                try {
                    mStartPos = x.waveform.secondsToPixels(
                            Double.parseDouble(
                                    x.editTextStartTime.getText().toString()));
                    updateDisplay();
                } catch (NumberFormatException e) {
                }
            }
            if (x.editTextEndTime.hasFocus()) {
                try {
                    mEndPos = x.waveform.secondsToPixels(
                            Double.parseDouble(
                                    x.editTextEndTime.getText().toString()));
                    updateDisplay();
                } catch (NumberFormatException e) {
                }
            }
        }
    };
    private String mArtist;
    private String mTitle;
    private long mLoadingLastUpdateTime;
    private boolean mFinishActivity;
    private String mInfoContent;

    private synchronized void handlePause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
        }
        x.waveform.setPlayback(-1);
        mIsPlaying = false;
        enableDisableButtons();
    }

    private void showFinalAlert(Exception e, int messageResourceId) {
        showFinalAlert(e, getResources().getText(messageResourceId));
    }

    private void showFinalAlert(Exception e, CharSequence message) {
        CharSequence title;

        if (e != null) {
            Log.e("Ringdroid", "Error: " + message);

            title = getResources().getText(R.string.alert_title_failure);
            setResult(RESULT_CANCELED, new Intent());
        } else {
            Log.v("Ringdroid", "Success: " + message);
            title = getResources().getText(R.string.alert_title_success);
        }

        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                        R.string.alert_ok_button,
                        (dialog, whichButton) -> finish())
                .setCancelable(false)
                .show();
    }

    private synchronized void onPlay(int startPosition) {
        if (mIsPlaying) {
            handlePause();
            return;
        }

        if (mPlayer == null) {
            // Not initialized yet
            return;
        }

        try {
            mPlayStartMsec = x.waveform.pixelsToMillisecs(startPosition);
            if (startPosition < mStartPos) {
                mPlayEndMsec = x.waveform.pixelsToMillisecs(mStartPos);
            } else if (startPosition > mEndPos) {
                mPlayEndMsec = x.waveform.pixelsToMillisecs(mMaxPos);
            } else {
                mPlayEndMsec = x.waveform.pixelsToMillisecs(mEndPos);
            }
            mPlayer.setOnCompletionListener(this::handlePause);
            mIsPlaying = true;

            mPlayer.seekTo(mPlayStartMsec);
            mPlayer.start();
            updateDisplay();
            enableDisableButtons();
        } catch (Exception e) {
            showFinalAlert(e, R.string.play_error);
        }
    }

    private void enableDisableButtons() {
        if (mIsPlaying) {
            x.btnPlayPause.setImageResource(R.drawable.ic_pause_audio);
            x.btnPlayPause.setContentDescription(getResources().getText(R.string.stop));
        } else {
            x.btnPlayPause.setImageResource(R.drawable.ic_playhorn);
            x.btnPlayPause.setContentDescription(getResources().getText(R.string.play));
        }
    }

    private synchronized void updateDisplay() {
        if (mIsPlaying) {
            int now = mPlayer.getCurrentPosition();
            int frames = x.waveform.millisecsToPixels(now);
            x.waveform.setPlayback(frames);
            setOffsetGoalNoUpdate(frames - mWidth / 2);
            if (now >= mPlayEndMsec) {
                handlePause();
            }
        }

        if (!mTouchDragging) {
            int offsetDelta;

            if (mFlingVelocity != 0) {
                offsetDelta = mFlingVelocity / 30;
                if (mFlingVelocity > 80) {
                    mFlingVelocity -= 80;
                } else if (mFlingVelocity < -80) {
                    mFlingVelocity += 80;
                } else {
                    mFlingVelocity = 0;
                }

                mOffset += offsetDelta;

                if (mOffset + mWidth / 2 > mMaxPos) {
                    mOffset = mMaxPos - mWidth / 2;
                    mFlingVelocity = 0;
                }
                if (mOffset < 0) {
                    mOffset = 0;
                    mFlingVelocity = 0;
                }
                mOffsetGoal = mOffset;
            } else {
                offsetDelta = mOffsetGoal - mOffset;

                if (offsetDelta > 10)
                    offsetDelta = offsetDelta / 10;
                else if (offsetDelta > 0)
                    offsetDelta = 1;
                else if (offsetDelta < -10)
                    offsetDelta = offsetDelta / 10;
                else if (offsetDelta < 0)
                    offsetDelta = -1;
                else
                    offsetDelta = 0;

                mOffset += offsetDelta;
            }
        }

        x.waveform.setParameters(mStartPos, mEndPos, mOffset);
        x.waveform.invalidate();

        x.startmarker.setContentDescription(
                getResources().getText(R.string.start_marker) + " " +
                        formatTime(mStartPos));
        x.endmarker.setContentDescription(
                getResources().getText(R.string.end_marker) + " " +
                        formatTime(mEndPos));

        int startX = mStartPos - mOffset - mMarkerLeftInset;
        if (startX + x.startmarker.getWidth() >= 0) {
            if (!mStartVisible) {
                // Delay this to avoid flicker
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        mStartVisible = true;
                        x.startmarker.setAlpha(1f);
                    }
                }, 0);
            }
        } else {
            if (mStartVisible) {
                x.startmarker.setAlpha(0f);
                mStartVisible = false;
            }
            startX = 0;
        }

        int endX = mEndPos - mOffset - x.endmarker.getWidth() + mMarkerRightInset;
        if (endX + x.endmarker.getWidth() >= 0) {
            if (!mEndVisible) {
                // Delay this to avoid flicker
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        mEndVisible = true;
                        x.endmarker.setAlpha(1f);
                    }
                }, 0);
            }
        } else {
            if (mEndVisible) {
                x.endmarker.setAlpha(0f);
                mEndVisible = false;
            }
            endX = 0;
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(
                startX,
                mMarkerTopOffset,
                -x.startmarker.getWidth(),
                -x.startmarker.getHeight());
        x.startmarker.setLayoutParams(params);

        params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(
                endX,
                x.waveform.getMeasuredHeight() - x.endmarker.getHeight() - mMarkerBottomOffset,
                -x.startmarker.getWidth(),
                -x.startmarker.getHeight());
        x.endmarker.setLayoutParams(params);
    }

    private void setOffsetGoalNoUpdate(int offset) {
        if (mTouchDragging) {
            return;
        }

        mOffsetGoal = offset;
        if (mOffsetGoal + mWidth / 2 > mMaxPos)
            mOffsetGoal = mMaxPos - mWidth / 2;
        if (mOffsetGoal < 0)
            mOffsetGoal = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = ActivityCreateOwnPrankSoundBinding.inflate(getLayoutInflater());
        setContentView(x.getRoot());
        activity = this;
        todo();
    }

    private void todo() {

        x.viewToolbar.imageViewBack.setOnClickListener(v -> onBackPressed());
        x.viewToolbar.textViewTitle.setText("Trim Audio");
        QuerkaNative.getInstance(activity).QuerkaAd(x.viewToolbar.gifImageView, null, x.viewToolbar.adTitle, null, x.viewToolbar.qurka);
        mPlayer = null;
        mIsPlaying = false;
        mAlertDialog = null;
        mProgressDialog = null;
        mLoadSoundFileThread = null;
        mRecordAudioThread = null;
        mSaveSoundFileThread = null;
        mSoundFile = null;
        mKeyDown = false;
        mHandler = new Handler();
        loadGui();
        mHandler.postDelayed(mTimerRunnable, 100);
        loadFromFile();
        x.textViewSave.setOnClickListener(v -> onSave());
        x.textViewCancel.setOnClickListener(v -> onBackPressed());
    }

    private void onSave() {
        if (mIsPlaying) {
            handlePause();
        }

        saveRingtone();
    }

    private void saveRingtone() {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC + File.separator+ "Airhorn" + File.separator + "My Prank Sounds");
        if (!path.exists()) {
            path.mkdirs();
        }
        Calendar calender = Calendar.getInstance();
        String fileDateTime = calender.get(Calendar.YEAR) + "_" +
                calender.get(Calendar.MONTH) + "_" +
                calender.get(Calendar.DAY_OF_MONTH) + "_" +
                calender.get(Calendar.HOUR_OF_DAY) + "_" +
                calender.get(Calendar.MINUTE) + "_" +
                calender.get(Calendar.SECOND);
        String fName = "pranksound_";
//        if (fileName != null && !fileName.isEmpty())
//            fName = fileName;
        File newFile = new File(path + File.separator + fName + fileDateTime);

        double startTime = x.waveform.pixelsToSeconds(mStartPos);
        double endTime = x.waveform.pixelsToSeconds(mEndPos);
        final int startFrame = x.waveform.secondsToFrames(startTime);
        final int endFrame = x.waveform.secondsToFrames(endTime);
        final int duration = (int) (endTime - startTime + 0.5);

        // Create an indeterminate progress dialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setTitle(R.string.progress_dialog_saving);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setProgressPercentFormat(null);
        mProgressDialog.show();

        // Save the sound file in a background thread
        mSaveSoundFileThread = new Thread() {
            public void run() {
                outFile = new File(newFile + ".mp3");
                boolean fallbackToWAV = false;
                try {
                    // Write the new file
                    mSoundFile.WriteFile(outFile, startFrame, endFrame - startFrame);
                } catch (Exception e) {
                    // log the error and try to create a .wav file instead
                    if (outFile.exists()) {
                        outFile.delete();
                    }
                    StringWriter writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));
                    fallbackToWAV = true;
                }
                // Try to load the new file to make sure it worked
                try {
                    final SoundFile.ProgressListener listener =
                            frac -> {
                                return true;  // Keep going
                            };
                    SoundFile.create(newFile + ".mp3", listener);
                } catch (final Exception e) {
                    mProgressDialog.dismiss();
                    e.printStackTrace();
                    mInfoContent = e.toString();
                    runOnUiThread(() -> x.textViewInfo.setText(mInfoContent));

                    Runnable runnable = () -> showFinalAlert(e, getResources().getText(R.string.write_error));
                    mHandler.post(runnable);
                    return;
                }

                mProgressDialog.dismiss();

                Runnable runnable = new Runnable() {
                    public void run() {
                        afterSavingRingtone(fName + fileDateTime, String.valueOf(newFile), duration);
                    }
                };
                mHandler.post(runnable);
            }
        };
        mSaveSoundFileThread.start();
    }

    private void afterSavingRingtone(CharSequence title, String outPutPath, int duration) {
        File outputFile = new File(outPutPath);
        long fileSize = outputFile.length();
        // Create the database record, pointing to the existing file path
        String mimeType;
        if (outPutPath.endsWith(".m4a")) {
            mimeType = "audio/mp4a-latm";
        } else if (outPutPath.endsWith(".wav")) {
            mimeType = "audio/wav";
        } else {
            // This should never happen.
            mimeType = "audio/mpeg";
        }

        String artist = "" + getResources().getText(R.string.artist_name);

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, outPutPath);
        values.put(MediaStore.MediaColumns.TITLE, title.toString());
        values.put(MediaStore.MediaColumns.SIZE, fileSize);
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);

        values.put(MediaStore.Audio.Media.ARTIST, artist);
        values.put(MediaStore.Audio.Media.DURATION, duration);


        // Insert it into the database
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(outPutPath);
        final Uri newUri = getContentResolver().insert(uri, values);
        setResult(RESULT_OK, new Intent().setData(newUri));

        // If Ringdroid was launched to get content, just return

        // There's nothing more to do with music or an alarm.  Show a
        // success message and then quit.
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        mediaScanIntent.setData(contentUri);
        activity.sendBroadcast(mediaScanIntent);
        MediaScannerConnection.scanFile(
                getApplicationContext(),
                new String[]{outFile.getAbsolutePath()},
                null,
                (path, ee) -> {

                });


        if (duration <= 10) {
            Toast.makeText(activity, getString(R.string.save_success_message), Toast.LENGTH_SHORT).show();
//            Utils.customToast(activity, getString(R.string.save_success_message));
            Intent intent1 = new Intent(activity, Thomsan_PrankSoundPlayActivity.class);
            intent1.putExtra(GlobalVariable.MUSIC_FILE, outFile);
            startActivity(intent1);
            finish();
        } else {
            Toast.makeText(activity, "Sound File Must be Less Then 10 Sec ", Toast.LENGTH_SHORT).show();

//            Utils.customToast(activity, "Sound File Must be Less Then 10 Sec ");
        }


    }

    private String makeRingtoneFilename(String fileDateTime, String extension) {

        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC + "/" + getString(R.string.app_name) + "/" + getString(R.string.ringtone_cutter));

        // Turn the title into a filename
        StringBuilder filename = new StringBuilder();
        for (int i = 0; i < fileDateTime.length(); i++) {
            if (Character.isLetterOrDigit(fileDateTime.charAt(i))) {
                filename.append(fileDateTime.charAt(i));
            }
        }

        // Try to make the filename unique
        String path = null;
        for (int i = 0; i < 100; i++) {
            String testPath;
            if (i > 0)
                testPath = file + filename.toString() + i + extension;
            else
                testPath = file + filename.toString() + extension;

            try {
                RandomAccessFile f = new RandomAccessFile(new File(testPath), "r");
                f.close();
            } catch (Exception e) {
                // Good, the file didn't exist
                path = testPath;
                path = testPath;
                break;
            }
        }

        return path;
    }

    private void loadFromFile() {
        mFile = new File(GlobalVariable.audio.getData());
        SongMetadataReader metadataReader = new SongMetadataReader(
                this, GlobalVariable.audio.getData());
        mTitle = metadataReader.mTitle;
        mArtist = metadataReader.mArtist;

        String titleLabel = mTitle;
        if (mArtist != null && mArtist.length() > 0) {
            titleLabel += " - " + mArtist;
        }
        setTitle(titleLabel);

        mLoadingLastUpdateTime = getCurrentTime();
        mLoadingKeepGoing = true;
        mFinishActivity = false;
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setTitle(R.string.progress_dialog_loading);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(
                dialog -> {
                    mLoadingKeepGoing = false;
                    mFinishActivity = true;
                });
        mProgressDialog.show();

        final SoundFile.ProgressListener listener =
                fractionComplete -> {
                    long now = getCurrentTime();
                    if (now - mLoadingLastUpdateTime > 100) {
                        mProgressDialog.setProgress(
                                (int) (mProgressDialog.getMax() * fractionComplete));
                        mLoadingLastUpdateTime = now;
                    }
                    return mLoadingKeepGoing;
                };

        // Load the sound file in a background thread
        mLoadSoundFileThread = new Thread() {
            public void run() {
                try {
                    mSoundFile = SoundFile.create(mFile.getAbsolutePath(), listener);

                    if (mSoundFile == null) {
                        mProgressDialog.dismiss();
                        String name = mFile.getName().toLowerCase();
                        String[] components = name.split("\\.");
                        String err;
                        if (components.length < 2) {
                            err = getResources().getString(
                                    R.string.no_extension_error);
                        } else {
                            err = getResources().getString(
                                    R.string.bad_extension_error) + " " +
                                    components[components.length - 1];
                        }
                        final String finalErr = err;
                        Runnable runnable = () -> showFinalAlert(new Exception(), finalErr);
                        mHandler.post(runnable);
                        return;
                    }
                    mPlayer = new SamplePlayer(mSoundFile);
                } catch (final Exception e) {
                    mProgressDialog.dismiss();
                    e.printStackTrace();
                    mInfoContent = e.toString();
                    runOnUiThread(() -> x.textViewInfo.setText(mInfoContent));

                    Runnable runnable = () -> showFinalAlert(e, getResources().getText(R.string.read_error));
                    mHandler.post(runnable);
                    return;
                }
                mProgressDialog.dismiss();
                if (mLoadingKeepGoing) {
                    Runnable runnable = () -> finishOpeningSoundFile();
                    mHandler.post(runnable);
                } else if (mFinishActivity) {
                    finish();
                }
            }
        };
        mLoadSoundFileThread.start();
    }

    private void finishOpeningSoundFile() {
        x.waveform.setSoundFile(mSoundFile);
        x.waveform.recomputeHeights(mDensity);

        mMaxPos = x.waveform.maxPos();
        mLastDisplayedStartPos = -1;
        mLastDisplayedEndPos = -1;

        mTouchDragging = false;

        mOffset = 0;
        mOffsetGoal = 0;
        mFlingVelocity = 0;
        resetPositions();
        if (mEndPos > mMaxPos)
            mEndPos = mMaxPos;

        mCaption =
                mSoundFile.getFiletype() + ", " +
                        mSoundFile.getSampleRate() + " Hz, " +
                        mSoundFile.getAvgBitrateKbps() + " kbps, " +
                        formatTime(mMaxPos) + " " +
                        getResources().getString(R.string.time_seconds);
        x.textViewInfo.setText(mCaption);

        updateDisplay();
    }

    private void resetPositions() {
        mStartPos = x.waveform.secondsToPixels(0.0);
        mEndPos = x.waveform.secondsToPixels(15.0);
    }

    private String getFileName(String extension) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC + "/" + getString(R.string.app_name) + "/" + getString(R.string.ringtone_cutter));
        if (!path.exists()) {
            path.mkdirs();
        }
        Calendar calender = Calendar.getInstance();
        String fileDateTime = calender.get(Calendar.YEAR) + "_" +
                calender.get(Calendar.MONTH) + "_" +
                calender.get(Calendar.DAY_OF_MONTH) + "_" +
                calender.get(Calendar.HOUR_OF_DAY) + "_" +
                calender.get(Calendar.MINUTE) + "_" +
                calender.get(Calendar.SECOND);
        String fName = "ringtone_";
//        if (fileName != null && !fileName.isEmpty())
//            fName = fileName;
        File newFile = new File(path + File.separator +
                (fName) + fileDateTime + extension);
        return String.valueOf(newFile);
    }

    long getByteNoFromSecNo(long startSec, long totalDuration, long noOfBytes) {
        return startSec * noOfBytes / totalDuration;
    }

    public String getDuration(long msec) {
        if (msec == 0)
            return "00:00";
        long sec = msec / 1000;
        long min = sec / 60;
        sec = sec % 60;
        String minstr = min + "";
        String secstr = sec + "";
        if (min < 10)
            minstr = "0" + min;
        if (sec < 10)
            secstr = "0" + sec;
        return minstr + ":" + secstr;
    }

    private void loadGui() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mDensity = metrics.density;

        mMarkerLeftInset = (int) (46 * mDensity);
        mMarkerRightInset = (int) (48 * mDensity);
        mMarkerTopOffset = (int) (10 * mDensity);
        mMarkerBottomOffset = (int) (10 * mDensity);


        x.editTextStartTime.addTextChangedListener(mTextWatcher);
        x.editTextEndTime.addTextChangedListener(mTextWatcher);
        x.btnPlayPause.setOnClickListener(v -> {
            onPlay(mStartPos);
            x.btnPlayPause.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        });
        x.imageButtonRewind.setOnClickListener(v -> {
            if (mIsPlaying) {
                int newPos = mPlayer.getCurrentPosition() - 5000;
                if (newPos < mPlayStartMsec)
                    newPos = mPlayStartMsec;
                mPlayer.seekTo(newPos);
            } else {
                x.startmarker.requestFocus();
                markerFocus(x.startmarker);
            }
        });
        x.imageButtonForward.setOnClickListener(v -> {
            if (mIsPlaying) {
                int newPos = 5000 + mPlayer.getCurrentPosition();
                if (newPos > mPlayEndMsec)
                    newPos = mPlayEndMsec;
                mPlayer.seekTo(newPos);
            } else {
                x.endmarker.requestFocus();
                markerFocus(x.endmarker);
            }
        });
        enableDisableButtons();

        x.waveform.setListener(this);
        x.textViewInfo.setText(mCaption);

        mMaxPos = 0;
        mLastDisplayedStartPos = -1;
        mLastDisplayedEndPos = -1;

        if (mSoundFile != null && !x.waveform.hasSoundFile()) {
            x.waveform.setSoundFile(mSoundFile);
            x.waveform.recomputeHeights(mDensity);
            mMaxPos = x.waveform.maxPos();
        }

        x.startmarker.setListener(this);
        x.startmarker.setAlpha(1f);
        x.startmarker.setFocusable(true);
        x.startmarker.setFocusableInTouchMode(true);
        mStartVisible = true;

        x.endmarker.setListener(this);
        x.endmarker.setAlpha(1f);
        x.endmarker.setFocusable(true);
        x.endmarker.setFocusableInTouchMode(true);
        mEndVisible = true;

        updateDisplay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.sharedPreferencesHelper.getOnAdsResume()) {
            setNativeInResume();
        }

    }

    private void setNativeInResume() {
        AdShow.getInstance(activity).ShowNativeAd(x.nativeBottomAdLayout.nativeAdLayout, AdUtils.NativeType.NATIVE_BANNER);
        if (AdConstant.LIST_VIEW_PER_AD != 0 && MyApplication.sharedPreferencesHelper.getNativeBigAdShow()) {
            AdShow.getInstance(activity).ShowNativeAd(x.nativeBigAdLayout.nativeAdLayout, AdUtils.NativeType.NATIVE_BIG);
        } else {
            x.nativeBigAdLayout.nativeAdLayout.setVisibility(View.GONE);

        }
    }

    private String formatDecimal(double x) {
        int xWhole = (int) x;
        int xFrac = (int) (100 * (x - xWhole) + 0.5);

        if (xFrac >= 100) {
            xWhole++; //Round up
            xFrac -= 100; //Now we need the remainder after the round up
            if (xFrac < 10) {
                xFrac *= 10; //we need a fraction that is 2 digits long
            }
        }

        if (xFrac < 10)
            return xWhole + ".0" + xFrac;
        else
            return xWhole + "." + xFrac;
    }

    private long getCurrentTime() {
        return System.nanoTime() / 1000000;
    }

    private String formatTime(int pixels) {
        if (x.waveform.isInitialized()) {
            return formatDecimal(x.waveform.pixelsToSeconds(pixels));
        } else {
            return "";
        }
    }

    private int trap(int pos) {
        if (pos < 0)
            return 0;
        return Math.min(pos, mMaxPos);
    }

    private void setOffsetGoalStart() {
        setOffsetGoal(mStartPos - mWidth / 2);
    }

    private void setOffsetGoal(int offset) {
        setOffsetGoalNoUpdate(offset);
        updateDisplay();
    }

    private void setOffsetGoalEnd() {
        setOffsetGoal(mEndPos - mWidth / 2);
    }

    private void setOffsetGoalStartNoUpdate() {
        setOffsetGoalNoUpdate(mStartPos - mWidth / 2);
    }

    private void setOffsetGoalEndNoUpdate() {
        setOffsetGoalNoUpdate(mEndPos - mWidth / 2);
    }

    @Override
    public void markerTouchStart(MarkerView marker, float pos) {
        mTouchDragging = true;
        mTouchStart = pos;
        mTouchInitialStartPos = mStartPos;
        mTouchInitialEndPos = mEndPos;
    }

    @Override
    public void markerTouchMove(MarkerView marker, float pos) {
        float delta = pos - mTouchStart;

        if (marker == x.startmarker) {
            mStartPos = trap((int) (mTouchInitialStartPos + delta));
            mEndPos = trap((int) (mTouchInitialEndPos + delta));
        } else {
            mEndPos = trap((int) (mTouchInitialEndPos + delta));
            if (mEndPos < mStartPos)
                mEndPos = mStartPos;
        }

        updateDisplay();
    }

    @Override
    public void markerTouchEnd(MarkerView marker) {
        mTouchDragging = false;
        if (marker == x.startmarker) {
            setOffsetGoalStart();
        } else {
            setOffsetGoalEnd();
        }

    }

    @Override
    public void markerFocus(MarkerView marker) {
        mKeyDown = false;
        if (marker == x.startmarker) {
            setOffsetGoalStartNoUpdate();
        } else {
            setOffsetGoalEndNoUpdate();
        }

        // Delay updaing the display because if this focus was in
        // response to a touch event, we want to receive the touch
        // event too before updating the display.
        mHandler.postDelayed(this::updateDisplay, 100);
    }

    @Override
    public void markerLeft(MarkerView marker, int velocity) {
        mKeyDown = true;

        if (marker == x.startmarker) {
            int saveStart = mStartPos;
            mStartPos = trap(mStartPos - velocity);
            mEndPos = trap(mEndPos - (saveStart - mStartPos));
            setOffsetGoalStart();
        }

        if (marker == x.endmarker) {
            if (mEndPos == mStartPos) {
                mStartPos = trap(mStartPos - velocity);
                mEndPos = mStartPos;
            } else {
                mEndPos = trap(mEndPos - velocity);
            }

            setOffsetGoalEnd();
        }

        updateDisplay();
    }

    @Override
    public void markerRight(MarkerView marker, int velocity) {
        mKeyDown = true;

        if (marker == x.startmarker) {
            int saveStart = mStartPos;
            mStartPos += velocity;
            if (mStartPos > mMaxPos)
                mStartPos = mMaxPos;
            mEndPos += (mStartPos - saveStart);
            if (mEndPos > mMaxPos)
                mEndPos = mMaxPos;

            setOffsetGoalStart();
        }

        if (marker == x.endmarker) {
            mEndPos += velocity;
            if (mEndPos > mMaxPos)
                mEndPos = mMaxPos;

            setOffsetGoalEnd();
        }

        updateDisplay();
    }

    @Override
    public void markerEnter(MarkerView marker) {
        mKeyDown = false;
        updateDisplay();
    }

    @Override
    public void markerKeyUp() {
        mKeyDown = false;
        updateDisplay();
    }

    @Override
    public void markerDraw() {

    }

    @Override
    public void waveformTouchStart(float x) {
        mTouchDragging = true;
        mTouchStart = x;
        mTouchInitialOffset = mOffset;
        mFlingVelocity = 0;
        mWaveformTouchStartMsec = getCurrentTime();
    }

    @Override
    public void waveformTouchMove(float x) {
        mOffset = trap((int) (mTouchInitialOffset + (mTouchStart - x)));
        updateDisplay();
    }

    @Override
    public void waveformTouchEnd() {
        mTouchDragging = false;
        mOffsetGoal = mOffset;

        long elapsedMsec = getCurrentTime() - mWaveformTouchStartMsec;
        if (elapsedMsec < 300) {
            if (mIsPlaying) {
                int seekMsec = x.waveform.pixelsToMillisecs(
                        (int) (mTouchStart + mOffset));
                if (seekMsec >= mPlayStartMsec &&
                        seekMsec < mPlayEndMsec) {
                    mPlayer.seekTo(seekMsec);
                } else {
                    handlePause();
                }
            } else {
                onPlay((int) (mTouchStart + mOffset));
            }
        }
    }

    @Override
    public void waveformFling(float x) {
        mTouchDragging = false;
        mOffsetGoal = mOffset;
        mFlingVelocity = (int) (-x);
        updateDisplay();
    }

    @Override
    public void waveformDraw() {
        mWidth = x.waveform.getMeasuredWidth();
        if (mOffsetGoal != mOffset && !mKeyDown)
            updateDisplay();
        else if (mIsPlaying) {
            updateDisplay();
        } else if (mFlingVelocity != 0) {
            updateDisplay();
        }
    }

    @Override
    public void waveformZoomIn() {
        x.waveform.zoomIn();
        mStartPos = x.waveform.getStart();
        mEndPos = x.waveform.getEnd();
        mMaxPos = x.waveform.maxPos();
        mOffset = x.waveform.getOffset();
        mOffsetGoal = mOffset;
        updateDisplay();
    }

    @Override
    public void waveformZoomOut() {
        x.waveform.zoomOut();
        mStartPos = x.waveform.getStart();
        mEndPos = x.waveform.getEnd();
        mMaxPos = x.waveform.maxPos();
        mOffset = x.waveform.getOffset();
        mOffsetGoal = mOffset;
        updateDisplay();
    }

    private final Runnable mTimerRunnable = new Runnable() {
        public void run() {
            // Updating an EditText is slow on Android.  Make sure
            // we only do the update if the text has actually changed.
            if (mStartPos != mLastDisplayedStartPos &&
                    !x.editTextStartTime.hasFocus()) {
                x.editTextStartTime.setText(formatTime(mStartPos));
                mLastDisplayedStartPos = mStartPos;
            }

            if (mEndPos != mLastDisplayedEndPos &&
                    !x.editTextEndTime.hasFocus()) {
                x.editTextEndTime.setText(formatTime(mEndPos));
                mLastDisplayedEndPos = mEndPos;
            }

            mHandler.postDelayed(mTimerRunnable, 100);
        }
    };

    @Override
    public void onBackPressed() {
        AdShow.getInstance(activity).ShowAd(adShow -> Thomsan_TrimPrankSoundActivity.super.onBackPressed(), AdUtils.ClickType.BACK_CLICK);
    }
}