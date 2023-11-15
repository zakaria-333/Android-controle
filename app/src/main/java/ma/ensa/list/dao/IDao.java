package ma.ensa.list.dao;

import java.util.List;

public interface IDao <T>{

    boolean create (T o);
    T findById(int id);
    boolean delete (T o);
    boolean update (T o);
}
