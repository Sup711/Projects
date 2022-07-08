public class EllipsisDemo {

    private static void showNames( String... names ) {
        System.out.println( System.lineSeparator() + names.length + " names" );
        for ( String name: names ) System.out.print( name + ' ' );
        System.out.println();
    }

    public static void main( String[] args ) {

        EllipsisDemo.showNames( "Manny", "Moe", "Jack" );

        String[] buddies = { "Bob", "Carol", "Ted", "Alice" };
        EllipsisDemo.showNames( buddies );

        EllipsisDemo.showNames();
    }
}
