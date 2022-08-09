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
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.app1.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatImageButton anonymousLogin;

  @NonNull
  public final TextView appName;

  @NonNull
  public final TextView decoration;

  @NonNull
  public final TextView decorationText;

  @NonNull
  public final TextView decorationUseSocial;

  @NonNull
  public final EditText email;

  @NonNull
  public final TextView forgotPassword;

  @NonNull
  public final Button googleConnect;

  @NonNull
  public final ImageView logo;

  @NonNull
  public final EditText password;

  @NonNull
  public final Button signIn;

  @NonNull
  public final TextView signUp;

  @NonNull
  public final Button twitterConnect;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatImageButton anonymousLogin, @NonNull TextView appName,
      @NonNull TextView decoration, @NonNull TextView decorationText,
      @NonNull TextView decorationUseSocial, @NonNull EditText email,
      @NonNull TextView forgotPassword, @NonNull Button googleConnect, @NonNull ImageView logo,
      @NonNull EditText password, @NonNull Button signIn, @NonNull TextView signUp,
      @NonNull Button twitterConnect) {
    this.rootView = rootView;
    this.anonymousLogin = anonymousLogin;
    this.appName = appName;
    this.decoration = decoration;
    this.decorationText = decorationText;
    this.decorationUseSocial = decorationUseSocial;
    this.email = email;
    this.forgotPassword = forgotPassword;
    this.googleConnect = googleConnect;
    this.logo = logo;
    this.password = password;
    this.signIn = signIn;
    this.signUp = signUp;
    this.twitterConnect = twitterConnect;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.anonymous_login;
      AppCompatImageButton anonymousLogin = ViewBindings.findChildViewById(rootView, id);
      if (anonymousLogin == null) {
        break missingId;
      }

      id = R.id.app_name;
      TextView appName = ViewBindings.findChildViewById(rootView, id);
      if (appName == null) {
        break missingId;
      }

      id = R.id.decoration;
      TextView decoration = ViewBindings.findChildViewById(rootView, id);
      if (decoration == null) {
        break missingId;
      }

      id = R.id.decoration_text;
      TextView decorationText = ViewBindings.findChildViewById(rootView, id);
      if (decorationText == null) {
        break missingId;
      }

      id = R.id.decoration_useSocial;
      TextView decorationUseSocial = ViewBindings.findChildViewById(rootView, id);
      if (decorationUseSocial == null) {
        break missingId;
      }

      id = R.id.email;
      EditText email = ViewBindings.findChildViewById(rootView, id);
      if (email == null) {
        break missingId;
      }

      id = R.id.forgot_password;
      TextView forgotPassword = ViewBindings.findChildViewById(rootView, id);
      if (forgotPassword == null) {
        break missingId;
      }

      id = R.id.google_connect;
      Button googleConnect = ViewBindings.findChildViewById(rootView, id);
      if (googleConnect == null) {
        break missingId;
      }

      id = R.id.logo;
      ImageView logo = ViewBindings.findChildViewById(rootView, id);
      if (logo == null) {
        break missingId;
      }

      id = R.id.password;
      EditText password = ViewBindings.findChildViewById(rootView, id);
      if (password == null) {
        break missingId;
      }

      id = R.id.sign_in;
      Button signIn = ViewBindings.findChildViewById(rootView, id);
      if (signIn == null) {
        break missingId;
      }

      id = R.id.sign_up;
      TextView signUp = ViewBindings.findChildViewById(rootView, id);
      if (signUp == null) {
        break missingId;
      }

      id = R.id.twitter_connect;
      Button twitterConnect = ViewBindings.findChildViewById(rootView, id);
      if (twitterConnect == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, anonymousLogin, appName,
          decoration, decorationText, decorationUseSocial, email, forgotPassword, googleConnect,
          logo, password, signIn, signUp, twitterConnect);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
