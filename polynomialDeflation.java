import java.util.Scanner;
public class polynomialDeflation {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Polinom derecesini giriniz: ");
        int derece = input.nextInt();

        double[] a = new double[derece + 1];
        System.out.println("Polinom katsayılarını giriniz:");
        for (int i=derece; i>=0; i--) {
            System.out.printf("a[%d]: ", i);
            a[i] = input.nextDouble();
        }

        System.out.print("Başlangıç değerini (x0) giriniz: ");
        double x0 = input.nextDouble();
        System.out.print("Delta (δ) değerini giriniz: ");
        double delta = input.nextDouble();
        System.out.print("Maksimum iterasyon sayısını giriniz: ");
        int maxIter = input.nextInt();
        System.out.print("Tolerans için n değeri (0.5×10^2-n): ");
        int n = input.nextInt();
        double eps = 0.5 * Math.pow(10, 2 - n);

        System.out.println("\nIterasyon |   Kök Değeri   | Polinom Derecesi | Katsayılar");
        System.out.println("-----------------------------------------------------------------------------------------");

        double xi = x0;
        int mevcutDerece = derece;

        for (int iter = 0; iter < maxIter && mevcutDerece > 0; iter++) {
            double fxi = f(xi);
            double dx = delta*xi;
            double fxiDelta = f(xi +dx);

            if (Math.abs(fxiDelta-fxi)<1e-10) {
                System.out.printf("%5d     | %14s | %15d  | ", iter+1, "HATA", mevcutDerece);
                katsayilariYazdir(a, mevcutDerece);
                break;
            }

            double xi1 = xi-(dx *fxi) /(fxiDelta- fxi);

            if (Math.abs(fxi)<eps) {
                System.out.printf("%5d     | %14.8f | %15d  | ", iter+1, xi, mevcutDerece);
                katsayilariYazdir(a, mevcutDerece);

                a = dereceDusur(a, xi);
                mevcutDerece--;
                xi = x0;
            } else {
                System.out.printf("%5d     | %14.8f | %15d  | ", iter+1, xi, mevcutDerece);
                katsayilariYazdir(a, mevcutDerece);
                xi = xi1;
            }
        }
        input.close();
    }

    public static double f(double x) {
        return Math.exp(-x)-x;
    }

    public static double[] dereceDusur(double[] a, double kok) {
        int n = a.length-1;
        double[] b = new double[n];
        b[n-1] = a[n];

        for (int i=n-2; i>=0; i--) {
            b[i] = a[i+1]+b[i+1]*kok;
        }
        return b;
    }

    public static void katsayilariYazdir(double[] a, int derece) {
        for (int i=derece; i>=0; i--) {
            System.out.printf("a[%d]=%8.4f ", i, a[i]);
        }
        System.out.println();
    }
}