package com.yssj.utils;

public class STextUtils {
	
	/** 替换Html特殊符号 */
//	public static String escapeHtml(CharSequence text) {
//		StringBuilder out = new StringBuilder();
//		withinStyle(out, text, 0, text.length());
//		return out.toString();
//	}
	
//	private static void withinStyle(StringBuilder out, CharSequence text,
//			int start, int end) {
//		for (int i = start; i < end; i++) {
//			char c = text.charAt(i);
//
//			if (c == '<') {
//				out.append("&lt;");
//			} else if (c == '>') {
//				out.append("&gt;");
//			} else if (c == '&') {
//				out.append("&amp;");
//			} else if (c == ' ') {
//				while (i + 1 < end && text.charAt(i + 1) == ' ') {
//					out.append("&nbsp;");
//					i++;
//				}
//
//				out.append(' ');
//			} else {
//				out.append(c);
//			}
//
//			// else if (c > 0x7E || c < ' ') {
//			// out.append("&#" + ((int) c) + ";");
//			// }
//		}
//	}

}
