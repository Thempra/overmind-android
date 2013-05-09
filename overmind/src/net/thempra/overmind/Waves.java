package net.thempra.overmind;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * A simple formatter to convert bar indexes into sensor names.
 */
class WavesIndexFormat extends Format {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 3626863847760160495L;

	@Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        Number num = (Number) obj;

        // using num.intValue() will floor the value, so we add 0.5 to round instead:
        int roundNum = (int) (num.floatValue() + 0.5f);
        switch(roundNum) {
            case 0:
                toAppendTo.append("Signal");
                break;
            case 1:
                toAppendTo.append("Attention");
                break;
            case 2:
                toAppendTo.append("Meditation");
                break;
            case 3:
                toAppendTo.append("Delta");
                break;
            case 4:
                toAppendTo.append("Theta");
                break;
            case 5:
                toAppendTo.append("Low Alpha");
                break;
            case 6:
                toAppendTo.append("High Alpha");
                break;
            case 7:
                toAppendTo.append("Low Beta");
                break;
            case 8:
                toAppendTo.append("High Beta");
                break;
            case 9:
                toAppendTo.append("Low Gamma");
                break;
            case 10:
                toAppendTo.append("High Gamma");
                break;
            default:
                toAppendTo.append("");
        }
        return toAppendTo;
    }

	@Override
	public Object parseObject(String string, ParsePosition position) {
		// TODO Auto-generated method stub
		return null;
	}
}

