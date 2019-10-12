package com.ssq.sortsmethod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

/**
 * https://www.cnblogs.com/yuandluck/p/9474751.html
 */
public class MainActivity extends AppCompatActivity {

    private TextView textView1, textView2, textView3, textView4;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        sortArr();
    }

    private void sortArr() {
//        int[] num = {3, 45, 78, 64, 52, 11, 64, 55, 99, 11, 18};
        int[] num = {6, 1, 2, 7, 9, 11, 4, 5, 10, 8};
//        int[] num = {3, 2, 4, 1, 5, 0};
        textView1.setText(arrayToString(num, "未排序"));
//        bubbleSort(num);
//        quickSort(num, 0, num.length - 1);
//        insertSort(num);
        selectSort(num);
        textView2.setText(arrayToString(num, "排序"));
        textView3.setText("数组个数：" + num.length);
        textView4.setText("循环次数：" + count);
    }

    /**
     * 将一个int类型数组转化为字符串
     *
     * @param arr
     * @param flag
     * @return
     */
    private String arrayToString(int[] arr, String flag) {
        String str = "数组为（" + flag + "）：";
        for (int a : arr) {
            str += a + "\t";
        }
        return str;
    }

    /**
     * 冒泡排序
     * 每次将最大的排到数组末尾
     * 时间复杂度：n² 空间复杂度：1
     * <p>
     * 五.冒泡排序的时间复杂度
     * 冒泡排序是一种用时间换空间的排序方法，最坏情况是把顺序的排列变成逆序，或者把逆序的数列变成顺序。在这种情况下，每一次比较都需要进行交换运算。举个例子来说，一个数列 5 4 3 2 1 进行冒泡升序排列
     * <p>
     * 第一轮的两两比较，需要比较4次；得到 4 3 2 1 5
     * 第二轮的两两比较，需要比较3次；得到 3 2 1 4 5
     * 第三轮的两两比较，需要比较2次；得到 2 1 3 4 5
     * 第四轮的两两比较，需要比较1次；得到 1 2 3 4 5
     * <p>
     * 所以总的比较次数为 4 + 3 + 2 + 1 = 10次
     * 对于n位的数列则有比较次数为 (n-1) + (n-2) + ... + 1 = n * (n - 1) / 2，这就得到了最大的比较次数。
     * 而O(N^2)表示的是复杂度的数量级。举个例子来说，如果n = 10000，那么 n(n-1)/2 = (n^2 - n) / 2 = (100000000 - 10000) / 2，相对10^8来说，10000小的可以忽略不计了，所以总计算次数约为0.5 * N^2。用O(N^2)就表示了其数量级（忽略前面系数0.5）。
     * <p>
     * 综上所述，冒泡排序的时间复杂度为：O(n²)
     */
    private void bubbleSort(int[] arr) {
        int temp;
        int lenth = arr.length;
        //外层循环，是需要进行比较的轮数，一共进行5次即可
        for (int i = 0; i < lenth - 1; i++) {
            //内层循环，是每一轮中进行的两两比较
            for (int j = 0; j < lenth - 1; j++) { //优化：j<lenth-i-1  最后一个肯定是最大的了，没必要再比较了
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    count++;
                }
            }

        }

    }


    /**
     * 快速排序（取基准值 使左侧比其小右侧比其大 递归
     * 时间复杂度 n㏒2n 空间复杂度 n㏒2n）
     *
     * @param num   排序的数组
     * @param left  数组的前针
     * @param right 数组后针
     */
    private void quickSort(int num[], int left, int right) {
        //如果left等于right，即数组只有一个元素，直接返回
        if (left >= right) {
            return;
        }
        //设置最左边的元素为基准值
        int key = num[left];
        int temp;// 记录临时中间值
        //数组中比key小的放在左边，比key大的放在右边，key值下标为i
        int i = left;
        int j = right;
        while (i < j) {
            //j向左移，直到遇到比key小的值
            while (key <= num[j] && i < j) {
                j--;
            }
            //i向右移，直到遇到比key大的值
            while (key >= num[i] && i < j) {
                i++;
            }
            //i和j指向的元素交换
            if (i < j) {
                temp = num[i];
                num[i] = num[j];
                num[j] = temp;
            }
        }

        count++;
        //最后将基准为与i和j相等位置的数字交换
        num[left] = num[i];
        num[i] = key;
        //递归调用左半数组
        quickSort(num, left, j - 1);
        //递归调用右半数组
        quickSort(num, j + 1, right);

    }

    /**
     * 选择排序 每次选一个最小的放到数组最前面
     * 时间复杂度：n² 空间复杂度：1
     */
    private void selectSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = i; //最小值索引
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[min]) min = j;
            }
            int temp = array[i];//交换最小值与array[i]
            array[i] = array[min];
            array[min] = temp;
        }
    }


    /**
     * 插入排序 扑克牌排序
     * 时间复杂度：n² 空间复杂度：1
     */
    private void insertSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int insert = arr[i];
            int j = i;
            while (j >= 1 && arr[j - 1] > insert) {
                arr[j] = arr[j - 1];
                j--;
                count++;
            }
            arr[j] = insert;
        }

    }


    /**
     * 归并排序 分成有序子列（长度为1认为有序）再合并 分阶段
     * 时间复杂度： nlog2n 空间复杂度 n
     */
    private void mergeSort(int[] a, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(a, low, mid);
            mergeSort(a, mid + 1, high);
            merge(a, low, mid, high);
        }
    }

    /*  合并阶段  */
    private void merge(int[] a, int low, int mid, int high) {
        //申请一个合并后的空间
        int[] temp = new int[high - low + 1];
        // i/j: 两个待合并数组的指针 t: 合并后数组指针
        int i = low, j = mid + 1, t = 0;
        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[t++] = a[i++];
            } else {
                temp[t++] = a[j++];
            }
        }
        //肯定只会走一个while循环：若其中一个数组还有未加入合并数组的 则直接加入
        while (i <= mid) {
            temp[t++] = a[i++];
        }
        while (j <= high) {
            temp[t++] = a[j++];
        }
        //将临时数组里的数据复制到原数组 注意指针是k+low
        for (int k = 0; k < temp.length; k++) {
            a[k + low] = temp[k];
        }
    }

    /**
     * 希尔排序
     * 时间复杂度 n2 空间复杂度 1
     */
    private void shellSort(int[] a) {
        int d = a.length;
        while (d > 0) {
            for (int i = d; i < a.length; i++) {
                int j = i;
                int temp = a[j];
                //应拿当前操作数进行比较
                while (j >= d && temp < a[j - d]) {
                    a[j] = a[j - d];
                    j -= d;
                }
                a[j] = temp;
            }
            d /= 2;
        }
    }

}
