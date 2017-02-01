package com.mooveit.android.androidtemplateproject.petdetails.di;

import com.mooveit.android.androidtemplateproject.common.di.components.ApplicationComponent;
import com.mooveit.android.androidtemplateproject.common.di.scopes.PerActivity;
import com.mooveit.android.androidtemplateproject.home.presenter.HomeActivity;
import com.mooveit.android.androidtemplateproject.petdetails.presenter.PetDetailsActivity;

import dagger.Component;

@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = { PetDetailsModule.class }
)
public interface PetDetailsComponent {

    void inject(PetDetailsActivity petDetailsActivity);
}
