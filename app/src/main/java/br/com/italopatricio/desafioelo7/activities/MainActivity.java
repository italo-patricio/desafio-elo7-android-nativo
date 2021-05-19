package br.com.italopatricio.desafioelo7.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import br.com.italopatricio.desafioelo7.R;
import br.com.italopatricio.desafioelo7.fragments.ProductListFragment;
import br.com.italopatricio.desafioelo7.fragments.ProductListViewModel;

public class MainActivity extends AppCompatActivity {

    private ProductListViewModel productListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            ProductListFragment productListFragment = ProductListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_view, productListFragment, null)
                    .commit();
        }

        productListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_right, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            productListViewModel.loadProducts();
        }
        return true;
    }
}