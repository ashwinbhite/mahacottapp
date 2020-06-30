package com.cotton.mahacott.util;

import java.util.Arrays;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.UnderlineSpan;

public class TextUtility {
	public static final String EMAIL_ID_REGEX = ".*[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.*";

	
	public static CharSequence setSpanBetweenTokens(CharSequence text,
			String token, CharacterStyle... cs) {
		
		int tokenLen = token.length();
		int start = text.toString().indexOf(token) + tokenLen;
		int end = text.toString().indexOf(token, start);

		if (start > -1 && end > -1) {
			
			SpannableStringBuilder ssb = new SpannableStringBuilder(text);
			for (CharacterStyle c : cs)
				ssb.setSpan(c, start, end, 0);

			
			ssb.delete(end, end + tokenLen);
			ssb.delete(start - tokenLen, start);

			text = ssb;
		}

		return text;
	}
	public static SpannableString setViewUnderline(String str) {
		SpannableString content = new SpannableString(str);
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		return content;
	}

	public static String stringify(String...strings) {
		return Arrays.toString(strings);
	}

	
}

