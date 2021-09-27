import java.util.Arrays;
import java.util.Comparator;

public class First
{
    public static void main(String [] args)
    {
        Comparator<String> comparator = Comparator.comparingInt(String::length);
        Comparator<String> nativeComparator = new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                return o1.length() < o2.length() ?  -1 : 1;
            }
        };

        String[] arrStr = new String[]{"1", "2", "423", "73"};
        Arrays.sort(arrStr, First::compare);
        for (String string: arrStr)
        {
            System.out.println(string);
        }
        int b = -1030;
        var c = b >> 3;
        System.out.println(c);
        System.out.println("Hello");
    }
    private static int compare(String o1, String o2)
    {
        return o1.length() -o2.length();
    }
}