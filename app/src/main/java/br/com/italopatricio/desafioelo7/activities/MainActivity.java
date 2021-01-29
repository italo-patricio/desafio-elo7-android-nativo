package br.com.italopatricio.desafioelo7.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import br.com.italopatricio.desafioelo7.R;
import br.com.italopatricio.desafioelo7.core.ServiceConfig;
import br.com.italopatricio.desafioelo7.fragments.ProductListFragment;

public class MainActivity extends AppCompatActivity {

    private static ServiceConfig serviceConfig;

    public MainActivity() {
        serviceConfig = ServiceConfig.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            ProductListFragment productListFragment = ProductListFragment.newInstance(serviceConfig);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_view, productListFragment, null)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_right, menu);
        return true;
    }

}