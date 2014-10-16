package alepha.loancalc;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class LoanCalcActivity extends Activity {
	
	// for loan type
	private static final String[] loanTypeStr = {"商业贷款","公积金贷款","组合贷款"};
	private ArrayAdapter<String> loanTypeStr_adapter;
	/*
	TableRow trCommerical = (TableRow)super.findViewById(R.id.commercialProportionRow);
	TableRow trCombination = (TableRow)super.findViewById(R.id.combinationProportionTableRow);
	TableRow trAccumution = (TableRow)super.findViewById(R.id.accumulationProportionRow);
	*/
	// for loan Calc Type
	private static final String[] loanCalcTypeStr = {"等额本息","等额本金"};
	private ArrayAdapter<String> loanCalcTypeStr_adapter;
	
	// for interest type
	private static final String[] loanInterestTypeStr = {"12年07月份利率","12年06月份利率","11年07月份利率","自定义"};
	private ArrayAdapter<String> loanInterestTypeStr_adapter;
	
	// for discount num
	private static final String[] loanInterestDisCountStr = {"0.7倍基准利率","0.85倍基准利率","1.0倍基准利率","自定义"};
	private ArrayAdapter<String> loanInterestDisCountStr_adapter;
	
	// for one or two house
	private static final String[] onetwoHouseStr = {"第一套房","第二套房"};
	private ArrayAdapter<String> onetwoHouseStr_adapter;
	
	int global_count = 0;
	int customize_count = 0;
	int customize_count_t = 0;
	
	EditText edttradeAmountNum;
	EditText edtProvidentAmountNum;
	EditText edtLoanRepaymentPeroid;
	
	TableLayout tblLayout;
	TableRow tblRow;
	TextView txtView;
	
	
	Spinner loanTypeStr_spinner;
	Spinner loanCalcTypeStr_spinner;
	Spinner loanInterestTypeStr_spinner;
	Spinner loanInterestDisCountStr_spinner;
	
	
	Double transInterestDouble;
	Double ProvidentInterestDouble;
	Double transInterestDiscountDouble;
	
	TableRow loanCalcTypeRow;
	TableRow oneTwoHouse;
	
	TableRow javatradeAmountNumTableRow;
	TableRow javaprovidentAmountTableRow;
	
	Spinner oneTwoHouse_spinner;
	
	TableRow javacustomizeRateRow;
	TableRow javacustomizeDiscountRow;
	
	EditText evcustomizeRate;
	EditText evcustomizeDiscount;
	
	TableRow loanDisCountRow;
    
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /***************/
        AdView adView = new AdView(this, AdSize.FIT_SCREEN);
        LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
        adLayout.addView(adView);
        /***************/
        
        tblLayout = (TableLayout)super.findViewById(R.id.tblLayoutMain);        
        edttradeAmountNum = (EditText)super.findViewById(R.id.tradeAmount);
        edtProvidentAmountNum = (EditText)super.findViewById(R.id.providentAmount);
        edtLoanRepaymentPeroid = (EditText)super.findViewById(R.id.loanPeriod);
        
        loanTypeStr_spinner = (Spinner)super.findViewById(R.id.loanTypeSpinner);
        loanTypeStr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,loanTypeStr);
        loanTypeStr_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        loanTypeStr_spinner.setAdapter(loanTypeStr_adapter);
        loanTypeStr_spinner.setOnItemSelectedListener(new SpinnerSelectedListerner());
        
        loanCalcTypeStr_spinner = (Spinner)super.findViewById(R.id.loanCalcTypeSpinner);
        loanCalcTypeStr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,loanCalcTypeStr);
        loanCalcTypeStr_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        loanCalcTypeStr_spinner.setAdapter(loanCalcTypeStr_adapter);
        
        loanInterestTypeStr_spinner = (Spinner)super.findViewById(R.id.loanInterestMethodSpinner);
        loanInterestTypeStr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,loanInterestTypeStr);
        loanInterestTypeStr_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        loanInterestTypeStr_spinner.setAdapter(loanInterestTypeStr_adapter);
        loanInterestTypeStr_spinner.setOnItemSelectedListener(new InterestSpinnerSelectedListerner());
        
        loanInterestDisCountStr_spinner = (Spinner)super.findViewById(R.id.loanDisCounSpinner);
        loanInterestDisCountStr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,loanInterestDisCountStr);
        loanInterestDisCountStr_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        loanInterestDisCountStr_spinner.setAdapter(loanInterestDisCountStr_adapter);
        loanInterestDisCountStr_spinner.setOnItemSelectedListener(new InterestDisCountSpinnerSelectedListerner());
        
        oneTwoHouse = (TableRow)super.findViewById(R.id.onetwoHouse);
        oneTwoHouse_spinner = (Spinner)super.findViewById(R.id.onetwoHouseSpinner);
        
