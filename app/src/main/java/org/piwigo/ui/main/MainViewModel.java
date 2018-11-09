/*
 * Piwigo for Android
 * Copyright (C) 2016-2018 Piwigo Team http://piwigo.org
 * Copyright (C) 2018-2018 Raphael Mack http://www.raphael-mack.de
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.piwigo.ui.main;

import android.accounts.Account;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import org.piwigo.R;
import org.piwigo.accounts.UserManager;

public class MainViewModel extends ViewModel {

    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> username = new ObservableField<>();
    public ObservableField<String> url = new ObservableField<>();
    public ObservableBoolean drawerState = new ObservableBoolean(false);
    public ObservableInt navigationItemId = new ObservableInt(R.id.nav_albums);

    private MutableLiveData<Integer> selectedNavigationItemId = new MutableLiveData<>();

    MainViewModel(UserManager userManager) {
        Account account = userManager.getActiveAccount().getValue();
        if (account != null) {
            username.set(userManager.getUsername(account));
            url.set(userManager.getSiteUrl(account));
        }

        navigationItemId.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {

            @Override public void onPropertyChanged(Observable sender, int propertyId) {
                selectedNavigationItemId.setValue(navigationItemId.get());
                drawerState.set(false);
            }
        });
    }

    LiveData<Integer> getSelectedNavigationItemId() {
        return selectedNavigationItemId;
    }

    public void navigationIconClick() {
        // TODO: show an "up arrow" and navigate back to the upper album if we are not in root
        drawerState.set(true);
    }
}
