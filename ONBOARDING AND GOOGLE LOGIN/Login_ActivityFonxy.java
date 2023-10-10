package com.singleck.loan.portals.fonxy_Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.singleck.load.ad.AdShow;
import com.singleck.load.ad.HandleClick.HandleClick;
import com.singleck.load.utils.AdConstant;
import com.singleck.load.utils.AdUtils;
import com.singleck.load.utils.MyApplication;
import com.singleck.loan.portals.R;
import com.singleck.loan.portals.fonxyopenappActivity.ExitActivityFonxy;
import com.singleck.loan.portals.fonxyopenappActivity.fonxyBaseActivity;
import com.singleck.loan.portals.databinding.ActivityLoginBinding;
import com.singleck.loan.portals.fonxy_Comman.fonxy_Model.Methods;
import com.singleck.loan.portals.fonxyOther.PreferenceManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rd.PageIndicatorView;

import java.util.Timer;
import java.util.TimerTask;


public class Login_ActivityFonxy extends fonxyBaseActivity {
    Activity activity;
    Button btnlogin;
    private int currentPage = 0;
    private GoogleSignInClient googleSignInClient;
    private Intent intent;
    private int[] layouts;
    PageIndicatorView pageIndicatorView;
    TextView text;
    Timer timer;
    ViewPager viewPager;

    private GoogleApiClient googleApiClient;

    ActivityLoginBinding binding;

    FirebaseAuth firebaseAuth;
    GoogleSignInAccount account;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.activity = this;

        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_200, getTheme()));
        } else if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_200));
        }

        if (!MyApplication.sharedPreferencesHelper.getOnAdsResume()) {
            if (AdConstant.LIST_VIEW_PER_AD != 0 && MyApplication.sharedPreferencesHelper.getNativeBigAdShow()) {
                AdShow.getInstance(activity).ShowNativeAd(binding.nativeMediumAdLayout.nativeAdLayout, AdUtils.NativeType.NATIVE_MEDIUM);
            } else {
                binding.nativeMediumAdLayout.nativeAdLayout.setVisibility(View.GONE);

            }
        }

        this.layouts = new int[]{R.layout.onboarding_1, R.layout.onboarding_2, R.layout.onboarding_3};
        this.viewPager = (ViewPager) findViewById(R.id.view_pager);
        this.pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        this.btnlogin = (Button) findViewById(R.id.btnlogin);
        this.viewPager.setAdapter(new MyViewPagerAdapter(this.activity, this.layouts));
        TextView textView = (TextView) findViewById(R.id.text);
        this.text = textView;
        textView.setText("Existing " + getResources().getString(R.string.app_name) + " Customer?");
        this.pageIndicatorView.setViewPager(this.viewPager);
        this.pageIndicatorView.setCount(this.layouts.length);
        final Handler handler = new Handler();
        Timer timer = new Timer();
        this.timer = timer;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPage == 3) {
                            currentPage = 0;
                        }
                        int i = currentPage;
                        currentPage = i + 1;
                        viewPager.setCurrentItem(i, true);
                    }
                });
            }
        }, 1000L, 3000L);
        this.googleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()).build();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

