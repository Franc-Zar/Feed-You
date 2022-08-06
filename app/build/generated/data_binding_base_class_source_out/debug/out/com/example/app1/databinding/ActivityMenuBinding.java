// Generated by view binder compiler. Do not edit!
package com.example.app1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ActivityMenuBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView aboutFeedYou;

  @NonNull
  public final TextView accountSettings;

  @NonNull
  public final TextView btnPref;

  @NonNull
  public final TextView btnSingleFeed;

  @NonNull
  public final TextView changePassword;

  @NonNull
  public final TextView connectSocials;

  @NonNull
  public final TextView decorationPasswordReset;

  @NonNull
  public final TextView logout;

  @NonNull
  public final TextView reportProblems;

  @NonNull
  public final TextView themes;

  @NonNull
  public final MaterialToolbar toolbarAccount;

  private ActivityMenuBinding(@NonNull ConstraintLayout rootView, @NonNull TextView aboutFeedYou,
      @NonNull TextView accountSettings, @NonNull TextView btnPref, @NonNull TextView btnSingleFeed,
      @NonNull TextView changePassword, @NonNull TextView connectSocials,
      @NonNull TextView decorationPasswordReset, @NonNull TextView logout,
      @NonNull TextView reportProblems, @NonNull TextView themes,
      @NonNull MaterialToolbar toolbarAccount) {
    this.rootView = rootView;
    this.aboutFeedYou = aboutFeedYou;
    this.accountSettings = accountSettings;
    this.btnPref = btnPref;
    this.btnSingleFeed = btnSingleFeed;
    this.changePassword = changePassword;
    this.connectSocials = connectSocials;
    this.decorationPasswordReset = decorationPasswordReset;
    this.logout = logout;
    this.reportProblems = reportProblems;
    this.themes = themes;
    this.toolbarAccount = toolbarAccount;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMenuBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMenuBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_menu, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMenuBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.about_feed_you;
      TextView aboutFeedYou = ViewBindings.findChildViewById(rootView, id);
      if (aboutFeedYou == null) {
        break missingId;
      }

      id = R.id.account_settings;
      TextView accountSettings = ViewBindings.findChildViewById(rootView, id);
      if (accountSettings == null) {
        break missingId;
      }

      id = R.id.btn_pref;
      TextView btnPref = ViewBindings.findChildViewById(rootView, id);
      if (btnPref == null) {
        break missingId;
      }

      id = R.id.btn_singleFeed;
      TextView btnSingleFeed = ViewBindings.findChildViewById(rootView, id);
      if (btnSingleFeed == null) {
        break missingId;
      }

      id = R.id.change_password;
      TextView changePassword = ViewBindings.findChildViewById(rootView, id);
      if (changePassword == null) {
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

      id = R.id.logout;
      TextView logout = ViewBindings.findChildViewById(rootView, id);
      if (logout == null) {
        break missingId;
      }

      id = R.id.report_problems;
      TextView reportProblems = ViewBindings.findChildViewById(rootView, id);
      if (reportProblems == null) {
        break missingId;
      }

      id = R.id.themes;
      TextView themes = ViewBindings.findChildViewById(rootView, id);
      if (themes == null) {
        break missingId;
      }

      id = R.id.toolbar_account;
      MaterialToolbar toolbarAccount = ViewBindings.findChildViewById(rootView, id);
      if (toolbarAccount == null) {
        break missingId;
      }

      return new ActivityMenuBinding((ConstraintLayout) rootView, aboutFeedYou, accountSettings,
          btnPref, btnSingleFeed, changePassword, connectSocials, decorationPasswordReset, logout,
          reportProblems, themes, toolbarAccount);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
