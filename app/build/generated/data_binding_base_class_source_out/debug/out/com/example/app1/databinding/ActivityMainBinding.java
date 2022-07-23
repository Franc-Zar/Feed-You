// Generated by view binder compiler. Do not edit!
package com.example.app1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.app1.R;
import com.google.android.material.appbar.MaterialToolbar;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final ConstraintLayout layoutApp;

  @NonNull
  public final ProgressBar loadingIcon;

  @NonNull
  public final RecyclerView rv;

  @NonNull
  public final SwipeRefreshLayout swiperefresh;

  @NonNull
  public final MaterialToolbar toolbarMain;

  private ActivityMainBinding(@NonNull CoordinatorLayout rootView,
      @NonNull ConstraintLayout layoutApp, @NonNull ProgressBar loadingIcon,
      @NonNull RecyclerView rv, @NonNull SwipeRefreshLayout swiperefresh,
      @NonNull MaterialToolbar toolbarMain) {
    this.rootView = rootView;
    this.layoutApp = layoutApp;
    this.loadingIcon = loadingIcon;
    this.rv = rv;
    this.swiperefresh = swiperefresh;
    this.toolbarMain = toolbarMain;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.layout_app;
      ConstraintLayout layoutApp = ViewBindings.findChildViewById(rootView, id);
      if (layoutApp == null) {
        break missingId;
      }

      id = R.id.loading_icon;
      ProgressBar loadingIcon = ViewBindings.findChildViewById(rootView, id);
      if (loadingIcon == null) {
        break missingId;
      }

      id = R.id.rv;
      RecyclerView rv = ViewBindings.findChildViewById(rootView, id);
      if (rv == null) {
        break missingId;
      }

      id = R.id.swiperefresh;
      SwipeRefreshLayout swiperefresh = ViewBindings.findChildViewById(rootView, id);
      if (swiperefresh == null) {
        break missingId;
      }

      id = R.id.toolbar_main;
      MaterialToolbar toolbarMain = ViewBindings.findChildViewById(rootView, id);
      if (toolbarMain == null) {
        break missingId;
      }

      return new ActivityMainBinding((CoordinatorLayout) rootView, layoutApp, loadingIcon, rv,
          swiperefresh, toolbarMain);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
