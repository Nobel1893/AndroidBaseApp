package bluecrunch.base;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by karim on 5/11/18.
 */

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel>
        extends Fragment {

    private BaseActivity mActivity;
    private View mRootView;
    private T mViewDataBinding;
    private V mViewModel;
    private ProgressDialog progressDialog;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        setHasOptionsMenu(false);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container,
                false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }



    public AlertDialog showPopUp(@StringRes int messageId,
                                 final @StringRes int posActionName,
                                 final DialogInterface.OnClickListener positiveAction,
                                 final @StringRes int negAction,
                                 boolean isCancelable){
        return mActivity.showPopUp(messageId,posActionName,positiveAction,negAction,isCancelable);
    }
    public AlertDialog showPopUp(@StringRes int messageId,
                                 final @StringRes int posAction,
                                 final DialogInterface.OnClickListener positiveAction,
                                 final @StringRes int negActioname,
                                 final DialogInterface.OnClickListener negAction,
                                 boolean isCancelable){
        return showPopUp(messageId,posAction,positiveAction,negActioname,negAction,isCancelable);
    }
    public AlertDialog showPopUp(@StringRes int messageId,
                                 final String posActionName,
                                 final DialogInterface.OnClickListener positiveAction,
                                 final String negActionName,
                                 final DialogInterface.OnClickListener negAction,
                                 boolean isCancelable){
        return mActivity.showPopUp(messageId,posActionName,positiveAction,negActionName,negAction,isCancelable);
    }
    public AlertDialog showPopUp(@StringRes int messageId,
                                 final @StringRes int posAction,
                                 final DialogInterface.OnClickListener positiveAction,
                                 boolean isCancelable){
        return mActivity.showPopUp(messageId,posAction,positiveAction,isCancelable);
    }
    public AlertDialog showPopUp(@StringRes int messageId,
                                 final @StringRes int posAction,
                                 boolean isCancelable){
        return mActivity.showPopUp(messageId,posAction,isCancelable);
    }

    public AlertDialog showPopUp(String title,String message,
                                 final  int posAction,
                                 final DialogInterface.OnClickListener positiveAction,
                                 final String negAction,
                                 boolean isCancelable){
       return mActivity.showPopUp(title,message,posAction,positiveAction,negAction,isCancelable) ;
    }
    public AlertDialog showPopUp( String title,
                                  String message,
                                  final  String posAction,
                                  boolean isCancelable){
        return mActivity.showPopUp(title,message,posAction,isCancelable);
    }
    public AlertDialog showPopUp(String message,
                                 final @StringRes int posAction,
                                 final DialogInterface.OnClickListener positiveAction,
                                 boolean isCancelable){
        return mActivity.showPopUp(message,posAction,positiveAction,isCancelable);
    }
    public AlertDialog showPopUp( String message,
                                  final @StringRes int posAction,
                                  boolean isCancelable){
        return mActivity.showPopUp(message,posAction,isCancelable);
    }
    public interface Callback {

        void onFragmentAttached();

        void onFragmentDetached(String tag);

    }

}
