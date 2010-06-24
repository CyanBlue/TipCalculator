package com.cyanblue.tipcalculator;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Main extends Activity
{
	DecimalFormat Currency, Percentage;
	
	EditText total;
	SeekBar seekTotal;
	
	int maxTotal;
	int defaultTotal;
	
	EditText tipRate;
	SeekBar seekTipRate;
	
	float maxTipRate;
	float defaultTipRate;
	
	EditText taxRate;
	SeekBar seekTaxRate;
	
	float maxTaxRate;
	float defaultTaxRate;
	
	TextView tip;
	TextView totalTax;
	TextView grandTotal;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Currency = new DecimalFormat("$ #0.00");
		
		maxTotal = 250;
		defaultTotal = 25;
		
		total = (EditText) findViewById(R.id.total);
		total.setText(Currency.format(defaultTotal));
		
		seekTotal = (SeekBar) findViewById(R.id.seekTotal);
		seekTotal.setMax(maxTotal * 100);
		seekTotal.setProgress(defaultTotal * 100);
		
//		total.addTextChangedListener(valueTextWatcher);
		
		
		Percentage = new DecimalFormat("#0.0 %");
		
		maxTipRate = 30;
		defaultTipRate = 15;
		
		tipRate = (EditText) findViewById(R.id.tipRate);
		tipRate.setText(Percentage.format(defaultTipRate / 100));
		
		seekTipRate = (SeekBar) findViewById(R.id.seekTipRate);
		seekTipRate.setMax((int) (maxTipRate * 10));
		seekTipRate.setProgress((int) (defaultTipRate * 10));
		
		
		maxTaxRate = 15;
		defaultTaxRate = 5;
		
		taxRate = (EditText) findViewById(R.id.taxRate);
		taxRate.setText(Percentage.format(defaultTaxRate / 100));
		
		seekTaxRate = (SeekBar) findViewById(R.id.seekTaxRate);
		seekTaxRate.setMax((int) (maxTaxRate * 10));
		seekTaxRate.setProgress((int) (defaultTaxRate * 10));
		
		
		tip = (TextView) findViewById(R.id.tip);
		totalTax = (TextView) findViewById(R.id.totalTax);
		grandTotal = (TextView) findViewById(R.id.grandTotal);
		
		updateTotal();
		
		seekTotal.setOnSeekBarChangeListener(onChanged_Total);
		seekTipRate.setOnSeekBarChangeListener(onChanged_Tip);
		seekTaxRate.setOnSeekBarChangeListener(onChanged_Tax);
	}
	
	private OnSeekBarChangeListener onChanged_Total = new OnSeekBarChangeListener()
	{
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch)
		{
			float index = seekBar.getProgress();
			
			total.setText(Currency.format(index / 100));
			
			updateTotal();
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
			// TODO Auto-generated method stub
		}
	};
	
	private OnSeekBarChangeListener onChanged_Tip = new OnSeekBarChangeListener()
	{
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch)
		{
			float index = seekBar.getProgress();
			
			tipRate.setText(Percentage.format(index / 1000));
			
			updateTotal();
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
			// TODO Auto-generated method stub
		}
	};
	
	private OnSeekBarChangeListener onChanged_Tax = new OnSeekBarChangeListener()
	{
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch)
		{
			float index = seekBar.getProgress();
			
			taxRate.setText(Percentage.format(index / 1000));
			
			updateTotal();
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
			// TODO Auto-generated method stub
		}
	};
	
	private void updateTotal()
	{
//		float tax = total.getText().replace("$", "")
		
		String st1, st2, st3;
		float _total;
		float _tipRate;
		float _taxRate;
		
		st1 = "";
		st1 = total.getText().toString();
		st1 = st1.replace("$", "");
		st1 = st1.replace(" ", "");
		_total = Float.valueOf(st1.trim()).floatValue();
		
		st2 = "";
		try
		{
			st2 = tipRate.getText().toString();
		}
		catch (NullPointerException e)
		{
			st2 = "0";
		}
		st2 = st2.replace("%", "");
		st2 = st2.replace(" ", "");
		_tipRate = Float.valueOf(st2.trim()).floatValue();
		
		st3 = "";
		try
		{
			st3 = taxRate.getText().toString();
		}
		catch (NullPointerException e)
		{
			st3 = "0";
		}
		st3 = st3.replace("%", "");
		st3 = st3.replace(" ", "");
		_taxRate = Float.valueOf(st3.trim()).floatValue();
		
		System.out.print("_total = " + _total + " : " + "_tipRate = " + _tipRate + " : " + "_taxRate = " + _taxRate);
		
		float _totalTax = _total * _taxRate / 100;
		float _tip = (_total + _totalTax) * _tipRate / 100;
		float _grandTotal = _total + _tip + _totalTax;
		
		tip.setText(Currency.format(_tip));
		totalTax.setText(Currency.format(_totalTax));
		grandTotal.setText(Currency.format(_grandTotal));
	}
	
	TextWatcher valueTextWatcher = new TextWatcher()
	{
		@Override
		public void afterTextChanged(Editable s)
		{
			// TODO Auto-generated method stub
//			validateFields();
			
			String st;
			float f;
			
			st = total.getText().toString();
			st = st.replace("$", "");
			f = Float.valueOf(st.trim()).floatValue();
			seekTotal.setProgress((int) f * 100);
		}
		
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
		}
		
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
		}
	};
}
