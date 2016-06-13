package com.peerless2012.somehospital.map;

import android.content.Context;

import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 21:14
 * @Version V1.0
 * @Description :
 */
public class SuggestionAdapter extends SearchSuggestionsAdapter{
    public SuggestionAdapter(Context context, int suggestionTextSize, Listener listener) {
        super(context, suggestionTextSize, listener);
    }

    @Override
    public List<? extends SearchSuggestion> getDataSet() {
        return super.getDataSet();
    }
}

