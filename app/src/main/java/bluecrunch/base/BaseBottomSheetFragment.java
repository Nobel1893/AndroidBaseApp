package bluecrunch.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by karim on 5/11/18.
 */

public abstract class BaseBottomSheetFragment<T extends ViewDataBinding, V extends BaseViewModel>
        extends BottomSheetDialogFragment {

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




    protected void showProgressDialog(int staringId) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.setMessage(getString(staringId));
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        progressDialog.dismiss();
    }

    protected void showPopUp(int stringId, String positiveActionName, String negativeActionName,
                             boolean isCancelable, final Command positiveCommand,
                             final Command negativeCommand) {

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(isCancelable)
                .setMessage(getString(stringId))
                .setPositiveButton(positiveActionName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        positiveCommand.execute();
                    }
                })
                .setNegativeButton(negativeActionName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        negativeCommand.execute();
                    }
                }).show();
    }

    protected void showPopUp(String message, String positiveActionName, String negativeActionName,
                             boolean isCancelable, final Command positiveCommand,
                             final Command negativeCommand) {

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(isCancelable)
                .setMessage(message)
                .setPositiveButton(positiveActionName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        positiveCommand.execute();
                    }
                })
                .setNegativeButton(negativeActionName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        negativeCommand.execute();
                    }
                }).show();

    }

    protected void showPopUp(int stringId, String positiveActionName,
                             boolean isCancelable, final Command positiveCommand) {

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(isCancelable)
                .setMessage(getString(stringId))
                .setPositiveButton(positiveActionName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        positiveCommand.execute();
                    }
                }).show();
    }

    protected void showPopUp(String string, String positiveActionName,
                             boolean isCancelable, final Command positiveCommand) {

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(isCancelable)
                .setMessage(string)
                .setPositiveButton(positiveActionName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        positiveCommand.execute();
                    }
                }).show();
    }

    protected void showPopUp(String string, String positiveActionName,
                             boolean isCancelable) {

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(isCancelable)
                .setMessage(string)
                .setPositiveButton(positiveActionName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

//    public void showCustomPopup(String title, List<?> items, int selectedPosition,
//                                Action1<Integer> callback) {
//
//        CustomPopupAdapter adapter = new CustomPopupAdapter(getContext(), items,
//                selectedPosition);
//        LayoutInflater inflater = (LayoutInflater) getActivity()
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View headerView = inflater.inflate(R.layout.bottom_sheet_header,
//                null, true);
//        DialogPlus dialogPlus = DialogPlus.newDialog(getActivity())
//                .setAdapter(adapter)
//                .setCancelable(true)
//                .setHeader(headerView)
//                .setOnItemClickListener((dialog, item, view, position) -> {
//                    callback.call(position);
//                    dialog.dismiss();
//                })
//                .setExpanded(true, 800)
//                .create();
//        headerView.findViewById(R.id.close_imageView).setOnClickListener(view ->
//                dialogPlus.dismiss());
//        ((TextView) headerView.findViewById(R.id.title_textView)).setText(title);
//        dialogPlus.show();
//    }

}
