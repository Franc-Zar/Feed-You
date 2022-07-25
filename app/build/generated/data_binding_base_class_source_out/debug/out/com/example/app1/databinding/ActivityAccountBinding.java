// Generated by view binder compiler. Do not edit!
package com.example.app1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.app1.R;
import com.google.android.material.appbar.MaterialToolbar;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAccountBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView currentEmail;

  @NonNull
  public final TextView decorationPasswordReset;

  @NonNull
  public final Button googleConnect;

  @NonNull
  public final TextView inviteFriends;

  @NonNull
  public final TextView language;

  @NonNull
  public final TextView logout;

  @NonNull
  public final TextView passwordReset;

  @NonNull
  public final Spinner spinnerLanguages;

  @NonNull
  public final MaterialToolbar toolbarAccount;

  @NonNull
  public final Button twitterConnect;

  private ActivityAccountBinding(@NonNull ConstraintLayout rootView, @NonNull TextView currentEmail,
      @NonNull TextView decorationPasswordReset, @NonNull Button googleConnect,
      @NonNull TextView inviteFriends, @NonNull TextView language, @NonNull TextView logout,
      @NonNull TextView passwordReset, @NonNull Spinner spinnerLanguages,
      @NonNull MaterialToolbar toolbarAccount, @NonNull Button twitterConnect) {
    this.rootView = rootView;
    this.currentEmail = currentEmail;
    this.decorationPasswordReset = decorationPasswordReset;
    this.googleConnect = googleConnect;
    this.inviteFriends = inviteFriends;
    this.language = language;
    this.logout = logout;
    this.passwordReset = passwordReset;
    this.spinnerLanguages = spinnerLanguages;
    this.toolbarAccount = toolbarAccount;
    this.twitterConnect = twitterConnect;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAccountBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAccountBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_account, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAccountBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.current_email;
      TextView currentEmail = ViewBindings.findChildViewById(rootView, id);
      if (currentEmail == null) {
        break missingId;
      }

      id = R.id.decoration_password_reset;
      TextView decorationPasswordReset = ViewBindings.findChildViewById(rootView, id);
      if (decorationPasswordReset == null) {
        break missingId;
      }

      id = R.id.google_connect;
      Button googleConnect = ViewBindings.findChildViewById(rootView, id);
      if (googleConnect == null) {
        break missingId;
      }

      id = R.id.invite_friends;
      TextView inviteFriends = ViewBindings.findChildViewById(rootView, id);
      if (inviteFriends == null) {
        break missingId;
      }

      id = R.id.language;
      TextView language = ViewBindings.findChildViewById(rootView, id);
      if (language == null) {
        break missingId;
      }

      id = R.id.logout;
      TextView logout = ViewBindings.findChildViewById(rootView, id);
      if (logout == null) {
        break missingId;
      }

      id = R.id.password_reset;
      TextView passwordReset = ViewBindings.findChildViewById(rootView, id);
      if (passwordReset == null) {
        break missingId;
      }

      id = R.id.spinner_languages;
      Spinner spinnerLanguages = ViewBindings.findChildViewById(rootView, id);
      if (spinnerLanguages == null) {
        break missingId;
      }

      id = R.id.toolbar_account;
      MaterialToolbar toolbarAccount = ViewBindings.findChildViewById(rootView, id);
      if (toolbarAccount == null) {
        break missingId;
      }

      id = R.id.twitter_connect;
      Button twitterConnect = ViewBindings.findChildViewById(rootView, id);
      if (twitterConnect == null) {
        break missingId;
      }

      return new ActivityAccountBinding((ConstraintLayout) rootView, currentEmail,
          decorationPasswordReset, googleConnect, inviteFriends, language, logout, passwordReset,
          spinnerLanguages, toolbarAccount, twitterConnect);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
