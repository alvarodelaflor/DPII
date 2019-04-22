package aa.aa;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Integer res = 0;
    	while (res < 100) {
			System.out.println(generaAdmin(res));
			res++;
		}
    }
    
    private static String generaAdmin(Integer num) {
    	String res = "";
    	res = 
    	"carferbenUserName" + num + "," + "carferbenEmail" + num+ "@carferbenEmail" + num;
    	return res;
    }
}
