import java.util.ArrayList;
import java.util.Map;
public class Main {
    public static void main(String[] args) {
        DataStore dataStore = new DataStoreImp();
        Map<String, ArrayList<String>> allRecords = dataStore.initData();
        System.out.println("Welcome to attendance record system! " +
                "Your current date of attendance is " + dataStore.getCurrentTime());
        dataStore.menu(allRecords);
    }
}/*program demonstration==>
Welcome to attendance record system! Your current date of attendance is 2021-12-08 19:20:55
Please select different options number:
1.check-in.
2.check-out.
3.check your attendance.
4.see all attendance.
5.exit
1
Please input your employee number:
111
Please input your firstname:
arun
Please input your lastname:
yang
Please input your check-in time(format: '2021-12-08 10:01:02'):
2021-12-08 10:01:02
Check in successfully!!!

Please select different options number:
1.check-in.
2.check-out.
3.check your attendance.
4.see all attendance.
5.exit
2
Please input your employee number:
111
Please input your check-out time(format: '2021-12-08 18:11:12'):
2021-12-08 10:01:02
The time of check-out should be later than that of check-in!
Please select different options number:
1.check-in.
2.check-out.
3.check your attendance.
4.see all attendance.
5.exit
2
Please input your employee number:
111
Please input your check-out time(format: '2021-12-08 18:11:12'):
2021-12-08 18:01:02
Check out successfully!!!

Please select different options number:
1.check-in.
2.check-out.
3.check your attendance.
4.see all attendance.
5.exit
4
|RecordID            |EmployeeId          |FirstName           |LastName            |CheckInTime         |CheckInTime         |
|111-2021-12-08      |111                 |arun                |yang                |2021-12-08 10:01:02 |2021-12-08 18:01:02 |


Please select different options number:
1.check-in.
2.check-out.
3.check your attendance.
4.see all attendance.
5.exit
1
Please input your employee number:
111
Please input your firstname:
arun
Please input your lastname:
yang
Please input your check-in time(format: '2021-12-08 10:01:02'):
2021-12-09 10:01:02
Check in successfully!!!

Please select different options number:
1.check-in.
2.check-out.
3.check your attendance.
4.see all attendance.
5.exit
2
Please input your employee number:
111
Please input your check-out time(format: '2021-12-08 18:11:12'):
2021-12-09 20:01:02
Check out successfully!!!

Please select different options number:
1.check-in.
2.check-out.
3.check your attendance.
4.see all attendance.
5.exit
3
(You can input your employee number, Lastname, Firstname and Middle Initial,
 also your time-in and time-out for search in this system.)
Please input the search string:
2021-12-08
|EmployeeId          |FirstName           |LastName            |Duration            |
|111                 |arun                |yang                |0d8h0m0s            |
Please select different options number:
1.check-in.
2.check-out.
3.check your attendance.
4.see all attendance.
5.exit
5

Process finished with exit code 0
*/
