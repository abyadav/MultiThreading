import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LRU {



    public static void main(String[] args) {
        Thread t1=new Thread(()->{
            System.out.println("Way 1");
            add(1,2);
        });

        Thread t2=new Thread(new Runnable() {
            public void run(){
                System.out.println("Way 2");
            }
        });
        Th th=new Th();
        Thread t3=new Thread(th);
    }

}
class Th implements Runnable{
    public void run(){
        System.out.println("Way 3");
    }
}





you are not audible
Hi by Dilip Chandra Sutradhar
Dilip Chandra Sutradhar
4:02 PM
Hi
Will the code compile successfully? If YES,... by Dilip Chandra Sutradhar
Dilip Chandra Sutradhar
4:16 PM
Will the code compile successfully? If YES, what will be the output of the program? IF NO WHY NO?
 
class A {
void m1(A a) {
    System.out.println("m1 method in class A");	 
}
}
class B extends A {	
public void m1(A a) {
    System.out.println("m1 method in class B");
}
}
public class Test{
public static void main(String[] args){
A a = new A();
a.m1(a);
a.m1(new B());
B b = new B();
b.m1(null);
a = b;
a.m1(null);
a.m1(new A());
  }
}

m1 method in class A m1 method in class A m... by Abhishek Kumar Yadav
4:20 PM
Abhishek Kumar Yadav
m1 method in class A
m1 method in class A
m1 method in class B
m1 method in class B
m1 method in class B
SOLID by Dilip Chandra Sutradhar
Dilip Chandra Sutradhar
4:33 PM
SOLID
Microservice Pattern by Dilip Chandra Sutradhar
Dilip Chandra Sutradhar
4:33 PM
Microservice Pattern
Polymorphism by Dilip Chandra Sutradhar
Dilip Chandra Sutradhar
4:34 PM
Polymorphism
Switch() On OFF by Dilip Chandra Sutradhar
Dilip Chandra Sutradhar
4:36 PM
Switch()
On
OFF
public class Solution {                    ... by Abhishek Kumar Yadav
4:44 PM
Abhishek Kumar Yadav
public class Solution {
    
        
        
        
        
}
 
class Switch{
 
    void changeLighting (Movement command);
    
    
    
}
class {}
 
class TurnOn{
    void changeLighting (Up command){
 
    }
}
 
class TurnOff{
    void changeLighting (Down command);
}
Lamdas by Dilip Chandra Sutradhar
Dilip Chandra Sutradhar
4:48 PM
Lamdas
HashMap<> vs concurrentHashMap<> by Dilip Chandra Sutradhar
Dilip Chandra Sutradhar
4:49 PM
HashMap<> vs concurrentHashMap<>
How COncurrentHashMap achieved its concurre... by Dilip Chandra Sutradhar
Dilip Chandra Sutradhar
4:49 PM
How COncurrentHashMap achieved its concurrency ?
can we break singleton pattern ? how by Dilip Chandra Sutradhar
Dilip Chandra Sutradhar
4:52 PM
can we break singleton pattern ? how
import java.util.ArrayList; import java.uti... by Abhishek Kumar Yadav
5:00 PM
Abhishek Kumar Yadav
import java.util.ArrayList;
import java.util.List;
 
static class Singleton{
    List<Integer> list;
    private Singleton(){
    }
 
    public List<Integer> getList(){
        if(list!=null){
            return list;
        }
 
        synchronized(this){
            if(list==null){
                list=new ArrayList<>();
            }
 
            return list;
        }
    }
}
 
// Singleton.getList();
import java.util.concurrent.locks.Lock; imp... by Abhishek Kumar Yadav
5:10 PM
Abhishek Kumar Yadav
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
 
class LRU{
    private static List<Integer> arr=new ArrayList<>();
    
    private static Lock lock=new ReentrantLock();
    public synchronized void add(int n){
        while(arr.size()==cacheSize){
            lock.lock();
            evict();
            lock.unlock();
        }
    }
 
    public void evict(){
        if(arr.size()==cacheSize){
            arr.remove(arr.size()-1);
        }
    }
}
import java.util.ArrayList; import java.uti... by Abhishek Kumar Yadav
5:20 PM
Abhishek Kumar Yadav
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
 
class LRU {
 
    private static Map<Integer, Integer> map=new HashMap();
    
    private static int size=10;
    
 
    private static synchronized void add(int x, int y){
        if(map.size()==size){
            evict();
        }
        map.put(x,y);
    }
 
    private static void evict(){
        map.remove();
    }
 
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println("Ok");
        });
        t.start();
    }
}
import java.util.ArrayList; import java.uti... by Abhishek Kumar Yadav
5:27 PM
Abhishek Kumar Yadav
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
 
class LRU {
 
    private static Map<Integer, Integer> map = new HashMap();
 
    private static int size = 10;
 
    private static synchronized void add(int x, int y) {
        if (map.size() == size) {
            evict();
 
        }
 
        map.put(x, y);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    private static void evict() {
        // map.remove();
    }
 
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            add(1, 2);
        });
        t.start();
 
        Thread t1=new Thread(()->{
            evict();
        });
 
        t1.start();
    }
}
        Thread t1=new Thread(()->{         ... by Abhishek Kumar Yadav
5:32 PM
Abhishek Kumar Yadav
        Thread t1=new Thread(()->{
            System.out.println("Way 1");
        });
 
        Thread t2=new Thread(new Runnable() {
            public void run(){
                System.out.println("Way 2");
            }
        });
        Th th=new Th();
        Thread t3=new Thread(th);
    }
 
}
class Th implements Runnable{
    public void run(){
        System.out.println("Way 3");
    }
}
has context menu


has context menu