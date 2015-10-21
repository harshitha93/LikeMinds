package com.ground0.likeminds.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ground0.likeminds.R;

/**
 * Created by Arjun on 26-08-2015.
 */
public class CreatePostFragment extends Fragment {

    private static final int RESULT_LOAD_IMAGES = 100;
    View rootView;
    Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplication();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Point screenSize = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(screenSize);


    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_post,container,false);
        final ViewGroup containerFinal = container;

        //Manage on Touch events
        View view = rootView.findViewById(R.id.add_image);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGES);
            }
        });


        Log.i("com.ground0.likeminds",
                "Toolbar Id : " +
                        ((ViewGroup) container.getParent()).findViewById(R.id.activity_post_toolbar_container)
        );


        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if((v.getId() == R.id.add_recipient_edittext )&& hasFocus ){
                    ( (ViewGroup) containerFinal.getParent())
                            .findViewById(R.id.activity_post_toolbar_container)
                            .setVisibility(View.GONE);
                }
                else {
                    ((ViewGroup) containerFinal.getParent())
                            .findViewById(R.id.activity_post_toolbar_container)
                            .setVisibility(View.VISIBLE);
                }
            }
        };

        EditText editText = (EditText)rootView.findViewById(R.id.add_recipient_edittext);
        editText.setOnFocusChangeListener(focusChangeListener);
        (rootView.findViewById(R.id.create_post_content)).setOnFocusChangeListener(focusChangeListener);


        rootView.findViewById(R.id.action_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Event Posted", Toast.LENGTH_SHORT).show();
            }
        });


        String[] memberList = {"Arjun Biju","Shashank Sahay","Vikram Thomas"};
        final AutoCompleteTextView textView = ((AutoCompleteTextView)rootView.findViewById(R.id.add_recipient_edittext));
        textView.setAdapter(new ArrayAdapter<String>(context, R.layout.simple_item_autocomplete, memberList));
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewGroup viewGroup = (ViewGroup)rootView.findViewById(R.id.recepient_list_add);
                View item = inflater.inflate(R.layout.recipient_item,viewGroup,false);
                ((TextView)item.findViewById(R.id.recepient_name))
                        .setText(((TextView) view).getText());
                textView.setText("");

                item.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                viewGroup.addView(item);


                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ViewGroup) v.getParent()).removeView(v);

                    }
                });

                DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
                float dp = 2f;
                float fpixels = metrics.density * dp;
                int pixels = (int) (fpixels + 0.5f);

                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)item.getLayoutParams();
                marginLayoutParams.setMargins(pixels,pixels,pixels,pixels);

            }
        });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("com.ground0.likeminds","startActivityForResult()");
        if(requestCode == RESULT_LOAD_IMAGES && data !=null && resultCode == AppCompatActivity.RESULT_OK)
        {
            Uri imageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(imageUri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

//            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//            Point size = new Point();
//            windowManager.getDefaultDisplay().getSize(size);

            //Scale down the image
            Bitmap image = BitmapFactory.decodeFile(picturePath);
            float height = image.getHeight();
            float width = image.getWidth();

            final float HEIGHT_IMAGE_VIEW = 100;
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            float heightDP = metrics.density * HEIGHT_IMAGE_VIEW;


            width = ( width / height ) * heightDP;
            int finalWidth = (int)(width + 0.5f);
            int finalHeight = (int)(heightDP + 0.5f);

            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight,false);



            ImageView imageView = (ImageView)rootView.findViewById(R.id.attachment);
            if(imageView.getVisibility() == View.GONE) {
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
//                params.width = size.x / 4;
//                params.height = size.y / 4;



                imageView.setImageBitmap(image);
                imageView.setVisibility(View.VISIBLE);
                imageView.setLayoutParams(params);
                imageView.invalidate();
            }else
            {
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                ImageView imgView = new ImageView(context);
                imgView.setImageBitmap(image);
                ViewGroup viewGroup = (ViewGroup)rootView.findViewById(R.id.attachment_parent);
                viewGroup.addView(imgView, params);

            }
        }
    }
}
