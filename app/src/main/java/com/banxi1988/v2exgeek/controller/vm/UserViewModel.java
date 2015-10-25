package com.banxi1988.v2exgeek.controller.vm;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.banxi1988.v2exgeek.BR;
import com.banxi1988.v2exgeek.controller.helper.Validators;
import com.banxi1988.v2exgeek.databinding.ActivityLoginBinding;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func2;

/**
 * Created by banxi on 15/10/24.
 */
public class UserViewModel extends BaseObservable {
    private String email;
    private String password;

    private final ActivityLoginBinding mBinding;
    public UserViewModel(ActivityLoginBinding binding){
        this.mBinding = binding;
        Observable<Boolean> loginEnabled =  Observable.combineLatest(RxTextView.textChanges(binding.email),
                RxTextView.textChanges(binding.password),new Func2<CharSequence,CharSequence,Boolean>(){
                    @Override
                    public Boolean call(CharSequence e, CharSequence p) {
                        return Validators.isEmailValid(e) && Validators.isPasswordValid(p);
                    }
                });
        loginEnabled.subscribe(RxView.enabled(binding.emailSignInButton));
    }



    private boolean logining;



    @Bindable
    public String getEmail() {
        return email;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public boolean isLogining() {
        return logining;
    }

    public void setLogining(boolean logining) {
        this.logining = logining;
        notifyPropertyChanged(BR.logining);
    }
}
