package com.qyc.design.structure.array;

/**
 * 使用动态数组可以方便地实现插入和访问元素的功能，而且不需要手动管理数组的大小，减少了代码的复杂性。
 */
public class DynamicArray {
    private int[] array; // 声明一个int类型的数组
    private int size; // 当前数组的大小

    public DynamicArray() {
        this.size = 10; // 初始化数组的大小为10
        this.array = new int[size]; // 初始化数组
    }

    public void insert(int element) {
        if (size == array.length) { // 如果数组已满
            int[] newArray = new int[size * 2]; // 扩展数组的大小
            System.arraycopy(array, 0, newArray, 0, size); // 将原数组的元素复制到新数组中
            array = newArray; // 更新数组的引用
            size *= 2; // 更新数组的大小
        }
        array[size] = element; // 在数组末尾添加新元素
        size++; // 更新数组的大小
    }

    public int get(int index) {
        if (index < 0 || index >= size) { // 如果索引越界
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size); // 抛出异常
        }
        return array[index]; // 返回指定索引位置的元素
    }
}
