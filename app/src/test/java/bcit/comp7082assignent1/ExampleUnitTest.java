package bcit.comp7082assignent1;

import org.junit.Test;

import bcit.comp7082assignent1.unittests.DataStorageImp;
import bcit.comp7082assignent1.unittests.IDataStore;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4,2+2);
    }

    @Test
    public void dataStorage() throws Exception{
        IDataStore idb = new DataStorageImp();
        idb.saveState("Testing");
        assertEquals("Testing", idb.getState());
    }
}