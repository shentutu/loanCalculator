package alepha.loancalc;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class printLoanResult extends Activity {
	
	TextView txtLoanAmount;
	TextView txtLoanPeriod;
	TextView txtLoanInterest;
	TextView txtRepayAmount;
	TextView txtRepayPerMonth;
	TableRow repaymentPerMonthRow;
	TableLayout printloanResultLayout1;
	
    /** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.loanresult);
		
        /***************/
        AdView adView = new AdView(this, AdSize.FIT_SCREEN);
        LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout2);
        adLayout.addView(adView);
        /***************/
		
		Intent intent = this.getIntent();
		String LoanType = intent.getStringExtra("LoanType"); //String
		Log.d("LoanType", LoanType);
		String LoanCalcType = intent.getStringExtra("LoanCalcType"); //String
		Log.d("LoanCalcType", LoanCalcType);
		String loanAmountNum = intent.getStringExtra("TradeAmountNum"); //Integer
		Log.d("loanAmountNum", loanAmountNum);
		String providentAmountNum = intent.getStringExtra("ProvidentAmountNum"); //Integer
		Log.d("providentAmountNum", providentAmountNum);
		String loanReplaymentPeroid = intent.getStringExtra("LoanReplaymentPeroid"); //Double
		Log.d("loanReplaymentPeroid", loanReplaymentPeroid);
		String transInterest = intent.getStringExtra("LoanInterest"); //Double
		Log.d("transInterest", transInterest);
		String providentInterest = intent.getStringExtra("ProvidentInterest");//Integer
		Log.d("providentInterest", providentInterest);
		
		TextView tvPrompt = (TextView)super.findViewById(R.id.resultPrompt);
		tvPrompt.setText("您选择的是"+LoanType);
		
		printloanResultLayout1 = (TableLayout)super.findViewById(R.id.printloanResultLayout1);
		repaymentPerMonthRow = (TableRow)super.findViewById(R.id.repaymentPerMonthRow);
		
		double interestFinal = Double.valueOf(transInterest) / 12;
		double interestProvidentFinal = Double.valueOf(providentInterest) / 12;
		int repayPeriod = (int)(Double.valueOf(loanReplaymentPeroid) * 12);
		
		int RepayAmount = 0;
		int RepayProvidentAmount = 0;
		int InterestAmount = 0;
		int InterestProvidentAmount = 0;
		int RepaymentPeroid = repayPeriod;
		int RepaymentPerMonth = 0;
		int RepaymentProvidentPerMonth = 0;
		
		if (LoanCalcType.equals("等额本金"))
		{
			RepayAmount = getRepayAmountBenJin(Integer.valueOf(loanAmountNum), interestFinal, repayPeriod);	
			InterestAmount = RepayAmount - Integer.valueOf(loanAmountNum);	
			printloanResultLayout1.removeView(repaymentPerMonthRow);
		} else {	
		    RepayAmount = getRepayAmount(Integer.valueOf(loanAmountNum), interestFinal, repayPeriod);		
		    InterestAmount = getInterestAmount(RepayAmount, Integer.valueOf(loanAmountNum));		
		    RepaymentPerMonth = (int)getRepaymentPerMonth(Integer.valueOf(loanAmountNum), interestFinal, repayPeriod);
		    printloanResultLayout1.removeView(repaymentPerMonthRow);
		    printloanResultLayout1.addView(repaymentPerMonthRow,4);
		}
		
		RepayProvidentAmount = getRepayAmount(Integer.valueOf(providentAmountNum), interestProvidentFinal, repayPeriod);
		InterestProvidentAmount = getInterestAmount(RepayProvidentAmount, Integer.valueOf(providentAmountNum));
		RepaymentProvidentPerMonth = (int)getRepaymentPerMonth(Integer.valueOf(providentAmountNum), interestProvidentFinal, repayPeriod);
		// varible for content
		txtLoanAmount = (TextView)this.findViewById(R.id.printloanAmountNum);
		txtRepayAmount = (TextView)this.findViewById(R.id.printRepayAmount);
		txtLoanInterest = (TextView)this.findViewById(R.id.printloanInterest);
		txtLoanPeriod = (TextView)this.findViewById(R.id.printloanPeroid);
		txtRepayPerMonth = (TextView)this.findViewById(R.id.printloanInterestPerMonth);
		
		// content 
		txtLoanAmount.setTextColor(Color.rgb(0, 0, 0));
		int loanFinal = Integer.valueOf(loanAmountNum) + Integer.valueOf(providentAmountNum);
		txtLoanAmount.setText(""+loanFinal);
		
		txtRepayAmount.setTextColor(Color.rgb(0, 0, 0));
		int repayFinal = RepayAmount +  RepayProvidentAmount;
		txtRepayAmount.setText(""+repayFinal);
		
		txtLoanInterest.setTextColor(Color.rgb(0, 0, 0));
		int interestFinal_t = InterestAmount + InterestProvidentAmount;
		txtLoanInterest.setText(""+interestFinal_t);
		
		txtLoanPeriod.setTextColor(Color.rgb(0, 0, 0));
		txtLoanPeriod.setText(""+RepaymentPeroid);
		
		if (!LoanCalcType.equals("等额本金"))
		{
		txtRepayPerMonth.setTextColor(Color.rgb(0, 0, 0));
		int repayFinal_t = RepaymentPerMonth + RepaymentProvidentPerMonth;
		txtRepayPerMonth.setText(""+repayFinal_t);
		}
		// table for content repaymentPeroid, repaymentPerMonth, interestPerMonth, principalPerMonth, leftLoanPrincipal

		TableLayout tblLt3 = (TableLayout)super.findViewById(R.id.printloanResultLayout3);
		tblLt3.setStretchAllColumns(true);
		int t5Text = Integer.valueOf(loanAmountNum) + Integer.valueOf(providentAmountNum);
		
		for (int i=1; i <= repayPeriod ; i++)
		{
			TableRow tr = new TableRow(this);
			TextView t1 = new TextView(this);
			TextView t2 = new TextView(this);
			TextView t3 = new TextView(this);
			TextView t4 = new TextView(this);
			TextView t5 = new TextView(this);
			
			
			String repayStrPeroid = "第" + i + "期";
			t1.setText(repayStrPeroid);
			t1.setTextColor(Color.rgb(0, 0, 0));
			t1.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
			
			
			int t2Text = 0;
			int principlPerMonth = 0;
			int interestPerMonth = 0;
			
			if (LoanCalcType.equals("等额本金"))
			{
			  principlPerMonth = Integer.valueOf(loanAmountNum) / RepaymentPeroid;
			  interestPerMonth = (int)(Integer.valueOf(loanAmountNum) * interestFinal / 100 * (RepaymentPeroid - i + 1) / RepaymentPeroid);
			}
			
			if (LoanCalcType.equals("等额本金"))
			{
			  t2Text = principlPerMonth + interestPerMonth + RepaymentProvidentPerMonth;
			} else {
			  t2Text = RepaymentPerMonth + RepaymentProvidentPerMonth;
			}
			t2.setText("" +t2Text);
			t2.setTextColor(Color.rgb(255, 0, 0));
			t2.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
			
			int t3Text_1 = getInterestPerMonth(Integer.valueOf(loanAmountNum), interestFinal, i, RepaymentPerMonth);
			int t3Text_2 = getInterestPerMonth(Integer.valueOf(providentAmountNum), interestProvidentFinal, i, RepaymentProvidentPerMonth);
			
			int t3Text = 0;
			
			if (LoanCalcType.equals("等额本金"))
			{
				t3Text = interestPerMonth + t3Text_2;
			} else 
			{
				t3Text = t3Text_1 + t3Text_2;
			}
			
			t3.setText(""+t3Text);
			t3.setTextColor(Color.rgb(0, 0, 255));
			t3.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
			
			
			int t4Text = 0;
			if (LoanCalcType.equals("等额本金"))
			{
				t4Text = Integer.valueOf(loanAmountNum) / RepaymentPeroid;
			} else {
				t4Text = t2Text - t3Text;
			}
			
			t4.setText(""+t4Text);
			t4.setTextColor(Color.rgb(0, 0, 255));
			t4.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
			
			
			
			if( i == repayPeriod) {
				t5Text = 0;
			}
			else
			{
				t5Text = t5Text - t4Text;
			}
			
			t5.setText(""+t5Text);
			t5.setTextColor(Color.rgb(0, 0, 255));
			t5.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
			
			tr.addView(t1);
			tr.addView(t2);
			tr.addView(t3);
			tr.addView(t4);
			tr.addView(t5);
			
			tblLt3.addView(tr);
		}
		
		
		
		
		//
		ScrollView sview = (ScrollView)super.findViewById(R.id.sView);
		sview.setOverScrollMode(getTitleColor());
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
	
	public int getRepayAmount(int loanAmount, double interest, int period)
	{
		if (loanAmount == 0)
			return 0;
		
		double repayAmount = getRepaymentPerMonth(loanAmount, interest, period);
		return (int)repayAmount *  period;
	}
	
	public int getInterestAmount(int repayAmount, int loanAmount)
	{
		return (repayAmount > loanAmount)?(repayAmount - loanAmount):0;
	}
	/*
	public int getRepaymentPeroid(int year)
	{
		return year * 24;
	}
	*/
	public double getRepaymentPerMonth(int loanAmount, double interest, int count)
	{	
		double repayAmountPerMonth;
		//"等额本息","等额本金"
    	repayAmountPerMonth = loanAmount * interest/100 * Math.pow(1+interest/100, count) / (Math.pow(1+interest/100, count) - 1);
		  
		return repayAmountPerMonth;
	}
	
	public int getInterestPerMonth(int loanAmount, double interest, int count, int alreadypay)
	{
		int repayInterestPerMonth;
		//"等额本息","等额本金"
		repayInterestPerMonth = (int)((loanAmount * interest/100 - alreadypay) * Math.pow(1+interest/100, count - 1) + alreadypay);
		return repayInterestPerMonth;
	}
	
	public int  getRepayAmountBenJin(int loanAmountNum, double interestFinal, int repayPeriod)
	{
		int repayAmount = 0;
		repayAmount = (int)(loanAmountNum + loanAmountNum * interestFinal / 100 * (repayPeriod + 1) / 2);
		return repayAmount;
	}
	
}
