package si.android.toy.view.adapter;

import si.android.toy.R;
import si.android.toy.model.CountriesList;
import si.android.toy.model.Country;
import si.android.toy.model.EmptyCountry;
import si.android.toy.view.ValidatingSpinner;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CountriesSpinner extends Spinner /*extends ValidatingSpinner<Country> */{
	private Context context;

	public CountriesSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		//super(context, attrs, new EmptyCountry());
		this.context = context;
		initializeAdapter();
	}
	
	public CountriesSpinner(Context context, Country emptyItem) {
		super(context);
		//super(context, new EmptyCountry());
		this.context = context;
		initializeAdapter();
	}

	public CountriesSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//super(context, attrs, defStyle, new EmptyCountry());
		this.context = context;
		initializeAdapter();
	}

	private void initializeAdapter() {
		ArrayAdapter<Country> countriesAdapter = new ArrayAdapter<Country>(
			context, R.layout.address_country_textview, new CountriesList());
		countriesAdapter.setDropDownViewResource(R.layout.address_country_dropdownview);
		setAdapter(countriesAdapter);
	}
}
