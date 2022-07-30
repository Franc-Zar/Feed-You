// Generated by view binder compiler. Do not edit!
package com.example.app1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.app1.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPasswordRecoveryBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView AboutUsText;

  @NonNull
  public final TextView appName;

  @NonNull
  public final TextView decorationPasswordReset;

  @NonNull
  public final EditText emailDecoration;

  @NonNull
  public final TextView haveAnAccount;

  @NonNull
  public final ImageView logo;

  @NonNull
  public final Button resetPassword;

  @NonNull
  public final TextView signIn;

  private ActivityPasswordRecoveryBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView AboutUsText, @NonNull TextView appName,
      @NonNull TextView decorationPasswordReset, @NonNull EditText emailDecoration,
      @NonNull TextView haveAnAccount, @NonNull ImageView logo, @NonNull Button resetPassword,
      @NonNull TextView signIn) {
    this.rootView = rootView;
    this.AboutUsText = AboutUsText;
    this.appName = appName;
    this.decorationPasswordReset = decorationPasswordReset;
    this.emailDecoration = emailDecoration;
    this.haveAnAccount = haveAnAccount;
    this.logo = logo;
    this.resetPassword = resetPassword;
    this.signIn = signIn;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPasswordRecoveryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPasswordRecoveryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_password_recovery, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPasswordRecoveryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.About_us_text;
      TextView AboutUsText = ViewBindings.findChildViewById(rootView, id);
      if (AboutUsText == null) {
        break missingId;
      }

      id = R.id.app_name;
      TextView appName = ViewBindings.findChildViewById(rootView, id);
      if (appName == null) {
        break missingId;
      }

      id = R.id.decoration_password_reset;
      TextView decorationPasswordReset = ViewBindings.findChildViewById(rootView, id);
      if (decorationPasswordReset == null) {
        break missingId;
      }

      id = R.id.email_decoration;
      EditText emailDecoration = ViewBindings.findChildViewById(rootView, id);
      if (emailDecoration == null) {
        break missingId;
      }

      id = R.id.have_an_account;
      TextView haveAnAccount = ViewBindings.findChildViewById(rootView, id);
      if (haveAnAccount == null) {
        break missingId;
      }

      id = R.id.logo;
      ImageView logo = ViewBindings.findChildViewById(rootView, id);
      if (logo == null) {
        break missingId;
      }

      id = R.id.reset_password;
      Button resetPassword = ViewBindings.findChildViewById(rootView, id);
      if (resetPassword == null) {
        break missingId;
      }

      id = R.id.sign_in;
      TextView signIn = ViewBindings.findChildViewById(rootView, id);
      if (signIn == null) {
        break missingId;
      }

      return new ActivityPasswordRecoveryBinding((ConstraintLayout) rootView, AboutUsText, appName,
          decorationPasswordReset, emailDecoration, haveAnAccount, logo, resetPassword, signIn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
