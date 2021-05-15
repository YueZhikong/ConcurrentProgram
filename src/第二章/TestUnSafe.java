package 第二章;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class TestUnSafe {
    static final Unsafe unsafe;
    static final long stateOffset;
    private volatile long state = 0;
    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");

            field.setAccessible(true);

            unsafe = (Unsafe) field.get(null);

            stateOffset = unsafe.objectFieldOffset(TestUnSafe.class.getDeclaredField("state"));
        }catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
            throw new Error(ex);
        }
    }

    public static void main(String[] args) {
        TestUnSafe test = new TestUnSafe();
        Boolean success = unsafe.compareAndSwapInt(test,stateOffset,0,1);
        System.out.println(success);
    }
}
