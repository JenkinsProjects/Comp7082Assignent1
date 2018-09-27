package bcit.comp7082assignent1.unittests;

/**
 * Created by Paul on 2018-09-20.
 */

public interface IDataStore {
    void saveState(String state);
    String getState();
}
