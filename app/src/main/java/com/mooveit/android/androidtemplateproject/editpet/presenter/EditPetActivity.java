
package com.mooveit.android.androidtemplateproject.editpet.presenter;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.common.AndroidTemplateApplication;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.ui.activity.BaseActivity;
import com.mooveit.android.androidtemplateproject.editpet.di.DaggerEditPetComponent;
import com.mooveit.android.androidtemplateproject.editpet.di.EditPetComponent;
import com.mooveit.android.androidtemplateproject.editpet.di.EditPetModule;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditPetActivity extends BaseActivity implements EditPetView {

    public static final String ARG_PET_EXTRAS = ":Extras:Pet:Parcelable";

    private EditPetComponent mEditPetComponent;
    private Pet mPet;
    private Snackbar mSnackbar;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.name_text_input_layout)
    TextInputLayout mPetNameTIL;
    @BindView(R.id.name_edit_text)
    TextInputEditText mPetNameET;
    @BindString(R.string.empty_name_error)
    String mEmptyNameErrorMessage;

    @Inject
    EditPetViewModel mEditPetViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);
        ButterKnife.bind(this);

        mEditPetComponent = DaggerEditPetComponent.builder()
                .applicationComponent(AndroidTemplateApplication.getInstance(this)
                        .getApplicationComponent())
                .editPetModule(new EditPetModule(this))
                .build();

        mEditPetComponent.inject(this);

        setupToolbar();
        retrieveExtras();

        mPetNameET.setText(mPet.getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEditPetViewModel.onViewDetached();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void retrieveExtras() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mPet = args.getParcelable(ARG_PET_EXTRAS);
        }
    }

    @OnClick(R.id.submit_edit_fab)
    void onEditPet() {
        String newName = mPetNameET.getText().toString();
        if (newName.isEmpty()) {
            mPetNameTIL.setError(mEmptyNameErrorMessage);
        } else {
            mPet.setName(newName);
            mEditPetViewModel.editPet(mPet);
        }
    }


    @Override
    public void hideProgress() {
        if (mSnackbar != null && mSnackbar.isShownOrQueued()) {
            mSnackbar.dismiss();
        }
    }

    @Override
    public void showProgress() {
        mSnackbar = Snackbar.make(mPetNameET, R.string.sending, Snackbar.LENGTH_INDEFINITE);
        mSnackbar.show();
    }

    @Override
    public void showSuccess() {
        mSnackbar = Snackbar.make(mPetNameET, R.string.pet_edit_success, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

    @Override
    public void showError(String message) {
        mSnackbar = Snackbar.make(mPetNameET, message, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }
}