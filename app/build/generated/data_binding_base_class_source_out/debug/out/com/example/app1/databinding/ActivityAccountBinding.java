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
  public final TextView accountEmail;

  @NonNull
  public final TextView accountName;

  @NonNull
  public final TextView btnBlock;

  @NonNull
  public final TextView connectSocials;

  @NonNull
  public final TextView decorationPasswordReset;

  @NonNull
  public final TextView deleteAccount;

  @NonNull
  public final TextView emailDecoration;

  @NonNull
  public final Button googleConnect;

  @NonNull
  public final TextView language;

  @NonNull
  public final TextView nameDecoration;

  @NonNull
  public final Spinner spinnerLanguages;

  @NonNull
  public final MaterialToolbar toolbarAccount;

  @NonNull
  public final TextView topics;

  @NonNull
  public final Button twitterConnect;

  private ActivityAccountBinding(@NonNull ConstraintLayout rootView, @NonNull TextView accountEmail,
      @NonNull TextView accountName, @NonNull TextView btnBlock, @NonNull TextView connectSocials,
      @NonNull TextView decorationPasswordReset, @NonNull TextView deleteAccount,
      @NonNull TextView emailDecoration, @NonNull Button googleConnect, @NonNull TextView language,
      @NonNull TextView nameDecoration, @NonNull Spinner spinnerLanguages,
      @NonNull MaterialToolbar toolbarAccount, @NonNull TextView topics,
      @NonNull Button twitterConnect) {
    this.rootView = rootView;
    this.accountEmail = accountEmail;
    this.accountName = accountName;
    this.btnBlock = btnBlock;
    this.connectSocials = connectSocials;
    this.decorationPasswordReset = decorationPasswordReset;
    this.deleteAccount = deleteAccount;
    this.emailDecoration = emailDecoration;
    this.googleConnect = googleConnect;
    this.language = language;
    this.nameDecoration = nameDecoration;
    this.spinnerLanguages = spinnerLanguages;
    this.toolbarAccount = toolbarAccount;
    this.topics = topics;
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
      id = R.id.account_email;
      TextView accountEmail = ViewBindings.findChildViewById(rootView, id);
      if (accountEmail == null) {
        break missingId;
      }

      id = R.id.account_name;
      TextView accountName = ViewBindings.findChildViewById(rootView, id);
      if (accountName == null) {
        break missingId;
      }

      id = R.id.btn_block;
      TextView btnBlock = ViewBindings.findChildViewById(rootView, id);
      if (btnBlock == null) {
        break missingId;
      }

      id = R.id.connect_socials;
      TextView connectSocials = ViewBindings.findChildViewById(rootView, id);
      if (connectSocials == null) {
        break missingId;
      }

      id = R.id.decoration_password_reset;
      TextView decorationPasswordReset = ViewBindings.findChildViewById(rootView, id);
      if (decorationPasswordReset == null) {
        break missingId;
      }

      id = R.id.delete_account;
      TextView deleteAccount = ViewBindings.findChildViewById(rootView, id);
      if (deleteAccount == null) {
        break missingId;
      }

      id = R.id.email_decoration;
      TextView emailDecoration = ViewBindings.findChildViewById(rootView, id);
      if (emailDecoration == null) {
        break missingId;
      }

      id = R.id.google_connect;
      Button googleConnect = ViewBindings.findChildViewById(rootView, id);
      if (googleConnect == null) {
        break missingId;
      }

      id = R.id.language;
      TextView language = ViewBindings.findChildViewById(rootView, id);
      if (language == null) {
        break missingId;
      }

      id = R.id.name_decoration;
      TextView nameDecoration = ViewBindings.findChildViewById(rootView, id);
      if (nameDecoration == null) {
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

      id = R.id.topics;
      TextView topics = ViewBindings.findChildViewById(rootView, id);
      if (topics == null) {
        break missingId;
      }

      id = R.id.twitter_connect;
      Button twitterConnect = ViewBindings.findChildViewById(rootView, id);
      if (twitterConnect == null) {
        break missingId;
      }

      return new ActivityAccountBinding((ConstraintLayout) rootView, accountEmail, accountName,
          btnBlock, connectSocials, decorationPasswordReset, deleteAccount, emailDecoration,
          googleConnect, language, nameDecoration, spinnerLanguages, toolbarAccount, topics,
          twitterConnect);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
