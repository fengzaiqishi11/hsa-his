package cn.hsa.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;

import java.io.File;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Package_name: cn.hsa.util
 * @Class_name: UtilFunc
 * @Describe(描述):
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/10/29 15:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public class UtilFunc {
    private static final char PACKAGE_SEPARATOR = '.';
    private static final char INNER_CLASS_SEPARATOR = '$';
    private static final String CGLIB_CLASS_SEPARATOR = "$$";
    public static final String NEW_LINE = System.getProperty("line.separator", "\n");
    public static final String FILE_SEPARATOR = System.getProperty("file.separator", "\\");
    private static final String EMPTY = "";
    private static final int PAD_LIMIT = 8192;
    private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private UtilFunc() {
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    public static boolean isEmpty(String str) {
        return !hasLength(str);
    }

    public static boolean isEmptyString(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            return !hasText((String)obj);
        } else {
            return !hasText(obj.toString());
        }
    }

    public static boolean isBlank(Object obj) {
        return isEmptyString(obj);
    }

    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

    public static String getString(Map map, Object key) {
        return getString(map, key, "");
    }

    public static String getString(Map map, Object key, String defaultVal) {
        if (!isEmpty(map) && key != null) {
            Object val = map.get(key);
            return val != null ? val.toString() : defaultVal;
        } else {
            return defaultVal;
        }
    }

    public static Object getValue(Map map, Object key) {
        return getValue(map, key, (Object)null);
    }

    public static Object getValue(Map map, Object key, Object defaultVal) {
        Object val = null;
        if (!isEmpty(map)) {
            val = map.get(key);
        }

        return val != null ? val : defaultVal;
    }

    public static Object[] toArray(Collection collection) throws RuntimeException {
        if (collection == null) {
            return null;
        } else {
            try {
                return collection.toArray();
            } catch (Exception var2) {
                throw new RuntimeException("集合转换对象数组出错", var2);
            }
        }
    }

    public static String[] toStringArray(Collection collection) throws RuntimeException {
        if (collection == null) {
            return null;
        } else {
            try {
                return (String[])((String[])collection.toArray(new String[collection.size()]));
            } catch (Exception var2) {
                throw new RuntimeException("集合转换字符串数组出错", var2);
            }
        }
    }

    public static String[] toStringArray(Enumeration enumeration) throws RuntimeException {
        if (enumeration == null) {
            return null;
        } else {
            try {
                List list = Collections.list(enumeration);
                return (String[])((String[])list.toArray(new String[list.size()]));
            } catch (Exception var2) {
                throw new RuntimeException("枚举转换字符串数组出错", var2);
            }
        }
    }

    public static Object[][] toArrayOfArray(List list) throws RuntimeException {
        if (list == null) {
            return (Object[][])null;
        } else {
            try {
                int size = list.size();
                Object[][] arr = new Object[size][];

                for(int i = 0; i < size; ++i) {
                    arr[i] = toArray((List)list.get(i));
                }

                return arr;
            } catch (Exception var4) {
                throw new RuntimeException("集合转换二维对象数组出错", var4);
            }
        }
    }

    public static String toDelimitedString(Object[] arr, String delim, String prefix, String suffix) {
        if (isEmpty(arr)) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            int len = arr.length;

            for(int i = 0; i < len; ++i) {
                if (i > 0) {
                    sb.append(delim);
                }

                sb.append(prefix).append(arr[i]).append(suffix);
            }

            return sb.toString();
        }
    }

    public static String toDelimitedString(Object[] arr, String delim) {
        return toDelimitedString(arr, delim, "", "");
    }

    public static String toCommaDelimitedString(Object[] arr) {
        return toDelimitedString(arr, ",");
    }

    public static String toDelimitedString(Collection coll, String delim, String prefix, String suffix) {
        if (isEmpty(coll)) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            Iterator it = coll.iterator();

            while(it.hasNext()) {
                sb.append(prefix).append(it.next()).append(suffix);
                if (it.hasNext()) {
                    sb.append(delim);
                }
            }

            return sb.toString();
        }
    }

    public static String toDelimitedString(Collection coll, String delim) {
        return toDelimitedString(coll, delim, "", "");
    }

    public static String toCommaDelimitedString(Collection coll) {
        return toDelimitedString(coll, ",");
    }

    public static Object[] toObjectArray(Object source) {
        if (source instanceof Object[]) {
            return (Object[])((Object[])source);
        } else if (source == null) {
            return new Object[0];
        } else if (!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        } else {
            int length = Array.getLength(source);
            if (length == 0) {
                return new Object[0];
            } else {
                Class wrapperType = Array.get(source, 0).getClass();
                Object[] newArray = (Object[])((Object[])Array.newInstance(wrapperType, length));

                for(int i = 0; i < length; ++i) {
                    newArray[i] = Array.get(source, i);
                }

                return newArray;
            }
        }
    }

    public static List arrayToList(Object source) {
        return Arrays.asList(toObjectArray(source));
    }

    public static List toList(Object source) {
        if (source == null) {
            return null;
        } else {
            List lst = null;
            if (source instanceof Object[]) {
                lst = arrayToList(source);
            } else if (source instanceof Collection) {
                lst = new ArrayList();
                ((List)lst).addAll((Collection)source);
            } else {
                lst = tokenizeToList(source.toString(), ",");
            }

            return (List)lst;
        }
    }

    public static String getShortClassName(String className) {
        if (isEmpty(className)) {
            return "";
        } else {
            int lastDotIndex = className.lastIndexOf(46);
            int nameEndIndex = className.indexOf("$$");
            if (nameEndIndex == -1) {
                nameEndIndex = className.length();
            }

            String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
            shortName = shortName.replace('$', '.');
            return shortName;
        }
    }

    public static String getShortClassName(Class clazz) {
        return getShortClassName(clazz.getName());
    }

    public static byte[] decodeBase64(byte[] base64Data) {
        return Base64.getDecoder().decode(base64Data);
    }

    public static byte[] fromBase64String(String base64Str) {
        return decodeBase64(base64Str.getBytes());
    }

    public static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        int i = 0;

        for(int var4 = 0; i < l; ++i) {
            out[var4++] = DIGITS[(240 & data[i]) >>> 4];
            out[var4++] = DIGITS[15 & data[i]];
        }

        return out;
    }

    public static byte[] decodeHex(char[] data) {
        int len = data.length;
        if ((len & 1) != 0) {
            throw new RuntimeException("Odd number of characters.");
        } else {
            byte[] out = new byte[len >> 1];
            int i = 0;

            for(int j = 0; j < len; ++i) {
                int f = toHexDigit(data[j], j) << 4;
                ++j;
                f |= toHexDigit(data[j], j);
                ++j;
                out[i] = (byte)(f & 255);
            }

            return out;
        }
    }

    private static int toHexDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal charcter " + ch + " at index " + index);
        } else {
            return digit;
        }
    }

    public static String toHexString(byte[] data) {
        return new String(encodeHex(data));
    }

    public static byte[] fromHexString(String hexStr) {
        return hexStr == null ? null : decodeHex(hexStr.toCharArray());
    }

    public static String base64ToHexString(String base64Str) {
        return isEmpty(base64Str) ? "" : toHexString(fromBase64String(base64Str));
    }

    public static boolean isXmlChar(char ch) {
        if (ch < 256) {
            if ('\t' <= ch && ch <= '\n' || ch == '\r' || ' ' <= ch) {
                return true;
            }
        } else if (256 <= ch && ch <= '\ud7ff' || '\ue000' <= ch && ch <= '?' || 65536 <= ch && ch <= 1114111) {
            return true;
        }

        return false;
    }

    public static String encodeXml(String str) {
        if (str == null) {
            return "";
        } else {
            int len = str.length();
            if (len == 0) {
                return str;
            } else {
                StringBuilder encoded = new StringBuilder();

                for(int i = 0; i < len; ++i) {
                    char c = str.charAt(i);
                    if (c == '<') {
                        encoded.append("&lt;");
                    } else if (c == '"') {
                        encoded.append("&quot;");
                    } else if (c == '>') {
                        encoded.append("&gt;");
                    } else if (c == '\'') {
                        encoded.append("&apos;");
                    } else if (c == '&') {
                        encoded.append("&amp;");
                    } else if (isXmlChar(c)) {
                        encoded.append(c);
                    }
                }

                return encoded.toString();
            }
        }
    }

    public static String decodeXml(String str) {
        if (str == null) {
            return "";
        } else {
            int len = str.length();
            if (len == 0) {
                return str;
            } else if (str.indexOf("&") < 0) {
                return str;
            } else {
                String strTransString = "";
                String[] strMarkupCharArray = new String[]{"&", ">", "<", "\"", "’"};
                String[] strEntityCharArray = new String[]{"&amp;", "&gt;", "&lt;", "&quot;", "&apos;"};
                strTransString = str;

                for(int i = strMarkupCharArray.length - 1; i >= 0; --i) {
                    strTransString = strTransString.replaceAll(strEntityCharArray[i], strMarkupCharArray[i]);
                }

                return strTransString;
            }
        }
    }

    public static boolean hasLength(CharSequence str) {
        return str != null && str.length() > 0;
    }

    public static boolean hasLength(String str) {
        return hasLength((CharSequence)str);
    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        } else {
            int strLen = str.length();

            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence)str);
    }

    public static String trimWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        } else {
            StringBuffer buf = new StringBuffer(str);

            while(buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
                buf.deleteCharAt(0);
            }

            while(buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
                buf.deleteCharAt(buf.length() - 1);
            }

            return buf.toString();
        }
    }

    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        } else {
            StringBuffer buf = new StringBuffer(str);
            int index = 0;

            while(buf.length() > index) {
                if (Character.isWhitespace(buf.charAt(index))) {
                    buf.deleteCharAt(index);
                } else {
                    ++index;
                }
            }

            return buf.toString();
        }
    }

    public static String[] trimArrayElements(String[] array) {
        if (isEmpty((Object[])array)) {
            return new String[0];
        } else {
            String[] result = new String[array.length];

            for(int i = 0; i < array.length; ++i) {
                String element = array[i];
                result[i] = element == null ? null : element.trim();
            }

            return result;
        }
    }

    public static String[] removeDuplicateStrings(String[] array) throws RuntimeException {
        if (isEmpty((Object[])array)) {
            return array;
        } else {
            Set set = new TreeSet();

            for(int i = 0; i < array.length; ++i) {
                set.add(array[i]);
            }

            return toStringArray((Collection)set);
        }
    }

    public static List tokenizeToList(String str, String delimiters) throws RuntimeException {
        return tokenizeToList(str, delimiters, true, true);
    }

    public static List tokenizeToList(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) throws RuntimeException {
        if (str == null) {
            return new ArrayList();
        } else {
            StringTokenizer st = new StringTokenizer(str, delimiters);
            ArrayList tokens = new ArrayList();

            while(true) {
                String token;
                do {
                    if (!st.hasMoreTokens()) {
                        return tokens;
                    }

                    token = st.nextToken();
                    if (trimTokens) {
                        token = token.trim();
                    }
                } while(ignoreEmptyTokens && token.length() <= 0);

                tokens.add(token);
            }
        }
    }

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        if (str != null && prefix != null) {
            if (str.startsWith(prefix)) {
                return true;
            } else if (str.length() < prefix.length()) {
                return false;
            } else {
                String lcStr = str.substring(0, prefix.length()).toLowerCase();
                String lcPrefix = prefix.toLowerCase();
                return lcStr.equals(lcPrefix);
            }
        } else {
            return false;
        }
    }

    public static boolean endsWithIgnoreCase(String str, String suffix) {
        if (str != null && suffix != null) {
            if (str.endsWith(suffix)) {
                return true;
            } else if (str.length() < suffix.length()) {
                return false;
            } else {
                String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
                String lcSuffix = suffix.toLowerCase();
                return lcStr.equals(lcSuffix);
            }
        } else {
            return false;
        }
    }

    public static boolean fileExists(String fileName) throws RuntimeException {
        try {
            File file = new File(fileName);
            return file.exists();
        } catch (Exception var2) {
            throw new RuntimeException("检查文件" + fileName + "存在出错", var2);
        }
    }

    public static boolean deleteFile(String fileName) throws RuntimeException {
        try {
            File file = new File(fileName);
            return file.delete();
        } catch (Exception var2) {
            throw new RuntimeException("删除文件" + fileName + "出错", var2);
        }
    }

    public static String join(Object[] array) {
        return join((Object[])array, (String)null);
    }

    public static String join(Object[] array, String separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            if (separator == null) {
                separator = "";
            }

            int bufSize = endIndex - startIndex;
            if (bufSize <= 0) {
                return "";
            } else {
                bufSize *= (array[startIndex] != null ? array[startIndex].toString().length() : 16) + separator.length();
                StringBuffer buf = new StringBuffer(bufSize);

                for(int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    if (array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(Iterator iterator, String separator) {
        if (iterator == null) {
            return null;
        } else if (!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if (!iterator.hasNext()) {
                return first != null ? first.toString() : "";
            } else {
                StringBuffer buf = new StringBuffer(256);
                if (first != null) {
                    buf.append(first);
                }

                while(iterator.hasNext()) {
                    if (separator != null) {
                        buf.append(separator);
                    }

                    Object obj = iterator.next();
                    if (obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String join(Collection collection, String separator) {
        return collection == null ? null : join(collection.iterator(), separator);
    }

    public static String joinList(List lst, String colName, String spearator) {
        if (lst == null) {
            return null;
        } else {
            Set set = new LinkedHashSet();
            String str = null;

            for(int i = 0; i < lst.size(); ++i) {
                str = getString((Map)lst.get(i), colName);
                if (str != null && str.length() > 0) {
                    set.add(str);
                }
            }

            return join((Collection)set, spearator);
        }
    }

    private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
        if (str == null) {
            return null;
        } else {
            int len = str.length();
            if (len == 0) {
                return new String[0];
            } else {
                List list = new ArrayList();
                int sizePlus1 = 1;
                int i = 0;
                int start = 0;
                boolean match = false;
                boolean lastMatch = false;
                if (separatorChars != null) {
                    if (separatorChars.length() != 1) {
                        label87:
                        while(true) {
                            while(true) {
                                if (i >= len) {
                                    break label87;
                                }

                                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                                    if (match || preserveAllTokens) {
                                        lastMatch = true;
                                        if (sizePlus1++ == max) {
                                            i = len;
                                            lastMatch = false;
                                        }

                                        list.add(str.substring(start, i));
                                        match = false;
                                    }

                                    ++i;
                                    start = i;
                                } else {
                                    lastMatch = false;
                                    match = true;
                                    ++i;
                                }
                            }
                        }
                    } else {
                        char sep = separatorChars.charAt(0);

                        label71:
                        while(true) {
                            while(true) {
                                if (i >= len) {
                                    break label71;
                                }

                                if (str.charAt(i) == sep) {
                                    if (match || preserveAllTokens) {
                                        lastMatch = true;
                                        if (sizePlus1++ == max) {
                                            i = len;
                                            lastMatch = false;
                                        }

                                        list.add(str.substring(start, i));
                                        match = false;
                                    }

                                    ++i;
                                    start = i;
                                } else {
                                    lastMatch = false;
                                    match = true;
                                    ++i;
                                }
                            }
                        }
                    }
                } else {
                    label103:
                    while(true) {
                        while(true) {
                            if (i >= len) {
                                break label103;
                            }

                            if (Character.isWhitespace(str.charAt(i))) {
                                if (match || preserveAllTokens) {
                                    lastMatch = true;
                                    if (sizePlus1++ == max) {
                                        i = len;
                                        lastMatch = false;
                                    }

                                    list.add(str.substring(start, i));
                                    match = false;
                                }

                                ++i;
                                start = i;
                            } else {
                                lastMatch = false;
                                match = true;
                                ++i;
                            }
                        }
                    }
                }

                if (match || preserveAllTokens && lastMatch) {
                    list.add(str.substring(start, i));
                }

                return (String[])((String[])list.toArray(new String[list.size()]));
            }
        }
    }

    public static String[] split(String str) {
        return split(str, (String)null, -1);
    }

    public static String[] split(String str, String separatorChars) {
        return splitWorker(str, separatorChars, -1, false);
    }

    public static String[] split(String str, String separatorChars, int max) {
        return splitWorker(str, separatorChars, max, false);
    }

    public static String left(String str, int len) {
        if (str == null) {
            return null;
        } else if (len < 0) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(0, len);
        }
    }

    public static String mid(String str, int pos, int len) {
        if (str == null) {
            return null;
        } else if (len >= 0 && pos <= str.length()) {
            if (pos < 0) {
                pos = 0;
            }

            return str.length() <= pos + len ? str.substring(pos) : str.substring(pos, pos + len);
        } else {
            return "";
        }
    }

    public static String right(String str, int len) {
        if (str == null) {
            return null;
        } else if (len < 0) {
            return "";
        } else {
            return str.length() <= len ? str : str.substring(str.length() - len);
        }
    }

    private static String padding(int repeat, char padChar) {
        if (repeat < 0) {
            return null;
        } else {
            char[] buf = new char[repeat];

            for(int i = 0; i < buf.length; ++i) {
                buf[i] = padChar;
            }

            return new String(buf);
        }
    }

    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        } else {
            int pads = size - str.length();
            if (pads <= 0) {
                return str;
            } else {
                return pads > 8192 ? leftPad(str, size, String.valueOf(padChar)) : padding(pads, padChar).concat(str);
            }
        }
    }

    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        } else {
            if (isEmpty(padStr)) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str;
            } else if (padLen == 1 && pads <= 8192) {
                return leftPad(str, size, padStr.charAt(0));
            } else if (pads == padLen) {
                return padStr.concat(str);
            } else if (pads < padLen) {
                return padStr.substring(0, pads).concat(str);
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for(int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return (new String(padding)).concat(str);
            }
        }
    }

    public static String rightPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        } else {
            int pads = size - str.length();
            if (pads <= 0) {
                return str;
            } else {
                return pads > 8192 ? rightPad(str, size, String.valueOf(padChar)) : str.concat(padding(pads, padChar));
            }
        }
    }

    public static String rightPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        } else {
            if (isEmpty(padStr)) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str;
            } else if (padLen == 1 && pads <= 8192) {
                return rightPad(str, size, padStr.charAt(0));
            } else if (pads == padLen) {
                return str.concat(padStr);
            } else if (pads < padLen) {
                return str.concat(padStr.substring(0, pads));
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for(int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return str.concat(new String(padding));
            }
        }
    }

    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        if (!isEmpty(text) && !isEmpty(searchString) && replacement != null && max != 0) {
            int start = 0;
            int end = text.indexOf(searchString, start);
            if (end == -1) {
                return text;
            } else {
                int replLength = searchString.length();
                int increase = replacement.length() - replLength;
                increase = increase >= 0 ? increase : 0;
                increase *= max >= 0 ? (max <= 64 ? max : 64) : 16;

                StringBuffer buf;
                for(buf = new StringBuffer(text.length() + increase); end != -1; end = text.indexOf(searchString, start)) {
                    buf.append(text.substring(start, end)).append(replacement);
                    start = end + replLength;
                    --max;
                    if (max == 0) {
                        break;
                    }
                }

                buf.append(text.substring(start));
                return buf.toString();
            }
        } else {
            return text;
        }
    }

    public static String arrayToString(Object[] a, String separator) {
        StringBuffer result = new StringBuffer();
        String split = separator != null ? separator : "";
        if (a != null && a.length > 0) {
            result.append(a[0]);

            for(int i = 1; i < a.length; ++i) {
                result.append(split);
                result.append(a[i]);
            }
        }

        return result.toString();
    }

    public static String toUpperCase(String str) {
        return str != null ? str.toUpperCase() : null;
    }

    public static String toLowerCase(String str) {
        return str != null ? str.toLowerCase() : null;
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String sud = uuid.toString();
        sud = sud.replaceAll("-", "");
        return sud;
    }

    public static List buildPatternsList(String patterns) {
        if (patterns != null && patterns.trim().length() != 0) {
            List list = new ArrayList();
            String[] tokens = patterns.split(",");
            String[] as = tokens;
            int j = tokens.length;

            for(int i = 0; i < j; ++i) {
                String token = as[i];

                try {
                    list.add(Pattern.compile(token.trim()));
                } catch (Exception var8) {
                    var8.printStackTrace();
                }
            }

            return list;
        } else {
            return null;
        }
    }

    public static int stringToInt(String str) {
        return stringToInt(str, 0);
    }

    public static int stringToInt(String str, int defaultValue) {
        try {
            if (str == null || str.length() == 0) {
                return defaultValue;
            }
        } catch (NumberFormatException var3) {
            return defaultValue;
        }

        return Integer.parseInt(str);
    }

    public static Integer stringToInteger(String str) {
        return stringToInteger(str, 0);
    }

    public static Integer stringToInteger(String str, int defaultValue) {
        return stringToInt(str, defaultValue);
    }

    public static boolean isDigits(String str) {
        if (str != null && str.length() != 0) {
            for(int i = 0; i < str.length(); ++i) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumber(String str) {
        if (isEmpty(str)) {
            return false;
        } else {
            char[] chars = str.toCharArray();
            int sz = chars.length;
            boolean hasExp = false;
            boolean hasDecPoint = false;
            boolean allowSigns = false;
            boolean foundDigit = false;
            int start = chars[0] != '-' ? 0 : 1;
            int i;
            if (sz > start + 1 && chars[start] == '0' && chars[start + 1] == 'x') {
                i = start + 2;
                if (i == sz) {
                    return false;
                } else {
                    while(i < chars.length) {
                        if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f') && (chars[i] < 'A' || chars[i] > 'F')) {
                            return false;
                        }

                        ++i;
                    }

                    return true;
                }
            } else {
                --sz;

                for(i = start; i < sz || i < sz + 1 && allowSigns && !foundDigit; ++i) {
                    if (chars[i] >= '0' && chars[i] <= '9') {
                        foundDigit = true;
                        allowSigns = false;
                    } else if (chars[i] == '.') {
                        if (hasDecPoint || hasExp) {
                            return false;
                        }

                        hasDecPoint = true;
                    } else if (chars[i] != 'e' && chars[i] != 'E') {
                        if (chars[i] != '+' && chars[i] != '-') {
                            return false;
                        }

                        if (!allowSigns) {
                            return false;
                        }

                        allowSigns = false;
                        foundDigit = false;
                    } else {
                        if (hasExp) {
                            return false;
                        }

                        if (!foundDigit) {
                            return false;
                        }

                        hasExp = true;
                        allowSigns = true;
                    }
                }

                if (i < chars.length) {
                    if (chars[i] >= '0' && chars[i] <= '9') {
                        return true;
                    } else if (chars[i] != 'e' && chars[i] != 'E') {
                        if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
                            return foundDigit;
                        } else if (chars[i] != 'l' && chars[i] != 'L') {
                            return false;
                        } else {
                            return foundDigit && !hasExp;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return !allowSigns && foundDigit;
                }
            }
        }
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static boolean isChinese(String strName) {
        if (strName == null) {
            return false;
        } else {
            char[] ch = strName.toCharArray();

            for(int i = 0; i < ch.length; ++i) {
                char c = ch[i];
                if (isChinese(c)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static String getEncoding(String str) {
        String encode = "GBK";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var7) {
        }

        encode = "ISO-8859-1";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var6) {
        }

        encode = "UTF-8";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var5) {
        }

        encode = "GB18030";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var4) {
        }

        encode = "GB2312";

        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception var3) {
        }

        return "";
    }

    public static String trimSqlParam(Object obj) {
        if (obj == null) {
            return null;
        } else {
            String str = obj.toString().trim();
            if (!hasLength(str)) {
                return str;
            } else {
                str = escapeSqlParam(str);
                return replace(str, "%", "");
            }
        }
    }

    public static String escapeSqlParam(String str) {
        return str == null ? null : replace(str, "'", "''");
    }

    public static String[] splitSqlParam(Object obj) {
        if (obj == null) {
            return null;
        } else {
            String str = obj.toString().trim();
            if (!hasLength(str)) {
                return null;
            } else {
                List lst = tokenizeToList(str, " ");
                Set set = new LinkedHashSet(lst.size());
                Iterator iterator = lst.iterator();

                while(iterator.hasNext()) {
                    String s = (String)iterator.next();
                    set.add(escapeSqlParam(s));
                }

                return (String[])((String[])set.toArray(new String[set.size()]));
            }
        }
    }

    public static String wrapSqlParam(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof String[]) {
            return toDelimitedString((Object[])((String[])((String[])obj)), ",", "'", "'");
        } else if (obj instanceof Collection) {
            return toDelimitedString((Collection)obj, ",", "'", "'");
        } else {
            String str = obj.toString().trim();
            if (!hasLength(str)) {
                return null;
            } else {
                List lst = tokenizeToList(str, ",");
                return toDelimitedString((Collection)lst, ",", "'", "'");
            }
        }
    }

    public static boolean compareCharset(String cs1, String cs2) {
        if (cs1 != null && cs2 != null) {
            String s1 = cs1.toUpperCase();
            String s2 = cs2.toUpperCase();
            return s1.length() >= 3 && s2.length() >= 3 && s1.startsWith("GB") && s2.startsWith("GB") ? true : s1.equals(s2);
        } else {
            return false;
        }
    }


    public static void isEmptyErr(Object[] objs, String[] errMsg) {
        for (int i = 0; i < objs.length; i++) {
            Object obj = objs[i];
            if (isEmpty(obj)) {
                throw new AppException(errMsg[i]);
            }
        }
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof String) {
            String str = (String) obj;
            return str == null || str.length() == 0;
        } else if (obj instanceof List) {
            List list = (List) obj;
            return list == null || list.isEmpty();
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            return map == null || map.isEmpty();
        } else if (obj instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) obj;
            return bd == null;
        } else if (obj instanceof Long) {
            Long bd = (Long) obj;
            return bd == null;
        }

        return obj == null;
    }
}
