
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach

    public void setup(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("White Sauce Pasta",249);
        restaurant.addToMenu("Chicken BBQ Pizza", 375);
        restaurant.addToMenu("Choco Lava Cake", 120);
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime mockCurrentTime = LocalTime.parse("12:00:00");
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(mockCurrentTime); //mocks as 12pm
        assertTrue(spiedRestaurant.isRestaurantOpen());

        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(mockCurrentTime.plusHours(5)); //mocks as 5pm
        assertTrue(spiedRestaurant.isRestaurantOpen());

        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(mockCurrentTime.minusHours(1)); //mocks as 11am
        assertTrue(spiedRestaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime mockCurrentTime = LocalTime.parse("23:30:00");
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(mockCurrentTime);
        assertFalse(spiedRestaurant.isRestaurantOpen());

        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(mockCurrentTime.plusHours(4)); //mocks as 3:30am
        assertFalse(spiedRestaurant.isRestaurantOpen());

        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(mockCurrentTime.minusHours(1)); // mocks as 10:30pm
        assertFalse(spiedRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // <<<<<<<<<<<<<<<<<<<<<<< Order Value Calculator Tests >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void passing_a_list_of_string_type_as_parameter_expecting_sum_of_all_the_prices_of_items_int_type(){

        String selectedItems1[] = new String[] {"Sweet corn soup", "Chicken BBQ Pizza", "Vegetable lasagne" };
        String selectedItems2[] = new String[] {"White Sauce Pasta", "Chicken BBQ Pizza", "Choco Lava Cake" };


        assertEquals(763,restaurant.calculateOrderValue(selectedItems1)); 
        assertEquals(744,restaurant.calculateOrderValue(selectedItems2));

    }



}