//        hidden oneTwoHouse and oneTwoHouse_spinner view
        tblLayout.removeView(oneTwoHouse);
        tblLayout.removeView(oneTwoHouse_spinner);
        
        onetwoHouseStr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,onetwoHouseStr);
        onetwoHouseStr_adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        oneTwoHouse_spinner.setAdapter(onetwoHouseStr_adapter);
        
        evcustomizeRate = (EditText)super.findViewById(R.id.customizeRate);
        evcustomizeDiscount = (EditText)super.findViewById(R.id.customizeDiscount);
        
        AdManager.getInstance(this).init("3252fc28f788d54b","a1a6359e2eae3c78", false); 
        
        boolean checkResult = net.youmi.android.smart.SmartBannerManager.checkSmartBannerAdConfig(this);
        System.out.println(checkResult);
        
        // for submit button
        Button btn = (Button)super.findViewById(R.id.loanSubmitButton);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				// mput loan amount num, loan interest, loan period
				
				// for loan type
				String LoanType = loanTypeStr_spinner.getSelectedItem().toString();
				intent.putExtra("LoanType", LoanType);
				
				// for loan calc type
				String LoanCalcType = loanCalcTypeStr_spinner.getSelectedItem().toString();
				intent.putExtra("LoanCalcType", LoanCalcType);	
				
				String loanAmountNum = edttradeAmountNum.getText().toString();
				
				String providentAmountNum = edtProvidentAmountNum.getText().toString();
				if (loanAmountNum.equals(""))
				{
					loanAmountNum = "0";
				}
				intent.putExtra("TradeAmountNum", loanAmountNum);
				
				if (providentAmountNum.equals(""))
				{
					providentAmountNum = "0";
				}
				intent.putExtra("ProvidentAmountNum", providentAmountNum);
				
				String loanReplaymentPeroid = edtLoanRepaymentPeroid.getText().toString();
				if (loanReplaymentPeroid.equals(""))
				{
					loanReplaymentPeroid = "0";
				}
				intent.putExtra("LoanReplaymentPeroid", loanReplaymentPeroid);
				
				String onetwoHouse = oneTwoHouse_spinner.getSelectedItem().toString();
				
				//TBD: loan period
				
				String loanInterestStr = loanInterestTypeStr_spinner.getSelectedItem().toString();
				if (loanInterestStr.equals(""))
				{
					loanInterestStr = "0";
				}
				String loanInterestDisCountStr = loanInterestDisCountStr_spinner.getSelectedItem().toString();
				if (loanInterestDisCountStr.equals(""))
				{
					loanInterestDisCountStr = "0";
				}
				
				double repayPeroid = Double.valueOf(loanReplaymentPeroid).doubleValue();
				
				double transInterest = gettransInterestStr(loanInterestStr,loanInterestDisCountStr,repayPeroid);
				double ProvidentInterestStr = getProvidentInterestStr(onetwoHouse, onetwoHouse, repayPeroid);
                
                Log.d("loanInterestStr", loanInterestStr);
				Log.d("loanInterestDisCountStr", loanInterestDisCountStr);
				Log.d("repayPeroid", ""+repayPeroid);
				
				intent.putExtra("LoanInterest",Double.valueOf(transInterest).toString());
				intent.putExtra("ProvidentInterest", Double.valueOf(ProvidentInterestStr).toString());
				
				intent.setClass(LoanCalcActivity.this, printLoanResult.class);
				startActivity(intent);
			}
		});
        
        loanCalcTypeRow = (TableRow)super.findViewById(R.id.loanCalcTypeRow);
        
    	javatradeAmountNumTableRow = (TableRow)super.findViewById(R.id.tradeAmountNumTableRow);
    	javaprovidentAmountTableRow = (TableRow)super.findViewById(R.id.providentAmountTableRow);

    	
    	javacustomizeRateRow = (TableRow)super.findViewById(R.id.customizeRateRow);
    	javacustomizeDiscountRow = (TableRow)super.findViewById(R.id.customizeDiscountRow);
    	
    	loanDisCountRow = (TableRow)super.findViewById(R.id.loanDisCountRow);
    	
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    }
    
    @Override
    public void onPause()
    {
    	super.onResume();
    }
    
    class SpinnerSelectedListerner implements OnItemSelectedListener {
    	public void onItemSelected(AdapterView<?>arg0, View arg1, int arg2, long arg3)
    	{	
    		if(loanTypeStr[arg2].equals("公积金贷款"))
    		{
      		  tblLayout.removeView(loanCalcTypeRow);
      		  tblLayout.removeView(oneTwoHouse);
      		  tblLayout.removeView(javatradeAmountNumTableRow);
      		  tblLayout.removeView(javaprovidentAmountTableRow);
      		  
      		  edttradeAmountNum.setText("");
      		  edtProvidentAmountNum.setText("");
      		  edtLoanRepaymentPeroid.setText("");
      		  evcustomizeRate.setText("");
      		  evcustomizeDiscount.setText("");
      		  loanDisCountRow.setVisibility(4);
      		  javacustomizeDiscountRow.setVisibility(4);
      		  
    	      tblLayout.addView(oneTwoHouse,1);
    	      tblLayout.addView(javaprovidentAmountTableRow,2);
    	      
    	      customize_count = 0;
    		} 
    		else if (loanTypeStr[arg2].equals("商业贷款"))
    		{
        		  tblLayout.removeView(loanCalcTypeRow);
          		  tblLayout.removeView(oneTwoHouse);
          		  tblLayout.removeView(javatradeAmountNumTableRow);
          		  tblLayout.removeView(javaprovidentAmountTableRow);

          		  edttradeAmountNum.setText("");
          		  edtProvidentAmountNum.setText("");
          		  edtLoanRepaymentPeroid.setText("");
          		  evcustomizeRate.setText("");
          		  evcustomizeDiscount.setText("");
          		loanDisCountRow.setVisibility(0);
          		javacustomizeDiscountRow.setVisibility(1);
          		  
    		  
    		  tblLayout.addView(loanCalcTypeRow,1);
    		  tblLayout.addView(javatradeAmountNumTableRow,2);
    		  
    		  
    		  customize_count = 0;
    		} 
    		else if (loanTypeStr[arg2].equals("组合贷款"))
    		{
        		  tblLayout.removeView(loanCalcTypeRow);
          		  tblLayout.removeView(oneTwoHouse);
          		  tblLayout.removeView(javatradeAmountNumTableRow);
          		  tblLayout.removeView(javaprovidentAmountTableRow);
          		  
          		  edttradeAmountNum.setText("");
          		  edtProvidentAmountNum.setText("");
          		  edtLoanRepaymentPeroid.setText("");
          		  evcustomizeRate.setText("");
          		  evcustomizeDiscount.setText("");
          		loanDisCountRow.setVisibility(0);
          		javacustomizeDiscountRow.setVisibility(0);
    		  
    		  tblLayout.addView(loanCalcTypeRow, 1);
      	      tblLayout.addView(oneTwoHouse, 2);
      	      tblLayout.addView(javatradeAmountNumTableRow,3);
      	      tblLayout.addView(javaprovidentAmountTableRow,4);
      	      
      	      customize_count = 2;
    		}
    	}
    	
    	public void onNothingSelected(AdapterView<?>arg0)
    	{
      	}
    	
    }
    
    class InterestSpinnerSelectedListerner implements OnItemSelectedListener 
    {
    	public void onItemSelected(AdapterView<?>arg0, View arg1, int arg2, long arg3)
    	{	

    		if(loanInterestTypeStr[arg2].equals("自定义"))
    		{
    			tblLayout.removeView(javacustomizeRateRow);
    			tblLayout.addView(javacustomizeRateRow,5+customize_count);
    			customize_count_t = 1;
    			
    		} else
    		{
    			tblLayout.removeView(javacustomizeRateRow);
    			customize_count_t = 0;
    		}

    	}
    	
    	public void onNothingSelected(AdapterView<?>arg0)
    	{
    		tblLayout.removeView(javacustomizeRateRow);
      	}
    }
    	
    class InterestDisCountSpinnerSelectedListerner implements OnItemSelectedListener 
    {
    	public void onItemSelected(AdapterView<?>arg0, View arg1, int arg2, long arg3)
    	{	
    		if(loanInterestDisCountStr[arg2].equals("自定义"))
    		{
    			tblLayout.removeView(javacustomizeDiscountRow);
                Log.d("customize_count", ""+customize_count);
                Log.d("customize_count_t", ""+customize_count_t);
    			tblLayout.addView(javacustomizeDiscountRow,6+customize_count+customize_count_t);

    		} else
    		{
    			tblLayout.removeView(javacustomizeDiscountRow);
    		}
    	}
    	
    	public void onNothingSelected(AdapterView<?>arg0)
    	{
    		tblLayout.removeView(javacustomizeDiscountRow);
      	}
    }

    public double gettransInterestStr(String interestOption, String loanInterestDisCountStr, double duration)
    {
    	double discount = 1;
    	
    	String LoanType = loanTypeStr_spinner.getSelectedItem().toString();
		//"商业贷款","公积金贷款","组合贷款"
    	if (LoanType.equals("公积金贷款"))
    	{
    		return 0;
    	}
    	//"11年07月份利率","11年04月份利率","11年02月份利率","10年12月份利率","自定义"
    	if (interestOption.equals("12年07月份利率"))
    	{
			if (duration < 0.6)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_120706_6m));
			} else if (duration > 0.6 && duration <= 1)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_120706_6m_2_1y));
				Log.d("transInterestDouble in loop",""+transInterestDouble);
			} else if (duration > 1 && duration <= 3)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_120706_1y_2_3y));
			} else if (duration > 3 && duration <= 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_120706_3y_2_5y));
			} else if (duration > 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_120706_5y_above));
			}	
    	} else if (interestOption.equals("12年06月份利率"))
    	{
			if (duration < 0.6)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_120608_6m));
			} else if (duration > 0.6 && duration <= 1)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_120608_6m_2_1y));
				Log.d("transInterestDouble in loop",""+transInterestDouble);
			} else if (duration > 1 && duration <= 3)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_120608_1y_2_3y));
			} else if (duration > 3 && duration <= 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_120608_3y_2_5y));
			} else if (duration > 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_120608_5y_above));
			}	
    	} else if (interestOption.equals("11年07月份利率")) {
			if (duration < 0.6)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110707_6m));
			} else if (duration > 0.6 && duration <= 1)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110707_6m_2_1y));
				Log.d("transInterestDouble in loop",""+transInterestDouble);
			} else if (duration > 1 && duration <= 3)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110707_1y_2_3y));
			} else if (duration > 3 && duration <= 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110707_3y_2_5y));
			} else if (duration > 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110707_5y_above));
			}		
		} else if (interestOption.equals("11年04月份利率"))
		{
			if (duration < 0.6)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110406_6m));
			} else if (duration > 0.6 && duration <= 1)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110406_6m_2_1y));
				Log.d("transInterestDouble in loop",""+transInterestDouble);
			} else if (duration > 1 && duration <= 3)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110406_1y_2_3y));
			} else if (duration > 3 && duration <= 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110406_3y_2_5y));
			} else if (duration > 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110406_5y_above));
			}		
		} else if (interestOption.equals("11年02月份利率"))
		{
			if (duration < 0.6)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110209_6m));
			} else if (duration > 0.6 && duration <= 1)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110209_6m_2_1y));
				Log.d("transInterestDouble in loop",""+transInterestDouble);
			} else if (duration > 1 && duration <= 3)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110209_1y_2_3y));
			} else if (duration > 3 && duration <= 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110209_3y_2_5y));
			} else if (duration > 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110209_5y_above));
			}		
		} else if (interestOption.equals("10年12月份利率"))
		{
			if (duration < 0.6)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_101226_6m));
			} else if (duration > 0.6 && duration <= 1)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_101226_6m_2_1y));
				Log.d("transInterestDouble in loop",""+transInterestDouble);
			} else if (duration > 1 && duration <= 3)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_101226_1y_2_3y));
			} else if (duration > 3 && duration <= 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_101226_3y_2_5y));
			} else if (duration > 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_101226_5y_above));
			}		
		} else if (interestOption.equals("11年02月份利率"))
		{
			if (duration < 0.6)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110209_6m));
			} else if (duration > 0.6 && duration <= 1)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110209_6m_2_1y));
			} else if (duration > 1 && duration <= 3)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110209_1y_2_3y));
			} else if (duration > 3 && duration <= 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110209_3y_2_5y));
			} else if (duration > 5)
			{
				transInterestDouble = Double.valueOf(getString(R.string.r_110209_5y_above));
			}
		} else if (interestOption.equals("自定义"))
		{
			transInterestDouble = Double.valueOf(evcustomizeRate.getText().toString());
		}
		
		//"0.7倍基准利率","0.85倍基准利率","1.0倍基准利率","自定义"
		if (loanInterestDisCountStr.endsWith("0.7倍基准利率"))
		{
			discount = 0.7;
		} else if (loanInterestDisCountStr.endsWith("0.85倍基准利率"))
		{
			discount = 0.85;
		} else if(loanInterestDisCountStr.endsWith("1.0倍基准利率"))
		{
			discount = 1;
		} else if(loanInterestDisCountStr.endsWith("自定义"))
		{
			discount = Double.valueOf(evcustomizeDiscount.getText().toString()) / 10;
			
		}
		Log.d("discount in loop",""+discount);
		return transInterestDouble * discount;
    }
    
    public double getProvidentInterestStr(String onetwoHouse, String loanInterestStr, double repayPeroid)
    {
    	String LoanType = loanTypeStr_spinner.getSelectedItem().toString();
		//"商业贷款","公积金贷款","组合贷款"
    	if (LoanType.equals("商业贷款"))
    	{
    		return 0;
    	}
    	
    	//"第一套房","第二套房"
    	double ProvidentInterest = 0;
    	int flagOneTwoHouse = 0;
    	
    	Log.d("loanInterestStr", loanInterestStr);
    	
    	if (loanInterestStr.equals("第一套房"))
    	{
    		flagOneTwoHouse = 1;
    	} else if (loanInterestStr.equals("第二套房"))
    	{
    		flagOneTwoHouse = 2;
    	} else
    	{
    		return flagOneTwoHouse;
    	}
    	if (repayPeroid < 5)
    	{
    		if (flagOneTwoHouse == 1)
    		{
    		    ProvidentInterest = Double.valueOf(getString(R.string.p_110707_1y_2_5y_house1));
    		} else
    		{
    			ProvidentInterest = Double.valueOf(getString(R.string.p_110707_5y_2_30y_house1));	
    		}
    	} else 
    	{
    		if (flagOneTwoHouse == 1)
    		{
    		    ProvidentInterest = Double.valueOf(getString(R.string.p_110707_1y_2_5y_house2));
    		} else
    		{
    			ProvidentInterest = Double.valueOf(getString(R.string.p_110707_5y_2_30y_house2));	
    		}
    	}
    	
    	return ProvidentInterest;
    }
}