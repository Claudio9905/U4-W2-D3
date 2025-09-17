import entities.Customer;
import entities.Order;
import entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //1
        Supplier<Product> detailsProduct = () -> {
            System.out.println("Stabilire l'ID del prodotto:");
            Long idProdotto = Long.parseLong(scanner.nextLine());
            System.out.println("Stabilire il nome del prodotto:");
            String nomeProdotto = scanner.nextLine();
            System.out.println("Stabilire la categoria del prodotto:");
            String categoriaProdotto = scanner.nextLine();
            System.out.println("Stabilire il prezzo del prodotto:");
            Double prezzoProdotto = Double.parseDouble(scanner.nextLine());

            return new Product(idProdotto, nomeProdotto, categoriaProdotto, prezzoProdotto);
        };
        List<Product> listaProdotti = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            listaProdotti.add(detailsProduct.get());
        }


        Supplier<Customer> detailsCustomer = () -> {
            System.out.println("| - Dettagli cliente:");
            System.out.println("ID:");
            Long idCustomer = Long.parseLong(scanner.nextLine());
            System.out.println("Nome:");
            String nameCustomer = scanner.nextLine();
            System.out.println("Tier:");
            Integer tierCustomer = Integer.parseInt(scanner.nextLine());

            return new Customer(idCustomer, nameCustomer, tierCustomer);
        };
        Customer customer = detailsCustomer.get();


        Supplier<Order> detailsOrders = () -> {

            System.out.println("| - Inserire i dettagli dell'ordine:");
            System.out.println("ID:");
            Long idOrdine = Long.parseLong(scanner.nextLine());
            System.out.println("Status:");
            String statusOrdine = scanner.nextLine();
            System.out.println("-------------------------");
            System.out.println("| - Data dell'ordine:");
            System.out.println("Anno:");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.println("Mese:");
            int month = Integer.parseInt(scanner.nextLine());
            System.out.println("Giorno:");
            int day = Integer.parseInt(scanner.nextLine());
            LocalDate orderDate = LocalDate.of(year, month, day);
            System.out.println("| - Data della consegna:");
            System.out.println("Anno:");
            int yearDelivery = Integer.parseInt(scanner.nextLine());
            System.out.println("Mese:");
            int monthDelivery = Integer.parseInt(scanner.nextLine());
            System.out.println("Giorno:");
            int dayDelivery = Integer.parseInt(scanner.nextLine());
            LocalDate deliveryDate = LocalDate.of(yearDelivery, monthDelivery, dayDelivery);
            System.out.println("-------------------------");

            return new Order(idOrdine, statusOrdine, orderDate, deliveryDate, listaProdotti, customer);
        };
        List<Order> listaOrdini = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            listaOrdini.add(detailsOrders.get());
        }

        System.out.println("[------------ Products ------------]");
        listaProdotti.forEach(System.out::println);
        System.out.println("[------------ Orders ------------]");
        listaOrdini.forEach(System.out::println);


        //1.1 Ottenere una lista di prodotti che appartengono alla categoria <<Books>> ed hanno un prezzo > 100

        Predicate<Product> isMoreThanUndred = product -> product.getPrice() > 100;
        Predicate<Product> categoryBooks = product -> product.getCategory().contains("Books");
        List<Product> booksProduct = listaProdotti.stream().filter(isMoreThanUndred.and(categoryBooks)).toList();
        System.out.println("Lista dei prodotti con categoria 'Books' e con un prezzo maggiore di 100: ");
        System.out.println(booksProduct);


        //1.2 Ottenere una lista di ordini con prodotti che appartengono alla categoria <<Baby>>
        Predicate<Product> categoryBaby = product -> product.getCategory().contains("Baby");
        List<Product> babyProduct =  listaProdotti.stream().filter(categoryBaby).toList();
        List<Order> babyOrder = listaOrdini.stream().filter(order -> order.getProducts().equals(babyProduct)).toList();
        System.out.println("Lista degli ordini dei prodotti con categoria 'Baby': ");
        System.out.println(babyOrder);


        //1.3 Ottenere una lista di prodotti che appartengono alla categoria <<Boys>> ed applicare 10% di sconto al loro prezzo
        //Predicate<Product> categoryBoys = product -> product.getCategory().contains("Boys");
        List<String> boysProduct = listaProdotti.stream().map(product -> product.getId() + product.getName() + product.getCategory().contains("Boys") + product.getPrice()).toList();
        System.out.println("Lista dei prodotti con categoria 'Boys':");
        System.out.println(boysProduct);

        //1.4 Ottenere una lista di prodotti ordinati da clienti di livello (tier2) tra l'01-Feb-2021 e l'01-Apr-2021
        LocalDate first = LocalDate.of(2021,02,01);
        Predicate<Order> tierLevel2 = order -> order.getCustomer().getTier().equals(2);
        //List<Order> orderListTier2 = listaOrdini.stream().filter(tierLevel2.and()).allMatch(order -> order.getOrderDate().equals(first)).toList();
        //System.out.println("Lista di prodotti ordinati da clienti di livello 'tier 2': ");
        //System.out.println(orderListTier2);

    }
}
