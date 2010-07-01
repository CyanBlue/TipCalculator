package com.cyanblue.tipcalculator;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Main extends Activity
{
	DecimalFormat Currency, Percentage, Person;
	
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
	
	EditText splitBy;
	SeekBar seekSplitBy;
	
	int maxSplitBy;
	int defaultSplitBy;
	
	TextView tip;
	TextView totalTax;
	TextView grandTotal;
	TextView label_totalPerPerson;
	TextView totalPerPerson;
	
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
		total.setId(101);
		total.setText(Currency.format(defaultTotal));
		
		seekTotal = (SeekBar) findViewById(R.id.seekTotal);
		seekTotal.setId(201);
		seekTotal.setMax(maxTotal * 100);
		seekTotal.setProgress(defaultTotal * 100);
		
		
		Percentage = new DecimalFormat("#0.0 %");
		
		maxTipRate = 30;
		defaultTipRate = 15;
		
		tipRate = (EditText) findViewById(R.id.tipRate);
		tipRate.setId(102);
		tipRate.setText(Percentage.format(defaultTipRate / 100));
		
		seekTipRate = (SeekBar) findViewById(R.id.seekTipRate);
		seekTipRate.setId(202);
		seekTipRate.setMax((int) (maxTipRate * 10));
		seekTipRate.setProgress((int) (defaultTipRate * 10));
		
		
		maxTaxRate = 15;
		defaultTaxRate = 5;
		
		taxRate = (EditText) findViewById(R.id.taxRate);
		taxRate.setId(103);
		taxRate.setText(Percentage.format(defaultTaxRate / 100));
		
		seekTaxRate = (SeekBar) findViewById(R.id.seekTaxRate);
		seekTaxRate.setId(203);
		seekTaxRate.setMax((int) (maxTaxRate * 10));
		seekTaxRate.setProgress((int) (defaultTaxRate * 10));
		
		
		Person = new DecimalFormat("#0 person");
		
		// Split by is 0 based...
		maxSplitBy = 9;
		defaultSplitBy = 0;
		
		splitBy = (EditText) findViewById(R.id.splitBy);
		splitBy.setId(104);
		splitBy.setText(Person.format(defaultSplitBy + 1));
		
		seekSplitBy = (SeekBar) findViewById(R.id.seekSplitBy);
		seekSplitBy.setId(204);
		seekSplitBy.setMax(maxSplitBy);
		seekSplitBy.setProgress(defaultSplitBy);
		
		
		tip = (TextView) findViewById(R.id.tip);
		totalTax = (TextView) findViewById(R.id.totalTax);
		grandTotal = (TextView) findViewById(R.id.grandTotal);
		label_totalPerPerson = (TextView) findViewById(R.id.label_totalPerPerson);
		totalPerPerson = (TextView) findViewById(R.id.totalPerPerson);
		
		updateTotal();
		
		total.setOnFocusChangeListener(onFocusChange_Listener);
		tipRate.setOnFocusChangeListener(onFocusChange_Listener);
		taxRate.setOnFocusChangeListener(onFocusChange_Listener);
		splitBy.setOnFocusChangeListener(onFocusChange_Listener);
		
		seekTotal.setOnSeekBarChangeListener(onChanged_Listener);
		seekTipRate.setOnSeekBarChangeListener(onChanged_Listener);
		seekTaxRate.setOnSeekBarChangeListener(onChanged_Listener);
		seekSplitBy.setOnSeekBarChangeListener(onChanged_Listener);
	}
	
	private OnFocusChangeListener onFocusChange_Listener = new OnFocusChangeListener()
	{
		@Override
		public void onFocusChange(View v, boolean hasFocus)
		{
			EditText txt = (EditText) v;
			int id = txt.getId();
			
			String st;
			float _fVal;
			int _iVal;
			
			st = "";
			st = txt.getText().toString();
			st = st.replace("$", "");
			st = st.replace("%", "");
			st = st.replace("person", "");
			st = st.replace(" ", "");
			if (st == "")
			{
				st = "0";
			}
			
			_fVal = Float.valueOf(st.trim()).floatValue();
			
			switch (id)
			{
				case 101:
				{
					seekTotal.setProgress((int) _fVal * 100);
					
					break;
				}
				case 102:
				{
					seekTipRate.setProgress((int) _fVal * 10);
					
					break;
				}
				case 103:
				{
					seekTaxRate.setProgress((int) _fVal * 10);
					
					break;
				}
				case 104:
				{
					_iVal = Integer.valueOf(st.trim()).intValue();
					seekSplitBy.setProgress(_iVal - 1);
					
					break;
				}
			}
		}
	};
	
	private OnSeekBarChangeListener onChanged_Listener = new OnSeekBarChangeListener()
	{
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch)
		{
			int id = seekBar.getId();
			float index = seekBar.getProgress();
			
			switch (id)
			{
				case 201:
				{
					total.setText(Currency.format(index / 100));
					
					break;
				}
				case 202:
				{
					tipRate.setText(Percentage.format(index / 1000));
					
					break;
				}
				case 203:
				{
					taxRate.setText(Percentage.format(index / 1000));
					
					break;
				}
				case 204:
				{
					splitBy.setText(Person.format(index + 1));
					
					break;
				}
			}
			
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
		String st1, st2, st3, st4;
		float _total;
		float _tipRate;
		float _taxRate;
		int _splitBy;
		
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
		
		st4 = "";
		try
		{
			st4 = splitBy.getText().toString();
		}
		catch (NullPointerException e)
		{
			st4 = "0";
		}
		st4 = st4.replace("person", "");
		st4 = st4.replace(" ", "");
		_splitBy = Integer.valueOf(st4.trim()).intValue();
		
		float _totalTax = _total * _taxRate / 100;
		float _tip = (_total + _totalTax) * _tipRate / 100;
		float _grandTotal = _total + _tip + _totalTax;
		float _totalPerPerson = _grandTotal / _splitBy;
		
		tip.setText(Currency.format(_tip));
		totalTax.setText(Currency.format(_totalTax));
		grandTotal.setText(Currency.format(_grandTotal));
		
		if (_splitBy == 1)
		{
			label_totalPerPerson.setVisibility(View.INVISIBLE);
			totalPerPerson.setVisibility(View.INVISIBLE);
		}
		else
		{
			label_totalPerPerson.setVisibility(View.VISIBLE);
			totalPerPerson.setVisibility(View.VISIBLE);
		}
		totalPerPerson.setText(Currency.format(_totalPerPerson));
	}
}
