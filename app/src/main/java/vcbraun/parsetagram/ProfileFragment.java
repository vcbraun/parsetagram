package vcbraun.parsetagram;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import vcbraun.parsetagram.fragments.PostsFragment;

public class ProfileFragment extends PostsFragment {
    @Override
    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_AUTHOR);
        postQuery.setLimit(20);
        postQuery.whereEqualTo(Post.KEY_AUTHOR, ParseUser.getCurrentUser());
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "problem querying post");
                    e.printStackTrace();
                    return;
                }
                adapter.clear();
                adapter.addAll(posts);
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
                for(int i = 0 ; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.d(TAG, "Post: " + post.getDescription() + " by " + post.getAuthor().getUsername());
                }
            }
        });
    }
}
