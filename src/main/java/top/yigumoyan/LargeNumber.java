package top.yigumoyan;

import java.util.Arrays;

public class LargeNumber {
    private int[] number;

    private boolean negative = false;

    public boolean isNegative() {
        return negative;
    }

    public void setNegative(boolean negative) {
        this.negative = negative;
    }

    public LargeNumber(String numberStr) {
        this.number = convertStringToDigits(numberStr);
    }

    public LargeNumber(int[] number, boolean negative) {
        this.number = number;
        this.negative = negative;
    }

    private int[] convertStringToDigits(String numberStr) {
        if (numberStr.charAt(0) == '-') {
            this.negative = true;
            numberStr = numberStr.substring(1);
        }
        int[] digits = new int[numberStr.length()];
        for (int i = 0; i < numberStr.length(); i++) {
            digits[i] = Character.getNumericValue(numberStr.charAt(i));
        }
        return digits;
    }

    private String convertDigitsToString() {
        StringBuilder result = new StringBuilder();

        boolean firstNonZero = false;
        for (int num : this.number) {
            if (firstNonZero) {
                result.append(num);
            } else if (num != 0) {
                result.append(num);
                firstNonZero = true;
            }
        }
        return result.toString();
    }

    public LargeNumber add(LargeNumber other) {
        if (this.negative && !other.isNegative()) {
            this.negative = false;
            return other.subtract(this);
        } else if (!this.negative && other.isNegative()) {
            other.setNegative(false);
            return this.subtract(other);
        } else if (this.negative && other.isNegative()) {
            this.negative = false;
            other.setNegative(false);
            LargeNumber result = this.add(other);
            result.negative = true;
            return result;
        }

        int maxLength = Math.max(this.number.length, other.number.length);
        int[] result = new int[maxLength + 1];
        int carry = 0;

        for (int i = 0; i < maxLength; i++) {
            int sum = carry;
            if (i < this.number.length) {
                sum += this.number[this.number.length - 1 - i];
            }
            if (i < other.number.length) {
                sum += other.number[other.number.length - 1 - i];
            }

            result[result.length - 1 - i] = sum % 10;
            carry = sum / 10;
        }

        result[0] = carry;

        return new LargeNumber(result, false);
    }

    public LargeNumber subtract(LargeNumber other) {
        if (this.negative && !other.isNegative()) {
            this.negative = false;
            LargeNumber result = this.add(other);
            result.negative = true;
            return result;
        } else if (!this.negative && other.isNegative()) {
            other.setNegative(false);
            return this.add(other);
        } else if (this.negative && other.isNegative()) {
            this.negative = false;
            other.setNegative(false);
            return this.subtract(other);
        } else if (!this.compare(other)) {
            LargeNumber result = other.subtract(this);
            result.setNegative(true);
            return result;
        }


        int maxLength = Math.max(this.number.length, other.number.length);
        int[] result = new int[maxLength];
        int borrow = 0;

        for (int i = 0; i < maxLength; i++) {
            int diff = borrow;
            if (i < this.number.length) {
                diff += this.number[this.number.length - 1 - i];
            }
            if (i < other.number.length) {
                diff -= other.number[other.number.length - 1 - i];
            }

            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            result[result.length - 1 - i] = diff;
        }

        int nonZeroIndex = 0;
        while (nonZeroIndex < result.length - 1 && result[nonZeroIndex] == 0) {
            nonZeroIndex++;
        }

        return new LargeNumber(Arrays.copyOfRange(result, nonZeroIndex, result.length), true);
    }

    private boolean compare(LargeNumber largeNumber) {
        if (this.number.length > largeNumber.number.length) {
            return true;
        } else if (this.number.length < largeNumber.number.length) {
            return false;
        } else {
            for (int i = 0; i < this.number.length; i++) {
                if (this.number[i] > largeNumber.number[i]) {
                    return true;
                } else if (this.number[i] < largeNumber.number[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        if (this.negative) {
            return "-" + this.convertDigitsToString();
        }
        return this.convertDigitsToString();
    }
}
