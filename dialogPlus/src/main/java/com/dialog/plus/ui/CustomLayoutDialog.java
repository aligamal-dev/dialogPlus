package com.dialog.plus.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.dialog.plus.R;
import com.dialog.plus.databinding.CustomLayoutDialogBinding;

import carbon.BR;

/**
 * Created by fawzy on 04,September,2019
 * ma7madfawzy@gmail.com
 **/
public class CustomLayoutDialog extends BaseDialogFragment<CustomLayoutDialogBinding> {
    private DialogPlusUiModel model;
    @LayoutRes
    private int customLayoutRes;
    private int variableId;
    private Object variableValue;
    private View customView;
    private ViewGroup customViewParent;

    CustomLayoutDialog(DialogPlusUiModel uiModel, @LayoutRes int customLayoutRes, int variableId, Object variableValue) {
        this.model = uiModel;
        initVariables(customLayoutRes, variableId, variableValue);
    }

    CustomLayoutDialog(DialogPlusUiModel uiModel, View view) {
        this.model = uiModel;
        this.customView = view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBindingVariables();
        setListeners();
        setHeader();
        if (customView != null)
            addCustomView();
    }

    private void setHeader() {
        if (model.getTitle() == null)
            model.setTitle("").setHeaderBgColor(R.color.dialogTransparent);
    }

    private void addCustomView() {
        customViewParent = (ViewGroup) customView.getParent();
        if (customViewParent != null)
            customViewParent.removeView(customView);
        binding.container.addView(customView);
    }

    private void setListeners() {
        binding.headerLayout.closeIV.setOnClickListener(v -> onCloseClicked());
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (customView != null)
            moveViewBackToHisParent();
        dismiss(true);
    }

    private void moveViewBackToHisParent() {
        binding.container.removeView(customView);
        if (customViewParent != null) {
            customViewParent.addView(customView);
        }
    }

    private void initBindingVariables() {
        binding.setCustomLayoutId(customLayoutRes);
        binding.setVariableId(variableId);
        binding.setVariableValue(variableValue);
    }

    @Override
    protected Object getVariableValue() {
        return model;
    }

    /**
     * returns an instance of the inflated layout's viewDataBinding
     * just in case it was a binding layout(included in the tag <layout><layout/>)
     */
    public ViewDataBinding getCustomLayoutBinding() {
        if (binding.container.getTag() == null || !(binding.container.getTag() instanceof ViewDataBinding))
            return null;
        return (ViewDataBinding) binding.container.getTag();
    }

    /**
     * returns an instance of the inflated layout's viewDataBinding
     * just in case it was a binding layout(included in the tag <layout><layout/>)
     */
    public View getCustomLayoutView() {
        if (binding.container.getTag() != null)
            if (binding.container.getTag() instanceof View)
                return (View) binding.container.getTag();
            else if (binding.container.getTag() instanceof ViewDataBinding)
                return getCustomLayoutBinding().getRoot();
        return null;
    }

    private void onCloseClicked() {
        dismiss(true);
    }

    @Override
    public int getBindingVariable() {
        return BR.model;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.custom_layout_dialog;
    }

    private void initVariables(int customLayoutRes, int variableId, Object variableValue) {
        this.customLayoutRes = customLayoutRes;
        this.variableId = variableId;
        this.variableValue = variableValue;
    }
}