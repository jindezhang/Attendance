package dbutils;

public class Trace {
	public static void prtStack( String msg ){
		StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
		String clazzName = stack.getClassName();
		clazzName = clazzName.replaceAll( 
				"\\w+(\\.(\\w+))+", "$2" );
		String methodName = stack.getMethodName();
		int lineNum = stack.getLineNumber();
		System.out.printf( "[%s] --> [%s] (%d) Object: %s\n", clazzName, 
				methodName, lineNum, msg );
	}
	public static void print( Object obj ){
		prtStack( obj.toString() );
	}
	public static void print( Exception e ){
		StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
		String clazzName = stack.getClassName();
		clazzName = clazzName.replaceAll( 
				"\\w+(\\.(\\w+))+", "$2" );
		String methodName = stack.getMethodName();
		int lineNum = stack.getLineNumber();
		System.out.printf( "[%s] --> [%s] (%d) Exception:%s\n", clazzName, 
				methodName, lineNum, e.getMessage() );
	}
	public static void print( Object[] eles ){
		StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
		String clazzName = stack.getClassName(); 
		clazzName = clazzName.replaceAll( 
				"\\w+(\\.(\\w+))+", "$2" );
		String methodName = stack.getMethodName();
		int lineNum = stack.getLineNumber();
		
		StringBuffer sb = new StringBuffer();
		int count = 0;
		for( Object o : eles ){
			sb.append( "["+ o +"]," );
			count ++;
		}
		if( count > 1 ){
			sb.setLength( sb.length() - 1 );
		}
		System.out.printf( "[%s] --> [%s] (%d) Array: %s\n", clazzName, 
				methodName, lineNum, sb.toString() );
	}
	public static void print(){
		StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
		String clazzName = stack.getClassName(); 
		clazzName = clazzName.replaceAll( 
				"\\w+(\\.(\\w+))+", "$2" );
		String methodName = stack.getMethodName();
		int lineNum = stack.getLineNumber();
		System.out.printf( "[%s] --> [%s] (%d)\n", clazzName, methodName, lineNum );
	}
	public static void print( String message ){
		//1 --> 为当前类, 2-->调用类  
		StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
		String clazzName = stack.getClassName(); 
		clazzName = clazzName.replaceAll( 
				"\\w+(\\.(\\w+))+", "$2" );
		String methodName = stack.getMethodName();
		int lineNum = stack.getLineNumber();
		System.out.printf( "[%s] --> [%s] (%d) %s\n", 
				clazzName, methodName, lineNum, message );
	}
	
	public static void printf(String format, Object...args) {
		StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
		String clazzName = stack.getClassName(); 
		clazzName = clazzName.replaceAll( 
				"\\w+(\\.(\\w+))+", "$2" );
		String methodName = stack.getMethodName();
		int lineNum = stack.getLineNumber();
		String message = String.format(format, args);
		System.out.printf( "[%s] --> [%s] (%d) %s\n", 
				clazzName, methodName, lineNum, message );
	}
}
