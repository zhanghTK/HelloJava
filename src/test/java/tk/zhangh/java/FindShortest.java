package tk.zhangh.java;

/**
 * Created by ZhangHao on 2016/9/14.
 */
public class FindShortest {
    public static void main(String[] args) {
        int arr[] = new int[] {1,5,3,4,2,6,7};

        System.out.println(findShortest(arr));
    }

    public static int findShortest(int[] arr){
        if(arr==null||arr.length<2){
            return 0;
        }
        int min=arr[arr.length-1];
        int noMinIndex=-1;
        for(int i=arr.length-2;i!=-1;i--){
            if(arr[i]>min){
                noMinIndex=i;
            }else{
                min=Math.min(min,arr[i]);
            }
        }
        if(noMinIndex==-1){
            return 0;
        }
        int max=arr[0];
        int noMaxIndex=-1;
        for(int i=1;i!=arr.length;i++){
            if(arr[i]<max){
                noMaxIndex=i;
            }else{
                max=Math.max(max,arr[i]);
            }
        }
        return noMaxIndex-noMinIndex+1;
    }
}
