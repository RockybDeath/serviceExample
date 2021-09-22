public class Main {
    public static void main(String[] args) {
        ServiceMethods serviceMethods = new ServiceMethods("http://127.0.0.1",8080);
        System.out.println(serviceMethods.getAll());
        System.out.println(serviceMethods.load());
        System.out.println(serviceMethods.getAll());
    }
}
