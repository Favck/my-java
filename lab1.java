// Вариант 6
import java.util.ArrayList;
import java.util.LinkedList;
import  java.util.Scanner;
import java.util.List;

public class lab1 {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        float[] array1 = new float[3];
        float[] array2 = new float[4];
        float[] array3 = new float[5];
        
        System.out.println("========Часть 1========");
        System.out.println("array1 (3 values):");
        read_int(array1, scanner);
        System.out.println("array2 (4 values):");
        read_int(array2, scanner);
        System.out.println("array3 (5 values):");
        read_int(array3, scanner);
        
        System.out.println("================");
        System.out.println("1 - 2:");
        calculate_D(array1, array2);

        System.out.println("1 - 3:");
        calculate_D(array1, array3);

        System.out.println("2 - 3 :" );
        calculate_D(array2, array3);

        System.out.println("========Часть 2========");
        ArrayList<Float> arrayList = new ArrayList<>();

        LinkedList<Float> linkedList = new LinkedList<>();

        read_dynamic(linkedList, arrayList, scanner);
        
        System.out.println("Часть2");
        claculateDdynamic(arrayList, linkedList);
        writeDynamyc(linkedList);
    }

    //Подсчитывает D для статических массивов
    public static void calculate_D(float[] array1, float[] array2){
        System.out.println(max_value(array1) - min_value(array2));
        System.out.println(max_value(array2) - min_value(array1));
        System.out.println("");
    }

    //Подсчитывает D для динамических массивов
    public static void  claculateDdynamic(List<Float> array1, List<Float> array2){
        System.out.println(max_valueDynamic(array1) - min_valueDynamic(array2));
        System.out.println(max_valueDynamic(array2) - min_valueDynamic(array1));
        System.out.println("");
    }
    
    //Считывание элементов в статические массивы
    public static  void read_int(float[] array, Scanner scanner){
        for (int i = 0; i < array.length; i++){
            array[i] = (float)scanner.nextInt() / 2;
        }
    }

    // Считываение элементов в динамические массивы
    public static void read_dynamic(LinkedList<Float> array1,ArrayList<Float> array2, Scanner scanner){

        System.out.println("ArrayList(3 values):");
        for(int i = 0; i< 3; i++){
            float value = (float)scanner.nextInt() / 2;
            
            array2.add(value);
        }

        System.out.println("LinkedList");
        for(int i = 0; i < 4; i++){
            float value = (float)scanner.nextInt() / 2;

            array1.add(value);
        }
    }

    //Поиск максимального значения в статическом
    public static  float max_value(float[] array){
        float value = array[0];
        for(int i=0; i < array.length; i++){
            if(array[i] > value){
                value = array[i];
            }
        }
        return value;
    }

    //Поиск максимального значения в динамическом
    public static  float max_valueDynamic(List<Float> array){
        float value = array.get(0);
        for(int i=0; i < array.size(); i++){
            if(array.get(i) > value){
                value = array.get(i);
            }
        }
        return value;
    }

    //Поиск минимального значения в динамическом
    public static  float min_valueDynamic(List<Float> array){
        float value = array.get(0);
        for(int i=0; i < array.size(); i++){
            if(array.get(i) < value){
                value = array.get(i);
            }
        }
        return value;
    }

    //Поиск минимального в статическом
    public static  float min_value(float[] array){
        float value = array[0];
        for(int i=0; i < array.length; i++){
            if(array[i] < value){
                value = array[i];
            }
        }
        return value;
    }

    //Вывод элементов статического
    public static void write(float[] array){
        for(int i=0; i < array.length; i++){
            System.out.printf("%.2f ", array[i]);
        }
    }

    //Вывод элементов динамического
    public static void writeDynamyc(List<Float> array) {
        for(float num : array){
            System.out.printf("%.2f ", num);
        }
    }
}