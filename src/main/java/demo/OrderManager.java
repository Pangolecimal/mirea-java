package demo;

import java.util.HashMap;

public class OrderManager {
    private final HashMap<String, Order> address_order;

    OrderManager() {
        this.address_order = new HashMap<String, Order>();
    }

    public boolean addOrder(String address, Order order) throws Exception {
        if (address_order.containsKey(address))
            throw new Exception("OrderAlreadyAddedException");
        else {
            address_order.put(address, order);
            return true;
        }
    }

    public Order getOrder(String address) {
        return address_order.getOrDefault(address, null);
    }

    public boolean removeOrder(String address) {
        try {
            if (!address_order.containsKey(address)) {
                return false;
            }

            address_order.remove(address);
            return true;
        } catch (Exception exc) {
            System.out.println("E:" + exc.getMessage());
            return false;
        }
    }

    public boolean addAddressItem(String address, Item item) {
        Order order = address_order.getOrDefault(address, null);
        if (order == null)
            return false;
        return order.add(item);
    }

    public Order[] getInternetOrders() {
        int InternetOrdersSize = 0, index = 0;
        Order[] order = new Order[address_order.size()];

        for (String str : address_order.keySet()) {
            order[index] = address_order.get(str);

            if (order[index] instanceof InternetOrder) {
                InternetOrdersSize++;
            }

            index++;
        }

        index = 0;
        Order[] orders = new Order[InternetOrdersSize];

        for (int i = 0; i < address_order.size(); i++) {
            if (order[i] instanceof InternetOrder) {
                orders[index++] = order[i];
            }
        }

        return orders;
    }

    private Order[] getOrders() {
        int index = 0;
        Order[] orders = new Order[address_order.size()];
        for (String str : address_order.keySet())
            orders[index++] = address_order.get(str);
        return orders;
    }

    public float getTotalInternetOrdersPrice() {
        float sum = 0;
        Order[] orders = getInternetOrders();

        for (Order order : orders) {
            sum += order.getTotalPrice();
        }

        return sum;
    }

    public int getTotalItemOrders(String name) {
        int count = 0;
        Order[] orders = getOrders();

        for (Order order : orders) {
            count += order.getTotalItemCount(name);
        }

        return count;
    }
}