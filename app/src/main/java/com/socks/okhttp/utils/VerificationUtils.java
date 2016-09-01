/*
 *   Copyright (C)  2016 android@19code.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.socks.okhttp.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * 验证工具类
 */
public class VerificationUtils {

    /**
     * 判断姓名格式
     * 真实姓名可以是汉字,也可以是字母,但是不能两者都有,也不能包含任何符号和数字
     * 1.如果是英文名,可以允许英文名字中出现空格
     * 2.英文名的空格可以是多个,但是不能连续出现多个
     * 3.汉字不能出现空格
     *
     * @param value
     * @return
     */
    public static boolean matcherRealName(String value) {
        String regex = "^([\\u4e00-\\u9fa5]+|([a-zA-Z]+\\s?)+)$";
        return testRegex(regex, value);
    }

    /**
     * 判断手机号格式 (匹配11数字,并且13-19开头)
     *
     * @param value
     * @return
     */
    public static boolean matcherPhoneNum(String value) {
        String regex = "^(\\+?\\d{2}-?)?(1[0-9])\\d{9}$";
        return testRegex(regex, value);
    }

    /**
     * 判断账号格式 (4-20位字符)
     *
     * @param value
     * @return
     */
    public static boolean matcherAccount(String value) {
        String regex = "[\\u4e00-\\u9fa5a-zA-Z0-9\\-]{4,20}";
        return testRegex(regex, value);
    }

    /**
     * 判断密码格式 (6-12位字母或数字)
     *
     * @param value
     * @return
     */
    public static boolean matcherPassword(String value) {
        String regex = "^[a-zA-Z0-9]{6,12}$";
        return testRegex(regex, value);
    }

    /**
     * 判断密码格式 (6-12位字母或数字,必须同时包含字母和数字)
     *
     * @param value
     * @return
     */
    public static boolean matcherPassword2(String value) {
        String regex = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}";
        return testRegex(regex, value);
    }

    /**
     * 判断邮箱格式
     *
     * @param value
     * @return
     */
    public static boolean matcherEmail(String value) {
        String regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+" +
                "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+" +
                "[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
        return testRegex(regex, value);
    }

    /**
     * 判断IP地址
     * @param value
     * @return
     */
    public static boolean matcherIP(String value) {
        String regex = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\" +
                "d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\" +
                "d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
        return testRegex(regex, value.toLowerCase());
    }

    /**
     * 判断URL (http,https,ftp)
     * @param value
     * @return
     */
    public static boolean matcherUrl(String value) {
        String regex = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[\\w-]+\\.\\w{2,4}(\\/.*)?$";
        return testRegex(regex, value.toLowerCase());
    }


    /**
     * 判断身份证号码格式
     * @param value
     * @return
     */
    public static boolean matcherIdentityCard(String value) {
        IDCardTester idCardTester = new IDCardTester();
        return idCardTester.test(value);
    }

    private static class IDCardTester {
        public boolean test(String content) {
            if (TextUtils.isEmpty(content)) {
                return false;
            }
            final int length = content.length();
            if (15 == length) {
                try {
                    return isOldCNIDCard(content);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return false;
                }
            } else if (18 == length) {
                return isNewCNIDCard(content);
            } else {
                return false;
            }
        }

        final int[] WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

        final char[] VALID = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

        public boolean isNewCNIDCard(String numbers) {
            numbers = numbers.toUpperCase();
            int sum = 0;
            for (int i = 0; i < WEIGHT.length; i++) {
                final int cell = Character.getNumericValue(numbers.charAt(i));
                sum += WEIGHT[i] * cell;
            }
            int index = sum % 11;
            return VALID[index] == numbers.charAt(17);
        }

        public boolean isOldCNIDCard(String numbers) {
            String yymmdd = numbers.substring(6, 11);
            boolean aPass = numbers.equals(String.valueOf(Long.parseLong(numbers)));
            boolean yPass = true;
            try {
                new SimpleDateFormat("yyMMdd").parse(yymmdd);
            } catch (Exception e) {
                e.printStackTrace();
                yPass = false;
            }
            return aPass && yPass;
        }
    }

    /**
     * 是否数值型
     * @param input
     * @return
     */
    public static boolean isNumeric(String input) {
        if (TextUtils.isEmpty(input)) {
            return false;
        }
        char[] chars = input.toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;
        int start = (chars[0] == '-' || chars[0] == '+') ? 1 : 0;
        if (sz > start + 1) {
            if (chars[start] == '0' && chars[start + 1] == 'x') {
                int i = start + 2;
                if (i == sz) {
                    return false;
                }
                for (; i < chars.length; i++) {
                    if ((chars[i] < '0' || chars[i] > '9')
                            && (chars[i] < 'a' || chars[i] > 'f')
                            && (chars[i] < 'A' || chars[i] > 'F')) {
                        return false;
                    }
                }
                return true;
            }
        }
        sz--;
        int i = start;
        while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                foundDigit = true;
                allowSigns = false;

            } else if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    return false;
                }
                hasDecPoint = true;
            } else if (chars[i] == 'e' || chars[i] == 'E') {
                if (hasExp) {
                    return false;
                }
                if (!foundDigit) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if (chars[i] == '+' || chars[i] == '-') {
                if (!allowSigns) {
                    return false;
                }
                allowSigns = false;
                foundDigit = false;
            } else {
                return false;
            }
            i++;
        }
        if (i < chars.length) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                return true;
            }
            if (chars[i] == 'e' || chars[i] == 'E') {
                return false;
            }
            if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
                return foundDigit;
            }
            if (chars[i] == 'l' || chars[i] == 'L') {
                return foundDigit && !hasExp;
            }
            return false;
        }
        return !allowSigns && foundDigit;
    }

    /**
     * 是否匹配正则
     * @param regex
     * @param inputValue
     * @return
     */
    public static boolean testRegex(String regex, String inputValue) {
        return Pattern.compile(regex).matcher(inputValue).matches();
    }

    /**
     *  匹配中国邮政编码
     * @param postcode
     * @return
     */
    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }
}
