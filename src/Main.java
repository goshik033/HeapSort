/*
-- ПРИНЦИП РАБОТЫ --
Первым делом создаем кучу, корень которой будет максимальным элементом.
И добавляем в эту кучу объекты класса Person.
Когда мы добавляем нового студента, то сначала он встает в самый конец массива,
а потом метот siftUp просеиваем его наверх, если это нужно.
После того, как мы получили кучу, мы удаляем самого первого студента из кучи и добавляем его в StringBuilder,
а первого студента кучи заменяем самым последним
и используем метод siftDown, чтобы просеять студента вниз если он стоит не на своем месте.
в итоге в StringBuilder у нас будет лежать отсортированная последовательность.

-- ДОКАЗАТЕЛЬСТВО КОРРЕКТНОСТИ --
Элементы в массиве, который является кучей начинаются с индекса 1, дети элемента под индексом i будут лежать
под индексами i*2 и i*2+1.
Метод siftUp будет просеивать элемент, пока его индекс не будет меньше или равен единице
или пока он не станет меньше родителя.
Метод siftDown просеиваем, пока индекс правого ребенка меньше или равен количеству элементов в куче или пока
родитель меньше ребенка.

-- ВРЕМЕННАЯ СЛОЖНОСТЬ --
Считывание входных данных занимает O(n), где n - количество студентов.
Создание и заполнение кучи занимает O(n*log n) , где n - количество студентов.
Так как мы добавляем нового студента n раз и просеиваем его вверх за O(log n) методом siftUp
Удаление студента из кучи O(n * log n) из-за того, что мы удаляем студентов n раз
и просеиваем новый корень вниз за O(log n)
Итак, общая временная сложность данного алгоритма составляет O(n * log n)

-- ПРОСТРАНСТВЕННАЯ СЛОЖНОСТЬ --
Массив, в котором хранятся студенты, будет занимать O(n) дополнительной памяти.

ID 90597536
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(bf.readLine());
        Heap heap = new Heap(n + 1);
        for (int i = 0; i < n; i++) {
            String[] str = bf.readLine().split(" ");
            Person person = new Person(str[0], Integer.parseInt(str[1]), Integer.parseInt(str[2]));
            heap.array[i + 1] = person;
        }
        heap.size = n;
        for (int i = n / 2 + 1; i >= 1; i--) {
            heap.heapify(i);
        }
        while (heap.size >= 1) {
            Person result = heap.removeRoot();
            sb.append(result.login + "\n");
        }
        System.out.println(sb);
    }

    static class Heap {
        int size;
        Person[] array;

        public Heap(int capacity) {
            this.array = new Person[capacity];
            size = 1;
        }

        public void heapify(int index) {
            int leftChild;
            int rightChild;
            int largestChild;

            while (true) {
                leftChild = 2 * index;
                rightChild = 2 * index + 1;
                largestChild = index;


                if (leftChild <= size && array[leftChild].compareTo(array[largestChild]) > 0) {
                    largestChild = leftChild;
                }
                if (rightChild <= size && array[rightChild].compareTo(array[largestChild]) > 0) {
                    largestChild = rightChild;
                }
                if (largestChild == index) {
                    break;
                }

                Person temp = array[index];
                array[index] = array[largestChild];
                array[largestChild] = temp;
                index = largestChild;
            }
        }

        public Person removeRoot() {
            Person result = array[1];
            array[1] = array[size];
            heapify(1);
            size--;
            return result;
        }
    }

    static class Person implements Comparable<Person> {
        String login;
        int points;
        int penalty;

        public Person(String login, int points, int penalty) {
            this.login = login;
            this.penalty = penalty;
            this.points = points;
        }

        @Override
        public int compareTo(Person other) {
            if (this.points < other.points) {
                return -1;
            } else if (this.points > other.points) {
                return 1;
            } else if (this.penalty < other.penalty) {
                return 1;
            } else if (this.penalty > other.penalty) {
                return -1;
            }
            return other.login.compareTo(this.login);
        }
    }
}