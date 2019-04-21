package bluecrunch.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import bluecrunch.utils.Utils;


public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel>
        extends AppCompatActivity implements BaseFragment.Callback {

    private T mViewDataBinding;
    private V mViewModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDataBinding();
        mViewModel.message.observe(this, (Observer<BaseMessage>) message -> {
            if (message!=null) {
                String content = message.messageId == -1 ? message.message:getString(message.messageId) ;
                String title = message.titleId == -1 ?  message.title:getString(message.titleId);
                String postext = message.posTextId == -1 ? getString(message.titleId) :message.title;
                if(title==null)title="";
                showPopUp(title,content,postext,true);
            }
        });
    }

    public abstract int getBindingVariable();

    public abstract
    @LayoutRes
    int getLayoutId();

    public abstract V getViewModel();


    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }



    protected void showProgressDialog(int staringId) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(getString(staringId));
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }


    public AlertDialog showPopUp(@StringRes int messageId,
                          final @StringRes int posActionName,
                          final DialogInterface.OnClickListener positiveAction,
                          final @StringRes int negAction,
                          boolean isCancelable){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        return builder.setCancelable(isCancelable)
                .setMessage(getString(messageId))
                .setPositiveButton(getString(posActionName),positiveAction)
                .setNegativeButton(negAction, (dialog, which) -> dialog.dismiss()).show();
    }
    public AlertDialog showPopUp(@StringRes int messageId,
                          final @StringRes int posAction,
                          final DialogInterface.OnClickListener positiveAction,
                          final @StringRes int negActioname,
                          final DialogInterface.OnClickListener negAction,
                          boolean isCancelable){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        return builder.setCancelable(isCancelable)
                .setMessage(getString(messageId))
                .setPositiveButton(getString(posAction), positiveAction)
                .setNegativeButton(getString(negActioname),negAction).show();
    }
    public AlertDialog showPopUp(@StringRes int messageId,
                          final String posActionName,
                          final DialogInterface.OnClickListener positiveAction,
                          final String negActionName,
                          final DialogInterface.OnClickListener negAction,
                          boolean isCancelable){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        return builder.setCancelable(isCancelable)
                .setMessage(getString(messageId))
                .setPositiveButton(posActionName, positiveAction)
                .setNegativeButton(negActionName, negAction).show();
    }
    public AlertDialog showPopUp(@StringRes int messageId,
                          final @StringRes int posAction,
                          final DialogInterface.OnClickListener positiveAction,
                          boolean isCancelable){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        return builder.setCancelable(isCancelable)
                .setMessage(getString(messageId))
                .setPositiveButton(getString(posAction), positiveAction)
                .show();
    }
    public AlertDialog showPopUp(@StringRes int messageId,
                          final @StringRes int posAction,
                          boolean isCancelable){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        return builder.setCancelable(isCancelable)
                .setMessage(getString(messageId))
                .setPositiveButton(getString(posAction),null)
                .show();
    }

    public AlertDialog showPopUp(String title,String message,
                          final  int posAction,
                          final DialogInterface.OnClickListener positiveAction,
                          final String negAction,
                          boolean isCancelable){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        return builder.setCancelable(isCancelable)
                .setMessage(message)
                .setPositiveButton(getString(posAction), positiveAction)
                .setNegativeButton(negAction, (dialog, which) -> dialog.dismiss()).show();
    }
    public AlertDialog showPopUp( String title,
                                  String message,
                          final  String posAction,
                          boolean isCancelable){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        return builder.setCancelable(isCancelable)
                .setMessage(message)
                .setPositiveButton(posAction, (dialog, which) -> dialog.dismiss())
                .show();
    }
    public AlertDialog showPopUp(String message,
                          final @StringRes int posAction,
                          final DialogInterface.OnClickListener positiveAction,
                          boolean isCancelable){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        return builder.setCancelable(isCancelable)
                .setMessage(message)
                .setPositiveButton(getString(posAction),positiveAction)
                .show();
    }
    public AlertDialog showPopUp( String message,
                          final @StringRes int posAction,
                          boolean isCancelable){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        return builder.setCancelable(isCancelable)
                .setMessage(message)
                .setPositiveButton(getString(posAction),null)
                .show();
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        String newLocale1;
        newLocale1 = Utils.getLocaleLanguage();
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
//            String newLocale;
//            newLocale = Utils.getLocaleLanguage();
//            Context context = MyContextWrapper.wrap(newBase, newLocale);
//            super.attachBaseContext(context);
//        } else {
//            super.attachBaseContext(newBase);
//        }
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    public void hideKeypadWhenClickingOut(View view) {

        // Set up touch listener for non-text box views endDate hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                hideSoftKeyboard(BaseActivity.this);
                return false;
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeypadWhenClickingOut(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null &&
                activity.getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }

    }

}

