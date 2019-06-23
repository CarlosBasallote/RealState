package com.carlosbt.carlosbtrealstate.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.carlosbt.carlosbtrealstate.UtilToken;
import com.carlosbt.carlosbtrealstate.fragment.MyPropertiesFragment;
import com.carlosbt.carlosbtrealstate.fragment.PropertiesFragment;
import com.carlosbt.carlosbtrealstate.fragment.favListFragment;
import com.carlosbt.carlosbtrealstate.listener.PropertiesInteractionListener;
import com.carlosbt.carlosbasalloteteba_realstate.R;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PropertiesInteractionListener {

    MenuItem itemManage, itemManage2, itemManage3, itemManage4;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        itemManage = navigationView.getMenu().findItem(R.id.nav_logout);
        itemManage2 = navigationView.getMenu().findItem(R.id.nav_fav);
        itemManage3 = navigationView.getMenu().findItem(R.id.nav_viviendas);
        itemManage4 = navigationView.getMenu().findItem(R.id.nav_login);
        fab = findViewById(R.id.fabAdd);


        if(UtilToken.getToken(this) == null) {
            itemManage.setVisible(false);
            itemManage2.setVisible(false);
            itemManage3.setVisible(false);
            fab.hide();

        }
        if(UtilToken.getToken(this) != null) {
            itemManage4.setVisible(false);
            fab.show();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new PropertiesFragment())
                .commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goAddProperty();
            }
        });
    }

    private void goAddProperty() {
        startActivity(new Intent(DashboardActivity.this,AddPropertyActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment f = null;

        switch (item.getItemId()) {
                case R.id.nav_inicio:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new PropertiesFragment())
                        .commit();
                    break;


                case R.id.nav_login:
                Intent goLogin = new Intent(this, LoginActivity.class);
                startActivity(goLogin);
                finish();
                break;

                case R.id.nav_logout:
                UtilToken.setIdUser(DashboardActivity.this, null);
                UtilToken.setToken(DashboardActivity.this, null);
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                finish();
                break;

                case R.id.nav_fav:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new favListFragment())
                        .commit();

                break;


                case R.id.nav_viviendas:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new MyPropertiesFragment())
                        .commit();

                break;



        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }  if(UtilToken.getToken(this) == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Quiere cerrar la aplicacion?")
                    .setTitle("Cerrar Aplicación");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea cerrar Sesión?")
                    .setTitle("Cerrar Sesión");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    UtilToken.setIdUser(DashboardActivity.this, null);
                    UtilToken.setToken(DashboardActivity.this, null);
                    startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                    finish();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }
}
