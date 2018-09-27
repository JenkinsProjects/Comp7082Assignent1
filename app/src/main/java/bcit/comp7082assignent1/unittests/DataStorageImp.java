package bcit.comp7082assignent1.unittests;

/**
 * Created by Paul on 2018-09-20.
 */

public class DataStorageImp implements IDataStore {
    public String state_ = null;

    @Override
    public void saveState(String state) {
        state_ = state;
    }

    @Override
    public String getState() {
        return state_;
    }
}
