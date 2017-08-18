package com.titanicrun.game;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.snapshot.Snapshot;
import com.titanicrun.game.TitanicClass;

public class AndroidLauncher extends AndroidApplication {
	GPGSImpl gpgs;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		gpgs = new GPGSImpl();
		gpgs.init( this );
		initialize(new TitanicClass(gpgs), config);
	}
	@Override
	public void onStart() {
		super.onStart();
		// Во время старта приложения, подключаемся к GPGS
		// Так рекомендует делать GOOGLE
		gpgs.connect();
	}

	@Override
	public void onStop() {
		super.onStop();
		gpgs.disconnect();
	}

	@Override
	public void onActivityResult( int request, int response, Intent intent ) {
		super.onActivityResult( request, response, intent );
		gpgs.onActivityResult( request, response, intent );
	}

}
