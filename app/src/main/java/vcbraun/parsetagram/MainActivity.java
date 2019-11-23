package vcbraun.parsetagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

import vcbraun.parsetagram.fragments.ComposeFragment;
import vcbraun.parsetagram.fragments.PostsFragment;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    final Fragment composeFragment = new ComposeFragment();
    final Fragment postsFragment = new PostsFragment();
    final Fragment profileFragment = new ProfileFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        //Toast.makeText(MainActivity.this,"Home!", Toast.LENGTH_SHORT).show();
                        fragment = postsFragment;
                        break;
                    case R.id.action_compose:
                        //Toast.makeText(MainActivity.this,"Compose!", Toast.LENGTH_SHORT).show();
                        fragment = composeFragment;
                        break;
                    case R.id.action_profile:
                        //Toast.makeText(MainActivity.this,"Profile!", Toast.LENGTH_SHORT).show();
                        fragment = profileFragment;
                        break;
                    case R.id.action_signout:
                        Toast.makeText(MainActivity.this,"Signing out!", Toast.LENGTH_SHORT).show();
                        fragment = postsFragment;
                        ParseUser.logOut();
                        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                        goLoginActivity();
                        break;
                    default:
                        fragment = composeFragment;
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }


    private void goLoginActivity() {
        Log.d(TAG, "going to login activity");
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
