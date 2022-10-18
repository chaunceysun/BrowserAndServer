package test;

/**
 * 二叉查找算法
 *
 * @author Chauncey Sun
 * @create 2022/10/18 22:48
 */
public class Test {
    public static void main(String[] args) {
        int[] nums = {-1, 0, 3, 5, 9, 12};
        int target1 = 9;
        int target2 = 2;
        int res1 = search(nums, target1);
        int res2 = search(nums, target2);
        System.out.println(res1);
        System.out.println(res2);
    }

    public static int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int num = nums[mid];
            if (num == target) {
                return mid;
            } else if (num > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
