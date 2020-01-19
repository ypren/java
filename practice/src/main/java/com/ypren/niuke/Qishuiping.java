package com.ypren.niuke;

/**
 * 有这样一道智力题：“某商店规定：三个空汽水瓶可以换一瓶汽水。小张手上有十个空汽水瓶，她最多可以换多少瓶汽水喝？”答案是5瓶，
 * 方法如下：先用9个空瓶子换3瓶汽水，喝掉3瓶满的，喝完以后4个空瓶子，用3个再换一瓶，喝掉这瓶满的，这时候剩2个空瓶子。
 * 然后你让老板先借给你一瓶汽水，喝掉这瓶满的，喝完以后用3个空瓶子换一瓶满的还给老板。如果小张手上有n个空汽水瓶，最多可以换多少瓶汽水喝？
 */
public class Qishuiping {
    public static void main(String[] args) {
        int original = 81;
        System.out.println(calMaxDrinkCount(original));
    }

    public static DrinkInfo  calMaxDrinkCount(int original) {
        DrinkInfo drinkInfo = new Qishuiping().new DrinkInfo(original);
        while (drinkInfo.drink()) {
            drinkInfo.swap();
        }
        return drinkInfo;
    }

    class DrinkInfo {
        public int full;
        public int empty;
        public int total;
        public int swap;

        public DrinkInfo(int full) {
            this.full = full;
            this.empty = 0;
            this.total = 0;
        }

        public boolean drink() {
            if (this.full == 0) {
                return false;
            }
            this.total = this.total + this.full;
            this.empty = this.empty + this.full;
            this.full = 0;
            return true;
        }

        public void swap() {
            this.full = this.empty / 3;
            this.swap = this.swap + this.full;
            this.empty = this.empty % 3;
            if (full == 0 && this.empty == 2) {
                this.full = this.full + 1;
                this.swap += 1;
                this.empty = 0;
            }
        }

        @Override
        public String toString() {
            return "DrinkInfo{" +
                    "full=" + full +
                    ", empty=" + empty +
                    ", total=" + total +
                    ", swap=" + swap +
                    '}';
        }
    }
}
