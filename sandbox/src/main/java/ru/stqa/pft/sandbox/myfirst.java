package ru.stqa.pft.sandbox;

public class Myfirst {
    public static void main(String[] args) {
        hello("world");
        hello("user");
        hello("kitty");

        Square s = new Square(5);
        System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

        Rectangle r = new Rectangle(4,6);
        System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + r.area());

        Point p1 = new Point(3,4);
        System.out.println("Координаты первой точки: X = " + p1.x + "; Y = " + p1.y);

        Point p2 = new Point(6,7);
        System.out.println("Координаты второй точки: X = " + p2.x + "; Y = " + p2.y);

        System.out.println("Расстояние между точками равно " + p1.distance(p2));
    }

    public static void hello(String somebody) {
        System.out.println("hello " + somebody + "!");
    }

}