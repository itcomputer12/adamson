import com.sun.deploy.util.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public  class DataStoreImp extends DataStore{
    @Override
    public Map<String, ArrayList<String>> initData(){
        Map<String, ArrayList<String>> allRecords = new LinkedHashMap<String, ArrayList<String>>(16,0.75f,false);
        return allRecords;
    }

    @Override
    public String getCurrentTime(){
        Date now = new Date();
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format0.format(now.getTime());
    }

    @Override
    public void searchData(String value, @NotNull Map<String, ArrayList<String>> allRecords) {
        if (allRecords.size() == 0) {
            System.out.println("No record, please check in and check out first!\n");
            return;
        }
        System.out.printf("|%-20s|%-20s|%-20s|%-20s|\n", "EmployeeId", "FirstName", "LastName", "Duration");
        Iterator iter = allRecords.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String lineString = StringUtils.join((ArrayList) entry.getValue(), "");
            String FirstName = (String) ((ArrayList) entry.getValue()).get(1);
            if (lineString.contains(value) || value.charAt(0) == FirstName.charAt(0)) {
                System.out.printf("|%-20s|", ((ArrayList) entry.getValue()).get(0));
                System.out.printf("%-20s|", FirstName);
                System.out.printf("%-20s|", ((ArrayList) entry.getValue()).get(2));
                String checkInTime = (String) ((ArrayList) entry.getValue()).get(3);
                String checkOutTime = (String) ((ArrayList) entry.getValue()).get(4);
                System.out.printf("%-20s|\n", duration(checkInTime, checkOutTime));
            }
        }
    }

    @Override
    public String duration(String checkInTime, String checkOutTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            java.util.Date in = df.parse(checkInTime);
            java.util.Date out = df.parse(checkOutTime);
            long l = out.getTime() - in.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            return "" + day + "d" + hour + "h" + min + "m" + s + "s";
        } catch (ParseException e) {
            System.out.printf("parse checkInTime or checkOutTime error, cause:%s", e);
        }
        return "";
    }

    @Override
    public void allData(@NotNull Map<String, ArrayList<String>> allRecords) {
        System.out.printf("|%-20s|%-20s|%-20s|%-20s|%-20s|%-20s|\n", "RecordID", "EmployeeId", "FirstName", "LastName", "CheckInTime", "CheckInTime");
        Iterator iter = allRecords.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            System.out.printf("|%-20s|", entry.getKey());
            for (Object value : (ArrayList) entry.getValue()) {
                System.out.printf("%-20s|", value);
            }
            System.out.printf("\n");
        }
        System.out.println("\n");
    }

    @Override
    public void menu(Map<String, ArrayList<String>> allRecords){
        while (true) {
            System.out.printf("Please select different options number:\n" +
                    "1.check-in.\n" +
                    "2.check-out.\n" +
                    "3.check your attendance.\n" +
                    "4.see all attendance.\n" +
                    "5.exit\n");
            int choice;
            Scanner scannerObj = new Scanner(System.in);
            choice = scannerObj.nextInt();

            Employee employee = new Employee();
            Attendance attendance = new Attendance();

            switch (choice) {
                case 1:
                    ArrayList<String> record = new ArrayList<String>();
                    System.out.printf("Please input your employee number:\n");
                    Scanner checkInInput = new Scanner(System.in);
                    employee.setEmployeeID(checkInInput.nextLine());
                    record.add(employee.getEmployeeID());

                    System.out.printf("Please input your firstname:\n");
                    employee.setEmployeeFirstName(checkInInput.nextLine());
                    record.add(employee.getEmployeeFirstName());


                    System.out.printf("Please input your lastname:\n");
                    employee.setEmployeeLastName(checkInInput.nextLine());
                    record.add(employee.getEmployeeLastName());

                    System.out.printf("Please input your check-in time(format: '2021-12-08 10:01:02'):\n");
                    attendance.setTimeIn(checkInInput.nextLine());
                    record.add(attendance.getTimeIn());

                    allRecords.put(employee.getEmployeeID() + "-" + attendance.getTimeIn().split(" ")[0], record);
                    System.out.println("Check in successfully!!!\n");
                    break;
                case 2:
                    Scanner checkOutInput = new Scanner(System.in);
                    System.out.printf("Please input your employee number:\n");
                    String employeeIdCheckout = checkOutInput.nextLine();

                    System.out.printf("Please input your check-out time(format: '2021-12-08 18:11:12'):\n");
                    attendance.setTimeOut(checkOutInput.nextLine());
                    String checkOutDay = attendance.getTimeOut().split(" ")[0];

                    String storeKey = employeeIdCheckout + "-" + checkOutDay;
                    if (allRecords.containsKey(storeKey)) {
                        if (!checkOutTime(allRecords.get(storeKey).get(3), attendance.getTimeOut())) {
                            break;
                        }
                        if (allRecords.get(storeKey).size() > 4) {
                            allRecords.get(storeKey).set(4, attendance.getTimeOut());
                        } else {
                            allRecords.get(storeKey).add(attendance.getTimeOut());
                        }
                        System.out.println("Check out successfully!!!\n");
                    } else {
                        System.out.println("You didn't check in, please check in again!");
                    }
                    break;
                case 3:
                    System.out.println("(You can input your employee number, Lastname, Firstname and Middle Initial,\n " +
                            "also your time-in and time-out for search in this system.)\n" +
                            "Please input the search string:");
                    Scanner searchInput = new Scanner(System.in);
                    String searchString = searchInput.nextLine();
                    searchData(searchString, allRecords);
                    break;
                case 4:
                    allData(allRecords);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Please intput above options correctly and must be number!");
                    break;
            }
        }
    }

    @Override
    public boolean checkOutTime(String checkin, String checkout) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            java.util.Date in = df.parse(checkin);
            java.util.Date out = df.parse(checkout);
            if (out.getTime() - in.getTime() <= 0) {
                System.out.println("The time of check-out should be later than that of check-in!");
                return false;
            }
        } catch (ParseException e) {
            System.out.printf("parse checkInTime or checkOutTime error, cause:%s", e);
        }
        return true;
    }
}