//         Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions);
        this.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Login_ActivityFonxy.this.GoogleLogin();
                Login_ActivityFonxy.this.startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(Login_ActivityFonxy.this.googleApiClient), 1);

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        // Check condition
        if (firebaseUser != null) {
            // When user already sign in redirect to profile activity
            Toast.makeText(activity, "Is from oncreate", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(activity, HomeActivityFonxy.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }


    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-In with Google succeeded, update UI
                        Log.d("Google Sign-In", "signInWithCredential:success");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // Sign-In with Google failed
                        Log.w("Google Sign-In", "signInWithCredential:failure", task.getException());
                        updateUI(null);
                    }
                });
    }


    public void GoogleLogin() {
        Methods.progressDialogShow(this.activity);
        GoogleSignInClient client = GoogleSignIn.getClient((Activity) this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build());
        this.googleSignInClient = client;
        Intent signInIntent = client.getSignInIntent();
        this.intent = signInIntent;
        startActivityForResult(signInIntent, Methods.RC_GOOGLE);


    }


    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        try {
            if (i == 1) {
                GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

                GoogleSignInResult signInResultFromIntent = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
                this.account = signInResultFromIntent.getSignInAccount();
                handleSignInResult(signInResultFromIntent);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
      /*  if (i == Methods.RC_GOOGLE) {
            Log.d("TAG", "onActivityResult: " + Methods.RC_GOOGLE);
            GoogleSignInAccount account = null;
            try {
                account = GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException.class);
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
            firebaseAuthWithGoogle(account);
            handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(intent));
        } else {
            Log.d("TAG", "onActivityResult: ELSE");
            Methods.progressDialogDismiss();
        }*/
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in, proceed to the next activity
        } else {

            // User is signed out
        }
    }

    private void handleSignInResult(GoogleSignInResult googleSignInResult) {
        if (googleSignInResult.isSuccess()) {
            String uri = googleSignInResult.getSignInAccount().getPhotoUrl() != null ? googleSignInResult.getSignInAccount().getPhotoUrl().toString() : "";
//            SharedPreferences.Editor edit = this.sharedPref.edit();
//            this.editor = edit;
//            edit.putString("name_user", "" + googleSignInResult.getSignInAccount().getDisplayName());
//            SharedPreferences.Editor editor = this.editor;
//            editor.putString("image", "" + uri);
//            SharedPreferences.Editor editor2 = this.editor;
//            editor2.putString("sign_in_email", "" + googleSignInResult.getSignInAccount().getEmail());
//            this.editor.apply();


            PreferenceManager.editor("selfie", uri);
            PreferenceManager.editor("name", googleSignInResult.getSignInAccount().getDisplayName());
            PreferenceManager.editor("email", googleSignInResult.getSignInAccount().getEmail());
            PreferenceManager.editor("isLogin", true);

            Methods.progressDialogDismiss();
            AdShow.getInstance(activity).ShowAd(new HandleClick() {
                @Override
                public void Show(boolean adShow) {
                    startActivity(new Intent(activity, HomeActivityFonxy.class));
                    finish();
                }
            }, AdUtils.ClickType.MAIN_CLICK);
            Log.d(NotificationCompat.CATEGORY_MESSAGE, "handle");
            return;
        } else {

            Toast.makeText(getApplicationContext(), "Error Occurred.Try Again", 1).show();
        }
    }


//    private void handleSignInResult(Task<GoogleSignInAccount> task) {
//        try {
//            GoogleSignInAccount result = task.getResult(ApiException.class);
//            Log.e("HHH...", "display name: " + result.getDisplayName());
//            result.getId();
//            String displayName = result.getDisplayName();
//            try {
//                PreferenceManager.editor("selfie", result.getPhotoUrl().toString());
//            } catch (Exception unused) {
//            }
//            String email = result.getEmail();
//            PreferenceManager.editor("name", displayName);
//            PreferenceManager.editor("email", email);
//            PreferenceManager.editor("isLogin", true);
//            Methods.progressDialogDismiss();
//            AdShow.getInstance(activity).ShowAd(new HandleClick() {
//                @Override
//                public void Show(boolean adShow) {
//                    startActivity(new Intent(activity, HomeActivityFonxy.class));
//                    finish();
//                }
//            }, AdUtils.ClickType.MAIN_CLICK);
//
//
//        } catch (ApiException unused2) {
//            Log.e("TAG", "handleSignInResult: " + unused2);
//            Methods.progressDialogDismiss();
//        }
//    }


    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public MyViewPagerAdapter(Activity activity, int[] iArr) {
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = (LayoutInflater) Login_ActivityFonxy.this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.layoutInflater = layoutInflater;
            View inflate = layoutInflater.inflate(Login_ActivityFonxy.this.layouts[i], viewGroup, false);
            viewGroup.addView(inflate);
            return inflate;
        }

        @Override
        public int getCount() {
            return Login_ActivityFonxy.this.layouts.length;
        }

        @Override
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.sharedPreferencesHelper.getOnAdsResume()) {
            if (AdConstant.LIST_VIEW_PER_AD != 0 && MyApplication.sharedPreferencesHelper.getNativeBigAdShow()) {
                AdShow.getInstance(activity).ShowNativeAd(binding.nativeMediumAdLayout.nativeAdLayout, AdUtils.NativeType.NATIVE_MEDIUM);
            } else {
                binding.nativeMediumAdLayout.nativeAdLayout.setVisibility(View.GONE);

            }

        }

    }

    @Override
    public void onBackPressed() {
        AdShow.getInstance(activity).ShowAd(new HandleClick() {
            @Override
            public void Show(boolean adShow) {
                startActivity(new Intent(activity, ExitActivityFonxy.class));
                finish();
            }
        }, AdUtils.ClickType.BACK_CLICK);


    }
}
