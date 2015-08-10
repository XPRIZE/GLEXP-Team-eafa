package com.motoli.apps.allsubjects;



import java.util.ArrayList;



import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Activity_LE02 extends Activity_General_Parent  implements
LoaderManager.LoaderCallbacks<Cursor>{
	
	

	private ArrayList<ArrayList<String>> currentWords;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
		setContentView(R.layout.activity_le02);
		appData.addToClassOrder(4);

		resetFontsTypeFace(); 					
		
		((TextView)findViewById(R.id.letter_1)).setText("");
		((TextView)findViewById(R.id.letter_2)).setText("");
		((TextView)findViewById(R.id.letter_3)).setText("");
		((TextView)findViewById(R.id.letter_4)).setText("");
		((TextView)findViewById(R.id.letter_5)).setText("");
		((TextView)findViewById(R.id.letter_6)).setText("");
		((TextView)findViewById(R.id.letter_7)).setText("");
		((TextView)findViewById(R.id.letter_8)).setText("");
		((TextView)findViewById(R.id.letter_9)).setText("");
		((TextView)findViewById(R.id.letter_10)).setText("");
		((TextView)findViewById(R.id.letter_11)).setText("");
		((TextView)findViewById(R.id.letter_12)).setText("");
		((TextView)findViewById(R.id.letter_13)).setText("");
		((TextView)findViewById(R.id.letter_14)).setText("");
		((TextView)findViewById(R.id.letter_15)).setText("");
		((TextView)findViewById(R.id.letter_16)).setText("");
		((TextView)findViewById(R.id.letter_17)).setText("");
		((TextView)findViewById(R.id.letter_18)).setText("");
		((TextView)findViewById(R.id.letter_19)).setText("");
		((TextView)findViewById(R.id.letter_20)).setText("");
		((TextView)findViewById(R.id.letter_21)).setText("");
		((TextView)findViewById(R.id.letter_22)).setText("");
		((TextView)findViewById(R.id.letter_23)).setText("");
		((TextView)findViewById(R.id.letter_24)).setText("");
		((TextView)findViewById(R.id.letter_25)).setText("");
		((TextView)findViewById(R.id.letter_26)).setText("");
		
		findViewById(R.id.letter_layout_1).setTag("");
		findViewById(R.id.letter_layout_2).setTag("");
		findViewById(R.id.letter_layout_3).setTag("");
		findViewById(R.id.letter_layout_4).setTag("");
		findViewById(R.id.letter_layout_5).setTag("");
		findViewById(R.id.letter_layout_6).setTag("");
		findViewById(R.id.letter_layout_7).setTag("");
		findViewById(R.id.letter_layout_8).setTag("");
		findViewById(R.id.letter_layout_9).setTag("");
		findViewById(R.id.letter_layout_10).setTag("");
		findViewById(R.id.letter_layout_11).setTag("");
		findViewById(R.id.letter_layout_12).setTag("");
		findViewById(R.id.letter_layout_13).setTag("");
		findViewById(R.id.letter_layout_14).setTag("");
		findViewById(R.id.letter_layout_15).setTag("");
		findViewById(R.id.letter_layout_16).setTag("");
		findViewById(R.id.letter_layout_17).setTag("");
		findViewById(R.id.letter_layout_18).setTag("");
		findViewById(R.id.letter_layout_19).setTag("");
		findViewById(R.id.letter_layout_20).setTag("");
		findViewById(R.id.letter_layout_21).setTag("");
		findViewById(R.id.letter_layout_22).setTag("");
		findViewById(R.id.letter_layout_23).setTag("");
		findViewById(R.id.letter_layout_24).setTag("");
		findViewById(R.id.letter_layout_25).setTag("");
		findViewById(R.id.letter_layout_26).setTag("");
		

		getLoaderManager().initLoader(MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS, null, this);
		 
			findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					playClickSound();
					moveBackwords();		
				}
			});

	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private void resetFontsTypeFace(){
		((TextView)findViewById(R.id.letter_1)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_2)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_3)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_4)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_5)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_6)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_7)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_8)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_9)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_10)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_11)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_12)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_13)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_14)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_15)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_16)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_17)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_18)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_19)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_20)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_21)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_22)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_23)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_24)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_25)).setTypeface(appData.getCurrentFontType());
		((TextView)findViewById(R.id.letter_26)).setTypeface(appData.getCurrentFontType());
		
		/*
		((TextView)findViewById(R.id.letter_1)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_2)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_3)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_4)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_5)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_6)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_7)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_8)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_9)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_10)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_11)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_12)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_13)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_14)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_15)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_16)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_17)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_18)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_19)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_20)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_21)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_22)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_23)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_24)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_25)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
		((TextView)findViewById(R.id.letter_26)).setTextSize(getResources().getDimension(R.dimen.le01_fontsize));
	*/	
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////

	private Runnable runRestFontTypeFace = new Runnable(){		
		@Override
		public void run(){
			resetFontsTypeFace(); 					
		}
	};
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private void setUpButtonListeners(){
		
		findViewById(R.id.letter_layout_1).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_1).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_1).getTag()));
					resetFontsTypeFace(); 					
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_1)).setTypeface(appData.getCurrentFontTypeBold()); 
					((TextView)findViewById(R.id.letter_1)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
					/*
					TableRow row=((TableRow) findViewById(R.id.letter_row_1));
					TableRow.LayoutParams params = new TableRow.LayoutParams(
		                    TableRow.LayoutParams.MATCH_PARENT,
		                    0, 0.5f);
					row.setLayoutParams(params);
					
					RelativeLayout box=((RelativeLayout) findViewById(R.id.letter_layout_1));
					TableRow.LayoutParams paramsRel=new TableRow.LayoutParams(0,RelativeLayout.LayoutParams.MATCH_PARENT,2f);
					findViewById(R.id.letter_layout_1).setLayoutParams(paramsRel);
					//findViewById(R.id.letter_layout_1).setLayoutParams(paramsRel);
					//box.setLayoutParams(paramsRel);
					
					//TableRow.LayoutParams tblRow = (TableRow.LayoutParams) ((TableRow) findViewById(R.id.letter_row_1)).getLayoutParams();
					//tblRow.weight = 0.5f;
					 */
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_2).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_2).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_2).getTag()));
					resetFontsTypeFace(); 					
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_2)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_2)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		findViewById(R.id.letter_layout_3).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_3).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_3).getTag()));
					resetFontsTypeFace(); 					
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_3)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_3)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_4).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_4).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_4).getTag()));
					resetFontsTypeFace(); 					
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_4)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_4)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_5).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_5).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_5).getTag()));
					resetFontsTypeFace(); 					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_5)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_5)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_6).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_6).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_6).getTag()));
					resetFontsTypeFace(); 					
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_6)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_6)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_7).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_7).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_7).getTag()));
					resetFontsTypeFace(); 					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_7)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_7)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_8).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_8).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_8).getTag()));
					resetFontsTypeFace(); 					
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_8)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_8)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_9).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_9).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_9).getTag()));
					resetFontsTypeFace(); 					
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_9)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_9)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_10).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_10).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_10).getTag()));
					resetFontsTypeFace(); 					
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_10)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_10)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_11).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_11).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_11).getTag()));
					resetFontsTypeFace(); 					
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_11)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_11)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_12).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_12).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_12).getTag()));
					resetFontsTypeFace(); 					
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_12)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_12)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_13).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_13).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_13).getTag()));
					resetFontsTypeFace(); 				
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_13)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_13)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_14).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_14).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_14).getTag()));
					resetFontsTypeFace(); 				
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_14)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_14)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_15).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_15).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_15).getTag()));
					resetFontsTypeFace(); 				
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_15)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_15)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_16).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_16).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_16).getTag()));
					resetFontsTypeFace(); 				
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_16)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_16)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_17).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_17).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_17).getTag()));
					resetFontsTypeFace(); 				
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_17)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_17)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_18).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_18).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_18).getTag()));
					resetFontsTypeFace(); 				
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_18)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_18)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_19).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_19).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_19).getTag()));
					resetFontsTypeFace(); 			
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_19)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_19)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_20).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_20).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_20).getTag()));
					resetFontsTypeFace(); 				
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_20)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_20)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_21).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_21).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_21).getTag()));
					resetFontsTypeFace(); 				
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_21)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_21)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_22).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_22).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_22).getTag()));
					resetFontsTypeFace(); 			
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_22)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_22)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_23).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_23).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_23).getTag()));
					resetFontsTypeFace(); 			
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_23)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_23)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_24).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_24).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_24).getTag()));
					resetFontsTypeFace(); 			
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_24)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_24)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_25).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_25).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_25).getTag()));
					resetFontsTypeFace(); 			
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_25)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_25)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		
		findViewById(R.id.letter_layout_26).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!((String)findViewById(R.id.letter_layout_26).getTag()).equals("")){
					Long audioDuration=playGeneralAudio("piano_"+((String) findViewById(R.id.letter_layout_26).getTag()));
					resetFontsTypeFace(); 				
					audioHandler.postDelayed(runRestFontTypeFace, audioDuration);
					((TextView)findViewById(R.id.letter_26)).setTypeface(appData.getCurrentFontTypeBold());
					((TextView)findViewById(R.id.letter_26)).setTextSize(getResources().getDimension(R.dimen.le01_chosenfontsize));
				}
			}
		});
	
		
	}
	

	
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private void displayScreen(Cursor currentWordsCursor){
		currentWords=new ArrayList<ArrayList<String>>();
		int currentWordNumber=0;
		while(currentWordsCursor.moveToNext()){
			currentWords.add(new ArrayList<String>());
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex(S4K_Database.Words.WORD_ID)));
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex(S4K_Database.Words.WORD_TEXT)));
			currentWords.get(currentWordNumber).add(currentWordsCursor.getString(currentWordsCursor.getColumnIndex(S4K_Database.Words.AUDIO_URL)));
			currentWordNumber++;
		}
		
		// THIS IS SET FOR ENGLISH LANGUAGE (26 letters) ONLY RIGHT NOW MORE CODING NEEDED TO ACCOMIDATE OTHER LETTERS
		
		for(int letter_number=1; letter_number<=26; letter_number++){
			switch(letter_number){
				default:
				case 0:{
					break;
				}
				case 1:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_1).setTag(currentWords.get(letter_number-1).get(0));
						
						((TextView)findViewById(R.id.letter_1)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_1).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_1).setAlpha(0.08f);
					}
					break;
				}
				case 2:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_2).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_2)).setText(currentWords.get(letter_number-1).get(1));

						findViewById(R.id.letter_layout_2).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_2).setAlpha(0.08f);
					}
					break;
				}
				case 3:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_3).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_3)).setText(currentWords.get(letter_number-1).get(1));

						findViewById(R.id.letter_layout_3).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_3).setAlpha(0.08f);
					}
					break;
				}
				case 4:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_4).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_4)).setText(currentWords.get(letter_number-1).get(1));

						findViewById(R.id.letter_layout_4).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_4).setAlpha(0.08f);
					}
					break;
				}
				case 5:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_5).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_5)).setText(currentWords.get(letter_number-1).get(1));

						findViewById(R.id.letter_layout_5).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_5).setAlpha(0.08f);
					}
					break;
				}
				case 6:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_6).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_6)).setText(currentWords.get(letter_number-1).get(1));

						findViewById(R.id.letter_layout_6).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_6).setAlpha(0.08f);
					}
					break;
				}
				case 7:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_7).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_7)).setText(currentWords.get(letter_number-1).get(1));

						findViewById(R.id.letter_layout_7).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_7).setAlpha(0.08f);
					}
					break;
				}
				case 8:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_8).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_8)).setText(currentWords.get(letter_number-1).get(1));

						findViewById(R.id.letter_layout_8).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_8).setAlpha(0.08f);
					}
					break;
				}
				case 9:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_9).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_9)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_9).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_9).setAlpha(0.08f);
					}
					break;
				}
				case 10:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_10).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_10)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_10).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_10).setAlpha(0.08f);
					}
					break;
				}
				case 11:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_11).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_11)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_11).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_11).setAlpha(0.08f);
					}
					break;
				}
				case 12:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_12).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_12)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_12).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_12).setAlpha(0.08f);
					}
					break;
				}
				case 13:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_13).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_13)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_13).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_13).setAlpha(0.08f);
					}
					break;
				}
				case 14:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_14).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_14)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_14).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_14).setAlpha(0.08f);
					}
					break;
				}
				case 15:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_15).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_15)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_15).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_15).setAlpha(0.08f);
					}
					break;
				}
				case 16:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_16).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_16)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_16).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_16).setAlpha(0.08f);
					}
					break;
				}
				case 17:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_17).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_17)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_17).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_17).setAlpha(0.08f);
					}
					break;
				}
				case 18:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_18).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_18)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_18).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_18).setAlpha(0.08f);
					}
					break;
				}
				case 19:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_19).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_19)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_19).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_19).setAlpha(0.08f);
					}
					break;
				}
				case 20:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_20).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_20)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_20).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_20).setAlpha(0.08f);
					}
					break;
				}
				case 21:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_21).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_21)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_21).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_21).setAlpha(0.08f);
					}
					break;
				}
				case 22:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_22).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_22)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_22).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_22).setAlpha(0.08f);
					}
					break;
				}
				case 23:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_23).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_23)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_23).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_23).setAlpha(0.08f);
					}
					break;
				}
				case 24:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_24).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_24)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_24).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_24).setAlpha(0.08f);
					}
					break;
				}				
				case 25:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_25).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_25)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_25).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_25).setAlpha(0.08f);
					}
					break;
				}
				case 26:{
					if(currentWords.size()>=letter_number){
						findViewById(R.id.letter_layout_26).setTag(currentWords.get(letter_number-1).get(0));
						((TextView)findViewById(R.id.letter_26)).setText(currentWords.get(letter_number-1).get(1));
						findViewById(R.id.letter_layout_26).setAlpha(1.0f);
					}else{
						findViewById(R.id.letter_layout_26).setAlpha(0.08f);
					}
					break;
				}				
			}//end switch(letter_number)
			
		}//end for(int letter_number=1; letter_number<=26; letter_number++){
		setUpButtonListeners();
		
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////

	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		//String[] projection =null;
		CursorLoader cursorLoader=null;
		switch(id){
			default:
			case MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS:{
				
				ArrayList<String> currentGSP=new ArrayList<String>(appData.getCurrentGroup_Section_Phase());

				
				//NOTICE THIS IS ALWAYS DEFAULTING TO PHASE 1
				
				String selection=S4K_Database.Words_Phase_Levels.GROUP_ID+"="+currentGSP.get(0)
						+" AND "+S4K_Database.Words_Phase_Levels.PHASE_ID+"=1"
						+" AND "+S4K_Database.Words_Phase_Levels.LEVEL_NUMBER+"<="+currentGSP.get(3);
				
				cursorLoader = new CursorLoader(this,
					    Motoli_ContentProvidor.CONTENT_URI_ACTIVITY_CURRENT_WRDS_LTRS, null, selection, null, null);
				break;
			}
		}
		
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

		 switch(loader.getId()) {
			 default:
			 case MotoliConstants.ACTIVITY_CURRENT_WRDS_LTRS:{
				displayScreen(data);
				 break;
			 }
		 }
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}