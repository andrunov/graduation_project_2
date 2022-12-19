package ru.agorbunov.restaurant.util;

import ru.agorbunov.restaurant.model.BaseEntity;
import ru.agorbunov.restaurant.util.exception.EmptyListException;
import ru.agorbunov.restaurant.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class for different validation methods
 */
public class ValidationUtil {

    private ValidationUtil() {
    }

    /*check that object was found in repository class
    * use if from repository returns false in case of not found*/
    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    /*check that object was found in repository class
    * use if from repository returns null in case of not found*/
    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    /*check that object was found or throw NotFoundException*/
    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    /*throws NotFoundException in case of not found object*/
    private static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    /*check that entity is new*/
    public static void checkNew(BaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    /*check that String not equals null or not equals empty string*/
    public static void checkEmpty(String string, String description){
        if ((string==null)||(string.trim().equals(""))){
            throw new IllegalArgumentException(description + " must be not empty");
        }
    }

    /*check that String not equals null or not equals empty string*/
    public static boolean checkEmpty(String string){
        return  ((string!=null)&&(!string.trim().equals("")));
    }

    /*check that Double bigger than zero */
    public static void checkEmpty(Double value, String description){
        if (value<=0.0){
            throw new IllegalArgumentException(description + " must be positive and more than zero");
        }
    }

    /*check that LocalDateTime not equals null */
    public static void checkEmpty(LocalDateTime dateTime, String description){
        if (dateTime==null){
            throw new IllegalArgumentException(description + " must be not empty");
        }
    }

    /*check that LocalDateTime not equals null */
    public static void checkEmpty(LocalDate date, String description){
        if (date == null){
            throw new IllegalArgumentException(description + " must be not empty");
        }
    }


    /*check that list has elements*/
    public static <T> void checkEmptyList(List<T> list){
        if ((list == null)&&(list.size()==0)) throw  new EmptyListException("empty list: " + list);
    }

    /*check that int[] array has elements and they sum > 0*/
    public static void checkEmptyArray(int[] intArray){
        if ((intArray == null)&&(intArray.length==0)) throw  new EmptyListException("empty int array: " + intArray);
        int quantity = 0;
        for (int element : intArray){
            quantity = quantity + element;
        }
        if (quantity <=0) throw  new EmptyListException("null sum elements array: " + intArray);
    }

    /*check that String[] array has elements */
    public static void checkEmptyArray(String[] stringArray){
        if ((stringArray == null)&&(stringArray.length==0)) throw  new EmptyListException("empty String array: " + stringArray);
    }

}
