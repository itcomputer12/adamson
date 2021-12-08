import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

public abstract class DataStore {
    public abstract String getCurrentTime();

    public abstract void searchData(String value, @NotNull Map<String, ArrayList<String>> allRecords);

    public abstract void menu(Map<String, ArrayList<String>> allRecords);

    public abstract String duration(String checkInTime, String checkOutTime) throws ParseException;

    public abstract Map<String, ArrayList<String>> initData();

    public abstract void allData(@NotNull Map<String, ArrayList<String>> allRecords);

    public abstract boolean checkOutTime(String checkin, String checkout);
}
