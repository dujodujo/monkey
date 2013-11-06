package si.picture.gallery.selector;

import java.util.LinkedList;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.support.v4.app.NavUtils;

public class PictureSelectActivity extends Activity 
{
	private final int PICKER = 1;
	private Gallery picture_gallery;
	private int current_picture = 0;
	private ImageView picture_view;
	private PictureAdapter image_adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        picture_view = (ImageView)findViewById(R.id.picture);
        picture_gallery = (Gallery)findViewById(R.id.gallery);
        image_adapter = new PictureAdapter(this);
        picture_gallery.setAdapter(image_adapter);
        
        //set long click listener for each gallery thumbnail item.
      	picture_gallery.setOnItemLongClickListener(new OnItemLongClickListener() 
      	{
      	    public boolean onItemLongClick(AdapterView<?> parent, View v,
      	        int position, long id) {
      		    //update the currently selected position
      	        current_picture = position;
      		    //move to the chosen image selection app
      		    Intent intent_new = new Intent();
      		    intent_new.setType("image/*");
      		    intent_new.setAction(Intent.ACTION_GET_CONTENT);
      		    //returned data is worked int onActivityResult();
      		    startActivityForResult(Intent.createChooser(intent_new, "Select Picture"), PICKER);
      		    return true;
      		}
      	});
      		
      	picture_gallery.setOnItemClickListener(new OnItemClickListener() {
      	//handle clicks
      		public void onItemClick(AdapterView<?> parent, View v, 
      			int position, long id) {
      			//set the larger image view to display the chosen calling method of adapter class
      			picture_view.setImageBitmap(image_adapter.get_picture(position));
      		}
      	});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public class PictureAdapter extends BaseAdapter 
    {
    	int default_item_background;                       //Default gallery background image.
		private Context gallery_context;                   //Gallery context.
		private LinkedList<Bitmap> image_bitmaps;          //Array for displayed bitmaps.
		Bitmap place_holder;                               //Place holder for empty spaces in gallery.
    	
		public PictureAdapter(Context con) {
			//initialize context
			gallery_context = con;
			//create bitmap
			image_bitmaps = new LinkedList<Bitmap>();
			//set place holder as all thumbnail images in gallery initially.
			place_holder = BitmapFactory.decodeResource(getResources(),    
					R.drawable.ic_launcher);
			
			for(int i = 0; i < 10; i++) {
				image_bitmaps.add(place_holder);
			}
			
			image_bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.one));
			image_bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.two));
			image_bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.android));
			
			//get styling attributes- use default Android system resources.
			TypedArray style_attributes = gallery_context.obtainStyledAttributes(R.styleable.PicGallery);
			default_item_background = style_attributes.getResourceId
					(R.styleable.PicGallery_android_galleryItemBackground, 0);
			//free memory asap
			style_attributes.recycle();
		}

		public int getCount() {
			return image_bitmaps.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			//create view
			ImageView image_view = new ImageView(gallery_context);
			//specify the bitmap at this position on the array
			image_view.setImageBitmap(image_bitmaps.get(position));
			//set layout options
			image_view.setLayoutParams(new Gallery.LayoutParams(300, 200));
			//scale type within view area
			image_view.setScaleType(image_view.getScaleType().FIT_CENTER);
			//set default gallery item background
			image_view.setBackgroundResource(default_item_background);
			//return the view
			return image_view;
		}
		
		//custom methods for this application.
		void add_picture(Bitmap pictue_new) 
		{
		    image_bitmaps.set(current_picture, pictue_new);
		}
				
		//return bitmap at specified position for larger display.
		public Bitmap get_picture(int position) {
			return image_bitmaps.get(position);
		}
    }
	
    protected void onActivityResult(int request_code, int result_code, Intent data) {
		if(result_code == RESULT_OK) {
			if(request_code == PICKER) {
				//returned picture URI
				Uri picked_uri = data.getData();
				//declare bitmap
				Bitmap picture = null;
			    String image_path = "";
			    //retrieve string using media data.
			    String media_data[] = {MediaStore.Images.Media.DATA};
			    //query data
			    Cursor picture_cursor = android.provider.MediaStore.Images.Media.query
			    		(getContentResolver(), picked_uri, media_data, null, null, null);
			    if(picture_cursor != null) {
			    	//get path to string
			    	int index = picture_cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			    	picture_cursor.moveToFirst();
			    	image_path = picture_cursor.getString(index);
			    } else {
			    	image_path = picked_uri.getPath();
			    }
			    //if new URI attempt to decode image bitmap
			    if(picked_uri != null) {
			        //set width and height we want to use as maximum display
			    	int width = 240;
			    	int height = 240;
			    	//create bitmap options to calculate and use sample size
			    	BitmapFactory.Options bmp_options = new BitmapFactory.Options();
			    	
			    	//first decode image dimensions
			    	bmp_options.inJustDecodeBounds = true;
			    	BitmapFactory.decodeFile(image_path, bmp_options);
			    	
			    	//image width and height before sampling
			    	int current_height = bmp_options.outHeight;
			    	int current_width = bmp_options.outWidth;
			    	
			    	//variable to store new sample size
			    	int sample_size = 1;
			    	
			    	//calculate the sample size if existing size is larger than target area.
			    	if(current_height > height || current_width > width) {
			    		if(current_width > current_height)
			    			sample_size = Math.round((float)current_height / (float)height);
			    		else
			    			sample_size = Math.round((float)current_width / (float)width);
			    	}
			    	//use new sample size
			    	bmp_options.inSampleSize = sample_size;
			    	//decode the bitmap using sample size
			    	bmp_options.inJustDecodeBounds = false;
			    	//get file as a bitmap
			    	picture = BitmapFactory.decodeFile(image_path, bmp_options);
			    	//pass bitmap to ImageAdapter to add to container bitmap_images
			    	image_adapter.add_picture(picture);
			    	//redraw gallery thumbnails to reflect the new addition.
			    	picture_gallery.setAdapter(image_adapter);
			    	//display the new selected image at larger size.
			    	picture_view.setImageBitmap(picture);
			    	//scale
			    	picture_view.setScaleType(ImageView.ScaleType.CENTER);
			    }
		    }
	    }
	    //superclass method
	    super.onActivityResult(request_code, result_code, data);
    }
}
