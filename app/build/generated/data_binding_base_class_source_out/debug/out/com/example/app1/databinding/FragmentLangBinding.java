// Generated by view binder compiler. Do not edit!
package com.example.app1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.app1.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentLangBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final Spinner spinnerLang;

  @NonNull
  public final TextView tvFragTitle;

  private FragmentLangBinding(@NonNull FrameLayout rootView, @NonNull Spinner spinnerLang,
      @NonNull TextView tvFragTitle) {
    this.rootView = rootView;
    this.spinnerLang = spinnerLang;
    this.tvFragTitle = tvFragTitle;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentLangBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentLangBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_lang, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentLangBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.spinner_lang;
      Spinner spinnerLang = ViewBindings.findChildViewById(rootView, id);
      if (spinnerLang == null) {
        break missingId;
      }

      id = R.id.tv_frag_title;
      TextView tvFragTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvFragTitle == null) {
        break missingId;
      }

      return new FragmentLangBinding((FrameLayout) rootView, spinnerLang, tvFragTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
