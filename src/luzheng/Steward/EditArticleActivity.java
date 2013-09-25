package luzheng.Steward;

import java.io.ByteArrayOutputStream;

import luzheng.Steward.ArticlesContract.ArticlesDbHelper;
import luzheng.Steward.ArticlesContract.ArticlesEntry;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class EditArticleActivity extends Activity {

	private ImageView imageView;
	private Bitmap picture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_article);
		
		// set the image view
		imageView = (ImageView) findViewById(R.id.imageView);
		picture = (Bitmap)(getIntent().getExtras().get("data"));
		imageView.setImageBitmap(picture);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	/** Called when the user clicks the Add button */
	public void addArticle(View view) {
		// get the information about this article
		EditText articleTitle = (EditText) findViewById(R.id.edit_title);
		EditText articlePrice = (EditText) findViewById(R.id.edit_price);
		EditText articleDescription = (EditText) findViewById(R.id.edit_description);
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		
		// add information about this article into database		
		ArticlesDbHelper dbHelper = new ArticlesContract().new ArticlesDbHelper(getBaseContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase(); // Gets the data repository in write mode
		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(ArticlesEntry.COLUMN_NAME_TITLE, articleTitle.getText().toString());
		values.put(ArticlesEntry.COLUMN_NAME_PRICE, articlePrice.getText().toString());
		values.put(ArticlesEntry.COLUMN_NAME_DESCRIPTION, articleDescription.getText().toString());
		values.put(ArticlesEntry.COLOUM_NAME_PHOTO, byteArray);
		// Insert the new row, returning the primary key value of the new row
		db.insert(ArticlesEntry.TABLE_NAME, null, values);
		
		// Return to the main panel
	    Intent intent = new Intent(this, MainActivity.class);
	    startActivity(intent);
	} 
}
