package luzheng.Steward;

import luzheng.Steward.ArticlesContract.ArticlesDbHelper;
import luzheng.Steward.ArticlesContract.ArticlesEntry;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final int ACTION_TAKE_PHOTO = 1;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// set the list view
		String[] articles = getArticles();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.title, articles);
		listView = (ListView) findViewById(R.id.list_articles);
		listView.setAdapter(adapter);
		
		TextView tv = (TextView) findViewById(R.id.count);
		tv.setText(String.valueOf(articles.length));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	    case R.id.action_photo:
	        takePhotos();
	        return true;
//	    case R.id.action_settings:
//	        openSettings();
//	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	// get all the articles from the database
	private String[] getArticles() {
		// add information about this article into database		
		ArticlesDbHelper dbHelper = new ArticlesContract().new ArticlesDbHelper(getBaseContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase(); // Gets the data repository in read mode
		Cursor c = db.query(ArticlesEntry.TABLE_NAME, new String[]{ArticlesEntry.COLUMN_NAME_TITLE}, null, null, null, null, null);
		String[] result = new String[c.getCount()];	
		c.moveToFirst();
		for(int i = 0; i < result.length; i ++) {
			result[i] = c.getString(c.getColumnIndexOrThrow(ArticlesEntry.COLUMN_NAME_TITLE));
			c.moveToNext();
		}
		return result;
	}
	
	/** call camera app */
	private void takePhotos() {
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    startActivityForResult(takePictureIntent, ACTION_TAKE_PHOTO);
	}
	
	/** when camera app ends, the edit activity should be called */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACTION_TAKE_PHOTO: {
			if (resultCode == RESULT_OK)
				editArticle(data);
			break;
		}
		}
	}

	/** start the edit article activity */
	private void editArticle(Intent data) {
	    Intent intent = new Intent(this, EditArticleActivity.class);
	    intent.putExtras(data);
	    startActivity(intent);
	}
}
